
//var lastVersion ='';// 'class HelloWorldApp {\n    public static void main(String[] args) {\n    \tSystem.out.println("Hello World!"); // Display the string.\n    }\n}';


$(function(){

	
$("#error").hide();
$('#nick-set-img').hide();
$('#nickname-panel').hide();
$("#password-form").hide();


$( "#nickname-panel" ).dialog({
	autoOpen: false,
	/** hide close button! **/
	closeOnEscape: false,
	open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
	/************************/
	modal: true,
	width: 150,
	buttons: {
		"OK": function() {
			setNickName();
		}
	}
});
$('input#nick').keypress(function(e) {
    if (e.keyCode == 13) setNickName();
});

	
//var x=0;
var editor = CodeMirror(document.body, {
		mode: "text/x-java",
		lineNumbers: true,
		matchBrackets: true,
		indentWithTabs: true,
		//undoDepth: 0,
		enterMode: "keep",
		onCursorActivity: function(i) {
			editor.setLineClass(hlLine, null);
			hlLine = editor.setLineClass(editor.getCursor().line, "activeline");
		
			SyncManager.cursorPos = editor.getCursor(false);
			//editor.addWidget(editor.getCursor(), document.getElementById("user") ,false);
		},


		
		onKeyEvent : function(ed, ke) {

			if (ke.type == 'keydown') {
				var k = ke.keyCode || ke.which;
				if (ke.ctrlKey && k==83) {
					ke.preventDefault();
					console.log("SAVE");
				}
			}

		}
		
	//	onChange : function(ed) {
			//if (x>10) {
				/*var dmp = new diff_match_patch();
				var out = dmp.diff_main(lastVersion, ed.getValue());
				alert(JSON.stringify( out ));
				lastVersion = ed.getValue();*/
				//x=0;
			//}
			//else
			//x++;
	//	}







});


var hlLine = editor.setLineClass(0, "activeline");

/***********
 * FULL SCREEN MODE
 */
var editorDiv = $('.CodeMirror-scroll');
editorDiv.addClass('fullscreen');
editorDiv.height('100%');
editorDiv.width('100%');
editor.refresh();

//editor.replaceRange(lastVersion,{line:0,ch:0}, null,true);

$.ajax({
	  url: "boxenv",
	  dataType: "json",
	  data: {alias: document.alias},
	  success: function(response){
			if (response.success) {
				
				
				if (response.loggedAs.length<1) 
					$( "#nickname-panel" ).dialog("open");
				
				
				
			}
			else
				$("#error").append("<p>Sorry, your box is not currently available...</p>").dialog({modal: true});
	  },
	  error: function() {
		  $("#error").append("<p>Service error... Please try later</p>").dialog({modal: true});
	  }
});


/*
EditorManager.setEditor(editor);
EditorManager.run();*/


//$(document).everyTime(300, 'flushBuffer', SyncManager.flush);
//EventsManager.setEditor(editor);
//EventsManager.run();

/*
$.ajax({
	  url: "join.php",
	  dataType: "text",
	  success: function(response){
			User.name = response;
	  }
	});

*/

//editor.getNewPos('',2,3);
//editor.replaceRange("ciao",{line:0,ch:0}, null,true);



/*CodeMirror.save();
alert(editor.getInputField().value);
editor.refresh();*/
});


function setNickName() {

	if ($( "input#nick" ).val()=='') return;
	
	$( "#nick-set-img" ).show();
	
	$.ajax({
		  url: "join",
		  dataType: "json",
		  data: ({ nick: $( "input#nick" ).val() }),
		  type: "POST",
		  success: function(response){
				if (response.success) {
					
					document.nick = $( "input#nick" ).val();
					$( "#nickname-panel" ).dialog("close");

					$('#comment-top').html($( "input#nick" ).val());
					
					$('#chat-wrapper').fadeIn(1000);
					//document.join = true;
					$('#chat-dot').attr('src', 'images/green_dot.gif');
					//$('#chat-button').html($( "input#nick" ).val());
				}
		  }
		});
}

