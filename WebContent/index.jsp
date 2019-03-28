<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="css/dist/css/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/dist/css/flat-ui.css">
<link rel="stylesheet" href="css/dist/img/favicon.ico">
<link rel="stylesheet" href="css/myTable.css">
<link rel="stylesheet" href="css/tableEdit.css">
<link rel="stylesheet" href="css/upload.css" type="text/css">
<link rel="stylesheet" href="css/windowsStyle.css" type="text/css">
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/myJs/index.js"></script>
<script type="text/javascript" src="js/myJs/status.js"></script>
<script src="css/dist/js/vendor/jquery.min.js"></script>
<script src="css/dist/js/flat-ui.js"></script>
<script src="assets/js/application.js"></script>

<head>
<title>婴儿培养箱系统</title>
</head>
<body style="background: #F9F9F9; overflow: hidden;">
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container" style="width: 90%;">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<img src="css/NRS.png" class="navbar-brand"><a
				class="navbar-brand" href="index.jsp">四川中测辐射科技有限公司</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="navigation">
			<ul class="nav navbar-nav navbar-right">
				<li><a class="editDetecion" style="cursor: pointer;">检测仪器编号编辑</a></li>
				<li style="display: none"><a class="originalTableHeadInfo" style="cursor: pointer;">原始记录基本信息录入</a></li>
				<li style="display: none"><a class="criterionInfo" style="cursor: pointer;">标准信息录入</a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-cog"></span> <b class="caret"></b></a>
	              <ul class="dropdown-menu">
	                 <li><a class="restartComm">重新启动通讯</a></li>
	                 <li><a class="deleteData">历史数据删除</a></li>
	                 <li><a class="syncData">数据信息检索同步</a></li>
	              </ul>
	            </li>
	        </ul>
		</div>
	</div>
	</nav>

	<div class="boxshow">
		<div class="box2">
			<div class="login5"></div>
			<div class="login51">

				<div style="margin-left: 5%" id="searchItems">
					<br>
					<h3 class="text-center" style="margin-left: -5%">搜索选项</h3>
					<div class="btn-group">
						<button data-toggle="dropdown"
							class="btn btn-primary dropdown-toggle" type="button">
							<span id="tempCenterBtn">数据源：采集板数据</span><span class="caret"></span>
						</button>
						<ul role="menu" class="dropdown-menu">
							<li><a href="#" class="historyData">数据源：采集板数据</a></li>
							<li><a href="#" class="realtimeData">数据源：实时数据</a></li>
						</ul>
					</div>
					<div
						style="border-top: 2px solid #3399CC; margin-top: 0.5%; width: 95%">
						<br>
						<form class="bs-example bs-example-form" role="form"
							id="historyInfoSearch" action="historyInfoSearch" method="post"
							style="margin: 1%">
							<div class="row" style="width: 99.9%">
								<input type="text" class="form-control" id="beginDate"
									name="beginDate"
									placeholder="请输入搜索时间，时间格式为：yyyy-MM-dd    若未输入则默认当日......">
							</div>
							<br>
							<div class="row" style="width: 99.9%">
								<input type="text" class="form-control" id="detectionId"
									name="detectionId" placeholder="请输入检测设备ID，可以只输入关键字段进行查询......">
							</div>
							<br>
							<div class="row regionInfoDiv"
								style="width: 99.9%; display: none">
								<input type="text" class="form-control" id="regionInfo"
									name="regionInfo" placeholder="请输入地区信息，可以只输入关键字段进行查询......">
							</div>
							<input style="display: none" id="dataSource" name="dataSource"
								value="${(sessionScope.dataSource==null||sessionScope.dataSource=='')?'0':sessionScope.dataSource}">
							<input style="display: none" id="intervalTime"
								name="intervalTime">
						</form>
					</div>
					<div class="btn-group btn-group-lg"
						style="margin-left: 30%; margin-top: 50px; margin-bottom: 20px">
						<button type="button" class="btn btn-info beginSearch">开始搜索</button>
						<button type="button" class="btn btn-default clearInput">清空文本框</button>
						<button type="button" class="btn btn-warning closePopWindow">关闭搜索框</button>
					</div>
				</div>
				<div id="selectIdDiv" style="padding: 100px 100px 10px;">
					<div style="max-height: 400px; overflow: auto;">
						<table id="selectDetectionId" class="table table-striped"
							style="font-size: large; text-align: center;">
							<tr>
								<th style="text-align: center;" width="40%">日期</th>
								<th style="text-align: center;" width="30%">检查仪器ID</th>
								<th style="text-align: center;" width="30%">操作</th>
							</tr>
						</table>
					</div>
					<div class="btn-group btn-group-lg"
						style="margin-left: 35%; margin-top: 50px; margin-bottom: 20px">
						<button type="button" class="btn btn-info returnLast">返回上一步</button>
						<button type="button" class="btn btn-warning closePopWindow">关闭搜索框</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="boxshow_3">
		<div class="box2_3">
			<div class="login5_3"></div>
			<div class="login51_3">
				<div style="margin-left: 5%" id="searchItems">
					<br>
					<div class="row originalSearchOption">
						<div class="row">
							<div class="col-md-12">
								<h4 class="text-center" style="margin-left: -10%">输入以下任一条件进行查询</h4>
								<br>
								<form class="form-inline" role="form">
									<div class="form-group">
										<label class="sr-only" for="originalBeginDate"></label> <input
											type="text" class="form-control" id="originalBeginDate"
											placeholder="输入开始时间">
									</div>
									<div class="form-group">
										<label class="sr-only" for="originalEndDate"></label> <input
											type="text" class="form-control" id="originalEndDate"
											placeholder="输入结束时间">
									</div>
									<div class="form-group">
										<label class="sr-only" for="originalName"></label> <input
											type="text" class="form-control" id="originalName"
											placeholder="输入表名">
									</div>
									<button type="button" class="btn btn-info searchOriginalList">查询</button>
									<button type="button" class="btn btn-danger newOriginal">创建新的原始记录表</button>
								</form>
							</div>
						</div>
						<div class="row originalTableListDiv"
							style="margin-top: 1%; margin-left: 3%; display: none; max-height: 400px; overflow: auto; width: 87%">
							<table id="originalDataList" class="table table-striped"
								style="font-size: large; text-align: center; width: 100%;">
								<tr>
									<th style="text-align: center;">记录表名称</th>
									<th style="text-align: center;" width="33%">创建时间</th>
									<th style="text-align: center;" width="33%">操作</th>
								</tr>
							</table>
						</div>
						<div class="btn-group btn-group-lg"
							style="margin-left: 40%; margin-top: 50px; margin-bottom: 20px">
							<button type="button"
								class="btn btn-warning closeSearchoriginalData">关闭搜索框</button>
						</div>
						<br>
					</div>
					<div class="row newOriginalOption" style="display: none">
						<h4>原始记录表创建</h4>
						<br>
						<div class="form-group form-group-lg" style="width: 90%">
							<label class="col-sm-2 control-label" for="formGroupInputLarge">请输入表名</label>
							<div class="col-sm-10">
								<input class="form-control" type="text" id="formGroupInputLarge"
									placeholder="若未输入将以当前时间作为表名">
							</div>
						</div>

						<div class="btn-group btn-group-lg"
							style="margin-left: 40%; margin-top: 50px; margin-bottom: 20px">
							<button type="button" class="btn btn-info originalDataCreate">创建</button>
							<button type="button"
								class="btn btn-warning returnOriginalSearchOption">返回</button>
						</div>
					</div>
					<br>
				</div>

			</div>
		</div>
	</div>

	<div class="boxshow_4">
		<div class="box2_4">
			<div class="login5_4"></div>
			<div class="login51_4">
				<div style="margin-left: 5%" id="searchItems">
					<h4 class="text-center"
						style="margin-left: -10%; padding-top: 20px">请输入原始记录表基本信息</h4>
					<div class="btn-group" style="margin-left: 5%;">
						<button data-toggle="dropdown"
							class="btn btn-primary dropdown-toggle" type="button">
							<span id="originalTableBtn">原始记录基本信息录入</span><span class="caret"></span>
						</button>
						<ul role="menu" class="dropdown-menu">
							<li><a href="#" class="baseInput">原始记录基本信息录入</a></li>
							<li><a href="#" class="inputSearch">原始记录基本信息查询</a></li>
						</ul>
					</div>
					<div
						style="border-top: 2px solid green; margin-top: 0.5%; margin-right: 11%; margin-left: 4%"></div>
					<div class="tableHeadInfoInput">
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-8">
								<div class="form-group">
									<input type="text" placeholder="委托单位信息录入"
										class="form-control cpyInfoInput" />
								</div>
							</div>
							<div class="col-xs-4">
								<div class="form-group">
									<input type="text" placeholder="检测仪器编号"
										class="form-control tableInfo_DetectoinId" />
								</div>
							</div>
						</div>
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-4">
								<div class="form-group">
									<input type="text" placeholder="器具名称信息录入"
										class="form-control appliancesInfoInput" />
								</div>
							</div>
							<div class="col-xs-4">
								<div class="form-group" style="font-size: 30px">
									<input type="text" placeholder="制造厂信息录入"
										class="form-control manufacturerInfoInput" />
								</div>
							</div>
							<div class="col-xs-4">
								<div class="form-group">
									<input type="text" placeholder="型号规格信息录入"
										class="form-control modelInfoInput" />
								</div>
							</div>

						</div>
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-4">
								<div class="form-group">
									<input type="text" placeholder="出产编号信息录入"
										class="form-control productionInfoInput" />
								</div>
							</div>
							<div class="col-xs-4">
								<div class="form-group">
									<input type="text" placeholder="温度信息录入（℃）"
										class="form-control tempInfoInput" />
								</div>
							</div>
							<div class="col-xs-4">
								<div class="form-group" style="font-size: 30px">
									<input type="text" placeholder="湿度信息录入（%RH）"
										class="form-control humidityInfoInput" />
								</div>
							</div>
						</div>
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-6">
								<div class="form-group">
									<input type="text" placeholder="地址信息录入"
										class="form-control regionInfo" />
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<input type="text" placeholder="校准日期信息录入"
										class="form-control calibrationDate" />
								</div>
							</div>
						</div>
						<div class="btn-group btn-group-lg"
							style="margin-left: 38%; margin-top: 20px; margin-bottom: 20px">
							<button type="button"
								class="btn btn-info submitOriginalTitleInfo">确定</button>
							<button type="button" class="btn btn-warning closeBox_4">关闭</button>
						</div>
						<br>
					</div>
					<div class="row originalTableTitleListDiv" style="display: none">
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; max-height: 300px; overflow: auto; width: 87%">
							<table id="originalTitleInfoList" class="table table-striped"
								style="font-size: large; text-align: center; width: 100%;">
								<tr>
									<th style="text-align: center;">创建时间</th>
									<th style="text-align: center;" width="30%">委托单位</th>
									<th style="text-align: center;" width="20%">检测仪器编号</th>
									<th style="text-align: center;" width="25%">操作</th>
								</tr>
							</table>

						</div>

						<div class="row previewTitleDiv"
							style="display: none; margin-left: 3%; width: 87%">
							<div
								style="border-top: 1px solid #3499db; margin-top: 0.5%; margin-left: 1%"></div>
							<div class="row">
								<div style="display: block; float: right; margin-right: 3.5%;">
									<span class="fui-cross" style="cursor: pointer;"></span>
								</div>
								<div style="display: none; float: right; margin-right: 2%"
									class="updateCheck">
									<span class="fui-check originalUpdate" style="cursor: pointer;"></span>
								</div>
							</div>
							<table class='titleTable'>
								<tr>
									<td class='nameTd' width='6%'><strong>委托单位</strong></td>
									<td class='underLine' colspan='2' width='40%'><input
										type="text"></td>
									<td class='nameTd' width='7%'><strong>检测仪器编号</strong></td>
									<td class='underLine' colspan='2' width='40%'><input
										type="text"></td>
								</tr>
								<tr>
									<td class='nameTd' width='6%'><strong>器具名称</strong></td>
									<td class='underLine' colspan='2' width='40%'><input
										type="text"></td>
									<td class='nameTd' width='6%'><strong>制造厂</strong></td>
									<td class='underLine' colspan='2' width='40%'><input
										type="text"></td>
								</tr>
								<tr>
									<td class='nameTd' width='12%'><strong>型号规格</strong></td>
									<td class='underLine' width='23%'><input type="text"></td>
									<td class='nameTd' width='14%'><strong>出厂编号</strong></td>
									<td class='underLine' width='23%'><input type="text"></td>
									<td class='nameTd' width='14%'><strong>校准日期</strong></td>
									<td class='underLine'><input type="text"></td>
								</tr>
								<tr>
									<td class='nameTd'><strong>&nbsp;地&nbsp;&nbsp;点&nbsp;</strong></td>
									<td class='underLine'><input type="text"></td>
									<td class='nameTd'><strong>&nbsp;温&nbsp;&nbsp;度&nbsp;</strong></td>
									<td class='underLine'><input type="text"
										style="width: 80%;">℃</td>
									<td class='nameTd'><strong>&nbsp;湿&nbsp;&nbsp;度&nbsp;</strong></td>
									<td class='underLine'><input type="text"
										style="width: 70%;">%RH</td>
								</tr>
							</table>
							<input class="idTmp" style="display: none">
						</div>
						<div class="col-xs-4" style="padding-top: 20px"></div>
						<div class="col-xs-3" style="padding-top: 20px">
							<strong><a style="cursor: pointer;"
								class="btn btn-block btn-lg btn-warning closeBox_4">关闭弹出框</a></strong> <br>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div class="boxshow_5">
		<div class="box2_5">
			<div class="login5_5"></div>
			<div class="login51_5">
				<div style="margin-left: 5%" id="searchItems">
					<div class="row originalSearchOption">
						<div class="row">
							<div class="col-md-12" style="margin-left: 5%">
								<h4 class="text-center"
									style="margin-left: -10%; padding-top: 20px">检测仪器编号编辑</h4>
								<form class="navbar-form navbar-left" action="#" role="search">
									<div class="form-group">
										<div class="input-group">
											<input class="form-control" id="navbarInput-01"
												style="width: 300px" type="search" placeholder="添加检测仪器编号">
											<span class="input-group-btn">
												<button type="button" class="btn addDetection">
													<span class="fui-plus"></span>
												</button>
											</span>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div
							style="border-top: 2px solid green; margin-top: 0.5%; margin-right: 11%; margin-left: 4%"></div>
						<div class="row originalTableListDiv"
							style="margin-top: 1%; margin-left: 3%; max-height: 400px; overflow: auto; width: 87%">
							<table id="detectionDataList" class="table table-striped"
								style="font-size: large; text-align: center; width: 100%;">
								<tr>
									<th style="text-align: center;" width="50%">仪器编号</th>
									<th style="text-align: center;">操作</th>
								</tr>
							</table>
						</div>
						<div class="col-xs-4"></div>
						<div class="col-xs-3" style="padding-top: 20px">
							<strong><a style="cursor: pointer;"
								class="btn btn-block btn-lg btn-warning closeDetectionOptions">关闭弹出框</a></strong>
							<br>
						</div>
					</div>
					<br>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade" id="detetion_modal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document" style="width: 45%">
				<div class="modal-content" style="display: block;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel_delete"
							style="font-weight: bold;">请选择需要显示的仪器编号</h4>
					</div>
					<div class="modal-body">
						<div class="row" style="width: 96%;margin-left: 2%;">
							<div class="col-sm-4">
								<input type="text" name="startId" class="form-control" id="startId"
								placeholder="请输入起始编号">	
							</div>
							<div class="col-sm-4">
								<input type="text" name="endId" class="form-control" id="endId"
								placeholder="请输入结束编号">	
							</div>
							<div class="col-sm-3">
								<a style="cursor: pointer; " class="btn btn-block btn-primary searchRealId"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</a>
							</div>
						</div>
						<div class="row" style="width: 96%;margin-left: 2%;">
								
						</div>
						<div class="row" style="margin-top: 2vh;width: 96%;margin-left: 2%;max-height: 60vh;overflow: auto;">
							<table id="detectionSelectList" class="table table-striped"
								style="font-size: large; text-align: center; width: 100%;">
								
								<thead>
									<tr>
										<th style="text-align: center;" width="50%">仪器编号</th>
										<th style="text-align: center;">
												<button type="button"
													class="btn btn-inverse boxBtn checkAll" >全选</button>
										</th>
									</tr>
								</thead>
								<tbody>
								
								
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer" style="display: block;">
						<div class="btn-group btn-group-lg"
							style="margin-left: 35%; margin-top: 20px; margin-bottom: 20px; width: 30%">
							<button type="button" class="btn btn-info enterRealTime"
								style="width: 50%">确定</button>
							<button type="button"
								class="btn btn-warning closeDetectionSelect" style="width: 50%">关闭</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	
	<div class="boxshow_2">
		<div class="box2_2">
			<div class="login5_2"></div>
			<div class="login51_2">
				<div style="margin-left: 5%" id="searchItems">
					<br>
					<h4 class="text-center" style="margin-left: -10%">请选择需要显示的仪器编号</h4>
					<div
						style="display: block; float: right; width: 30%; margin-bottom: 2%">
						<button type="button" class="btn btn-inverse cancelCheckAll"
							style="width: 60%; margin-right: 40%">取消全选</button>
					</div>
					<div
						style="display: block; float: right; width: 20%; margin-bottom: 2%">
						<button type="button" class="btn btn-primary checkAll"
							style="width: 95%;">全选</button>
					</div>
					<div class="row originalSearchOption">
						<div class="row originalTableListDiv"
							style="margin-top: 1%; margin-left: 3%; max-height: 400px; overflow: auto; width: 87%">
							<table id="detectionSelectList" class="table table-striped"
								style="font-size: large; text-align: center; width: 100%;">
								<tr>
									<th style="text-align: center;" width="50%">仪器编号</th>
									<th style="text-align: center;">操作</th>
								</tr>
							</table>
						</div>
						<div class="btn-group btn-group-lg"
							style="margin-left: 35%; margin-top: 20px; margin-bottom: 20px; width: 30%">
							<button type="button" class="btn btn-info enterRealTime"
								style="width: 50%">确定</button>
							<button type="button"
								class="btn btn-warning closeDetectionSelect" style="width: 50%">关闭</button>
						</div>
						<br>
					</div>
					<br>
				</div>
			</div>
		</div>
	</div>

	<div class="boxshow_6">
		<div class="box2_6">
			<div class="login5_6"></div>
			<div class="login51_6">
				<div style="margin-left: 5%">
					<h4 class="text-center"
						style="margin-left: -10%; padding-top: 20px">请输入标准信息</h4>
					<div class="btn-group" style="margin-left: 5%;">
						<button data-toggle="dropdown"
							class="btn btn-primary dropdown-toggle" type="button">
							<span id="originalTableBtn_6">标准信息录入</span><span class="caret"></span>
						</button>
						<ul role="menu" class="dropdown-menu">
							<li><a href="#" class="baseInput_6">标准信息录入</a></li>
							<li><a href="#" class="inputSearch_6">标准信息查询</a></li>
						</ul>
					</div>
					<div
						style="border-top: 2px solid green; margin-top: 0.5%; margin-right: 11%; margin-left: 4%"></div>
					<div class="tableHeadInfoInput_6">
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-4">
								<div class="form-group">
									<input type="text" placeholder="标准名称" value="婴儿培养箱测量仪"
										class="form-control ctrName" />
								</div>
							</div>
							<div class="col-xs-8">
								<div class="form-group">
									<input type="text" placeholder="标准制造厂" value="四川中测辐射科技有限公司"
										class="form-control ctrFactory" />
								</div>
							</div>

						</div>
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-4">
								<div class="form-group">
									<input type="text" placeholder="标准型号" value="NT7300"
										class="form-control ctrVersion" />
								</div>
							</div>
							<div class="col-xs-8">
								<div class="form-group" style="font-size: 30px">
									<input type="text" placeholder="标准编号"
										class="form-control ctrId" />
								</div>
							</div>

						</div>
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-12">
								<div class="form-group">
									<input type="text" placeholder="等级（不确定度）"
										class="form-control ctrLevel" />
								</div>
							</div>
						</div>
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-12">
								<div class="form-group">
									<input type="text" placeholder="检定/校准证书号"
										class="form-control certificateId" />
								</div>
							</div>
						</div>
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; width: 87%">
							<div class="col-xs-12">
								<div class="form-group">
									<input type="text" placeholder="有效期至"
										class="form-control validTimeInfo" />
								</div>
							</div>
						</div>
						<div class="btn-group btn-group-lg"
							style="margin-left: 38%; margin-top: 20px; margin-bottom: 20px">
							<button type="button"
								class="btn btn-info submitOriginalTitleInfo_6">确定</button>
							<button type="button" class="btn btn-warning closeBox_6">关闭</button>
						</div>
						<br>
					</div>
					<div class="row originalTableTitleListDiv_6" style="display: none">
						<div class="row"
							style="margin-top: 1%; margin-left: 3%; max-height: 300px; overflow: auto; width: 87%">
							<table id="originalTitleInfoList_6" class="table table-striped"
								style="font-size: large; text-align: center; width: 100%;">
								<tr>
									<th style="text-align: center;">创建时间</th>
									<th style="text-align: center;" width="30%">标准名称</th>
									<th style="text-align: center;" width="20%">标准编号</th>
									<th style="text-align: center;" width="25%">操作</th>
								</tr>
							</table>

						</div>

						<div class="row previewTitleDiv_6"
							style="display: none; margin-left: 3%; width: 87%">
							<div
								style="border-top: 1px solid #3499db; margin-top: 0.5%; margin-left: 1%"></div>
							<div class="row">
								<div style="display: block; float: right; margin-right: 3.5%;">
									<span class="fui-cross closePriviewCtr"
										style="cursor: pointer;"></span>
								</div>
								<div style="display: none; float: right; margin-right: 2%"
									class="updateCheck_6">
									<span class="fui-check ctrUpdate" style="cursor: pointer;"></span>
								</div>
							</div>
							<table class='titleTable criterionTable' border="1"
								style="border-color: 1px solid black;">
								<tr style='margin-top: 10px'>
									<td height="40px"><strong>标准名称</strong></td>
									<td colspan='3'><input type="text"></td>
									<td width='13%'><strong>标准制造厂</strong></td>
									<td><input type="text"></td>
								</tr>
								<tr>
									<td width='17%' height="40px"><strong>标准型号</strong></td>
									<td width='21%' colspan='3'><input type="text"></td>
									<td width='13%'><strong>标准编号</strong></td>
									<td><input type="text"></td>
								</tr>
								<tr>
									<td height="40px"><strong>等级(不确定度)</strong></td>
									<td colspan='5'><input type="text"></td>
								</tr>
								<tr>
									<td class='nameTd' height="40px"><strong>检定/校准证书号</strong></td>
									<td class='underLine' colspan='5'><input type="text"></td>
								</tr>
								<tr>
									<td class='nameTd' height="40px"><strong>有效期至</strong></td>
									<td class='underLine' colspan='5'><input type="text"></td>
								</tr>
							</table>
							<input class="idTmp_6" style="display: none">
						</div>
						<div class="col-xs-4" style="padding-top: 20px"></div>
						<div class="col-xs-3" style="padding-top: 20px">
							<strong><a style="cursor: pointer;"
								class="btn btn-block btn-lg btn-warning closeBox_6">关闭弹出框</a></strong> <br>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	
	<div class="modal fade" id="delete_modal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document" style="width: 60%">
				<div class="modal-content" style="display: block;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel_delete"
							style="font-weight: bold;">删除</h4>
					</div>
					<div class="modal-body">
						<div class="row" style="width: 96%;margin-left: 2%;">
							<div class="col-sm-4">
								<input type="text" name="phone" class="form-control" id="deleteDate"
								placeholder="请输入日期">	
							</div>
							<div class="col-sm-4">
								<input type="text" name="phone" class="form-control" id="deleteDid"
								placeholder="请输入编号">	
							</div>
							<div class="col-sm-2">
								<a style="cursor: pointer; " class="btn btn-block btn-lg btn-primary searchAllDateAndId"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>查询</a>
							</div>
						</div>
						<div class="row" style="margin-top: 2vh;width: 96%;margin-left: 2%;max-height: 60vh;overflow: auto;">
							<table class="table table-striped deleteDataResult" >
								<thead class="info" >
									<tr>
										<th>操作</th>
										<th>序号</th>
										<th>日期</th>
										<th>编号</th>
										<th>数据源</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer" style="display: block;">
						<button type="button" class="btn btn-danger deleteConfirm" >
							<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>确认删除
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
						</button>
					</div>
				</div>
			</div>
		</div>
	<div class="modal fade" id="sync_modal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document" style="width: 60%">
				<div class="modal-content" style="display: block;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel_delete"
							style="font-weight: bold;">数据同步</h4>
					</div>
					<div class="modal-body">
						<h4 class="syncH4" style="font-weight: bold;">数据信息检索正在同步中，请稍后。。。。。</h4>
					</div>
					<div class="modal-footer" style="display: block;">
					</div>
				</div>
			</div>
		</div>
	<div class="container" style="width: 90%;">
		<br>
		<br>
		<div>
			<div class="col-xs-3"></div>
			<div class="col-xs-3"></div>
			<div class="col-xs-3"></div>
			<div class="col-xs-3">
				<div class="text-right">
					<div style="float: right;">
						<img class="commImg"  src="css/4.png" width="60px" height="60px">
					</div>
				</div>
				
			</div>
		</div>
	
		<br>
		<h2 class="text-center">婴儿培养箱检测仪管理系统</h2>

		<div class="row demo-tiles" style="height: 80vh; margin-top: 10%">
			<div class="col-xs-3">
				<div class="tile Search" style="cursor: pointer;">
					<img src="css/dist/img/icons/svg/book.svg" alt="Chat"
						class="tile-image">
					<h3 class="tile-title">
						<strong>历史数据信息</strong>
					</h3>
					<br> <br> <br> <a
						class="btn btn-primary btn-large btn-block"
						style="font-size: large; cursor: pointer;"><strong>
							进&nbsp;&nbsp;入</strong></a>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="tile OriginalData" style="cursor: pointer;">
					<img src="css/dist/img/icons/svg/clipboard.svg" alt="Compas"
						class="tile-image big-illustration">
					<h3 class="tile-title">原始记录表</h3>
					<br> <br> <br> <a
						class="btn btn-primary btn-large btn-block"
						style="font-size: large; cursor: pointer;"><strong>
							进&nbsp;&nbsp;入</strong></a>

				</div>
			</div>

			<div class="col-xs-3" style="height: 50%">
				<div class="tile updateData" style="cursor: pointer;">
					<img src="css/dist/img/icons/svg/pencils.svg" alt="Infinity-Loop"
						class="tile-image">
					<h3 class="tile-title">上传数据</h3>
					<br> <br> <br> <a
						class="btn btn-primary btn-large btn-block"
						style="font-size: large; cursor: pointer;"><strong>
							进&nbsp;&nbsp;入</strong></a>
				</div>
			</div>

			<div class="col-xs-3">
				<div class="tile realTimeFigure" style="cursor: pointer;">
					<img src="css/dist/img/icons/svg/map.svg" alt="Pensils"
						class="tile-image">
					<h3 class="tile-title">实时数据</h3>
					<br> <br> <br> <a
						class="btn btn-primary btn-large btn-block "
						style="font-size: large; cursor: pointer;"><strong>
							进&nbsp;&nbsp;入</strong></a>
				</div>
			</div>

		</div>
		<!-- /tiles -->

	</div>
</body>
</html>




