$(document).ready(function () {
	
//	var menuLeft = document.getElementById( 'cbp-spmenu-s1' );
//	var Left = document.getElementById( 'showLeft' );
//	classie.toggle( Left, 'active' );
//	classie.toggle( menuLeft, 'cbp-spmenu-open' );
//	disableOther( 'showLeft' );
	
	var intervalFlag = setInterval(function() { commStatus() } , 900);
	var myChart = echarts.init(document.getElementById('main'));
	window.onresize = myChart.resize;
	var option = {
			title : {
				text : '婴儿培养箱各参数关系图' ,
				x : 'center',
				textStyle:{
					fontSize:'36'
	            }
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					animation : true
				}
			},
			legend : {
				show : true,
				data : [ "", '温度A', '温度B', '温度C', '温度D',  '温度E','湿度A',"",
						"被检仪器编号", "控制温度", "箱外报警噪声", "显示温度", "舱内报警噪声",
						"舱内噪声", "显示湿度",  "显示风速", "倾斜角","表头信息" ],
				selected : {
					'温度B' : false,
					'温度C' : false,
					'温度D' : false,
					'温度E' : false
				},
				y : 'bottom',
				x : 'left',
				left : 50,
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
				top : '90%',
				xAxisIndex : [ 0, 1 ]
			}, {
				type : 'inside',
				realtime : true,
				start : 0,
				end : 100,
				top : '90%',
				xAxisIndex : [ 0, 1 ]
			} ],
			grid : [ {
				top : 80,
				left : 50,
				right : 50,
				height : '50%'
			}, {
				left : 50,
				right : 50,
				top : '71%',
				height : '18%'
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
				name : '湿度(%RH)',
				type : 'value',
				scale : true,
				
			}, {
				gridIndex : 1,
				type : 'value',
				inverse : true
			}, {
				name : '温度(℃)',
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
				yAxisIndex : 2,
				type : 'line',
				smooth : true,
				clickable : true,
				symbolSize : 8,
				hoverAnimation : false,
				data : []
			}, {
				name : '温度B',
				yAxisIndex : 2,
				type : 'line',
				smooth : true,
				clickable : true,
				symbolSize : 8,
				hoverAnimation : false,
				data : []
			}, {
				name : '温度C',
				yAxisIndex : 2,
				type : 'line',
				smooth : true,
				clickable : true,
				symbolSize : 8,
				hoverAnimation : false,
				data : []
			}, {
				name : '温度D',
				yAxisIndex : 2,
				type : 'line',
				smooth : true,
				clickable : true,
				symbolSize : 8,
				hoverAnimation : false,
				data : []
			}, {
				name : '温度E',
				yAxisIndex : 2,
				clickable : true,
				type : 'line',
				smooth : true,
				//			itemStyle: {normal: {areaStyle: {type: 'default'}}},
				symbolSize : 8,
				hoverAnimation : false,
				data : []
			}, {
				name : '湿度A',
				clickable : true,
 				type : 'line',
				smooth : true,
				symbolSize : 8,
				hoverAnimation : false,
				data : []
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '显示温度',
				type : 'scatter',
				data : []
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '显示风速',
				type : 'scatter',
				data : []
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '控制温度',
				type : 'scatter',
				data : []
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '箱外报警噪声',
				type : 'scatter',
				data : []
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '舱内报警噪声',
				type : 'scatter',
				data : []
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '舱内噪声',
				type : 'scatter',
				data : []
			}, {
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '显示湿度',
				type : 'scatter',
				data : []
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
				data : []
			},{
				xAxisIndex : 1,
				yAxisIndex : 1,
				symbolSize : 20,
				name : '被检仪器编号',
				type : 'scatter',
				data : []
			},{
				xAxisIndex : 1,
				yAxisIndex : 3,
				symbolSize : 28,
				name : '表头信息',
				type : 'scatter',
				symbol: 'triangle',
				data : []
			}  ]
		};

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
 	var isFresh = true;
	var optionFlag = false;
