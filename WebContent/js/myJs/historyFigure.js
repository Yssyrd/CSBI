$(document).ready(function(){
	
	$(".resetRadio").click(function(){
		
		$("input[name='outLine']").attr("checked",false);
		$("input[name='powerWarn']").attr("checked",false);
		$("input[name='windWarn']").attr("checked",false);
		$("input[name='heartWarn']").attr("checked",false);
	});
	
	$(".detectionIdDropDown").click(function(){
		
		
		if(this.innerHTML == $(".textDetId").html()){
			$("#detectionId").val(this.innerHTML);
        	$("#beginDate").val($("#timeTmp").val());
        	$("#dataSource").val( $("#sourceTmp").val());
        	$('#historyInfoSearch').submit();
		}else{
			if(confirm('原始记录表即将退出，是否保存？')) {
				for(var i = 0;i<9;i++){
					originalDataOptionsSave(i.toString());
				}
				$("#detectionId").val(this.innerHTML);
				$.ajax({
                     type: "post",
                     url: "SwitchDetection",
                     data: {beginDate:$("#beginDate").val(), endDate:$("#beginDate").val(), detectionId:$("#detectionId").val()},
                     dataType: "json",
                     success: function(){
                    	 $("#beginDate").val($("#timeTmp").val());
                    	 $("#dataSource").val( $("#sourceTmp").val());
                    	 $('#historyInfoSearch').submit();
                     }
        		 });
			} else {
				$("#detectionId").val(this.innerHTML);
				$.ajax({
                    type: "post",
                    url: "SwitchDetection",
                    data: {beginDate:$("#beginDate").val(), endDate:$("#beginDate").val(), detectionId:$("#detectionId").val()},
                    dataType: "json",
                    success: function(){
                    	$("#beginDate").val($("#timeTmp").val());
                    	$("#dataSource").val( $("#sourceTmp").val());
                    	$('#historyInfoSearch').submit();
                    }
       		 });
			}
		}
		
	});
	function sleep(n) { 
        var start = new Date().getTime();
        while (true){
        	if(new Date().getTime() - start > n)
        		break;
        }
    }
	
	$(".skipIndex").click(function(){
		 $.ajax({
             type: "post",
             url: "SwitchDetection",
             data: {beginDate:$("#beginDate").val(), endDate:$("#beginDate").val(), detectionId:$("#detectionId").val()},
             dataType: "json",
             success: function(){
            	 window.location.href = "index.jsp";
             }
		 });
	});
	
	$(".originalPart").click(function(){
		$('.box2_2').fadeIn("slow");
        var str=$(this).val();
        $("#getStep").val(str);
        optionsShowControl(str);
        
	});
	$(".SearchoriginalData").click(function(){
		$('.box2_3').fadeIn("slow");
		$('.box2').fadeOut("slow");
		
	});
	$(".closePopWindow").click(function(){
		$('.box2').fadeOut("slow");
	});
	$(".closeSearchoriginalData").click(function(){
		$('.box2_3').fadeOut("slow");
	});
	$(".cancel").click(function(){
		$('.box2_2').fadeOut("slow");
		$("#optionsValue").val("");
		originalDataOptionsSave(8);
	});
	
	$(".checkCancel").click(function(){
		$('.box2_4').fadeOut("slow");
	});
	
	$(".cancelThisOption").click(function(){
		$('.box2_4').fadeOut("slow");
		$("#optionsValue").val("");
		$('.checkCancel').hide();
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
             		 window.location.reload();
             		 
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
	            data: {originalBeginDate:$("#originalBeginDate").val(), 
	            	   originalEndDate:$("#originalEndDate").val(), 
	            	   originalName:$("#originalName").val()},
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
	
	$(".returnOriginalSearchOption").click(function(){
		
		$('.originalSearchOption').show();
		$('.newOriginalOption').hide();
	});
	
	$(".nextStep").click(function(){
		
		var str=$("#getStep").val();
		originalDataOptionsSave(str);
		str++;
		$("#getStep").val(str);
		optionsShowControl(str);
		dataCalculate();
		var result = originalDataOptionsSave(8);
	});
	$(".lastStep").click(function(){
		
		var str=$("#getStep").val();
		originalDataOptionsSave(str);
		str--;
		$("#getStep").val(str);
		optionsShowControl(str);
		
		dataCalculate();
		var result = originalDataOptionsSave(8);
		
	});
	
	$(".setDISTValue").click(function(){
		
		var input=$(".DISTValueInput").val().trim();
		
		if(input!=""){
			
			if($(".flagForTableTwo").val()=="0"){
				
				$(".T32CDISTInputText").val(input);
				$(".AVGOne").html(input);
			}
			
			if($(".flagForTableTwo").val()=="1"){
				
				$(".T36DISTInputText").val(input);
				$(".AVGTwo").html(input);
				
			}
			
			T32ChangeTrigger();
			T36ChangeTrigger();;
		}
		
		
	});
	
	
	$(".saveInfo").click(function(){
		
		dataCalculate();
		
		for(var i = 0;i<10;i++){
			originalDataOptionsSave(i.toString());
		}
		alert("保存成功！");
		if($("#getStep").val().trim()=='9'){
			$('.box2_2').fadeOut("slow");
    		$("#optionsValue").val("");
		}
//		var step=$("#getStep").val().trim();
//		var result = originalDataOptionsSave(step);
//		alert("保存成功！");
		
	});
	
	function originalDataOptionsSave(step){
		
		var result = "";
		
		switch (step) {
        case ('0'):
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
	            url: "updateOraiginalTableTitle",
	            async:false,
	            data: {originalId: $("#originalId").val(),tabletitle:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "0";
	            	}
	            }
	        });
    	    
            break;
            
        case ("1"):
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
		    	
		    	 index = info.eq(i).val() ;
		    	 if(index==""){
			    		index = " ";
			    	}
		    	 infoList = infoList + index + "#@";
	        }
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableOne",
	            data: {originalId: $("#originalId").val(),tableOne:infoList},
	            dataType: "json",
	            async:false,
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "1";
	            	}
	            }
	        });
		    
            break;
      
        case ('2'):
        	
        	var index = "";
	    	var infoList = "";
	    	
	    	var info = $('.T32').find('.T32CTr');
	    	var info2 = $('.T36').find('.T36Tr');
		    for (var i = 0; i < info.length; i++) {
		    	
		    	index = info.eq(i).children(".T32CDIST").children(".T32CDISTInputText").val();
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
	    	  	index = info2.eq(i).children(".T36DIST").children(".T36DISTInputText").val();
		    	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info2.eq(i).children(".T36AT").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info2.eq(i).children(".T36BT").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info2.eq(i).children(".T36CT").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info2.eq(i).children(".T36DT").html(); 
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	index = info2.eq(i).children(".T36ET").html();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	    	  	
	    	  	
	        }
		    
    	  	
    	  	info = $('.T32CRevise').find(':text');
	  	    for (var i = 0; i < info.length; i++) {
		    	index = info.eq(i).val();
		    	if(index==""){
		    		index = " ";
		    	}
		    	infoList = infoList + index + "#@";
	  	    }
	  	    
		    
		    info = $('.T36Revise').find(':text');
	  	    for (var i = 0; i < info.length; i++) {
		    	index = info.eq(i).val();
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
		    
	  	//------------------------------------------
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
	            data: {originalId: $("#originalId").val(),tableTwo:infoList},
	            dataType: "json",
	            async:false,
	            success: function(data){
	            	if(data.length==0){
	            		result = "2";
	            	}
	            }
	        });
            break;
       
        case ('3'):

        	var index = "";
	    	var infoList = "";
	    	
	    	var info = $('#T32LR').find('.T32LTr');
		    for (var i = 0; i < info.length; i++) {
		    	index = info.eq(i).children(".T32LDIST").children(".T32LDISTInputText").val();
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
		    	index = info.eq(i).children(".T32RDIST").children(".T32RDISTInputText").val();
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
		    	index = info.eq(i).val();
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
	            data: {originalId: $("#originalId").val(),tableThree:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "3";
	            	}
	            }
	        });
		    
            break;
            
        case ('4'):
        	
        	var index = "";
	    	var infoList = "";
	    	
	    	var info = $('.DIRSHInputTr').find('.DIRSHInputData');
		    for (var i = 0; i < info.length; i++) {
	    	  	index = info.eq(i).val();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	        }
		    
		    infoList = infoList + (($('.avgDIRSHInputData').html()!="")?$('.avgDIRSHInputData').html():" ") + "#@";
		    
		    info = $('.AFInputTr').find('.realData');
	    	
		    for (var i = 0; i < info.length; i++) {
	    	  	index = info.eq(i).val();
	    	  	if(index==""){
		    		index = " ";
		    	}
	    	  	infoList = infoList + index + "#@";
	        }
		    
