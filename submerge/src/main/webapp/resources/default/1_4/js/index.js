$(function() {

	// ============== EVENTS FOR UPDATING PREVIEWS ================

	$('.sub-panel').on('changeColor', function(event) {

		var color = event.color.toHex();
		var $currentTarget = $(event.currentTarget);
		var $preview = $currentTarget.find('.fontPreview span.textPreview');

		if ($(event.target).hasClass('color')) {
			$preview.css('color', color);
		} else {
			var stroke = $currentTarget.find('.outlineWidth option:selected').val();
			addTextShadow(stroke, color, $preview);
		}

	});

	$('.outlineWidth').change(function() {

		var $panel = $(this).closest('.panel-body');

		var color = $panel.find('.outlineColor input').val();
		var selector = $panel.find('.fontPreview span.textPreview');

		addTextShadow(this.value, color, selector);

	}).change();

	$('.fontName').change(function() {

		var selector = $(this).closest('.panel-body').find('.fontPreview span.textPreview');
		selector.css('font-family', this.value + ', sans-serif');

	}).change();

	// ============== COLOR PICKERS INIT ON PAGE LOAD ==============

	$('.color-picker').colorpicker({
		format : 'hex'
	})

	// ================= PREVIEW INIT ON PAGE LOAD =================

	$('.color-picker').find('.fontName').change();

	$('.color-picker').each(function() {
		$(this).colorpicker('setValue', this.firstChild.value);
	});

});

/**
 * Original source: http://codepen.io/pixelass/pen/gbGZYL
 */
function textShadow(stroke, color) {

	if (stroke == 0) {
		return 'none';
	}
	var blur = stroke > 1 ? 1 : 0;
	var shadow = new Array();
	var from = stroke * -1;
	for (var i = from; i <= stroke; i++) {
		for (var j = from; j <= stroke; j++) {
			shadow.push(i + 'px ' + j + 'px ' + blur + 'px ' + color);
		}
	}
	return shadow.toString();

}

function addTextShadow(stroke, color, $selector) {

	$selector.css('text-shadow', textShadow(stroke, color));

}
