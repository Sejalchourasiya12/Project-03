<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.dto.StaffMemberDTO"%>
<%@page import="in.co.rays.project_3.model.ModelFactory"%>
<%@page import="in.co.rays.project_3.model.StaffMemberModelInt"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.StaffMemberListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Staff Member List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
.text {
	text-align: center;
}
</style>
</head>
<%@include file="Header.jsp"%>
<body class="hm">

<div>
	<form action="<%=ORSView.STAFF_MEMBER_LIST_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.StaffMemberDTO" scope="request"></jsp:useBean>
		
		<!-- Success/Error Messages -->
		<% if(!ServletUtility.getSuccessMessage(request).equals("")) { %>
			<div class="alert alert-success alert-dismissible" style="background-color: #80ff80">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<%=ServletUtility.getSuccessMessage(request)%>
			</div>
		<% } %>
		<% if(!ServletUtility.getErrorMessage(request).equals("")) { %>
			<div class="alert alert-danger alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<%=ServletUtility.getErrorMessage(request)%>
			</div>
		<% } %>

		<!-- Table -->
		<div class="table-responsive">
			<table class="table table-bordered table-dark table-hover">
				<thead>
					<tr style="background-color: #8C8C8C;">
						<th><input type="checkbox" id="select_all" name="Select" class="text"> Select All</th>
						<th class="text">S.No</th>
						<th class="text">Full Name</th>
						<th class="text">Division</th>
						<th class="text">Joining Date</th>
						<th class="text">Previous Employee Id</th>
						<th class="text">Edit</th>
					</tr>
				</thead>
				<tbody>
				<%
					List list = ServletUtility.getList(request);
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo-1)*pageSize)+1;
					int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
					Iterator<StaffMemberDTO> it = list.iterator();
					while(it.hasNext()) {
						dto = it.next();
				%>
					<tr>
						<td align="center">
							<input type="checkbox" class="checkbox" name="ids" value="<%=dto.getId()%>">
						</td>
						<td class="text"><%=index++%></td>
						<td class="text"><%=dto.getFullName()%></td>
						<td class="text"><%=dto.getDivision()%></td>
						<td class="text"><%=DataUtility.getDateString(dto.getJoiningDate())%></td>
						<td class="text"><%=dto.getPreviousEmployeeId()%></td>
						<td class="text">
							<a href="StaffMemberCtl?id=<%=dto.getId()%>">Edit</a>
						</td>
					</tr>
				<% } %>
				</tbody>
			</table>
		</div>

		<!-- Pagination & Actions -->
		<table width="100%">
			<tr>
				<td>
					<input type="submit" name="operation" class="btn btn-warning btn-md" value="<%= StaffMemberListCtl.OP_PREVIOUS%>" <%= pageNo > 1 ? "" : "disabled" %>>
				</td>
				<td>
					<input type="submit" name="operation" class="btn btn-primary btn-md" value="<%=StaffMemberListCtl.OP_NEW%>">
				</td>
				<td>
					<input type="submit" name="operation" class="btn btn-danger btn-md" value="<%=StaffMemberListCtl.OP_DELETE%>">
				</td>
				<td align="right">
					<input type="submit" name="operation" class="btn btn-warning btn-md" value="<%=StaffMemberListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled" %>>
				</td>
			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>">
		<input type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
</div>

<%@include file="FooterView.jsp"%>
</body>
</html>
