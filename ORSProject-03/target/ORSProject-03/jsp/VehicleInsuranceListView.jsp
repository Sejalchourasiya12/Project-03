<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.VehicleInsuranceListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.VehicleInsuranceDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Vehicle Insurance List</title>
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
<form class="pb-5" action="<%=ORSView.VEHICLE_INSURANCE_LIST_CTL%>" method="post">

    <jsp:useBean id="dto" class="in.co.rays.project_3.dto.VehicleInsuranceDTO" scope="request"></jsp:useBean>

    <%
        int pageNo = ServletUtility.getPageNo(request);
        int pageSize = ServletUtility.getPageSize(request);
        int index = ((pageNo - 1) * pageSize) + 1;
        int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
        List list = ServletUtility.getList(request);
        Iterator<VehicleInsuranceDTO> it = list.iterator();
        if (list.size() != 0) {
    %>

    <center><h1 class="text-dark font-weight-bold pt-3"><u>Vehicle Insurance List</u></h1></center>

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
            <input type="text" name="ownerName" placeholder="Owner Name" class="form-control"
                value="<%=ServletUtility.getParameter("ownerName", request)%>">
        </div>
        &emsp;
        <div class="col-sm-2">
            <input type="text" name="vehicleNumber" placeholder="Vehicle Number" class="form-control"
                value="<%=ServletUtility.getParameter("vehicleNumber", request)%>">
        </div>
        &emsp;
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap vehicleTypeSearchMap = new java.util.LinkedHashMap();
                vehicleTypeSearchMap.put("",              "-- Vehicle Type --");
                vehicleTypeSearchMap.put("Two Wheeler",   "Two Wheeler");
                vehicleTypeSearchMap.put("Three Wheeler", "Three Wheeler");
                vehicleTypeSearchMap.put("Four Wheeler",  "Four Wheeler");
                vehicleTypeSearchMap.put("Truck",         "Truck");
                vehicleTypeSearchMap.put("Bus",           "Bus");
                vehicleTypeSearchMap.put("Commercial",    "Commercial");
                String vehicleTypeSearch = HTMLUtility.getList(
                    "vehicleType",
                    ServletUtility.getParameter("vehicleType", request),
                    vehicleTypeSearchMap
                );
            %>
            <%=vehicleTypeSearch%>
        </div>
        &emsp;
        <div class="col-sm-2">
            <input type="submit" class="btn btn-primary btn-md" style="font-size:15px"
                name="operation" value="<%=VehicleInsuranceListCtl.OP_SEARCH%>">
            &nbsp;
            <input type="submit" class="btn btn-dark btn-md" style="font-size:15px"
                name="operation" value="<%=VehicleInsuranceListCtl.OP_RESET%>">
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
                <th width="18%" class="text">Owner Name</th>
                <th width="15%" class="text">Vehicle Number</th>
                <th width="17%" class="text">Vehicle Type</th>
                <th width="20%" class="text">Insurance Company</th>
                <th width="8%"  class="text">Edit</th>
            </tr>
        </thead>
        <% while (it.hasNext()) { dto = it.next(); %>
        <tbody>
            <tr>
                <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=dto.getId()%>"></td>
                <td class="text"><%=index++%></td>
                <td class="text"><%=dto.getOwnerName()%></td>
                <td class="text"><%=dto.getVehicleNumber()%></td>
                <td class="text"><%=dto.getVehicleType()%></td>
                <td class="text"><%=dto.getInsuranceCompany()%></td>
                <td class="text"><a href="<%=ORSView.APP_CONTEXT%>/ctl/VehicleInsuranceCtl?id=<%=dto.getId()%>">Edit</a></td>
            </tr>
        </tbody>
        <% } %>
    </table>
    </div>

    <table width="100%">
        <tr>
            <td><input type="submit" name="operation" class="btn btn-warning btn-md"
                style="font-size:17px" value="<%=VehicleInsuranceListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>></td>
            <td><input type="submit" name="operation" class="btn btn-primary btn-md"
                style="font-size:17px" value="<%=VehicleInsuranceListCtl.OP_NEW%>"></td>
            <td><input type="submit" name="operation" class="btn btn-danger btn-md"
                style="font-size:17px" value="<%=VehicleInsuranceListCtl.OP_DELETE%>"></td>
            <td align="right"><input type="submit" name="operation" class="btn btn-warning btn-md"
                style="font-size:17px" value="<%=VehicleInsuranceListCtl.OP_NEXT%>" <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
        </tr>
    </table>

    <% } if (list.size() == 0) { %>

    <center><h1 style="font-size:40px; color:#162390;">Vehicle Insurance List</h1></center><br>

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
            style="font-size:17px" value="<%=VehicleInsuranceListCtl.OP_BACK%>">
    </div>

    <% } %>

    <input type="hidden" name="pageNo" value="<%=pageNo%>">
    <input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>
</div>
</body>
<%@include file="FooterView.jsp"%>
</html>
