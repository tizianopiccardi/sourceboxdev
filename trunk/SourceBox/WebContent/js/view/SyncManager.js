
var SyncManager = {
		
		buffer: [],
		
		//TUTTO CIò MANDATO NON CONFERMATO
		history: [],
		
		cursorPos: {},
		
		bufferAdd: function(from, to, newText) {
			if (!SyncManager.isNullAction(from, to, newText)) {
				var insert = Insert.makeInsert(from.line, from.ch, to.line, to.ch, newText.join("\n"), User.name); 
				this.history.push(insert);
				this.buffer.push(insert);
				
			}
		},
		
		isNullAction: function(from, to, newText) {
			return (newText=='' & from.line==to.line & from.ch==to.ch);
		},
		
		isRunning: false,
		
		//Buffer locked by SyncManager during the transformation
		lockBuffer: false,
		
		revisionNumber: 0,
		
		
		errorFlag: false,
		counterErrorFlag: 0,		
		lastWasError: function() {
			if (SyncManager.errorFlag && SyncManager.counterErrorFlag<10) 
			{SyncManager.counterErrorFlag++; return true;}
			SyncManager.errorFlag=false;SyncManager.counterErrorFlag=0;
			return false;
		},
		
		
		ackReceived: function(op) {
			var newHis = [];
			for ( var int = 0; int < this.history.length; int++) {
				if (this.history[int].seq != op.seq) {
					//this.history.slice(int, 1);
					newHis.push(this.history[int]);
				}
			}
			this.history = newHis;
		},
		
		
		flush: function() {
			
			if (SyncManager.lastWasError()) return;
				
			if ((SyncManager.buffer.length > 0 || SyncManager.cursorPos.line!=undefined) 
					&& !SyncManager.isRunning 
					&& !SyncManager.lockBuffer) {
				
				var currentBuffer = SyncManager.buffer.slice();
				SyncManager.buffer = [];
				
				SyncManager.isRunning = true;
				$.ajax({
					  url: "edit",
					  dataType: "json",
					  traditional: true,
					  data: {	alias: document.alias,
						  		c: JSON.stringify(SyncManager.cursorPos), 
								e: JSON.stringify(currentBuffer)},
					  type: "POST",
					  success: function(response){
						  
					  },
					  error: function(jqXHR, textStatus) {
						  SyncManager.buffer = currentBuffer.concat(SyncManager.buffer);
						  SyncManager.errorFlag = true;
					  },
					  complete: function() {
						  SyncManager.isRunning = false;
						  SyncManager.cursorPos = {};
						  delete currentBuffer;
					  }
					});
			}

		}
		
		
		
};

