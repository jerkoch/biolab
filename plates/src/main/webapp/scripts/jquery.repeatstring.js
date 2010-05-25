(function($) {
	$.fn.duplicate = function(count, cloneEvents) {
		var tmp = [];
		for ( var i = 0; i < count; i++) {
			$.merge(tmp, this.clone(cloneEvents).get());
		}
		return this.pushStack(tmp);
	};
})(jQuery);