<%--
  Created by IntelliJ IDEA.
  User: huanyingcool
  Date: 01.09.2019
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var detect_goods = null;// 检测货物编号
    var detect_batch = null; // 检测货物批次
    var detect_repository = null;// 所在仓库编号

    var detect_passed = null;// 良品数量
    var detect_scratch = null // 划痕数量
    var detect_damage = null;// 故障数量

    var goodsCache = new Array();//货物信息缓存

    $(function(){
        repositorySelectorInit();
        dataValidateInit();
        // detilInfoToggle();
        //
        goodsAutocomplete();
        // customerAutocomplete();
        fetchStorage();
        detectionOption();
    })

    // 仓库下拉列表初始化
    function repositorySelectorInit(){
        $.ajax({
            type : 'GET',
            url : 'repositoryManage/getRepositoryList',
            dataType : 'json',
            contentType : 'application/json',
            data : {
                searchType : 'searchAll',
                keyWord : '',
                offset : -1,
                limit : -1
            },
            success : function(response){
                $.each(response.rows,function(index,elem){
                    $('#repository_selector').append("<option value='" + elem.id + "'>" + elem.id +"号仓库</option>");
                });
            },
            error : function(response){
                $('#repository_selector').append("<option value='-1'>加载失败</option>");
            }

        })
    }

    // 数据校验
    function dataValidateInit(){
        $('#passed_form').bootstrapValidator({
            message : 'This is not valid',
            fields : {
                detect_passed : {
                    validators : {
                        notEmpty : {
                            message : '良品数量不能为空'
                        },
                        greaterThan: {
                            value: 0,
                            message: '检测数量不能小于0'
                        }
                    }
                }
            }
        })
        $('#scratch_form').bootstrapValidator({
            message : 'This is not valid',
            fields : {
                detect_passed : {
                    validators : {
                        notEmpty : {
                            message : '划痕数量不能为空'
                        },
                        greaterThan: {
                            value: 0,
                            message: '检测数量不能小于0'
                        }
                    }
                }
            }
        })
        $('#damage_form').bootstrapValidator({
            message : 'This is not valid',
            fields : {
                detect_passed : {
                    validators : {
                        notEmpty : {
                            message : '故障数量不能为空'
                        },
                        greaterThan: {
                            value: 0,
                            message: '检测数量不能小于0'
                        }
                    }
                }
            }
        })
    }

    // 货物信息自动匹配
    function goodsAutocomplete(){
        $('#goods_input').autocomplete({
            minLength : 0,
            delay : 200,
            source : function(request, response){
                $.ajax({
                    type : 'GET',
                    url : 'goodsManage/getGoodsList',
                    dataType : 'json',
                    contentType : 'application/json',
                    data : {
                        offset : -1,
                        limit : -1,
                        keyWord : request.term,
                        searchType : 'searchByName'
                    },
                    success : function(data){
                        var autoCompleteInfo = new Array();
                        $.each(data.rows, function(index,elem){
                            goodsCache.push(elem);
                            autoCompleteInfo.push({label:elem.name,value:elem.id});
                        });
                        response(autoCompleteInfo);
                    }
                });
            },
            focus : function(event, ui){
                $('#goods_input').val(ui.item.label);
                return false;
            },
            select : function(event, ui){
                $('#goods_input').val(ui.item.label);
                detect_goods = ui.item.value;
                loadStorageInfo();
                return false;
            }
        })
    }

    // 获取仓库当前库存量
    function fetchStorage(){
        $('#repository_selector').change(function(){
            detect_repository = $(this).val();
            loadStorageInfo();
        });

    }
    function loadStorageInfo(){
        if(detect_repository != null && detect_goods != null){
            $.ajax({
                type : 'GET',
                url : 'storageManage/getStorageListWithRepository',
                dataType : 'json',
                contentType : 'application/json',
                data : {
                    offset : -1,
                    limit : -1,
                    searchType : 'searchByGoodsID',
                    repositoryBelong : detect_repository,
                    keyword : detect_goods
                },
                success : function(response){
                    if(response.total > 0){
                        data = response.rows[0].number;
                        $('#info_storage').text(data);
                    }else{
                        $('#info_storage').text('0');
                    }
                },
                error : function(response){

                }
            })
        }
    }

    // 执行货物检测操作
    function detectionOption(){
        $('#submit').click(function(){
            // data validate
            $('#passed_form').data('bootstrapValidator').validate();
            if (!$('#passed_form').data('bootstrapValidator').isValid()) {
                return;
            }
            $('#scratch_form').data('bootstrapValidator').validate();
            if (!$('#scratch_form').data('bootstrapValidator').isValid()) {
                return;
            }
            $('#damage_form').data('bootstrapValidator').validate();
            if (!$('#damage_form').data('bootstrapValidator').isValid()) {
                return;
            }

            data = {
                goodsID : detect_goods,
                batch : $('#batch_input').val(),
                repositoryID : detect_repository,
                passed: $('#passed_input').val(),
                scratch: $('#scratch_input').val(),
                damage: $('#damage_input').val(),

            }

            $.ajax({
                type : 'POST',
                url : 'detectManage/detect',
                dataType : 'json',
                content : 'application/json',
                data : data,
                success : function(response){
                    var msg;
                    var type;

                    if(response.result == "success"){
                        type = 'success';
                        msg = '检测录入成功';
                        inputReset();
                    }else{
                        type = 'error';
                        msg = '检测录入失败'
                    }
                    infoModal(type, msg);
                },
                error : function(response){
                    var msg = "服务器错误";
                    var type = "error";
                    infoModal(type, msg);
                }
            })
        });
    }

    // 页面重置
    function inputReset(){
        $('#batch_input').val('');
        $('#goods_input').val('');
        $('#passed_form').bootstrapValidator("resetForm",true);
        $('#scratch_form').bootstrapValidator("resetForm",true);
        $('#damage_form').bootstrapValidator("resetForm",true);
    }

    //操作结果提示模态框
    function infoModal(type, msg) {
        $('#info_success').removeClass("hide");
        $('#info_error').removeClass("hide");
        if (type == "success") {
            $('#info_error').addClass("hide");
        } else if (type == "error") {
            $('#info_success').addClass("hide");
        }
        $('#info_content').text(msg);
        $('#info_modal').modal("show");
    }

