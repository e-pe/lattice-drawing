//creates a new lattice viewer and disposes the old
function createLatticeViewer(dimension, number){
	var params = "";
	//gets the current lattice viewer object
	var lattice = window["lattice"];
	//creates a parameter string presentation
	if(dimension && number)
		params = "?dimension="+ dimension +"&number=" + number;	
	//disposes the previous lattice viewer object
	if(lattice)
		lattice.dispose();	
	//creates a new request for downloading the data	
	$.ajax({
		  url: "../LatticeServlet" + params,
		  success: function(points) {
			var viewer = new LatticeViewer($("#cvsLattice"), $("#cvsHolder"));			
			viewer.set_data(points);
			viewer.set_gridEnabled(true);
			viewer.set_pointCssClass("circle");
			viewer.set_markablePointCssClass("markable-circle");
			viewer.set_scale(50, 50);			
			viewer.show(); 
		  },
		  error: function(){
			  alert("Lattice cannot be loaded.");
		  }
	});
}

//handles the button click
function onLoadLatticeClick(){
	createLatticeViewer($('#ddlDimension').val(), $('#txtInput').val());
}

//handles the button click
function onLoadNextLatticeClick(){
	$('#txtInput').val(parseInt($('#txtInput').val()) + 1);

	onLoadLatticeClick();
}