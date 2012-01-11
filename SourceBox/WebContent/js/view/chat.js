
$(function(){

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

	    	
	    	$('#comments-list').append('<div><b>'+User.name+'</b>: '+$('#comment').val()+"</div>");
	    	$('#comments-list').scroll();
	    	
	    	$('#comments-list').scrollTo('100%');
	    	$(this).val('');
	    }
	});
	
	
	
});

function toggleChat() {

	$('#chat-wrapper').fadeToggle();
}
