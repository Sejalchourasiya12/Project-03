<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.DonationCampListCtl"%>
<%@page import="in.co.rays.project_3.dto.DonationCampDTO"%>
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
<title>Donation Camp List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
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

<%@include file="Header.jsp"%>

<form action="<%=ORSView.DONATION_CAMP_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DonationCampDTO"
	scope="request"></jsp:useBean>

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
List list = ServletUtility.getList(request);
Iterator<DonationCampDTO> it = list.iterator();
%>

<center>
<h1 class="text-light font-weight-bold pt-3">
<font color="black"><u>Donation Camp List</u></font>
</h1>
</center>

<br>

<div class="row">
<div class="col-sm-3">
<input type="text" class="form-control" name="campName"
placeholder="Enter Camp Name"
value="<%=ServletUtility.getParameter("campName", request)%>">
</div>

<div class="col-sm-3">
<input type="text" class="form-control" name="organizer"
placeholder="Enter Organizer"
value="<%=ServletUtility.getParameter("organizer", request)%>">
</div>

<div class="col-sm-3">
<input type="submit" class="btn btn-primary btn-md"
name="operation"
value="<%=DonationCampListCtl.OP_SEARCH%>">

<input type="submit" class="btn btn-dark btn-md"
name="operation"
value="<%=DonationCampListCtl.OP_RESET%>">
</div>
</div>

<br>

<div class="table-responsive">
<table class="table table-dark table-bordered table-hover">
<thead>
<tr style="background-color: #8C8C8C;">
<th width="10%"><input type="checkbox" id="select_all"> Select All</th>
<th>S.NO</th>
<th>Camp Name</th>
<th>Camp Date</th>
<th>Organizer</th>
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
<td align="center"><%=dto.getCampName()%></td>
<td align="center"><%=DataUtility.getDateString(dto.getCampDate())%></td>
<td align="center"><%=dto.getOrganizer()%></td>
<td align="center">
<a href="DonationCampCtl?id=<%=dto.getId()%>">Edit</a>
</td>
</tr>
<%
}
%>
</tbody>
</table>
</div>

<table width="100%">
<tr>
<td>
<input type="submit" name="operation"
class="btn btn-secondary btn-md"
value="<%=DonationCampListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td>
<input type="submit" name="operation"
class="btn btn-primary btn-md"
value="<%=DonationCampListCtl.OP_NEW%>">
</td>

<td>
<input type="submit" name="operation"
class="btn btn-danger btn-md"
value="<%=DonationCampListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation"
class="btn btn-secondary btn-md"
value="<%=DonationCampListCtl.OP_NEXT%>"
<%=(nextPageSize != 0) ? "" : "disabled"%>>
</td>
</tr>
</table>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>