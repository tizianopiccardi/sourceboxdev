

$(function() {
	
	$( "#revisions-prev" ).button();
	$( "#revisions-next" ).button();
	$( "#revisions-restore" ).button();
	$( "#revisions-close" ).button();
	$( '#revisions-dialog' ).dialog({ autoOpen: false, modal: true, minWidth: 500 });


	$( "#revisions-close" ).click(function() {$('#revisions-dialog').dialog('close');});
	
	
});


var RevisionsBrowser = {
		
		current: null,
		
		open: function() {

			$('#revisions-dialog').dialog('open');
			$('#revisions').val('');
			
			$.ajax({
				url : "revision",
				dataType : "json",
				data : {
					alias : document.alias
				},
				type: "POST",
				success : function(response) {

						$('#revisions').val(response.revision.text);
						$('#revisions-number').html("Revision: "+ response.revision.id);
						RevisionsBrowser.current = response.revision.id;
					}
				});
			
		}
		
		
};