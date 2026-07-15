<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.ATMListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.ATMDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ATM List</title>
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
<form class="pb-5" action="<%=ORSView.ATM_LIST_CTL%>" method="post">

    <jsp:useBean id="dto" class="in.co.rays.project_3.dto.ATMDTO" scope="request"></jsp:useBean>

    <%
        int pageNo   = ServletUtility.getPageNo(request);
        int pageSize = ServletUtility.getPageSize(request);
        int index    = ((pageNo - 1) * pageSize) + 1;
        int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
        List list = ServletUtility.getList(request);
        Iterator<ATMDTO> it = list.iterator();

        if (list.size() != 0) {
    %>

    <center><h1 class="text-dark font-weight-bold pt-3"><u>ATM List</u></h1></center>

    <%-- Success Message --%>
    <% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4 alert alert-success alert-dismissible" style="background-color:#80ff80">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4><font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font></h4>
        </div>
        <div class="col-md-4"></div>
    </div>
    <% } %>

    <%-- Error Message --%>
    <% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4 alert alert-danger alert-dismissible">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h4>
        </div>
        <div class="col-md-4"></div>
    </div>
    <% } %>

    <%-- Search Fields --%>
    <div class="row">
        <div class="col-sm-1"></div>

        <%-- Security Code Search --%>
        <div class="col-sm-2">
            <input type="text" name="securityCode" placeholder="Security Code"
                class="form-control"
                value="<%=ServletUtility.getParameter("securityCode", request)%>">
        </div>
        &emsp;

        <%-- Bank Name Dropdown Search --%>
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap bankSearchMap = new java.util.LinkedHashMap();
                bankSearchMap.put("",               "-- Bank Name --");
                bankSearchMap.put("SBI",            "SBI");
                bankSearchMap.put("HDFC Bank",      "HDFC Bank");
                bankSearchMap.put("ICICI Bank",     "ICICI Bank");
                bankSearchMap.put("Axis Bank",      "Axis Bank");
                bankSearchMap.put("PNB",            "PNB");
                bankSearchMap.put("Bank of Baroda", "Bank of Baroda");
                bankSearchMap.put("Canara Bank",    "Canara Bank");
                bankSearchMap.put("Kotak Mahindra", "Kotak Mahindra");
                bankSearchMap.put("Yes Bank",       "Yes Bank");
                bankSearchMap.put("Union Bank",     "Union Bank");
                String bankSearch = HTMLUtility.getList(
                    "bankName",
                    ServletUtility.getParameter("bankName", request),
                    bankSearchMap
                );
            %>
            <%=bankSearch%>
        </div>
        &emsp;

        <%-- FIX: Location search field add kiya --%>
        <div class="col-sm-2">
            <input type="text" name="location" placeholder="Location"
                class="form-control"
                value="<%=ServletUtility.getParameter("location", request)%>">
        </div>
        &emsp;

        <%-- Cash Available Dropdown --%>
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap cashSearchMap = new java.util.LinkedHashMap();
                cashSearchMap.put("",    "-- Cash Available --");
                cashSearchMap.put("Yes", "Yes");
                cashSearchMap.put("No",  "No");
                String cashSearch = HTMLUtility.getList(
                    "cashAvailable",
                    ServletUtility.getParameter("cashAvailable", request),
                    cashSearchMap
                );
            %>
            <%=cashSearch%>
        </div>
        &emsp;

        <%-- Search & Reset Buttons --%>
        <div class="col-sm-2">
            <input type="submit" class="btn btn-primary btn-md" style="font-size:15px"
                name="operation" value="<%=ATMListCtl.OP_SEARCH%>">
            &nbsp;
            <input type="submit" class="btn btn-dark btn-md" style="font-size:15px"
                name="operation" value="<%=ATMListCtl.OP_RESET%>">
        </div>

        <div class="col-sm-1"></div>
    </div>
    <br>

    <%-- Data Table --%>
    <div style="margin-bottom:20px;" class="table-responsive">
    <table class="table table-bordered table-dark table-hover">
        <thead>
            <tr style="background-color:#8C8C8C;">
                <th width="10%"><input type="checkbox" id="select_all" name="Select" class="text"> Select All</th>
                <th width="5%"  class="text">S.NO</th>
                <th width="15%" class="text">Security Code</th>
                <th width="20%" class="text">Bank Name</th>
                <th width="25%" class="text">Location</th>
                <th width="15%" class="text">Cash Available</th>
                <th width="8%"  class="text">Edit</th>
            </tr>
        </thead>
        <% while (it.hasNext()) { dto = it.next(); %>
        <tbody>
            <tr>
                <td align="center">
                    <input type="checkbox" class="checkbox" name="ids" value="<%=dto.getId()%>">
                </td>
                <td class="text"><%=index++%></td>
                <td class="text"><%=dto.getSecurityCode()%></td>
                <td class="text"><%=dto.getBankName()%></td>
                <td class="text"><%=dto.getLocation()%></td>
                <td class="text"><%=dto.getCashAvailable()%></td>
                <td class="text">
                    <a href="<%=ORSView.APP_CONTEXT%>/ctl/ATMCtl?id=<%=dto.getId()%>">Edit</a>
                </td>
            </tr>
        </tbody>
        <% } %>
    </table>
    </div>

    <%-- Pagination + Action Buttons --%>
    <table width="100%">
        <tr>
            <td>
                <input type="submit" name="operation" class="btn btn-warning btn-md"
                    style="font-size:17px" value="<%=ATMListCtl.OP_PREVIOUS%>"
                    <%=pageNo > 1 ? "" : "disabled"%>>
            </td>
            <td>
                <input type="submit" name="operation" class="btn btn-primary btn-md"
                    style="font-size:17px" value="<%=ATMListCtl.OP_NEW%>">
            </td>
            <td>
                <input type="submit" name="operation" class="btn btn-danger btn-md"
                    style="font-size:17px" value="<%=ATMListCtl.OP_DELETE%>">
            </td>
            <td align="right">
                <input type="submit" name="operation" class="btn btn-warning btn-md"
                    style="font-size:17px" value="<%=ATMListCtl.OP_NEXT%>"
                    <%=(nextPageSize != 0) ? "" : "disabled"%>>
            </td>
        </tr>
    </table>

    <% } if (list.size() == 0) { %>

    <%-- Empty State --%>
    <center><h1 style="font-size:40px; color:#162390;">ATM List</h1></center><br>

    <% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4 alert alert-danger alert-dismissible">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h4>
        </div>
        <div class="col-md-4"></div>
    </div>
    <% } %><br>

    <%-- Search Fields (empty state mein bhi dikhenge) --%>
    <div class="row">
        <div class="col-sm-1"></div>
        <div class="col-sm-2">
            <input type="text" name="securityCode" placeholder="Security Code"
                class="form-control"
                value="<%=ServletUtility.getParameter("securityCode", request)%>">
        </div>
        &emsp;
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap bankSearchMap2 = new java.util.LinkedHashMap();
                bankSearchMap2.put("",               "-- Bank Name --");
                bankSearchMap2.put("SBI",            "SBI");
                bankSearchMap2.put("HDFC Bank",      "HDFC Bank");
                bankSearchMap2.put("ICICI Bank",     "ICICI Bank");
                bankSearchMap2.put("Axis Bank",      "Axis Bank");
                bankSearchMap2.put("PNB",            "PNB");
                bankSearchMap2.put("Bank of Baroda", "Bank of Baroda");
                bankSearchMap2.put("Canara Bank",    "Canara Bank");
                bankSearchMap2.put("Kotak Mahindra", "Kotak Mahindra");
                bankSearchMap2.put("Yes Bank",       "Yes Bank");
                bankSearchMap2.put("Union Bank",     "Union Bank");
                String bankSearch2 = HTMLUtility.getList(
                    "bankName",
                    ServletUtility.getParameter("bankName", request),
                    bankSearchMap2
                );
            %>
            <%=bankSearch2%>
        </div>
        &emsp;
        <div class="col-sm-2">
            <input type="text" name="location" placeholder="Location"
                class="form-control"
                value="<%=ServletUtility.getParameter("location", request)%>">
        </div>
        &emsp;
        <div class="col-sm-2">
            <%
                java.util.LinkedHashMap cashSearchMap2 = new java.util.LinkedHashMap();
                cashSearchMap2.put("",    "-- Cash Available --");
                cashSearchMap2.put("Yes", "Yes");
                cashSearchMap2.put("No",  "No");
                String cashSearch2 = HTMLUtility.getList(
                    "cashAvailable",
                    ServletUtility.getParameter("cashAvailable", request),
                    cashSearchMap2
                );
            %>
            <%=cashSearch2%>
        </div>
        &emsp;
        <div class="col-sm-2">
            <input type="submit" class="btn btn-primary btn-md" style="font-size:15px"
                name="operation" value="<%=ATMListCtl.OP_SEARCH%>">
            &nbsp;
            <input type="submit" class="btn btn-dark btn-md" style="font-size:15px"
                name="operation" value="<%=ATMListCtl.OP_RESET%>">
        </div>
        <div class="col-sm-1"></div>
    </div>
    <br>

    <div style="padding-left:48%;">
        <input type="submit" name="operation" class="btn btn-primary btn-md"
            style="font-size:17px" value="<%=ATMListCtl.OP_BACK%>">
    </div>

    <% } %>

    <input type="hidden" name="pageNo"   value="<%=pageNo%>">
    <input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>
</div>
</body>
<%@include file="FooterView.jsp"%>
</html>
