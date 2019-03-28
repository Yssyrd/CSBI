	$(document).ready(function(){ 
	    
		$('#recordSearchForm').bootstrapValidator({
			message: 'This value is not valid',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				stations: {
					validators: {
						notEmpty: {
	                        message: 'aa'
	                  }
	              }
	          },
	           
	           	elements: {
					validators: {
						notEmpty: {
	                        message: 'bb'
	                  }
	              }
	          },
	      
				startTime: {
					validators: { 
						regexp: {
							regexp: /^(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])$/,
							message: 'cc'
	 					}, 
	              }
	          },
	          
				endTime: {
					validators: { 
						regexp: {
							regexp: /^(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])$/,
							message: 'dd'
	 					}, 
	              }
	          },
			}
		}); 
	     
	});