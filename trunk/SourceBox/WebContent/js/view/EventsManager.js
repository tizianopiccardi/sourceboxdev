var EventsManager = {
		
		editor: null,
		setEditor : function(ed) {
			this.editor = ed;
		},
		
		run: function() {
			$.ajax({
				  url: "events",
				  dataType: "json",
				  data: ({ alias: document.alias}),
				  type: "POST",
				  success: function(response){
						if (response.events) 
							EventsManager.eventSelector(response.events);
				  },
				  complete: function() {
					  $(document).stopTime('getEvent');
					  $(document).oneTime(200, 'getEvent', EventsManager.run);
				  }
				});
		},
		
		eventSelector: function(eventsObj) {
			
			//console.log(eventsObj);
			
			EventsManager.onUserList(eventsObj['users']);
			EventsManager.onMessage(eventsObj['msg']);
			EventsManager.onEditCase(eventsObj['op']);
			EventsManager.onCursors(eventsObj['cursors']);

			
			
			/*for(var key in eventsList) {
				switch (key) {
				case 'users': EventsManager.onUserList(eventsList[key]);
							break;	
				case 'op': EventsManager.onEditCase(eventsList[key]);
							 break;
				case 'msg': EventsManager.onMessage(eventsList[key]);
							break;	
				case 'cursors': EventsManager.onCursors(eventsList[key]);
							break;
				default:
					break;
				}

			}*/
		},
		
		onCursors: function(cursors){
			//$('[id^="users_"]').remove();
			//console.log(cursors);
			for(var uid in cursors) {
				//console.log(uid);
				if (uid==User.uid) continue;
				
				$('#users_'+uid).remove();
				
				username = UsersManager.getName(uid);
				//console.log(username);
				cursor = cursors[uid];
				var newUserDiv = "<div id=\"users_"+uid+"\" style=\"color: red;position:absolute;font-size:10px !important;\">&oline;"+username+"</div>";
				$("#users-markers").append(newUserDiv);
				
				this.editor.addWidget(cursor, document.getElementById("users_"+uid), false);
			}
			
			
			/*for ( var i = 0; i < cursors.length; i++) {
				var cursor = cursors[i];
				
				if (cursor.) continue;

				/*var pos = {line: user.line, ch: user.ch};
				//if ($("#users_"+user.username).length < 1) {
					var newUserDiv = "<div id=\"users_"+user.username+"\" style=\"color: red;position:absolute;font-size:10px !important;\">&oline;"+user.username+"</div>";
					//console.log("ADD: " + newUserDiv);
					$("#users-markers").append(newUserDiv);
				//}
				
				this.editor.addWidget(pos, document.getElementById("users_"+user.username), false);
			}*/
		},
		
		onMessage: function(messages) {
			//console.log(messages);
			for ( var i = 0; i < messages.length; i++) {
				if (messages[i].uid != User.uid)
					$('#comments-list').append('<div><b>'+UsersManager.getName(messages[i].uid)+'</b>: '+messages[i].text+"</div>");
			}

			$('#comments-list').scroll();
	    	$('#comments-list').scrollTo('100%');
			
		},
		
		onUserList: function(users) {
			
			
			for ( var i = 0; i < users.length; i++) {
				var user = users[i];
				if (user.userid==User.uid) continue;

				
				UsersManager.add(user);
				showNotification({
	                message: "'"+user.username + "' logged in!",
	                autoClose: true,
	                duration: 2
	            });
				
			}
			
			
			
			//document.getElementById("users-markers").
			//$("#users-markers").html('');
			/*$('[id^="users_"]').remove();
			
			for ( var i = 0; i < users.length; i++) {
				var user = users[i];
				
				if (user.username==User.name) continue;

				var pos = {line: user.line, ch: user.ch};
				//if ($("#users_"+user.username).length < 1) {
					var newUserDiv = "<div id=\"users_"+user.username+"\" style=\"color: red;position:absolute;font-size:10px !important;\">&oline;"+user.username+"</div>";
					//console.log("ADD: " + newUserDiv);
					$("#users-markers").append(newUserDiv);
				//}
				
				this.editor.addWidget(pos, document.getElementById("users_"+user.username), false);
			}*/
		},
		
		onEditCase: function (values) {
			
			SyncManager.lockBuffer = true;
			//ATTENZIONE DEVO TRASFORMARE QUELLO CHE RESTA NEL BUFFER
			var newVal = [];
			for ( var int = 0; int < values.length; int++) {
				if (values[int].u == User.name) {
					//rimuovi da his
					//console.log(values[int]);
					SyncManager.ackReceived(values[int]);
					//values.splice(int,1);
				}
				else
					newVal.push(values[int]);
			}
			
			values = newVal;
			
			for ( var int = 0; int < values.length; int++) {
				
				var current = values[int];

				
				//se ho il buffer pieno con ad esempio dei \n
				//i dati che arrivano finiscono sulla riga sbagliata quindi OT
				current = EventsManager.applyHistoryOT(current);
				
				from = {line:current.f.l, ch:current.f.c};
				to = {line:current.t.l, ch:current.t.c};

				//QUI DEVO FARE LA TRANSFORMATION
				//RICORDA DI METTERE UN LOCK AL BUFFER
				EventsManager.editor.replaceRange(current.s, from, to, true);

				
				
			}
			
			//TRASFORMO QUI QUELLO CHE RESTA IN BUFFER
			var tmp = [];
			for ( var j = 0; j < SyncManager.buffer.length; j++) {
				var buffer_el =  SyncManager.buffer[j];
				for ( var int = 0; int < values.length; int++) {
					buffer_el = OTEngine.T(buffer_el, values[int]);
				}
				tmp.push(buffer_el);
			}
			SyncManager.buffer = tmp;
			
			SyncManager.lockBuffer = false;
		},
		
		applyHistoryOT: function(external /*modifica arrivata*/) {
			for (var i = 0 ; i < SyncManager.history.length ; i++) {
				external = OTEngine.T(external, SyncManager.history[i]);
			}
			return external;
			
		}
		
		
		
};