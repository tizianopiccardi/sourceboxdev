var Insert = {
		
		sequence: 0,
		
		makeInsert: function(fL, fC, tL, tC, s, u) {
				return {
					sq: 0/*this.sequence++*/,
					f: {l: fL, c: fC},
					t: {l: tL, c: tC},
					s: s,
					u: u
				};
		}
		
		
};