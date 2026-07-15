<%@page import="in.co.rays.project_3.controller.DeliveryTrackingCtl"%>
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
<title>Delivery Tracking</title>
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

<form action="<%=ORSView.DELIVERY_TRACKING_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DeliveryTrackingDTO"
scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">

<div class="card">

<div class="card-body">

<%

long id = DataUtility.getLong(request.getParameter("id"));

if (id > 0) {

%>

<h3 class="text-center text-primary">Update Delivery Tracking</h3>

<%

} else {

%>

<h3 class="text-center text-primary">Add Delivery Tracking</h3>

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

<!-- Order Number -->

<span><b>Order Number</b><span style="color:red">*</span></span><br>

<input type="text"
name="orderNumber"
maxlength="5"
pattern="[0-9]{1,5}"
class="form-control"
placeholder="Enter Order Number"
value="<%=DataUtility.getStringData(dto.getOrderNumber())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("orderNumber", request)%>

</font>

<br>

<!-- Customer Name -->

<span><b>Customer Name</b><span style="color:red">*</span></span><br>

<input type="text"
name="customerName"
class="form-control"
placeholder="Enter Customer Name"
value="<%=DataUtility.getStringData(dto.getCustomerName())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("customerName", request)%>

</font>

<br>

<!-- Delivery Status -->

<span><b>Delivery Status</b><span style="color:red">*</span></span><br>

<%

HashMap map = new LinkedHashMap();

map.put("Pending","Pending");
map.put("Shipped","Shipped");
map.put("Out For Delivery","Out For Delivery");
map.put("Delivered","Delivered");

String htmlList = HTMLUtility.getList("deliveryStatus",
dto.getDeliveryStatus(), map);

%>

<%=htmlList%>

<font color="red">

<%=ServletUtility.getErrorMessage("deliveryStatus", request)%>

</font>

<br>

<!-- Delivery Date -->

<span><b>Delivery Date</b><span style="color:red">*</span></span><br>

<input type="text"
id="datepicker2"
name="deliveryDate"
class="form-control"
value="<%=DataUtility.getDateString(dto.getDeliveryDate())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("deliveryDate", request)%>

</font>

<br><br>

<%

if (id > 0) {

%>

<div class="text-center">

<input type="submit"
name="operation"
class="btn btn-success btn-md"
value="<%=DeliveryTrackingCtl.OP_UPDATE%>">

<input type="submit"
name="operation"
class="btn btn-warning btn-md"
value="<%=DeliveryTrackingCtl.OP_CANCEL%>">

</div>

<%

} else {

%>

<div class="text-center">

<input type="submit"
name="operation"
class="btn btn-success btn-md"
value="<%=DeliveryTrackingCtl.OP_SAVE%>">

<input type="submit"
name="operation"
class="btn btn-warning btn-md"
value="<%=DeliveryTrackingCtl.OP_RESET%>">

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
