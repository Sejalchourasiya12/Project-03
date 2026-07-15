<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.QueueDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.QueueListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Queue List View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
 href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

<style>
.hm {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    padding-top: 85px;
}
</style>
</head>

<body class="hm">

<%@include file="Header.jsp"%>

<form action="<%=ORSView.QUEUE_LIST_CTL%>" method="post">

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;

int nextPageSize = 0;
if (request.getAttribute("nextListSize") != null) {
    nextPageSize = DataUtility.getInt(
        request.getAttribute("nextListSize").toString());
}

List list = ServletUtility.getList(request);
Iterator<QueueDTO> it = list.iterator();
%>

<div class="container">

<center>
<h1 class="text-dark font-weight-bold pt-3">
<u>Queue List</u>
</h1>
</center>

<!-- Success Message -->
<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div class="alert alert-success alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>

<!-- Error Message -->
<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div class="alert alert-danger alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>

<!-- 🔍 Search Row -->
<div class="row mt-3">

<div class="col-sm-3">
<input type="text" name="queueCode"
class="form-control"
placeholder="Enter Queue Code"
value="<%=ServletUtility.getParameter("queueCode", request)%>">
</div>

<div class="col-sm-3">
<input type="text" name="queueName"
class="form-control"
placeholder="Enter Queue Name"
value="<%=ServletUtility.getParameter("queueName", request)%>">
</div>

<div class="col-sm-3">
<select name="queueStatus" class="form-control">
<option value="">--Select Status--</option>
<option value="Active">Active</option>
<option value="Inactive">Inactive</option>
</select>
</div>

<div class="col-sm-3">
<input type="submit" name="operation"
class="btn btn-primary"
value="<%=QueueListCtl.OP_SEARCH%>">

<input type="submit" name="operation"
class="btn btn-dark"
value="<%=QueueListCtl.OP_RESET%>">
</div>

</div>

<br>

<% if(list != null && !list.isEmpty()) { %>

<!-- 📋 Table -->
<div class="table-responsive">
<table class="table table-bordered table-dark table-hover">

<thead>
<tr style="background-color:#8C8C8C;">
<th><input type="checkbox" id="select_all"> Select All</th>
<th>S.No</th>
<th>Queue Code</th>
<th>Queue Name</th>
<th>Queue Size</th>
<th>Status</th>
<th>Edit</th>
</tr>
</thead>

<tbody>
<%
while(it.hasNext()) {
QueueDTO dto = it.next();
%>

<tr>
<td align="center">
<input type="checkbox" name="ids"
value="<%=dto.getId()%>">
</td>

<td align="center"><%=index++%></td>
<td align="center"><%=dto.getQueueCode()%></td>
<td align="center"><%=dto.getQueueName()%></td>
<td align="center"><%=dto.getQueueSize()%></td>
<td align="center"><%=dto.getQueueStatus()%></td>

<td align="center">
<a href="QueueCtl?id=<%=dto.getId()%>">Edit</a>
</td>
</tr>

<% } %>
</tbody>
</table>
</div>

<!-- 🔁 Pagination Buttons -->
<table width="100%">
<tr>

<td>
<input type="submit" name="operation"
class="btn btn-warning"
value="<%=QueueListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center">
<input type="submit" name="operation"
class="btn btn-primary"
value="<%=QueueListCtl.OP_NEW%>">

<input type="submit" name="operation"
class="btn btn-danger"
value="<%=QueueListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation"
class="btn btn-warning"
value="<%=QueueListCtl.OP_NEXT%>"
<%=(nextPageSize != 0) ? "" : "disabled"%>>
</td>

</tr>
</table>

<% } else { %>

<div class="alert alert-info text-center mt-4">
<h4>No Queues Available</h4>
</div>

<% } %>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</div>

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>