//    var trigger = $('.hamburger'),
//	    overlay = $('.overlay'),
//	    isClosed = false;
    var isCount = true;
    var timeIdJason = {};
	var realTimeCurrentPage = 1;
    
	myChart.setOption(option, true);
	
	var flag; 
	var timerflag;
	
	setInterval(function() {setTimeClear()},28800000);
	function inittran() {
	    myChart.showLoading();
	    myrefresh();
	    flag= setInterval(function() { myrefresh() } , 7700);
	}
	
	function myrefresh(){ 
		if( $(".deviceIdInput").val()!=""){
			$.ajax({
		         type : "post",
		         async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		         url : "getRealTimeInfo",    //请求发送到TestServlet处
		         data : {detectionId: $(".deviceIdInput").val(),lastTime:$(".maxTimeInput").val()},
		         dataType : "json",        //返回数据形式为json
		         success : function(result) {
		             //请求成功时执行该函数内容，result即为服务器返回的json对象
		             if (result) {
		            	var len=result.length-1;
	                    for(var i=0;i<result.length;i++){       
	                    	timeX.push(result[i].time);    //挨个取出类别并填入类别数组
	                        AT.push(result[i].AT);
	                        BT.push(result[i].BT);
	                        CT.push(result[i].CT);
	                        DT.push(result[i].DT);
	                        ET.push(result[i].ET);
	                        AF.push( result[i].AF);
	                        
	                        id.push(result[i].id);
	                        DIST.push(result[i].DIST);
	                        CONT.push(result[i].CONT);
	                        DISRH.push(result[i].DISRH);
	                        NOISE.push(result[i].NOISE);
	                        INNOIS.push(result[i].INNOIS);
	                        OUTNOI.push(result[i].OUTNOI);
	                        FLOW.push(result[i].FLOW);
	                        AngleX.push(result[i].AngleX);
	                        tableHead.push(result[i].tableInfo);
	                        $(".maxTimeInput").val(result[i].time);
	                        
	                        
	                    	$(".signnalImg").attr("src","css/6.png");
	                        setTimeout(function() {
	                        	$(".signnalImg").attr("src","css/7.png");
							},1000);
	                     }
	                    
	                    $(".AT_A").html(result[len].AT);
	                    $(".BT_A").html(result[len].BT);
	                    $(".CT_A").html(result[len].CT);
	                    $(".DT_A").html(result[len].DT);
	                    $(".ET_A").html(result[len].ET);
	                    $(".AF_A").html(result[len].AF);
	                    $(".time_a").html(result[len].time);
//	                    var strTmp = "当前时间：" + result[len].time + "<br>BT:" +result[len].BT 
//                  		+ "℃&nbsp;&nbsp;<br>AT" +result[len].AT+"℃&nbsp;&nbsp;<br>DT:"+result[len].DT
//                  		+"℃&nbsp;&nbsp;<br>CT:"+result[len].CT +"℃&nbsp;&nbsp;<br>AF:" + result[len].AF 
//                  		+"%RH&nbsp;&nbsp;<br>ET:"+result[len].ET+"℃";
//	                  	$(".idDetailInfo").html(strTmp);
//	                	$(".settingInfo").html("设置温度：32℃&nbsp;&nbsp;设置湿度：50%RH肤温:33℃");
	                  	
	                    myChart.hideLoading();   
	                    myChart.setOption({
	        		        xAxis: [{
	        		            data: timeX
	        		        },{
	        		            data: timeX
	        		        }],
	        		        series: [{
	        					name : '温度A',
	        					data : AT
	        				}, {
	        					name : '温度B',
	        					data : BT
	        				}, {
	        					name : '温度C',
	        					data : CT
	        				}, {
	        					name : '温度D',
	        					data : DT
	        				}, {
	        					name : '温度E',
	        					data : ET
	        				}, {
	        					name : '湿度A',
	        					data : AF
	        				} , {
	        					name : '显示温度',
	        					data : DIST
	        				}, {
	        					name : '显示风速',
	        					data : FLOW
	        				}, {
	        					name : '控制温度',
	        					data : CONT
	        				}, {
	        					name : '箱外报警噪声',
	        					data : OUTNOI
	        				}, {
	        					name : '舱内报警噪声',
	        					data : INNOIS
	        				}, {
	        					name : '舱内噪声',
	        					data : NOISE
	        				}, {
	        					name : '显示湿度',
	        					data : DISRH
	        				}, {
	        					name : '倾斜角',
	        					data : AngleX
	        				}, {
	        					name : '被检仪器编号',
	        					data : id
	        				},{
	        					name : '表头信息',
	        					data : tableHead
	        				}  ]
	        		    });
		             }
		         },
		    });
			myChart.hideLoading();   
			
		}
	
	} 
	detectinFresh();
	idflag = setInterval(function() {detectinFresh()},5000);
	function detectinFresh(){ 
		$.ajax({
	         type : "post",
	         async : false,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
	         url : "detectionInfoFresh",    //请求发送到TestServlet处
	         dataType : "json",        //返回数据形式为json
	         data: {page : realTimeCurrentPage},
	         success : function(result) {
	             //请求成功时执行该函数内容，result即为服务器返回的json对象
	             $(".nums").html("");
	             var strNav = "";
	             var onlineSum = 0;
	             if (result.length>0) {
	                 for(var i=0;i<result.length;i++){ 
	                	  if(result[i].status=="1"){
	                		  onlineSum++;
	                    	  strNav += "<a class='online'>"
	                    			  +result[i].devices+"</a>";
	                	  }
	                  }
	                 $(".nums").append(strNav);
	                 strNav = "";
	                 for(var i=0;i<result.length;i++){ 
	                	 if(result[i].status=="2"){
	                		  strNav += "<a class='hasData'>"
	                   			  +result[i].devices+"</a>";
	                	 }
	                   	 
	                 }
	                 $(".nums").append(strNav);
	                 strNav = "";
	                 for(var i=0;i<result.length;i++){ 
	                	 if(result[i].status=="0"){
	                		  strNav += "<a class='disonline'>"
	                   			  +result[i].devices+"</a>";
	                	 }
	                   	 
	                 }
	                 $(".nums").append(strNav);
	                 $(".onlineSum").html("在线："+onlineSum+"台");
	            }
	         },
	    });
		
	  }

	setTimeout(function() {
		setTimeClear();
	},3600000);
	setInterval(function() {setTimeClear()},3600000);
	function setTimeClear(){
		 
		$(".maxTimeInput").val("");
		  AF = [];
	 	  AT = [];
	 	  BT = [];
	 	  CT = [];
	 	  DT = [];
	 	  ET = [];
	 	  timeX = [];
	 	  
	 	  DIST = [];
		  CONT = [];
		  DISRH = [];
		  NOISE = [];
		  INNOIS = [];
		  OUTNOI = [];
		  FLOW = [];
		  AngleX = [];
		  id = [];
		  tableHead = [];
	 	  
		  clearInterval(flag);
	 	  inittran();
	 	  clearInterval(idflag);
	 	  idflag = setInterval(function() {detectinFresh()},7700);
	 	  isFresh = true;
		  $(".Fresh").html("暂停刷新");
		  
		  //here to write code
		  clearInterval(timerflag);
		  isCount = true;	
		  $(".startCount").html("平衡计时");
		
		  for(var jason in timeIdJason){
			  if(jason == $(".deviceIdInput").val().trim()){
				  $(".tempTime").val(timeIdJason[jason]);
				  isCount = false;
				  timerflag = setInterval(function() {timerss()},1000);
			  }
			  
		  }
	}
	
	
	function timerss(){ 
		var stringTime = "2017-01-01 00:00:00";  
		var timestamp2 = Date.parse(new Date(stringTime.replace("-", "/").replace("-", "/"))); 
		var timestamp=new Date().getTime();
		var str =  timestamp - $(".tempTime").val() + timestamp2;
		$(".startCount").html("暂停&nbsp;&nbsp;" + getMyDate(str));
	
	}
	
   function getMyDate(str){    
        var oDate = new Date(str),   
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),    
        oSen = oDate.getSeconds(),    
        oTime =  getzf(oHour) +':'+getzf(oMin) +':'+getzf(oSen);//最后拼接时间
        
        return oTime;    
    };    
    function getLocalTime(format) {   
    	var oDate = new Date(format);
    	var year=oDate.getYear(); 
    	var month=oDate.getMonth()+1; 
    	var date=oDate.getDate(); 
    	var hour=oDate.getHours(); 
    	var minute=oDate.getMinutes(); 
    	var second=oDate.getSeconds(); 
    	return "20"+year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
    }     
    function getzf(num){    
        if(parseInt(num) < 10){    
            num = '0'+num;    
        }    
        return num;    
    } 
    myChart.on('click', function(params) {
		//var str = params.name + params.seriesName + params.data;
		if(params.seriesName == "表头信息"){
			var recordTime = params.name;
//			$("#figureBeignTime").val(recordTime);
			var detectionId = $(".deviceIdInput").val();
			
			$.ajax({
	             type: "post",
	             url: "getTableBaseInfo",
	             data: {
	            	 detectionId: $(".deviceIdInput").val(),
	            	 recordTime : recordTime
	             },
	             dataType: "json",
	             success: function(data){
	            	 
	            	 optionFlag = true;
	            	 $(".h6Id").html($(".deviceIdShow").html());
	        		 optionFlag = true;
	        		 $('.titleDataId').val(data.id);
	        		 var str = data.info.split("#@");
	        		 $(".cpyInfoInput").val(str[0]);
	        		 $(".appliancesInfoInput").val(str[1]);
	        		 $(".manufacturerInfoInput").val(str[2]);
	        		 $(".modelInfoInput").val(str[3]);
	        		 $(".productionInfoInput").val(str[4]);
	        		 $(".tempInfoInput").val(str[7]);
	        		 $(".humidityInfoInput").val(str[8]);
	        		 $(".regionInfo").val(str[6]);
	        		 $(".calibrationDate").val(str[5]);
	        		 
	        		 $('#tempCenterBtn').html("原始记录基本信息录入");
        			 $('.baseInfoInput').hide();
        			 $('.tableHeadInfoInput').fadeIn("slow");
	        		 $('#optionSetModal').modal({backdrop: 'static', keyboard: false});
	             }
			});
		}
	});
    
