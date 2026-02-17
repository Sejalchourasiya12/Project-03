<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.controller.BankCtl"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bank Registration</title>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/bank1.jpg');
	background-size: cover;
	padding-top: 60px;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}
</style>
</head>

<body class="p4">

<%@include file="Header.jsp"%>
<%@include file="calendar.jsp"%>

<main>
<form action="<%=ORSView.BANK_CTL%>" method="post">

<div class="row pt-3">
	<div class="col-md-4"></div>

	<div class="col-md-4">
		<div class="card input-group-addon">
			<div class="card-body">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BankDTO" scope="request"/>

<h3 class="text-center text-success pb-2">Bank Registration</h3>

<!-- SUCCESS MESSAGE -->
<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div class="alert alert-success alert-dismissible text-center">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>

<!-- ERROR MESSAGE -->
<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div class="alert alert-danger alert-dismissible text-center">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>

<input type="hidden" name="id" value="<%=dto.getId()%>">

<!-- Account No -->
<b>Account No *</b>
<input type="text" class="form-control" name="accountNo"
	placeholder="Account Number"
	value="<%=DataUtility.getStringData(dto.getAccountNo())%>">
<font color="red"><%=ServletUtility.getErrorMessage("accountNo", request)%></font><br>

<!-- Account Holder -->
<b>Account Holder *</b>
<input type="text" class="form-control" name="accountHolder"
	placeholder="Account Holder Name"
	value="<%=DataUtility.getStringData(dto.getAccountHolder())%>">
<font color="red"><%=ServletUtility.getErrorMessage("accountHolder", request)%></font><br>

<!-- Account Type -->
<b>Account Type *</b>
<select name="accountType" class="form-control">
	<option value="">--Select--</option>
	<option value="Saving"
		<%="Saving".equals(dto.getAccountType())?"selected":""%>>Saving</option>
	<option value="Current"
		<%="Current".equals(dto.getAccountType())?"selected":""%>>Current</option>
</select>
<font color="red"><%=ServletUtility.getErrorMessage("accountType", request)%></font><br>

<!-- Opening Date -->
<b>Opening Date *</b>
<input type="text" id="datepicker" name="openingDate"
	class="form-control" readonly
	value="<%=DataUtility.getDateString(dto.getOpeningDate())%>">
<font color="red"><%=ServletUtility.getErrorMessage("openingDate", request)%></font><br>

<!-- Balance -->
<b>Opening Balance *</b>
<input type="text" class="form-control" name="balance"
	placeholder="Opening Balance"
	value="<%=dto.getBalance()%>">
<font color="red"><%=ServletUtility.getErrorMessage("balance", request)%></font><br>

<!-- Account Limit -->
<b>Account Limit</b>
<input type="text" class="form-control" name="accountLimit"
	placeholder="Account Limit"
	value="<%=dto.getAccountLimit()%>"><br>

<!-- BUTTONS -->
<div class="text-center">
	<input type="submit" name="operation"
		class="btn btn-success btn-md"
		value="<%=BankCtl.OP_SAVE%>">
	<input type="submit" name="operation"
		class="btn btn-secondary btn-md"
		value="<%=BankCtl.OP_RESET%>">
</div>

			</div>
		</div>
	</div>

	<div class="col-md-4"></div>
</div>

</form>
</main>

<%@include file="FooterView.jsp"%>

</body>
</html>
