var UsersManager = {
		users: [],

		add: function(user) {
			//console.log(user);
			this.users["u"+user.userid] = user;
		},
		
		remove: function(user) {
			delete this.users["u"+user.userid];
		},
		
		addList: function(list) {
			for ( var i = 0; i < list.length; i++) {
				this.add(list[i]);
			}
		},
		
		getName: function(uid) {
			return (this.users["u"+uid]==undefined)?"[-]":this.users["u"+uid].username;
		}

};