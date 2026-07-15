<%@page import="in.co.rays.project_3.controller.PromotionCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Promotion View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style>

.p4{

background-image:url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');

background-repeat:no-repeat;

background-attachment:fixed;

background-size:cover;

padding-top:75px;

}

</style>

</head>

<body class="p4">

<div class="header">

<%@include file="Header.jsp"%>

<%@include file="calendar.jsp"%>

</div>

<main>

<form action="<%=ORSView.PROMOTION_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PromotionDTO"
scope="request"></jsp:useBean>

<div class="row pt-3 pb-4">

<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">

<div class="card">

<div class="card-body">

<%

long id = DataUtility.getLong(request.getParameter("id"));

if(id>0){

%>

<h3 class="text-center text-primary">Update Promotion</h3>

<%

}else{

%>

<h3 class="text-center text-primary">Add Promotion</h3>

<%

}

%>

<!-- Success Message -->

<h4 align="center">

<%

if(!ServletUtility.getSuccessMessage(request).equals("")){

%>

<div class="alert alert-success alert-dismissible">

<button type="button" class="close" data-dismiss="alert">&times;</button>

<%=ServletUtility.getSuccessMessage(request)%>

</div>

<% } %>

</h4>


<!-- Error Message -->

<h4 align="center">

<%

if(!ServletUtility.getErrorMessage(request).equals("")){

%>

<div class="alert alert-danger alert-dismissible">

<button type="button" class="close" data-dismiss="alert">&times;</button>

<%=ServletUtility.getErrorMessage(request)%>

</div>

<% } %>

</h4>


<input type="hidden" name="id" value="<%=dto.getId()%>">

<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">

<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">

<input type="hidden" name="createdDatetime"
value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

<input type="hidden" name="modifiedDatetime"
value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">


<!-- Promotion Code -->

<span><b>Promotion Code</b><span style="color:red">*</span></span><br>

<input type="text" name="promotionCode" class="form-control"
    placeholder="Enter Promotion Code"
    maxlength="4"
    oninput="this.value=this.value.replace(/[^0-9]/g,'')"
    value="<%=DataUtility.getStringData(dto.getPromotionCode())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("promotionCode",request)%>
</font>

<br>


<!-- Promotion Title -->

<span><b>Promotion Title</b><span style="color:red">*</span></span><br>

<input type="text" name="promotionTitle" class="form-control"

placeholder="Enter Promotion Title"

value="<%=DataUtility.getStringData(dto.getPromotionTitle())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("promotionTitle",request)%>

</font>

<br>


<!-- Start Date -->

<span><b>Start Date</b><span style="color:red">*</span></span><br>

<input type="text" id="datepicker3"

name="startDate"

class="form-control"

value="<%=DataUtility.getDateString(dto.getStartDate())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("startDate",request)%>

</font>

<br>


<!-- Promotion Status -->

<span><b>Promotion Status</b><span style="color:red">*</span></span><br>

<%

HashMap<String,String> statusMap = new LinkedHashMap<String,String>();

statusMap.put("Active","Active");

statusMap.put("Inactive","Inactive");

%>

<%=HTMLUtility.getList("promotionStatus",
DataUtility.getStringData(dto.getPromotionStatus()),statusMap)%>

<font color="red">

<%=ServletUtility.getErrorMessage("promotionStatus",request)%>

</font>

<br><br>


<%

if(id>0){

%>

<div class="text-center">

<input type="submit"

name="operation"

class="btn btn-success btn-md"

value="<%=PromotionCtl.OP_UPDATE%>">


<input type="submit"

name="operation"

class="btn btn-warning btn-md"

value="<%=PromotionCtl.OP_CANCEL%>">

</div>

<%

}else{

%>

<div class="text-center">

<input type="submit"

name="operation"

class="btn btn-success btn-md"

value="<%=PromotionCtl.OP_SAVE%>">


<input type="submit"

name="operation"

class="btn btn-warning btn-md"

value="<%=PromotionCtl.OP_RESET%>">

</div>

<% } %>

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