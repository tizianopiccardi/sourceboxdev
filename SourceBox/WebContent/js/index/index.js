
$(function(){
	
	$( "#send" ).button();
	
	$( "#password" ).defaultText('Password...')
	
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
		//beforeClose: function() {return false;},
		autoOpen: false
	});
	$( "#uploadbar" ).progressbar({
		value: 100
	});
	
	/*
	var editorDiv = $('.CodeMirror-scroll');
	editorDiv.addClass('fullscreen');
	//editorDiv.height('100%');
	editorDiv.width('30px');
	editor.refresh();
	*/
/*	$( "#show-url" ).hide();
	$( "#codeout" ).hide();
	
	$('#preview-text').hide();
	$( "#send" ).button();
	$( "#lastposts" ).accordion();
	$( "#upload-dialog" ).dialog({
		height: 140,
		modal: true,
		closeOnEscape: false,
		resizable: false,
		height: 110,
		title: 'Uploading...',
		//beforeClose: function() {return false;},
		autoOpen: false
	});
	$( "#uploadbar" ).progressbar({
		value: 100
	});


/*

	$(document).everyTime(100000, function(i) {
		$.ajax({
			  url: "lastposts.php",
			  success: function(response){
					$( "#lastposts" ).accordion( "destroy" );
					$( "#lastposts" ).html(response);
					$( "#lastposts" ).accordion();
			  }
			});
		
			
	});*/
	/*
	$('input#send').click(function() {
	
		$( "#upload-dialog" ).dialog( "open" );
		
		 $.ajax({
			  url: "store.php",
			  dataType: "json",
			  data: ({ 
				  		captcha_code: $('#captcha_code').val(),
				  		code: $('#codetext').val(),
				  		language: $('#language-selector').val(),
				  		password: $('#password').val()
				  	}),
			  type: "POST",
			  success: function(response){
			 	$( "#codeout" ).show();
			  	if(response.success) {
			  		window.scrollTo(0,0);
			  		
			  		$('#infoset').hide();
			  		$("#codeout").html('');
					$("#codeout").append(response.message.preview);
					$('#codetext').hide('slow');
					
					$('#preview-text').show();
					$( "#show-url" ).show().append('<a href="'+response.message.url+'">'+response.message.url+'</a>');
			  	}
			  	else {
			  		$("#codeout").append('<div class="error">'+response.message+"<div>");
			  	}
			  	
			  	$( "#upload-dialog" ).dialog( "close" );
			  }
			});
	 });*/
	
	
	
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
			 	//$( "#codeout" ).show();
			  	if(response.success) {
			  		window.scrollTo(0,0);
			  		$('#maincontent').hide('slow');
			  		
			  		$("#BoxURL").append('<h1><a href="'+response.url+'">'+response.url+'</a></h1>'+
			  				'Destroy link:<br>'+
			  				'<a href="'+window.location+"destroy?alias="+response.alias+"&key="+response.key
			  				+'">'+window.location+"destroy?alias="+response.alias+"&key="+response.key+'</a>'
			  		);
			  		
			  		/*
			  		$('#infoset').hide();
			  		$("#codeout").html('');
					$("#codeout").append(response.message.preview);
					$('#codetext').hide('slow');
					
					$('#preview-text').show();
					$( "#show-url" ).show().append('<a href="'+response.message.url+'">'+response.message.url+'</a>');
			  	*/
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