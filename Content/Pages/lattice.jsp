<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />	

<title></title>
<link rel="stylesheet" type="text/css" href="../CssClasses/lattice.css" />

<script src="../Scripts/jquery-1.4.2.js"></script>
<script src="../Scripts/utils.js"></script>
<script src="../Scripts/lattice.drawing.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//createLatticeViewer($("#ddlDimension").val()[0], $("#txtInput").val());
	});
</script>
</head>
<body>
	<canvas id="cvsLattice" style="position:absolute; top:70px;left:50px" width="1400px" height="900px">
		<div id="cvsHolder"></div>
	</canvas>
	<select id="ddlDimension">
		<option>2</option>
		<option>3</option>
		<option>4</option>
		<option>5</option>
		<option>6</option>
		<option>7</option>
		<option>8</option>
		<option>9</option>
		<option>10</option>
		<option>11</option>
		<option>12</option>
	</select>
	<input type="text" id="txtInput" value="4"></input>
	<input type="button" value="Load Lattice" onclick="onLoadLatticeClick();" />
	<input type="button" value="Load Next Lattice" onclick="onLoadNextLatticeClick();" />
</body>
</html>