</script>

<div class="panel panel-default">
    <ol class="breadcrumb">
        <li>货物检测</li>
    </ol>
    <div class="panel-body">
        <div class="row">

            <div class="col-md-6 col-sm-6">
                <div class="row">
                    <div class="col-md-1 col-sm-1"></div>
                    <div class="col-md-10 col-sm-11">
                        <form action="" class="form-inline">
                            <div class="form-group">
                                <label for="" class="form-label">检测货物：</label>
                                <input type="text" class="form-control" id="goods_input" placeholder="请输入货物名称">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-sm-6">
                <div class="row">
                    <div class="col-md-1 col-sm-1"></div>
                    <div class="col-md-10 col-sm-11">
                        <form action="" class="form-inline">
                            <div class="form-group">
                                <label for="" class="form-label">&ensp;批次号：</label>
                                <input type="text" class="form-control" id="batch_input" placeholder="请输入货物批次号">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" style="margin-top:20px">
            <div class="col-md-6 col-sm-6">
                <div class="row">
                    <div class="col-md-1 col-sm-1"></div>
                    <div class="col-md-10 col-sm-11">
                        <form action="" class="form-inline">
                            <div class="form-group">
                                <label for="" class="form-label">入库仓库：</label>
                                <select name="" id="repository_selector" class="form-control">
                                    <option value="">请选择仓库</option>
                                </select>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" style="margin-top:20px">
            <div class="col-md-6 col-sm-6">
                <div class="row">
                    <div class="col-md-1 col-sm-1"></div>
                    <div class="col-md-10 col-sm-11">
                        <form action="" class="form-inline" id="passed_form">
                            <div class="form-group">
                                <label for="" class="control-label">良品数量：</label>
                                <input type="text" class="form-control" placeholder="请输入良品数量" id="passed_input" name="passed_input">
                                <span>当前库存量：</span>
                                <span id="info_storage">-</span>
                                <%--								<span>)</span>--%>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" style="margin-top:25px">
            <div class="col-md-6 col-sm-6">
                <div class="row">
                    <div class="col-md-1 col-sm-1"></div>
                    <div class="col-md-10 col-sm-11">
                        <form action="" class="form-inline" id="scratch_form">
                            <div class="form-group">
                                <label for="" class="control-label">划痕数量：</label>
                                <input type="text" class="form-control" placeholder="请输入划痕数量" id="scratch_input" name="scratch_input">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-sm-6">
                <div class="row">
                    <div class="col-md-1 col-sm-1"></div>
                    <div class="col-md-10 col-sm-11">
                        <form action="" class="form-inline" id="damage_form">
                            <div class="form-group">
                                <label for="" class="control-label">故障数量：</label>
                                <input type="text" class="form-control" placeholder="请输入故障数量" id="damage_input" name="damage_input">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel-footer">
        <div style="text-align:right">
            <button class="btn btn-success" id="submit">提交检测</button>
        </div>
    </div>
</div>

<!-- 提示消息模态框 -->
<div class="modal fade" id="info_modal" table-index="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">信息</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-4 col-sm-4"></div>
                    <div class="col-md-4 col-sm-4">
                        <div id="info_success" class=" hide" style="text-align: center;">
                            <img src="media/icons/success-icon.png" alt=""
                                 style="width: 100px; height: 100px;">
                        </div>
                        <div id="info_error" style="text-align: center;">
                            <img src="media/icons/error-icon.png" alt=""
                                 style="width: 100px; height: 100px;">
                        </div>
                    </div>
                    <div class="col-md-4 col-sm-4"></div>
                </div>
                <div class="row" style="margin-top: 10px">
                    <div class="col-md-4 col-sm-4"></div>
                    <div class="col-md-4 col-sm-4" style="text-align: center;">
                        <h4 id="info_content"></h4>
                    </div>
                    <div class="col-md-4 col-sm-4"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal">
                    <span>&nbsp;&nbsp;&nbsp;关闭&nbsp;&nbsp;&nbsp;</span>
                </button>
            </div>
        </div>
    </div>
</div>