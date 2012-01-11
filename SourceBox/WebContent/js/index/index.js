
$(function(){
	
	$( "#send" ).button();
	
	$( "#password" ).defaultText('Password...');
	
	$( "#password" ).hide();
	
	$( "#private" ).click(function() {if($( "#password" ).is(":visible")) $( "#password" ).hide(); else $( "#password" ).show();} );

	var editor = CodeMirror.fromTextArea(document.getElementById('codetext'), {
		mode: "text/plain",
		lineNumbers: true,
		matchBrackets: true,
		indentWithTabs: true,
		enterMode: "keep"
	});

	editor.focus();
	
	
	
	$('#language-selector').change(function() {
			editor.setOption('mode', $(this).val());
	});
	
	
	$( "#upload-dialog" ).dialog({
		height: 150,
		modal: true,
		closeOnEscape: false,
		resizable: false,
		height: 110,
		title: 'Uploading...',

		autoOpen: false
	});
	$( "#uploadbar" ).progressbar({
		value: 100
	});
	

	
	
	
	$('#send').click(function() {
	
		$( "#upload-dialog" ).dialog( "open" );
		
		 $.ajax({
			  url: "store",
			  dataType: "json",
			  data: ({ 
				  		captcha: $('#captcha_code').val(),
				  		code: editor.getValue(),
				  		language: $('#language-selector').val(),
				  		password: $('#password').val(),
				  		is_private: $('#private').is(':checked'),
				  		readonly: $('#readonly').is(':checked')
				  	}),
			  type: "POST",
			  success: function(response){

			  	if(response.success) {
			  		window.scrollTo(0,0);
			  		$('#maincontent').hide('slow');
			  		
			  		$("#BoxURL").append('<h1><a href="'+response.url+'">'+response.url+'</a></h1>'+
			  				'Destroy link:<br>'+
			  				'<a href="'+window.location+"destroy?alias="+response.alias+"&key="+response.key
			  				+'">'+window.location+"destroy?alias="+response.alias+"&key="+response.key+'</a>'
			  		);
			  		

			  	}
			  	else {
			  		$("#error").append('<div class="error">'+response.message+"<div>");
			  	}
			  	
			  	$( "#upload-dialog" ).dialog( "close" );
			  },
			  error: function(response, textStatus, errorThrown) {
				  console.log(response);
				  console.log(textStatus);
				  console.log(errorThrown);
				  $("#error").append('<div class="error">'+response.message+"<div>");
				  $( "#upload-dialog" ).dialog( "close" );
			  }
			});
	 });
	
	

});