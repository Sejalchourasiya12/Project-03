<%@page import="in.co.rays.project_3.controller.MobileStoreCtl"%>
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
<title>Mobile Store View</title>
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
<form action="<%=ORSView.MOBILE_STORE_CTL%>" method="post">
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.MobileStoreDTO" scope="request"></jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    if (dto.getBrandName() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update Mobile Store</h3>
                <% } else { %>
                <h3 class="text-center default-text text-primary">Add Mobile Store</h3>
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

                    <!-- Brand Name - Dropdown -->
                    <span class="pl-sm-5"><b>Brand Name</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-mobile-alt grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap brandMap = new LinkedHashMap();
                                brandMap.put("Samsung",  "Samsung");
                                brandMap.put("Apple",    "Apple");
                                brandMap.put("OnePlus",  "OnePlus");
                                brandMap.put("Xiaomi",   "Xiaomi");
                                brandMap.put("Realme",   "Realme");
                                brandMap.put("Vivo",     "Vivo");
                                brandMap.put("Oppo",     "Oppo");
                                brandMap.put("Nokia",    "Nokia");
                                brandMap.put("Motorola", "Motorola");
                                String brandDropdown = HTMLUtility.getList(
                                    "brandName",
                                    DataUtility.getStringData(dto.getBrandName()),
                                    brandMap
                                );
                            %>
                            <%=brandDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("brandName", request)%></font><br>

                    <!-- Model Name -->
                    <span class="pl-sm-5"><b>Model Name</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-tag grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="modelName"
                                placeholder="e.g. Galaxy S23"
                                value="<%=DataUtility.getStringData(dto.getModelName())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("modelName", request)%></font><br>

                    <!-- RAM Size - Dropdown -->
                    <span class="pl-sm-5"><b>RAM Size</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-memory grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap ramMap = new LinkedHashMap();
                                ramMap.put("2GB",  "2 GB");
                                ramMap.put("3GB",  "3 GB");
                                ramMap.put("4GB",  "4 GB");
                                ramMap.put("6GB",  "6 GB");
                                ramMap.put("8GB",  "8 GB");
                                ramMap.put("12GB", "12 GB");
                                ramMap.put("16GB", "16 GB");
                                String ramDropdown = HTMLUtility.getList(
                                    "ramSize",
                                    DataUtility.getStringData(dto.getRamSize()),
                                    ramMap
                                );
                            %>
                            <%=ramDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("ramSize", request)%></font><br>

                    <!-- Price -->
                    <span class="pl-sm-5"><b>Price (&#8377;)</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-rupee-sign grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="price"
                                placeholder="e.g. 25000"
                                value="<%=DataUtility.getStringData(dto.getPrice())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("price", request)%></font><br>

                    <!-- Buttons -->
                    <% if (dto.getBrandName() != null && dto.getId() > 0) { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=MobileStoreCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=MobileStoreCtl.OP_CANCEL%>">
                    </div>
                    <% } else { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=MobileStoreCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=MobileStoreCtl.OP_RESET%>">
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
