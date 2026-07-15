<%@page import="in.co.rays.project_3.controller.DonationCampCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Donation Camp View</title>
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
<form action="<%=ORSView.DONATION_CAMP_CTL%>" method="post">

<div class="row pt-3 pb-4">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DonationCampDTO"
    scope="request"></jsp:useBean>

<div class="col-md-4 mb-4"></div>
<div class="col-md-4 mb-4">
<div class="card">
<div class="card-body">

<%
    long id = DataUtility.getLong(request.getParameter("id"));
    if (dto.getId() != null) {
%>
<h3 class="text-center text-primary">Update Donation Camp</h3>
<%
    } else {
%>
<h3 class="text-center text-primary">Add Donation Camp</h3>
<%
    }
%>

<!-- Success Message — session se bhi check karo (redirect ke baad) -->
<%
    String sessionSuccess = (String) session.getAttribute("successMessage");
    if (sessionSuccess != null && !sessionSuccess.isEmpty()) {
%>
<div class="alert alert-success alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=sessionSuccess%>
</div>
<%
    session.removeAttribute("successMessage");
    }
%>

<%
    if (!ServletUtility.getSuccessMessage(request).equals("")) {
%>
<div class="alert alert-success alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<%
    }
%>

<!-- Error Message -->
<%
    if (!ServletUtility.getErrorMessage(request).equals("")) {
%>
<div class="alert alert-danger alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getErrorMessage(request)%>
</div>
<%
    }
%>

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
    value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
    value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Camp Name -->
<span><b>Camp Name</b><span style="color: red;">*</span></span><br>
<input type="text" name="campName" class="form-control"
    placeholder="Enter Camp Name"
    value="<%=DataUtility.getStringData(dto.getCampName())%>">
<font color="red"><%=ServletUtility.getErrorMessage("campName", request)%></font>
<br>

<!-- Camp Date -->
<span><b>Camp Date</b><span style="color: red;">*</span></span><br>
<input type="text" id="datepicker2" name="campDate" class="form-control"
    value="<%=DataUtility.getDateString(dto.getCampDate())%>">
<font color="red"><%=ServletUtility.getErrorMessage("campDate", request)%></font>
<br>

<!-- Organizer — Static Dropdown from preload -->
<span><b>Organizer</b><span style="color: red;">*</span></span><br>
<select name="organizer" class="form-control">
    <option value="">-- Select Organizer --</option>
<%
    LinkedHashMap<String, String> organizerMap =
        (LinkedHashMap<String, String>) request.getAttribute("organizerMap");
    if (organizerMap != null) {
        for (java.util.Map.Entry<String, String> entry : organizerMap.entrySet()) {
            String selected = entry.getKey().equals(dto.getOrganizer()) ? "selected" : "";
%>
    <option value="<%=entry.getKey()%>" <%=selected%>><%=entry.getValue()%></option>
<%
        }
    }
%>
</select>
<font color="red"><%=ServletUtility.getErrorMessage("organizer", request)%></font>
<br><br>

<%
    if (id > 0) {
%>
<div class="text-center">
<input type="submit" name="operation" class="btn btn-success btn-md"
    value="<%=DonationCampCtl.OP_UPDATE%>">
<input type="submit" name="operation" class="btn btn-warning btn-md"
    value="<%=DonationCampCtl.OP_CANCEL%>">
</div>
<%
    } else {
%>
<div class="text-center">
<input type="submit" name="operation" class="btn btn-success btn-md"
    value="<%=DonationCampCtl.OP_SAVE%>">
<input type="submit" name="operation" class="btn btn-warning btn-md"
    value="<%=DonationCampCtl.OP_RESET%>">
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