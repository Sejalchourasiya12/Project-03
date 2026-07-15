<%@page import="in.co.rays.project_3.controller.SubscriptionPlanCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subscription Plan</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.p4 {
background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
background-repeat: no-repeat;
background-attachment: fixed;
background-size: cover;
padding-top: 75px;
}
</style>
</head>

<body class="p4">

<div class="header">
<%@include file="Header.jsp"%>
<%@include file="calendar.jsp"%>
</div>

<main>

<form action="<%=ORSView.SUBSCRIPTION_PLAN_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.SubscriptionPlanDTO"
scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">

<div class="card">

<div class="card-body">

<%
long id = DataUtility.getLong(request.getParameter("id"));
if (id > 0) {
%>
<h3 class="text-center text-primary">Update Subscription Plan</h3>
<%
} else {
%>
<h3 class="text-center text-primary">Add Subscription Plan</h3>
<%
}
%>

<!-- Success Message -->
<h4 align="center">
<%
if (!ServletUtility.getSuccessMessage(request).equals("")) {
%>
<div class="alert alert-success alert-dismissible">
<button type="button" class="close" data-dismiss="alert">×</button>
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<%
}
%>
</h4>

<!-- Error Message -->
<h4 align="center">
<%
if (!ServletUtility.getErrorMessage(request).equals("")) {
%>
<div class="alert alert-danger alert-dismissible">
<button type="button" class="close" data-dismiss="alert">×</button>
<%=ServletUtility.getErrorMessage(request)%>
</div>
<%
}
%>
</h4>

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Plan Name -->
<span><b>Plan Name</b><span style="color:red">*</span></span><br>
<input type="text"
name="planName"
class="form-control"
placeholder="Enter Plan Name"
value="<%=DataUtility.getStringData(dto.getPlanName())%>">
<font color="red"><%=ServletUtility.getErrorMessage("planName", request)%></font>
<br>

<!-- Price -->
<span><b>Price</b><span style="color:red">*</span></span><br>
<input type="number"
step="0.01"
name="price"
class="form-control"
placeholder="Enter Price"
value="<%=dto.getPrice() != null ? dto.getPrice() : ""%>">
<font color="red"><%=ServletUtility.getErrorMessage("price", request)%></font>
<br>

<!-- Validity Days -->
<span><b>Validity Days</b><span style="color:red">*</span></span><br>
<input type="number"
name="validityDays"
class="form-control"
placeholder="Enter Validity Days"
value="<%=dto.getValidityDays() != null ? dto.getValidityDays() : ""%>">
<font color="red"><%=ServletUtility.getErrorMessage("validityDays", request)%></font>
<br><br>

<%
if (id > 0) {
%>
<div class="text-center">
<input type="submit" name="operation" class="btn btn-success btn-md"
value="<%=SubscriptionPlanCtl.OP_UPDATE%>">
<input type="submit" name="operation" class="btn btn-warning btn-md"
value="<%=SubscriptionPlanCtl.OP_CANCEL%>">
</div>
<%
} else {
%>
<div class="text-center">
<input type="submit" name="operation" class="btn btn-success btn-md"
value="<%=SubscriptionPlanCtl.OP_SAVE%>">
<input type="submit" name="operation" class="btn btn-warning btn-md"
value="<%=SubscriptionPlanCtl.OP_RESET%>">
</div>
<%
}
%>

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
