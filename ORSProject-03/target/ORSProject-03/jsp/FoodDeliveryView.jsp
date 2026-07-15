<%@page import="in.co.rays.project_3.controller.FoodDeliveryCtl"%>
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
<title>Food Delivery View</title>
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
<form action="<%=ORSView.FOOD_DELIVERY_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.FoodDeliveryDTO"
	scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>
<div class="col-md-4 mb-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getId() != null && dto.getId() > 0) {
%>
<h3 class="text-center text-primary">Update Food Order</h3>
<%
	} else {
%>
<h3 class="text-center text-primary">Add Food Order</h3>
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

<!-- Order Number -->
<span><b>Order Number</b><span style="color: red;">*</span></span><br>
<input type="text" name="orderNumber" class="form-control"
	placeholder="Enter Order Number"
	value="<%=DataUtility.getStringData(dto.getOrderNumber())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("orderNumber", request)%>
</font>
<br>

<!-- Restaurant Name -->
<span><b>Restaurant Name</b><span style="color: red;">*</span></span><br>
<input type="text" name="restaurantName" class="form-control"
	placeholder="Enter Restaurant Name"
	value="<%=DataUtility.getStringData(dto.getRestaurantName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("restaurantName", request)%>
</font>
<br>

<!-- Delivery Address -->
<span><b>Delivery Address</b><span style="color: red;">*</span></span><br>
<input type="text" name="deliveryAddress" class="form-control"
	placeholder="Enter Delivery Address"
	value="<%=DataUtility.getStringData(dto.getDeliveryAddress())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("deliveryAddress", request)%>
</font>
<br>

<!-- Order Amount -->
<span><b>Order Amount</b><span style="color: red;">*</span></span><br>
<input type="text" name="orderAmount" class="form-control"
	placeholder="Enter Order Amount"
	value="<%=DataUtility.getStringData(dto.getOrderAmount())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("orderAmount", request)%>
</font>
<br>

<!-- Delivery Partner -->
<span><b>Delivery Partner</b><span style="color: red;">*</span></span><br>
<input type="text" name="deliveryPartner" class="form-control"
	placeholder="Enter Delivery Partner"
	value="<%=DataUtility.getStringData(dto.getDeliveryPartner())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("deliveryPartner", request)%>
</font>
<br>

<!-- Delivery Status -->
<!-- Delivery Status -->
<span><b>Delivery Status</b><span style="color: red;">*</span></span><br>

<%
HashMap<String, String> statusMap = new LinkedHashMap<String, String>();
    statusMap.put("Active", "Active");
    statusMap.put("Inactive", "Inactive");
%>

<%=HTMLUtility.getList("deliveryStatus",
        DataUtility.getStringData(dto.getDeliveryStatus()),
        statusMap)%>

<font color="red">
<%=ServletUtility.getErrorMessage("deliveryStatus", request)%>
</font>
<br><br>

<%
	if (id > 0) {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success btn-md"
	value="<%=FoodDeliveryCtl.OP_UPDATE%>">
<input type="submit" name="operation"
	class="btn btn-warning btn-md"
	value="<%=FoodDeliveryCtl.OP_CANCEL%>">
</div>
<%
	} else {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success btn-md"
	value="<%=FoodDeliveryCtl.OP_SAVE%>">
<input type="submit" name="operation"
	class="btn btn-warning btn-md"
	value="<%=FoodDeliveryCtl.OP_RESET%>">
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