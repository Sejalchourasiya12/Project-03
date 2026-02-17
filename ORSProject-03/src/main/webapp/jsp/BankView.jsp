<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.BankCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bank View</title>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/bank.jpg');
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

<main>
<form action="<%=ORSView.BANK_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BankDTO" scope="request"/>

<div class="row pt-3">
	<div class="col-md-4"></div>

	<div class="col-md-4">
		<div class="card input-group-addon">
			<div class="card-body">

<%
	if (dto.getId() != null && dto.getId() > 0) {
%>
	<h3 class="text-center text-primary">Update Bank Account</h3>
<%
	} else {
%>
	<h3 class="text-center text-primary">Add Bank Account</h3>
<%
	}
%>

<!-- Success Message -->
<%
	if (!ServletUtility.getSuccessMessage(request).equals("")) {
%>
<div class="alert alert-success text-center">
	<%=ServletUtility.getSuccessMessage(request)%>
</div>
<%
	}
%>

<!-- Error Message -->
<%
	if (!ServletUtility.getErrorMessage(request).equals("")) {
%>
<div class="alert alert-danger text-center">
	<%=ServletUtility.getErrorMessage(request)%>
</div>
<%
	}
%>

<input type="hidden" name="id" value="<%=dto.getId()%>">

<!-- Account No -->
<b>Account No *</b>
<input type="text" class="form-control" name="accountNo"
	value="<%=DataUtility.getStringData(dto.getAccountNo())%>">
<font color="red"><%=ServletUtility.getErrorMessage("accountNo", request)%></font><br>

<!-- Account Holder -->
<b>Account Holder *</b>
<input type="text" class="form-control" name="accountHolder"
	value="<%=DataUtility.getStringData(dto.getAccountHolder())%>">
<font color="red"><%=ServletUtility.getErrorMessage("accountHolder", request)%></font><br>

<!-- Account Type -->
<b>Account Type *</b>
<select name="accountType" class="form-control">
	<option value="">--Select--</option>
	<option value="Saving" <%= "Saving".equals(dto.getAccountType())?"selected":"" %>>Saving</option>
	<option value="Current" <%= "Current".equals(dto.getAccountType())?"selected":"" %>>Current</option>
</select>
<font color="red"><%=ServletUtility.getErrorMessage("accountType", request)%></font><br>

<!-- Opening Date -->
<b>Opening Date *</b>
<input type="text" name="openingDate" id="datepicker2"
	class="form-control" readonly
	value="<%=DataUtility.getDateString(dto.getOpeningDate())%>">
<font color="red"><%=ServletUtility.getErrorMessage("openingDate", request)%></font><br>

<!-- Balance -->
<b>Balance *</b>
<input type="text" class="form-control" name="balance"
	value="<%=dto.getBalance()%>">
<font color="red"><%=ServletUtility.getErrorMessage("balance", request)%></font><br>

<!-- Account Limit -->
<b>Account Limit</b>
<input type="text" class="form-control" name="accountLimit"
	value="<%=dto.getAccountLimit()%>"><br>

<!-- Buttons -->
<div class="text-center">
<%
	if (dto.getId() != null && dto.getId() > 0) {
%>
	<input type="submit" name="operation" value="<%=BankCtl.OP_UPDATE%>"
		class="btn btn-success">
	<input type="submit" name="operation" value="<%=BankCtl.OP_CANCEL%>"
		class="btn btn-warning">
<%
	} else {
%>
	<input type="submit" name="operation" value="<%=BankCtl.OP_SAVE%>"
		class="btn btn-success">
	<input type="submit" name="operation" value="<%=BankCtl.OP_RESET%>"
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
</main>

<%@include file="FooterView.jsp"%>

</body>
</html>
