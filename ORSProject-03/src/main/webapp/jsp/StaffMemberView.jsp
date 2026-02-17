<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.dto.StaffMemberDTO"%>
<%@page import="in.co.rays.project_3.controller.StaffMemberCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Staff Member</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed; 
	background-size: cover;
	padding-top: 75px;
}
.input-group-addon{
	box-shadow: 9px 8px 7px #001a33;
}
</style>
</head>
<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp" %>
	</div>

	<main>
		<form action="<%=ORSView.STAFF_MEMBER_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.StaffMemberDTO" scope="request"></jsp:useBean>

			<div class="row pt-3">
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));
								if (dto.getFullName() != null && dto.getId() > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update Staff Member</h3>
							<% } else { %>
							<h3 class="text-center default-text text-primary">Add Staff Member</h3>
							<% } %>

							<H4 align="center">
								<%
									if (!ServletUtility.getSuccessMessage(request).equals("")) {
								%>
								<div class="alert alert-success alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<%=ServletUtility.getSuccessMessage(request)%>
								</div>
								<% } %>
							</H4>

							<H4 align="center">
								<%
									if (!ServletUtility.getErrorMessage(request).equals("")) {
								%>
								<div class="alert alert-danger alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<%=ServletUtility.getErrorMessage(request)%>
								</div>
								<% } %>
							</H4>

							<!-- Hidden Fields -->
							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
							<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
							<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- Full Name -->
							<div class="md-form">
								<span class="pl-sm-5"><b>Full Name</b> <span style="color:red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text"><i class="fa fa-user grey-text"></i></div>
										</div>
										<input type="text" class="form-control" name="fullName" placeholder="Full Name" value="<%=DataUtility.getStringData(dto.getFullName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("fullName", request)%></font></br>
							</div>

							<!-- Division -->
							<div class="md-form">
								<span class="pl-sm-5"><b>Division</b> <span style="color:red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text"><i class="fa fa-building grey-text"></i></div>
										</div>
										<input type="text" class="form-control" name="division" placeholder="Division" value="<%=DataUtility.getStringData(dto.getDivision())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("division", request)%></font></br>
							</div>

							<!-- Joining Date -->
							<div class="md-form">
								<span class="pl-sm-5"><b>Joining Date</b> <span style="color:red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text"><i class="fa fa-calendar grey-text"></i></div>
										</div>
										<input type="text" id="datepicker" class="form-control" name="joiningDate" placeholder="Joining Date" readonly="readonly" value="<%=DataUtility.getDateString(dto.getJoiningDate())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("joiningDate", request)%></font></br>
							</div>

							<!-- Previous Employee ID -->
							<div class="md-form">
								<span class="pl-sm-5"><b>Previous Employee ID</b></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text"><i class="fa fa-id-card grey-text"></i></div>
										</div>
										<input type="text" class="form-control" name="previousEmployeeId" placeholder="Previous Employee ID" value="<%=DataUtility.getStringData(dto.getPreviousEmployeeId())%>">
									</div>
								</div>
							</div>

							<!-- Submit Buttons -->
							<div class="text-center">
								<%
									if (dto.getFullName()!=null && dto.getId() > 0) {
								%>
								<input type="submit" name="operation" class="btn btn-success btn-md" value="<%=StaffMemberCtl.OP_UPDATE%>">
								<input type="submit" name="operation" class="btn btn-warning btn-md" value="<%=StaffMemberCtl.OP_CANCEL%>">
								<% } else { %>
								<input type="submit" name="operation" class="btn btn-success btn-md" value="<%=StaffMemberCtl.OP_SAVE%>">
								<input type="submit" name="operation" class="btn btn-warning btn-md" value="<%=StaffMemberCtl.OP_RESET%>">
								<% } %>
							</div>

						</div>
					</div>
				</div>
				<div class="col-md-4 mb-4"></div>
			</div>
		</form>
	</main>
</body>
<%@include file="FooterView.jsp"%>
</html>
