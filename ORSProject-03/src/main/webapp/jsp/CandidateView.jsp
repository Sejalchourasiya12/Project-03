<%@page import="in.co.rays.project_3.controller.CandidateCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Candidate View</title>
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
</div>

<main>
<form action="<%=ORSView.CANDIDATE_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.CandidateDTO" scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>
<div class="col-md-4 mb-4">

<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getId() != null) {
%>
<h3 class="text-center text-primary">Update Candidate</h3>
<%
	} else {
%>
<h3 class="text-center text-primary">Add Candidate</h3>
<%
	}
%>

<!-- Success Message -->
<H4 align="center">
<%
	if (!ServletUtility.getSuccessMessage(request).equals("")) {
%>
<div class="alert alert-success alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<%
	}
%>
</H4>

<!-- Error Message -->
<H4 align="center">
<%
	if (!ServletUtility.getErrorMessage(request).equals("")) {
%>
<div class="alert alert-danger alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getErrorMessage(request)%>
</div>
<%
	}
%>
</H4>

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Candidate Code -->
<span class="pl-sm-5"><b>Candidate Code</b><span style="color: red;">*</span></span><br>
<div class="col-sm-12">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-id-badge grey-text"></i>
</div>
</div>
<input type="text" name="candidateCode" class="form-control"
	placeholder="Enter Candidate Code"
	value="<%=DataUtility.getStringData(dto.getCandidateCode())%>">
</div>
</div>
<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("candidateCode", request)%>
</font><br>

<!-- Candidate Name -->
<span class="pl-sm-5"><b>Candidate Name</b><span style="color: red;">*</span></span><br>
<div class="col-sm-12">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-user grey-text"></i>
</div>
</div>
<input type="text" name="candidateName" class="form-control"
	placeholder="Enter Candidate Name"
	value="<%=DataUtility.getStringData(dto.getCandidateName())%>">
</div>
</div>
<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("candidateName", request)%>
</font><br>

<!-- Email -->
<span class="pl-sm-5"><b>Email</b><span style="color: red;">*</span></span><br>
<div class="col-sm-12">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-envelope grey-text"></i>
</div>
</div>
<input type="text" name="email" class="form-control"
	placeholder="Enter Email"
	value="<%=DataUtility.getStringData(dto.getEmail())%>">
</div>
</div>
<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("email", request)%>
</font><br>

<!-- Skill Set -->
<span class="pl-sm-5"><b>Skill Set</b><span style="color: red;">*</span></span><br>
<div class="col-sm-12">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-cogs grey-text"></i>
</div>
</div>
<input type="text" name="skillSet" class="form-control"
	placeholder="Enter Skill Set"
	value="<%=DataUtility.getStringData(dto.getSkillSet())%>">
</div>
</div>
<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("skillSet", request)%>
</font><br><br>

<%
	if (id > 0) {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success btn-md"
	value="<%=CandidateCtl.OP_UPDATE%>">

<input type="submit" name="operation"
	class="btn btn-warning btn-md"
	value="<%=CandidateCtl.OP_CANCEL%>">
</div>
<%
	} else {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success btn-md"
	value="<%=CandidateCtl.OP_SAVE%>">

<input type="submit" name="operation"
	class="btn btn-warning btn-md"
	value="<%=CandidateCtl.OP_RESET%>">
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