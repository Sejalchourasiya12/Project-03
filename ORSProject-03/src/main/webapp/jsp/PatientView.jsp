<%@page import="in.co.rays.project_3.controller.PatientCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient View</title>

<style>
.hm {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/hospital.jpg');
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    padding-top: 75px;
}
.input-group-addon {
    box-shadow: 9px 8px 7px #001a33;
}
</style>
</head>

<body class="hm">

<%@include file="Header.jsp"%>
<%@include file="calendar.jsp"%>

<form action="<%=ORSView.PATIENT_CTL%>" method="post">

<jsp:useBean id="dto"
    class="in.co.rays.project_3.dto.PatientDTO"
    scope="request"/>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card input-group-addon">
<div class="card-body">

<%
    if (dto.getId() != null && dto.getId() > 0) {
%>
    <h3 class="text-center text-primary">Update Patient</h3>
<%
    } else {
%>
    <h3 class="text-center text-primary">Add Patient</h3>
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

<!-- Patient Name -->
<b>Patient Name *</b>
<input type="text" name="name" class="form-control"
    value="<%=DataUtility.getStringData(dto.getName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("name", request)%>
</font><br>

<!-- Date of Birth -->
<b>Date of Birth *</b>
<input type="text" name="dob" id="datepicker2"
    class="form-control" readonly
    value="<%=DataUtility.getDateString(dto.getDob())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("dob", request)%>
</font><br>

<!-- Mobile No -->
<b>Mobile No *</b>
<input type="text" name="mobileNo" class="form-control"
    value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("mobileNo", request)%>
</font><br>

<!-- Disease -->
<b>Disease *</b>
<input type="text" name="decease" class="form-control"
    value="<%=DataUtility.getStringData(dto.getDecease())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("decease", request)%>
</font><br>

<!-- Buttons -->
<div class="text-center">
<%
    if (dto.getId() != null && dto.getId() > 0) {
%>
    <input type="submit" name="operation"
        value="<%=PatientCtl.OP_UPDATE%>"
        class="btn btn-success">

    <input type="submit" name="operation"
        value="<%=PatientCtl.OP_CANCEL%>"
        class="btn btn-warning">
<%
    } else {
%>
    <input type="submit" name="operation"
        value="<%=PatientCtl.OP_SAVE%>"
        class="btn btn-success">

    <input type="submit" name="operation"
        value="<%=PatientCtl.OP_RESET%>"
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
