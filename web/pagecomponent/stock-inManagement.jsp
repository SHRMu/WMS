<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
var stockin_packet = null; //入库包裹运单号
var stockin_batch = null; //入库包裹批次
var stockin_repository = null;// 入库仓库编号

var stockin_customer = null;// 入库供应商编号
var stockin_goods = null;// 入库货物编号
var stockin_number = null;// 入库数量

var customerCache = new Array();// 客户信息缓存
var goodsCache = new Array();//货物信息缓存

$(function(){
	batchSelectorInit();
	repositorySelectorInit();
	dataValidateInit();
	detilInfoToggle();

	goodsAutocomplete();
	customerAutocomplete();
	fetchStorage();

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
				$('#batch_selector').append("<option value='" + elem.id + "'>" + elem.id +"号批次</option>");
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

// 数据校验
function dataValidateInit(){
	$('#stockin_form').bootstrapValidator({
		message : 'This is not valid',

		fields : {
			packet_input : {
				validators : {
					notEmpty : {
						message : '入库包裹运单号不能为空'
					},
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

// 详细信息显示与隐藏
function detilInfoToggle(){
	$('#info-show').click(function(){
		$('#detailInfo').removeClass('hide');
		$(this).addClass('hide');
		$('#info-hidden').removeClass('hide');
	});

	$('#info-hidden').click(function(){
		$('#detailInfo').removeClass('hide').addClass('hide');
		$(this).addClass('hide');
		$('#info-show').removeClass('hide');
	});
}

// 客户信息自动匹配
function customerAutocomplete(){
	$('#customer_input').autocomplete({
		minLength : 0,
		delay:200,
		source : function(request, response){
			$.ajax({
				type : "GET",
				url : 'customerManage/getCustomerList',
				dataType : 'json',
				contentType : 'application/json',
				data : {
					offset : -1,
					limit : -1,
					searchType : 'searchByName',
					keyWord : request.term
				},
				success : function(data){
					var autoCompleteInfo = new Array();
					$.each(data.rows,function(index, elem){
						customerCache.push(elem);
						autoCompleteInfo.push({label:elem.name,value:elem.id});
					});
					response(autoCompleteInfo);
				}
			});
		},
		focus : function(event, ui){
			$('#customer_input').val(ui.item.label);
			return false;
		},
		select : function(event, ui){
			$('#customer_input').val(ui.item.label);
			stockin_customer = ui.item.value;
			customerInfoSet(stockin_customer);
			return false;
		}
	})
}

// 填充客户详细信息
function customerInfoSet(customerID){
	var detailInfo;
	$.each(customerCache,function(index,elem){
		if(elem.id == customerID){
			detailInfo = elem;

			if(detailInfo.id==null)
				$('#info_customer_ID').text('-');
			else
				$('#info_customer_ID').text(detailInfo.id);
			
			if(detailInfo.name==null)
				$('#info_customer_name').text('-');
			else
				$('#info_customer_name').text(detailInfo.name);
			
			if(detailInfo.tel==null)
				$('#info_customer_tel').text('-');
			else
				$('#info_customer_tel').text(detailInfo.tel);
			
			if(detailInfo.personInCharge==null)
				$('#info_customer_person').text('-');
			else
				$('#info_customer_person').text(detailInfo.personInCharge);
			
			if(detailInfo.email==null)
				$('#info_customer_email').text('-');
			else
				$('#info_customer_email').text(detailInfo.email);
			
			
			if(detailInfo.adress==null)
				$('#info_customer_address').text('-');
			else
				$('#info_customer_address').text(detailInfo.address);
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
			goodsInfoSet(stockin_goods);
			loadStorageInfo();
			return false;
		}
	})
}

// 填充货物详细信息
function goodsInfoSet(goodsID){
	var detailInfo;
	$.each(goodsCache,function(index,elem){
		if(elem.id == goodsID){
			detailInfo = elem;
			if(detailInfo.id==null)
				$('#info_goods_ID').text('-');
			else
				$('#info_goods_ID').text(detailInfo.id);
			
			if(detailInfo.name==null)
				$('#info_goods_name').text('-');
			else
				$('#info_goods_name').text(detailInfo.name);
			
			if(detailInfo.type==null)
				$('#info_goods_type').text('-');
			else
				$('#info_goods_type').text(detailInfo.type);
			
			if(detailInfo.size==null)
				$('#info_goods_size').text('-');
			else
				$('#info_goods_size').text(detailInfo.size);
			
			if(detailInfo.value==null)
				$('#info_goods_value').text('-');
			else
				$('#info_goods_value').text(detailInfo.value);
		}
	})
}

// 获取仓库当前库存量
function fetchStorage(){
	$('#repository_selector').change(function(){
		stockin_repository = $(this).val();
		loadStorageInfo();
	});

}

function loadStorageInfo(){
	if(stockin_repository != null && stockin_goods != null){
		$.ajax({
			type : 'GET',
			url : 'storageManage/getStorageListWithRepository',
			dataType : 'json',
			contentType : 'application/json',
			data : {
				offset : -1,
				limit : -1,
				searchType : 'searchByGoodsID',
				repositoryBelong : stockin_repository,
				keyword : stockin_goods
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

// 执行货物入库操作
function stockInOption(){
	$('#submit').click(function(){
		// data validate
		$('#stockin_form').data('bootstrapValidator').validate();
		if (!$('#stockin_form').data('bootstrapValidator').isValid()) {
			return;
		}

		data = {
			packet : $('#packet_input').val(),
			batch : $('#batch_input').val(),
			repositoryID : stockin_repository,
			customerID : stockin_customer,
			goodsID : stockin_goods,
			number : $('#stockin_input').val(),

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
	$('#info_customer_ID').text('-');
	$('#info_customer_name').text('-');
	$('#info_customer_tel').text('-');
	$('#info_customer_address').text('-');
	$('#info_customer_email').text('-');
	$('#info_customer_person').text('-');
	$('#info_goods_ID').text('-');
	$('#info_goods_name').text('-');
	$('#info_goods_size').text('-');
	$('#info_goods_type').text('-');
	$('#info_goods_value').text('-');
	$('#info_storage').text('-');
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
	<div class="panel-body">
		<div class="row" style="margin-bottom: 25px">
			<div class="col-md-6 col-sm-6">
				<div class="row">
					<div class="col-md-1 col-sm-1"></div>
					<div class="col-md-10 col-sm-11">
						<form action="" class="form-inline">
							<div class="form-group">
								<label for="" class="form-label">包裹运单：</label>
								<input type="text" class="form-control" placeholder="请输入运单号信息" id="packet_input" name="">
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
								<label for="" class="form-label">包裹批次：</label>
<%--								<input type="text" class="form-control" placeholder="请输入包裹批次" id="batch_input" name="">--%>
								<select name="" id="batch_selector" class="form-control">
									<option value="">请选择可用批次</option>
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
								<label for="" class="form-label">&nbsp;&nbsp;&nbsp;&nbsp;发货商：</label>
								<input type="text" class="form-control" id="customer_input" placeholder="请输入发货商名称">
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
								<label for="" class="form-label">入库货物：</label>
								<input type="text" class="form-control" id="goods_input" placeholder="请输入货物名称">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row visible-md visible-lg">
			<div class="col-md-12 col-sm-12">
				<div class='pull-right' style="cursor:pointer" id="info-show">
					<span>显示详细信息</span>
					<span class="glyphicon glyphicon-chevron-down"></span>
				</div>
				<div class='pull-right hide' style="cursor:pointer" id="info-hidden">
					<span>隐藏详细信息</span>
					<span class="glyphicon glyphicon-chevron-up"></span>
				</div>
			</div>
		</div>
		<div class="row hide" id="detailInfo" style="margin-bottom:30px">
			<div class="col-md-6 col-sm-6  visible-md visible-lg">
				<div class="row">
					<div class="col-md-1 col-sm-1"></div>
					<div class="col-md-10 col-sm-10">
						<label for="" class="text-info">客户信息</label>
					</div>
				</div>
				<div class="row">
					<div class="col-md-1 col-sm-1"></div>
					<div class="col-md-11 col-sm-11">
						<div class="col-md-6 col-sm-6">
							<div style="margin-top:5px">
								<div class="col-md-6 col-sm-6">
									<span for="" class="pull-right">客户ID：</span>
								</div>
								<div class="col-md-6 col-sm-6">
									<span id="info_customer_ID">-</span>
								</div>
							</div>
							<div style="margin-top:5px">
								<div class="col-md-6 col-sm-6">
									<span for="" class="pull-right">负责人：</span>
								</div>
								<div class="col-md-6 col-sm-6">
									<span id="info_customer_person">-</span>
								</div>
							</div>
						</div>
						<div class="col-md-6 col-sm-6  visible-md visible-lg">
							<div style="margin-top:5px">
								<div class="col-md-6 col-sm-6">
									<span for="" class="pull-right">名称：</span>
								</div>
								<div class="col-md-6 col-sm-6">
									<span id="info_customer_name">-</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="margin-top: 10px">
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
						<form action="" class="form-inline" id="stockin_form">
							<div class="form-group">
								<label for="" class="control-label">入库数量：</label>
								<input type="text" class="form-control" placeholder="请输入数量" id="stockin_input" name="stockin_input">
								<span>当前库存量：</span>
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