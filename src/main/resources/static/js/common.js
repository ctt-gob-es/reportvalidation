// Deletes a row that contains it an rename the name attr to preserve indexes
function deleteRow(e) {
	var table = e.closest('table');
	e.closest('tr').remove();
	var index = 0;
	
	$("#"+table.id + " tbody tr").each(
			function(i) {
				$('input', this).each(
						function(i) {
							$(this).attr(
									'name',
									$(this).attr('name').replace(/\[[0-9]*\]/g,
											'[' + index + ']'));
						});
				index++;
			});
}

function deleteVertice(e) {
	var table = e.closest('table');
	var row = e.closest('tr');
	var verticeValue = $("input[name$=numeroVertice]", row).val();
	e.closest('tr').remove();
	var index = 0;
	
	$("input[name$=numeroVertice]").each(function() {
		
		if(parseInt($(this).val()) > parseInt(verticeValue)){
			$(this).val($(this).val()-1);
		}}
		);
	$("#"+table.id + " tbody tr").each(
			function(i) {
				$('input', this).each(
						function(i) {
							$(this).attr(
									'name',
									$(this).attr('name').replace(/\[[0-9]*\]/g,
											'[' + index + ']'));
						});
				index++;
			});
}

function ordenarNumeroVertice() {
	
	var lastInputValue = $('#verticesTable tbody tr:last-child input[name$=numeroVertice]').val();
	var index = 0;
	var verticeIndex = 1;
console.log($('#verticesTable tbody tr:has( input[name$=numeroVertice][value = ' + (parseInt(lastInputValue)) + '])').first());
	$('#verticesTable tbody tr:has( input[name$=numeroVertice][value = ' + (parseInt(lastInputValue)) + '])').first().before($('#verticesTable tbody tr:last-child '));
	
$("input[name$=numeroVertice]").each(function() {
		
		
			$(this).attr('value', verticeIndex);
			$(this).val(verticeIndex);

			verticeIndex++;
		}
		);

	
}

function ordenarVertices(id) {
	
	var lastInputValue = $('#verticesTable tbody tr:last-child input[name$=numeroVertice]').val();
	var index = 0;
	var verticeIndex = 1;
console.log($('#verticesTable tbody tr:has( input[name$=numeroVertice][value = ' + (parseInt(lastInputValue)) + '])').first());
	$('#verticesTable tbody tr:has( input[name$=numeroVertice][value = ' + (parseInt(lastInputValue)) + '])').first().before($('#verticesTable tbody tr:last-child '));
	
$("input[name$=numeroVertice]").each(function() {
		
		
			$(this).attr('value', verticeIndex);
			$(this).val(verticeIndex);

			verticeIndex++;
		}
		);

	$("#verticesTable tbody tr").each(
			function(i) {
				$('input', this).each(
						function(i) {
							$(this).attr(
									'name',
									$(this).attr('name').replace(/\[[0-9]*\]/g,
											'[' + index + ']'));
						});
				index++;
			});
	
}