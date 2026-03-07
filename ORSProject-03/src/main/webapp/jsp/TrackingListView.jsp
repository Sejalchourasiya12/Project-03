<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.TrackingListCtl"%>
<%@page import="in.co.rays.project_3.dto.TrackingDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Tracking List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed; 
	background-size: cover;
	padding-top: 85px;
}
</style>
</head>

<body class="p4">

<%@include file="Header.jsp"%>

<form action="<%=ORSView.TRACKING_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.TrackingDTO"
	scope="request"></jsp:useBean>

<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
	List list = ServletUtility.getList(request);
	Iterator<TrackingDTO> it = list.iterator();
%>

<% if (list.size() != 0) { %>

<center>
<h1 class="text-light font-weight-bold pt-3">
	<font color="black"><u>Tracking List</u></font>
</h1>
</center>

<!-- Success Message -->
<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div align="center" style="color:green;">
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>

<!-- Error Message -->
<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div align="center" style="color:red;">
<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>

<br>

<!-- Search Section -->
<table align="center">
<tr>

<td>
<input type="text" name="trackingNumber"
	placeholder="Enter Tracking Number"
	value="<%=ServletUtility.getParameter("trackingNumber", request)%>">
</td>

<td>
<input type="text" name="currentLocation"
	placeholder="Enter Current Location"
	value="<%=ServletUtility.getParameter("currentLocation", request)%>">
</td>

<td>
<input type="submit" name="operation"
	value="<%=TrackingListCtl.OP_SEARCH%>">

<input type="submit" name="operation"
	value="<%=TrackingListCtl.OP_RESET%>">
</td>

</tr>
</table>

<br>

<!-- Table -->
<table border="1" width="100%" cellpadding="5"
	class="table table-bordered table-dark table-hover">

<tr>
<th><input type="checkbox" id="select_all"> Select All</th>
<th>S.NO</th>
<th>Tracking Number</th>
<th>Current Location</th>
<th>Status</th>
<th>Edit</th>
</tr>

<% while (it.hasNext()) {
	dto = it.next();
%>

<tr align="center">

<td>
<input type="checkbox" name="ids"
	value="<%=dto.getId()%>">
</td>

<td><%=index++%></td>
<td><%=dto.getTrackingNumber()%></td>
<td><%=dto.getCurrentLocation()%></td>
<td><%=dto.getStatus()%></td>

<td>
<a href="TrackingCtl?id=<%=dto.getId()%>">Edit</a>
</td>

</tr>

<% } %>

</table>

<br>

<!-- Buttons -->
<table width="100%">
<tr>

<td>
<input type="submit" name="operation"
	value="<%=TrackingListCtl.OP_PREVIOUS%>"
	<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center">
<input type="submit" name="operation"
	value="<%=TrackingListCtl.OP_NEW%>">
</td>

<td align="center">
<input type="submit" name="operation"
	value="<%=TrackingListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation"
	value="<%=TrackingListCtl.OP_NEXT%>"
	<%=(nextPageSize != 0) ? "" : "disabled"%>>
</td>

</tr>
</table>

<% } else { %>

<center>
<h1>Tracking List</h1>
</center>

<br>

<div align="center">
<input type="submit" name="operation"
	value="<%=TrackingListCtl.OP_BACK%>">
</div>

<% } %>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>