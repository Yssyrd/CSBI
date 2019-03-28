<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nt.csbi.entities.HistoryInfo"%>
<%@page import="java.util.List"%>
<html lang="zh-CN">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
<title>婴儿培养箱系统</title>
<link rel="stylesheet" href="css/dist/css/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/dist/css/flat-ui.css">
<link rel="stylesheet" href="css/dist/img/favicon.ico">
<link rel="stylesheet" href="css/myStyle.css">
<link rel="stylesheet" href="css/front/showInfoSearch.css">
<link rel="stylesheet" href="css/upload.css" type="text/css">
<link rel="stylesheet" href="css/tableEdit.css">
<link rel="stylesheet" href="css/realTime.css">
<link rel="stylesheet" type="text/css" href="css/component.css" />
<script type="text/javascript" src="js/front/echarts.js"></script>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>

<script src="css/dist/js/vendor/jquery.min.js"></script>
<script src="css/dist/js/flat-ui.js"></script>
<script src="js/application.js"></script>
<script src="js/myJs/realTimeFigure.js"></script>
<script src="js/myJs/status.js"></script>

</head>
<body style="background: #F9F9F9;">
		<!-- Sidebar -->

		<!-- <ul class="nav sidebar-nav" style="margin-bottom: 50px">
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"></a>
				<ul role="menu" >
					<li style="cursor: pointer;"><a href="index.jsp"> <strong>主页</strong>
					</a></li>
				</ul></li>
			<li><br></li>
			<li><a style="cursor: pointer;"
				class="btn btn-block btn-lg btn-success detectionIdOption">检测仪器编号设置</a>
			</li>
		</ul> -->
		<!-- <div class="row pageDiv" style="width: 400px;">
			<div class="col-xs-4 text-left" >
				<Strong><a style="cursor: pointer;" class="previousPage">上一页</a></Strong>
			</div>
			<div class="col-xs-4 text-center">
				<Strong><a  class="currentPage">1/1</a></Strong>
			</div>
			<div class="col-xs-4 text-right">
				<Strong><a style="cursor: pointer;" class="nextPage">下一页</a></Strong>
			</div>
			<input class="pageNum" style="display: none">
		</div> -->
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
			<div class="row" style="width: 99%;">
					<div class="col-xs-1">
						<div class="row pick" style="display:block;height:5vh">
							<h3 class="detectionIdOption">选择仪器</h3>
						</div>
						<div class="row onlineNum" style="display:block;height:5vh">
							<h3 class="onlineSum">在线：8台</h3>
						</div>
						<div class="row nums" style="display:block;height:90vh;overflow-y:auto">
						</div>
					</div>
					<div class="col-xs-9">
						<div class="row"
						style="height: 80vh; width: 100%; display: block;margin-top: 10vh;"
						id="main"></div>
					</div>
					<div class="col-xs-2">
						<div class="row" style="display: block;  margin-top: 15vh">
							<div class="col-xs-6">
								<div class="row text-center" style="border-bottom-style: solid;
									border-bottom-width: 2px;border-bottom-color: #2980b9">
									<a>通讯状态</a>
								</div>
								<div class="row text-center" style="display: block;">
									<img class="commImg"  src="css/4.png" width="50%" height="50%">
								</div>
							</div>
							<div class="col-xs-6">
								<div class="row text-center" style="border-bottom-style: solid;
									border-bottom-width: 2px;border-bottom-color: #2980b9">
									<a>数据状态</a>
								</div>
								<div class="row text-center showSignnal" >
									<img src="css/7.png" class="signnalImg" width="50%" height="50%">
								</div>
							</div>
						</div>
						<div class="row"  style="display: block;margin-top: 1vh">
							<div class="row"  style="display: block;">
								<div class="col-xs-4">
									<div class="panel panel-info text-center">
										<div class="panel-heading"><strong>BT(℃)</strong></div>
										<div class="panel-body">
											<a class="BT_A"></a>
										</div>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="panel panel-info text-center">
										<div class="panel-heading"><strong>AT(℃)</strong></div>
										<div class="panel-body">
											<a class="AT_A"></a>
										</div>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="panel panel-info text-center">
										<div class="panel-heading"><strong>DT(℃)</strong></div>
										<div class="panel-body">
											<a class="DT_A"></a>
										</div>
									</div>
								</div>
							</div>
							<div class="row"  style="display: block;">
								<div class="col-xs-4">
									<div class="panel panel-info text-center">
										<div class="panel-heading"><strong>CT(℃)</strong></div>
										<div class="panel-body">
											<a class="CT_A"></a>
										</div>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="panel panel-info text-center">
										<div class="panel-heading">AF(%RH)</div>
										<div class="panel-body">
											<a class="AF_A"></a>
										</div>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="panel panel-info text-center">
										<div class="panel-heading"><strong>ET(℃)</strong> </div>
										<div class="panel-body">
											<a class="ET_A"></a>
										</div>
									</div>
								</div>
							</div>
							<div class="row"  style="display: block;">
								<div class="col-xs-4">
									<div class="panel panel-info text-center">
										<div class="panel-heading"><strong>采集器</strong></div>
										<div class="panel-body">
											<a class="deviceIdShow"></a>
										</div>
									</div>
								</div>
								<div class="col-xs-8">
									<div class="panel panel-info text-center">
										<div class="panel-heading"><strong>时间</strong> </div>
										<div class="panel-body">
											<a class="time_a"></a>
										</div>
									</div>
								</div>
								
							</div>
							<div class="row" style="display: block; ">
								<button type="button" 
									class="btn btn-primary btn-lg btn-block startCount"> 
									平衡计时</button>
							</div>
							<div class="row" style="display: none;  margin-top: 2%">
								<button type="button"
									class="btn btn-primary btn-lg btn-block Fresh">暂停刷新</button>
							</div>
							<div class="row" style="display: block;  margin-top: 2%">
								<button type="button"
									class="btn btn-primary btn-lg btn-block optionSet"
									>参数设置</button>
							</div>
							<div class="row" style="display: block;  margin-top: 2%">
								<button type="button"
									class="btn btn-primary btn-lg btn-block mainUI"
									>返回主页</button>
							</div>
							<h6 style="display: none">
								<strong><a class="idDetailInfo" style="font-size: 25px;"></a></strong>
							</h6>
							<h6 style="display: none">
								<strong><a class="settingInfo" style="font-size: 25px; color: #2980B9 "></a></strong>
							</h6>
						</div>
						<div class="row"  style="display: none;">
							<h6>
								<strong><a class="timeInfo" style="font-size: 25px;"></a></strong>
							</h6>
							<input style="display: none" class="tempTime">
						</div>
						
					</div>
			</div>
		<input type="text" style="display: none" class="deviceIdInput">
		<input type="text" style="display: none" class="maxTimeInput">
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
		
		<div class="modal fade" id="optionSetModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document" style="width: 45%">
				<div class="modal-content" style="display: block;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title"  style="font-weight: bold;">参数设置</h4>
					</div>
					<div class="modal-body">
							<div class="row" style="border-bottom: 2px solid green; padding-bottom: -0.5%;margin-bottom: 1%">
								
								<div class="col-xs-7">
									<div class="btn-group " style="margin-left: 1%;">
										<button data-toggle="dropdown"
											class="btn btn-primary dropdown-toggle" type="button">
											<span id="tempCenterBtn">被检设备参数录入</span><span class="caret"></span>
										</button>
										<ul role="menu" class="dropdown-menu">
											<li><a href="#" class="baseInput">被检设备参数录入</a></li>
											<li style="display: none"><a href="#" class="tableHeadInfo">原始记录基本信息录入</a></li>
										</ul>
									</div>
								</div>
								<div class="col-xs-5">
									<h6 class="demo-panel-title">
										当前仪器编号:<a class="h6Id"></a><input class="titleDataId" style="display: none">
									</h6>
								</div>
							</div>
							<div class="baseInfoInput">
								<div class="row" >
									<div class="col-xs-4">
										<div class="form-group">
											<input type="text" placeholder="被检仪器编号输入"
												class="form-control inspectedId" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group" style="font-size: 30px">
											<input type="text" placeholder="显示温度输入（℃）"
												class="form-control DIST" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<input type="text" placeholder="控制温度输入（℃）"
												class="form-control CONT" />
										</div>
									</div>

								</div>
								<div class="row" >
									<div class="col-xs-4">
										<div class="form-group">
											<input type="text" placeholder="显示风速输入（m/sec）"
												class="form-control FLOW" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<input type="text" placeholder="显示湿度输入（%RH）"
												class="form-control DISRH" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<input type="text" placeholder="箱外报警噪声输入（dB）"
												class="form-control OUTNOI" />
										</div>
									</div>
								</div>
								<div class="row" >
									<div class="col-xs-6">
										<div class="form-group" style="font-size: 30px">
											<input type="text" placeholder="舱内报警噪声输入（dB）"
												class="form-control INNOIS" />
										</div>
									</div>
									<div class="col-xs-6">
										<div class="form-group">
											<input type="text" placeholder="舱内噪声输入（dB）"
												class="form-control NOISE" />
										</div>
									</div>
								</div>
							</div>
							<div class="tableHeadInfoInput" style="display: none">
								<div class="row" >
									<div class="col-xs-12">
										<div class="form-group">
											<input type="text" placeholder="委托单位信息录入"
												class="form-control cpyInfoInput" />
										</div>
									</div>
								</div>
								<div class="row" >
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
								<div class="row" >
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
								<div class="row" >
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
							</div>
							
					</div>
					<div class="modal-footer" style="display: block;">
						<div class="btn-group btn-group-lg"
							style="margin-left: 35%; margin-top: 20px; margin-bottom: 20px; width: 30%">
							<button type="button" class="btn btn-info submitOption"
								style="width: 50%">确定</button>
							<button type="button"
								class="btn btn-warning closeOptionSet" style="width: 50%">关闭</button>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>