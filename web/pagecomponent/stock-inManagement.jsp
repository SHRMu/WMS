<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>

var stockin_packet = null; //入库包裹批次
var stockin_batch = null;
var stockin_repository = null;// 入库仓库编号

var stockin_customer = null;// 入库供应商编号
var stockin_goods = null;// 入库货物编号
var stockin_number = null;// 入库数量

var packetCache = new Array();//包裹信息缓存
var customerCache = new Array();// 客户信息缓存
var goodsCache = new Array();//货物信息缓存

$(function(){
	batchSelectorInit();
	repositorySelectorInit();

	packetAutocomplete();
	goodsAutocomplete();
    dataValidateInit();

	stockInOption();
})

//当前可用的批次初始化
function batchSelectorInit() {
	$.ajax({
		type : 'GET',
		url : 'repositoryBatchManage/getRepositoryBatchList',
		dataType : 'json',
		contentType : 'application/json',
		data : {
			searchType : 'searchByActive',
			keyWord : '',
			offset : -1,
			limit : -1
		},
		success : function(response){
			$.each(response.rows,function(index,elem){
				$('#batch_selector').append("<option value='" + elem.id + "'>第 " + elem.id +" 批次</option>");
			});
		},
		error : function(response){
			$('#batch_selector').append("<option value='-1'>加载失败</option>");
		}
	})
}

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

// 包裹信息自动匹配
function packetAutocomplete(){
	$('#packet_input').autocomplete({
		minLength : 0,
		delay : 200,
		source : function(request, response){
			$.ajax({
				type : 'GET',
				url : 'packetManage/getPacketList',
				dataType : 'json',
				contentType : 'application/json',
				data : {
					offset : -1,
					limit : -1,
					keyWord : request.term,
					repositoryID:$('#repository_selector').val(),
					searchType:'searchByTrace'
				},
				success : function(data){
					var autoCompleteInfo = new Array();
					$.each(data.rows, function(index,elem){
						packetCache.push(elem);
						autoCompleteInfo.push({label:elem.trace,value:elem.id});
					});
					response(autoCompleteInfo);
				}
			});
		},
		focus : function(event, ui){
			$('#packet_input').val(ui.item.label);
			return false;
		},
		select : function(event, ui){
			$('#packet_input').val(ui.item.label);
			stockin_packet = ui.item.value;
			return false;
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
			stockin_goods = ui.item.value;
			loadStorageInfo();
			return false;
		}
	})
}

function loadStorageInfo(){
	stockin_repository = $('#repository_selector').val();
	if(stockin_packet != null && stockin_repository != null && stockin_goods != null){
		$.ajax({
			type : 'GET',
			url : 'packetStorageManage/getStorageListWithPacket',
			dataType : 'json',
			contentType : 'application/json',
			data : {
				offset : -1,
				limit : -1,
				searchType : 'searchByGoodsID',
				packetBelong : stockin_packet,
				repositoryBelong : stockin_repository,
				keyword : stockin_goods
			},
			success : function(response){
				if(response.total > 0){
					data = response.rows[0].storage;
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

// 数据校验
function dataValidateInit(){
    $('#stockin_form').bootstrapValidator({
        message : 'This is not valid',
        fields : {
            packet_input : {
                validators : {
                    notEmpty: {
                        message: '运单号不能为空'
                    }
                }
            },
            stockin_input : {
                validators : {
                    notEmpty : {
                        message : '入库数量不能为空'
                    },
                    greaterThan: {
                        value: 0,
                        message: '入库数量不能小于0'
                    }
                }
            }
        }
    })
}

// 执行货物入库操作
function stockInOption(){
	$('#submit').click(function(){
		// data validate
		$('#stockin_form').data('bootstrapValidator').validate();
		if (!$('#stockin_form').data('bootstrapValidator').isValid()) {
			return;
		}
		data = {
            packetID: stockin_packet,
			batchID : $('#batch_selector').val(),
            repositoryID: stockin_repository,
            goodsID: stockin_goods,
            number: $('#stockin_input').val()
        }
		$.ajax({
			type : 'POST',
			url : 'stockRecordManage/stockIn',
			dataType : 'json',
			content : 'application/json',
			data : data,
			success : function(response){
				var msg;
				var type;
				
				if(response.result == "success"){
					type = 'success';
					msg = '货物入库成功';
					inputReset();
				}else{
					type = 'error';
					msg = '货物入库失败'
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
	$('#packet_input').val('');
	$('#customer_input').val('');
	$('#goods_input').val('');
	$('#stockin_input').val('');
	$('#stockin_form').bootstrapValidator("resetForm",true); 
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
		<li>货物入库</li>
	</ol>
	<div class="panel-body" id="stockin_div">
		<div class="row" style="margin-bottom: 25px">
			<div class="col-md-6 col-sm-6">
				<div class="row">
					<div class="col-md-1 col-sm-1"></div>
					<div class="col-md-10 col-sm-11">
						<form action="" class="form-inline" >
							<div class="form-group">
								<label for="" class="form-label">包裹运单：</label>
								<input type="text" class="form-control" placeholder="请输入运单号信息" id="packet_input" name="packet_input">
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
								<label for="" class="form-label">入库仓库：</label>
								<select name="" id="repository_selector" class="form-control">
									<%--									<option value="">请选择仓库</option>--%>
								</select>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="row">
					<div class="col-md-1 col-sm-1"></div>
					<div class="col-md-10 col-sm-11">
						<form action="" class="form-inline">
							<div class="form-group">
								<label for="" class="form-label">入库货物：</label>
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
								<label for="" class="form-label">货物批次：</label>
								<select name="" id="batch_selector" class="form-control">
									<%--									<option value="">请选择仓库</option>--%>
								</select>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="margin-top: 25px">
			<div class="col-md-6 col-sm-6">
				<div class="row">
					<div class="col-md-1 col-sm-1"></div>
					<div class="col-md-10 col-sm-11">
						<form action="" class="form-inline" id="stockin_form">
							<div class="form-group">
								<label for="" class="control-label">入库数量：</label>
								<input type="text" class="form-control" placeholder="请输入数量" id="stockin_input" name="stockin_input">
								<span>未到货数量：</span>
								<span id="info_storage">-</span>
								<%--								<span>)</span>--%>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="margin-top:80px"></div>
	</div>
	<div class="panel-footer">
		<div style="text-align:right">
			<button class="btn btn-success" id="submit">提交入库</button>
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