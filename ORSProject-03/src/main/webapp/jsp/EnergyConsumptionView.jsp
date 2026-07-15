<%@page import="in.co.rays.project_3.controller.EnergyConsumptionCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Energy Consumption View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
.input-group-addon { box-shadow: 9px 8px 7px #001a33; }
.hm {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    padding-top: 75px;
}
</style>
</head>
<body class="hm">
<div class="header">
    <%@include file="Header.jsp"%>
    <%@include file="calendar.jsp"%>
</div>
<div>
<main>
<form action="<%=ORSView.ENERGY_CONSUMPTION_CTL%>" method="post">
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EnergyConsumptionDTO" scope="request"></jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    if (dto.getEnergyCode() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update Energy Consumption</h3>
                <% } else { %>
                <h3 class="text-center default-text text-primary">Add Energy Consumption</h3>
                <% } %>

                <H4 align="center">
                <% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
                <div class="alert alert-success alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <%=ServletUtility.getSuccessMessage(request)%>
                </div>
                <% } %>
                </H4>

                <H4 align="center">
                <% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <%=ServletUtility.getErrorMessage(request)%>
                </div>
                <% } %>
                </H4>

                <input type="hidden" name="id" value="<%=dto.getId()%>">
                <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
                <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
                <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
                <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

                <div class="md-form">

                    <!-- Energy Code -->
                    <span class="pl-sm-5"><b>Energy Code</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-bolt grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="energyCode"
                                placeholder="e.g. EC-001"
                                value="<%=DataUtility.getStringData(dto.getEnergyCode())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("energyCode", request)%></font><br>

                    <!-- Device Name - Dropdown -->
                    <span class="pl-sm-5"><b>Device Name</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-plug grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap deviceMap = new LinkedHashMap();
                                deviceMap.put("Air Conditioner", "Air Conditioner");
                                deviceMap.put("Refrigerator",    "Refrigerator");
                                deviceMap.put("Washing Machine", "Washing Machine");
                                deviceMap.put("Television",      "Television");
                                deviceMap.put("Microwave",       "Microwave");
                                deviceMap.put("Water Heater",    "Water Heater");
                                deviceMap.put("Fan",             "Fan");
                                deviceMap.put("LED Light",       "LED Light");
                                deviceMap.put("Computer",        "Computer");
                                String deviceDropdown = HTMLUtility.getList(
                                    "deviceName",
                                    DataUtility.getStringData(dto.getDeviceName()),
                                    deviceMap
                                );
                            %>
                            <%=deviceDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("deviceName", request)%></font><br>

                    <!-- Units Consumed -->
                    <span class="pl-sm-5"><b>Units Consumed</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-tachometer-alt grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="unitsConsumed"
                                placeholder="e.g. 150 or 23.5"
                                value="<%=DataUtility.getStringData(dto.getUnitsConsumed())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("unitsConsumed", request)%></font><br>

                    <!-- Status - Dropdown -->
                    <span class="pl-sm-5"><b>Status</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-toggle-on grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap statusMap = new LinkedHashMap();
                                statusMap.put("Active",   "Active");
                                statusMap.put("Inactive", "Inactive");
                                statusMap.put("Pending",  "Pending");
                                statusMap.put("Faulty",   "Faulty");
                                String statusDropdown = HTMLUtility.getList(
                                    "status",
                                    DataUtility.getStringData(dto.getStatus()),
                                    statusMap
                                );
                            %>
                            <%=statusDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("status", request)%></font><br>

                    <!-- Buttons -->
                    <% if (dto.getEnergyCode() != null && dto.getId() > 0) { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=EnergyConsumptionCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=EnergyConsumptionCtl.OP_CANCEL%>">
                    </div>
                    <% } else { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=EnergyConsumptionCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=EnergyConsumptionCtl.OP_RESET%>">
                    </div>
                    <% } %>

                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4 mb-4"></div>
</div>
</form>
</main>
</div>
</body>
<%@include file="FooterView.jsp"%>
</html>
