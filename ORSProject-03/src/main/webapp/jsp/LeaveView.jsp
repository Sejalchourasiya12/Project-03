<%@page import="in.co.rays.project_3.controller.LeaveCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>

<title>Leave View</title>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {

		$("#udate1").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '1950:2030', // auto-updates years
		// up to today's date
		});
		$("#udate2").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '1950:2030', // auto-updates years
		// up to today's date
		});

	});
</script>

<style>
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

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.LeaveDTO"
		scope="request" />

	<main>

	<form action="<%=ORSView.LEAVE_CTL%>" method="post">

		<div class="row pt-3 pb-3">

			<div class="col-md-4"></div>

			<div class="col-md-4">

				<div class="card">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Leave</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Leave</h3>
						<%
							}
						%>

						<!-- Success Message -->
						<%
							if (!ServletUtility.getSuccessMessage(request).equals("")) {
						%>

						<div class="alert alert-success">
							<%=ServletUtility.getSuccessMessage(request)%>
						</div>

						<%
							}
						%>

						<!-- Error Message -->
						<%
							if (!ServletUtility.getErrorMessage(request).equals("")) {
						%>

						<div class="alert alert-danger">
							<%=ServletUtility.getErrorMessage(request)%>
						</div>

						<%
							}
						%>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Leave Code -->
						<b>Leave Code *</b> <input type="text" name="leaveCode"
							class="form-control"
							value="<%=DataUtility.getStringData(dto.getLeaveCode())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("leaveCode", request)%>
						</font> <br>

						<!-- Employee Name -->
						<b>Employee Name *</b> <input type="text" name="employeeName"
							class="form-control"
							value="<%=DataUtility.getStringData(dto.getEmployeeName())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("employeeName", request)%>
						</font> <br>

						<!-- Start Date -->
						<b>Leave Start Date *</b> <input type="text" id="udate1"
							name="leaveStartDate" class="form-control" readonly
							value="<%=DataUtility.getDateString(dto.getLeaveStartDate())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("leaveStartDate", request)%>
						</font> <br>

						<!-- End Date -->
						<b>Leave End Date</b> <input type="text" id="udate2"
							name="leaveEndDate" class="form-control" readonly
							value="<%=DataUtility.getDateString(dto.getLeaveEndDate())%>">

						<br>

						<!-- Status -->
						<b>Leave Status *</b> <select name="leaveStatus"
							class="form-control">

							<option value="">--Select--</option>

							<option value="Pending"
								<%="Pending".equals(dto.getLeaveStatus()) ? "selected" : ""%>>
								Pending</option>

							<option value="Approved"
								<%="Approved".equals(dto.getLeaveStatus()) ? "selected" : ""%>>
								Approved</option>

							<option value="Rejected"
								<%="Rejected".equals(dto.getLeaveStatus()) ? "selected" : ""%>>
								Rejected</option>

						</select> <font color="red"> <%=ServletUtility.getErrorMessage("leaveStatus", request)%>
						</font> <br>
						<br>

						<!-- Button Section -->

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>

						<div class="text-center">

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=LeaveCtl.OP_UPDATE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=LeaveCtl.OP_CANCEL%>">

						</div>

						<%
							} else {
						%>

						<div class="text-center">

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=LeaveCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=LeaveCtl.OP_RESET%>">

						</div>

						<%
							}
						%>

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