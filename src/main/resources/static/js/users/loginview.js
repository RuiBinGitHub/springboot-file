$(document).ready(function() {

	$("input[name=pass]").attr("type", "password");

	$("input").on("input", function() {
		$("#tips").text("");
	});

});