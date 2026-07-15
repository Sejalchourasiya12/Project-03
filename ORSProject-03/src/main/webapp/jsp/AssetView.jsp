<%@page import="in.co.rays.project_3.controller.AssetCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asset View</title>
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
<form action="<%=ORSView.ASSET_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AssetDTO"
	scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>
<div class="col-md-4 mb-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (id > 0) {
%>
<h3 class="text-center text-primary">Update Asset</h3>
<%
	} else {
%>
<h3 class="text-center text-primary">Add Asset</h3>
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

<!-- Asset Code -->
<span><b>Asset Code</b><span style="color: red;">*</span></span><br>
<input type="text" name="assetCode" class="form-control"
	placeholder="Enter Asset Code"
	value="<%=DataUtility.getStringData(dto.getAssetCode())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("assetCode", request)%>
</font>
<br>

<!-- Asset Name -->
<span><b>Asset Name</b><span style="color: red;">*</span></span><br>
<input type="text" name="assetName" class="form-control"
	placeholder="Enter Asset Name"
	value="<%=DataUtility.getStringData(dto.getAssetName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("assetName", request)%>
</font>
<br>

<!-- Assigned To -->
<span><b>Assigned To</b><span style="color: red;">*</span></span><br>
<input type="text" name="assignedTo" class="form-control"
	placeholder="Enter Assigned To"
	value="<%=DataUtility.getStringData(dto.getAssignedTo())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("assignedTo", request)%>
</font>
<br>

<!-- Issue Date -->
<span><b>Issue Date</b><span style="color: red;">*</span></span><br>
<input type="text" id="datepicker2" name="issueDate"
	class="form-control"
	value="<%=DataUtility.getDateString(dto.getIssueDate())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("issueDate", request)%>
</font>
<br>

<!-- Asset Status -->
<span><b>Asset Status</b><span style="color: red;">*</span></span><br>

<%
HashMap<String, String> statusMap = new LinkedHashMap<String, String>();
    statusMap.put("Active", "Active");
    statusMap.put("Inactive", "Inactive");
%>

<%=HTMLUtility.getList("assetStatus",
        DataUtility.getStringData(dto.getAssetStatus()),
        statusMap)%>

<font color="red">
<%=ServletUtility.getErrorMessage("assetStatus", request)%>
</font>
<br><br>


<%
	if (id > 0) {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success btn-md"
	value="<%=AssetCtl.OP_UPDATE%>">
<input type="submit" name="operation"
	class="btn btn-warning btn-md"
	value="<%=AssetCtl.OP_CANCEL%>">
</div>
<%
	} else {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success btn-md"
	value="<%=AssetCtl.OP_SAVE%>">
<input type="submit" name="operation"
	class="btn btn-warning btn-md"
	value="<%=AssetCtl.OP_RESET%>">
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