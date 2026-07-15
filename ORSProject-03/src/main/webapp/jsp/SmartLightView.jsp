<%@page import="in.co.rays.project_3.controller.SmartLightCtl"%>
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
<title>Smart Light View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
i.css {
    border: 2px solid #8080803b;
    padding-left: 10px;
    padding-bottom: 11px;
    background-color: #ebebe0;
}
.input-group-addon {
    box-shadow: 9px 8px 7px #001a33;
}
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
<form action="<%=ORSView.SMART_LIGHT_CTL%>" method="post">

<jsp:useBean id="dto"
    class="in.co.rays.project_3.dto.SmartLightDTO"
    scope="request">
</jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    long id = DataUtility.getLong(request.getParameter("id"));
                    if (dto.getLightCode() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update Smart Light</h3>
                <%
                    } else {
                %>
                <h3 class="text-center default-text text-primary">Add Smart Light</h3>
                <%
                    }
                %>

                <!-- Success Message -->
                <H4 align="center">
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
                </H4>

                <!-- Error Message -->
                <H4 align="center">
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
                </H4>

                <input type="hidden" name="id" value="<%=dto.getId()%>">
                <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
                <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
                <input type="hidden" name="createdDatetime"
                    value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
                <input type="hidden" name="modifiedDatetime"
                    value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

                <div class="md-form">

                    <!-- Light Code -->
                    <span class="pl-sm-5"><b>Light Code</b>
                    <span style="color: red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-lightbulb grey-text" style="font-size: 1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="lightCode"
                                placeholder="e.g. LGT001"
                                value="<%=DataUtility.getStringData(dto.getLightCode())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5">
                        <%=ServletUtility.getErrorMessage("lightCode", request)%>
                    </font><br>

                    <!-- Room Name - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Room Name</b>
                    <span style="color: red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-home grey-text" style="font-size: 1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap roomMap = new LinkedHashMap();
                                roomMap.put("Living Room",  "Living Room");
                                roomMap.put("Bedroom",      "Bedroom");
                                roomMap.put("Kitchen",      "Kitchen");
                                roomMap.put("Bathroom",     "Bathroom");
                                roomMap.put("Study Room",   "Study Room");
                                roomMap.put("Garage",       "Garage");
                                roomMap.put("Hall",         "Hall");
                                String roomDropdown = HTMLUtility.getList(
                                    "roomName",
                                    DataUtility.getStringData(dto.getRoomName()),
                                    roomMap
                                );
                            %>
                            <%=roomDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5">
                        <%=ServletUtility.getErrorMessage("roomName", request)%>
                    </font><br>

                    <!-- Brightness Level - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Brightness Level</b>
                    <span style="color: red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-sun grey-text" style="font-size: 1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap brightnessMap = new LinkedHashMap();
                                brightnessMap.put("1",  "1  - Very Dim");
                                brightnessMap.put("2",  "2  - Dim");
                                brightnessMap.put("3",  "3  - Low");
                                brightnessMap.put("4",  "4  - Below Medium");
                                brightnessMap.put("5",  "5  - Medium");
                                brightnessMap.put("6",  "6  - Above Medium");
                                brightnessMap.put("7",  "7  - Bright");
                                brightnessMap.put("8",  "8  - Very Bright");
                                brightnessMap.put("9",  "9  - Extra Bright");
                                brightnessMap.put("10", "10 - Maximum");

                                String selectedBrightness = "";
                                if (dto.getBrightnessLevel() != null && dto.getBrightnessLevel() != 0) {
                                    selectedBrightness = String.valueOf(dto.getBrightnessLevel()).trim();
                                }
                                	String brightnessDropdown = HTMLUtility.getList(
                                	    "brightnessLevel",
                                	    selectedBrightness,
                                	    brightnessMap
                                	);
                            %>
                            <%=brightnessDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5">
                        <%=ServletUtility.getErrorMessage("brightnessLevel", request)%>
                    </font><br>

                    <!-- Status - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Status</b>
                    <span style="color: red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-toggle-on grey-text" style="font-size: 1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap statusMap = new LinkedHashMap();
                                statusMap.put("ON",  "ON");
                                statusMap.put("OFF", "OFF");
                                String statusDropdown = HTMLUtility.getList(
                                    "status",
                                    DataUtility.getStringData(dto.getStatus()),
                                    statusMap
                                );
                            %>
                            <%=statusDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5">
                        <%=ServletUtility.getErrorMessage("status", request)%>
                    </font><br>

                    <!-- Buttons -->
                    <%
                        if (dto.getLightCode() != null && dto.getId() > 0) {
                    %>
                    <div class="text-center">
                        <input type="submit" name="operation"
                            class="btn btn-success btn-md" style="font-size: 17px"
                            value="<%=SmartLightCtl.OP_UPDATE%>">
                        <input type="submit" name="operation"
                            class="btn btn-warning btn-md" style="font-size: 17px"
                            value="<%=SmartLightCtl.OP_CANCEL%>">
                    </div>
                    <%
                        } else {
                    %>
                    <div class="text-center">
                        <input type="submit" name="operation"
                            class="btn btn-success btn-md" style="font-size: 17px"
                            value="<%=SmartLightCtl.OP_SAVE%>">
                        <input type="submit" name="operation"
                            class="btn btn-warning btn-md" style="font-size: 17px"
                            value="<%=SmartLightCtl.OP_RESET%>">
                    </div>
                    <%
                        }
                    %>

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
