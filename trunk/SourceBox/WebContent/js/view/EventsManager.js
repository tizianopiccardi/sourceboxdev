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
			EventsManager.onAction(eventsObj['actions']);

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

				if (user.add) {
					UsersManager.add(user);
					showNotification({
		                message: "'"+user.username + "' logged in!",
		                autoClose: true,
		                duration: 2
		            });
				}
				else {
					UsersManager.remove(user);
					$('#users_'+user.userid).remove();
					showNotification({
		                message: "'"+user.username + "' logged out!",
		                type: 'warning',
		                autoClose: true,
		                duration: 2
		            });
				}
				
			}
			

		},
		
		onEditCase: function (values) {
			
			for ( var int = 0; int < values.length; int++) {
				
				var current = values[int];
				
				if (current.uid == User.uid || current.sq <= Insert.sequence) continue;
				
				from = {line:current.fromLine, ch:current.fromChar};
				to = {line:current.toLine, ch:current.toChar};

				/*console.log(from);
				console.log(to);*/
				Insert.sequence = current.sq;
				
				EventsManager.editor.replaceRange(current.text, from, to, true);
			}
			
		},
		
		onAction: function (actions) {
			for ( var i = 0; i < actions.length; i++) {
				current = actions[i];
				
				switch (current.name) {
				case 'save':
					if (current.params!=User.uid) 
						showNotification({
			                message: "Saved",
			                autoClose: true,
			                duration: 1
			            });
					break;

				default:
					break;
				}
				
			}
		}
		/*onEditCase: function (values) {
			
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
		
		applyHistoryOT: function(external ) {
			for (var i = 0 ; i < SyncManager.history.length ; i++) {
				external = OTEngine.T(external, SyncManager.history[i]);
			}
			return external;
			
		}*/
		
		
		
};