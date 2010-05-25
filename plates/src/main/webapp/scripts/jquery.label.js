(function($) {

	// I am the controller for Plate
	function Label(selectSelector, editButtonSelector, newButtonSelector, deleteButtonSelector) {
		this.select = $(selectSelector);
		this.editButton = $(editButtonSelector);
		this.newButton = $(newButtonSelector);
		this.deleteButton = $(deleteButtonSelector);

		this.initLabelSelectors();
	}

	Label.prototype.initLabelSelectors = function() {

		var self = this;

		// populate labels
		this.populateLabels();

		// add edit label click handler
		this.editButton.click(function(event) {
			var name = prompt('Edit label name:', '');
			if (name) {
				$.post('labels', {
					id : self.select.val(),
					name : name
				}, function(data) {
					self.populateLabels(data.id);
				});
			}

			return false;
		});

		// add new label click handler
		this.newButton.click(function(event) {
			var name = prompt('Label name:', '');
			if (name) {
				$.post('labels', {
					name : name
				}, function(data) {
					self.populateLabels(data.id);
				});
			}

			return false;
		});

		// add delete label click handler
		this.deleteButton.click(function(event) {
			var url = 'labels/delete/' + self.select.val();
			$.post(url, function(data) {
				self.populateLabels();
			});
			return false;
		});
	};

	Label.prototype.populateLabels = function(selectedValue) {

		// retrieve labels
		var self = this;
		$.getJSON('labels', function(j) {
			var options = '';
			for ( var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].id + '">' + j[i].name + '</option>';
			}
			self.select.html(options);

			if (typeof selectedValue == "undefined") {
				$(self.select.selector + ' option:eq(0)').attr('selected', 'selected');
			} else {
				$(self.select.selector + " option[value='" + selectedValue + "']").attr('selected', 'selected');
			}
		});
	};

	window.Label = Label;

})(jQuery);