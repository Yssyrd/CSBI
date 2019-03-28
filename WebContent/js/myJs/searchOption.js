	$(document).ready(function(){
		var endDate = $("#endDate").val();
		var beginDate = $("#beginDate").val();
		var detectionId = $("#detectionId").val();
		$('.Search').click(function(){
			
			if(detectionId!=""&&beginDate!=""){
				searchFigureData();
			}else{
				$("#searchItems").show();
				$("#selectIdDiv").hide();
				$('.box2').fadeIn("slow");
			}
			
		});
		$('.returnLast').click(function(){
			$("#searchItems").show();
			$("#selectIdDiv").hide();
			});
		$('.clearInput').click(function(){
			endDate = $("#endDate").val();
			beginDate = $("#beginDate").val();
			detectionId = $("#detectionId").val();
			$("#beginDate").val("");
			$("#endDate").val("");
			$("#detectionId").val("");
			});
		$('.closePopWindow').click(function(){
			$('.box2').fadeOut("slow");
			$("#beginDate").val(beginDate);
			$("#endDate").val(endDate);
			$("#detectionId").val(detectionId);
			});
		
		$("#selectDetectionId").delegate(".getDid", "click", function(){
		     var $td=$(this).parents('tr').children('td');
		     $("#beginDate").val($td.eq(0).text());
			 $("#detectionId").val($td.eq(1).text());
			 $('#historyInfoSearch').submit();
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
		             data: {beginDate:$("#beginDate").val(), endDate:$("#beginDate").val(), detectionId:$("#detectionId").val()},
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
		                         data: {beginDate:$("#beginDate").val(), endDate:$("#beginDate").val(), detectionId:$("#detectionId").val()},
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
		             }
		         });
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
		
	});