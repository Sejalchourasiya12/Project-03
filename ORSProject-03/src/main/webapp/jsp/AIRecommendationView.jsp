<%@page import="in.co.rays.project_3.controller.AIRecommendationCtl"%>
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
<title>AI Recommendation View</title>
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
<form action="<%=ORSView.AI_RECOMMENDATION_CTL%>" method="post">
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AIRecommendationDTO" scope="request"></jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    if (dto.getRecommendationCode() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update AI Recommendation</h3>
                <% } else { %>
                <h3 class="text-center default-text text-primary">Add AI Recommendation</h3>
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

                    <!-- Recommendation Code -->
                    <span class="pl-sm-5"><b>Recommendation Code</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-robot grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="recommendationCode"
                                placeholder="e.g. REC-001"
                                value="<%=DataUtility.getStringData(dto.getRecommendationCode())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("recommendationCode", request)%></font><br>

                    <!-- Username -->
                    <span class="pl-sm-5"><b>Username</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-user grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="userName"
                                placeholder="e.g. rahul.sharma"
                                value="<%=DataUtility.getStringData(dto.getUserName())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("userName", request)%></font><br>

                    <!-- Recommendation Type - Dropdown -->
                    <span class="pl-sm-5"><b>Recommendation Type</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-brain grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap typeMap = new LinkedHashMap();
                                typeMap.put("Product",      "Product");
                                typeMap.put("Movie",        "Movie");
                                typeMap.put("Music",        "Music");
                                typeMap.put("News",         "News");
                                typeMap.put("Course",       "Course");
                                typeMap.put("Job",          "Job");
                                typeMap.put("Food",         "Food");
                                typeMap.put("Travel",       "Travel");
                                typeMap.put("Health",       "Health");
                                String typeDropdown = HTMLUtility.getList(
                                    "recommendationType",
                                    DataUtility.getStringData(dto.getRecommendationType()),
                                    typeMap
                                );
                            %>
                            <%=typeDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("recommendationType", request)%></font><br>

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
                                statusMap.put("Active",    "Active");
                                statusMap.put("Inactive",  "Inactive");
                                statusMap.put("Pending",   "Pending");
                                statusMap.put("Approved",  "Approved");
                                statusMap.put("Rejected",  "Rejected");
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
                    <% if (dto.getRecommendationCode() != null && dto.getId() > 0) { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=AIRecommendationCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=AIRecommendationCtl.OP_CANCEL%>">
                    </div>
                    <% } else { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=AIRecommendationCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=AIRecommendationCtl.OP_RESET%>">
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
