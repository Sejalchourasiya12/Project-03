<%@page import="in.co.rays.project_3.controller.ATMCtl"%>
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
<title>ATM View</title>
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
<form action="<%=ORSView.ATM_CTL%>" method="post">
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ATMDTO" scope="request"></jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    long id = DataUtility.getLong(request.getParameter("id"));
                    if (dto.getSecurityCode() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update ATM</h3>
                <% } else { %>
                <h3 class="text-center default-text text-primary">Add ATM</h3>
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

                    <!-- Security Code -->
                    <span class="pl-sm-5"><b>Security Code</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-shield-alt grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="securityCode"
                                placeholder="e.g. ATM001"
                                value="<%=DataUtility.getStringData(dto.getSecurityCode())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("securityCode", request)%></font><br>

                    <!-- Bank Name - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Bank Name</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-university grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap bankMap = new LinkedHashMap();
                                bankMap.put("SBI",             "SBI");
                                bankMap.put("HDFC Bank",       "HDFC Bank");
                                bankMap.put("ICICI Bank",      "ICICI Bank");
                                bankMap.put("Axis Bank",       "Axis Bank");
                                bankMap.put("PNB",             "PNB");
                                bankMap.put("Bank of Baroda",  "Bank of Baroda");
                                bankMap.put("Canara Bank",     "Canara Bank");
                                bankMap.put("Kotak Mahindra",  "Kotak Mahindra");
                                bankMap.put("Yes Bank",        "Yes Bank");
                                bankMap.put("Union Bank",      "Union Bank");
                                String bankDropdown = HTMLUtility.getList(
                                    "bankName",
                                    DataUtility.getStringData(dto.getBankName()),
                                    bankMap
                                );
                            %>
                            <%=bankDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("bankName", request)%></font><br>

                    <!-- Location -->
                    <span class="pl-sm-5"><b>Location</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-map-marker-alt grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="location"
                                placeholder="Enter ATM Location"
                                value="<%=DataUtility.getStringData(dto.getLocation())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("location", request)%></font><br>

                    <!-- Cash Available - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Cash Available</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-money-bill-wave grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap cashMap = new LinkedHashMap();
                                cashMap.put("Yes", "Yes");
                                cashMap.put("No",  "No");
                                String cashDropdown = HTMLUtility.getList(
                                    "cashAvailable",
                                    DataUtility.getStringData(dto.getCashAvailable()),
                                    cashMap
                                );
                            %>
                            <%=cashDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("cashAvailable", request)%></font><br>

                    <!-- Buttons -->
                    <% if (dto.getSecurityCode() != null && dto.getId() > 0) { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=ATMCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=ATMCtl.OP_CANCEL%>">
                    </div>
                    <% } else { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=ATMCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=ATMCtl.OP_RESET%>">
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
