<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Welcome</title>
<link rel="stylesheet" href=" <c:url value="/styles/style.css"/>" />
<script type="text/javascript" src="<c:url value="/scripts/jquery-1.4.1.min.js" /> "></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery.repeatstring.js" /> "></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery.ajax.js" /> "></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery.label.js" /> "></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery.plate.js" /> "></script>
<script type="text/javascript">
	// When the DOM is ready, build the mine sweeper game.
	jQuery(function($) {
		var plate = new Plate($("table"), 12, 8, 1);
	});
</script>
</head>
<body>
<div id="container">
<div id="header"></div>
<div id="mainContent">
<div id="loading">Loading...</div>
<div id="plateDetails">
<fieldset><label>Group Label:</label><select id="labelName"></select><input id="editLabelButton" type="button" value="Edit..." /><input id="newLabelButton" type="button" value="New..." /><input
	id="deleteLabelButton" type="button" value="Delete" /></fieldset>
<fieldset><label>Plate Name:</label><select id="plateName"></select><input id="savePlateButton" type="button" value="Save" /><input
	id="deletePlateButton" type="button" value="Delete" /><input id="newPlateButton" type="button" value="New..." /></fieldset>
</div>
<div id="grid">
<table cellspacing="2">
	<!--- Will be populated dynamically. --->
</table>
</div>
<div id="plateInfo">
	<fieldset>
		<label>Name:</label><input id="name" type="text" />
		<label>Label:</label><select id="label"></select>
		<label>Width:</label><input id="width" type="text" />
		<label>Height:</label><input id="height" type="text" />
		<label>SubDivide:</label><input id="subDivide" type="text" />
	</fieldset>
</div>
</div>
<div id="footer"></div>
</div>
</body>
</html>
