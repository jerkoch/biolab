(function($) {

	// create global start request handler
	$(document).ajaxStart(function() {
		$("#loading").show();
	});

	// create global stop request handler
	$(document).ajaxStop(function() {
		$("#loading").hide();
	});

	// create the global error handler
	$.ajaxSetup( {
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
			alert(errorThrown);
			alert(XMLHttpRequest.responseText);
		}
	});

})(jQuery);
