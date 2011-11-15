jQuery(function($) {
	var menuRoot = $("#menu-root");
	var menu = $("#menu");

	// Hook up menu root click event.
	menuRoot.attr("href", "javascript:void( 0 )").click(function() {
		// Toggle the menu display.
		menu.toggle();

		// Blur the link to remove focus.
		menuRoot.blur();

		document.lock_lines = true;

		$("#menu-root").addClass("menu-root-hover");
		
		// Cancel event (and its bubbling).
		return (false);
	});
	
	menu.click(function() {
		closeMenu();
	});

	

	$(document).click(function(event) {
		if (menu.is(":visible") && !$(event.target).closest("#menu").size()) {
			closeMenu();
		}
	});
	
	function closeMenu() {

		menu.hide();
		$("#menu-root").removeClass("menu-root-hover");
		document.lock_lines = false;
	}

});