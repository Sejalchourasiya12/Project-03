<%@page import="in.co.rays.project_3.controller.AnnouncementCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Announcement View</title>
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
	<%@include file="calendar.jsp" %>
</div>

<main>
<form action="<%=ORSView.ANNOUNCEMENT_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AnnouncementDTO"
	scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>
<div class="col-md-4 mb-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getId() != null) {
%>
<h3 class="text-center text-primary">Update Announcement</h3>
<%
	} else {
%>
<h3 class="text-center text-primary">Add Announcement</h3>
<%
	}
%>

<!-- Success Message -->
<h4 align="center">
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
</h4>

<!-- Error Message -->
<h4 align="center">
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
</h4>

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Announcement Code -->
<span><b>Announcement Code</b><span style="color: red;">*</span></span><br>
<input type="text" name="announcementCode" class="form-control"
	placeholder="Enter Announcement Code"
	value="<%=DataUtility.getStringData(dto.getAnnouncementCode())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("announcementCode", request)%>
</font>
<br>

<!-- Title -->
<span><b>Title</b><span style="color: red;">*</span></span><br>
<input type="text" name="title" class="form-control"
	placeholder="Enter Title"
	value="<%=DataUtility.getStringData(dto.getTitle())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("title", request)%>
</font>
<br>

<!-- Description -->
<span><b>Description</b><span style="color: red;">*</span></span><br>
<textarea name="description" class="form-control"
	placeholder="Enter Description"><%=DataUtility.getStringData(dto.getDescription())%></textarea>
<font color="red">
<%=ServletUtility.getErrorMessage("description", request)%>
</font>
<br>

<!-- Publish Date -->
<span><b>Publish Date</b><span style="color: red;">*</span></span><br>
<input type="text" id="datepicker2" name="publishDate" class="form-control"
	value="<%=DataUtility.getDateString(dto.getPublishDate())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("publishDate", request)%>
</font>
<br><br>

<%
	if (id > 0) {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success btn-md"
	value="<%=AnnouncementCtl.OP_UPDATE%>">
<input type="submit" name="operation"
	class="btn btn-warning btn-md"
	value="<%=AnnouncementCtl.OP_CANCEL%>">
</div>
<%
	} else {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success btn-md"
	value="<%=AnnouncementCtl.OP_SAVE%>">
<input type="submit" name="operation"
	class="btn btn-warning btn-md"
	value="<%=AnnouncementCtl.OP_RESET%>">
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