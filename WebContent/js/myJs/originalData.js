$(document).ready(function(){
	
	$(".resetRadio").click(function(){
		
		$("input[name='outLine']").attr("checked",false);
		$("input[name='powerWarn']").attr("checked",false);
		$("input[name='windWarn']").attr("checked",false);
		$("input[name='heartWarn']").attr("checked",false);
	});
	
	$(".closeSearchoriginalData").click(function(){
		$('.box2_3').fadeOut("slow");
	});
	
	$(".returnOriginalSearchOption").click(function(){
		
		$('.originalSearchOption').show();
		$('.newOriginalOption').hide();
	});
	$(".SearchoriginalData").click(function(){
		$('.box2_3').fadeIn("slow");
		
	});
	
	$(".exportFile").click(function(){
		$('.exportFileDiv').show();
		$('.setExportFileName').hide();
		$("#exportFileName").val("");
		$('.box2_5').fadeIn("slow");
	});
	$(".closeExportFile").click(function(){
		$('.box2_5').fadeOut("slow");
	});
	$(".excelFile").click(function(){
		$('.exportFileDiv').hide();
		$('.setExportFileName').fadeIn("slow");
		$('.fileInput').val("0");
	});
	$(".excelFile").click(function(){
		$('.exportFileH4').html("请输入导出的Excel文件名称");
		$('.exportFileDiv').hide();
		$('.setExportFileName').fadeIn("slow");
		$('.fileInput').val("0");
	});
	
	$(".pdfFile").click(function(){
		$('.exportFileH4').html("请输入导出的PDF文件名称");
		$('.exportFileDiv').hide();
		$('.setExportFileName').fadeIn("slow");
		$('.fileInput').val("1");
	});
	
	$(".returnExportFileDiv").click(function(){
		$('.setExportFileName').hide();
		$('.exportFileDiv').fadeIn("slow");
	});
	
	$(".exportFileNameCreate").click(function(){
		if( $("#originalId").val()==""){
			alert("请首先创建新表或者选中历史表");
			$('.box2_5').fadeOut("slow");
			$('.box2_3').fadeIn("slow");
			
		}else if($("#exportFileName").val().indexOf("_")>-1){
			alert("名字中禁止包含符号'_'");
		}else{
			var str = $('.fileInput').val().trim();
			for(var i = 0; i<10;i++){
				originalDataOptionsSave(i);
			}
			if(str == "0"){
				window.location.href = "html2Excel/" + $("#originalId").val() + "_" + $("#exportFileName").val();
				$('.box2_5').fadeOut("slow");
			}else if(str == "1"){
				window.location.href = "html2PDF/" + $("#originalId").val() + "_" + $("#exportFileName").val();
				$('.box2_5').fadeOut("slow");
			}else{
				alert("抱歉，我忘了你选的是什么类型的文件，请返回重新再选一次。");
			}
			
		}
		
	});
	
	
	$(".returnLastFigure").click(function(){
         $('#historyInfoSearch').submit();
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
            		 window.location.reload();
            		 
           	 }
           	 
            }
        });
		
	});
	$(".newOriginal").click(function(){
		
		$('.originalSearchOption').hide();
		$('.newOriginalOption').show();
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
	           data: {originalBeginDate:$("#originalBeginDate").val(), 
	           		originalEndDate:$("#originalEndDate").val(), originalName:$("#originalName").val()},
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
            		 window.location.reload();
	           	 
	            }
	        });
		 
	  });
	$('.DIRSHInputData').bind('input propertychange', function() {  
		var info = $('.DIRSHInputTr').find(':text');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
        }
	    
	    if(temp){
	    	var avg = 0;
    		var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/3;
	    	
	    	subStr = avg.toString();
  		    
  		    $(".avgDIRSHInputData").html(dataHandler(subStr));
  		    
  		   if($(".avgAFInputData").html().trim()!=""){
		    	 var str = (Number(subStr)*1000-Number($(".avgAFInputData").html())*1000)/1000;
				    $(".AFRelative").html(dataHandler(str.toString()));
		    }
  		   
  		    
		    dataCalculate();
    	}else{
    		$(".avgDIRSHInputData").html("");
    		$(".AFRelative").html("");
    		 dataCalculate();
    	}
    	 
	});  
	
	$('.realData').bind('input propertychange', function() {  
		var info = $('.realData');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
        }
	    
	    if(temp){
	    	var avg = 0;
    		var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/3;
	    	
	    	subStr = avg.toString();
  		    
  		    $(".avgAFInputData").html(dataHandler(subStr));
  		    if($(".avgDIRSHInputData").html().trim()!=""){
  		    	var str = (Number($(".avgDIRSHInputData").html())*1000-Number(subStr)*1000)/1000;
  	  		    $(".AFRelative").html(dataHandler(str.toString()));
  		    }
  		   
  		    dataCalculate();
    	}else{
    		$(".avgAFInputData").html("");
    		$(".AFRelative").html("");
    		 dataCalculate();
    	}
    	 
	});  
	
	$('.O2input').bind('input propertychange', function() {  
	    
		var info = $('.O2input');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
        }
	    
	    if(temp){
	    	var avg = 0;
    		var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/3;
	    	subStr = avg.toString();
	    	
  		    $(".O2Four").html(dataHandler(subStr));
  		    
  		  dataCalculate();
  		    
    	}else{
    		$(".O2Four").html("");
    	}
	    
	    if(temp&&$('.O2Five').val().trim()!=""){
	    	
	    	var O2Str =  (Number($(".O2Four").html())*1000 -  Number($(".O2Five").val())*1000)/(1000);
	    	$('.O2Six').html(dataHandler(O2Str.toString().substring(0,6)));
	    	
	    }else{
	    	$('.O2Six').html("");
	    	 dataCalculate();
	    }
    	 
	});  
	
	$('.O2Five').bind('input propertychange', function() {  
	    
		var info = $('.O2input');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
        }
	    
	    if(temp&&$('.O2Five').val().trim()!=""){
	    	
	    	var O2Str =  (Number($(".O2Four").html())*1000 -  Number($(".O2Five").val())*1000)/(1000);
	    	$('.O2Six').html(dataHandler(O2Str.toString().substring(0,6)));
	    	
	    	dataCalculate();
	    }else{
	    	$('.O2Six').html("");
	    	 dataCalculate();
	    }
    	 
	});  
	
	
	$('.NoiseInput').bind('input propertychange', function() {  
	    
		var info = $('.NoiseInput');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
        }
	    
	    if(temp){
	    	var avg = 0;
    		var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/3;
	    	subStr = avg.toString();
	    	
  		    $(".NoiseFour").html(dataHandler(subStr));
  		    
  		  dataCalculate();
  		    
    	}else{
    		$(".NoiseFour").html("");
    		 dataCalculate();
    	}
    	 
	}); 
	$('.NoiwarinInput').bind('input propertychange', function() {  
	    
		var info = $('.NoiwarinInput');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
        }
	    
	    if(temp){
	    	var avg = 0;
    		var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/3;
	    	subStr = avg.toString();
	    	
  		    $(".NoiwarinFour").html(dataHandler(subStr));
  		    
  		  dataCalculate();
  		    
    	}else{
    		$(".NoiwarinFour").html("");
    		 dataCalculate();
    	}
    	 
	}); 
	$('.outNoiInput').bind('input propertychange', function() {  
	    
		var info = $('.outNoiInput');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
	    }
	    
	    if(temp){
	    	var avg = 0;
			var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/3;
	    	subStr = avg.toString();
	    	
		    $(".NoiwarinEight").html(dataHandler(subStr));
		    
		    dataCalculate();
		}else{
			 $(".NoiwarinEight").html("");
			 dataCalculate();
		}
		 
	}); 
	
	
	
	$('.inputTableTwo').bind('input propertychange', function() {  
	    
		var info = $('.T32CDISTInputText');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
	    }
	    
	    if(temp){
	    	var avg = 0;
			var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/15;
	    	subStr = avg.toString();
	    	
		    $(".AVGOne").html(dataHandler(subStr));
		    
		    info = $('.T32CAVG');
		    
		    var deviStr = (Number(info.eq(0).html())*1000-Number(info.eq(1).html())*1000)/1000;
		    
			$(".T32deviation").html(dataHandler(deviStr.toString()));
			
			info = $('.T32CAT');
			var max = (Number(info.eq(0).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
			var min = (Number(info.eq(0).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
			var strNum;
			for (var i = 1; i < info.length; i++) {
				
				strNum = (Number(info.eq(i).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
				if(strNum>max){
					max = strNum;
				}
				if(strNum<min){
					min = strNum
				}
		    }
			if(Math.abs(min)>Math.abs(max)){
				strNum = min;
			}else{
				strNum = max;
			}
			$('.T32Wave').html(dataHandler(strNum.toString()));
			
			info = $('.T32CAVG');
			
			var max = (Number(info.eq(2).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
			var min = (Number(info.eq(2).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
			
			for (var i = 3; i < info.length; i++) {
				
				strNum = (Number(info.eq(i).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
		    	
				if(strNum>max){
					max = strNum;
				}
				if(strNum<min){
					min = strNum
				}
		    }
			if(Math.abs(min)>Math.abs(max)){
				strNum = min;
			}else{
				strNum = max;
			}
			$('.UniformityT32Avg').html(dataHandler(strNum.toString()));
			
		}else{
			 $(".AVGOne").html("");
			 $(".T32deviation").html("");
			 dataCalculate();
		}
		 
		info = $('.T36DISTInputText');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
	    }
	    
	    if(temp){
	    	var avg = 0;
			var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/15;
	    	subStr = avg.toString();
	    	
		    $(".AVGTwo").html(dataHandler(subStr));
		    
		    var info = $('.T36AVG');
		    
		    var deviStr = (Number(info.eq(0).html())*1000-Number(info.eq(1).html())*1000)/1000;
		    
			$(".T36deviation").html(dataHandler(deviStr.toString()));
		    
			info = $('.T36AT');
			var max = (Number(info.eq(0).html().trim())*1000 - Number($(".AVGATT36").html())*1000)/1000;
			var min = (Number(info.eq(0).html().trim())*1000 - Number($(".AVGATT36").html())*1000)/1000;
			var strNum;
			for (var i = 1; i < info.length; i++) {
				
				strNum = (Number(info.eq(i).html().trim())*1000 - Number($(".AVGATT36").html())*1000)/1000;
		    	
				if(strNum>max){
					max = strNum;
				}
				if(strNum<min){
					min = strNum
				}
		    }
			if(Math.abs(min)>Math.abs(max)){
				strNum = min;
			}else{
				strNum = max;
			}
			$('.T36Wave').html(dataHandler(strNum.toString()));
			
			info = $('.T36AVG');
			max = (Number(info.eq(2).html().trim())*1000 - Number($(".AVGATT36").html())*1000)/1000;
			min = (Number(info.eq(2).html().trim())*1000 - Number($(".AVGATT36").html())*1000)/1000;
			
			for (var i = 3; i < info.length; i++) {
				
				strNum = (Number(info.eq(i).html().trim())*1000 - Number($(".AVGATT36").html())*1000)/1000;
		    	
				if(strNum>max){
					max = strNum;
				}
				if(strNum<min){
					min = strNum
				}
		    }
			if(Math.abs(min)>Math.abs(max)){
				strNum = min;
			}else{
				strNum = max;
			}
			$('.UniformityT36Avg').html(dataHandler(strNum.toString()));
	    
			dataCalculate();
	    
	    }else{
	    	 $(".AVGTwo").html("");
	    	 $(".T36deviation").html("");
	    	 dataCalculate();
	    }
	    
		 
	});
	
	$(".updateOriginalDataTable").click(function(){
		
		for(var i = 0; i<10;i++){
			originalDataOptionsSave(i);
		}
		
		alert("保存成功");
	});
	
	function originalDataOptionsSave(step){
		
		var result = "";
		
		switch (step) {
        case (0):
        	var index = "";
        	var infoList = "";
        	var info = $('.tableTitle').find(':text');
        	index = info.eq(0).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@ #@";
        	
        	index = info.eq(3).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = info.eq(1).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = info.eq(6).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = info.eq(2).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@ #@";
        	
        	
        	index = info.eq(4).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = info.eq(5).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = info.eq(7).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = info.eq(8).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@ #@ #@";
        	
        	index = info.eq(9).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = $(".uncertainty").val();
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = info.eq(10).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
        	index = info.eq(11).val() ;
        	if(index==""){
	    		index = " ";
	    	}
        	infoList = infoList + index + "#@";
        	
    	    $.ajax({
	            type: "post",
	            async:false, 
	            url: "updateOraiginalTableTitle",
	            data: {originalId: $("#originalId").val().trim(),tabletitle:infoList},
	            dataType: "json",
	            success: function(data){
	            	if(data.length==0){
	            		result = "0";
	            	}
	            }
	        });
    	    
            break;
            
        case (1):
        	var index = "";
	    	var infoList = "";
	    	index = $("input[name='outLine']:checked").val();
	    	if(index==null){
	    		index = " ";
	    	}
	    	infoList = infoList + index + "#@";
	    	
	    	index = $("input[name='powerWarn']:checked").val();
	    	if(index==null){
	    		index = " ";
	    	}
	    	infoList = infoList + index + "#@";
	    	
	    	index = $("input[name='windWarn']:checked").val();
	    	if(index==null){
	    		index = " ";
	    	}
	    	infoList = infoList + index + "#@";
	    	
	    	index = $("input[name='heartWarn']:checked").val();
	    	if(index==null){
	    		index = " ";
	    	}
	    	infoList = infoList + index + "#@";
	    	var info = $('.tableOne').find(':text');
		    for (var i = 0; i < info.length; i++) {
		    	
		    	 index = info.eq(i).val().trim() ;
		    	 if(index==""){
			    	index = " ";
			     }
		    	 infoList = infoList + index + "#@";
	        }
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableOne",
	            async:false, 
	            data: {originalId: $("#originalId").val().trim(),tableOne:infoList},
	            dataType: "json",
	            success: function(data){
	            	if(data.length==0){
	            		result = "1";
	            	}
	            }
	        });
		    
            break;
      
        case (2):
        	
        	var showflag = $(".optionInputtmp").val();
        	if(showflag=='1'){
        		var index = "";
    	    	var infoList = "";
    	    	var info = $('.T36And32Tr');
    		    for (var i = 0; i < info.length; i++) {
    		    	
    		    	index = info.eq(i).children(".T32CDIST").children(".T32CDISTInputText").val().trim();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CAT").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CBT").html(); 
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CCT").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CDT").html(); 
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CET").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T36DIST").children(".T36DISTInputText").val().trim();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T36AT").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T36BT").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T36CT").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T36DT").html(); 
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T36ET").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	        }
    		    
        	  	
        	  	info = $('.T32CRevise').find(':text');
    	  	    for (var i = 0; i < info.length; i++) {
    		    	index = info.eq(i).val().trim();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    		    	infoList = infoList + index + "#@";
    	  	    }
    	  	    
    		    
    		    info = $('.T36Revise').find(':text');
    	  	    for (var i = 0; i < info.length; i++) {
    		    	index = info.eq(i).val().trim();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    		    	infoList = infoList + index + "#@";
    	  	    }
    	  	    info = $('.T32CAVG');
    	  	    for (var i = 0; i < info.length; i++) {
    		    	index = info.eq(i).html();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    		    	infoList = infoList + index + "#@";
    	  	    }
    	  	    
    	  	    info = $('.T36AVG');
    	  	    for (var i = 0; i < info.length; i++) {
    		    	index = info.eq(i).html();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    		    	infoList = infoList + index + "#@";
    	  	    }
    	  	    
    	  	    
    	  		index = $(".T32deviation").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".T32Wave").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".T36deviation").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".T36Wave").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".UniformityT32Avg").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".UniformityT36Avg").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".tableTwoSaveOne").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".tableTwoSaveTwo").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".tableTwoSaveThree").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
    	  	    
        		index = $(".T32Time").val();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	
        	  	index = $(".T36Time").val();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	
    		    $.ajax({
    	            type: "post",
    	            url: "updateOraiginalTableTwo",
    	            async:false, 
    	            data: {originalId: $("#originalId").val().trim(),tableTwo:infoList},
    	            dataType: "json",
    	            success: function(data){
    	            	if(data.length==0){
    	            		result = "2";
    	            	}
    	            }
    	        });
        	};
        	if(showflag=='0'){
        		var index = "";
    	    	var infoList = "";
    	    	var info = $('.T36And32Tr2');
    		    for (var i = 0; i < info.length; i++) {
    		    	
    		    	index = info.eq(i).children(".T32CDIST2").children(".T32CDISTInputText2").val().trim();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CAT2").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CBT2").html(); 
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CCT2").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CDT2").html(); 
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    	    	  	index = info.eq(i).children(".T32CET2").html();
    	    	  	if(index==""){
    		    		index = " ";
    		    	}
    	    	  	infoList = infoList + index + "#@";
    		    	
    	    	  	
    	    	  	index = " ";
    	    	  	infoList = infoList + index + "#@";
    		    	
    	    	  	index = " ";
    		    	infoList = infoList + index + "#@";
    		    	
    		    	index = " ";
    	    	  	infoList = infoList + index + "#@";
    		    	
    	    	  	index = " ";
    	    	  	infoList = infoList + index + "#@";
    		    	
    	    	  	index = " ";
    	    	  	infoList = infoList + index + "#@";
    		    	
    	    	  	index = " ";
    	    	  	infoList = infoList + index + "#@";
    	        }
    		    
        	  	
        	  	info = $('.T32CRevise2').find(':text');
    	  	    for (var i = 0; i < info.length; i++) {
    		    	index = info.eq(i).val().trim();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    		    	infoList = infoList + index + "#@";
    	  	    }
    	  	    
    		    
    		    info = $('.T36Revise').find(':text');
    	  	    for (var i = 0; i < info.length; i++) {
    		    	index = info.eq(i).val().trim();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    		    	infoList = infoList + index + "#@";
    	  	    }
    	  	    info = $('.T32CAVG2');
    	  	    for (var i = 0; i < info.length; i++) {
    		    	index = info.eq(i).html();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    		    	infoList = infoList + index + "#@";
    	  	    }
    	  	    
    	  	    info = $('.T36AVG');
    	  	    for (var i = 0; i < info.length; i++) {
    		    	index = info.eq(i).html();
    		    	if(index==""){
    		    		index = " ";
    		    	}
    		    	infoList = infoList + index + "#@";
    	  	    }
    	  	    
    	  		index = $(".T32deviation2").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".T32Wave2").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".T36deviation").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".T36Wave").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".UniformityT32Avg2").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".UniformityT36Avg").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".tableTwoSaveOne").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".tableTwoSaveTwo").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	index = $(".tableTwoSaveThree").html();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
    	  	    
        		index = $(".T32Time").val();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	
        	  	index = $(".T36Time").val();
        	  	if(index==""){
    	    		index = " ";
    	    	}
        	  	infoList = infoList + index + "#@";
        	  	
    		    $.ajax({
    	            type: "post",
    	            url: "updateOraiginalTableTwo",
    	            async:false, 
    	            data: {originalId: $("#originalId").val().trim(),tableTwo:infoList},
    	            dataType: "json",
    	            success: function(data){
    	            	if(data.length==0){
    	            		result = "2";
    	            	}
    	            }
    	        });
        	}
            break;
       
        case (3):

        	var index = "";
	    	var infoList = "";
	    	
	    	var info = $('#T32LR').find('.T32LTr');
		    for (var i = 0; i < info.length; i++) {
		    	index = info.eq(i).children(".T32LDIST").children(".T32LDISTInputText").val().trim();
		    	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32LAT").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32LBT").html(); 
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32LCT").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32LDT").html(); 
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32LET").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	        }
        	
		    var info = $('#T32LR').find('.T32RTr');
		    for (var i = 0; i < info.length; i++) {
		    	index = info.eq(i).children(".T32RDIST").children(".T32RDISTInputText").val().trim();
		    	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32RAT").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32RBT").html(); 
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32RCT").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32RDT").html(); 
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info.eq(i).children(".T32RET").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	        }
		    
			info = $('.T32LRRevise').find(':text');
	  	    for (var i = 0; i < info.length; i++) {
		    	index = info.eq(i).val().trim();
		    	if(index==""){
		    		index = " ";
		    	}
		    	infoList = infoList + index + "#@";
	  	    }
	  	    info = $('.T32LRAVG');
	  	    for (var i = 0; i < info.length; i++) {
		    	index = info.eq(i).html();
		    	if(index==""){
		    		index = " ";
		    	}
		    	infoList = infoList + index + "#@";
	  	    }
		  	info = $('.T32LRUniformity');
	  	    for (var i = 0; i < info.length; i++) {
		    	index = info.eq(i).html();
		    	if(index==""){
		    		index = " ";
		    	}
		    	infoList = infoList + index + "#@";
	  	    }
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableThree",
	            async:false, 
	            data: {originalId: $("#originalId").val().trim(),tableThree:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "3";
	            	}
	            }
	        });
            break;
        case (4):
        	
        	var index = "";
	    	var infoList = "";
	    	
	    	var info = $('.DIRSHInputTr').find('.DIRSHInputData');
		    for (var i = 0; i < info.length; i++) {
	    	  	index = info.eq(i).val().trim();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	        }
		    
		    infoList = infoList + (($('.avgDIRSHInputData').html()!="")?$('.avgDIRSHInputData').html():" ") + "#@";
		    
		    info = $('.AFInputTr').find('.realData');
	    	
		    for (var i = 0; i < info.length; i++) {
	    	  	index = info.eq(i).val().trim();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	        }
		    
		    infoList = infoList + (($('.avgAFInputData').html()!="")?$('.avgAFInputData').html():" ") + "#@";
		    infoList = infoList + (($('.AFRelative').html()!="")?$('.AFRelative').html():" ") + "#@";
		    
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableFour",
	            async:false, 
	            data: {originalId: $("#originalId").val().trim(),tableFour:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "4";
	            	}
	            }
	        });
        	
            break;
            
        case (5):
        	var index = "";
	    	var infoList = "";
	    	
		    infoList = infoList + (($('.O2One').val().trim()!="")?$('.O2One').val().trim():" ") + "#@";
		    infoList = infoList + (($('.O2Two').val().trim()!="")?$('.O2Two').val().trim():" ") + "#@";
		    infoList = infoList + (($('.O2Three').val().trim()!="")?$('.O2Three').val().trim():" ") + "#@";
		    infoList = infoList + (($('.O2Four').html()!="")?$('.O2Four').html():" ") + "#@";
		    infoList = infoList + (($('.O2Five').val().trim()!="")?$('.O2Five').val().trim():" ") + "#@";
		    infoList = infoList + (($('.O2Six').html()!="")?$('.O2Six').html():" ") + "#@";
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableFive",
	            data: {originalId: $("#originalId").val().trim(),tableFive:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "5";
	            	}
	            }
	        });
            break;
            
        case (6):
        	var index = "";
	    	var infoList = "";
	    	
		    infoList = infoList + (($('.NoiseOne').val().trim()!="")?$('.NoiseOne').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiseTwo').val().trim()!="")?$('.NoiseTwo').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiseThree').val().trim()!="")?$('.NoiseThree').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiseFour').html()!="")?$('.NoiseFour').html():" ") + "#@";
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableSix",
	            async:false, 
	            data: {originalId: $("#originalId").val().trim(),tableSix:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "6";
	            	}
	            }
	        });

            break;
            
        case (7):
        	var index = "";
	    	var infoList = "";
	    	
		    infoList = infoList + (($('.NoiwarinOne').val().trim()!="")?$('.NoiwarinOne').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinTwo').val().trim()!="")?$('.NoiwarinTwo').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinThree').val().trim()!="")?$('.NoiwarinThree').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinFour').html()!="")?$('.NoiwarinFour').html():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinFive').val().trim()!="")?$('.NoiwarinFive').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinSix').val().trim()!="")?$('.NoiwarinSix').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinSeven').val().trim()!="")?$('.NoiwarinSeven').val().trim():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinEight').html()!="")?$('.NoiwarinEight').html():" ") + "#@";
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableSeven",
	            async:false, 
	            data: {originalId: $("#originalId").val().trim(),tableSeven:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "7";
	            	}
	            }
	        });
            break;
            
        case (8):
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableEight",
	            async:false, 
	            data: {originalId: $("#originalId").val().trim(),tableEight:$(".textareaRemark").val()},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "8";
	            	}
	            }
	        });
            break;
            
        case (9):
        	
        	
        	var str = (($('.signOne').val().trim()!="")?$('.signOne').val().trim():" ")+"#@"+(($('.signTwo').val().trim()!="")?$('.signTwo').val().trim():" ")+"#@"+(($('.serialId').val().trim()!="")?$('.serialId').val().trim():" ");
