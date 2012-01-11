//NON IMPLEMENTATO
var OTEngine = {
		
		compare: function(op1, op2) {
			if (op1.t.l == op2.t.l && op1.f.c == op2.f.c)
				return 0;
			if (op1.t.l < op2.f.l || (op1.t.l == op2.f.l && op1.t.c < op2.f.c))
				return -1;
			if (op1.f.l > op2.t.l || (op1.f.l == op2.t.l && op1.f.c > op2.t.c)) 
				return 1;
			return 2;
		},

		//da .. dato
		T: function(external, local) {
				
				switch (OTEngine.compare(local, external)) {

					case 1: break;
					case 0: if (User.compare(external.u)==-1) break;
					case -1:
							//prima ho rimpiazzato n lines
							replacedRowsCount = local.t.l - local.f.l;
							
							//con questo numero di lines messe al suo posto
							lineslocalEdit = local.s.split("\n");
							linesNumberIndex = lineslocalEdit.length-1;
							
							//alert(linesNumberIndex);
							
							//Quindi la differenza è: (anche negativa ovviamente)
							lineOffset = linesNumberIndex - replacedRowsCount;
							//alert(lineOffset);
							
							
							//Shifto from e to di x righe
							external.f.l+=lineOffset; 
							external.t.l+=lineOffset;
							
							
							//SE IMPATTANO SULLA STESSA RIGA anche la colonna ne risente
							if (linesNumberIndex + local.f.l == external.f.l) {
								//alert('si');

								toOffSet = (lineslocalEdit.length>1)?local.t.c:0;
		
								lastLineSize = lineslocalEdit[linesNumberIndex].length;
								
								//alert(lastLineSize);
								external.f.c+= (lastLineSize - toOffSet);
								//se il to è sulla stessa riga shifta in là
								if (external.f.l == external.t.l)
									external.t.c+=(lastLineSize - toOffSet);
								
							}
							break;
					case 2: //BOH!!!
			}

			return external;
		}
			
			
			
			
			
			

}