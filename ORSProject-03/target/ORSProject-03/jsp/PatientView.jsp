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

<style>
.hm {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/hospital.jpg');
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    padding-top: 80px;
    padding-bottom: 50px;
}

.card-design {
    box-shadow: 8px 8px 10px #001a33;
    border-radius: 10px;
}

.form-group {
    margin-bottom: 15px;
}
</style>
</head>

<body class="hm">

<%@include file="Header.jsp"%>

<form action="<%=ORSView.QUEUE_CTL%>" method="post">

<jsp:useBean id="dto"
    class="in.co.rays.project_3.dto.QueueDTO"
    scope="request"/>

<div class="row">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card card-design">
<div class="card-body p-4">

<%
    if (dto.getId() != null && dto.getId() > 0) {
%>
    <h3 class="text-center text-primary mb-4">Update Queue</h3>
<%
    } else {
%>
    <h3 class="text-center text-primary mb-4">Add Queue</h3>
<%
    }
%>

<!-- Success Message -->
<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div class="alert alert-success text-center">
    <%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>

<!-- Error Message -->
<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div class="alert alert-danger text-center">
    <%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>

<input type="hidden" name="id" value="<%=dto.getId()%>">

<!-- Queue Code -->
<div class="form-group">
<b>Queue Code *</b>
<input type="text" name="queueCode" class="form-control"
    value="<%=DataUtility.getStringData(dto.getQueueCode())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("queueCode", request)%>
</font>
</div>

<!-- Queue Name -->
<div class="form-group">
<b>Queue Name *</b>
<input type="text" name="queueName" class="form-control"
    value="<%=DataUtility.getStringData(dto.getQueueName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("queueName", request)%>
</font>
</div>

<!-- Queue Size -->
<div class="form-group">
<b>Queue Size *</b>
<input type="number" name="queueSize" class="form-control"
    value="<%=dto.getQueueSize() != null ? dto.getQueueSize() : ""%>">
<font color="red">
<%=ServletUtility.getErrorMessage("queueSize", request)%>
</font>
</div>

<!-- Queue Status -->
<div class="form-group">
<b>Queue Status *</b>
<select name="queueStatus" class="form-control">
    <option value="">--Select--</option>
    <option value="Active"
        <%= "Active".equals(dto.getQueueStatus()) ? "selected" : "" %>>
        Active
    </option>
    <option value="Inactive"
        <%= "Inactive".equals(dto.getQueueStatus()) ? "selected" : "" %>>
        Inactive
    </option>
</select>
<font color="red">
<%=ServletUtility.getErrorMessage("queueStatus", request)%>
</font>
</div>

<!-- Buttons -->
<div class="text-center mt-4">
<%
    if (dto.getId() != null && dto.getId() > 0) {
%>
    <input type="submit" name="operation"
        value="<%=QueueCtl.OP_UPDATE%>"
        class="btn btn-success mr-2">

    <input type="submit" name="operation"
        value="<%=QueueCtl.OP_CANCEL%>"
        class="btn btn-warning">
<%
    } else {
%>
    <input type="submit" name="operation"
        value="<%=QueueCtl.OP_SAVE%>"
        class="btn btn-success mr-2">

    <input type="submit" name="operation"
        value="<%=QueueCtl.OP_RESET%>"
        class="btn btn-warning">
<%
    }
%>
</div>

</div>
</div>
</div>

<div class="col-md-4"></div>
</div>

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>