//    overlay.show();
//    trigger.removeClass('is-closed');
//    trigger.addClass('is-open');
    isClosed = true;
//    $('#wrapper').toggleClass('toggled');
    
//    trigger.click(function () {
//      hamburger_cross();      
//    });

//    function hamburger_cross() {
//
//      if (isClosed == true) {          
//        overlay.hide();
//        trigger.removeClass('is-open');
//        trigger.addClass('is-closed');
//        isClosed = false;
//        $(".pageDiv").css("display","none");
//      } else {   
//        overlay.show();
//        trigger.removeClass('is-closed');
//        trigger.addClass('is-open');
//        isClosed = true;
//        $(".pageDiv").css("display","block");
//        
//      }
//  }
//  $('[data-toggle="offcanvas"]').click(function () {
//	    $('.box2_3').hide();
//	    $('.box2_2').hide();
//	    optionFlag = false;
//        $('#wrapper').toggleClass('toggled');
//  });  
 
  
  
  $(".nums").delegate(".online", "click", function(){
	  if(optionFlag){
		  if($(".titleDataId").val().trim()!=""){
			  if(confirm("如果切换仪器编号，则此次操作信息将不能修改，只能添加，是否继续？")){
		 		  $(".h6Id").html($(this).parent().val());
				  $(".titleDataId").val("");
			  }
		  }else{
			  $(".h6Id").html($(this).html());
		  }
	  }else{
		  if($(this).html() != $(".deviceIdInput").val()){
			 
			  $(".deviceIdInput").val($(this).html());
			  $(".deviceIdShow").html($(this).html());
			  $(".maxTimeInput").val("");
			  AF = [];
		 	  AT = [];
		 	  BT = [];
		 	  CT = [];
		 	  DT = [];
		 	  ET = [];
		 	  timeX = [];
		 	  
		 	  DIST = [];
			  CONT = [];
			  DISRH = [];
			  NOISE = [];
			  INNOIS = [];
			  OUTNOI = [];
			  FLOW = [];
			  AngleX = [];
			  id = [];
			  tableHead = [];
			  myChart.setOption({
			  title : {
					text : '婴儿培养箱各参数关系图(编号：'+$(".deviceIdInput").val()+')' ,
					x : 'center',
					textStyle:{
						fontSize:'36'
		            }
				},
  		        xAxis: [{
  		            data: timeX
  		        },{
  		            data: timeX
  		        }],
  		        dataZoom : [ {
					start : 0,
					end : 100,
					xAxisIndex : [ 0, 1 ]
				}, {
					start : 0,
					end : 100,
					xAxisIndex : [ 0, 1 ]
				} ],
  		        series: [{
  					name : '温度A',
  					data : AT
  				}, {
  					name : '温度B',
  					data : BT
  				}, {
  					name : '温度C',
  					data : CT
  				}, {
  					name : '温度D',
  					data : DT
  				}, {
  					name : '温度E',
  					data : ET
  				}, {
  					name : '湿度A',
  					data : AF
  				} , {
  					name : '显示温度',
  					data : DIST
  				}, {
  					name : '显示风速',
  					data : FLOW
  				}, {
  					name : '控制温度',
  					data : CONT
  				}, {
  					name : '箱外报警噪声',
  					data : OUTNOI
  				}, {
  					name : '舱内报警噪声',
  					data : INNOIS
  				}, {
  					name : '舱内噪声',
  					data : NOISE
  				}, {
  					name : '显示湿度',
  					data : DISRH
  				}, {
  					name : '倾斜角',
  					data : AngleX
  				}, {
  					name : '被检仪器编号',
  					data : id
  				},{
  					name : '表头信息',
  					data : tableHead
  				}  ]
  		      });
			 
			  clearInterval(flag);
		 	  inittran();
		 	  clearInterval(idflag);
		 	  idflag = setInterval(function() {detectinFresh()},7700);
		 	  isFresh = true;
			  $(".Fresh").html("暂停刷新");
			  
			  //here to write code
			  clearInterval(timerflag);
			  isCount = true;	
			  $(".startCount").html("平衡计时");
			 
			  for(var jason in timeIdJason){
				  if(jason == $(".deviceIdInput").val().trim()){
					  $(".tempTime").val(timeIdJason[jason]);
					  isCount = false;
					  timerss()
					  timerflag = setInterval(function() {timerss()},1000);
				  }
			  }
		  }
	  }
  });
  $(".nums").delegate(".hasData", "click", function(){
	  if(optionFlag){
		  if($(".titleDataId").val().trim()!=""){
			  if(confirm("如果切换仪器编号，则此次操作信息将不能修改，只能添加，是否继续？")){
		 		  $(".h6Id").html($(this).parent().val());
				  $(".titleDataId").val("");
			  }
		  }else{
			  $(".h6Id").html($(this).html());
		  }
	  }else{
		  if($(this).html() != $(".deviceIdInput").val()){
			 
			  $(".deviceIdInput").val($(this).html());
			  $(".deviceIdShow").html($(this).html());
			  $(".maxTimeInput").val("");
			  AF = [];
		 	  AT = [];
		 	  BT = [];
		 	  CT = [];
		 	  DT = [];
		 	  ET = [];
		 	  timeX = [];
		 	  
		 	  DIST = [];
			  CONT = [];
			  DISRH = [];
			  NOISE = [];
			  INNOIS = [];
			  OUTNOI = [];
			  FLOW = [];
			  AngleX = [];
			  id = [];
			  tableHead = [];
			  myChart.setOption({
			  title : {
					text : '婴儿培养箱各参数关系图(编号：'+$(".deviceIdInput").val()+')' ,
					x : 'center',
					textStyle:{
						fontSize:'36'
		            }
				},
  		        xAxis: [{
  		            data: timeX
  		        },{
  		            data: timeX
  		        }],
  		        dataZoom : [ {
					start : 0,
					end : 100,
					xAxisIndex : [ 0, 1 ]
				}, {
					start : 0,
					end : 100,
					xAxisIndex : [ 0, 1 ]
				} ],
				series: [{
  					name : '温度A',
  					data : AT
  				}, {
  					name : '温度B',
  					data : BT
  				}, {
  					name : '温度C',
  					data : CT
  				}, {
  					name : '温度D',
  					data : DT
  				}, {
  					name : '温度E',
  					data : ET
  				}, {
  					name : '湿度A',
  					data : AF
  				} , {
  					name : '显示温度',
  					data : DIST
  				}, {
  					name : '显示风速',
  					data : FLOW
  				}, {
  					name : '控制温度',
  					data : CONT
  				}, {
  					name : '箱外报警噪声',
  					data : OUTNOI
  				}, {
  					name : '舱内报警噪声',
  					data : INNOIS
  				}, {
  					name : '舱内噪声',
  					data : NOISE
  				}, {
  					name : '显示湿度',
  					data : DISRH
  				}, {
  					name : '倾斜角',
  					data : AngleX
  				}, {
  					name : '被检仪器编号',
  					data : id
  				},{
  					name : '表头信息',
  					data : tableHead
  				}  ]
  		      });
			 
			  clearInterval(flag);
		 	  inittran();
		 	  clearInterval(idflag);
		 	  idflag = setInterval(function() {detectinFresh()},7700);
		 	  isFresh = true;
			  $(".Fresh").html("暂停刷新");
			  
			  //here to write code
			  clearInterval(timerflag);
			  isCount = true;	
			  $(".startCount").html("平衡计时");
			 
			  for(var jason in timeIdJason){
				  if(jason == $(".deviceIdInput").val().trim()){
					  $(".tempTime").val(timeIdJason[jason]);
					  isCount = false;
					  timerss()
					  timerflag = setInterval(function() {timerss()},1000);
				  }
			  }
		  }
	  }
  });