//        	alert(str);
        	$.ajax({
	            type: "post",
	            url: "updateOraiginalTableNine",
	            async:false, 
	            data: {originalId: $("#originalId").val().trim(),tableNine:str},
	            dataType: "json",
	            success: function(data){
	            	if(data.length==0){
	            		result = "9";
	            	}
	            }
	        });
            break;
            
		}
		
		return result;
		
	}
	
	function dataHandler(str){
		var data = [];
		data = str.split(".");
		var strFloat = "";
		var result = str;
	    var flag = true;
		if(result.trim().substring(0,1)=='-'){
			flag = false;
		}
		if(data.length == 1){
			
			result += ".00";
			
		}
	
		if(data.length == 2){
			strFloat = data[1];
			if(strFloat.length<2){
				result = data[0] + "." + strFloat + "0";
			}else if(strFloat.length==2){
				result = str;
			}else{
				if(strFloat[2]=='5'){
					if(strFloat[1]%2==0){
						strFloat = strFloat.substring(0,2);
						result = data[0] + "." + strFloat;
					}
					if(strFloat[1]%2==1){
						if(flag){
							str = Number(str)*100;
							var str2 = Math.ceil(str)/100;
							result = dataHandler(str2.toString());
						}else{
							str = Number(str)*100;
							var str1 = Math.ceil(str);
							var str2 = (str1-1)/100;
							result = dataHandler(str2.toString());
						}
						
					}
					
				}else{
					str= Math.round(Number(str)*100)/100;
					result = dataHandler(str.toString());
				}
			}
		}
		return result;
	
	}
	
	function sleep(n) { 
        var start = new Date().getTime();
        while (true){
        	if(new Date().getTime() - start > n)
        		break;
        }
    }
	
	function dataCalculate(){
		
		var str = "";
	    
	    if(Math.abs(Number($(".T32deviation").html()))>0.8){
	    	str += "32℃平铺温度偏差超过 0.8℃；";
	    }
	    if(Math.abs(Number($(".T36deviation").html()))>0.8){
	    	str += "36℃平铺温度偏差超过 0.8℃；";
	    }
	    
	    if(Math.abs(Number($(".T32Wave").html()))>0.5){
	    	str += "36℃平铺温度波动度超过 0.5℃；";
	    }
	    if(Math.abs(Number($(".T36Wave").html()))>0.5){
	    	str += "36℃平铺温度波动度超过 0.5℃；";
	    }
	    
	    if(Math.abs(Number($(".UniformityT32Avg").html()))>0.8){
	    	str += "32℃平铺温度均匀超过 0.8℃；";
	    }
	    if(Math.abs(Number($(".UniformityT36Avg").html()))>0.8){
	    	str += "36℃平铺温度均匀超过 0.8℃；";
	    }
	    
	    if(Math.abs(Number($(".tableTwoSaveOne").html()))>1.5){
	    	str += "平均培养箱温度与控制培养箱温度之差超过 1.5℃；";
	    }
	    
	    if(Math.abs(Number($(".tableTwoSaveThree").html()))>2){
	    	str += "温度超调量超过2℃；";
	    }
	    
	    if(Math.abs(Number($(".AFRelative").html()))>10){
	    	
	    	str += "相对湿度偏差超过 10%RH；";
	    }
	    
	    if(Math.abs(Number($(".O2Six").html()))>5){
	    	str += "氧分析器示值允许误差超过 5%FS；";
	    }
	    
	    if(Math.abs(Number($(".NoiseFour").html()))>60){
	    	str += "婴儿舱内的噪声超过60dB；";
	    }
	    
	    if(Math.abs(Number($(".NoiwarinFour").html()))>80){
	    	str += "报警器报警时婴儿舱内噪声超过80dB；";
	    }
	    
	    if($(".NoiwarinEight").html().trim()!=""){
	    		
	    	 if(Math.abs(Number($(".NoiwarinEight").html()))<65){
	 	    	str += "报警器报警时箱外噪声小于65dB；";
	 	    }
	    	
	    }
	    
	    var info = $(".T32LRUniformity");
	    for(var i = 0; i<info.length;i++){
	    	
	    	if(Math.abs(Number(info.eq(i).html()))>1){
	    		str += " 床垫倾斜时温度均匀超过 1℃；";
	    		break;
	    	}
	    	
	    }
	    $(".textareaRemark").val(str);
		
	}
	
	$(".UIOptionsPop").click(function(){
		$('.box2').fadeIn("slow");
	});	
	
	$(".UIOptions").click(function(){
		
		var $shadow = $(".shadow");
		$shadow.hide();
		$shadow.eq(0).show();
		$(".table32And36").show();
		
		$(".32And36One").hide();
		$(".32And36Two").show();
		$(".optionInputtmp").val("0");
		
		var options = "";
		$('input[name="checkboxOption"]:checked').each(function(){ 
			options += $(this).val();
			setUIOptions($(this).val());
		});
		var optionfinal = "";
		for(var i=0;i<9;i++){
			if(options.indexOf(i.toString())!=-1){
				optionfinal += "1" + "#@";
			}else{
				optionfinal += "0" + "#@";
			}
		}
		$.ajax({
            type: "post",
            url: "updateOraiginalUIOptions",
            async:false, 
            data: {originalId: $("#originalId").val().trim(),options:optionfinal},
            dataType: "json",
            success: function(){
            	
            }
        });
		
		$('.box2').fadeOut("slow");
	});
	$(".cancelUIOptions").click(function(){
		$('.box2').fadeOut("slow");
	});	
	function setUIOptions(options){
		
		var $row = $(".showOptionUI");
		switch (options) {
	        
			case ('0'):
				$row.eq(0).show();
	            break;
	            
	        case ('1'):
	        	$(".32And36One").show();
				$(".32And36Two").hide();
				$(".optionInputtmp").val("1");
	        	break;
	      
	        case ('2'):
	        	$row.eq(1).show();
	            break;
	       
	        case ('3'):
	        	$row.eq(2).show();
	            break;
	        
	        case ('4'):
	        	$row.eq(3).show();
	            break;
	            
	        case ('5'):
	        	$row.eq(4).show();
	            break;
	            
	        case ('6'):
	        	$row.eq(5).show();
	            break;
	            
	        case ('7'):
	        	$(".remark").show();
	            break;
	        case ('8'):
	        	$(".criterionInfo").show();
	            break;   
		}
		
	}
	$('.inputTableTwo2').bind('input propertychange', function() {  
	    
		var info = $('.T32CDISTInputText2');
		var temp = true;
		
	    for (var i = 0; i < info.length; i++) {
	    	if(info.eq(i).val().trim() ==""){
	    		temp = false;
	    	}
	    }
	    
	    if(temp){
	    	var avg = 0;
			var subStr = "";
	    	
	    	for (var i = 0; i < info.length; i++) {
	    		avg += Number(info.eq(i).val().trim());
	        }
	    	
	    	avg = avg/15;
	    	subStr = avg.toString();
	    	
		    $(".AVGOne2").html(dataHandler(subStr));
		    
		    info = $('.T32CAVG2');
		    
		    var deviStr = (Number(info.eq(0).html())*1000-Number(info.eq(1).html())*1000)/1000;
		    
			$(".T32deviation2").html(dataHandler(deviStr.toString()));
			
			info = $('.T32CAT2');
			var max = (Number(info.eq(0).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
			var min = (Number(info.eq(0).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
			var strNum;
			for (var i = 1; i < info.length; i++) {
				
				strNum = (Number(info.eq(i).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
				if(strNum>max){
					max = strNum;
				}
				if(strNum<min){
					min = strNum
				}
		    }
			if(Math.abs(min)>Math.abs(max)){
				strNum = min;
			}else{
				strNum = max;
			}
			$('.T32Wave2').html(dataHandler(strNum.toString()));
			
			info = $('.T32CAVG2');
			
			var max = (Number(info.eq(2).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
			var min = (Number(info.eq(2).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
			
			for (var i = 3; i < info.length; i++) {
				
				strNum = (Number(info.eq(i).html().trim())*1000 - Number($(".AVGATT32").html())*1000)/1000;
		    	
				if(strNum>max){
					max = strNum;
				}
				if(strNum<min){
					min = strNum
				}
		    }
			if(Math.abs(min)>Math.abs(max)){
				strNum = min;
			}else{
				strNum = max;
			}
			$('.UniformityT32Avg2').html(dataHandler(strNum.toString()));
			
		}else{
			 $(".AVGOne2").html("");
			 $(".T32deviation2").html("");
		}
		 
	});
	
});

