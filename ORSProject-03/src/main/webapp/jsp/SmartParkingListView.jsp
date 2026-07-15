<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.SmartParkingListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.SmartParkingDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smart Parking List</title>
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
<form class="pb-5" action="<%=ORSView.SMART_PARKING_LIST_CTL%>" method="post">

    <jsp:useBean id="dto" class="in.co.rays.project_3.dto.SmartParkingDTO" scope="request"></jsp:useBean>

    <%
        int pageNo = ServletUtility.getPageNo(request);
        int pageSize = ServletUtility.getPageSize(request);
        int index = ((pageNo - 1) * pageSize) + 1;
        int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
        List list = ServletUtility.getList(request);
        Iterator<SmartParkingDTO> it = list.iterator();
        if (list.size() != 0) {
    %>

    <center><h1 class="text-dark font-weight-bold pt-3"><u>Smart Parking List</u></h1></center>

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
            <input type="text" name="parkingCode" placeholder="Parking Code" class="form-control"
                value="<%=ServletUtility.getParameter("parkingCode", request)%>">
        </div>
        &emsp;
        <div class="col-sm-2">
            <input type="text" name="vechicleNumber" placeholder="Vehicle Number" class="form-control"
                value="<%=ServletUtility.getParameter("vechicleNumber", request)%>">
        </div>
        &emsp;
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap slotSearchMap = new java.util.LinkedHashMap();
                slotSearchMap.put("",    "-- Slot Number --");
                slotSearchMap.put("A-01","A-01");
                slotSearchMap.put("A-02","A-02");
                slotSearchMap.put("A-03","A-03");
                slotSearchMap.put("B-01","B-01");
                slotSearchMap.put("B-02","B-02");
                slotSearchMap.put("B-03","B-03");
                slotSearchMap.put("C-01","C-01");
                slotSearchMap.put("C-02","C-02");
                slotSearchMap.put("C-03","C-03");
                slotSearchMap.put("D-01","D-01");
                slotSearchMap.put("D-02","D-02");
                slotSearchMap.put("D-03","D-03");
                String slotSearch = HTMLUtility.getList(
                    "slotNumber",
                    ServletUtility.getParameter("slotNumber", request),
                    slotSearchMap
                );
            %>
            <%=slotSearch%>
        </div>
        &emsp;
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap statusSearchMap = new java.util.LinkedHashMap();
                statusSearchMap.put("",            "-- Status --");
                statusSearchMap.put("Available",   "Available");
                statusSearchMap.put("Occupied",    "Occupied");
                statusSearchMap.put("Reserved",    "Reserved");
                statusSearchMap.put("Maintenance", "Maintenance");
                String statusSearch = HTMLUtility.getList(
                    "status",
                    ServletUtility.getParameter("status", request),
                    statusSearchMap
                );
            %>
            <%=statusSearch%>
        </div>
        &emsp;
        <div class="col-sm-2">
            <input type="submit" class="btn btn-primary btn-md" style="font-size:15px"
                name="operation" value="<%=SmartParkingListCtl.OP_SEARCH%>">
            &nbsp;
            <input type="submit" class="btn btn-dark btn-md" style="font-size:15px"
                name="operation" value="<%=SmartParkingListCtl.OP_RESET%>">
        </div>
    </div>
    <br>

    <div style="margin-bottom:20px;" class="table-responsive">
    <table class="table table-bordered table-dark table-hover">
        <thead>
            <tr style="background-color:#8C8C8C;">
                <th width="10%"><input type="checkbox" id="select_all" name="Select" class="text"> Select All</th>
                <th width="5%"  class="text">S.NO</th>
                <th width="15%" class="text">Parking Code</th>
                <th width="18%" class="text">Vehicle Number</th>
                <th width="15%" class="text">Slot Number</th>
                <th width="15%" class="text">Status</th>
                <th width="8%"  class="text">Edit</th>
            </tr>
        </thead>
        <% while (it.hasNext()) { dto = it.next(); %>
        <tbody>
            <tr>
                <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=dto.getId()%>"></td>
                <td class="text"><%=index++%></td>
                <td class="text"><%=dto.getParkingCode()%></td>
                <td class="text"><%=dto.getVechicleNumber()%></td>
                <td class="text"><%=dto.getSlotNumber()%></td>
                <td class="text"><%=dto.getStatus()%></td>
                <td class="text"><a href="<%=ORSView.APP_CONTEXT%>/ctl/SmartParkingCtl?id=<%=dto.getId()%>">Edit</a></td>
            </tr>
        </tbody>
        <% } %>
    </table>
    </div>

    <table width="100%">
        <tr>
            <td><input type="submit" name="operation" class="btn btn-warning btn-md"
                style="font-size:17px" value="<%=SmartParkingListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>></td>
            <td><input type="submit" name="operation" class="btn btn-primary btn-md"
                style="font-size:17px" value="<%=SmartParkingListCtl.OP_NEW%>"></td>
            <td><input type="submit" name="operation" class="btn btn-danger btn-md"
                style="font-size:17px" value="<%=SmartParkingListCtl.OP_DELETE%>"></td>
            <td align="right"><input type="submit" name="operation" class="btn btn-warning btn-md"
                style="font-size:17px" value="<%=SmartParkingListCtl.OP_NEXT%>" <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
        </tr>
    </table>

    <% } if (list.size() == 0) { %>

    <center><h1 style="font-size:40px; color:#162390;">Smart Parking List</h1></center><br>

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
            style="font-size:17px" value="<%=SmartParkingListCtl.OP_BACK%>">
    </div>

    <% } %>

    <input type="hidden" name="pageNo" value="<%=pageNo%>">
    <input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>
</div>
</body>
<%@include file="FooterView.jsp"%>
</html>