//  $(".previousPage").click(function(){
//	  	
//	  
//	  
//	  
//		if(realTimeCurrentPage>1){
//			realTimeCurrentPage = realTimeCurrentPage-1;
//			clearInterval(idflag);
//			idflag = setInterval(function() {detectinFresh()},7700);
//			detectinFresh();
//		 	$(".nextPage").fadeIn();
//		}
//		if(realTimeCurrentPage == "1" ){
//			$(".previousPage").hide();
//		}
//	    if(realTimeCurrentPage ==  $(".pageNum").val()){
//			$(".nextPage").hide();
//	    }
//  });
  
//  $(".nextPage").click(function(){
//	  if(realTimeCurrentPage< $(".pageNum").val()){
//			realTimeCurrentPage = realTimeCurrentPage + 1;
//			clearInterval(idflag);
//			detectinFresh();
//			idflag = setInterval(function() {detectinFresh()},7700);
//		 	$(".previousPage").fadeIn();
//	  }
//	  if(realTimeCurrentPage ==  $(".pageNum").val()){
//			$(".nextPage").hide();
//	  }
//	  if(realTimeCurrentPage == "1" ){
//			$(".previousPage").hide();
//	  }
//  });
 
  $(".Fresh").click(function(){
		if(isFresh){
			clearInterval(flag);
			isFresh = false;
			$(".Fresh").html("开始刷新");
		}else{
			inittran();
			isFresh = true;
			$(".Fresh").html("暂停刷新");
		} 
		
  });
  
  	$(".detectionIdOption").click(function(){
		$("#startId").val("")
		$("#endId").val("");
		getDetectionIdSelectList();
		$("#detetion_modal").modal();
	});
  	$(".searchRealId").click(function() {
		getDetectionIdSelectList();
	});
	$(".closeDetectionSelect").click(function(){
//		$('.box2_2').fadeOut("slow");
		$("#detetion_modal").modal('hide');
	});
	
	$(".optionSet").click(function(){
		 $(".h6Id").html($(".deviceIdShow").html());
//		 overlay.show();
//	     trigger.removeClass('is-closed');
//	     trigger.addClass('is-open');
//	     isClosed = true;
	     $(".pageDiv").css("display","block");
//	     $('#wrapper').toggleClass('toggled');
		 $('#optionSetModal').modal({backdrop: 'static', keyboard: false});
		 optionFlag = true;
		 $(".inspectedId").val("");
		 $(".DIST").val("");
		 $(".CONT").val("");
		 $(".inspectedIdDetail").val("");
		 $(".FLOW").val("");
		 $(".DISRH").val("");
		 $(".OUTNOI").val("");
		 $(".INNOIS").val("");
		 $(".NOISE").val("");
		 $(".regionInfo").val("");
		  
		 $(".cpyInfoInput").val("");
		 $(".appliancesInfoInput").val("");
		 $(".manufacturerInfoInput").val("");
		 $(".modelInfoInput").val("");
		 $(".productionInfoInput").val("");
		 $(".tempInfoInput").val("");
		 $(".humidityInfoInput").val("");
		 $(".regionInfo").val("");
		 $(".calibrationDate").val("");
		 $(".titleDataId").val("");
	});
	
	$(".mainUI").click(function() {
		window.location.href="index.jsp";
	});
	
	$(".closeOptionSet").click(function(){
		
//		overlay.hide();
//        trigger.removeClass('is-open');
//        trigger.addClass('is-closed');
//        isClosed = false;
//        $('#wrapper').toggleClass('toggled');
//		$('.box2_3').hide();
		 $(".pageDiv").css("display","none");
		 optionFlag = false;
		 $('#optionSetModal').modal('hide');
	});
	
	$(".enterRealTime").click(function(){
		
		var options = "";
		var ids = "";
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
          	
          	window.location.reload();
          	
          }
	    });
		
	}); 
	
	
	$(".startCount").click(function(){
		if(isCount){
			clearInterval(timerflag);
			var timestamp=new Date().getTime();
			$(".tempTime").val(timestamp);
			
			var str = $(".deviceIdInput").val();
			timeIdJason[str] = timestamp;
			
			isCount = false;
			timerss()
			timerflag = setInterval(function() {timerss()},1000);
		}else{
			clearInterval(timerflag);
			isCount = true;
			var tempStr = $(".startCount").html();
			$(".startCount").html("平衡计时" + tempStr.substring(2,tempStr.length));
			
			var ss = $(".deviceIdInput").val();
			delete timeIdJason[ss];
		}
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
	
	$(".submitOption").click(function(){
		
		if($('#tempCenterBtn').html()=="原始记录基本信息录入"){
			var tableHeadStr = "";
			
			if(  $(".cpyInfoInput").val().trim()==""&&
			  $(".appliancesInfoInput").val().trim()==""&&
			  $(".manufacturerInfoInput").val().trim()==""&&
			  $(".modelInfoInput").val().trim()==""&&
			  $(".productionInfoInput").val().trim()==""&&
			  $(".tempInfoInput").val().trim()==""&&
			  $(".humidityInfoInput").val().trim()==""&&
			  $(".regionInfo").val().trim()==""&&
			  $(".calibrationDate").val().trim()==""){
				tableHeadStr = "";
			}else{
				tableHeadStr += ($(".cpyInfoInput").val().trim()==""?" ":$(".cpyInfoInput").val()) + "#@";
				tableHeadStr += ($(".appliancesInfoInput").val().trim()==""?" ":$(".appliancesInfoInput").val()) + "#@";
				tableHeadStr += ($(".manufacturerInfoInput").val().trim()==""?" ":$(".manufacturerInfoInput").val()) + "#@";
				tableHeadStr += ($(".modelInfoInput").val().trim()==""?" ":$(".modelInfoInput").val()) + "#@";
				tableHeadStr += ($(".productionInfoInput").val().trim()==""?" ":$(".productionInfoInput").val()) + "#@";
				tableHeadStr += ($(".calibrationDate").val().trim()==""?" ":$(".calibrationDate").val()) + "#@";
				tableHeadStr += ($(".regionInfo").val().trim()==""?" ":$(".regionInfo").val()) + "#@";
				tableHeadStr += ($(".tempInfoInput").val().trim()==""?" ":$(".tempInfoInput").val()) + "#@";
				tableHeadStr += ($(".humidityInfoInput").val().trim()==""?" ":$(".humidityInfoInput").val()) + "#@";
			}
			$.ajax({
		          type: "post",
		          url: "addTitleInfo",
		          data : {
		        	  detectionId:$(".h6Id").html(),
		        	  inspectedIdDetail:tableHeadStr,
		        	  inputTime:$(".maxTimeInput").val(),
		        	  regionInfo:$(".regionInfo").val().trim(),
		        	  titleDataId:$(".titleDataId").val()
		          },
		          async:false, 
		          dataType: "json",
		          success: function(){
					  
		        	  $(".closeOptionSet").click();
		        	  
		        	  $('.box2_3').hide();
		        	  optionFlag = false;
		    		
		    		  $(".regionInfo").val("");
		    		  $(".maxTimeInput").val("");
		    		  $(".cpyInfoInput").val("");
		    		  $(".appliancesInfoInput").val("");
		    		  $(".manufacturerInfoInput").val("");
		    		  $(".modelInfoInput").val("");
		    		  $(".productionInfoInput").val("");
		    		  $(".tempInfoInput").val("");
		    		  $(".humidityInfoInput").val("");
		    		  $(".regionInfo").val("");
		    		  $(".calibrationDate").val("");
		    		  $(".titleDataId").val("");
		        	  
		        	  AT = [];
		        	  BT = [];
		        	  CT = [];
		        	  DT = [];
		        	  ET = [];
		        	  timeX = [];
		        	  id = [];
		        	  DIST = [];
		        	  CONT = [];
		        	  DISRH = [];
		        	  NOISE = [];
		        	  INNOIS = [];
		        	  OUTNOI = [];
		        	  FLOW = [];
		        	  AngleX = [];
		    		  AF = [];
		    		  tableHead = [];
		    		  
		    		  $(".deviceIdInput").val($(".h6Id").html());
		    		  $(".deviceIdShow").html($(".h6Id").html());
		    		  clearInterval(flag);
				 	  inittran();
				 	 
				 	  clearInterval(idflag);
				 	  idflag = setInterval(function() {detectinFresh()},7700);
				 	  detectinFresh();
				 	  isFresh = true;
					  $(".Fresh").html("暂停刷新");
					  
					  //here to write code
					  clearInterval(timerflag);
					  isCount = true;	
					  $(".startCount").html("平衡计时");
					
					  for(var jason in timeIdJason){
						  if(jason == $(".deviceIdInput").val().trim()){
							  $(".tempTime").val(timeIdJason[jason]);
							  isCount = false;
								timerflag = setInterval(function() {timerss()},1000);
						  }
						  
					  }
		    		  
		          },
		          error:function(){
		        	  alert("error");
		          }
			    });
		}else{
			$.ajax({
		          type: "post",
		          url: "addDetailInfo",
		          data : {
		        	  detectionId:$(".h6Id").html(),
		        	  inspectedId:$(".inspectedId").val(),
		        	  DIST:$(".DIST").val(),
		        	  CONT:$(".CONT").val(),
		        	  FLOW:$(".FLOW").val(),
		        	  DISRH:$(".DISRH").val(),
		        	  OUTNOI:$(".OUTNOI").val(),
		        	  INNOIS:$(".INNOIS").val(),
		        	  NOISE:$(".NOISE").val(),
		        	  inputTime:$(".maxTimeInput").val(),
		        	  titleDataId:$(".titleDataId").val()
		          },
		          async:false, 
		          dataType: "json",
		          success: function(){
					  
		        	  $(".closeOptionSet").click();
		        	  
		        	  $('.box2_3').hide();
		        	  optionFlag = false;
		    		
		    		  $(".inspectedId").val("");
		    		  $(".DIST").val("");
		    		  $(".CONT").val("");
		    		  $(".inspectedIdDetail").val("");
		    		  $(".FLOW").val("");
		    		  $(".DISRH").val("");
		    		  $(".OUTNOI").val("");
		    		  $(".INNOIS").val("");
		    		  $(".NOISE").val("");
		    		  $(".regionInfo").val("");
		    		  $(".maxTimeInput").val("");
		    		  
		    		  $(".titleDataId").val("");
		        	  
		        	  AT = [];
		        	  BT = [];
		        	  CT = [];
		        	  DT = [];
		        	  ET = [];
		        	  timeX = [];
		        	  id = [];
		        	  DIST = [];
		        	  CONT = [];
		        	  DISRH = [];
		        	  NOISE = [];
		        	  INNOIS = [];
		        	  OUTNOI = [];
		        	  FLOW = [];
		        	  AngleX = [];
		    		  AF = [];
		    		  tableHead = [];
		    		  
		    		  $(".deviceIdInput").val($(".h6Id").html());
		    		  $(".deviceIdShow").html($(".h6Id").html());
		    		  clearInterval(flag);
				 	  inittran();
				 	 
				 	  clearInterval(idflag);
				 	  detectinFresh();
				 	  idflag = setInterval(function() {detectinFresh()},7700);
				 	  isFresh = true;
					  $(".Fresh").html("暂停刷新");
					  
					  //here to write code
					  clearInterval(timerflag);
					  isCount = true;	
					  $(".startCount").html("平衡计时");
					
					  for(var jason in timeIdJason){
						  if(jason == $(".deviceIdInput").val().trim()){
							  $(".tempTime").val(timeIdJason[jason]);
							  isCount = false;
							  timerflag = setInterval(function() {timerss()},1000);
						  }
					  }
		          },
		          error:function(){
		        	  alert("error");
		          }
			    });
		}
	}); 
	
	$(".inspectedId").change(function(){
		if(isNaN($('.inspectedId').val())){
			$('.inspectedId').val("");
			alert("被检仪器编号输入不能包含字母！");
		};
		
		if($('.inspectedId').val()>9999||$('.inspectedId').val()<0){
			$('.inspectedId').val("");
			alert("被检仪器编号输入范围为0-9999，您已超出范围！");
		}
		
	});
	$(".DIST").change(function(){
		if(isNaN($('.DIST').val())){
			$('.DIST').val("");
			alert("显示温度输入不能包含字母！");
		};
		
		if($('.DIST').val()>80||$('.DIST').val()<5){
			$('.DIST').val("");
			alert("显示温度输入范围为5.00℃-80.00℃，您已超出范围！");
		}
		
	});
	$(".CONT").change(function(){
		if(isNaN($('.CONT').val())){
			$('.CONT').val("");
			alert("控制温度输入不能包含字母！");
		};
		
		if($('.CONT').val()>50||$('.CONT').val()<5){
			$('.CONT').val("");
			alert("控制温度输入范围为5.00℃-50.00℃，您已超出范围！");
		}
		
	});
	$(".FLOW").change(function(){
		if(isNaN($('.FLOW').val())){
			$('.FLOW').val("");
			alert("显示风速输入不能包含字母！");
		};
		if($('.FLOW').val()>5||$('.FLOW').val()<0){
			$('.FLOW').val("");
			alert("显示风速输入范围为0m/sec-5m/sec，您已超出范围！");
		}
	});
	$(".DISRH").change(function(){
		if(isNaN($('.DISRH').val())){
			$('.DISRH').val("");
			alert("显示湿度输入不能包含字母！");
		};
		if($('.DISRH').val()>100||$('.DISRH').val()<0){
			$('.DISRH').val("");
			alert("显示湿度输入范围为0%RH-100.00%RH，您已超出范围！");
		};
	});
	$(".OUTNOI").change(function(){
		if(isNaN($('.OUTNOI').val())){
			$('.OUTNOI').val("");
			alert("箱外报警噪声输入不能包含字母！");
		};
		if($('.OUTNOI').val()>200||$('.OUTNOI').val()<0){
			$('.OUTNOI').val("");
			alert("箱外报警噪声输入范围为0dB-200dB，您已超出范围！");
		};
	});
	$(".INNOIS").change(function(){
		if(isNaN($('.INNOIS').val())){
			$('.INNOIS').val("");
			alert("舱内报警噪声输入不能包含字母！");
		};
		if($('.INNOIS').val()>200||$('.INNOIS').val()<0){
			$('.INNOIS').val("");
			alert("舱内报警噪声输入范围为0dB-200dB，您已超出范围！");
		};
	});
	$(".NOISE").change(function(){
		if(isNaN($('.NOISE').val())){
			$('.NOISE').val("");
			alert("舱内噪声输入不能包含字母！");
		};
		if($('.NOISE').val()>200||$('.NOISE').val()<0){
			$('.NOISE').val("");
			alert("舱内噪声输入范围为0dB-200dB，您已超出范围！");
		};
	});
	$(".baseInput").click(function(){
		$('#tempCenterBtn').html("被检设备参数录入");
		$('.tableHeadInfoInput').hide();
		$('.baseInfoInput').fadeIn("slow");
	});
	$(".tableHeadInfo").click(function(){
		$('#tempCenterBtn').html("原始记录基本信息录入");
		$('.baseInfoInput').hide();
		$('.tableHeadInfoInput').fadeIn("slow");
	});
});

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
        	 }
        }
    });
}

