<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.CandidateListCtl"%>
<%@page import="in.co.rays.project_3.dto.CandidateDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Candidate List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p1 {
	padding: 8px;
}
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
</style>
</head>

<body class="p4">

<div>
	<%@include file="Header.jsp"%>
</div>

<div>
<form action="<%=ORSView.CANDIDATE_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.CandidateDTO"
	scope="request"></jsp:useBean>

<%
	List list1 = (List) request.getAttribute("candidateList");

	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

	List list = ServletUtility.getList(request);
	Iterator<CandidateDTO> it = list.iterator();

	if (list.size() != 0) {
%>

<center>
	<h1 class="text-light font-weight-bold pt-3">
		<font color="black"><u>Candidate List</u></font>
	</h1>
</center>

<div class="row">
	<div class="col-md-4"></div>

	<%
		if (!ServletUtility.getSuccessMessage(request).equals("")) {
	%>
	<div class="col-md-4 alert alert-success alert-dismissible"
		style="background-color: #80ff80">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4>
			<font color="#008000">
				<%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</h4>
	</div>
	<% } %>

	<div class="col-md-4"></div>
</div>

<div class="row">
	<div class="col-md-4"></div>

	<%
		if (!ServletUtility.getErrorMessage(request).equals("")) {
	%>
	<div class=" col-md-4 alert alert-danger alert-dismissible">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4>
			<font color="red">
				<%=ServletUtility.getErrorMessage(request)%>
			</font>
		</h4>
	</div>
	<% } %>

	<div class="col-md-4"></div>
</div>

</br>

<!-- Search Row Same Structure -->
<div class="row">

	<div class="col-sm-2"></div>

	<div class="col-sm-3">
		<input class="form-control" type="text" name="candidateName"
			placeholder="Enter Candidate Name"
			value="<%=ServletUtility.getParameter("candidateName", request)%>">
	</div>

	<div class="col-sm-3">
		<input class="form-control" type="text" name="email"
			placeholder="Enter Email"
			value="<%=ServletUtility.getParameter("email", request)%>">
	</div>

	<div class="col-sm-2">
		<input type="submit" class="btn btn-primary btn-md"
			name="operation"
			value="<%=CandidateListCtl.OP_SEARCH%>">
		&emsp;
		<input type="submit" class="btn btn-dark btn-md"
			name="operation"
			value="<%=CandidateListCtl.OP_RESET%>">
	</div>

	<div class="col-sm-2"></div>

</div>

</br>

<div class="table-responsive">
<table class="table table-dark table-bordered table-hover">

<thead>
<tr style="background-color: #8C8C8C;">
	<th width="10%">
		<input type="checkbox" id="select_all"
			name="Select" class="text"> Select All
	</th>
	<th>S.NO</th>
	<th>Candidate Code</th>
	<th>Candidate Name</th>
	<th>Email</th>
	<th>Skill Set</th>
	<th>Edit</th>
</tr>
</thead>

<tbody>
<%
	while (it.hasNext()) {
		dto = it.next();
%>
<tr>
	<td align="center">
		<input type="checkbox" class="checkbox"
			name="ids" value="<%=dto.getId()%>">
	</td>
	<td align="center"><%=index++%></td>
	<td align="center"><%=dto.getCandidateCode()%></td>
	<td align="center"><%=dto.getCandidateName()%></td>
	<td align="center"><%=dto.getEmail()%></td>
	<td align="center"><%=dto.getSkillSet()%></td>
	<td align="center">
		<a href="CandidateCtl?id=<%=dto.getId()%>">Edit</a>
	</td>
</tr>
<% } %>
</tbody>

</table>
</div>

<table width="100%">
<tr>
<td>
	<input type="submit" name="operation"
		class="btn btn-secondary btn-md"
		value="<%=CandidateListCtl.OP_PREVIOUS%>"
		<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td>
	<input type="submit" name="operation"
		class="btn btn-primary btn-md"
		value="<%=CandidateListCtl.OP_NEW%>">
</td>

<td>
	<input type="submit" name="operation"
		class="btn btn-danger btn-md"
		value="<%=CandidateListCtl.OP_DELETE%>">
</td>

<td align="right">
	<input type="submit" name="operation"
		class="btn btn-secondary btn-md"
		value="<%=CandidateListCtl.OP_NEXT%>"
		<%=(nextPageSize != 0) ? "" : "disabled"%>>
</td>
</tr>
</table>

</br>

<% } %>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>
</div>

</br>
</br>

</body>

<%@include file="FooterView.jsp"%>
</html>