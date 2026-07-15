<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.AnnouncementListCtl"%>
<%@page import="in.co.rays.project_3.dto.AnnouncementDTO"%>
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
<title>Announcement List View</title>

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

<form action="<%=ORSView.ANNOUNCEMENT_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AnnouncementDTO"
	scope="request"></jsp:useBean>

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
List list = ServletUtility.getList(request);
Iterator<AnnouncementDTO> it = list.iterator();
%>

<center>
<h1 class="text-light font-weight-bold pt-3">
<font color="black"><u>Announcement List</u></font>
</h1>
</center>

<br>

<div class="row">
<div class="col-sm-3">
<input type="text" class="form-control" name="title"
placeholder="Enter Title"
value="<%=ServletUtility.getParameter("title", request)%>">
</div>

<div class="col-sm-3">
<input type="text" class="form-control" name="announcementCode"
placeholder="Enter Code"
value="<%=ServletUtility.getParameter("announcementCode", request)%>">
</div>

<div class="col-sm-3">
<input type="submit" class="btn btn-primary btn-md"
name="operation"
value="<%=AnnouncementListCtl.OP_SEARCH%>">

<input type="submit" class="btn btn-dark btn-md"
name="operation"
value="<%=AnnouncementListCtl.OP_RESET%>">
</div>
</div>

<br>

<div class="table-responsive">
<table class="table table-dark table-bordered table-hover">
<thead>
<tr style="background-color: #8C8C8C;">
<th width="10%"><input type="checkbox" id="select_all"> Select All</th>
<th>S.NO</th>
<th>Code</th>
<th>Title</th>
<th>Description</th>
<th>Publish Date</th>
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
<td align="center"><%=dto.getAnnouncementCode()%></td>
<td align="center"><%=dto.getTitle()%></td>
<td align="center"><%=dto.getDescription()%></td>
<td align="center">
<%=DataUtility.getDateString(dto.getPublishDate())%>
</td>
<td align="center">
<a href="AnnouncementCtl?id=<%=dto.getId()%>">Edit</a>
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
value="<%=AnnouncementListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td>
<input type="submit" name="operation"
class="btn btn-primary btn-md"
value="<%=AnnouncementListCtl.OP_NEW%>">
</td>

<td>
<input type="submit" name="operation"
class="btn btn-danger btn-md"
value="<%=AnnouncementListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation"
class="btn btn-secondary btn-md"
value="<%=AnnouncementListCtl.OP_NEXT%>"
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