var UsersManager = {
		users: [],

		add: function(user) {
			//console.log(user);
			this.users["u"+user.userid] = user;
		},
		
		addList: function(list) {
			for ( var i = 0; i < list.length; i++) {
				this.add(list[i]);
			}
		},
		
		getName: function(uid) {
			return this.users["u"+uid].username;
		}

};