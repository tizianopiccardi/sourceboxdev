var EditorManager = {

		editor: null,
		setEditor: function(ed) {this.editor=ed;},
		
		saved: true,
		
		isReadOnly: false,
		
		save: function() {
			
			if (EditorManager.isReadOnly) return;
			
			$("#save-loading").show();
			
			$.ajax({
				url : "save",
				dataType : "json",
				data : {alias : document.alias},
				success : function(response) {
					if (response.success) {
						this.saved = true;
					}
					$("#save-loading").hide();
					$('#save-status').html("");
				}
			});
			
			
		},
		
		openNew: function() {
			
			if (!this.saved) {
				var askSave = confirm("Save?");
				if (askSave){
					save();
				}
			}
			else
				window.location = "./";
				
		},
		
		
		share: function() {
			$("#share-dialog").dialog({
				closeOnEscape : false,
				modal : true
			});
			$("#boxurl").val(location.href);
		}



};
