<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.PatientListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Patient List</title>

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

<form action="<%=ORSView.PATIENT_LIST_CTL%>" method="post">

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.PatientDTO"
	scope="request"/>

<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

	List list = ServletUtility.getList(request);
	Iterator it = list.iterator();
%>

<% if (list.size() != 0) { %>

<center>
	<h1 class="text-dark font-weight-bold pt-3">
		<u>Patient List</u>
	</h1>
</center>

<!-- SUCCESS MESSAGE -->
<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div class="alert alert-success text-center">
	<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>

<!-- ERROR MESSAGE -->
<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div class="alert alert-danger text-center">
	<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>

<!-- SEARCH -->
<div class="row">
	<div class="col-sm-3"></div>

	<div class="col-sm-3">
		<input type="text" name="name"
			placeholder="Patient Name"
			class="form-control"
			value="<%=ServletUtility.getParameter("name", request)%>">
	</div>

	<div class="col-sm-3">
		<input type="submit"
			class="btn btn-primary"
			name="operation"
			value="<%=PatientListCtl.OP_SEARCH%>">

		<input type="submit"
			class="btn btn-dark"
			name="operation"
			value="<%=PatientListCtl.OP_RESET%>">
	</div>
</div>

<br>

<!-- TABLE -->
<div class="table-responsive">
<table class="table table-bordered table-dark table-hover">

<thead>
<tr style="background-color:#8C8C8C;">
	<th width="10%"><input type="checkbox" id="select_all"> Select</th>
	<th width="5%" class="text">S.No</th>
	<th width="20%" class="text">Name</th>
	<th width="15%" class="text">DOB</th>
	<th width="15%" class="text">Mobile No</th>
	<th width="20%" class="text">Disease</th>
	<th width="10%" class="text">Edit</th>
</tr>
</thead>

<tbody>
<%
	while (it.hasNext()) {
		in.co.rays.project_3.dto.PatientDTO p =
			(in.co.rays.project_3.dto.PatientDTO) it.next();
%>

<tr>
	<td align="center">
		<input type="checkbox" class="checkbox"
			name="ids"
			value="<%=p.getId()%>">
	</td>

	<td class="text"><%=index++%></td>
	<td class="text"><%=p.getName()%></td>
	<td class="text"><%=DataUtility.getDateString(p.getDob())%></td>
	<td class="text"><%=p.getMobileNo()%></td>
	<td class="text"><%=p.getDecease()%></td>

	<td class="text">
		<a href="PatientCtl?id=<%=p.getId()%>">Edit</a>
	</td>
</tr>

<% } %>
</tbody>
</table>
</div>

<!-- BUTTONS -->
<table width="100%">
<tr>
<td>
<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=PatientListCtl.OP_PREVIOUS%>"
	<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td>
<input type="submit" name="operation"
	class="btn btn-primary"
	value="<%=PatientListCtl.OP_NEW%>">
</td>

<td>
<input type="submit" name="operation"
	class="btn btn-danger"
	value="<%=PatientListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=PatientListCtl.OP_NEXT%>"
	<%=(nextPageSize != 0) ? "" : "disabled"%>>
</td>
</tr>
</table>

<% } else { %>

<center>
	<h2>No Patient Record Found</h2>
</center>

<div class="text-center">
	<input type="submit"
		name="operation"
		class="btn btn-primary"
		value="<%=PatientListCtl.OP_BACK%>">
</div>

<% } %>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>
