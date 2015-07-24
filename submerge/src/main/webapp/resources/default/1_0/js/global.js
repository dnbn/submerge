window.sbm = (function() {

	return {
		setLocale : function(locale) {
			$('#form-language\\:locale').val(locale);
		}
	};

}());

$(function() {
	// =========== INIT ALL COLOR PICKERS ============

	$('.color-picker').colorpicker({
		format : 'hex'
	});

	// ====== KEEP CONNECT MENU OPEN ON CLICK ========

	$('#form-login ul.dropdown-menu').on('click', function(event) {
		var events = $._data(document, 'events') || {};
		events = events.click || [];
		for (var i = 0; i < events.length; i++) {
			if (events[i].selector) {

				// Check if the clicked element matches the event selector
				if ($(event.target).is(events[i].selector)) {
					events[i].handler.call(event.target, event);
				}

				// Check if any of the clicked element parents matches the
				// delegated event selector (Emulating propagation)
				$(event.target).parents(events[i].selector).each(function() {
					events[i].handler.call(this, event);
				});
			}
		}
		event.stopPropagation(); // Always stop propagation
	});

});
