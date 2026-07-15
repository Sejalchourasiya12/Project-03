<%@page import="in.co.rays.project_3.controller.AchievementCtl"%>
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
<title>Achievement View</title>
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
<form action="<%=ORSView.ACHIEVEMENT_CTL%>" method="post">

<jsp:useBean id="dto"
    class="in.co.rays.project_3.dto.AchievementDTO"
    scope="request">
</jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    long id = DataUtility.getLong(request.getParameter("id"));
                    if (dto.getAchievementCode() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update Achievement</h3>
                <%
                    } else {
                %>
                <h3 class="text-center default-text text-primary">Add Achievement</h3>
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

                    <!-- Achievement Code -->
                    <span class="pl-sm-5"><b>Achievement Code</b>
                    <span style="color: red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-id-card grey-text" style="font-size: 1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="achievementCode"
                                placeholder="e.g. ACH001"
                                value="<%=DataUtility.getStringData(dto.getAchievementCode())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5">
                        <%=ServletUtility.getErrorMessage("achievementCode", request)%>
                    </font><br>

                    <!-- Achievement Name - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Achievement Name</b>
                    <span style="color: red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-trophy grey-text" style="font-size: 1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap achievementMap = new LinkedHashMap();
                                achievementMap.put("Gold Medal",        "Gold Medal");
                                achievementMap.put("Silver Medal",      "Silver Medal");
                                achievementMap.put("Bronze Medal",      "Bronze Medal");
                                achievementMap.put("Best Employee",     "Best Employee");
                                achievementMap.put("Best Student",      "Best Student");
                                achievementMap.put("Top Performer",     "Top Performer");
                                achievementMap.put("Certificate",       "Certificate");
                                achievementMap.put("Trophy",            "Trophy");
                                achievementMap.put("Star Award",        "Star Award");
                                achievementMap.put("Excellence Award",  "Excellence Award");
                                String achievementDropdown = HTMLUtility.getList(
                                    "achievementName",
                                    DataUtility.getStringData(dto.getAchievementName()),
                                    achievementMap
                                );
                            %>
                            <%=achievementDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5">
                        <%=ServletUtility.getErrorMessage("achievementName", request)%>
                    </font><br>

                    <!-- Earned By -->
                    <span class="pl-sm-5"><b>Earned By</b>
                    <span style="color: red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="earnedBy"
                                placeholder="Enter Name"
                                value="<%=DataUtility.getStringData(dto.getEarnedBy())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5">
                        <%=ServletUtility.getErrorMessage("earnedBy", request)%>
                    </font><br>

                    <!-- Status - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Status</b>
                    <span style="color: red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-info-circle grey-text" style="font-size: 1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap statusMap = new LinkedHashMap();
                                statusMap.put("Active",   "Active");
                                statusMap.put("Inactive", "Inactive");
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
                        if (dto.getAchievementCode() != null && dto.getId() > 0) {
                    %>
                    <div class="text-center">
                        <input type="submit" name="operation"
                            class="btn btn-success btn-md" style="font-size: 17px"
                            value="<%=AchievementCtl.OP_UPDATE%>">
                        <input type="submit" name="operation"
                            class="btn btn-warning btn-md" style="font-size: 17px"
                            value="<%=AchievementCtl.OP_CANCEL%>">
                    </div>
                    <%
                        } else {
                    %>
                    <div class="text-center">
                        <input type="submit" name="operation"
                            class="btn btn-success btn-md" style="font-size: 17px"
                            value="<%=AchievementCtl.OP_SAVE%>">
                        <input type="submit" name="operation"
                            class="btn btn-warning btn-md" style="font-size: 17px"
                            value="<%=AchievementCtl.OP_RESET%>">
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
