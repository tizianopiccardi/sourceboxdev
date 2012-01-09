

$(function() {
	
	$( "#revisions-prev" ).button();
	$( "#revisions-next" ).button();
	$( "#revisions-restore" ).button();
	$( "#revisions-close" ).button();
	$( '#revisions-dialog' ).dialog({ autoOpen: false, modal: true, minWidth: 500 });


	$( "#revisions-close" ).click(function() {$('#revisions-dialog').dialog('close');});
	
	$( "#revisions-prev" ).click(function() {RevisionsBrowser.getRevision(--RevisionsBrowser.current);});
	$( "#revisions-next" ).click(function() {RevisionsBrowser.getRevision(++RevisionsBrowser.current);});
	
	$( "#revisions-restore" ).click(function() {
		revText = $('#revisions').val();
		linesCount = RevisionsBrowser.editor.lineCount();
		RevisionsBrowser.editor.replaceRange(revText, {line:0,ch:0}, 
													{line: linesCount,
													ch:RevisionsBrowser.editor.getLine(linesCount-1).lenght
													});
		//EditorManager.save();
		
	});
	
});


var RevisionsBrowser = {
		
		current: 0,
		
		editor: null,
		setEditor: function(ed) {this.editor=ed;},
		
		open: function() {

			$('#revisions-dialog').dialog('open');
			$('#revisions').val('');
			
			this.getRevision();
			
		},
		
		getRevision: function(id) {
			$.ajax({
				url : "revision",
				dataType : "json",
				data : {
					alias : document.alias,
					revision: id
				},
				type: "POST",
				success : function(response) {
						if (response.limit){$('#revisions').val(''); return;}
						$('#revisions').val(response.revision.text);
						$('#revisions-number').html("Revision: "+ response.revision.id);
						RevisionsBrowser.current = response.revision.id;
					}
				});
		}
		
		
};