<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nt.csbi.entities.RealTimeInfo"%>
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

<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/dist/css/flat-ui.css">
<link rel="stylesheet" href="css/dist/img/favicon.ico">
<link rel="stylesheet" href="css/myStyle.css">
<link rel="stylesheet" href="css/front/showInfoSearch.css">
<link rel="stylesheet" href="css/upload.css" type="text/css">
<link rel="stylesheet" href="css/tableEdit.css">
<link rel="stylesheet" href="css/windowsStyle.css" type="text/css">

<script type="text/javascript" src="js/front/echarts.js"></script>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/myJs/historyFigure.js"></script>

<script src="css/dist/js/vendor/jquery.min.js"></script>
<script src="css/dist/js/flat-ui.js"></script>
<script src="js/application.js"></script>


</head>
<body style="background: #F9F9F9;">

	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header" style="margin-left: -10%">
			<img src="css/NRS.png" class="navbar-brand"><a
				class="navbar-brand" href="#">四川中测辐射科技有限公司</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="navigation"
			style="font-size: large;">
			
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${!empty sessionScope.intervalData}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button">选取时间段<span
							class="caret"></span></a>
						<ul class="dropdown-menu">
							<li role="separator" class="divider"></li>
							<c:forEach items="${sessionScope.intervalData}" var="ntervalData" varStatus="status">
								<li><a href="#" class="intervalDataDropDown">${ntervalData}</a></li>
							</c:forEach>
							<li><a href="#" class="intervalDataDropDown" style="font-size: larger;">全显</a></li>
						</ul>
					</li>
				</c:if>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button">检测仪器编号 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li role="separator" class="divider"></li>
						<li><a>当前检测仪器编号为</a><a class="textDetId">${sessionScope.detectionId}</a></li>
						<li role="separator" class="divider"></li>
						<c:if test="${!empty sessionScope.detectionIds && fn:length(sessionScope.detectionIds) > 1}">
							<li><a>单击以下编号切换</a></li>
							<li role="separator" class="divider"></li>
							<c:forEach items="${sessionScope.detectionIds}" var="info" varStatus="status">
								<li value="${info[0]}"><a href="#" class="detectionIdDropDown">${info[0]}</a></li>
							</c:forEach>
						</c:if>
					</ul></li>
				<c:if test="${!empty sessionScope.newOriginalData}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button">编辑当前记录表<span
							class="caret"></span></a>
						<ul class="dropdown-menu">
							<li role="separator" class="divider"></li>
							<li ><a>当前原始记录表为</a></li>
							<li><a>${sessionScope.newOriginalData.getOriginalDataName()}</a></li>
							
							<li role="separator" class="divider"></li>
							<li class="originalPart" value="0"><a href="#"
								class="originalDataDropDown">1、基本信息填写</a></li>
							<li class="originalPart" value="1"><a href="#"
								class="originalDataDropDown">2、外观、报警功能及电器安全检查</a></li>
							<li class="originalPart" value="2"><a href="#"
								class="originalDataDropDown">3、温度校准记录</a></li>
							<li class="originalPart" value="3"><a href="#"
								class="originalDataDropDown">4、床垫倾斜时温度均匀度</a></li>
							<li class="originalPart" value="4"><a href="#"
								class="originalDataDropDown">5、湿度相对偏差</a></li>
							<li class="originalPart" value="5"><a href="#"
								class="originalDataDropDown">6、婴儿舱内氧分析器示值误差</a></li>
							<li class="originalPart" value="6"><a href="#"
								class="originalDataDropDown">7、婴儿舱的噪声</a></li>
							<li class="originalPart" value="7"><a href="#"
								class="originalDataDropDown">8、报警器报警噪声</a></li>
							<li class="originalPart" value="8"><a href="#"
								class="originalDataDropDown">9、备注</a></li>
							<li class="originalPart" value="9"><a href="#"
								class="originalDataDropDown">10、等级(不确定度)</a></li>
							<li><a href="originalDataPreview" style="font-size: large ;color: #FFFFFF">
										预览当前原始记录表</a></li>
						</ul></li>
				</c:if>
				<li><a href="#" class="SearchoriginalData">原始记录表</a></li>
				<li><a href="#" class="Search">检测仪器数据搜索</a></li>
				<li style="display: none"><a href="uploadData">数据上传</a></li>
				<li><a href="#" class="skipIndex">主页</a></li>
			</ul>
		</div>
	</div>
	</nav>
	<div class="boxshow">
		<div class="box2">
			<div class="login5"></div>
			<div class="login51">
				<div style="margin-left: 5%" id="searchItems">
				<br><h3 class="text-center" style="margin-left: -5%">搜索选项</h3>
				    <div class="btn-group">
				            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" 
				            	type="button"><span id = "sourceDataShowInSearch" >数据源：采集板数据</span><span class="caret"></span></button>
				            <ul role="menu" class="dropdown-menu">
				              <li><a href="#" class="historyData">数据源：采集板数据</a></li>
				              <li><a href="#" class="realtimeData">数据源：实时数据</a></li>
				            </ul>
				    </div>
					<div style="border-top: 2px solid #3399CC;margin-top:0.5%; width: 95%" >
					<br>
					<form class="bs-example bs-example-form" role="form" id="historyInfoSearch" action="historyInfoSearch" 
						method="post" style="margin: 1%">
						<div class="row" style="width: 99.9%">
							<input type="text" class="form-control" id="beginDate" name="beginDate" 
								placeholder="请输入搜索时间，时间格式为：yyyy-MM-dd    若未输入则默认当日......">
						</div>
						<br>
						<div class="row" style="width: 99.9%">
							<input type="text" class="form-control" id="detectionId" name="detectionId"
								placeholder="请输入检测设备ID，可以只输入关键字段进行查询......">
						</div>
						<br>
						<div class="row regionInfoDiv"  style="width: 99.9%;display: none">
							<input type="text" class="form-control" id="regionInfo" name="regionInfo"
								placeholder="请输入地区信息，可以只输入关键字段进行查询......">
						</div>
						<input style="display: none" id="dataSourceDiv" name="dataSourceDiv" value="0" >
						<input style="display: none" id="dataSource" name="dataSource" value="${(sessionScope.dataSource==null||sessionScope.dataSource=='')?'0':sessionScope.dataSource}" >
						<input style="display: none" id = "sourceTmp" value="${sessionScope.dataSource}">
						<input style="display: none" id = "timeTmp" value="${sessionScope.beginDate}">
						<input style="display: none" id = "intervalTime" name="intervalTime" >
					</form>
					</div>
					<div class="btn-group btn-group-lg" style="margin-left: 30%;margin-top: 50px;margin-bottom: 20px">
						<button type="button" class="btn btn-info beginSearch">开始搜索</button>
						<button type="button" class="btn btn-default clearInput">清空文本框</button>
						<button type="button" class="btn btn-warning closePopWindow">关闭搜索框</button>
					</div>
				</div>
				<div id="selectIdDiv" style="padding: 100px 100px 10px;">
					<div style="max-height: 400px;overflow: auto;">
					<table id="selectDetectionId" class="table table-striped" style="font-size: large; 
						text-align:  center; ">
						<tr>
							<th style="text-align: center; " width="40%">日期</th>
							<th style="text-align: center; " width="30%">检查仪器ID</th>
							<th style="text-align: center; " width="30%">操作</th>
						</tr>
					</table>
					</div>
					<div class="btn-group btn-group-lg" style="margin-left: 35%;margin-top: 50px;margin-bottom: 20px">
						<button type="button" class="btn btn-info returnLast">返回上一步</button>
						<button type="button" class="btn btn-warning closePopWindow">关闭搜索框</button>
					</div>
				</div>


			</div>
		</div>
	</div>

	<div class="boxshow_4">
		<div class="box2_4">
			<div class="login5_4"></div>
			<div class="login51_4">
				<div style="margin-left: 5%" id="searchItems">
				 <div class="row" style="margin-left: 5%;padding-top: 50px">
				 <div>
				 	<div class="demo-type-example">
	      				<p class="pInfo"></p>
      				</div>
				 </div>
				 <div class="row optionsDiv" style="margin-left: 3%;display: block;">
		          	<div style="display: block;float: left;">
		          		  <label class="radio" style="font-size: large;">
			                <input type="radio" data-toggle="radio" name="optionsRadios" id="T32C" value="0" data-radiocheck-toggle="radio" >
			                32℃平铺
			              </label>
			        </div>
			        <div style="display: block;float: left;margin-left: 15px">
		          		  <label class="radio" style="font-size: large;">
			                <input type="radio" data-toggle="radio" name="optionsRadios" id="T32L" value="1" data-radiocheck-toggle="radio" >
			                32℃左倾
			              </label>
			        </div>
			        <div style="display: block;float: left;margin-left: 15px">
		          		  <label class="radio" style="font-size: large;">
			                <input type="radio" data-toggle="radio" name="optionsRadios" id="T32R" value="2" data-radiocheck-toggle="radio" >
			                32℃右倾
			              </label>
			        </div>
		            <div style="display: block;float: left;margin-left: 15px">
			              <label class="radio" style="font-size: large;">
			                <input type="radio" data-toggle="radio" name="optionsRadios" id="T36" value="3" data-radiocheck-toggle="radio" >
			                36℃
			              </label>
					</div>
					<div style="display: block;float: left;margin-left: 15px" class="tableHeadDiv">
			              <label class="radio" style="font-size: large;">
			                <input type="radio" data-toggle="radio" name="optionsRadios" id="tableHeadRadio" value="5" data-radiocheck-toggle="radio" >
			                记录表基本信息
			              </label>
					</div>
					<div style="display: block;float: left;margin-left: 15px" class="AF">
		              <label class="checkbox" for="AF" style="font-size: large;">
		                <input type="checkbox" name="checkboxOption" data-toggle="checkbox" value="4" id="AF" >
		                湿度
		              </label>
		             </div>
		          </div>
				  <div class="row" style="margin-left: 8%;display: none">
		              <div style="display: block;float: left;margin-left: 5px" class="NOISE">
		              <label class="checkbox" for="NOISE" style="font-size: large;">
		                <input type="checkbox" name="checkboxOption" data-toggle="checkbox" checked="checked" value="5" id="NOISE" >
		                舱内噪声
		              </label>
		               </div>
		              <div style="display: block;float: left;margin-left: 5px" class="INNOI">
		              <label class="checkbox" for="INNOI" style="font-size: large;">
		                <input type="checkbox" name="checkboxOption" data-toggle="checkbox" value="6" id="INNOI" >
		                舱内报警噪声
		              </label>
		               </div>
		              <div style="display: block;float: left;margin-left: 5px" class="OUTNOI">
		              <label class="checkbox" for="OUTNOI" style="font-size: large;">
		                <input type="checkbox" name="checkboxOption" data-toggle="checkbox"  value="7" id="OUTNOI" >
		                箱外报警噪声
		              </label>
		               </div>
		          </div>
		          <div class="row" style="margin-left: 10%;margin-top:5%; display: block;">
		          		<div class="form-group">
				          <label class="col-sm-2 control-label" for="timeInterval" style="width: 20%">时间间隔(×10秒)</label>
				          <div class="col-sm-10" style="width: 40%">
				            <input class="form-control" type="text" id="timeInterval" 
				            	placeholder="若未输入则为间隔10秒获取数据">
				          </div>
				        </div>
				        <input type="text" style="display: none" id ="figureBeignTime">
		          </div>
		          
		          
		          <div class="btn-group btn-group-lg"
						style="margin-left: 20%; margin-top: 50px; margin-bottom: 20px">
						<button type="button" class="btn btn-info submitFigureData">导入数据</button>
						<button type="button" class="btn btn-primary checkCancel" style= "display: none">重新选取数据</button>
						<button type="button" class="btn btn-warning cancelThisOption" >关闭此操作</button>
				</div>
				<input type="text" style="display: none" class="tiltStatus">
		          </div>
		        </div>
			</div>
		</div>
	</div>
	
	<div class="boxshow_5">
		<div class="box2_5">
			<div class="login5_5"></div>
			<div class="login51_5">
				<div >
				 <div class="row" style="padding-top: 50px">
				 <div>
				 	<div class="demo-type-example">
				 	<h6 class="text-center">当前没有选中的原始记录表，请先选中要导入记录的原始记录表<br>或创建一个新的原始记录表</h6>
      				</div>
				 </div>
		          
		          <div class="btn-group btn-group-lg"
						style="margin-left: 35%; margin-top: 50px; margin-bottom: 20px">
						<button type="button" class="btn btn-info noOriginalId">原始记录表选择</button>
						<button type="button" class="btn btn-warning closebox2_5">关闭搜索框</button>
				  </div>
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
								<h4>输入以下任一条件进行查询</h4>
								<br>
								<form class="form-inline" role="form">
									<div class="form-group">
										<label class="sr-only" for="originalBeginDate">Email
											address</label> <input type="text" class="form-control"
											id="originalBeginDate" placeholder="输入开始时间">
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
						<div class="row originalTableListDiv" style="margin-top: 1%;margin-left:3%;display: none;
								max-height: 400px;overflow: auto;width: 87%">
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
								<input class="form-control" type="text"
									id="formGroupInputLarge" placeholder="若未输入将以当前时间作为表名">
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
<input type="text" class="T36ATtempInput" style="display: none">

	<div class="boxshow_2">
		<div class="box2_2">
			<div class="login5_2"></div>
			<div class="login51_2">
				<div style="padding-left: 120px; padding-top: 5px;max-height: 95%;display: block;">
					<input type="text" id="getStep" style="display: none">
					<input type="text" style="display: none" id="optionsValue">
					
					<div class="row tableTitle" style="display: none;margin-left: -30px;">
					<h4 class="text-center" style="margin-left: -10%">原始记录表基本信息</h4>
					<br>
						<c:set value="${ fn:split(sessionScope.newOriginalData.getTitleInfo(), '#@') }" var="titleInfo" />
			    		<table class = 'titleTable tableHead' border="1" style="width: 90%;word-break: break-all; word-wrap: break-word">
			    			<tr>
			    				<td style="width:12%"><strong>委托单位</strong></td>
			    				<td colspan = '5' >
			    					<div style="float: right">
										<span class="fui-cross tableHeadClear" style="cursor: pointer;display:none; "></span>
			    					</div>
			    					<div style="display: block;float: right;width: 95%">
			    				    	<input type="text" class="tableHeadCpy"  value="${titleInfo[0]==' '?'':titleInfo[0]}">
			    					</div>
								</td>
			    			</tr>
			    			<tr>
			    				<td><strong>型号规格</strong></td>
			    				<td colspan = '2' style="width: 45% "><input type="text" value="${titleInfo[3]}"></td>
			    				<td  style="width:18%"><strong>校准日期</strong></td>
			    				<td colspan = '2'><input type="text" value="${titleInfo[5]}" style="word-break:break-all"></td>
			    			</tr>
			    			<tr>
			    				<td><strong>制造厂</strong></td>
			    				<td colspan = '2'><input type="text" value="${titleInfo[2]}"></td>
			    				<td><strong>检定环境</strong></td>
			    				<td><input type="text" style="width: 80%;" value="${titleInfo[7]}">℃</td>
			    				<td><input type="text" style="width: 60%;" value="${titleInfo[8]}">%RH</td>
			    			</tr>
			    			<tr>
			    				<td><strong>出厂编号</strong></td>
			    				<td colspan = '2'>
			    					<div style="float: right">
										<span class="fui-cross tableHeadModelIdClear" style="cursor: pointer;display:none; "></span>
			    					</div>
			    					<div style="display: block;float: right;width: 85%">
			    				    	<input type="text" class="tableHeadModelId" value="${titleInfo[4]}">
			    					</div>
			    				</td>
			    				<td><strong>校准依据</strong></td>
			    				<td colspan = '2'><input type="text" value="${(titleInfo[9]==' '||titleInfo[9]==null)?'JJF 1260——2010':titleInfo[9]}"></td>
			    			</tr>
			    			<tr style='margin-top: 10px'>
			    				<td><strong>标准器名称</strong></td>
			    				<td colspan = '2' ><input type="text" value="${(titleInfo[10]==' '||titleInfo[10]==null)?'婴儿培养箱测量仪':titleInfo[10]}"></td>
			    				<td height="40px" ><strong>校准器证书号</strong></td>
			    				<td colspan = '5'><input type="text" value="${titleInfo[13]}"></td>
			    			</tr>
			    			<tr>
			    				<td><strong>标准编号</strong></td>
			    				<td colspan = '2'><input type="text" value="${titleInfo[15]}"></td>
			    				<td height="40px" ><strong>有效期至</strong></td>
			    				<td colspan = '2'><input type="text"  value="${titleInfo[16]}"></td>
			    			</tr>
			    		</table>
			    		
			    		<div class="row originalTitleInfoListDiv" style="margin-top: 2%;margin-right:-4%;
								max-height: 400px;overflow: auto;width: 95%;display: none">
							<table id="originalTitleInfoList" class="table table-striped"
								style="font-size: large; text-align: center; width: 100%;">
								<tr class="titleTr" style="background-color: #3499db;color: #FFF">
									<th style="text-align: center;">创建时间</th>
									<th style="text-align: center;" width="53%">委托单位</th>
									<th style="text-align: center;" width="13%">出厂编号</th>
									<th style="text-align: center;" width="9%">检测仪</th>
								</tr>
							</table>
							
						</div>
					</div>

					<c:set value="${ fn:split(sessionScope.newOriginalData.getTableOne(),'#@') }" var="tableOne" />
					<div class="row tableOne" style="display: none">
						<h4 class="text-center" style="margin-left: -10%">外观、报警功能及电气安全检查</h4>
						<p class="text-right" style="margin-right: 10%">
							<a class="btn btn-info resetRadio">清空选项</a>
						</p>
						<table class="oneTable" style="margin-left: 0%">
							<tr>
								<td colspan="2" height="50px" width="40%" class="bordertd"><strong>检查项目</strong></td>
								<td colspan="2" width="50%" width="40%" class="bordertd"><strong>测&nbsp;量&nbsp;值</strong></td>
							</tr>
							<tr>
								<td colspan="2" height="50px" class="bordertd"><strong>&nbsp;外&nbsp;&nbsp;观&nbsp;</strong></td>
								<td colspan="2" class="bordertd"><input type="radio"
									name="outLine" value="0" style="width: 18px; height: 18px" 
										<c:out value="${tableOne[0]=='0'?'checked':'' }"/>/>符合标准
									&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" 
									name="outLine" value="1" style="width: 18px; height: 18px" 
										<c:out value="${tableOne[0]=='1'?'checked':'' }"/>/>不符合标准
								</td>
							</tr>
							<tr>
								<td colspan="2" height="50px" class="bordertd"><strong>电源中断报警</strong></td>
								<td colspan="2" class="bordertd"><input type="radio"
									name="powerWarn" value="0" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[1]=='0'?'checked':'' }"/>/>符合
									&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" 
									name="powerWarn" value="1" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[1]=='1'?'checked':'' }"/>/>不符合
										&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" 
									name="powerWarn" value="2" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[1]=='2'?'checked':'' }"/>/>功能缺失
								</td>
							</tr>
							<tr>
								<td colspan="2" height="50px" class="bordertd"><strong>风机报警</strong></td>
								<td colspan="2" class="bordertd"><input type="radio"
									name="windWarn" value="0" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[2]=='0'?'checked':'' }"/>/>符合
									&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" 
									name="windWarn" value="1" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[2]=='1'?'checked':'' }"/>/>不符合
									&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" 
									name="windWarn" value="2" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[2]=='2'?'checked':'' }"/>/>功能缺失
								</td>
							</tr>
							<tr>
								<td colspan="2" height="50px" class="bordertd"><strong>过热切断装置报警</strong></td>
								<td colspan="2" class="bordertd"><input type="radio"
									name="heartWarn" value="0" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[3]=='0'?'checked':'' }"/>/>符合
									&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" 
									name="heartWarn" value="1" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[3]=='1'?'checked':'' }"/>/>不符合
									&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" 
									name="heartWarn" value="2" style="width: 18px; height: 20px" 
										<c:out value="${tableOne[3]=='2'?'checked':'' }"/>/>功能缺失
								</td>
							</tr>
										<tr>
								<td rowspan="2" class="bordertd"><strong>患者漏电流/μA</strong></td>
								<td height="50px" class="bordertd"><strong>&nbsp;直&nbsp;&nbsp;流&nbsp;</strong></td>
								<td colspan="2" class="bordertd"><input type="text"  value="${tableOne[4]}"></td>
							</tr>
							<tr>
								<td height="50px" class="bordertd"><strong>&nbsp;交&nbsp;&nbsp;流&nbsp;</strong></td>
								<td colspan="2" class="bordertd"><input type="text" value="${tableOne[5]}"></td>
							</tr>
							<tr>
								<td colspan="2" height="50px" class="bordertd"><strong>机壳漏电流/μA</strong></td>
								<td colspan="2" class="bordertd"><input type="text" value="${tableOne[6]}"></td>
							</tr>
							<tr>
								<td colspan="2" height="50px" class="bordertd"><strong>接地电阻/Ω</strong></td>
								<td colspan="2" class="bordertd"><input type="text" value="${tableOne[7]}"></td>
							</tr>
						</table>
					</div>
					<div class="row tableTwo" style=" display: none; ">
						<h5 class="text-center" style="margin-left: -10%">温度校准记录</h5>
						<h6></h6>
						 <div class="btn-group">
				            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" 
				            	type="button"><span id = "tempCenterBtn" >控制温度：32 单位：℃ </span><span class="caret"></span></button>
				            <ul role="menu" class="dropdown-menu">
				              <li><a href="#" class="T32Show">控制温度：32 &nbsp;&nbsp;单位：℃</a></li>
				              <li><a href="#" class="T36Show">控制温度：36 &nbsp;&nbsp;单位：℃</a></li>
				            </ul>
				          </div><!-- /btn-group -->
				          
				          <input class="flagForTableTwo" value="0" style="display:none " type="text">
				          <div class="input-group" style="width: 30%;float: right;margin-right: 11%;margin-bottom: 1%">
					              <span class="input-group-addon">显示温度</span>
					              <input type="text" class="form-control DISTValueInput" >
					              <span class="input-group-btn">
					                <button class="btn btn-default setDISTValue" type="button">设置</button>
					              </span>
					        </div>
							<div class="T32">
							<c:set value="${ fn:split(sessionScope.newOriginalData.getTableTwo(), '#@') }" var="TableTwo" />
							<input type="text" class="T32Time" style="display:none " value="${TableTwo[211]}">
							<table class="oneTable table_input" id="T32" style="width: 90%">
								<tr>
									<td class="bordertd" width="15%"></td>
									<td class="bordertd" width="15%"><strong>显示温度</strong></td>
									<td class="bordertd" width="14%"><strong>A</strong></td>
									<td class="bordertd" width="14%"><strong>B</strong></td>
									<td class="bordertd" width="14%"><strong>C</strong></td>
									<td class="bordertd" width="14%"><strong>D</strong></td>
									<td class="bordertd" width="14%"><strong>E</strong></td>
								</tr>
							
								<c:forEach var="i" begin="1" end="15">
									<tr class="T32CTr">
										<td class="bordertd"><strong>${i}</strong></td>
										<td class="bordertd T32CDIST"><input type="text" class="T32CDISTInputText" value="${TableTwo[(i-1)*12]}"></td>
										<td class="bordertd T32CAT">${TableTwo[(i-1)*12+1]}</td>
										<td class="bordertd T32CBT">${TableTwo[(i-1)*12+2]}</td>
										<td class="bordertd T32CCT">${TableTwo[(i-1)*12+3]}</td>
										<td class="bordertd T32CDT">${TableTwo[(i-1)*12+4]}</td>
										<td class="bordertd T32CET">${TableTwo[(i-1)*12+5]}</td>
									</tr>
								</c:forEach>
								<tr>
										<td class="bordertd"><strong>修正值</strong></td>
										<td class="bordertd T32CnoneValue">/</td>
										<td class="bordertd T32CRevise"><input type="text" value=" ${TableTwo[180]}"></td>
										<td class="bordertd T32CRevise"><input type="text" value=" ${TableTwo[181]}"></td>
										<td class="bordertd T32CRevise"><input type="text" value=" ${TableTwo[182]}"></td>
										<td class="bordertd T32CRevise"><input type="text" value=" ${TableTwo[183]}"></td>
										<td class="bordertd T32CRevise"><input type="text" value=" ${TableTwo[184]}"></td>
								</tr>
								<tr>
										<td class="bordertd"><strong>平均值</strong></td>
										<td class="bordertd T32CAVG AVGOne">${TableTwo[190]}</td>
										<td class="bordertd T32CAVG AVGATT32">${TableTwo[191]}</td>
										<td class="bordertd T32CAVG">${TableTwo[192]}</td>
										<td class="bordertd T32CAVG">${TableTwo[193]}</td>
										<td class="bordertd T32CAVG">${TableTwo[194]}</td>
										<td class="bordertd T32CAVG">${TableTwo[195]}</td>
								</tr>
							</table>
							<button type="button" class="btn btn-info btn-lg btn-block T32CInputBtn"
								style="width: 90%">从图中导入32℃数据</button>
						</div>
						<div class="T36">
							<input type="text" class="T36Time" style="display:none " value="${TableTwo[212]}">
							<table class="oneTable table_input T36_class" id="T36" style="width: 90%">
								<tr >
									<td class="bordertd" width="15%"></td>
									<td class="bordertd" width="15%"><strong>显示温度</strong></td>
									<td class="bordertd" width="14%"><strong>A</strong></td>
									<td class="bordertd" width="14%"><strong>B</strong></td>
									<td class="bordertd" width="14%"><strong>C</strong></td>
									<td class="bordertd" width="14%"><strong>D</strong></td>
									<td class="bordertd" width="14%"><strong>E</strong></td>
								</tr>
								
								<c:forEach var="i" begin="1" end="15">
									<tr class="T36Tr">
										<td class="bordertd"><strong>${i}</strong></td>
										<td class="bordertd T36DIST"><input type="text" class="T36DISTInputText" value="${TableTwo[(i-1)*12+6]}"></td>
										<td class="bordertd T36AT">${TableTwo[(i-1)*12+7]}</td>
										<td class="bordertd T36BT">${TableTwo[(i-1)*12+8]}</td>
										<td class="bordertd T36CT">${TableTwo[(i-1)*12+9]}</td>
										<td class="bordertd T36DT">${TableTwo[(i-1)*12+10]}</td>
										<td class="bordertd T36ET">${TableTwo[(i-1)*12+11]}</td>
									</tr>
								</c:forEach>
								<tr>
										<td class="bordertd"><strong>修正值</strong></td>
										<td class="bordertd T36noneValue">/</td>
										<td class="bordertd T36Revise"><input type="text" value=" ${TableTwo[185]}"></td>
										<td class="bordertd T36Revise"><input type="text" value=" ${TableTwo[186]}"></td>
										<td class="bordertd T36Revise"><input type="text" value=" ${TableTwo[187]}"></td>
										<td class="bordertd T36Revise"><input type="text" value=" ${TableTwo[188]}"></td>
										<td class="bordertd T36Revise"><input type="text" value=" ${TableTwo[189]}"></td>
								</tr>
								<tr>
										<td class="bordertd"><strong>平均值</strong></td>
										<td class="bordertd T36AVG AVGTwo">${TableTwo[196]}</td>
										<td class="bordertd T36AVG AVGATT36">${TableTwo[197]}</td>
										<td class="bordertd T36AVG">${TableTwo[198]}</td>
										<td class="bordertd T36AVG">${TableTwo[199]}</td>
										<td class="bordertd T36AVG">${TableTwo[200]}</td>
										<td class="bordertd T36AVG">${TableTwo[201]}</td>
								</tr>
								<tr style="display: none">
									<td colspan="1" rowspan="2" class="bordertd"><strong>温度偏差</strong></td>
									<td colspan="1" class="bordertd"><strong>32</strong></td>
									<td colspan="1" class="bordertd T32deviation">${TableTwo[202]}</td>
									<td colspan="2" rowspan="2" class="bordertd"><strong>温度波动度</strong></td>
									<td colspan="1" class="bordertd"><strong>32</strong></td>
									<td colspan="1" class="bordertd T32Wave">${TableTwo[203]}</td>
								</tr>
								<tr style="display: none">
									<td colspan="1" class="bordertd"><strong>36</strong></td>
									<td colspan="1" class="bordertd T36deviation">${TableTwo[204]}</td>
									<td colspan="1" class="bordertd"><strong>36</strong></td>
									<td colspan="1" class="bordertd T36Wave">${TableTwo[205]}</td>
								</tr>
								<tr style="display: none">
									<td colspan="1" rowspan="2" class="bordertd"><strong>温度均匀度</strong></td>
									<td colspan="1"  class="bordertd"><strong>32</strong></td>
									<td colspan="1" class="bordertd UniformityT32Avg">${TableTwo[206]}</td>
									<td colspan="2" rowspan="2" class="bordertd"><strong>平均培养箱温度与<br>控制温度之差
									</strong></td>
									<td colspan="2" rowspan="2" height="20px" class="bordertd tableTwoSaveOne">${TableTwo[208]}</td>
								</tr>
								<tr style="display: none">
									<td colspan="1" class="bordertd"><strong>36</strong></td>
									<td colspan="1" class="bordertd UniformityT36Avg">${TableTwo[207]}</td>
								</tr>
								<tr style="display: none">
									<td colspan="2" rowspan="2" class="bordertd"><strong>调整控制温度后，<br>测得培养箱温度最大值
									</strong></td>
									<td colspan="1" rowspan="2" class="bordertd tableTwoSaveTwo">${TableTwo[209]}</td>
									<td colspan="2" rowspan="2" class="bordertd"><strong>温度超调量</strong></td>
									<td colspan="2" rowspan="2" class="bordertd tableTwoSaveThree">${TableTwo[210]}</td>
								</tr>
							</table>
							<button type="button" class="btn btn-info btn-lg btn-block T36InputBtn" 
								style="width: 90%">从图中导入36℃数据</button>
						</div>
					</div>
					<div class="row tableThree" style="display: none">
						<h5 class="text-center" style="margin-left: -10%">床垫倾斜时的温度</h5>
						<h6>控制温度：32 &nbsp;&nbsp;单位：℃</h6>
						<table class="oneTable table_input" id="T32LR" style="width: 90%">
							<tr>
								<td height="20px" class="bordertd" width="15%"></td>
								<td class="bordertd showT32LRInfo" width="15%" style="display: none"><strong>显示温度</strong></td>
								<td class="bordertd" width="14%"><strong>A</strong></td>
								<td class="bordertd" width="14%"><strong>B</strong></td>
								<td class="bordertd" width="14%"><strong>C</strong></td>
								<td class="bordertd" width="14%"><strong>D</strong></td>
								<td class="bordertd" width="14%"><strong>E</strong></td>
							</tr>
							<c:set value="${ fn:split(sessionScope.newOriginalData.getTableThree(), '#@') }" var="TableThree" />
							<c:forEach var="i" begin="1" end="7">
								<tr class="T32LTr">
									<td height="20px" class="bordertd"><strong>${i}</strong></td>
									<td class="bordertd T32LDIST"  style="display: none"><input class="T32LDISTInputText" type="text" value="${TableThree[(i-1)*6]}"></td>
									<td class="bordertd T32LAT">${TableThree[(i-1)*6+1]}</td>
									<td class="bordertd T32LBT">${TableThree[(i-1)*6+2]}</td>
									<td class="bordertd T32LCT">${TableThree[(i-1)*6+3]}</td>
									<td class="bordertd T32LDT">${TableThree[(i-1)*6+4]}</td>
									<td class="bordertd T32LET">${TableThree[(i-1)*6+5]}</td>
								</tr>
							</c:forEach>
							<tr><td height="20px" class="bordertd"  colspan="6" ></td></tr>
							<c:forEach var="i" begin="8" end="15">
								<tr class="T32RTr">
									<td height="20px" class="bordertd"><strong>${i}</strong></td>
									<td class="bordertd T32RDIST"  style="display: none"><input class="T32RDISTInputText" type="text" value="${TableThree[(i-1)*6]}"></td>
									<td class="bordertd T32RAT">${TableThree[(i-1)*6+1]}</td>
									<td class="bordertd T32RBT">${TableThree[(i-1)*6+2]}</td>
									<td class="bordertd T32RCT">${TableThree[(i-1)*6+3]}</td>
									<td class="bordertd T32RDT">${TableThree[(i-1)*6+4]}</td>
									<td class="bordertd T32RET">${TableThree[(i-1)*6+5]}</td>
								</tr>
							</c:forEach>
								<tr>
										<td height="20px" class="bordertd"><strong>修正值</strong></td>
										<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[90]}"></td>
										<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[91]}"></td>
										<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[92]}"></td>
										<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[93]}"></td>
										<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[94]}"></td>
								</tr>
								<tr>
										<td height="20px" class="bordertd"><strong>平均值</strong></td>
										<td class="bordertd T32LRAVG">${TableThree[95]}</td>
										<td class="bordertd T32LRAVG">${TableThree[96]}</td>
										<td class="bordertd T32LRAVG">${TableThree[97]}</td>
										<td class="bordertd T32LRAVG">${TableThree[98]}</td>
										<td class="bordertd T32LRAVG">${TableThree[99]}</td>
								</tr>
								<tr style="display: none">
									<td height="20px" class="bordertd" colspan="2" ><strong>床垫倾斜时温度均匀度</strong></td>
									<td class="bordertd T32LRUniformity">${TableThree[100]}</td>
									<td class="bordertd T32LRUniformity">${TableThree[101]}</td>
									<td class="bordertd T32LRUniformity">${TableThree[102]}</td>
									<td class="bordertd T32LRUniformity">${TableThree[103]}</td>
								</tr>
						</table>
						 <div class="row demo-row">
					        <div class="col-xs-3" style="width: 45%">
					          <a href="#" class="btn btn-block btn-lg btn-info T32LInputBtn">导入左倾数据</a>
					        </div>
					        <div class="col-xs-3" style="width: 45%">
					          <a href="#" class="btn btn-block btn-lg btn-info T32RInputBtn">导入右倾数据</a>
					        </div>
					      </div> <!-- /row -->
						</div>

					<div class="row tableFour" style="display: none;">
						<h5 class="text-center"style="margin-left: -10%">湿度相对偏差</h5>
						<br>
						<table class="oneTable AFTable" style="width: 90%">
							<tr>
								<td width="20%" height="40px" class="bordertd"><strong>次数</strong></td>
								<td width="20%" class="bordertd"><strong>1</strong></td>
								<td width="20%" class="bordertd"><strong>2</strong></td>
								<td width="20%" class="bordertd"><strong>3</strong></td>
								<td class="bordertd"><strong>平均值</strong></td>
							</tr>
							<c:set value="${ fn:split(sessionScope.newOriginalData.getTableFour(), '#@') }" var="TableFour" />
							<tr class="DIRSHInputTr">
								<td height="40px" class="bordertd"><strong>显示湿度/%RH</strong></td>
								<td class="bordertd"><input type="text" class="DIRSHInputData" value="${TableFour[0]}"></td>
								<td class="bordertd"><input class="DIRSHInputData" type="text" value="${TableFour[1]}"></td>
								<td class="bordertd"><input class="DIRSHInputData" type="text" value="${TableFour[2]}"></td>
								<td class="bordertd avgDIRSHInputData">${TableFour[3]}</td>
							</tr>
							<tr  class="AFInputTr">
								<td height="40px" class="bordertd"><strong>实测湿度/%RH</strong></td>
								<td class="bordertd AFInputDataOne" ><input class="realData" type="text" value="${TableFour[4]}"></td>
								<td class="bordertd AFInputDataTwo"><input class="realData" type="text" value="${TableFour[5]}"></td>
								<td class="bordertd AFInputDataThree"><input class="realData" type="text"value="${TableFour[6]}"></td>
								<td class="bordertd avgAFInputData">${TableFour[7]}</td>
							</tr>
							<tr>
								<td height="40px" class="bordertd"><strong>相对湿度偏差/%RH</strong></td>
								<td colspan="4" class="bordertd AFRelative">${TableFour[8]}</td>
							</tr>
						</table>
						<button type="button" class="btn btn-info btn-lg btn-block AFInputBtn" 
								style="width: 90%">从图中导入湿度数据</button>
					</div>
					<div class="row tableFive" style="display: none">
						<h5 class="text-center"style="margin-left: -10%">婴儿舱内氧分析器示值误差</h5>
						<br>
						<div class="form-group" style="margin-right: 10%">
				            <div class="input-group" style="width: 30%">
				              <span class="input-group-addon">满量程值</span>
				              <input type="text" class="form-control tableFiveR" value="100" >
				            </div>
				         </div>
						<table class="oneTable" style="width: 90%">
							<tr>
								<td width="30%" height="40px" class="bordertd"><strong>次数</strong></td>
								<td width="20%" class="bordertd"><strong>1</strong></td>
								<td width="20%" class="bordertd"><strong>2</strong></td>
								<td width="20%" class="bordertd"><strong>3</strong></td>
								<td class="bordertd"><strong>平均值</strong></td>
							</tr>
							<c:set value="${ fn:split(sessionScope.newOriginalData.getTableFive(), '#@') }" var="TableFive" />
							<tr>
								<td height="40px" class="bordertd"><strong>显示氧体积分数/%</strong></td>
								<td class="bordertd "><input class="O2input O2One" type="text" value="${TableFive[0]}"></td>
								<td class="bordertd "><input class="O2input O2Two" type="text" value="${TableFive[1]}"></td>
								<td class="bordertd "><input class="O2input O2Three" type="text" value="${TableFive[2]}"></td>
								<td class="bordertd O2Four">${TableFive[3]}</td>
							</tr>
							<tr>
								<td height="40px" class="bordertd"><strong>氧标准气体的体积分数/%</strong></td>
								<td colspan="4" class="bordertd"><input class="O2Five" type="text" value="${TableFive[4]}"></td>
							</tr>
							<tr>
								<td height="40px" class="bordertd"><strong>氧分析器示值误差/%</strong></td>
								<td colspan="4" class="bordertd O2Six">${TableFive[5]}</td>
							</tr>
						</table>
					</div>

					<div class="row tableSix" style="display: none">
						<h5 class="text-center"style="margin-left: -10%">婴儿舱内的噪声</h5>
						<br>
						<table class="oneTable" style="width: 90%">
						<c:set value="${ fn:split(sessionScope.newOriginalData.getTableSix(), '#@') }" var="TableSix" />
							<tr>
								<td width="20%" height="40px" class="bordertd"><strong>婴儿舱内的噪声/dB</strong></td>
								<td width="10%" class="bordertd"><strong>1</strong></td>
								<td width="10%" class="bordertd"><input type="text" class="NoiseInput NoiseOne" value="${TableSix[0]}"></td>
								<td width="10%" class="bordertd"><strong>2</strong></td>
								<td width="10%" class="bordertd"><input type="text"  class="NoiseInput NoiseTwo" value="${TableSix[1]}"></td>
								<td width="10%" class="bordertd"><strong>3</strong></td>
								<td width="10%" class="bordertd"><input type="text"  class="NoiseInput NoiseThree" value="${TableSix[2]}"></td>
								<td class="bordertd"><strong>平均值</strong></td>
								<td width="10%" class="bordertd NoiseFour">${TableSix[3]}</td>
							</tr>
						</table>
					</div>
					<div class="row tableSeven" style="display: none">
						<h5 class="text-center" style="margin-left: -10%">报警器报警噪声</h5>
						<br>
						<c:set value="${ fn:split(sessionScope.newOriginalData.getTableSeven(), '#@') }" var="TableSeven" />
						<table class="oneTable" style="width: 90%">
							<tr>
								<td width="23%" height="40px" class="bordertd"><strong>婴儿舱内的噪声/dB</strong></td>
								<td width="9%" class="bordertd"><strong>1</strong></td>
								<td width="10%" class="bordertd"><input type="text" class="NoiwarinInput NoiwarinOne" value="${TableSeven[0]}"></td>
								<td width="9%" class="bordertd"><strong>2</strong></td>
								<td width="10%" class="bordertd"><input type="text" class="NoiwarinInput NoiwarinTwo" value="${TableSeven[1]}"></td>
								<td width="9%" class="bordertd"><strong>3</strong></td>
								<td width="10%" class="bordertd"><input type="text" class="NoiwarinInput NoiwarinThree" value="${TableSeven[2]}"></td>
								<td class="bordertd"><strong>平均值</strong></td>
								<td width="10%" class="bordertd NoiwarinFour">${TableSeven[3]}</td>
							</tr>
							<tr>
								<td width="23%" height="40px" class="bordertd"><strong>箱外噪声/dB</strong></td>
								<td width="9%" class="bordertd"><strong>1</strong></td>
								<td width="10%" class="bordertd"><input type="text"  class="outNoiInput NoiwarinFive" value="${TableSeven[4]}"></td>
								<td width="9%" class="bordertd"><strong>2</strong></td>
								<td width="10%" class="bordertd"><input type="text" class="outNoiInput NoiwarinSix" value="${TableSeven[5]}"></td>
								<td width="9%" class="bordertd"><strong>3</strong></td>
								<td width="10%" class="bordertd"><input type="text" class="outNoiInput NoiwarinSeven" value="${TableSeven[6]}"></td>
								<td class="bordertd"><strong>平均值</strong></td>
								<td width="10%" class="bordertd NoiwarinEight">${TableSeven[7]}</td>
							</tr>
						</table>
					</div>
					<div class="row tableEight" style="display:">
						<h5 class="text-center" style="margin-left: -10%">备注</h5>
						<br>
						<div class="form-group" style="width: 90%">
							<textarea class="form-control textareaRemark" rows="3">${sessionScope.newOriginalData.getTableEight()}</textarea>
						</div>
					</div>
					<div class="row tableNine" style="display:">
						<h5 class="text-center" style="margin-left: -10%">等级(不确定度)</h5>
						<br>
						<table class = 'titleTable' border="1" style="border-color:1px solid black;width: 90%">
			    			<tr>
			    				<td class='underLine' height="40px" style="width: 6%"><strong>等级(不确定度)</strong></td>
			    				<td class='underLine' colspan = '5'><input type="text" class="uncertainty" value="${titleInfo[14]}"></td>
			    			</tr>
			    		</table>
					</div>
					<div class="btn-group btn-group-lg"
						style="margin-left: 25%; margin-top: 30px; margin-bottom: 20px">
						<button type="button" class="btn btn-info saveInfo">保存</button>
						<button type="button" class="btn btn-primary lastStep">上一步</button>
						<button type="button" class="btn btn-primary nextStep">下一步</button>
						<button type="button" class="btn btn-warning cancel">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container"
		style="width: 100%;">
		<div class="wrapper" >
			<div style="display: none">
				<form action="getOriginalTable" id="OriginalIdSubmit" method="post">
					<input type="text" name="originalId" id="originalId" value="${sessionScope.newOriginalData.getOriginalDataId()}">
				</form>
			</div>
			<script>
		      $(document).ready(function(){
		        $('.skin input').iCheck({
		          checkboxClass: 'icheckbox_flat-red',
		          radioClass: 'iradio_flat-red'
		        });
		      });
		   	</script>
			<div style="height: 650px; width: 100%; margin: 10px auto;" id="main"
				class="row"></div>
			
			<div class="row">
			<div style="display: block;float: left;width: 80%">
				<input style="display: none" value="${sessionScope.beginDate}" class="click_date">
				<h6 class="h6Show">检测仪器编号为：${sessionScope.detectionId}, &nbsp;&nbsp;日期为：${sessionScope.beginDate},&nbsp;&nbsp;${sessionScope.intervalTimeTmp} &nbsp;&nbsp;数据来源：${(sessionScope.dataSource=='1')?'实时记录表':'历史记录表'} </h6>
				<h6 class="h6Show">备注：若倾斜角数值为0，则为平铺状态，若倾斜角位-15，则为左倾状态，若倾斜角为15，则为右倾状态。</h6>
			</div>
			<div style="display: block;float: left;">
              <ul class="pager">
               <li class="previous">
                  <a class="previousTime" style="cursor: pointer;">
                    <span class="fui-arrow-left"></span>
                  </a>
                </li>
                <li>
                  <a class="timeSelection" style="cursor: pointer;">
                    <span>&nbsp; ${sessionScope.beginDate}&nbsp;</span>
                  </a>
                </li>
                <li class="next">
                    <a class="nextTime" style="cursor: pointer;">
                    <span class="fui-arrow-right"></span>
                  </a>
                </li>
              </ul> <!-- /pager -->
            </div>
           </div>
		</div>
		<input class="tableHeadTmp" style="display: none">
	</div>
	<script type="text/javascript">

	   function max(array)  
       {  
         return Math.max.apply(Math,array);  
       } 
	   function min(array)  
       {  
         return Math.min.apply(Math,array);  
       } 
		
		var AT = [];
		var BT = [];
		var CT = [];
		var DT = [];
		var ET = [];
		var timeX = [];
		var id = [];
		var DIST = [];
		var CONT = [];
		var DISRH = [];
		var NOISE = [];
		var INNOIS = [];
		var OUTNOI = [];
		var FLOW = [];
		var AngleX = [];
		var AF = [];
		var tableHead = [];
		
		var minT = [];
		var maxT = [];
		
		var minY = 0;
		var maxY = 0;
		
		var maxH = 0;
		var minH = 0;
		<%List<HistoryInfo> list = null;
			if ((List<HistoryInfo>) session.getAttribute("historyInfoList") != null) {
				list = (List<HistoryInfo>) session.getAttribute("historyInfoList");

				for (HistoryInfo info : list) {

					if (Integer.valueOf(info.getAngleX().toString()) > 1950
							&& Integer.valueOf(info.getAngleX().toString()) < 2088) {%>
						AngleX.push(0);
					<%} else if (Integer.valueOf(info.getAngleX().toString()) > 1648
							&& Integer.valueOf(info.getAngleX().toString()) < 1950) {%>
						AngleX.push(15);
					<%} else if (Integer.valueOf(info.getAngleX().toString()) > 2088
							&& Integer.valueOf(info.getAngleX().toString()) < 2448) {%>
						AngleX.push(-15);
					<%} else {%>
						AngleX.push(0);
					<%}%>
				id.push("<%=Integer.valueOf(info.getInspectedId().toString()) == 0 ? "" : info.getInspectedId()%>");
				DIST.push("<%=Double.valueOf(info.getDIST().toString()) == 0 ? "" : info.getDIST()%>");
				CONT.push("<%=Double.valueOf(info.getCONT().toString()) == 0 ? "" : info.getCONT()%>");
				DISRH.push("<%=Double.valueOf(info.getDISRH().toString()) == 0 ? "" : info.getDISRH()%>");
				NOISE.push("<%=Double.valueOf(info.getNOISE().toString()) == 0 ? "" : info.getNOISE()%>");
				INNOIS.push("<%=Double.valueOf(info.getINNOIS().toString()) == 0 ? "" : info.getINNOIS()%>");
				OUTNOI.push("<%=Double.valueOf(info.getOUTNOI().toString()) == 0 ? "" : info.getOUTNOI()%>");
				FLOW.push("<%=Double.valueOf(info.getFLOW().toString()) == 0 ? "" : info.getFLOW()%>");
				AF.push("<%=Double.valueOf(info.getAF().toString()) == 0 ? "" : info.getAF()%>");
				AT.push("<%=info.getAT()%>");
				BT.push("<%=info.getBT()%>");
				CT.push("<%=info.getCT()%>");
				DT.push("<%=info.getDT()%>");
				ET.push("<%=info.getET()%>");
				tableHead.push("");
				timeX.push("<%=info.getRecordTime().toString().substring(5, 19)%>");
				
		<%}
		}%>
		
		<%List<RealTimeInfo> realTimeList = null;
		if (session.getAttribute("realtimeInfoList") != null) {
			realTimeList = (List<RealTimeInfo>) session.getAttribute("realtimeInfoList");
			System.out.print("xzcxzczx");
			for (RealTimeInfo info : realTimeList) {

				if (Integer.valueOf(info.getAngleX().toString().trim()) > 1950
						&& Integer.valueOf(info.getAngleX().toString().trim()) < 2088) {%>
					AngleX.push(0);
				<%} else if (Integer.valueOf(info.getAngleX().toString().trim()) > 1648
						&& Integer.valueOf(info.getAngleX().toString().trim()) < 1950) {%>
					AngleX.push(15);
				<%} else if (Integer.valueOf(info.getAngleX().toString().trim()) > 2088
						&& Integer.valueOf(info.getAngleX().toString().trim()) < 2448) {%>
					AngleX.push(-15);
				<%} else {%>
					AngleX.push(0);
				<%}%>
			id.push("<%=info.getInspectedId()%>");
			DIST.push("<%=info.getDIST()%>");
			CONT.push("<%=info.getCONT()%>");
			DISRH.push("<%=info.getDISRH()%>");
			NOISE.push("<%=info.getNOISE()%>");
			INNOIS.push("<%=info.getINNOIS()%>");
			OUTNOI.push("<%=info.getOUTNOI()%>");
			FLOW.push("<%=info.getFLOW()%>");
			AF.push("<%=info.getAF()%>");
			AT.push("<%=info.getAT()%>");
			BT.push("<%=info.getBT()%>");
			CT.push("<%=info.getCT()%>");
			DT.push("<%=info.getDT()%>");
			ET.push("<%=info.getET()%>");
			timeX.push("<%=info.getRecordTime().toString().substring(5, 19)%>");
			
			<%if(info.getDetectionIdDetail()!=null){%>
				tableHead.push("10");
			<%}else{%>
				tableHead.push("");
			<%}
		  }
		}%>
			
		var myChart = echarts.init(document.getElementById('main'));
		myChart.showLoading();
		option = {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					animation : true
				}
			},
			legend : {
				show : true,
				data : [ "", '温度A', '温度B', '温度C', '温度D', '温度E', '湿度A',"", 
						"被检仪器编号", "控制温度", "箱外报警噪声",  "显示温度", "倾斜角","","舱内报警噪声",
						"舱内噪声", "显示湿度", "显示风速", "表头信息" ],
				selected : {
					'温度B' : false,
					'温度C' : false,
					'温度D' : false,
					'温度E' : false
				},
				x : 'left'
			},
			toolbox : {
				feature : {
					dataZoom : {
						yAxisIndex : 'none'
					},
					restore : {},
					saveAsImage : {}
				}
			},
			axisPointer : {
				link : {
					xAxisIndex : 'all'
				}
			},
			dataZoom : [ {
				show : true,
				realtime : true,
				start : 0,
				end : 100,
				xAxisIndex : [ 0, 1 ]
			}, {
				type : 'inside',
				realtime : true,
				start : 0,
				end : 100,
				xAxisIndex : [ 0, 1 ]
			} ],
			grid : [ {
				top : 120,
				left : 50,
				right : 50,
				height : '45%'
			}, {
				left : 50,
				right : 50,
				top : '70%',
				height : '24%'
			} ],
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				axisLine : {
					onZero : true
				},
				data : timeX,
			}, {
				gridIndex : 1,
				type : 'category',
				boundaryGap : false,
				axisLine : {
					onZero : true
				},
				data : timeX,
				position : 'top'
			} ],
			yAxis : [ {
				name : '温度(℃)',
				type : 'value',
				scale : true,
				
			}, {
				gridIndex : 1,
				type : 'value',
				inverse : true
			}, {
				name : '湿度(%RH)',
				type : 'value',
				scale : true,
			
			}, {
				gridIndex : 1,
				max:30,
				min:-30,
				type : 'value',
				inverse : true
			} ],
			series : [ {
				name : '温度A',
				type : 'line',
				smooth : true,
				clickable : true,
				symbolSize : 8,
				hoverAnimation : false,
				itemStyle : {
					normal : {
						color : '#ff9666',
					}
				},
				data : AT
			}, {
				name : '温度B',
				type : 'line',
				smooth : true,
				clickable : true,
				symbolSize : 8,
				hoverAnimation : false,
				itemStyle : {
					normal : {
						color : '#7a56ff',
					}
				},
				data : BT
			}, {
				name : '温度C',
				type : 'line',
				smooth : true,
				clickable : true,
				symbolSize : 8,
				hoverAnimation : false,
				itemStyle : {
					normal : {
						color : '#ffee00',
					}
				},
				data : CT
			}, {
				name : '温度D',
				type : 'line',
				smooth : true,
				clickable : true,
				symbolSize : 8,
				hoverAnimation : false,
				itemStyle : {
					normal : {
						color : '#00ad51',
					}
				},
				data : DT
			}, {
				name : '温度E',
				clickable : true,
				type : 'line',
				smooth : true,
				//			itemStyle: {normal: {areaStyle: {type: 'default'}}},
				symbolSize : 8,
				hoverAnimation : false,
				itemStyle : {
					normal : {
						color : '#7ecef4',
					}
				},
				data : ET
			}, {
				name : '湿度A',
				clickable : true,
				yAxisIndex : 2,
				type : 'line',
				smooth : true,
				//			itemStyle: {normal: {areaStyle: {type: 'default'}}},
				
				symbolSize : 8,
				hoverAnimation : false,
				itemStyle : {
					normal : {
						color : '#ff0096',
					}
				},
				data : AF
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '显示温度',
				type : 'scatter',
				itemStyle : {
					normal : {
						color : '#f7b322',
					}
				},
				data : DIST
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '显示风速',
				type : 'scatter',
				itemStyle : {
					normal : {
						color : '#8c8e68',
					}
				},
				data : FLOW
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '控制温度',
				type : 'scatter',
				itemStyle : {
					normal : {
						color : '#61a0a8',
					}
				},
				data : CONT
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '箱外报警噪声',
				type : 'scatter',
				itemStyle : {
					normal : {
						color : '#f22902',
					}
				},
				data : OUTNOI
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '舱内报警噪声',
				type : 'scatter',
				itemStyle : {
					normal : {
						color : '#89c997',
					}
				},
				data : INNOIS
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '舱内噪声',
				type : 'scatter',
				itemStyle : {
					normal : {
						color : '#c5683a',
					}
				},
				data : NOISE
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '显示湿度',
				type : 'scatter',
				itemStyle : {
					normal : {
						color : '#2f4554',
					}
				},
				data : DISRH
			}, {
				xAxisIndex : 1,
				yAxisIndex : 3,
				smooth : true,
				clickable : true,
				name : '倾斜角',
				itemStyle : {
					normal : {
						color : '#7ecef4',
						areaStyle : {
							type : 'default',
							color : '#7ecef4'
						}
					}
				},
				type : 'line',
				data : AngleX
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '被检仪器编号',
				type : 'scatter',
				itemStyle : {
					normal : {
						color : '#0068b7',
					}
				},
				data : id
			},{
				xAxisIndex : 1,
				yAxisIndex : 3,
				symbolSize : 28,
				name : '表头信息',
				type : 'scatter',
				symbol: 'triangle',
				data : tableHead
			}  ]
		};

		myChart.setOption(option);
		myChart.hideLoading();
		myChart.on('click', function(params) {
			//var str = params.name + params.seriesName + params.data;
			
			if($("#originalId").val()==""||$("#originalId").val()==null){
				
				$('.box2_5').fadeIn("slow");
				
				
			}else{
				
				$(".optionsDiv").show();
				var recordTime = $(".click_date").val().substring(0,4) + "-" +params.name;
				$("#figureBeignTime").val(recordTime);
				
				if($("#optionsValue").val()!=""){
					$(".optionsDiv").hide();
				}
				$("#NOISE").removeAttr("checked");
				$("#INNOI").removeAttr("checked");
				$("#OUTNOI").removeAttr("checked");
				$("#AF").removeAttr("checked");
				
				$.ajax({
		             type: "post",
		             url: "getSpecifyHistoryData",
		             data: {detectionId: $(".textDetId").html(),recordTime : recordTime,dataSource:$("#sourceTmp").val()},
		             dataType: "json",
		             success: function(data){
							$(".tableHeadTmp").val("");
							var arr = data.split('#@');
							var html = "<strong><h6>该点的基本信息为：</h6></strong>"; 
							html += "<strong>时间：</strong>" + arr[0] + "&nbsp;&nbsp;" + "<strong>检测仪器编号：</strong>" 
								+ $(".textDetId").html() + "&nbsp;&nbsp;";
							if(arr[8]!="0"){
								html +="<strong>被检仪器编号为：</strong>"+arr[8] + "&nbsp;&nbsp;";
							}
							
							$(".T36ATtempInput").val(arr[1]);
							html += "<br> <strong>温度A：</strong>" + arr[1] + "℃&nbsp;&nbsp;<strong>温度B：</strong>" + arr[2]
								+ "℃&nbsp;&nbsp;<strong>温度C：</strong>" + arr[3] + "℃&nbsp;&nbsp;<strong>温度D：</strong>" 
								+ arr[4] + "℃&nbsp;&nbsp;<strong>温度E：</strong>" 
								+ arr[5] + "℃&nbsp;&nbsp;<br><strong>湿度：</strong>" + arr[6] +"%RH&nbsp;&nbsp;";
							if(arr[7]>2088&&arr[7]<2488){
								html += "<strong>倾斜方向：</strong>左倾&nbsp;&nbsp;";
								$("#T32L").attr("checked","checked");
								
								$(".tiltStatus").val("1");
							}
							if(arr[7]>1950&&arr[7]<2088){
								html += "<strong>倾斜方向：</strong>平铺&nbsp;&nbsp;";
								$("#T32C").attr("checked","checked");
								$(".tiltStatus").val("0");
							}
							if(arr[7]>1648&&arr[7]<1950){
								html += "<strong>倾斜方向：</strong>右倾&nbsp;&nbsp;";
								$("#T32R").attr("checked","checked");
								$(".tiltStatus").val("2");
							}
							if(arr[9]!="0.00"){
								html +="<strong>舱内报警噪声：</strong>"+arr[9] + "/dB&nbsp;&nbsp;";
							}
							if(arr[10]!="0.00"){
								html +="<strong>箱外报警噪声：</strong>"+arr[10] + "/dB&nbsp;&nbsp;";
							}
							if(arr[11]!="0.00"){
								html +="<br><strong>舱内噪声：</strong>"+arr[11] + "/dB&nbsp;&nbsp;";
							}
							if(arr[12]!="0.00"){
								html +="<strong>显示温度：</strong>"+arr[12] + "℃&nbsp;&nbsp;";
							}
							var numCount = 0;
							var htmlTable = "";
							if(arr.length>13){
								$(".tableHeadTmp").val(arr[13]+"#@"+arr[14]+"#@"+arr[15]+"#@"+arr[16]+"#@"+
										arr[17]+"#@"+arr[18]+"#@"+arr[19]+"#@"+arr[20]+"#@"+arr[21]+"#@");
								if(arr[13]!=" "){
									htmlTable +="<strong>委托单位：</strong>"+arr[13] + "&nbsp;&nbsp;";
									numCount++;
								}
								if(arr[14]!=" "){
									htmlTable +="<strong>器具名称：</strong>"+arr[14] + "&nbsp;&nbsp;";
									numCount++;
								}
								if(arr[15]!=" "){
									htmlTable +="<strong>制造厂：</strong>"+arr[15] + "&nbsp;&nbsp;";
									numCount++;
								}
								if(arr[16]!=" "){
									htmlTable +="<strong>型号规格：</strong>"+arr[16] + "&nbsp;&nbsp;";
									numCount++;
								}
								if(numCount==4){
									htmlTable +="<br>";
									numCount = 0;
								}
								if(arr[17]!=" "){
									htmlTable +="<strong>出厂编号：</strong>"+arr[17] + "&nbsp;&nbsp;";
									numCount++;
								}
								if(numCount==4){
									htmlTable +="<br>";
									numCount = 0;
								}
								if(arr[21]!=" "){
									htmlTable +="<strong>校准日期：</strong>"+arr[18] + "&nbsp;&nbsp;";
									numCount++;
								}
								if(numCount==4){
									htmlTable +="<br>";
									numCount = 0;
								}
								if(arr[20]!=" "){
									htmlTable +="<strong>地点：</strong>"+arr[19] + "&nbsp;&nbsp;";
									numCount++;
								}
								if(numCount==4){
									htmlTable +="<br>";
									numCount = 0;
								}
								if(arr[18]!=" "){
									htmlTable +="<strong>温度：</strong>"+arr[20] + "℃&nbsp;&nbsp;";
									numCount++;
								}
								if(numCount==4){
									htmlTable +="<br>";
									numCount = 0;
								}
								if(arr[19]!=" "){
									htmlTable +="<strong>湿度：</strong>"+arr[21] + "%RH&nbsp;&nbsp;";
									numCount++;
								}
								if(numCount>0){
									htmlTable = "<br><br><strong><h6>记录表基本信息：</h6></strong>" + htmlTable;
									$(".tableHeadDiv").show();
								}else{
									$(".tableHeadDiv").hide();
								}
							}
							html = html + htmlTable;
							$(".pInfo").text("");
							$(".pInfo").append(html);
		             }
				});
				
				$('.box2_4').fadeIn("slow");
			}
		
		});
	</script>

</body>
</html>