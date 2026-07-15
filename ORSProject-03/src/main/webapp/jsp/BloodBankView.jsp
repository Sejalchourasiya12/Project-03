<%@page import="in.co.rays.project_3.controller.BloodBankCtl"%>
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
<title>Blood Bank View</title>
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
<form action="<%=ORSView.BLOOD_BANK_CTL%>" method="post">
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BloodBankDTO" scope="request"></jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    long id = DataUtility.getLong(request.getParameter("id"));
                    if (dto.getDonorName() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update Blood Bank</h3>
                <% } else { %>
                <h3 class="text-center default-text text-primary">Add Blood Bank</h3>
                <% } %>

                <!-- Success Message -->
                <% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
                <div class="alert alert-success alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <%=ServletUtility.getSuccessMessage(request)%>
                </div>
                <% } %>

                <!-- Error Message -->
                <% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <%=ServletUtility.getErrorMessage(request)%>
                </div>
                <% } %>

                <!-- Hidden Fields -->
                <input type="hidden" name="id" value="<%=dto.getId()%>">
                <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
                <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
                <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
                <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

                <div class="md-form">

                    <!-- Donor Name -->
                    <span class="pl-sm-5"><b>Donor Name</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-user grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="donorName"
                                placeholder="Enter Donor Name"
                                value="<%=DataUtility.getStringData(dto.getDonorName())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("donorName", request)%></font><br>

                    <!-- Blood Group Dropdown -->
                    <span class="pl-sm-5"><b>Blood Group</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-tint grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap bloodGroupMap = new LinkedHashMap();
                                bloodGroupMap.put("A+",  "A+");
                                bloodGroupMap.put("A-",  "A-");
                                bloodGroupMap.put("B+",  "B+");
                                bloodGroupMap.put("B-",  "B-");
                                bloodGroupMap.put("AB+", "AB+");
                                bloodGroupMap.put("AB-", "AB-");
                                bloodGroupMap.put("O+",  "O+");
                                bloodGroupMap.put("O-",  "O-");
                                String bloodGroupDropdown = HTMLUtility.getList(
                                    "bloodGroup",
                                    DataUtility.getStringData(dto.getBloodGroup()),
                                    bloodGroupMap
                                );
                            %>
                            <%=bloodGroupDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("bloodGroup", request)%></font><br>

                    <!-- Available Units — FIX: 0 nahi dikhega blank form par -->
                    <span class="pl-sm-5"><b>Available Units</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-flask grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="available"
                                placeholder="Enter Units Available"
                                value="<%=dto.getAvailable() > 0 ? String.valueOf(dto.getAvailable()) : ""%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("available", request)%></font><br>

                    <!-- Contact Number -->
                    <span class="pl-sm-5"><b>Contact Number</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-phone-square grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="contactNumber"
                                placeholder="Enter 10-digit Contact Number" maxlength="10"
                                value="<%=DataUtility.getStringData(dto.getContactNumber())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("contactNumber", request)%></font><br>

                    <!-- Buttons -->
                    <% if (dto.getDonorName() != null && dto.getId() > 0) { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=BloodBankCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=BloodBankCtl.OP_CANCEL%>">
                    </div>
                    <% } else { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=BloodBankCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=BloodBankCtl.OP_RESET%>">
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
