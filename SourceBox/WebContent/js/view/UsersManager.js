var UsersManager = {
		users: [],

		add: function(user) {
			//console.log(user);
			this.users["u"+user.userid] = user;
			
			$( '#dialog-users-list' ).append('<li id="user_join_'+user.userid+'">'+user.username+'</li>');
		},
		
		remove: function(user) {
			delete this.users["u"+user.userid];
			$('#user_join_'+user.userid).remove();
		},
		
		addList: function(list) {
			for ( var i = 0; i < list.length; i++) {
				this.add(list[i]);
			}
		},
		
		getName: function(uid) {
			return (this.users["u"+uid]==undefined)?"[-]":this.users["u"+uid].username;
		},
		
		openWin: function() {
			$( '#dialog-users-list' ).dialog('open');
		}

};



$(function() {
	
	$( '#dialog-users-list' ).dialog({ autoOpen: false/*, modal: true */});

	
});
