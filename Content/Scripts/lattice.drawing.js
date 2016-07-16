//this class visualizes a lattice structure
function LatticeViewer(canvas, holder){
	this.canvas = canvas;
	this.holder = holder;
	this.scale = null;
	this.data = null;
	this.gridEnabled = false;
	this.pointCssClass = "";
	this.markablePointCssClass = "";
	this.points = new LatticePointCollection();
	
	//sets the global alias
	window["lattice"] = this;
}

LatticeViewer.prototype = {
	//gets the canvas element as a jquery wrapper
	get_canvas: function(){
		return this.canvas;
	},
	
	//gets the holder element as a jquery wrapper 
	//for storing other elements
	get_holder: function(){
		return this.holder;
	},
	
	//gets the 2-D context of the canvas element
	get_context: function(){
		return this.canvas.get(0).getContext("2d");
	},
	
	//gets the offset positions of the lattice viewer
	get_originPoint: function(){
		var offset = this.get_canvas().offset();
		//gets the DOM element from the jquery wrapper
		var canvas = this.get_canvas().get(0);
		//sets the x coordinate to the middle of the lattice grid
		offset.width = (offset.left + canvas.offsetWidth) / 2;
		//sets the y coordinate to the bottom of the lattice grid
		offset.height = offset.top + canvas.offsetHeight - 30;
		
		return offset;
	},
	
	//ges the point collection
	get_data: function(){
		return this.data;
	},
	
	//sets the point collection
	set_data: function(value){
		this.data = value;
	},
	
	//gets the point css class
	get_pointCssClas: function(){
		return this.pointCssClass;
	},
	
	//sets the point css class
	set_pointCssClass: function(value){
		this.pointCssClass = value;
	},
	
	//gets the css class for the point which is markable
	get_markablePointCssClass: function(){
		return this.markablePointCssClass;
	},
	
	//sets the css class for the point which is markable
	set_markablePointCssClass: function(value){
		this.markablePointCssClass = value;
	},
	
	//returns true whether the grid is enabled 
	get_gridEnabled: function(){
		return this.gridEnabled;
	},
	
	//sets the value whether the grid is enabled
	set_gridEnabled: function(value){
		this.gridEnabled = value;
	},
	
	//gets the x, y axes scale 
	get_scale: function(){
		return this.scale;
	},
	
	//sets the x, y axes scale
	set_scale: function(scaleX, scaleY){
		this.scale = {"x": scaleX, "y": scaleY};
	},
	
	//adds a new point to the lattice visualization
	addPoint: function(descriptor){
		var cvs = this.canvas.get(0);
		var scale = this.get_scale();
		var offset = this.get_originPoint();

		//scales the coordinates
		var x = descriptor.x * scale.x;
		var y = descriptor.y * scale.y;
		
		//calculates the relative coordinates
		var top = offset.height - y;
		var left = offset.width + x;
		
		if(left > cvs.offsetWidth)
			left = cvs.offsetWidth - 10;
		
		if(left < cvs.offsetLeft)
			left = cvs.offsetLeft + 10;
				
		//creates a new point element
		var pointElement = $("<span></span>")
         .addClass(descriptor.markable ? this.get_markablePointCssClass() : this.get_pointCssClas())
         //adds an id to the element
         .attr("id", "pt-" + descriptor.name)
         //sets the text of the element
         .html(descriptor.name)
         //adds x, y positions to the element
         .css({"top": top + "px", "left": left + "px"})
        	   
         //adds the element to the page
         .insertAfter(this.get_canvas());
		
		this.points.add(new LatticePoint(
				this.get_canvas(), pointElement, descriptor));
	},
	
	//shows the grid visualization
	showGrid: function(){
		var gridSize = 20;	
		var cvs = this.get_canvas();
		var ctx = this.get_context();
		var width = cvs.width();
		var height = cvs.height();
		
		ctx.beginPath();
		//draws horizontal lines
        for (var ypos = 0; ypos < height; ypos += gridSize) {
            ctx.moveTo(0, ypos);
            ctx.lineTo(width, ypos);
        }
        
        //draws vertical lines
        for (var xpos = 0; xpos < width; xpos += gridSize) {
            ctx.moveTo(xpos, 0);
            ctx.lineTo(xpos, width);
        }

        ctx.strokeStyle = "#eee";
        ctx.lineWidth = .7;
        ctx.stroke();
	},
	
	//shows the lattice points
	show: function(){
		var self = this;
		var ctx = this.get_context();
		
		if(this.gridEnabled)
			this.showGrid();
		
		if(!this.data)
			return;
		
		//adds points to the right position 
		$.each(this.data, function(index, descriptor){
			self.addPoint(descriptor);
		});
		
		//draws lines
		$.each(this.points.get_array(), function(index, srcPoint){
			var srcPosition = srcPoint.get_position();
			
			$.each(srcPoint.get_dependents(), function(index, name){
            	var dstPoint = self.points.findByName(name);
            	var dstPosition = dstPoint.get_position();
            	       	
            	//draws the line
            	ctx.beginPath();
            	ctx.moveTo(srcPosition.left, srcPosition.top);
            	ctx.lineTo(dstPosition.left, dstPosition.top);
            	ctx.strokeStyle = "#000000";
                ctx.lineWidth = .4;
                ctx.stroke();           	
            });
		});
	},
	
	dispose: function(){
		var cvs = this.canvas.get(0);
		//clears the canvas
		cvs.width = cvs.width;
		
		if(this.points)
			//disposes each point in the collection
			$.each(this.points.get_array(), function(index, point){
				point.dispose();
			});
		
		//clears the point collection
		this.points = null;
	}
};

//this class represents a lattice element
function LatticePoint(canvas, pointElement, pointDescriptor){
	this.canvas = canvas; /* jQuery wrapper */
	this.pointElement = pointElement; /*jQuery wrapper */
	this.pointDescriptor = pointDescriptor; /* JSON object with properties: name, x, y, dependents */
}

LatticePoint.prototype = {
    //gets the name of the lattice point
	get_name: function(){
		return this.pointDescriptor.name;
	},
	
	//gets the point dependents
	get_dependents: function(){
		return this.pointDescriptor.dependents;
	},
	
	//gets the point position
	get_position: function(){
		var offset = this.canvas.offset();
		var element =  this.pointElement.get(0);
	    var offsetWidth = element.offsetWidth;
	    var offsetHeight = element.offsetHeight;
		var position = this.pointElement.position();
		
		return {
			"left": position.left - offset.left + (offsetWidth / 2), 
			"top": position.top - offset.top + (offsetHeight /2 )};
	},
	
	//disposes the lattice point
	dispose: function(){
		this.pointElement.remove();
	}
};

//this class represents a lattice point collection
function LatticePointCollection(){
	this.collection = new Array();
}

LatticePointCollection.prototype = {
	//gets the length of the collection
	get_length: function(){
		return this.collection.length;
	},
	
	//gets the array of the collection
	get_array: function(){
		return this.collection;
	},	
	
	//adds a new point to the collection
	add: function(point){
		this.collection[this.collection.length] = point;
	},
	
	//finds a point by name
	findByName: function(name){
		for(var i = 0; i < this.collection.length; i++){
			var point = this.collection[i];
			
			if(point.get_name() === name)
				return point;
		}
		
		return null;
	}
};