//		   
//		    infoList = infoList + (($('.AFInputDataOne').children(".realData").val()!="")?$('.AFInputDataOne').children(".realData").val():" ") + "#@";
//		    infoList = infoList + (($('.AFInputDataTwo').children(".realData").val()!="")?$('.AFInputDataTwo').children(".realData").val():" ") + "#@";
//		    infoList = infoList + (($('.AFInputDataThree').children(".realData").val()!="")?$('.AFInputDataThree').children(".realData").val():" ") + "#@";
		    infoList = infoList + (($('.avgAFInputData').html()!="")?$('.avgAFInputData').html():" ") + "#@";
		    infoList = infoList + (($('.AFRelative').html()!="")?$('.AFRelative').html():" ") + "#@";
		    
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableFour",
	            data: {originalId: $("#originalId").val(),tableFour:infoList},
	            async:false,
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "4";
	            	}
	            }
	        });
        	
            break;
            
        case ('5'):
        	var index = "";
	    	var infoList = "";
	    	
		    infoList = infoList + (($('.O2One').val()!="")?$('.O2One').val():" ") + "#@";
		    infoList = infoList + (($('.O2Two').val()!="")?$('.O2Two').val():" ") + "#@";
		    infoList = infoList + (($('.O2Three').val()!="")?$('.O2Three').val():" ") + "#@";
		    infoList = infoList + (($('.O2Four').html()!="")?$('.O2Four').html():" ") + "#@";
		    infoList = infoList + (($('.O2Five').val()!="")?$('.O2Five').val():" ") + "#@";
		    infoList = infoList + (($('.O2Six').html()!="")?$('.O2Six').html():" ") + "#@";
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableFive",
	            async:false,
	            data: {originalId: $("#originalId").val(),tableFive:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "5";
	            	}
	            }
	        });
            break;
            
        case ('6'):
        	var index = "";
	    	var infoList = "";
	    	
		    infoList = infoList + (($('.NoiseOne').val()!="")?$('.NoiseOne').val():" ") + "#@";
		    infoList = infoList + (($('.NoiseTwo').val()!="")?$('.NoiseTwo').val():" ") + "#@";
		    infoList = infoList + (($('.NoiseThree').val()!="")?$('.NoiseThree').val():" ") + "#@";
		    infoList = infoList + (($('.NoiseFour').html()!="")?$('.NoiseFour').html():" ") + "#@";
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableSix",
	            async:false,
	            data: {originalId: $("#originalId").val(),tableSix:infoList},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "6";
	            	}
	            }
	        });

            break;
            
        case ('7'):
        	var index = "";
	    	var infoList = "";
	    	
		    infoList = infoList + (($('.NoiwarinOne').val()!="")?$('.NoiwarinOne').val():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinTwo').val()!="")?$('.NoiwarinTwo').val():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinThree').val()!="")?$('.NoiwarinThree').val():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinFour').html()!="")?$('.NoiwarinFour').html():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinFive').val()!="")?$('.NoiwarinFive').val():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinSix').val()!="")?$('.NoiwarinSix').val():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinSeven').val()!="")?$('.NoiwarinSeven').val():" ") + "#@";
		    infoList = infoList + (($('.NoiwarinEight').html()!="")?$('.NoiwarinEight').html():" ") + "#@";
		    $.ajax({
	            type: "post",
	            url: "updateOraiginalTableSeven",
	            data: {originalId: $("#originalId").val(),tableSeven:infoList},
	            dataType: "json",
	            async:false,
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "7";
	            	}
	            }
	        });
            break;
            
        case ('8'):
		    $.ajax({
	            type: "post",
	            async:false,
	            url: "updateOraiginalTableEight",
	            data: {originalId: $("#originalId").val(),tableEight:$(".textareaRemark").val()},
	            dataType: "json",
	            success: function(data){
	            	
	            	if(data.length==0){
	            		result = "8";
	            	}
//	            	$('.box2_2').fadeOut("slow");
//	        		$("#optionsValue").val("");
	            }
	        });
            break;
        case ('9'):
