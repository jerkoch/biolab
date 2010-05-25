(function($) {

	// I am the controller for Plate
	function Plate(tableSelector, columnCount, rowCount, subdivide) {

		var self = this;

		this.plates = {};
		this.curPlate = null;

		this.select = $("#plateName");
		this.saveButton = $("#savePlateButton");
		this.newButton = $("#newPlateButton");
		this.deleteButton = $("#deletePlateButton");

		// Setup Labels
		this.label = new Label("#labelName", "#editLabelButton", "#newLabelButton", "#deleteLabelButton");

		// Bind the click handler for the table. This way, we
		// don't have to attach event handlers to each cell.
		this.table = $(tableSelector);
		this.table.click(function(event) {
			// Pass off to the table click handler.
				self.onClick(event);

				// Cancel default event.
				return (false);
			});

		this.initPlate();
	}

	// I build the actual markup of the table using the
	// given number of columns and rows.
	Plate.prototype.buildTable = function() {
		// create the table
		var d = this.curPlate.subDivide;
		for ( var i = 0; i < this.curPlate.height; i++) {
			var tr = $("<tr></tr>");
			for ( var j = 0; j < this.curPlate.width; j++) {
				var td = $("<td></td>"), c = (i + j) % 2 ? "active" : "alt active";
				td.data("dp", {
					x : "" + (j + 1),
					y : String.fromCharCode(65 + i),
					z : ""
				});
				td.text("[" + td.data("dp").x + td.data("dp").y + "]");
				tr.append(td.addClass(c).duplicate(d, true));
			}
			this.table.append(tr.duplicate(d, true));
		}

		// sub label the divs and add hover
		for ( var i = 0; i < this.curPlate.height; i++) {
			for ( var j = 0; j < this.curPlate.width; j++) {
				var id = (j + 1) + String.fromCharCode(65 + i);
				$("td:contains(\"[" + id + "]\")").each(function(index) {
					$(this).data("cellId", id);
					$(this).data("dp").z = "" + (index + 1);
					$(this).html("&nbsp;");
					$(this).hover(function() {
						$(this).html($(this).data("cellId"));
					}, function() {
						if ($(this).hasClass("active")) {
							$(this).html("&nbsp;");
						}
					});
				});
			}
		}
	};

	// I clear the table of any markup.
	Plate.prototype.clearTable = function() {
		this.table.empty();
	};

	// I initialize the table.
	Plate.prototype.initTable = function() {
		var self = this;

		this.curPlate = this.selectedPlate();
		$("#plateInfo #name").val(this.curPlate.name);
		$("#plateInfo #width").val(this.curPlate.width);
		$("#plateInfo #height").val(this.curPlate.height);
		$("#plateInfo #subDivide").val(this.curPlate.subDivide);

		// Clear the table if there is any existing markup.
		this.clearTable();

		// Now that we have ensured that the table is
		// empty, let's build out the HTML for the table.
		this.buildTable();

		// Gather the cells of the table.
		this.cells = this.table.find("td");
	};

	// I handle the clicks at the table level.
	Plate.prototype.onClick = function(event) {
		// Get the trigger for the event.
		var target = $(event.target);

		// Check to make sure the target is an active cell.
		// If it is not, then we are not interested.
		if (!target.is("td.active")) {
			// Deselect the cell
			this.selectCell(target, false);
		} else {
			// Select the cell
			this.selectCell(target, true);
		}
	};

	// I select/deselect the cell
	Plate.prototype.selectCell = function(cell, select) {
		// Check if we should select or deselect
		if (select) {
			cell.removeClass("active");
			cell.addClass("selected");
		} else {
			cell.removeClass("selected");
			cell.addClass("active");
		}
	};

	Plate.prototype.retrieveDataPoints = function() {
		var arr = [];
		this.table.find("td.selected").each(function(index, e) {
			arr[index] = $(e).data("dp");
		});
		return arr;
	};

	Plate.prototype.initPlate = function() {
		var self = this;
		this.populatePlates();

		// setup save click handler
		this.saveButton.click(function() {
			self.curPlate.name = $("#plateInfo #name").val();
			self.curPlate.width = parseInt($("#plateInfo #width").val());
			self.curPlate.height = parseInt($("#plateInfo #height").val());
			self.curPlate.subDivide = parseInt($("#plateInfo #subDivide").val());
			self.curPlate.result = {};
			self.curPlate.result.dataPoints = self.retrieveDataPoints();
			$.post('plates', self.curPlate, function(data) {
				self.curPlate = data;
				self.initTable();
			});
		});

		// setup on select handler
		this.select.change(function() {
			self.initTable();
		});

	};

	Plate.prototype.selectedPlate = function() {
		return this.plates[$(this.select).attr("selectedIndex")];
	};

	Plate.prototype.populatePlates = function(selectedValue) {

		// retrieve plates
		var self = this;
		$.getJSON('plates', function(j) {
			self.plates = j;

			var options = '';
			for ( var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].id + '">' + j[i].name + '</option>';
			}
			self.select.html(options);

			if (typeof selectedValue == "undefined") {
				$(self.select.selector + " option:eq(0)").attr("selected", "selected");
			} else {
				$(self.select.selector + " option[value='" + selectedValue + "']").attr('selected', 'selected');
			}
			self.initTable();
		});
	};

	window.Plate = Plate;

})(jQuery);