/*document.join = false;
document.nick = '';
document.chat_sequence = -1;
*/
$(function(){
	//$('#nick-set-img').hide();
	$('#chat-wrapper').hide();
	
	
	$('#comment').keypress(function(e) {
	    if (e.keyCode == 13 && ! e.shiftKey) {
	    	e.preventDefault();
	    	if ($('#comment').val()=='') return;
	    	
	    	
	    	
	    	
	    	$.ajax({
	  		  url: "chat",
	  		  dataType: "json",
	  		  data: ({ alias: document.alias, msg: $('#comment').val() }),
	  		  type: "POST",
	  		  success: function(response){
	  				if (response.success) {
	  					
	  					
	  					
	  				}
	  		  }
	  		});
	    	
	    	
	    	
	    	
	    	$('#comments-list').append('<br/>'+document.boxinfo.loggedAs+': '+$('#comment').val());
	    	$('#comments-list').scroll();
	    	
	    	$('#comments-list').scrollTo('100%');
	    	$(this).val('');
	    }
	});
	
	
	
});

function toggleChat() {
	
	/*
	$( "#chat-not_complete" ).dialog({
		modal: true,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	});
	return;*/
	
	
	//if (!document.join) {openNickNamePanel();return;}
	/*$('#comments-list').toggle();
	$('#comment').toggle();*/
	$('#chat-wrapper').fadeToggle();
}
/*


function openNickNamePanel() {
	if (document.join) return;
	$( "#nickname-panel" ).dialog("open");
}
*//*
function setNickName() {

	if ($( "input#nick" ).val()=='') return;
	
	$( "#nick-set-img" ).show();
	
	$.ajax({
		  url: "join.php",
		  dataType: "json",
		  data: ({ id: document.id, nick: $( "input#nick" ).val() }),
		  type: "POST",
		  success: function(response){
				if (response.success) {
					
					document.nick = $( "input#nick" ).val();
					$( "#nickname-panel" ).dialog("close");
					/*$('#comments-list').show("slide",{ direction: "down" }, 1000);
					$('#comment').show();*/
					//$('#chat-wrapper').show("slide",{ direction: "down" }, 1000);
			/*		
					$('#comment-top').html($( "input#nick" ).val());
					
					$('#chat-wrapper').fadeIn(1000);
					document.join = true;
					$('#chat-dot').attr('src', 'images/green_dot.gif');
					//$('#chat-button').html($( "input#nick" ).val());
				}
		  }
		});
}*/
