<%@page import="in.co.rays.project_3.controller.BookIssueCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Issue</title>
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
<form action="<%=ORSView.BOOK_ISSUE_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BookIssueDTO"
    scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">
<div class="card">
<div class="card-body">

<%
long id = DataUtility.getLong(request.getParameter("id"));
if (id > 0) {
%>
<h3 class="text-center text-primary">Update Book Issue</h3>
<%
} else {
%>
<h3 class="text-center text-primary">Add Book Issue</h3>
<%
}
%>

<!-- Success Message -->
<h4 align="center">
<%if (!ServletUtility.getSuccessMessage(request).equals("")) {%>
<div class="alert alert-success alert-dismissible">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <%=ServletUtility.getSuccessMessage(request)%>
</div>
<%}%>
</h4>

<!-- Error Message -->
<h4 align="center">
<%if (!ServletUtility.getErrorMessage(request).equals("")) {%>
<div class="alert alert-danger alert-dismissible">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <%=ServletUtility.getErrorMessage(request)%>
</div>
<%}%>
</h4>

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
    value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
    value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Issue Code -->
<span><b>Issue Code</b><span style="color:red">*</span></span><br>
<input type="text"
    name="issueCode"
    maxlength="6"
    class="form-control"
    placeholder="e.g. ISS001"
    value="<%=DataUtility.getStringData(dto.getIssueCode())%>">
<font color="red"><%=ServletUtility.getErrorMessage("issueCode", request)%></font>
<br>

<!-- Book Name -->
<span><b>Book Name</b><span style="color:red">*</span></span><br>
<input type="text"
    name="bookName"
    class="form-control"
    placeholder="Enter Book Name"
    value="<%=DataUtility.getStringData(dto.getBookName())%>">
<font color="red"><%=ServletUtility.getErrorMessage("bookName", request)%></font>
<br>

<!-- Issue Date -->
<span><b>Issue Date</b><span style="color:red">*</span></span><br>
<input type="text"
    id="datepicker1"
    name="issueDate"
    class="form-control"
    placeholder="DD/MM/YYYY"
    value="<%=DataUtility.getDateString(dto.getIssueDate())%>">
<font color="red"><%=ServletUtility.getErrorMessage("issueDate", request)%></font>
<br>

<!-- Return Date -->
<span><b>Return Date</b><span style="color:red">*</span></span><br>
<input type="text"
    id="datepicker2"
    name="returnDate"
    class="form-control"
    placeholder="DD/MM/YYYY"
    value="<%=DataUtility.getDateString(dto.getReturnDate())%>">
<font color="red"><%=ServletUtility.getErrorMessage("returnDate", request)%></font>
<br>

<!-- Issue Status -->
<span><b>Issue Status</b><span style="color:red">*</span></span><br>
<%
HashMap map = new LinkedHashMap();
map.put("Issued",    "Issued");
map.put("Returned",  "Returned");
map.put("Overdue",   "Overdue");
map.put("Lost",      "Lost");
String htmlList = HTMLUtility.getList("issueStatus", dto.getIssueStatus(), map);
%>
<%=htmlList%>
<font color="red"><%=ServletUtility.getErrorMessage("issueStatus", request)%></font>
<br><br>

<%if (id > 0) {%>
<div class="text-center">
    <input type="submit" name="operation" class="btn btn-success btn-md"
        value="<%=BookIssueCtl.OP_UPDATE%>">
    <input type="submit" name="operation" class="btn btn-warning btn-md"
        value="<%=BookIssueCtl.OP_CANCEL%>">
</div>
<%} else {%>
<div class="text-center">
    <input type="submit" name="operation" class="btn btn-success btn-md"
        value="<%=BookIssueCtl.OP_SAVE%>">
    <input type="submit" name="operation" class="btn btn-warning btn-md"
        value="<%=BookIssueCtl.OP_RESET%>">
</div>
<%}%>

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
