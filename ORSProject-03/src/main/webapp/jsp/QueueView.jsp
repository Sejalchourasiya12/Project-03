<%@page import="in.co.rays.project_3.controller.QueueCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Queue View</title>

<style type="text/css">
.p4{
background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
background-repeat: no-repeat;
background-attachment: fixed; 
background-size: cover;
padding-top: 75px;
padding-bottom: 50px;
}
</style>
</head>

<body class="p4">

<div class="header">
	<%@include file="Header.jsp"%>
</div>

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.QueueDTO"
	scope="request"></jsp:useBean>

<main>
<form action="<%=ORSView.QUEUE_CTL%>" method="post">

<div class="row pt-3 pb-3">
<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">
<div class="card">
<div class="card-body">

<% if (dto.getId() != null && dto.getId() > 0) { %>
<h3 class="text-center text-primary">Update Queue</h3>
<% } else { %>
<h3 class="text-center text-primary">Add Queue</h3>
<% } %>

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

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Queue Code -->
<span class="pl-sm-5"><b>Queue Code</b><span style="color: red;">*</span></span><br>

<div class="col-sm-12 mb-2">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-code grey-text"></i>
</div>
</div>
<input type="text" class="form-control" name="queueCode"
placeholder="Queue Code"
value="<%=DataUtility.getStringData(dto.getQueueCode())%>">
</div>
</div>
<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("queueCode", request)%>
</font><br>

<!-- Queue Name -->
<span class="pl-sm-5"><b>Queue Name</b><span style="color: red;">*</span></span><br>

<div class="col-sm-12 mb-2">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-list"></i>
</div>
</div>
<input type="text" class="form-control" name="queueName"
placeholder="Queue Name"
value="<%=DataUtility.getStringData(dto.getQueueName())%>">
</div>
</div>
<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("queueName", request)%>
</font><br>

<!-- Queue Size -->
<span class="pl-sm-5"><b>Queue Size</b><span style="color: red;">*</span></span><br>

<div class="col-sm-12 mb-2">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-sort-numeric-up"></i>
</div>
</div>
<input type="number" class="form-control" name="queueSize"
placeholder="Queue Size"
min="1"
required
value="<%=dto.getQueueSize()!=null?dto.getQueueSize():""%>">
</div>
</div>
<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("queueSize", request)%>
</font><br>

<!-- Queue Status -->
<span class="pl-sm-5"><b>Queue Status</b><span style="color: red;">*</span></span><br>

<div class="col-sm-12 mb-3">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-toggle-on"></i>
</div>
</div>

<select name="queueStatus" class="form-control" required>
<option value="">--Select--</option>
<option value="Active"
<%= "Active".equals(dto.getQueueStatus())?"selected":"" %>>
Active
</option>
<option value="Inactive"
<%= "Inactive".equals(dto.getQueueStatus())?"selected":"" %>>
Inactive
</option>
</select>

</div>
</div>
<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("queueStatus", request)%>
</font><br>

<!-- Buttons -->
<div class="text-center mt-3">

<% if (dto.getId()!=null && dto.getId()>0) { %>

<input type="submit" class="btn btn-success mr-2" name="operation"
value="<%=QueueCtl.OP_UPDATE%>">

<input type="submit" class="btn btn-warning" name="operation"
value="<%=QueueCtl.OP_CANCEL%>">

<% } else { %>

<input type="submit" name="operation"
class="btn btn-success btn-md mr-2"
value="<%=QueueCtl.OP_SAVE%>">

<input type="submit" name="operation"
class="btn btn-warning btn-md"
value="<%=QueueCtl.OP_RESET%>">

<% } %>

</div>

</div>
</div>
</div>

<div class="col-md-4 mb-4"></div>
</div>

</form>
</main>

<%@include file="FooterView.jsp"%>

</body>
</html>
