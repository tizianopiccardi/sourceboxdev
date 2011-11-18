var EditorManager = {

		editor: null,
		setEditor: function(ed) {this.editor=ed},
		
		saved: true,
		
		save: function() {
			
			this.saved = true;
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
				
		}



};
