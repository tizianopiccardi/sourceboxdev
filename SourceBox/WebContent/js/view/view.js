
$(function() {

	$("#error").hide();
	$('#nick-set-img').hide();
	$('#nickname-panel').hide();
	$("#password-form").hide();
	$("#pw-check-img").hide();
	$("#wrong-password").hide();
	
	$("#save-status").hide();
	
	$("#save-loading").hide();

	$("#password-form").dialog({
		autoOpen : false,
		closeOnEscape : false,
		open : function(event) {
			$(".ui-dialog-titlebar-close").hide();
			$(".ui-resizable-handle").hide();
		},
		modal : true,
		buttons : {
			"OK" : function() {
				load($("input#pass").val());
			}
		}
	});

	$('input#pass').keypress(function(e) {
		if (e.keyCode == 13)
			load($("input#pass").val());
	});

	$("#nickname-panel").dialog({
		autoOpen : false,
		/** hide close button! * */
		closeOnEscape : false,
		open : function(event, ui) {
			$(".ui-dialog-titlebar-close").hide();
		},
		/** ********************* */
		modal : true,
		width : 150,
		buttons : {
			"OK" : function() {
				setNickName();
			}
		}
	});
	$('input#nick').keypress(function(e) {
		if (e.keyCode == 13)
			setNickName();
	});

	var editor = CodeMirror(document.body,
			{
				mode : "text/x-java",
				lineNumbers : true,
				matchBrackets : true,
				indentWithTabs : true,
				undoDepth: 0,
				enterMode : "keep",
				onCursorActivity : function(i) {
					editor.setLineClass(hlLine, null);
					hlLine = editor.setLineClass(editor.getCursor().line,
							"activeline");

					SyncManager.cursorPos = editor.getCursor(false);
					// editor.addWidget(editor.getCursor(),
					// document.getElementById("user") ,false);
				},

				onKeyEvent : function(ed, ke) {
					
					if (ke.type == 'keydown') {
						var k = ke.keyCode || ke.which;
						//console.log(k);
						if (ke.ctrlKey && k == 83) {
							ke.preventDefault();
							EditorManager.save();
							return;
						}
						if (ke.ctrlKey && k == 78) {
							ke.preventDefault();
							EditorManager.openNew();
						}
					}

				},
				onChange : function(ed) {
					$('#save-status').show();
				}

			});

	var hlLine = editor.setLineClass(0, "activeline");

	/***************************************************************************
	 * FULL SCREEN MODE
	 */
	var editorDiv = $('.CodeMirror-scroll');
	editorDiv.addClass('fullscreen');
	editorDiv.height('100%');
	editorDiv.width('100%');
	editor.refresh();

	$.ajax({
		url : "boxenv",
		dataType : "json",
		data : {
			alias : document.alias
		},
		success : function(response) {
			if (response.success) {

				document.boxinfo = response;

				if (response.loggedAs == undefined
						|| response.loggedAs.length < 1)
					$("#nickname-panel").dialog("open");
				else {
					User.name = response.loggedAs;
					join();
					init();
				}

			} else
				$("#error").append(
						"<p>Sorry, your box is not currently available...</p>")
						.dialog({
							modal : true
						});
		},
		error : function() {
			$("#error").append("<p>Service error... Please try later</p>")
					.dialog({
						modal : true
					});
		}
	});

	EditorManager.setEditor(editor);
	/*
	 * EditorManager.setEditor(editor); EditorManager.run();
	 */

	/*************
	 * Every n seconds I check the buffer for new changes
	 */
	$(document).everyTime(300, 'flushBuffer', SyncManager.flush);
	// EventsManager.setEditor(editor);
	// EventsManager.run();
	/*
	 * $.ajax({ url: "join.php", dataType: "text", success: function(response){
	 * User.name = response; } });
	 * 
	 */

	// editor.getNewPos('',2,3);
	// editor.replaceRange("ciao",{line:0,ch:0}, null,true);

	/*
	 * CodeMirror.save(); alert(editor.getInputField().value); editor.refresh();
	 */

	function join() {

		$('#comment-top').html(User.name);
		$('#chat-wrapper').fadeIn(1000);
		$('#chat-dot').attr('src', 'images/green_dot.gif');
	}

	function load(password) {
		if (password != null)
			$("#pw-check-img").show();

		$.ajax({
			url : "get",
			dataType : "json",
			data : ({
				alias : document.alias,
				pass : $("input#pass").val()
			}),
			type : "POST",
			success : function(response) {
				if (response.success) {
					$("#password-form").dialog("close");
					editor.replaceRange(response.code, {
						line : 0,
						ch : 0
					}, null, true);
					editor.setOption('mode', response.language);
					EditorManager.isReadOnly = (response.readonly>0);
					editor.setOption('readOnly', (EditorManager.isReadOnly));
					
					User.name = response.loggedAs;
				}

				else {
					if (password != null) {
						$("#pw-check-img").hide();
						$("input#pass").val("");
						$("#wrong-password").show();
						$("#password-form").effect("shake", {
							times : 3
						}, 50);
					}
				}
			}
		});
	}

	function init() {

		if (document.boxinfo.isPrivate)

			$("#password-form").dialog("open");

		else
			load();

	}

	function setNickName() {

		if ($("input#nick").val() == '')
			return;

		$("#nick-set-img").show();

		$.ajax({
			url : "join",
			dataType : "json",
			data : ({
				nick : $("input#nick").val()
			}),
			type : "POST",
			success : function(response) {
				if (response.success) {
					User.name = $("input#nick").val();
					//document.boxinfo.loggedAs = $("input#nick").val();
					$("#nickname-panel").dialog("close");

					join();

					init();

				}

			}
		});
	}

});