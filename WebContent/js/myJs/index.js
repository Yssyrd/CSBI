$(document).ready(function(){
	var endDate = $("#endDate").val();
	var beginDate = $("#beginDate").val();
	var detectionId = $("#detectionId").val();
	var intervalFlag = setInterval(function() { commStatus() } , 900);
	$('.criterionInfo').click(function(){
		$('.updateCheck_6').hide();
		$('.previewTitleDiv_6').hide();
		$('.box2_6').fadeIn("slow");
		
	});
	
	$(".syncData").click(function() {
		$("#sync_modal").modal();
		$(".syncH4").html("数据信息检索正在同步中，需要一定的时间，请耐心等待。。。。。");
		$.ajax({
			type: "post",
			url: "syncData",
			dataType: "json",
			success: function(data){
				if(data=="ok"){
				
					$(".syncH4").html("数据更新成功！对话框2s后自动关闭！");
					
					setTimeout("$('#sync_modal').modal('hide');", 2000);
				}
			}
		});
		
	});
	
	$('.closeBox_6').click(function(){
		
		$('.box2_6').fadeOut("slow");
		
	});
	
	$(".deleteConfirm").click(function() {
		
		var arr = [];
		if(confirm("确认要删除吗？")==true){
			 
			 var $tr = $(".deleteDataResult tbody tr");
			 $tr.each(function(){//把所有被选中的复选框的值存入数组
				 if($(this).find("input").is(":checked")){
					 $td = $(this).find("td");
					 var json = {
							 id : $(this).find("input").val(),
							 date : $td.eq(2).html(),
							 dId : $td.eq(3).html(),
							 source : $td.eq(5).html()
					 };
					 arr.push(json);
				 }
			 });
			 
			 $.ajax({
					type: "post",
					url: "deleteData",
					dataType: "json",
					data : {
						ds : JSON.stringify(arr)
					},
					success: function(data){
						getAllDateAndIds();
						alert("共删除"+data+"条数据！");
					}
				});
		}
		
	});
	
	$(".restartComm").click(function() {
		$.ajax({
			type: "post",
			url: "restartComm",
			dataType: "json",
			success: function(data){
				if(data=="ok"){
					commStatus();
					alert("正在重启中，请稍后查询状态！");
				}
			}
		});
	});
	
	$(".deleteData").click(function() {
		$("#delete_modal").modal();
		
		getAllDateAndIds();
		
	});
	
	$('.deleteDataResult').delegate('tr', 'click', function() {
		
		var $check = $(this).find("input");
		
		if($check.is(":checked")){
			$check.prop("checked",false);
		}else{
			$check.prop("checked",true);
		}
	});
	
	$(".searchAllDateAndId").click(function() {
		getAllDateAndIds();
	});
	
	$('.baseInput_6').click(function(){
		
		$('#originalTableBtn_6').html('标准信息录入');
		$('.originalTableTitleListDiv_6').hide();
		$(".previewTitleDiv_6").hide();
		$('.tableHeadInfoInput_6').fadeIn();
	});

	$('.inputSearch_6').click(function(){
		
		$('#originalTableBtn_6').html('标准信息查询');
		$('.tableHeadInfoInput_6').hide();
		$(".previewTitleDiv_6").hide();
		$('.originalTableTitleListDiv_6').fadeIn("slow");
		
		$.ajax({
			type: "post",
			url: "getCriterionInfoList",
			dataType: "json",
			success: function(data){
				if(data.length>0){
					$("#originalTitleInfoList_6 tr:gt(0)").remove();
					var str = "";
					for(var i = 0; i<data.length; i++){
						str += "<tr><td>" + data[i].date + "</td><td>" 
							+ data[i].name + "</td><td>" 
							+ data[i].certificateId + "</td><td><strong><a class='previewTitle_6' style = 'cursor:pointer;'>预览" +
									"</a>&nbsp;&nbsp;<a  class='deleteTitle_6' style = 'cursor:pointer;'>删除</a></strong></td>"
							+ "<td style = 'display:none'>" + data[i].id + "</td>" 
							+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
					}
					$("#originalTitleInfoList_6").append(str);
				}else{
					alert("当前尚未录入标准信息！");
					$('#originalTableBtn_6').html('标准信息录入');
					$('.originalTableTitleListDiv_6').hide();
					$(".previewTitleDiv_6").hide();
					$('.tableHeadInfoInput_6').fadeIn();
				}
			}
		});
		
	});
	
	$('.submitOriginalTitleInfo_6').click(function(){
		
		if($('.ctrId').val().trim==""){
			alert("标准编号不能为空！");
		}else{
			
			$.ajax({
				type: "post",
				url: "criterionCreate",
				data: {
					ctrName:$('.ctrName').val(),
					ctrFactory:$('.ctrFactory').val(),
					ctrVersion:$('.ctrVersion').val(),
					ctrId:$('.ctrId').val(),
					ctrLevel:$('.ctrLevel').val(),
					certificateId:$('.certificateId').val(),
					validTimeInfo:$('.validTimeInfo').val()
				},
				dataType: "json",
				success: function(){
					$('.ctrName').val("婴儿培养箱测量仪");
					$('.ctrFactory').val("四川中测辐射科技有限公司");
					$('.ctrVersion').val("NT7300");
					$('.ctrId').val("");
					$('.ctrLevel').val("");
					$('.certificateId').val("");
					$('.validTimeInfo').val("");
					$('#originalTableBtn_6').html('标准信息查询');
					$('.tableHeadInfoInput_6').hide();
					$(".previewTitleDiv_6").hide();
					$('.originalTableTitleListDiv_6').fadeIn("slow");
					
					$.ajax({
						type: "post",
						url: "getCriterionInfoList",
						dataType: "json",
						success: function(data){
							if(data.length>0){
								$("#originalTitleInfoList_6 tr:gt(0)").remove();
								var str = "";
								for(var i = 0; i<data.length; i++){
									str += "<tr><td>" + data[i].date + "</td><td>" 
										+ data[i].name + "</td><td>" 
										+ data[i].certificateId + "</td><td><strong><a class='previewTitle_6' style = 'cursor:pointer;'>预览" +
												"</a>&nbsp;&nbsp;<a  class='deleteTitle_6' style = 'cursor:pointer;'>删除</a></strong></td>"
										+ "<td style = 'display:none'>" + data[i].id + "</td>" 
										+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
								}
								$("#originalTitleInfoList_6").append(str);
							}else{
								alert("当前尚未录入标准信息！");
								$('#originalTableBtn_6').html('标准信息录入');
								$('.originalTableTitleListDiv_6').hide();
								$(".previewTitleDiv_6").hide();
								$('.tableHeadInfoInput_6').fadeIn();
							}
						}
					});
				}
			});
		}
		
	});
	
	$('.ctrUpdate').click(function(){
		var info = $('.criterionTable').find(':text');
		if($(".idTmp_6").val().trim()==""){
			alert("标准信息序列为空，请重新查询后再修改！");
		}else{
			$.ajax({
				type: "post",
				url: "criterionUpdate",
				data: {
					id: $(".idTmp_6").val(),
					ctrName:info.eq(0).val(),
					ctrFactory:info.eq(1).val(),
					ctrVersion:info.eq(2).val(),
					ctrId:info.eq(3).val(),
					ctrLevel:info.eq(4).val(),
					certificateId:info.eq(5).val(),
					validTimeInfo:info.eq(6).val()
				},
				dataType: "json",
				success: function(data){
					if(data=="0"){
						$('.updateCheck_6').fadeOut("slow");
						$(".previewTitleDiv_6").fadeOut("slow");
						
						$.ajax({
							type: "post",
							url: "getCriterionInfoList",
							dataType: "json",
							success: function(data){
								if(data.length>0){
									$("#originalTitleInfoList_6 tr:gt(0)").remove();
									var str = "";
									for(var i = 0; i<data.length; i++){
										str += "<tr><td>" + data[i].date + "</td><td>" 
											+ data[i].name + "</td><td>" 
											+ data[i].certificateId + "</td><td><strong><a class='previewTitle_6' style = 'cursor:pointer;'>预览" +
													"</a>&nbsp;&nbsp;<a  class='deleteTitle_6' style = 'cursor:pointer;'>删除</a></strong></td>"
											+ "<td style = 'display:none'>" + data[i].id + "</td>" 
											+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
									}
									$("#originalTitleInfoList_6").append(str);
								}else{
									alert("当前尚未录入标准信息！");
									$('#originalTableBtn_6').html('标准信息录入');
									$('.originalTableTitleListDiv_6').hide();
									$(".previewTitleDiv_6").hide();
									$('.tableHeadInfoInput_6').fadeIn();
								}
							}
						});
					}else{
						alert("标准信息修改失败！");
					}
				}
			});
		}
		
	});
	
	$("#originalTitleInfoList_6").delegate(".deleteTitle_6", "click", function(){
		var $td=$(this).parents('tr').children('td');
		var tip = "(标准名称："+ $td.eq(1).text() + "  标准编号：" + $td.eq(2).text() + ")";
		if(confirm("确定要删除数据吗？"+tip)){
			$.ajax({
				type: "post",
				url: "criterionInfoDelete",
				data: {id: $td.eq(4).text()},
				dataType: "json",
				success: function(){
					$('.updateCheck_6').fadeOut("slow");
					$(".previewTitleDiv_6").fadeOut("slow");
					
					$.ajax({
						type: "post",
						url: "getCriterionInfoList",
						dataType: "json",
						success: function(data){
							if(data.length>0){
								$("#originalTitleInfoList_6 tr:gt(0)").remove();
								var str = "";
								for(var i = 0; i<data.length; i++){
									str += "<tr><td>" + data[i].date + "</td><td>" 
										+ data[i].name + "</td><td>" 
										+ data[i].certificateId + "</td><td><strong><a class='previewTitle_6' style = 'cursor:pointer;'>预览" +
												"</a>&nbsp;&nbsp;<a  class='deleteTitle_6' style = 'cursor:pointer;'>删除</a></strong></td>"
										+ "<td style = 'display:none'>" + data[i].id + "</td>" 
										+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
								}
								$("#originalTitleInfoList_6").append(str);
							}else{
								alert("当前尚未录入标准信息！");
								$('#originalTableBtn_6').html('标准信息录入');
								$('.originalTableTitleListDiv_6').hide();
								$(".previewTitleDiv_6").hide();
								$('.tableHeadInfoInput_6').fadeIn();
							}
						}
					});
					alert("原始记录信息删除成功！");
				}
			});
		}
	});
	
	$("#originalTitleInfoList_6").delegate(".previewTitle_6", "click", function(){
		$('.updateCheck_6').hide();
		var $td=$(this).parents('tr').children('td');
		var str = $td.eq(5).text().split("#@");
		var info = $('.criterionTable').find(':text');
    	info.eq(0).val(str[0]);
    	info.eq(1).val(str[1]);
    	info.eq(2).val(str[2]);
    	info.eq(3).val(str[3]);
    	info.eq(4).val(str[4]);
    	info.eq(5).val(str[5]);
    	info.eq(6).val(str[6]);
    	info.eq(7).val(str[7]);
    	$(".idTmp_6").val( $td.eq(4).text());
		$(".previewTitleDiv_6").fadeIn("slow");
	
	});
	
	$('.closePriviewCtr').click(function(){
		$(".previewTitleDiv_6").fadeOut("slow");
	});
	$('.criterionTable input').bind('input propertychange', function() { 
		$(".updateCheck_6").fadeIn("slow");
	}); 
	
	
	
	$('.originalTableHeadInfo').click(function(){
		$('.updateCheck').hide();
		$('.previewTitleDiv').hide();
		$('.box2_4').fadeIn("slow");
		
	});
	
	$('.closeBox_4').click(function(){
		
		$('.box2_4').fadeOut("slow");
		
	});
	
	$('.baseInput').click(function(){
		
		$('#originalTableBtn').html('原始记录基本信息录入');
		$('.originalTableTitleListDiv').hide();
		$(".previewTitleDiv").hide();
		$('.tableHeadInfoInput').fadeIn();
	});

	$('.inputSearch').click(function(){
		
		$('#originalTableBtn').html('原始记录基本信息查询');
		$('.tableHeadInfoInput').hide();
		$(".previewTitleDiv").hide();
		$('.originalTableTitleListDiv').fadeIn("slow");
		
		$.ajax({
			type: "post",
			url: "getTitleInfoList",
			dataType: "json",
			success: function(data){
				if(data.length>0){
					$("#originalTitleInfoList tr:gt(0)").remove();
					var str = "";
					for(var i = 0; i<data.length; i++){
						str += "<tr><td>" + data[i].date + "</td><td>" 
							+ data[i].cpyInfo + "</td><td>" 
							+ data[i].detectionId + "</td><td><strong><a class='previewTitle' style = 'cursor:pointer;'>预览" +
									"</a>&nbsp;&nbsp;<a  class='deleteTitle' style = 'cursor:pointer;'>删除</a></strong></td>"
							+ "<td style = 'display:none'>" + data[i].id + "</td>" 
							+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
					}
					$("#originalTitleInfoList").append(str);
				}else{
					alert("当前尚未录入原始记录基本信息！");
					$('#originalTableBtn').html('原始记录基本信息录入');
					$('.originalTableTitleListDiv').hide();
					$(".previewTitleDiv").hide();
					$('.tableHeadInfoInput').fadeIn();
				}
			}
		});
		
	});
	
	$('.submitOriginalTitleInfo').click(function(){
		
		if($('.cpyInfoInput').val().trim==""||$('.tableInfo_DetectoinId').val().trim()==""){
			alert("委托单位和检测仪器编号不能为空！");
		}else{
			var str = "";
			str = $('.cpyInfoInput').val() + "#@"
				+ $('.appliancesInfoInput').val() + "#@"
				+ $('.manufacturerInfoInput').val() + "#@"
				+ $('.modelInfoInput').val() + "#@"
				+ $('.productionInfoInput').val() + "#@"
				+ $('.calibrationDate').val() + "#@"
				+ $('.regionInfo').val() + "#@"
				+ $('.tempInfoInput').val() + "#@"
				+ $('.humidityInfoInput').val() + "#@JJF 1260——2010#@";
			
			$.ajax({
				type: "post",
				url: "originalTitleInfoCreate",
				data: {originalName:str, detectionId:$(".tableInfo_DetectoinId").val()},
				dataType: "json",
				success: function(){
					$('.cpyInfoInput').val("");
					$('.tableInfo_DetectoinId').val("");
					$('.appliancesInfoInput').val("");
					$('.manufacturerInfoInput').val("");
					$('.modelInfoInput').val("");
					$('.productionInfoInput').val("");
					$('.tempInfoInput').val("");
					$('.humidityInfoInput').val("");
					$('.regionInfo').val("");
					$('.calibrationDate').val("");
					
					$('#originalTableBtn').html('原始记录基本信息查询');
					$('.tableHeadInfoInput').hide();
					$(".previewTitleDiv").hide();
					$('.originalTableTitleListDiv').fadeIn("slow");
					
					$.ajax({
						type: "post",
						url: "getTitleInfoList",
						dataType: "json",
						success: function(data){
							if(data.length>0){
								$("#originalTitleInfoList tr:gt(0)").remove();
								var str = "";
								for(var i = 0; i<data.length; i++){
									str += "<tr><td>" + data[i].date + "</td><td>" 
										+ data[i].cpyInfo + "</td><td>" 
										+ data[i].detectionId + "</td><td><strong><a class='previewTitle' style = 'cursor:pointer;'>预览" +
												"</a>&nbsp;&nbsp;<a  class='deleteTitle' style = 'cursor:pointer;'>删除</a></strong></td>"
										+ "<td style = 'display:none'>" + data[i].id + "</td>" 
										+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
								}
								$("#originalTitleInfoList").append(str);
							}else{
								alert("当前尚未录入原始记录基本信息！");
								$('#originalTableBtn').html('原始记录基本信息录入');
								$('.originalTableTitleListDiv').hide();
								$(".previewTitleDiv").hide();
								$('.tableHeadInfoInput').fadeIn();
							}
						}
					});
				}
			});
		}
		
	});
	
	$("#originalTitleInfoList").delegate(".previewTitle", "click", function(){
		$('.updateCheck').hide();
		var $td=$(this).parents('tr').children('td');
		var str = $td.eq(5).text().split("#@");
		var info = $('.titleTable').find(':text');
    	info.eq(0).val(str[0]);
    	info.eq(1).val($td.eq(2).text());
    	info.eq(2).val(str[1]);
    	info.eq(3).val(str[2]);
    	info.eq(4).val(str[3]);
    	info.eq(5).val(str[4]);
    	info.eq(6).val(str[5]);
    	info.eq(7).val(str[6]);
    	info.eq(8).val(str[7]);
    	info.eq(9).val(str[8]);
    	$(".idTmp").val( $td.eq(4).text());
		$(".previewTitleDiv").fadeIn("slow");
	
	});
	
	$('.fui-cross').click(function(){
		
		$(".previewTitleDiv").fadeOut("slow");
	});
	
	$("#originalTitleInfoList").delegate(".deleteTitle", "click", function(){
		var $td=$(this).parents('tr').children('td');
		var tip = "(委托单位："+ $td.eq(1).text() + "  仪器编号：" + $td.eq(2).text() + ")";
		if(confirm("确定要删除数据吗？"+tip)){
			$.ajax({
				type: "post",
				url: "originalTitleInfoDelete",
				data: {id: $td.eq(4).text()},
				dataType: "json",
				success: function(){
					$('.updateCheck').fadeOut("slow");
					$(".previewTitleDiv").fadeOut("slow");
					
					$.ajax({
						type: "post",
						url: "getTitleInfoList",
						dataType: "json",
						success: function(data){
							if(data.length>0){
								$("#originalTitleInfoList tr:gt(0)").remove();
								var str = "";
								for(var i = 0; i<data.length; i++){
									str += "<tr><td>" + data[i].date + "</td><td>" 
										+ data[i].cpyInfo + "</td><td>" 
										+ data[i].detectionId + "</td><td><strong><a class='previewTitle' style = 'cursor:pointer;'>预览" +
												"</a>&nbsp;&nbsp;<a  class='deleteTitle' style = 'cursor:pointer;'>删除</a></strong></td>"
										+ "<td style = 'display:none'>" + data[i].id + "</td>" 
										+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
								}
								$("#originalTitleInfoList").append(str);
							}else{
								$('#originalTableBtn').html('原始记录基本信息录入');
								$('.originalTableTitleListDiv').hide();
								$(".previewTitleDiv").hide();
								$('.tableHeadInfoInput').fadeIn();
							}
						}
					});
					alert("原始记录信息删除成功！");
				}
			});
		}
	});
	
	$('.titleTable input').bind('input propertychange', function() { 
		$(".updateCheck").fadeIn("slow");
	});  
	
	$('.originalUpdate').click(function(){
		var str = "";
		var info = $('.titleTable').find(':text');
		if(info.eq(0).val().trim()!=""){
			str += info.eq(0).val() +"#@";
		}else{
			str += " #@";
		}
		
		if(info.eq(2).val().trim()!=""){
			str += info.eq(2).val() +"#@";
		}else{
			str += " #@";
		}
		
		if(info.eq(3).val().trim()!=""){
			str += info.eq(3).val() +"#@";
		}else{
			str += " #@";
		}
		
		if(info.eq(4).val().trim()!=""){
			str += info.eq(4).val() +"#@";
		}else{
			str += " #@";
		}
		
		if(info.eq(5).val().trim()!=""){
			str += info.eq(5).val() +"#@";
		}else{
			str += " #@";
		}
		
		if(info.eq(6).val().trim()!=""){
			str += info.eq(6).val() +"#@";
		}else{
			str += " #@";
		}
		
		if(info.eq(7).val().trim()!=""){
			str += info.eq(7).val() +"#@";
		}else{
			str += " #@";
		}
		
		if(info.eq(8).val().trim()!=""){
			str += info.eq(8).val() +"#@";
		}else{
			str += " #@";
		}
		
		if(info.eq(9).val().trim()!=""){
			str += info.eq(9).val() +"#@";
		}else{
			str += " #@";
		}
		str += " #@";
		$.ajax({
			type: "post",
			url: "originalTitleInfoUpdate",
			data: {
					id: $(".idTmp").val(),
					originalName:str, 
					detectionId:info.eq(1).val()
					},
			dataType: "json",
			success: function(){
				
				$('.updateCheck').fadeOut("slow");
				$(".previewTitleDiv").fadeOut("slow");
				
				$.ajax({
					type: "post",
					url: "getTitleInfoList",
					dataType: "json",
					success: function(data){
						if(data.length>0){
							$("#originalTitleInfoList tr:gt(0)").remove();
							var str = "";
							for(var i = 0; i<data.length; i++){
								str += "<tr><td>" + data[i].date + "</td><td>" 
									+ data[i].cpyInfo + "</td><td>" 
									+ data[i].detectionId + "</td><td><strong><a class='previewTitle' style = 'cursor:pointer;'>预览" +
											"</a>&nbsp;&nbsp;<a  class='deleteTitle' style = 'cursor:pointer;'>删除</a></strong></td>"
									+ "<td style = 'display:none'>" + data[i].id + "</td>" 
									+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
							}
							$("#originalTitleInfoList").append(str);
						}
					}
				});
			}
		});
	});
	
	$('.Search').click(function(){
		
		$("#searchItems").show();
		$("#selectIdDiv").hide();
		$('.box2').fadeIn("slow");
		
	});
	$('.returnLast').click(function(){
		$("#searchItems").show();
		$("#selectIdDiv").hide();
		});
	$('.updateData').click(function(){
		window.location.href = "uploadData";
	});
	$('.clearInput').click(function(){
		endDate = $("#regionInfo").val();
		beginDate = $("#beginDate").val();
		detectionId = $("#detectionId").val();
		$("#beginDate").val("");
		$("#regionInfo").val("");
		$("#detectionId").val("");
		});
	$('.closePopWindow').click(function(){
		$('.box2').fadeOut("slow");
		$("#beginDate").val(beginDate);
		$("#regionInfo").val(endDate);
		$("#detectionId").val(detectionId);
		});
	
	$("#selectDetectionId").delegate(".getDid", "click", function(){
	     var $td=$(this).parents('tr').children('td');
		 $("#beginDate").val($td.eq(0).text());
		 $("#detectionId").val($td.eq(1).text());
		 $.ajax({
             type: "post",
             url: "SwitchDetection",
             data: {beginDate:$("#beginDate").val(), endDate:$("#beginDate").val(), detectionId:$("#detectionId").val()},
             dataType: "json",
             success: function(){
            	 $('#historyInfoSearch').submit();
             }
		 });
	  });
	
	$('.beginSearch').click(function(){
		searchFigureData();
	});
	
	function searchFigureData(){
		 if(!CheckDateTime()){
			 alert("日期格式不正确！格式为：YYYY-MM-DD，例如2010-01-01。");
			 $("#searchItems").show();
			 $("#selectIdDiv").hide();
			 $('.box2').fadeIn("slow");
		 }else{
			 $.ajax({
	             type: "post",
	             url: "getDetectionIds",
	             data: {
	            	 beginDate:$("#beginDate").val(), 
	            	 regionInfo:$("#regionInfo").val(),
	            	 dataSource:$("#dataSource").val(),
	            	 detectionId:$("#detectionId").val()
	            	 },
	             dataType: "json",
	             success: function(data){
	            	 if(data.length == 0){
	            		 alert("查询不到任何信息，请重新搜索");
	            		 $("#searchItems").show();
	     				 $("#selectIdDiv").hide();
	     				 $('.box2').fadeIn("slow");
	            	 }
	            	 if(data.length == 1){
	            		 $('.box2').fadeOut("slow");
	            		 $("#detectionId").val(data[0].id);
	            		 $("#beginDate").val(data[0].date);
	            		 $.ajax({
	                         type: "post",
	                         url: "SwitchDetection",
	                         data: {
	                        	 beginDate:$("#beginDate").val(), 
	                        	 endDate:$("#beginDate").val(), 
	                        	 detectionId:$("#detectionId").val(),
	                        	 dataSource:$("#dataSource").val()
	                        	 },
	                         dataType: "json",
	                         success: function(){
	                        	 $('#historyInfoSearch').submit();
	                         }
	            		 });
	            	 }
	            	 if(data.length > 1){
	            		 $('#searchItems').hide();
	            		 $("#selectIdDiv").show();
	            		 $("#selectDetectionId tr:gt(0)").remove();
	            		 var trHTML = "";
	            		 for(var i=0; i<data.length; i++){
	            			 trHTML += "<tr><td>";
	            			 trHTML += data[i].date;
	            			 trHTML += "</td><td>";
	            			 trHTML += data[i].id;
	            			 trHTML += "</td><td>";
	            			 trHTML += "<a class='getDid'>选择</a></td></tr>";
	            		 }
	            		 $("#selectDetectionId").append(trHTML);
	            		 
	            	 }
	             },
	             error: function() {
					alert("error");
				} 
	         });
		 }
	}
	function checkDateFormat(){
	if($("#beginDate").val().trim()==""){
		return true;
	}else{
		var a = /^(\d{4})-(\d{2})-(\d{2})$/
		if (!a.test($("#beginDate").val())) { 
			return false;
		}else 
			return true;
		} 
	}
	function CheckDateTime(){ 
		
		if($("#beginDate").val().trim()==""){
			return true;
		}else{
		  var str = $("#beginDate").val();
		  var reg=/^(\d+)-(\d{1,2})-(\d{1,2})$/; 
		  var r=str.match(reg); 
		  if(r==null) return false; 
		  r[2]=r[2]-1; 
		  var d= new Date(r[1],r[2],r[3]); 
		  if(d.getFullYear()!=r[1]) return false; 
		  if(d.getMonth()!=r[2]) return false; 
		  if(d.getDate()!=r[3]) return false; 
		  return true; 
		}
	}
	
	
	$(".OriginalData").click(function(){
		$('.box2_3').fadeIn("slow");
	});
	$(".closeSearchoriginalData").click(function(){
		$('.box2_3').fadeOut("slow");
	});
	
	$(".newOriginal").click(function(){
		
		$('.originalSearchOption').hide();
		$('.newOriginalOption').show();
	});
	$(".originalDataCreate").click(function(){
		
		 $.ajax({
             type: "post",
             url: "originalTableCreate",
             data: {originalName:$("#formGroupInputLarge").val()},
             dataType: "json",
             success: function(data){
            	 
            	 $('.box2_3').fadeOut("slow");
            	 
            	 
            	 if(data!=null||data.trim()!=""){
            		 $("#originalId").val(data);
            		 $('.originalSearchOption').show();
             		 $('.newOriginalOption').hide();
             		 window.location.href="OriginalData";
            	 }
            	 
             }
         });
		
	});
	$(".searchOriginalList").click(function(){
		var flag = true;
		var begin = $("#originalBeginDate").val();
		var end = $("#originalEndDate").val();
		if(!isDate(begin)){
			alert("开始时间请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例 如：2001-01-01\n\r");
			flag = false;
		}
		if(!isDate(end)){
			alert("结束时间请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例 如：2001-01-01\n\r");
			flag = false;
		}
		if(flag){
			$.ajax({
	            type: "post",
	            url: "getOriginalTableList",
	            data: { originalBeginDate:$("#originalBeginDate").val(), 
	            		originalEndDate:$("#originalEndDate").val(), 
	            		originalName:$("#originalName").val()
	            	  },
	            dataType: "json",
	            success: function(data){
		           	$("#originalDataList").css("max-height","400px");
		           	$('.originalTableListDiv').fadeIn("slow");
		           	$("#originalDataList tr:gt(0)").remove();
		           	$("#originalDataList").append(data);
	            }
	        });
		}
	});
	function isDate(dateString){
		if(dateString.trim()=="")return true;
		var r=dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		if(r==null){
			return false;
		}
		var d=new Date(r[1],r[3]-1,r[4]);
		var num = (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
		return (num!=0);
	}
	
	$("#originalDataList").delegate(".getODId", "click", function(){
	     var $td=$(this).parents('tr').children('td');
		 $.ajax({
	            type: "post",
	            url: "getOriginalTable",
	            data: {originalId: $td.eq(0).text()},
	            dataType: "json",
	            success: function(data){
	            	 $("#originalId").val($td.eq(0).text());
	            	 $('.originalSearchOption').show();
             		 $('.newOriginalOption').hide();
             		 window.location.href="OriginalData";
	            }
	        });
	  });
	
	$("#detectionDataList").delegate(".detectionDel", "click", function(){
		
	     var $td=$(this).parents('tr').children('td');
		 $.ajax({
	            type: "post",
	            url: "detectionIdDel",
	            data: {deviceId: $td.eq(0).text()},
	            dataType: "json",
	            success: function(data){
	            	$("#detectionDataList tr:gt(0)").remove();
		           	$("#detectionDataList").append(data);
	            }
	        });
		 
	  });
	
	$(".returnOriginalSearchOption").click(function(){
		
		$('.originalSearchOption').show();
		$('.newOriginalOption').hide();
	});
	
	$(".editDetecion").click(function(){
		
		 $.ajax({
	            type: "post",
	            url: "getDetectionIdList",
	            dataType: "json",
	            success: function(data){
		           	 $("#detectionDataList tr:gt(0)").remove();
		           	 $("#detectionDataList").append(data);
		           	 $('.box2_5').fadeIn("slow");
	            }
	        });
	});
	$(".closeDetectionOptions").click(function(){
		$('.box2_5').fadeOut("slow");
	});
	
	$(".addDetection").click(function(){
		
		if($("#navbarInput-01").val().trim()==""){
			  alert("仪器编号不能为空，请输入仪器编号！");
		}else{
			  $.ajax({
			         type : "post",
			         async : false,            
			         url : "addDetection",    
			         data : {detectionId:$("#navbarInput-01").val()},
			         dataType : "json",        
			         success : function(result) {
			        	 if(result.length>0){
			        		 alert("此编号已存在！");
			        	 }else{
			        		 alert("添加成功");
			        		 $(".editDetecion").click();
			        		 $("#navbarInput-01").val("")
			        	 }
			         },
			    });
		}
	  });
	$(".realTimeFigure").click(function(){
		getDetectionIdSelectList();
		$("#startId").val("");
    	$("#endId").val("");
		
	});
	$(".searchRealId").click(function() {
		getDetectionIdSelectList();
	});
	$("input[name='checkboxOption']").prop("checked",true);
	
//	$('#detectionSelectList tbody').delegate('tr', 'click', function() {
//		var $check = $(this).find("input[name='checkboxOption']");
//		
//		if($check.is(":checked")){
//			$check.prop("checked",false);
//		}else{
//			$check.prop("checked",true);
//		}
//		
//	});
	
	$(".closeDetectionSelect").click(function(){
//		$('.box2_2').fadeOut("slow");
		$("#detetion_modal").modal('hide');
	});
	
	$(".boxBtn").click(function(){
		
		if($(".boxBtn").hasClass("checkAll")){
			$("input[name='checkboxOption']").prop("checked",true);
			$(".boxBtn").removeClass("checkAll");
			$(".boxBtn").addClass("cancelCheckAll");
			$(".boxBtn").html("取消全选");
		}else{
			$("input[name='checkboxOption']").prop("checked",false);
			$(".boxBtn").removeClass("cancelCheckAll");
			$(".boxBtn").addClass("checkAll");
			$(".boxBtn").html("全选");
		}
	});
	
//	$(".checkAll").click(function(){
//		$("input[name='checkboxOption']").prop("checked",true);
//	}); 
//	$(".cancelCheckAll").click(function(){
//		$("input[name='checkboxOption']").prop("checked",false);
//	}); 
	
	$(".enterRealTime").click(function(){
		
		var options = "";
		var ids = ""
		$('input[name="checkboxOption"]:checked').each(function(){ 
			options += $(this).val()+"#@";
			ids += $(this).parents("tr").children("td").eq(0).text()+"#@";
		}); 
		$.ajax({
            type: "post",
            url: "setDetectionStatus",
            data : {
            	deviceId:options,
            	ids:ids
            },
            async:false, 
            dataType: "json",
            success: function(data){
            	
            	window.location.href="realTimeFigure";
            	
            }
	    });
		
	});
	
	$(".historyData").click(function(){
		$("#tempCenterBtn").html("数据源：采集板数据");
		$('.regionInfoDiv').fadeOut("slow");
		$("#dataSource").val("0");
		
		endDate = $("#regionInfo").val();
		beginDate = $("#beginDate").val();
		detectionId = $("#detectionId").val();
		$("#beginDate").val("");
		$("#regionInfo").val("");
		$("#detectionId").val("");
		
	}); 
	$(".realtimeData").click(function(){
		$("#tempCenterBtn").html("数据源：实时数据");
		$('.regionInfoDiv').fadeIn("slow");
		$("#dataSource").val("1");
		
		endDate = $("#regionInfo").val();
		beginDate = $("#beginDate").val();
		detectionId = $("#detectionId").val();
		$("#beginDate").val("");
		$("#regionInfo").val("");
		$("#detectionId").val("");
		
	}); 
	
});

function getAllDateAndIds() {
	var json = {
			date :$("#deleteDate").val(),
			did : $("#deleteDid").val() 
	};
	
	$.ajax({
		type: "post",
		url: "AllDateAndId",
		dataType: "json",
		data : {
			ds : JSON.stringify(json)
		},
		success: function(data){
			$(".deleteDataResult tbody tr").remove();
			var str = "";
        	for(var i=0;i<data.length;i++){
        		
        		str += "<tr><td><input type='checkbox' style='width: 20px;height: 20px'"
        			 	+" value='"+data[i].id+"'></td><td>"
        				+ (i+1) + "</td><td>"
        				+ data[i].date + "</td><td>"
        				+ data[i].dId + "</td><td>"
        				+ (data[i].source == "0"? "历史数据源" :"实时数据源" )
        				+ "</td><td style = 'display:none'>" + data[i].source +	"</td></tr>";
        	}
        	$(".deleteDataResult tbody").append(str);
		}
	});
}

function getDetectionIdSelectList(){
	$(".boxBtn").html("全选");
	$(".boxBtn").removeClass("cancelCheckAll");
	$(".boxBtn").addClass("checkAll");
	$.ajax({
        type: "post",
        url: "getDetectionIdSelectList",
        dataType: "json",
        data : {
        	startId : $("#startId").val(),
        	endId : $("#endId").val()
        },
        success: function(data){
        	 if(data.trim()==""){
        		 alert("请先添加检测仪器编号。");
        		 $(".editDetecion").click();
        	 }else{
        		 
        		 $("#detectionSelectList tr:gt(0)").remove();
	           	 $("#detectionSelectList").append(data);
	           	 $("#detetion_modal").modal();
        	 }
        }
    });
}

