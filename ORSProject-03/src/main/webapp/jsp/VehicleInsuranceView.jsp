<%@page import="in.co.rays.project_3.controller.VehicleInsuranceCtl"%>
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
<title>Vehicle Insurance View</title>
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
<form action="<%=ORSView.VEHICLE_INSURANCE_CTL%>" method="post">
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.VehicleInsuranceDTO" scope="request"></jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    if (dto.getOwnerName() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update Vehicle Insurance</h3>
                <% } else { %>
                <h3 class="text-center default-text text-primary">Add Vehicle Insurance</h3>
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

                    <!-- Owner Name -->
                    <span class="pl-sm-5"><b>Owner Name</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-user grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="ownerName"
                                placeholder="e.g. Rahul Sharma"
                                value="<%=DataUtility.getStringData(dto.getOwnerName())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("ownerName", request)%></font><br>

                    <!-- Vehicle Number -->
                    <span class="pl-sm-5"><b>Vehicle Number</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-id-card grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="vehicleNumber"
                                placeholder="e.g. MP09AB1234"
                                value="<%=DataUtility.getStringData(dto.getVehicleNumber())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("vehicleNumber", request)%></font><br>

                    <!-- Vehicle Type - Dropdown -->
                    <span class="pl-sm-5"><b>Vehicle Type</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-car grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap vehicleTypeMap = new LinkedHashMap();
                                vehicleTypeMap.put("Two Wheeler",    "Two Wheeler");
                                vehicleTypeMap.put("Three Wheeler",  "Three Wheeler");
                                vehicleTypeMap.put("Four Wheeler",   "Four Wheeler");
                                vehicleTypeMap.put("Truck",          "Truck");
                                vehicleTypeMap.put("Bus",            "Bus");
                                vehicleTypeMap.put("Commercial",     "Commercial");
                                String vehicleTypeDropdown = HTMLUtility.getList(
                                    "vehicleType",
                                    DataUtility.getStringData(dto.getVehicleType()),
                                    vehicleTypeMap
                                );
                            %>
                            <%=vehicleTypeDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("vehicleType", request)%></font><br>

                    <!-- Insurance Company - Dropdown -->
                    <span class="pl-sm-5"><b>Insurance Company</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-building grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap companyMap = new LinkedHashMap();
                                companyMap.put("LIC",              "LIC");
                                companyMap.put("HDFC Ergo",        "HDFC Ergo");
                                companyMap.put("Bajaj Allianz",    "Bajaj Allianz");
                                companyMap.put("ICICI Lombard",    "ICICI Lombard");
                                companyMap.put("New India",        "New India Assurance");
                                companyMap.put("United India",     "United India Insurance");
                                companyMap.put("Tata AIG",         "Tata AIG");
                                companyMap.put("Reliance General", "Reliance General");
                                String companyDropdown = HTMLUtility.getList(
                                    "insuranceCompany",
                                    DataUtility.getStringData(dto.getInsuranceCompany()),
                                    companyMap
                                );
                            %>
                            <%=companyDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("insuranceCompany", request)%></font><br>

                    <!-- Buttons -->
                    <% if (dto.getOwnerName() != null && dto.getId() > 0) { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=VehicleInsuranceCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=VehicleInsuranceCtl.OP_CANCEL%>">
                    </div>
                    <% } else { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=VehicleInsuranceCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=VehicleInsuranceCtl.OP_RESET%>">
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
