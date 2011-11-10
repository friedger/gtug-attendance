<%@ include file="include.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>GTUG Attendance <c:out value="${param.pageTitle}"/></title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="https://github.com/necolas/normalize.css/blob/master/normalize.css">
	<link href='http://fonts.googleapis.com/css?family=Terminal+Dosis' rel='stylesheet' type='text/css'>
	<link rel="stylesheet/less" type="text/css" href="/styles.less">
	<script src="http://lesscss.googlecode.com/files/less-1.1.3.min.js" type="text/javascript"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
	<script src="https://raw.github.com/jquery/jquery-tmpl/master/jquery.tmpl.js"></script>
    <script src="http://ajax.cdnjs.com/ajax/libs/underscore.js/1.1.4/underscore-min.js"></script>
    <script src="http://ajax.cdnjs.com/ajax/libs/backbone.js/0.3.3/backbone-min.js"></script>
</head>
<body>
	
	<header>
		<h1>GTUG Attendance</h1>
	</header>
	
	<section id="content">