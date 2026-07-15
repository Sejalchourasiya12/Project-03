<%@page import="in.co.rays.project_3.controller.EnrollmentCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enrollment</title>
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

<form action="<%=ORSView.ENROLLMENT_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EnrollmentDTO"
scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">

<div class="card">

<div class="card-body">

<%

long id = DataUtility.getLong(request.getParameter("id"));

if (id > 0) {

%>

<h3 class="text-center text-primary">Update Enrollment</h3>

<%

} else {

%>

<h3 class="text-center text-primary">Add Enrollment</h3>

<%

}

%>

<!-- Success Message -->

<h4 align="center">

<%

if (!ServletUtility.getSuccessMessage(request).equals("")) {

%>

<div class="alert alert-success alert-dismissible">

<button type="button" class="close" data-dismiss="alert">×</button>

<%=ServletUtility.getSuccessMessage(request)%>

</div>

<%

}

%>

</h4>

<!-- Error Message -->

<h4 align="center">

<%

if (!ServletUtility.getErrorMessage(request).equals("")) {

%>

<div class="alert alert-danger alert-dismissible">

<button type="button" class="close" data-dismiss="alert">×</button>

<%=ServletUtility.getErrorMessage(request)%>

</div>

<%

}

%>

</h4>

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Enrollment Code -->

<span><b>Enrollment Code</b><span style="color:red">*</span></span><br>

<input type="text"
name="enrollmentCode"
maxlength="6"
pattern="[A-Z0-9]{1,6}"
class="form-control"
placeholder="Enter Enrollment Code"
value="<%=DataUtility.getStringData(dto.getEnrollmentCode())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("enrollmentCode", request)%>

</font>

<br>

<!-- Student Name -->

<span><b>Student Name</b><span style="color:red">*</span></span><br>

<input type="text"
name="studentName"
class="form-control"
placeholder="Enter Student Name"
value="<%=DataUtility.getStringData(dto.getStudentName())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("studentName", request)%>

</font>

<br>

<!-- Enrollment Status -->

<span><b>Enrollment Status</b><span style="color:red">*</span></span><br>

<%

HashMap map = new LinkedHashMap();

map.put("Pending","Pending");
map.put("Active","Active");
map.put("Completed","Completed");
map.put("Cancelled","Cancelled");

String htmlList = HTMLUtility.getList("enrollmentStatus",
dto.getEnrollmentStatus(), map);

%>

<%=htmlList%>

<font color="red">

<%=ServletUtility.getErrorMessage("enrollmentStatus", request)%>

</font>

<br>

<!-- Enrollment Date -->

<span><b>Enrollment Date</b><span style="color:red">*</span></span><br>

<input type="text"
id="datepicker2"
name="enrollmentDate"
class="form-control"
value="<%=DataUtility.getDateString(dto.getEnrollmentDate())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("enrollmentDate", request)%>

</font>

<br><br>

<%

if (id > 0) {

%>

<div class="text-center">

<input type="submit"
name="operation"
class="btn btn-success btn-md"
value="<%=EnrollmentCtl.OP_UPDATE%>">

<input type="submit"
name="operation"
class="btn btn-warning btn-md"
value="<%=EnrollmentCtl.OP_CANCEL%>">

</div>

<%

} else {

%>

<div class="text-center">

<input type="submit"
name="operation"
class="btn btn-success btn-md"
value="<%=EnrollmentCtl.OP_SAVE%>">

<input type="submit"
name="operation"
class="btn btn-warning btn-md"
value="<%=EnrollmentCtl.OP_RESET%>">

</div>

<%

}

%>

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