<%@ page import="org.unitsofmeasurement.quantity.*, org.eclipse.uomo.units.*, org.eclipse.uomo.units.impl.quantity.*" %>
<html>
<head>
	<title>Eclipse UOMo</title>
</head>
<body>
<h2>Hello UOMo!</h2>
<%
	Quantity<Length> q = new LengthAmount(12, SI.METRE);
%>
<%= q %>
</body>
</html>
