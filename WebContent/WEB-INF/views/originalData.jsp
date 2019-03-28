<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script src="css/dist/js/vendor/jquery.min.js"></script>
<script src="css/dist/js/flat-ui.js"></script>
<script src="js/application.js"></script>
<script type="text/javascript" src="js/myJs/originalData.js"></script>
<script type="text/javascript" src="js/myJs/searchOption.js"></script>
<link rel="stylesheet" href="css/upload.css" type="text/css">
<link rel="stylesheet" href="css/myTable.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/dist/css/flat-ui.css">
<link rel="stylesheet" href="css/dist/img/favicon.ico">
<link rel="stylesheet" href="css/windowsStyle.css" type="text/css">
<style>
	img {
	  display: block;
	  margin: 0 auto;
	}
	
	canvas {
		display: block;
		margin:0 auto;
	
	}
	
	.shadow:hover{
		height:30%; 
		box-shadow:0 1px 5px #1abd9d;
		-moz-transition:border ease-in-out 0.5s,box-shadow ease-in-out 0.6s;
		-webkit-transition:border ease-in-out 0.5s,box-shadow ease-in-out 0.6s;
	}
	.row{
		padding-right: 50px
	}
</style>

<head>
<title>婴儿培养箱系统</title>
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header" style="margin-left: -10%">
			<img src="css/NRS.png" class="navbar-brand"><a
				class="navbar-brand" href="#">四川中测辐射科技有限公司</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse" style="margin-right: -8%">
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${!empty sessionScope.newOriginalData}">
					<li><a style="font-size: large; "><strong>表名：${sessionScope.newOriginalData.getOriginalDataName()}</strong></a></li>
				</c:if>
				<c:if test="${!empty sessionScope.detectionId&&!empty sessionScope.beginDate}">
					<li><a style="font-size: large;cursor: pointer; " class="returnLastFigure"><strong>返回数据图表</strong></a></li>
				</c:if>
				<li><a style="font-size: large;cursor: pointer; " class="UIOptionsPop"><strong>记录表布局显示</strong></a></li>
				<li><a class="SearchoriginalData" style="font-size: large;cursor: pointer; "><strong>原始记录表查询</strong></a></li>
				<li><a class="exportFile" style="font-size: large;cursor: pointer; "><strong>导出文件</strong></a></li>
				<li><a class="updateOriginalDataTable" style="font-size: large;cursor: pointer; "><strong>保存修改</strong></a></li>
				<li><a href="index.jsp" style="font-size: large;"><strong>主页</strong></a></li>
			</ul>
		</div>
	</div>
	</nav>
		<c:set value="${ fn:split(sessionScope.newOriginalData.getShowOptions(), '#@') }" var="showOptions" />
	<div class="boxshow" style="margin-top: -5%">
		<div class="box2" >
			<div class="login5"></div>
			<div class="login51">
				<div style="display: none">
				<div style="padding: 100px 100px 10px;display: none " id="searchItems" >
					<form class="bs-example bs-example-form" role="form" id="historyInfoSearch" action="historyInfoSearch" method="post">
						<div class="input-group input-group-lg">
							<span class="input-group-addon">检测时间</span>
							<input type="text" class="form-control" id="beginDate" name="beginDate" value="${sessionScope.beginDate}"
								placeholder="时间格式为：yyyy-MM-dd    默认当日......">
						</div>
						<br><br>
						<div class="input-group input-group-lg" style="display: none">
							<span class="input-group-addon">检测结束时间</span> 
							<input type="text" class="form-control" id="endDate" name="endDate" value="${sessionScope.beginDate}"
								placeholder="时间格式为：yyyy-MM-dd    默认当日......">
						</div>
						<br><br>
						<input style="display: none" type="text" class="form-control" id="intervalTime" name="intervalTime" >
						<input style="display: none" type="text" class="form-control" id="regionInfo" name="regionInfo" >
						<input style="display: none" id="dataSource" name="dataSource" value="${(sessionScope.dataSource==null||sessionScope.dataSource=='')?'0':sessionScope.dataSource}" >
						<div class="input-group input-group-lg">
							<span class="input-group-addon">检测设备ID</span> 
							<input type="text" class="form-control" id="detectionId" name="detectionId" value="${sessionScope.detectionId}"
								placeholder="请输入检测设备ID，可以只输入关键字段进行查询......">
						</div>
						
						<br> <br>
					</form>
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
				
				<div style="margin-left: 5%" id="searchItems" >
				<br>
					<div class="row">
							<h4 class="text-center">请选择需要显示的选项</h4>
							<br>
						<div style="border-top: 2px solid #1abd9d;border-bottom:  2px solid #1abd9d;">
						<table style="margin-left: 4%;display: block;width: 100%;">
							<tr>
								<td style="width: 26%">
								<label class="checkbox" for="orOne" style="font-size: large;">
					                <input type="checkbox" data-toggle="checkbox" name="checkboxOption" id="orOne" 
					                  <c:out value="${showOptions[0]=='1'?'checked':'' }"/> value="0" data-radiocheck-toggle="radio" >
					                外观、报警功能及电气安全检查
					              </label>
								</td>
								<td style="width: 26%">
								 <label class="checkbox" for="orTwo" style="font-size: large;">
					                <input type="checkbox" data-toggle="checkbox" name="checkboxOption" id="orTwo" 
					                   <c:out value="${showOptions[1]=='1'?'checked':'' }"/>  value="1" data-radiocheck-toggle="radio" >
					                36℃温度校准记录
					              </label>
								</td>
								<td style="width: 26%">
								  <label class="checkbox" for="orThree" style="font-size: large;">
					                <input type="checkbox" data-toggle="checkbox" name="checkboxOption" id="orThree" 
					                  <c:out value="${showOptions[2]=='1'?'checked':'' }"/> value="2" data-radiocheck-toggle="radio" >
					               床垫倾斜时温度均匀度
					              </label>
								</td>
							</tr>
							<tr>
								<td>
								 <label class="checkbox" for="orFour" style="font-size: large;">
					                <input type="checkbox" data-toggle="checkbox" name="checkboxOption" id="orFour" 
					                 <c:out value="${showOptions[3]=='1'?'checked':'' }"/> value="3" data-radiocheck-toggle="radio" >
					               湿度相对偏差
					              </label>
								</td>
								<td>
									 <label class="checkbox" for="orFive" style="font-size: large;">
						                <input type="checkbox" name="checkboxOption" data-toggle="checkbox" value="4" 
						                  <c:out value="${showOptions[4]=='1'?'checked':'' }"/> id="orFive" >
						                婴儿舱内氧分析器示值误差
						              </label>
								</td>
								<td>
									 <label class="checkbox" for="orsix" style="font-size: large;">
						                <input type="checkbox" name="checkboxOption" data-toggle="checkbox" value="5" 
						                  <c:out value="${showOptions[5]=='1'?'checked':'' }"/> id="orsix" >
						                婴儿舱内的噪声
						              </label>
								</td>
							</tr>
							<tr>
								<td>
									   <label class="checkbox" for="orSeven" style="font-size: large;">
							                <input type="checkbox" name="checkboxOption" data-toggle="checkbox" 
							                 <c:out value="${showOptions[6]=='1'?'checked':'' }"/> value="6" id="orSeven" >
							                报警器报警噪声
							            </label>
								</td>
								<td>
									   <label class="checkbox" for="orEight" style="font-size: large;">
						                <input type="checkbox" name="checkboxOption" data-toggle="checkbox" value="7" 
						                  <c:out value="${showOptions[7]=='1'?'checked':'' }"/> id="orEight" >
						                备注
						              </label>
								</td>
								<td>
									   <label class="checkbox" for="orNine" style="font-size: large;">
						                <input type="checkbox" name="checkboxOption" data-toggle="checkbox" value="8" 
						                  <c:out value="${showOptions[8]=='1'?'checked':'' }"/> id="orNine" >
						                等级(不确定度)
						              </label>
								</td>
							</tr>
						</table>
						</div>
						<br>
						<div class="btn-group btn-group-lg" style="margin-left: 40%;margin-top: 50px;margin-bottom: 20px">
							<button type="button" class="btn btn-info UIOptions">确定</button>
							<button type="button" class="btn btn-warning cancelUIOptions">关闭</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="boxshow_3" style="margin-top: -5%">
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
										<label class="sr-only" for="originalBeginDate">
											</label> <input type="email" class="form-control"
											id="originalBeginDate" placeholder="开始时间YYYY-MM-DD">
									</div>
									
									<div class="form-group">
										<label class="sr-only" for="originalEndDate"></label> <input
											type="text" class="form-control" id="originalEndDate"
											placeholder="结束时间YYYY-MM-DD">
									</div>
									<div class="form-group">
										<label class="sr-only" for="originalName"></label> <input
											type="text" class="form-control" id="originalName"
											placeholder="输入表名">
									</div>
									<button type="button" class="btn btn-info searchOriginalList">查询</button>
									<button type="button" class="btn btn-danger newOriginal">创建原始记录表</button>
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
	
	<div class="boxshow_6" style="margin-top: -5%">
		<div class="box2_6">
			<div class="login5_6"></div>
			<div class="login51_6">
				<div style="width: 100%;height: 100%;background-color: #4D4D4D">
					<br>
					
					<div style="">
						<canvas id="c"></canvas>
					    <script src='js/jquery.js'></script>
					    <script src="js/index.js"></script>	
					</div>
					
				</div>

			</div>
		</div>
	</div>
	<div class="boxshow_5" style="margin-top: -5%">
		<div class="box2_5">
			<div class="login5_5"></div>
			<div class="login51_5">
				<div style="margin-left: 5%" id="selectIcon">
					<br>
					<div class="row exportFileDiv">
						<div style="margin-left: 5%" class="row">
							<h4 style="margin-left: 29%">请选择文件要导出的类型</h4>
							<div class="row" style="border-top:  2px solid #1abd9d;">
								<br>
								<div style="display: block;width: 100px;height:100px;float: left;margin-left: 46%">
									<img src="css/excel.png" class="excelFile" width="100%" height="100%" style="cursor: pointer;">
								</div>
								<div style="display: none;width: 100px;height:100px;float: left;margin-left: 20%">
									<img src="css/reader.png" class="pdfFile" width="100%" height="100%" style="cursor: pointer;">
								</div>
							</div>
							
							<br>
						</div>
					</div>
					<div class="row setExportFileName" style="display: none">
						<h4 style="margin-left: 32%" class="exportFileH4">请输入导出的excel名称</h4>
						<br>
						<div class="row" style="border-top:  2px solid #1abd9d;width: 80%;margin-left: 10%">
						  <br>
							<div style="width: 85%;display: block;">
								<div class="col-sm-10">
									<input class="form-control" type="text"
										id="exportFileName" placeholder="请输入文件名，未输入将以时间为名,禁止包含符号'_' ">
								</div>
							</div>
							<div class="btn-group btn-group-lg" style="margin-left: 5%; display: block;float: left;">
								<button type="button" class="btn btn-info exportFileNameCreate">创建</button>
								<button type="button"
									class="btn btn-danger returnExportFileDiv">返回</button>
							</div>
						    <br>
					    </div>
					</div>
					<input style="display: none;" class="fileInput">
					<div class="row" style="margin-top: 50px; margin-bottom: 20px">
						 <div class="col-xs-4"></div>
						 <div class="col-xs-4">
					        <a class="btn btn-block btn-lg btn-warning closeExportFile">关闭框口</a>
					     </div>
					</div>
					<br>
				</div>
			</div>
		</div>
	</div>
	<input type="text" style="display:none " id = "originalId" value="${sessionScope.newOriginalData.getOriginalDataId()}" >
	<div class="container"
		style="margin-top: 100px; width: 65%; min-width: 1000px">

		<div class="row" >
		<div style="display: block;">
			<h1 class="text-center" >
				<strong>婴儿培养箱校准记录</strong>
			</h1>
		</div>
		<c:set value="${ fn:split(sessionScope.newOriginalData.getAutograph(), '#@') }" var="TableNine" />
		<div style="border-bottom: 2px solid #000;display: block;width: 25%;float: right;">
			<input style="width: 100%;border:none;text-align: center;" class="serialId" placeholder="证书编号"  
			value="${(TableNine[2]==' '||TableNine[2]==null)?'':TableNine[2]}">
		</div>
		</div>

		<div class="row tableTitle shadow">
    	<br></br>
    	
    	<c:set value="${ fn:split(sessionScope.newOriginalData.getTitleInfo(), '#@') }" var="titleInfo" />
    		<table class = 'titleTable tableHead' border="1" style="word-break: break-all; word-wrap: break-word">
    			<tr >
    				<td ><strong>委托单位</strong></td>
    				<td colspan = '5' ><input type="text" value="${titleInfo[0]}"></td>
    			</tr>
    			<tr>
    				<td><strong>型号规格</strong></td>
    				<td colspan = '2' style="width: 45% "><input type="text" value="${titleInfo[3]}"></td>
    				<td><strong>校准日期</strong></td>
    				<td colspan = '2'><input type="text" value="${titleInfo[5]}" style="word-break:break-all"></td>
    			</tr>
    			<tr>
    				<td><strong>制造厂</strong></td>
    				<td colspan = '2'><input type="text" value="${titleInfo[2]}"></td>
    				<td><strong>检定环境</strong></td>
    				<td><input type="text" style="width: 80%;" value="${titleInfo[7]}">℃</td>
    				<td><input type="text" style="width: 70%;" value="${titleInfo[8]}">%RH</td>
    			</tr>
    			<tr>
    			<td><strong>出厂编号</strong></td>
    				<td colspan = '2'><input type="text" value="${titleInfo[4]}"></td>
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
    		 <br>
    	</div>
    
		<div class="row tableOne shadow showOptionUI" style="display:${showOptions[0]=='0'?'none':'' }">
			<br>
			
			<h3 style="margin-left: 100px">
				<strong>外观、报警功能及电气安全检查</strong>
				
			</h3>
			<p class="text-right">
				<a class="btn btn-info resetRadio">清空选项</a>
			</p>
			<c:set value="${ fn:split(sessionScope.newOriginalData.getTableOne(),'#@') }" var="tableOne" />
			<table class="oneTable">
				<tr>
					<td colspan="2" height="50px" width="40%" class="bordertd"><strong>检查项目</strong></td>
					<td colspan="2" width="50%" width="40%" class="bordertd"><strong>测&nbsp;量&nbsp;值</strong></td>
				</tr>
				<tr>
					<td colspan="2" height="50px" class="bordertd"><strong>&nbsp;外&nbsp;&nbsp;观&nbsp;</strong></td>
					<td colspan="2" class="bordertd"><input type="radio"
						name="outLine" value="0" style="width: 18px; height: 20px" 
							<c:out value="${tableOne[0]=='0'?'checked':'' }"/>/>符合
						&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" 
						name="outLine" value="1" style="width: 18px; height: 20px" 
							<c:out value="${tableOne[0]=='1'?'checked':'' }"/>/>不符合
					</td>
				</tr>
				<tr style="display: none">
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
				<tr style="display: none">
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
				<tr style="display: none">
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
			 <br>
		</div>
		<div class="row tableTwo shadow table32And36 " >
			<br>
			<c:set value="${ fn:split(sessionScope.newOriginalData.getTableTwo(), '#@') }" var="TableTwo" />
			<h3 style="margin-left: 100px">
				<strong>温度校准记录</strong>
			</h3>
			<p class="text-right">
				<strong>单位：℃</strong>
			</p>
			<table class="oneTable 32And36One" style="display:${showOptions[1]=='0'?'none':'' } ">
				<tr>
					<td rowspan="3" width="80px" class="bordertd"><strong>次数</strong></td>
					<td colspan="3" height="40px" class="bordertd"><strong>控制温度</strong></td>
					<td colspan="3" class="bordertd"><strong>32</strong></td>
					<td colspan="3" class="bordertd"><strong>控制温度</strong></td>
					<td colspan="3"><strong>36</strong></td>
				</tr>
				<tr>
					<td rowspan="2" width="80px" class="bordertd"><strong>显示温度</strong></td>
					<td colspan="5" height="40px" class="bordertd"><strong>温度</strong></td>
					<td rowspan="2" width="80px" class="bordertd"><strong>显示温度</strong></td>
					<td colspan="5"><strong>温度</strong></td>
				</tr>
				<tr>
					<td height="40px" class="bordertd"><strong>A</strong></td>
					<td class="bordertd"><strong>B</strong></td>
					<td class="bordertd"><strong>C</strong></td>
					<td class="bordertd"><strong>D</strong></td>
					<td class="bordertd"><strong>E</strong></td>
					<td class="bordertd"><strong>A</strong></td>
					<td class="bordertd"><strong>B</strong></td>
					<td class="bordertd"><strong>C</strong></td>
					<td class="bordertd"><strong>D</strong></td>
					<td class="bordertd"><strong>E</strong></td>
				</tr>
				<c:forEach var="i" begin="1" end="15">
					<tr class="T36And32Tr">
						<td height="30px" class="bordertd"><strong>${i}</strong></td>
						<td class="bordertd T32CDIST" width="7.69%"><input type="text" class="T32CDISTInputText inputTableTwo" value="${TableTwo[(i-1)*12]}"></td>
						<td class="bordertd T32CAT" width="7.69%">${TableTwo[(i-1)*12+1]}</td>
						<td class="bordertd T32CBT" width="7.69%">${TableTwo[(i-1)*12+2]}</td>
						<td class="bordertd T32CCT" width="7.69%">${TableTwo[(i-1)*12+3]}</td>
						<td class="bordertd T32CDT" width="7.69%">${TableTwo[(i-1)*12+4]}</td>
						<td class="bordertd T32CET" width="7.69%">${TableTwo[(i-1)*12+5]}</td>
						<td class="bordertd T36DIST" width="7.69%"><input type="text" class="T36DISTInputText inputTableTwo" value="${TableTwo[(i-1)*12+6]}"></td>
						<td class="bordertd T36AT" width="7.69%">${TableTwo[(i-1)*12+7]}</td>
						<td class="bordertd T36BT" width="7.69%">${TableTwo[(i-1)*12+8]}</td>
						<td class="bordertd T36CT" width="7.69%">${TableTwo[(i-1)*12+9]}</td>
						<td class="bordertd T36DT" width="7.69%">${TableTwo[(i-1)*12+10]}</td>
						<td class="bordertd T36ET" width="7.69%">${TableTwo[(i-1)*12+11]}</td>
					</tr>
				</c:forEach>
				<tr>
					<td height="30px" class="bordertd"><strong>修正值</strong></td>
					<td class="Slash">/</td>
					<td class="bordertd T32CRevise"><input type="text" value="${TableTwo[180]}"></td>
					<td class="bordertd T32CRevise"><input type="text" value="${TableTwo[181]}"></td>
					<td class="bordertd T32CRevise"><input type="text" value="${TableTwo[182]}"></td>
					<td class="bordertd T32CRevise"><input type="text" value="${TableTwo[183]}"></td>
					<td class="bordertd T32CRevise"><input type="text" value="${TableTwo[184]}"></td>
					<td class="Slash">/</td>
					<td class="bordertd T36Revise"><input type="text" value="${TableTwo[185]}"></td>
					<td class="bordertd T36Revise"><input type="text" value="${TableTwo[186]}"></td>
					<td class="bordertd T36Revise"><input type="text" value="${TableTwo[187]}"></td>
					<td class="bordertd T36Revise"><input type="text" value="${TableTwo[188]}"></td>
					<td class="bordertd T36Revise"><input type="text" value="${TableTwo[189]}"></td>
				</tr>
				<tr>
					<td height="30px" class="bordertd"><strong>平均值</strong></td>
					<td class="bordertd T32CAVG AVGOne">${TableTwo[190]}</td>
					<td class="bordertd T32CAVG AVGATT32">${TableTwo[191]}</td>
					<td class="bordertd T32CAVG">${TableTwo[192]}</td>
					<td class="bordertd T32CAVG">${TableTwo[193]}</td>
					<td class="bordertd T32CAVG">${TableTwo[194]}</td>
					<td class="bordertd T32CAVG">${TableTwo[195]}</td>
					<td class="bordertd T36AVG AVGTwo">${TableTwo[196]}</td>
					<td class="bordertd T36AVG AVGATT36">${TableTwo[197]}</td>
					<td class="bordertd T36AVG">${TableTwo[198]}</td>
					<td class="bordertd T36AVG">${TableTwo[199]}</td>
					<td class="bordertd T36AVG">${TableTwo[200]}</td>
					<td class="bordertd T36AVG">${TableTwo[201]}</td>
				</tr>
				<tr>
					<td colspan="2" rowspan="2" class="bordertd"><strong>温度偏差</strong></td>
					<td colspan="2" height="30px" class="bordertd"><strong>32</strong></td>
					<td colspan="3" class="bordertd T32deviation">${TableTwo[202]}</td>
					<td colspan="2" rowspan="2" class="bordertd"><strong>温度波动度</strong></td>
					<td colspan="2" height="30px" class="bordertd"><strong>32</strong></td>
					<td colspan="2" class="bordertd T32Wave">${TableTwo[203]}</td>
				</tr>
				<tr>
					<td colspan="2" height="30px" class="bordertd"><strong>36</strong></td>
					<td colspan="3" class="bordertd T36deviation">${TableTwo[204]}</td>
					<td colspan="2" class="bordertd"><strong>36</strong></td>
					<td colspan="2" class="bordertd T36Wave">${TableTwo[205]}</td>
				</tr>
				<tr>
					<td colspan="2" rowspan="2" class="bordertd"><strong>温度均匀度</strong></td>
					<td colspan="2" height="30px" class="bordertd"><strong>32</strong></td>
					<td colspan="3" class="bordertd UniformityT32Avg">${TableTwo[206]}</td>
					<td colspan="4" rowspan="2" class="bordertd"><strong>平均培养箱温度与<br>控制温度之差
					</strong></td>
					<td colspan="2" rowspan="2"  height="30px" class="bordertd tableTwoSaveOne">${TableTwo[208]}</td>
				</tr>
				<tr>
					<td colspan="2" height="40px" class="bordertd"><strong>36</strong></td>
					<td colspan="3" class="bordertd UniformityT36Avg">${TableTwo[207]}</td>
				</tr>
				<tr>
					<td colspan="4" rowspan="2" height="60px" class="bordertd"><strong>调整控制温度后，<br>测得培养箱温度最大值
					</strong></td>
					<td colspan="3" rowspan="2" class="bordertd tableTwoSaveTwo">${TableTwo[209]}</td>
					<td colspan="3" rowspan="2" class="bordertd"><strong>温度超调量</strong></td>
					<td colspan="3" rowspan="2" class="bordertd tableTwoSaveThree">${TableTwo[210]}</td>
				</tr>
				
			</table>
			<input class="optionInputtmp" value="${showOptions[1]}" style="display: none">
			<table class="oneTable 32And36Two" style="display:${(showOptions[1]=='1'||showOptions[1]==null)?'none':'' }">
				<tr>
					<td rowspan="3" width="80px" class="bordertd"><strong>次数</strong></td>
					<td colspan="3" height="40px" class="bordertd"><strong>控制温度</strong></td>
					<td colspan="3" class="bordertd"><strong>32</strong></td>
				</tr>
				<tr>
					<td rowspan="2" width="80px" class="bordertd"><strong>显示温度</strong></td>
					<td colspan="5" height="40px" class="bordertd"><strong>温度</strong></td>
				</tr>
				<tr>
					<td height="40px" class="bordertd"><strong>A</strong></td>
					<td class="bordertd"><strong>B</strong></td>
					<td class="bordertd"><strong>C</strong></td>
					<td class="bordertd"><strong>D</strong></td>
					<td class="bordertd"><strong>E</strong></td>
				</tr>
				<c:forEach var="i" begin="1" end="15">
					<tr class="T36And32Tr2">
						<td height="30px" class="bordertd"><strong>${i}</strong></td>
						<td class="bordertd T32CDIST2" width="15.3%"><input type="text" class="T32CDISTInputText2 inputTableTwo2" value="${TableTwo[(i-1)*12]}"></td>
						<td class="bordertd T32CAT2" width="15.3%">${TableTwo[(i-1)*12+1]}</td>
						<td class="bordertd T32CBT2" width="15.3%">${TableTwo[(i-1)*12+2]}</td>
						<td class="bordertd T32CCT2" width="15.3%">${TableTwo[(i-1)*12+3]}</td>
						<td class="bordertd T32CDT2" width="15.3%">${TableTwo[(i-1)*12+4]}</td>
						<td class="bordertd T32CET2" width="15.3%">${TableTwo[(i-1)*12+5]}</td>
					</tr>
				</c:forEach>
				<tr>
					<td height="30px" class="bordertd"><strong>修正值</strong></td>
					<td class="Slash">/</td>
					<td class="bordertd T32CRevise2"><input type="text" value="${TableTwo[180]}"></td>
					<td class="bordertd T32CRevise2"><input type="text" value="${TableTwo[181]}"></td>
					<td class="bordertd T32CRevise2"><input type="text" value="${TableTwo[182]}"></td>
					<td class="bordertd T32CRevise2"><input type="text" value="${TableTwo[183]}"></td>
					<td class="bordertd T32CRevise2"><input type="text" value="${TableTwo[184]}"></td>
				</tr>
				<tr>
					<td height="30px" class="bordertd"><strong>平均值</strong></td>
					<td class="bordertd T32CAVG2 AVGOne2">${TableTwo[190]}</td>
					<td class="bordertd T32CAVG2 AVGATT322">${TableTwo[191]}</td>
					<td class="bordertd T32CAVG2">${TableTwo[192]}</td>
					<td class="bordertd T32CAVG2">${TableTwo[193]}</td>
					<td class="bordertd T32CAVG2">${TableTwo[194]}</td>
					<td class="bordertd T32CAVG2">${TableTwo[195]}</td>
				</tr>
				<tr>
					<td class="bordertd"><strong>温度偏差</strong></td>
					<td class="bordertd T32deviation2">${TableTwo[202]}</td>
					<td class="bordertd"><strong>温度波动度</strong></td>
					<td class="bordertd T32Wave2">${TableTwo[203]}</td>
					<td class="bordertd"><strong>温度均匀度</strong></td>
					<td colspan="2" class="bordertd UniformityT32Avg2">${TableTwo[206]}</td>
				</tr>
			</table>
			
			
			<input type="text" class="T36Time" style="display: none" value="${TableTwo[212]}">
			<input type="text" class="T32Time" style="display: none" value="${TableTwo[211]}">
			 <br>
		</div>
		<div class="row shadow showOptionUI" style="display:${showOptions[2]=='0'?'none':'' } ">
			<br>
			<h3 style="margin-left: 100px">
				<strong>床垫倾斜时温度均匀度</strong>
			</h3>
			<p class="text-right">
				<strong>单位：℃</strong>
			</p>
			<br>
			<table class="oneTable" id="T32LR">
				<tr>
					<td rowspan="2" height="80px" class="bordertd" width="20%"><strong>次数</strong>
					</td>
					<td colspan="2" height="40px" class="bordertd" width="32%"><strong>控制温度</strong></td>
					<td colspan="3" class="bordertd"><strong>32</strong></td>
				</tr>
				<tr>
					<td class="bordertd"><strong>A</strong></td>
					<td class="bordertd"><strong>B</strong></td>
					<td class="bordertd"><strong>C</strong></td>
					<td class="bordertd"><strong>D</strong></td>
					<td class="bordertd"><strong>E</strong></td>
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
						<td height="30px" class="bordertd"><strong>修正值</strong></td>
						<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[90]}"></td>
						<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[91]}"></td>
						<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[92]}"></td>
						<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[93]}"></td>
						<td class="bordertd T32LRRevise"><input type="text"  value=" ${TableThree[94]}"></td>
				</tr>
				<tr>
						<td height="30px" class="bordertd"><strong>平均值</strong></td>
						<td class="bordertd T32LRAVG">${TableThree[95]}</td>
						<td class="bordertd T32LRAVG">${TableThree[96]}</td>
						<td class="bordertd T32LRAVG">${TableThree[97]}</td>
						<td class="bordertd T32LRAVG">${TableThree[98]}</td>
						<td class="bordertd T32LRAVG">${TableThree[99]}</td>
				</tr>
				<tr>
					<td height="30px" class="bordertd" colspan="2" ><strong>床垫倾斜时温度均匀度</strong></td>
					<td class="bordertd T32LRUniformity">${TableThree[100]}</td>
					<td class="bordertd T32LRUniformity">${TableThree[101]}</td>
					<td class="bordertd T32LRUniformity">${TableThree[102]}</td>
					<td class="bordertd T32LRUniformity">${TableThree[103]}</td>
				</tr>
			</table>
			 <br>
		</div>
		<div class="row shadow showOptionUI" style="display:${showOptions[3]=='0'?'none':'' } ">
			<br>
			<h3 style="margin-left: 100px">
				<strong>湿度相对偏差</strong>
			</h3>
			<br>
			<table class="oneTable">
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
			 <br>
		</div>
		<div class="row shadow showOptionUI" style="display:${showOptions[4]=='0'?'none':'' } ">
			<br>
			<h3 style="margin-left: 100px">
				<strong>婴儿舱内氧分析器示值误差</strong>
			</h3>
			<br>
			<table class="oneTable">
				<tr>
					<td width="20%" height="40px" class="bordertd"><strong>次数</strong></td>
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
			 <br>
		</div>
		<div class="row shadow showOptionUI" style="display:${showOptions[5]=='0'?'none':'' } " >
			<br>
			<h3 style="margin-left: 100px">
				<strong>婴儿舱内的噪声</strong>
			</h3>
			<br>
			<table class="oneTable" >
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
			<br>
		</div>
		<div class="row shadow showOptionUI" style="display:${showOptions[6]=='0'?'none':'' } ">
			<br>
			<h3 style="margin-left: 100px">
				<strong>报警器报警噪声</strong>
			</h3>
			<br>
			<c:set value="${ fn:split(sessionScope.newOriginalData.getTableSeven(), '#@') }" var="TableSeven" />
			<table class="oneTable" >
				<tr>
					<td width="20%" height="40px" class="bordertd"><strong>婴儿舱内的噪声/dB</strong></td>
					<td width="10%" class="bordertd"><strong>1</strong></td>
					<td width="10%" class="bordertd"><input type="text" class="NoiwarinInput NoiwarinOne" value="${TableSeven[0]}"></td>
					<td width="10%" class="bordertd"><strong>2</strong></td>
					<td width="10%" class="bordertd"><input type="text" class="NoiwarinInput NoiwarinTwo" value="${TableSeven[1]}"></td>
					<td width="10%" class="bordertd"><strong>3</strong></td>
					<td width="10%" class="bordertd"><input type="text" class="NoiwarinInput NoiwarinThree" value="${TableSeven[2]}"></td>
					<td class="bordertd"><strong>平均值</strong></td>
					<td width="10%" class="bordertd NoiwarinFour">${TableSeven[3]}</td>
				</tr>
				<tr>
					<td width="20%" height="40px" class="bordertd"><strong>箱外噪声/dB</strong></td>
					<td width="10%" class="bordertd"><strong>1</strong></td>
					<td width="10%" class="bordertd"><input type="text"  class="outNoiInput NoiwarinFive" value="${TableSeven[4]}"></td>
					<td width="10%" class="bordertd"><strong>2</strong></td>
					<td width="10%" class="bordertd"><input type="text" class="outNoiInput NoiwarinSix" value="${TableSeven[5]}"></td>
					<td width="10%" class="bordertd"><strong>3</strong></td>
					<td width="10%" class="bordertd"><input type="text" class="outNoiInput NoiwarinSeven" value="${TableSeven[6]}"></td>
					<td class="bordertd"><strong>平均值</strong></td>
					<td width="10%" class="bordertd NoiwarinEight">${TableSeven[7]}</td>
				</tr>
			</table>
			 <br>	
		</div>
		<div class="shadow remark" style="display:${showOptions[7]=='0'?'none':'' } ">
		<br>
			<h3 style="margin-left: 100px">
				<strong>备注</strong>
			</h3>
			<br>
			<div class="form-group" style="width: 94%;margin-left: 3%">
				<textarea class="form-control textareaRemark" rows="3">${sessionScope.newOriginalData.getTableEight()}</textarea>
			</div>
		</div>
		<div class="row shadow criterionInfo" style="display:${showOptions[8]=='0'?'none':'' }">
			<h3 style="margin-left: 100px">
				<strong>等级(不确定度)</strong>
			</h3>
		<br>
			<table class = 'titleTable' border="1" style="border-color:1px solid black;margin-left:5%;width: 95%">
    			<tr>
    				<td class='underLine' height="40px" style="width: 6%"><strong>等级(不确定度)</strong></td>
    				<td class='underLine' colspan = '5'><input type="text" class="uncertainty" value="${titleInfo[14]}"></td>
    			</tr>
    		</table>
    		<br>
		</div>
		<br>
		<div class="row" style="margin-left: 5%">
			
			<table class="titleTable" style="width: 80%">
				<tr>
					<td width="15%"></td>
					<td width="10%"><strong>校准员</strong></td>
					<td width="10%" class="underLine"><input type="text" class="signOne" value="${TableNine[0]}"></td>
					<td width="10%"></td>
					<td width="10%"><strong>核验员</strong></td>
					<td width="10%" class="underLine"><input type="text" class="signTwo" value="${TableNine[1]}"></td>
					<td></td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
	</div>

</body>
</html>