//        	var index = "";
//        	var infoList = "";
//        	var info = $('.tableTitle').find(':text');
//    	    for (var i = 0; i < info.length; i++) {
//    	    	
//    	    	 index = info.eq(i).val() ;
//    	    	 if(index==""){
// 		    		index = " ";
// 		    	}
//    	    	 infoList = infoList + index + "#@";
//            }
//    	    info = $('.criterionInfo').find(':text');
//    	    for (var i = 0; i < info.length; i++) {
//    	    	
//    	    	 index = info.eq(i).val() ;
//    	    	 if(index==""){
// 		    		index = " ";
// 		    	}
//    	    	 infoList = infoList + index + "#@";
//            }
//    	    $.ajax({
//	            type: "post",
//	            url: "updateOraiginalTableTitle",
//	            async:false,
//	            data: {originalId: $("#originalId").val(),tabletitle:infoList},
//	            dataType: "json",
//	            success: function(data){
//	            	
//	            	if(data.length==0){
//	            		result = "0";
//	            	}
//	            }
//	        });
    	    
            break;
		}
		
	}
	
	function optionsShowControl(step){
		switch (step) {
        case (0):
        	$('.tableTitle').fadeIn();
        	$('.tableOne').hide();
        	$('.tableTwo').hide();
        	$('.tableThree').hide();
        	$('.tableFour').hide();
        	$('.tableFive').hide();
        	$('.tableSix').hide();
        	$('.tableSeven').hide();
        	$('.tableEight').hide();
        	$('.tableNine').hide();
        	$('.originalTitleInfoListDiv').hide();
        	$('.lastStep').hide();
        	$('.nextStep').fadeIn();
            break;
            
        case (1):
        	$('.tableTitle').hide();
	    	$('.tableOne').fadeIn();
	    	$('.tableTwo').hide();
	    	$('.tableThree').hide();
	    	$('.tableFour').hide();
	    	$('.tableFive').hide();
	    	$('.tableSix').hide();
	    	$('.tableSeven').hide();
	    	$('.tableEight').hide();
	    	$('.tableNine').hide();
	    	$('.lastStep').fadeIn();
	    	$('.nextStep').fadeIn();
            break;
      
        case (2):
        	$('.tableTitle').hide();
	    	$('.tableOne').hide();
	    	$('.tableTwo').fadeIn();
	    	$('.tableThree').hide();
	    	$('.tableFour').hide();
	    	$('.tableFive').hide();
	    	$('.tableSix').hide();
	    	$('.tableSeven').hide();
	    	$('.tableEight').hide();
	    	$('.tableNine').hide();
	    	$("#tempCenterBtn").text("控制温度：32  单位：℃");
			$(".T32").show();
			$(".T36").hide();
			$(".flagForTableTwo").val("0");
	    	$('.lastStep').fadeIn();
	    	$('.nextStep').fadeIn();
            break;
       
        case (3):
        	$('.tableTitle').hide();
	    	$('.tableOne').hide();
	    	$('.tableTwo').hide();
	    	$('.tableThree').fadeIn();
	    	$('.tableFour').hide();
	    	$('.tableFive').hide();
	    	$('.tableSix').hide();
	    	$('.tableSeven').hide();
	    	$('.tableEight').hide();
	    	$('.tableNine').hide();
	    	$('.lastStep').fadeIn();
	    	$('.nextStep').fadeIn();
            break;
            
        case (4):
        	$('.tableTitle').hide();
	    	$('.tableOne').hide();
	    	$('.tableTwo').hide();
	    	$('.tableThree').hide();
	    	$('.tableFour').fadeIn();
	    	$('.tableFive').hide();
	    	$('.tableSix').hide();
	    	$('.tableSeven').hide();
	    	$('.tableEight').hide();
	    	$('.tableNine').hide();
	    	$('.lastStep').fadeIn();
	    	$('.nextStep').fadeIn();
            break;
            
        case (5):
        	$('.tableTitle').hide();
	    	$('.tableOne').hide();
	    	$('.tableTwo').hide();
	    	$('.tableThree').hide();
	    	$('.tableFour').hide();
	    	$('.tableFive').fadeIn();
	    	$('.tableSix').hide();
	    	$('.tableSeven').hide();
	    	$('.tableEight').hide();
	    	$('.tableNine').hide();
	    	$('.lastStep').fadeIn();
	    	$('.nextStep').fadeIn();
            break;
            
        case (6):
        	$('.tableTitle').hide();
	    	$('.tableOne').hide();
	    	$('.tableTwo').hide();
	    	$('.tableThree').hide();
	    	$('.tableFour').hide();
	    	$('.tableFive').hide();
	    	$('.tableSix').fadeIn();
	    	$('.tableSeven').hide();
	    	$('.tableEight').hide();
	    	$('.tableNine').hide();
	    	$('.lastStep').fadeIn();
	    	$('.nextStep').fadeIn();
            break;
            
        case (7):
        	$('.tableTitle').hide();
	    	$('.tableOne').hide();
	    	$('.tableTwo').hide();
	    	$('.tableThree').hide();
	    	$('.tableFour').hide();
	    	$('.tableFive').hide();
	    	$('.tableSix').hide();
	    	$('.tableSeven').fadeIn();
	    	$('.tableEight').hide();
	    	$('.tableNine').hide();
	    	$('.lastStep').fadeIn();
	    	$('.nextStep').fadeIn();
            break;
            
        case (8):
        	$('.tableTitle').hide();
	    	$('.tableOne').hide();
	    	$('.tableTwo').hide();
	    	$('.tableThree').hide();
	    	$('.tableFour').hide();
	    	$('.tableFive').hide();
	    	$('.tableSix').hide();
	    	$('.tableSeven').hide();
	    	$('.tableEight').fadeIn();
	    	$('.tableNine').hide();
	    	
	    	$('.nextStep').fadeIn();
	    	$('.lastStep').fadeIn();
            break;
        
        case (9):
        	$('.tableTitle').hide();
	    	$('.tableOne').hide();
	    	$('.tableTwo').hide();
	    	$('.tableThree').hide();
	    	$('.tableFour').hide();
	    	$('.tableFive').hide();
	    	$('.tableSix').hide();
	    	$('.tableSeven').hide();
	    	$('.tableEight').hide();
	    	$('.tableNine').fadeIn();
	    	
	    	$('.nextStep').hide();
	    	$('.lastStep').fadeIn();
            break;
		}
	}
	
	$(".submitFigureData").click(function(){
		
		if($("#optionsValue").val()!=""){
			inputDataOptions($("#optionsValue").val());
			
		}else{
			
			var options = "";
			options = $("input[name='optionsRadios']:checked").val();
			inputDataOptions(options);
			
			options = "";
			
			$('input[name="checkboxOption"]:checked').each(function(){ 
				options = $(this).val();
			}); 
			inputDataOptions(options);
			
		}
	});
	$(".T32Show").click(function(){
		
		$("#tempCenterBtn").text("控制温度：32  单位：℃");
		$(".T32").show();
		$(".T36").hide();
		$(".flagForTableTwo").val("0");
		
	});
	$(".T36Show").click(function(){
		$("#tempCenterBtn").text("控制温度：36  单位：℃");
		$(".T36").show();
		$(".T32").hide();
		$(".flagForTableTwo").val("1");
	});
	$(".noOriginalId").click(function(){
		
		$(".box2_5").fadeOut("slow");
		$('.box2_3').fadeIn("slow");
	});
	$(".closebox2_5").click(function(){
		$(".box2_5").fadeOut("slow");
	});
	$(".T32CInputBtn").click(function(){
		$("#optionsValue").val("0");
		$(".box2_2").fadeOut("slow");
		$('.checkCancel').show();
		
	});
	$(".T32LInputBtn").click(function(){
		$("#optionsValue").val("1");
		$(".box2_2").fadeOut("slow");
		$('.checkCancel').show();
		
	});
	$(".T32RInputBtn").click(function(){
		$("#optionsValue").val("2");
		$(".box2_2").fadeOut("slow");
		$('.checkCancel').show();
		
	});
	$(".T36InputBtn").click(function(){
		$("#optionsValue").val("3");
		$(".box2_2").fadeOut("slow");
		$('.checkCancel').show();
	});
	
	$(".AFInputBtn").click(function(){
		$("#optionsValue").val("4");
		$(".box2_2").fadeOut("slow");
		$('.checkCancel').show();
	});
	
	function inputDataOptions(options){
		
		switch (options) {
	        
			case ('0'):
	        	
				if($(".tiltStatus").val()!='0'){
					
					if(confirm('该点不是平铺状态，是否导入？')) {
					} else {
						break;
					}
				}
		        var options = "0";
				var timeInterval = $("#timeInterval").val();
				var figureBeignTime = $("#figureBeignTime").val();
				var detectionId = $(".textDetId").html();
				$(".T32Time").val(figureBeignTime);
				if(timeInterval == "" || timeInterval == null){
					timeInterval = "1";
				}
				$.ajax({
		            type: "post",
		            url: "getFiftyPoints",
		            async : false,  
		            data: {
		            		timeInterval:timeInterval,
		            		figureBeignTime:figureBeignTime,
		            		detectionId:detectionId,
		            		options:options,
		            		sourceData:$("#sourceTmp").val()
		            	   },
		            dataType: "json",
		            success: function(data){
		            	if(data.trim()=="false"){
		            		alert("数据量太少，导入失败");
		            	}else{
		            		var array = [];
		            		var ATAvg = 0;
		            		var BTAvg = 0;
		            		var CTAvg = 0;
		            		var DTAvg = 0;
		            		var ETAvg = 0;
		            		array = data.trim().split("#@");
		            		var i =1;
		            		$("#T32 .T32CTr").each(function(){  
		            			  if(array[0]!=""){
		            				  $(this).children(".T32CDIST").children(".T32CDISTInputText").val(array[0]);
		            			  }
		            			  ATAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32CAT").html(array[i++]);
		            	          BTAvg += ( Number(array[i].trim()));
		            	          $(this).children(".T32CBT").html(array[i++]);
		            	          CTAvg += ( Number(array[i].trim()));
		            	          $(this).children(".T32CCT").html(array[i++]);
		            	          DTAvg += ( Number(array[i].trim()));
		            	          $(this).children(".T32CDT").html(array[i++]);
		            	          ETAvg += ( Number(array[i].trim()));
		            	          $(this).children(".T32CET").html(array[i++]);  
		            	     });  
		            		
		            		info = $('.T32CRevise').find(":text");
		            		for(var i = 0; i<info.length;i++){
		            			info.eq(i).val("");
		            		}
		            		
		            		info = $('.T32CAVG');
		            		
		            		if(array[0]!=""){
		            			info.eq(0).html(array[0]);
		            		}
		            		
		            		var subStr = "";
		            		
		            		ATAvg = ATAvg/15;
		            		subStr = ATAvg.toString();
		        		    info.eq(1).html(dataHandler(subStr));
		        		    BTAvg = BTAvg/15;
		        		    subStr = BTAvg.toString();
		        		    info.eq(2).html(dataHandler(subStr));
		        		    
		        		    CTAvg = CTAvg/15;
		        		    subStr = CTAvg.toString();
		        		    info.eq(3).html(dataHandler(subStr));
		        		    
		        		    DTAvg = DTAvg/15;
		        		    subStr = DTAvg.toString();
		        		    info.eq(4).html(dataHandler(subStr));
		        		    
		        		    ETAvg = ETAvg/15;
		        		    subStr = ETAvg.toString();
		        		    info.eq(5).html(dataHandler(subStr));
		        		    
		            		optionsShowControl(2);
		            		$("#getStep").val("2");
		            		$("#tempCenterBtn").text("控制温度：32  单位：℃");
		            		$(".T32").show();
		            		$(".T36").hide();
		            		$("#optionsValue").val("");
		            		$(".box2_2").fadeIn("slow");
		            		$(".box2_4").fadeOut("slow");
		            		
		            	}
		            	T32ChangeTrigger();
		            }
		        });
				if($(".T36Time").val().trim()!=""&&$(".T32Time").val().trim()!=""){
						var Tbegin = $(".T32Time").val();
						var Tend = $(".T36Time").val();
						$.ajax({
				            type: "post",
				            url: "getMaxValue32To36",
				            data: {Tbegin:Tbegin,Tend:Tend,detectionId:detectionId,sourceData:$("#sourceTmp").val()},
				            dataType: "json",
				            success: function(data){
				            	
				            	var str = (Number(data)*1000 - 36000)/1000;
				            	
				            	$(".tableTwoSaveTwo").html(dataHandler(data));
				            	$(".tableTwoSaveThree").html(dataHandler(str));
				            }
						});
					}
	            break;
	            
	        case ('1'):
	        	
	        	if($(".tiltStatus").val()!='1'){
					
					if(confirm('该点不是左倾状态，是否导入？')) {
					} else {
						break;
					}
				}
	        	
	        	var options = "1";
				var timeInterval = $("#timeInterval").val();
				var figureBeignTime = $("#figureBeignTime").val();
				var detectionId = $(".textDetId").html();
				
				if(timeInterval == "" || timeInterval == null){
					timeInterval = "1";
				}
				$.ajax({
		            type: "post",
		            url: "getFiftyPoints",
		            data: {timeInterval:timeInterval,figureBeignTime:figureBeignTime,
		            		detectionId:detectionId,options:options,sourceData:$("#sourceTmp").val()},
		            dataType: "json",
		            success: function(data){
		            	if(data.trim()=="false"){
		            		alert("数据量太少，导入失败");
		            	}else{
		            		var array = [];
		            		
		            		var ATAvg = 0;
		            		var BTAvg = 0;
		            		var CTAvg = 0;
		            		var DTAvg = 0;
		            		var ETAvg = 0;
		            		
		            		array = data.trim().split("#@");
		            		var i =1;
		            		$("#T32LR .T32LTr").each(function(){  
		            	          $(this).children(".T32LDIST").children(".T32LDISTInputText").val(array[0]);
		            	          ATAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32LAT").html(array[i++]);
		            	          BTAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32LBT").html(array[i++]);
		            	          CTAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32LCT").html(array[i++]); 
		            	          DTAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32LDT").html(array[i++]); 
		            	          ETAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32LET").html(array[i++]);  
		            	     }); 
		            		$("#T32LR .T32RTr").each(function(){  
		            	          ATAvg += Number($(this).children(".T32RAT").html());
		            	          BTAvg += Number($(this).children(".T32RBT").html());
		            	          CTAvg += Number($(this).children(".T32RCT").html());
		            	          DTAvg += Number($(this).children(".T32RDT").html());
		            	          ETAvg += Number($(this).children(".T32RET").html());
		            	     }); 
		            		
		            		info = $('.T32LRRevise').find(":text");
		            		for(var i = 0; i<info.length;i++){
		            			info.eq(i).val("");
		            		}
		            		
		            		var subStr = "";
		            		info = $('.T32LRAVG');
		            		info2 = $('.T32LRUniformity');
		            		
		            		ATAvg = ATAvg/15;
		            		
		            		var subStrAT = ATAvg.toString();
		        		    if(subStrAT.length>5){
		        		    	subStrAT = subStrAT.substring(0,6);
		        		    }
		        		    info.eq(0).html(dataHandler(subStrAT));
		        		    BTAvg = BTAvg/15;
		        		    subStr = BTAvg.toString();
		        		    info.eq(1).html(dataHandler(subStr));

		        		    var strTAvg = (Number(info.eq(1).html())*1000-Number(info.eq(0).html())*1000)/1000;
		        		    info2.eq(0).html(dataHandler(strTAvg.toString()));
		        		    
		        		    CTAvg = CTAvg/15;
		        		    subStr = CTAvg.toString();
		        		    info.eq(2).html(dataHandler(subStr));
		        		    strTAvg = (Number(info.eq(2).html())*1000-Number(info.eq(0).html())*1000)/1000;
		        		    info2.eq(1).html(dataHandler(strTAvg.toString()));
		        		    
		        		    DTAvg = DTAvg/15;
		        		    subStr = DTAvg.toString();
		        		    info.eq(3).html(dataHandler(subStr));
		        		    strTAvg = (Number(info.eq(3).html())*1000-Number(info.eq(0).html())*1000)/1000;
		        		    info2.eq(2).html(dataHandler(strTAvg.toString()));
		        		    
		        		    ETAvg = ETAvg/15;
		        		    subStr = ETAvg.toString();
		        		    info.eq(4).html(dataHandler(subStr));
		        		    strTAvg = (Number(info.eq(4).html())*1000-Number(info.eq(0).html())*1000)/1000;
		        		    info2.eq(3).html(dataHandler(strTAvg.toString()));
		        		    
		            		optionsShowControl(3);
		            		$("#getStep").val("3");
		            		$("#optionsValue").val("");
		            		$(".box2_2").fadeIn("slow");
		            		$(".box2_4").fadeOut("slow");
		            		
		            	}
		            }
		        });
	        	
	        	break;
	        
	        case ('2'):
	        	
	        	
	        	if($(".tiltStatus").val()!='2'){
					
					if(confirm('该点不是右倾状态，是否导入？')) {
					} else {
						break;
					}
				}
	        	
	        	var options = "2";
				var timeInterval = $("#timeInterval").val();
				var figureBeignTime = $("#figureBeignTime").val();
				var detectionId = $(".textDetId").html();
				
				if(timeInterval == "" || timeInterval == null){
					timeInterval = "1";
				}
				$.ajax({
		            type: "post",
		            url: "getFiftyPoints",
		            data: {timeInterval:timeInterval,figureBeignTime:figureBeignTime,
		            		detectionId:detectionId,options:options,sourceData:$("#sourceTmp").val()},
		            dataType: "json",
		            success: function(data){
		            	if(data.trim()=="false"){
		            		alert("数据量太少，导入失败");
		            	}else{
		            		var array = [];

		            		var ATAvg = 0;
		            		var BTAvg = 0;
		            		var CTAvg = 0;
		            		var DTAvg = 0;
		            		var ETAvg = 0;
		            		
		            		array = data.trim().split("#@");
		            		var i =1;
		            		$("#T32LR .T32RTr").each(function(){  
		            	          $(this).children(".T32RDIST").children(".T32RDISTInputText").val(array[0]);
		            	          ATAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32RAT").html(array[i++]);
		            	          BTAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32RBT").html(array[i++]); 
		            	          CTAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32RCT").html(array[i++]); 
		            	          DTAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32RDT").html(array[i++]); 
		            	          ETAvg += (Number(array[i].trim()));
		            	          $(this).children(".T32RET").html(array[i++]);  
		            	     });  
		            		$("#T32LR .T32LTr").each(function(){  
		            			 ATAvg += (Number($(this).children(".T32LAT").html()));
		            	         BTAvg += (Number($(this).children(".T32LBT").html()));
		            	         CTAvg += (Number($(this).children(".T32LCT").html()));
		            	         DTAvg += (Number($(this).children(".T32LDT").html()));
		            	         ETAvg += (Number($(this).children(".T32LET").html()));
		            	     }); 
		            		optionsShowControl(3);
		            		$("#getStep").val("3");
		            		$("#optionsValue").val("");
		            		$(".box2_2").fadeIn("slow");
		            		$(".box2_4").fadeOut("slow");
		            		
		            		info = $('.T32LRRevise').find(":text");
		            		info2 = $('.T32LRUniformity');
		            		for(var i = 0; i<info.length;i++){
		            			info.eq(i).val("");
		            		}
		            		
		            		var subStr = "";
		            		info = $('.T32LRAVG');

		            		ATAvg = ATAvg/15;
		            		var subStrAT = ATAvg.toString();
		        		    if(subStrAT.length>5){
		        		    	subStrAT = subStrAT.substring(0,6);
		        		    }
		        		    info.eq(0).html(dataHandler(subStrAT));
		        		    
		        		    BTAvg = BTAvg/15;
		        		    subStr = BTAvg.toString();
		        		    info.eq(1).html(dataHandler(subStr));
		        		    
		        		    var strTAvg = (Number(info.eq(1).html())*1000-Number(info.eq(0).html())*1000)/1000;
		        		    info2.eq(0).html(dataHandler(strTAvg.toString()));
		        		    
		        		    CTAvg = CTAvg/15;
		        		    subStr = CTAvg.toString();
		        		    info.eq(2).html(dataHandler(subStr));
		        		    strTAvg = (Number(info.eq(2).html())*1000-Number(info.eq(0).html())*1000)/1000;
		        		    info2.eq(1).html(dataHandler(strTAvg.toString()));
		        		    
		        		    
		        		    DTAvg = DTAvg/15;
		        		    subStr = DTAvg.toString();
		        		    info.eq(3).html(dataHandler(subStr));
		        		    strTAvg = (Number(info.eq(3).html())*1000-Number(info.eq(0).html())*1000)/1000;
		        		    info2.eq(2).html(dataHandler(strTAvg.toString()));
		        		    
		        		    ETAvg = ETAvg/15;
		        		    subStr = ETAvg.toString();
		        		    info.eq(4).html(dataHandler(subStr));
		        		    strTAvg = (Number(info.eq(4).html())*1000-Number(info.eq(0).html())*1000)/1000;
		        		    info2.eq(3).html(dataHandler(strTAvg.toString()));
		            	}
		            }
		        });
	        	break;
	        
	        case ('3'):
	        	
	        	if($(".tiltStatus").val()!='0'){
					
					if(confirm('该点不是平铺状态，是否导入？')) {
					} else {
						break;
					}
				}
	        
	        	var options = "3";
				var timeInterval = $("#timeInterval").val();
				var figureBeignTime = $("#figureBeignTime").val();
				var detectionId = $(".textDetId").html();
				
				 $(".T36Time").val(figureBeignTime);
				
				if(timeInterval == "" || timeInterval == null){
					timeInterval = "1";
				}
				$.ajax({
		            type: "post",
		            url: "getFiftyPoints",
		            data: {timeInterval:timeInterval,figureBeignTime:figureBeignTime,
		            		detectionId:detectionId,options:options,sourceData:$("#sourceTmp").val()},
		            dataType: "json",
		            success: function(data){
		            	if(data.trim()=="false"){
		            		alert("数据量太少，导入失败");
		            	}else{
		            		var array = [];
		            		var ATAvg = 0;
		            		var BTAvg = 0;
		            		var CTAvg = 0;
		            		var DTAvg = 0;
		            		var ETAvg = 0;
		            		
		            		array = data.trim().split("#@");
		            		
		            		var i =1;
		            		var kkkk = $("#T36 .T36Tr");
		            		$("#T36 .T36Tr").each(function(){ 
		            			 if(array[0].toString().trim()!=""){
		            				  $(this).children(".T36DIST").children(".T36DISTInputText").val(array[0].toString().trim());
		            			  }
		            			  ATAvg += (   Number(array[i].trim()));
		            			  $(this).children(".T36AT").html(array[i++]);
		            			  BTAvg += (   Number(array[i].trim()));
		            	          $(this).children(".T36BT").html(array[i++]);  
		            	          CTAvg += (   Number(array[i].trim()));
		            	          $(this).children(".T36CT").html(array[i++]); 
		            	          DTAvg += (   Number(array[i].trim()));
		            	          $(this).children(".T36DT").html(array[i++]);  
		            	          ETAvg += (   Number(array[i].trim()));
		            	          $(this).children(".T36ET").html(array[i++]);  
		            	     });  
		            		
		            		info = $('.T36Revise').find(":text");
		            		for(var i = 0; i<info.length;i++){
		            			info.eq(i).val("");
		            		}
		            		
		            		info = $('.T36AVG');
		            		if(array[0]!=""){
		            			info.eq(0).html(array[0]);
		            		}
		            		
		            		var subStr = "";
		            		
		            		ATAvg = ATAvg/15;
		            		subStr = ATAvg.toString();
		        		    info.eq(1).html(dataHandler(subStr));
		        		    
		        		    BTAvg = BTAvg/15;
		        		    subStr = BTAvg.toString();
		        		    info.eq(2).html(dataHandler(subStr));
		        		    
		        		    CTAvg = CTAvg/15;
		        		    subStr = CTAvg.toString();
		        		    info.eq(3).html(dataHandler(subStr));
		        		    
		        		    DTAvg = DTAvg/15;
		        		    subStr = DTAvg.toString();
		        		    info.eq(4).html(dataHandler(subStr));
		        		    
		        		    ETAvg = ETAvg/15;
		        		    subStr = ETAvg.toString();
		        		    info.eq(5).html(dataHandler(subStr));
		            		
		        		    var tableTwoSaveOneStr = (Number(info.eq(1).html().trim())*1000-36000)/1000;
		        		    
		        		    $(".tableTwoSaveOne").html(dataHandler(tableTwoSaveOneStr.toString()));
		        		    
		            		optionsShowControl(2);
		            		$("#getStep").val("2");
		            		$(".flagForTableTwo").val("1");
		            		$("#tempCenterBtn").text("控制温度：36  单位：℃");
		            		$(".T36").show();
		            		$(".T32").hide();
		            		$("#optionsValue").val("");
		            		$(".box2_2").fadeIn("slow");
		            		$(".box2_4").fadeOut("slow");
		            		
		            	}
		            	T36ChangeTrigger();
		            }
		        });
				
				if($(".T36Time").val()!=""&&$(".T32Time").val()!=""){
					var Tbegin = $(".T32Time").val();
					var Tend = $(".T36Time").val();
					$.ajax({
			            type: "post",
			            url: "getMaxValue32To36",
			            data: {Tbegin:Tbegin,Tend:Tend,detectionId:detectionId,sourceData:$("#sourceTmp").val()},
			            dataType: "json",
			            success: function(data){
			            	
			            	if(data<=$(".T36AT").eq(0).html()){
			            		
			            		alert("根据32&36℃所导入的数据，36℃测试点温度可能未处于平衡状况！");
			            	
			            	}
			            	
			            	var str = (Number(data)*1000 - 36000)/1000;
			            	$(".tableTwoSaveTwo").html(dataHandler(data));
			            	$(".tableTwoSaveThree").html(dataHandler(str.toString()));
			            }
					});
				}
				
				if($(".AVGOne").html().trim()==""){
					
					$(".T32deviation").html("");
					$(".T32Wave").html("");
					$(".UniformityT32Avg").html("");
				}
				if($(".AVGTwo").html().trim()==""){
					
					$(".T36deviation").html("");
					$(".T36Wave").html("");
					$(".UniformityT36Avg").html("");
					
				}
				
	        	break;
	        
	        case ('4'):
	        	
	        	if($(".tiltStatus").val()!='0'){
					
					if(confirm('该点不是平铺状态，是否导入？')) {
					} else {
						break;
					}
				}
	        	
	        	var options = "4";
				var timeInterval = $("#timeInterval").val();
				var figureBeignTime = $("#figureBeignTime").val();
				var detectionId = $(".textDetId").html();
				
				if(timeInterval == "" || timeInterval == null){
					timeInterval = "1";
				}
				$.ajax({
		            type: "post",
		            url: "getFiftyPoints",
		            data: {timeInterval:timeInterval,figureBeignTime:figureBeignTime,
		            		detectionId:detectionId,options:options,sourceData:$("#sourceTmp").val()},
		            dataType: "json",
		            success: function(data){
		            	if(data.trim()=="false"){
		            		alert("数据量太少，导入失败");
		            	}else{
		            		
		            		var str = [];
		            		str = data.trim().split("&*");
		            		var array = [];
		            		array = str[1].trim().split("#@");
		            		var info = $('.DIRSHInputTr').find(':text');
		            		if(array[0].trim()!=""){
		            			 for (var i = 0; i < info.length; i++) {
			            	    	 info.eq(i).val(array[0]) ;
			            	    	 
			                    }
		            			 $(".avgDIRSHInputData").html(array[0]);
		            		}
		            		
		            		info = $('.AFInputTr').find('.realData');
		         	    	
		         		    for (var i = 0; i < info.length; i++) {
		         		    	 info.eq(i).val(array[i+1]) ;
		         	        }
		         		    var avg = (Number(array[1])+Number(array[2])
		            				+Number(array[3]))/3;
		         		    
		            		var subStr = "";
		         		    subStr = avg.toString();
		         		    
		            		$(".avgAFInputData").html(dataHandler(subStr));
		            		if($(".avgDIRSHInputData").html().trim()!=""&&$(".avgAFInputData").html().trim()!=""){
		            			 var str = (Number($(".avgDIRSHInputData").html())*1000-Number($(".avgAFInputData").html())*1000)/1000;
				         		 $(".AFRelative").html(dataHandler(str.toString()));
		            		}
		            		
		            		if(	$("#optionsValue").val().trim()!=""){
		            			optionsShowControl(4);
		            			$("#getStep").val("4");
			            		$("#optionsValue").val("");
			            		$(".box2_2").fadeIn("slow");
			            		$(".box2_4").fadeOut("slow");
		            		}
		            		
		            	}
		            }
		        });
	        	
	        	break;
	        case ('5'):
	        	var strTableHead = $(".tableHeadTmp").val().split('#@');
	        	if(strTableHead.length>8){
	        		var eq = $(".titleTable").find(':text');
	        		eq.eq(0).val(strTableHead[0]);
	        		eq.eq(1).val(strTableHead[1]);
	        		eq.eq(2).val(strTableHead[2]);
	        		eq.eq(3).val(strTableHead[3]);
	        		eq.eq(4).val(strTableHead[4]);
	        		eq.eq(5).val(strTableHead[5]);
	        		eq.eq(6).val(strTableHead[6]);
	        		eq.eq(7).val(strTableHead[7]);
	        		eq.eq(8).val(strTableHead[8]);
	        	}
    			optionsShowControl(0);
    			$("#getStep").val("0");
        		$("#optionsValue").val("");
        		$(".box2_2").fadeIn("slow");
        		$(".box2_4").fadeOut("slow");
		            		
	        	break;
		}
	}
		  
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
	  		
  		    info = $('.AFInputTr').find(':text');
	 	    for (var i = 0; i < info.length; i++) {
	 	    	if(info.eq(i).val().trim() ==""){
	 	    		temp = false;
	 	    	}
	        }
	 	    if(temp){
	 	    	var str = (Number(subStr)*1000-Number($(".avgAFInputData").html())*1000)/1000;
	 			$(".AFRelative").html(dataHandler(str.toString()));
	 	    }
  		    
    	}else{
    		$(".avgDIRSHInputData").html("");
    		$(".AFRelative").html("");
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
		    
    		info = $('.DIRSHInputTr').find(':text');
  		    for (var i = 0; i < info.length; i++) {
	 	    	if(info.eq(i).val().trim() ==""){
	 	    		temp = false;
	 	    	}
	        }
	 	    if(temp){
	 	    	 var str = (Number($(".avgDIRSHInputData").html())*1000-Number(subStr)*1000)/1000;
	 		    $(".AFRelative").html(dataHandler(str.toString()));
	 	    }
		   
    	}else{
    		$(".avgAFInputData").html("");
    		$(".AFRelative").html("");
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
  		    
    	}else{
    		$(".O2Four").html("");
    	}
	    if(temp&&$('.O2Five').val().trim()!=""){
	    	
	    	var O2Str =  (Number($(".O2Four").html())*1000 -  Number($(".O2Five").val())*1000)/(Number($(".tableFiveR").val())*10);
	    	
	    	$('.O2Six').html(dataHandler(O2Str.toString().substring(0,6)));
	    	
	    }else{
	    	$('.O2Six').html("");
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
	    	
	    }else{
	    	$('.O2Six').html("");
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
  		    
    	}else{
    		$(".NoiseFour").html("");
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
  		    
    	}else{
    		$(".NoiwarinFour").html("");
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
		}else{
			 $(".NoiwarinEight").html("");
		}
		 
	}); 
	$('.T32CDISTInputText').bind('input propertychange', function() {  
	    
		T32ChangeTrigger();
		 
	});
	
	function T32ChangeTrigger(){
		
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
		}
	}
	
	
	$('.T36DISTInputText').bind('input propertychange', function() {  
	    
		T36ChangeTrigger();
		 
	});
	
	$('.ctrId').bind('input propertychange', function() { 
		if($(".ctrId").val()!=""){
			$.ajax({
	            type: "post",
	            url: "criterionInfoMatch",
	            data: {ctrId:$(".ctrId").val().trim()},
	            dataType: "json",
	            success: function(data){
	            	if(data.length>0){
	            		$(".criterionInfoListDiv").fadeIn("slow");
	            		$("#criterionInfoList tr:gt(0)").remove();
						var str = "";
						for(var i = 0; i<data.length; i++){
							str += "<tr class='ctrInfoTr' style='background-color: #f9f9f9;cursor:pointer;'><td>"
								+ data[i].date + "</td><td>"
								+ data[i].ctrName + "</td><td>"
								+ data[i].ctrId + "</td><td style = 'display:none'>"
								+ data[i].completeInfo + "</td></tr>";
						}
						$("#criterionInfoList").append(str);
	            	}else{
	            		$(".criterionInfoListDiv").fadeOut("slow");
	            	}
	            }
			});
		}else{
    		$(".criterionInfoListDiv").fadeOut("slow");
    	}
	});
	
	var ctrInfoStrTmp = "";
	$("#criterionInfoList").delegate(".ctrInfoTr", "mouseover", function(){
		
		var info = $('.criterionInfo').find(':text');
		var index = "";
	    for (var i = 0; i < info.length; i++) {
	    	 index = info.eq(i).val();
	    	 if(index==""){
		    		index = " ";
		     }
	    	 ctrInfoStrTmp = ctrInfoStrTmp + index + "#@";
        }
	    var str = $(this).children('td').eq(3).text().split("#@");
	    for (var i = 0; i < info.length; i++) {
	    	 info.eq(i).val(str[i]) ;
        }
		$(this).css("background-color","#1abd9d");
		$(this).css("color","#FFF");
		$(this).css("font-size","larger");
	});
	$("#criterionInfoList").delegate(".ctrInfoTr", "mouseout", function(){
		var info = $('.criterionInfo').find(':text');
		var str = ctrInfoStrTmp.split("#@");
		for (var i = 0; i < info.length; i++){
	    	 info.eq(i).val(str[i]) ;
        }
		ctrInfoStrTmp = "";
		$(this).css("background-color","#f9f9f9");
		$(this).css("color","#000");
		$(this).css("font-size","large");
	});
	$("#criterionInfoList").delegate(".ctrInfoTr", "click", function(){
		var info = $('.criterionInfo').find(':text');
		ctrInfoStrTmp = $(this).children('td').eq(3).text();
		var str = ctrInfoStrTmp.split("#@");
	    for (var i = 0; i < info.length; i++) {
	    	 info.eq(i).val(str[i]) ;
        }
	    $(".criterionInfoListDiv").fadeOut('slow');
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$('.tableHeadCpy').bind('input propertychange', function() { 
		
		if($(".tableHeadCpy").val()!=""||$(".tableHeadModelId").val()!=""){
			$.ajax({
	            type: "post",
	            url: "matchTableHeadInfo",
	            data: {tableHeadName:$(".tableHeadCpy").val().trim(),modelId:$(".tableHeadModelId").val().trim()},
	            dataType: "json",
	            success: function(data){
	            	if(data.length>0){
	            		$(".originalTitleInfoListDiv").fadeIn("slow");
	            		$("#originalTitleInfoList tr:gt(0)").remove();
						var str = "";
						for(var i = 0; i<data.length; i++){
							str += "<tr class='infoTr'  style='background-color: #f9f9f9'><td>" + data[i].date + "</td><td>" 
								+ data[i].cpyInfo + "</td><td>" 
								+ data[i].modelId + "</td><td>"
								+ data[i].detectionId + "</td>"
								+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
						}
						$("#originalTitleInfoList").append(str);
	            	}else{
	            		$(".originalTitleInfoListDiv").fadeOut("slow");
	            	}
	            }
			});
		}else{
    		$(".originalTitleInfoListDiv").fadeOut("slow");
    	}
	});
	
	$('.tableHeadModelId').bind('input propertychange', function() { 
		
		if($(".tableHeadCpy").val().trim()!=""||$(".tableHeadModelId").val()!=""){
			$.ajax({
	            type: "post",
	            url: "matchTableHeadInfo",
	            data: {tableHeadName:$(".tableHeadCpy").val().trim(),modelId:$(".tableHeadModelId").val().trim()},
	            dataType: "json",
	            success: function(data){
	            	if(data.length>0){
	            		$(".originalTitleInfoListDiv").fadeIn("slow");
	            		$("#originalTitleInfoList tr:gt(0)").remove();
						var str = "";
						for(var i = 0; i<data.length; i++){
							str += "<tr class='infoTr'  style='background-color: #f9f9f9'><td>" + data[i].date + "</td><td>" 
								+ data[i].cpyInfo + "</td><td>" 
								+ data[i].modelId + "</td><td>"
								+ data[i].detectionId + "</td>"
								+ "<td style = 'display:none'>" + data[i].completeInfo + "</td></tr>";
						}
						$("#originalTitleInfoList").append(str);
	            	}else{
	            		$(".originalTitleInfoListDiv").fadeOut("slow");
	            	}
	            }
			});
		}else{
    		$(".originalTitleInfoListDiv").fadeOut("slow");
    	}
	});
	var titleInfoStrTmp = "";
	$("#originalTitleInfoList").delegate(".infoTr", "mouseover", function(){
		
		var info = $('.tableTitle').find(':text');
		var index = "";
	    for (var i = 0; i < info.length; i++) {
	    	 index = info.eq(i).val();
	    	 if(index==""){
		    		index = " ";
		     }
	    	 titleInfoStrTmp = titleInfoStrTmp + index + "#@";
        }
	    
	    var str = $(this).children('td').eq(4).text().split("#@");
	    info.eq(0).val(str[0]) ;
	    info.eq(1).val(str[3]) ;
	    info.eq(2).val(str[5]) ;
	    info.eq(3).val(str[2]) ;
	    info.eq(4).val(str[7]) ;
	    info.eq(5).val(str[8]) ;
	    info.eq(6).val(str[4]) ;
	    info.eq(7).val(str[9]) ;
	    info.eq(8).val(str[10]) ;
	    info.eq(9).val(str[13]) ;
	    info.eq(10).val(str[15]) ;
	    info.eq(11).val(str[16]) ;
	    $(".uncertainty").val(str[14]);
	    
	    $(this).css("background-color","#1abd9d");
		$(this).css("color","#FFF");
		$(this).css("font-size","larger");
	});
	$("#originalTitleInfoList").delegate(".infoTr", "mouseout", function(){
		var info = $('.tableTitle').find(':text');
		var str = titleInfoStrTmp.split("#@");
		for (var i = 0; i < info.length; i++) {
	    	 info.eq(i).val(str[i]) ;
        }
		titleInfoStrTmp = "";
		$(this).css("background-color","#f9f9f9");
		$(this).css("color","#000");
		$(this).css("font-size","large");
	});
	$("#originalTitleInfoList").delegate(".infoTr", "click", function(){
		var info = $('.tableTitle').find(':text');
		titleInfoStrTmp = $(this).children('td').eq(4).text();
		var str = titleInfoStrTmp.split("#@");
		info.eq(0).val(str[0]) ;
	    info.eq(1).val(str[3]) ;
	    info.eq(2).val(str[5]) ;
	    info.eq(3).val(str[2]) ;
	    info.eq(4).val(str[7]) ;
	    info.eq(5).val(str[8]) ;
	    info.eq(6).val(str[4]) ;
	    info.eq(7).val(str[9]) ;
	    info.eq(8).val(str[10]) ;
	    info.eq(9).val(str[13]) ;
	    info.eq(10).val(str[15]) ;
	    info.eq(11).val(str[16]) ;
	    $(".uncertainty").val(str[14]);
	    $(".originalTitleInfoListDiv").fadeOut('slow');
	    
	    var index = "";
	    titleInfoStrTmp = "";
	    for (var i = 0; i < info.length; i++) {
	    	 index = info.eq(i).val();
	    	 if(index==""){
		    		index = " ";
		     }
	    	 titleInfoStrTmp = titleInfoStrTmp + index + "#@";
        }
	    
	});
	
	$(".tableHeadCpy").focus(function(){ 
		$(".tableHeadClear").fadeIn("slow");
		$(".tableHeadModelId").css("color","red");
	}); 
	$(".tableHeadCpy").blur(function(){ 
		$(".tableHeadClear").fadeOut("slow");
		$(".tableHeadModelIdClear").fadeOut("slow");
		$(".tableHeadModelId").css("color","#000");
	}); 
	
	$(".tableHeadModelId").focus(function(){ 
		$(".tableHeadModelIdClear").fadeIn("slow");
		$(".tableHeadCpy").css("color","red");
	}); 
	$(".tableHeadModelId").blur(function(){ 
		$(".tableHeadModelIdClear").fadeOut("slow");
		$(".tableHeadClear").fadeOut("slow");
		$(".tableHeadCpy").css("color","#000");
	}); 
	
	$(".tableHeadClear").click(function(){
		$(".tableHeadCpy").val('');
	});
	$(".tableHeadModelIdClear").click(function(){
		$(".tableHeadModelId").val('');
	});
	
	function T36ChangeTrigger(){
		
		var info = $('.T36DISTInputText');
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
		}
	    else{
	    	 $(".AVGTwo").html("");
	    	 $(".T36deviation").html("");
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
	
});
$(document).ready(function(){
	var endDate = $("#endDate").val();
	var beginDate = $("#beginDate").val();
	var detectionId = $("#detectionId").val();
	$('.Search').click(function(){
		
		$("#sourceDataShowInSearch").html("数据源：采集板数据");
		$('.regionInfoDiv').hide("slow");
		
		endDate = $("#regionInfo").val();
		beginDate = $("#beginDate").val();
		detectionId = $("#detectionId").val();
		$("#beginDate").val("");
		$("#regionInfo").val("");
		$("#detectionId").val("");
		
		$("#searchItems").show();
		$("#selectIdDiv").hide();
		$("#dataSourceDiv").val("0");
		$('.box2').fadeIn("slow");
		$('.box2_3').fadeOut("slow");
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
		
		$("#dataSource").val($("#dataSourceDiv").val());
		searchFigureData();
	});
	
	$(".historyData").click(function(){
		$("#sourceDataShowInSearch").html("数据源：采集板数据");
		$('.regionInfoDiv').fadeOut("slow");
		$("#dataSourceDiv").val("0");
		
		endDate = $("#regionInfo").val();
		beginDate = $("#beginDate").val();
		detectionId = $("#detectionId").val();
		$("#beginDate").val("");
		$("#regionInfo").val("");
		$("#detectionId").val("");
		
	}); 
	$(".realtimeData").click(function(){
		$("#sourceDataShowInSearch").html("数据源：实时数据");
		$('.regionInfoDiv').fadeIn("slow");
		$("#dataSourceDiv").val("1");
		
		endDate = $("#regionInfo").val();
		beginDate = $("#beginDate").val();
		detectionId = $("#detectionId").val();
		$("#beginDate").val("");
		$("#regionInfo").val("");
		$("#detectionId").val("");
		
	}); 
	
	$(".intervalDataDropDown").click(function(){
		if($(this).text()!="全显"){
			$("#intervalTime").val($(this).text());
		}else{
			$("#intervalTime").val("00:00:00--23:59:59");
		}
		$("#beginDate").val($("#timeTmp").val());
		$("#detectionId").val($(".textDetId").text());
		$('#historyInfoSearch').submit();
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
	            	 endDate:$("#beginDate").val(), 
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
	
	$('.timeSelection').click(function(){
		$.ajax({
            type: "post",
            url: "getDetectionIds",
            data: {
           	 beginDate:"", 
           	 endDate:"", 
           	 regionInfo:"",
           	 dataSource:$("#dataSource").val(),
           	 detectionId:$(".textDetId").html()
           	 },
            dataType: "json",
            success: function(data){
           	 if(data.length > 1){
           	     $('.box2').fadeIn("slow");
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
		
	});
	$('.previousTime').click(function(){
		$.ajax({
            type: "post",
            url: "previousDetections",
            data: {
           	 beginDate:$("#timeTmp").val(), 
           	 endDate:"", 
           	 regionInfo:"",
           	 dataSource:$("#dataSource").val(),
           	 detectionId:$(".textDetId").html()
           	 },
            dataType: "json",
            success: function(data){
        	   if(data.length < 1){
        		   alert("当前日期之前无数据！");
           	   }
        	   if(data.length >0){
        		   
        		   for(var i=0; i<1; i++){
        			   $("#beginDate").val(data[i].date);
        			   $("#detectionId").val(data[i].id);
             	   }
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
            }
        });
		
	});
	
	$('.nextTime').click(function(){
		$.ajax({
            type: "post",
            url: "nextDetections",
            data: {
           	 beginDate:$("#timeTmp").val(), 
           	 endDate:"", 
           	 regionInfo:"",
           	 dataSource:$("#dataSource").val(),
           	 detectionId:$(".textDetId").html()
           	 },
            dataType: "json",
            success: function(data){
        	   if(data.length < 1){
        		   alert("当前日期之后无数据！");
           	   }
        	   if(data.length >0){
        		   
        		   for(var i=0; i<1; i++){
        			   $("#beginDate").val(data[i].date);
        			   $("#detectionId").val(data[i].id);
             	   }
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
            }
        });
		
	});
});

