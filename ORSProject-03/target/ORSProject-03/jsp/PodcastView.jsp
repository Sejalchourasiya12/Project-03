<%@page import="in.co.rays.project_3.controller.PodcastCtl"%>
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
<title>Podcast View</title>
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
<form action="<%=ORSView.PODCAST_CTL%>" method="post">
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PodcastDTO" scope="request"></jsp:useBean>
<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    long id = DataUtility.getLong(request.getParameter("id"));
                    if (dto.getPodcastCode() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update Podcast</h3>
                <% } else { %>
                <h3 class="text-center default-text text-primary">Add Podcast</h3>
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

                    <!-- Podcast Code -->
                    <span class="pl-sm-5"><b>Podcast Code</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-id-card grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="podcastCode"
                                placeholder="e.g. POD001"
                                value="<%=DataUtility.getStringData(dto.getPodcastCode())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("podcastCode", request)%></font><br>

                    <!-- Podcast Title -->
                    <span class="pl-sm-5"><b>Podcast Title</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-microphone grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="podcastTitle"
                                placeholder="Enter Podcast Title"
                                value="<%=DataUtility.getStringData(dto.getPodcastTitle())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("podcastTitle", request)%></font><br>

                    <!-- Host Name -->
                    <span class="pl-sm-5"><b>Host Name</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-user grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="hostName"
                                placeholder="Enter Host Name"
                                value="<%=DataUtility.getStringData(dto.getHostName())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("hostName", request)%></font><br>

                    <!-- Status - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Status</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-info-circle grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap statusMap = new LinkedHashMap();
                                statusMap.put("Active",   "Active");
                                statusMap.put("Inactive", "Inactive");
                                statusMap.put("Upcoming", "Upcoming");
                                statusMap.put("Ended",    "Ended");
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
                    <% if (dto.getPodcastCode() != null && dto.getId() > 0) { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=PodcastCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=PodcastCtl.OP_CANCEL%>">
                    </div>
                    <% } else { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=PodcastCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=PodcastCtl.OP_RESET%>">
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
