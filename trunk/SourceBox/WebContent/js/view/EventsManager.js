var EventsManager = {
		
		editor: null,
		setEditor : function(ed) {
			this.editor = ed;
		},
		
		run: function() {
			$.ajax({
				  url: "events.php",
				  dataType: "json",
				  data: ({ id: ''/*, string: EventsManager.editor.getValue*/}),
				  type: "POST",
				  success: function(response){
						if (response) 
							EventsManager.eventSelector(response);
				  },
				  complete: function() {
					  $(document).stopTime('getEvent');
					  $(document).oneTime(200, 'getEvent', EventsManager.run);
				  }
				});
		},
		
		eventSelector: function(eventsList) {
			for(var key in eventsList) {
				
				switch (key) {
				case 'edit': EventsManager.onEditCase(eventsList[key]);
							 break;
				default:
					break;
				}

			}
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