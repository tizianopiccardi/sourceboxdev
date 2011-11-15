var User = {
		
		id: '0',
		
		name: 'user'+Math.floor(Math.random()*1000),
		
		compare: function(uid) {
			myself = parseInt(User.id,16);
			somebody = parseInt(uid,16);
			if (myself<somebody) return -1
			if (myself>somebody) return 1
			return 0;
		}
		
}