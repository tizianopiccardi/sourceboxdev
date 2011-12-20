var UsersManager = {
		users: [],

		add: function(user) {
			console.log(user);
			this.users["u"+user.userid] = user;
		},
		
		getName: function(uid) {
			return this.users["u"+uid].username;
		}

};