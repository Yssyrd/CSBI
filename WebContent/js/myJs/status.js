function commStatus() {
	
	$.ajax({
        type: "post",
        url: "getCommStatus",
        async:false, 
        dataType: "json",
        success: function(data){
        	
        	if(data=="0"){
        		$(".commImg").attr("src","css/4.png");
        		$(".commImg").attr("alt","通讯失败");
        	}
        	if(data=="1"){
        		$(".commImg").attr("src","css/5.png");
        		$(".commImg").attr("alt","通讯正常");
        	}
        	
        }
    });
	
}