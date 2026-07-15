<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.BugTrackingListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.BugTrackingDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bug Tracking List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.hm {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
    background-repeat: no-repeat; background-attachment: fixed;
    background-size: cover; padding-top: 85px;
}
.text { text-align: center; }
</style>
</head>

<%@include file="Header.jsp"%>

<body class="hm">
<div>
<form class="pb-5" action="<%=ORSView.BUG_TRACKING_LIST_CTL%>" method="post">

    <jsp:useBean id="dto" class="in.co.rays.project_3.dto.BugTrackingDTO" scope="request"></jsp:useBean>

    <%
        int pageNo = ServletUtility.getPageNo(request);
        int pageSize = ServletUtility.getPageSize(request);
        int index = ((pageNo - 1) * pageSize) + 1;
        int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
        List list = ServletUtility.getList(request);
        Iterator<BugTrackingDTO> it = list.iterator();
        if (list.size() != 0) {
    %>

    <center><h1 class="text-dark font-weight-bold pt-3"><u>Bug Tracking List</u></h1></center>

    <div class="row">
        <div class="col-md-4"></div>
        <% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
        <div class="col-md-4 alert alert-success alert-dismissible" style="background-color:#80ff80">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4><font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font></h4>
        </div>
        <% } %>
        <div class="col-md-4"></div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
        <div class="col-md-4 alert alert-danger alert-dismissible">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h4>
        </div>
        <% } %>
        <div class="col-md-4"></div>
    </div>

    <!-- Search Fields -->
    <div class="row">
        <div class="col-sm-1"></div>
        <div class="col-sm-2">
            <input type="text" name="projectName" placeholder="Project Name" class="form-control"
                value="<%=ServletUtility.getParameter("projectName", request)%>">
        </div>
        &emsp;
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap severitySearchMap = new java.util.LinkedHashMap();
                severitySearchMap.put("",         "-- Severity --");
                severitySearchMap.put("Critical", "Critical");
                severitySearchMap.put("High",     "High");
                severitySearchMap.put("Medium",   "Medium");
                severitySearchMap.put("Low",      "Low");
                String severitySearch = HTMLUtility.getList(
                    "severity",
                    ServletUtility.getParameter("severity", request),
                    severitySearchMap
                );
            %>
            <%=severitySearch%>
        </div>
        &emsp;
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap statusSearchMap = new java.util.LinkedHashMap();
                statusSearchMap.put("",            "-- Status --");
                statusSearchMap.put("Open",        "Open");
                statusSearchMap.put("In Progress", "In Progress");
                statusSearchMap.put("Resolved",    "Resolved");
                statusSearchMap.put("Closed",      "Closed");
                statusSearchMap.put("Reopened",    "Reopened");
                String statusSearch = HTMLUtility.getList(
                    "status",
                    ServletUtility.getParameter("status", request),
                    statusSearchMap
                );
            %>
            <%=statusSearch%>
        </div>
        &emsp;
        <div class="col-sm-3">
            <input type="submit" class="btn btn-primary btn-md" style="font-size:15px"
                name="operation" value="<%=BugTrackingListCtl.OP_SEARCH%>">
            &nbsp;
            <input type="submit" class="btn btn-dark btn-md" style="font-size:15px"
                name="operation" value="<%=BugTrackingListCtl.OP_RESET%>">
        </div>
        <div class="col-sm-1"></div>
    </div>
    <br>

    <div style="margin-bottom:20px;" class="table-responsive">
    <table class="table table-bordered table-dark table-hover">
        <thead>
            <tr style="background-color:#8C8C8C;">
                <th width="10%"><input type="checkbox" id="select_all" name="Select" class="text"> Select All</th>
                <th width="5%"  class="text">S.NO</th>
                <th width="18%" class="text">Project Name</th>
                <th width="15%" class="text">Severity</th>
                <th width="20%" class="text">Assigned To</th>
                <th width="15%" class="text">Status</th>
                <th width="8%"  class="text">Edit</th>
            </tr>
        </thead>
        <% while (it.hasNext()) { dto = it.next(); %>
        <tbody>
            <tr>
                <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=dto.getId()%>"></td>
                <td class="text"><%=index++%></td>
                <td class="text"><%=dto.getProjectName()%></td>
                <td class="text"><%=dto.getSeverity()%></td>
                <td class="text"><%=dto.getAssignedTo()%></td>
                <td class="text"><%=dto.getStatus()%></td>
                <td class="text"><a href="<%=ORSView.APP_CONTEXT%>/ctl/BugTrackingCtl?id=<%=dto.getId()%>">Edit</a></td>
            </tr>
        </tbody>
        <% } %>
    </table>
    </div>

    <table width="100%">
        <tr>
            <td><input type="submit" name="operation" class="btn btn-warning btn-md"
                style="font-size:17px" value="<%=BugTrackingListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>></td>
            <td><input type="submit" name="operation" class="btn btn-primary btn-md"
                style="font-size:17px" value="<%=BugTrackingListCtl.OP_NEW%>"></td>
            <td><input type="submit" name="operation" class="btn btn-danger btn-md"
                style="font-size:17px" value="<%=BugTrackingListCtl.OP_DELETE%>"></td>
            <td align="right"><input type="submit" name="operation" class="btn btn-warning btn-md"
                style="font-size:17px" value="<%=BugTrackingListCtl.OP_NEXT%>" <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
        </tr>
    </table>

    <% } if (list.size() == 0) { %>

    <center><h1 style="font-size:40px; color:#162390;">Bug Tracking List</h1></center><br>

    <div class="row">
        <div class="col-md-4"></div>
        <% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
        <div class="col-md-4 alert alert-danger alert-dismissible">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h4>
        </div>
        <% } %>
        <div class="col-md-4"></div>
    </div><br>

    <div style="padding-left:48%;">
        <input type="submit" name="operation" class="btn btn-primary btn-md"
            style="font-size:17px" value="<%=BugTrackingListCtl.OP_BACK%>">
    </div>

    <% } %>

    <input type="hidden" name="pageNo" value="<%=pageNo%>">
    <input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>
</div>
</body>
<%@include file="FooterView.jsp"%>
</html>
