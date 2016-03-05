function filter1(phrase, _id) {
	var words = phrase.value.toLowerCase().split(" ");
	var table = document.getElementById(_id);
	for ( var r = 1; r < table.rows.length; r++) {
		var cellsV = table.rows[r].cells[0].innerHTML.replace(/<[^>]+>/g, "");
		var cellsV = [ cellsV ].join(" ");
		var displayStyle = 'none';
		for ( var i = 0; i < words.length; i++) {
			if (cellsV.toLowerCase().indexOf(words[i]) >= 0)
				displayStyle = '';
			else {
				displayStyle = 'none';
				break;
			}
		}
		table.rows[r].style.display = displayStyle;
	}
}
