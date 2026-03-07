<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.QueueCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.dto.QueueDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Queue Registration</title>
<link rel="stylesheet"
    href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style type="text/css">
.p4 {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/registerqueue.jpg');
    background-size: 100%;
    padding-top: 60px;
}
.input-group-addon {
    box-shadow: 9px 8px 7px #001a33;
}
i.css {
    border: 2px solid #8080803b;
    padding: 6px;
    background-color: #ebebe0;
}
</style>
</head>

<body class="p4">
<div class="header">
    <%@include file="Header.jsp"%>
    <%@include file="calendar.jsp"%>
</div>

<main>
<form action="<%=ORSView.QUEUE_CTL%>" method="post">
    <jsp:useBean id="dto" class="in.co.rays.project_3.dto.QueueDTO" scope="request"/>
    
    <div class="row pt-3">
        <div class="col-md-4 mb-4"></div>
        <div class="col-md-4 mb-4">
            <div class="card input-group-addon">
                <div class="card-body">

                    <h3 class="text-center default-text text-success pb-2">Queue Registration</h3>

                    <%-- Success/Error messages --%>
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
                    <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
                    <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

                    <%-- Queue Code --%>
                    <span class="pl-sm-5"><b>Queue Code</b><span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text"><i class="fa fa-hashtag grey-text" style="font-size:1rem;"></i></div>
                            </div>
                            <input type="text" class="form-control" name="queueCode" placeholder="Queue Code" value="<%=DataUtility.getStringData(dto.getQueueCode())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("queueCode", request)%></font><br>

                    <%-- Queue Name --%>
                    <span class="pl-sm-5"><b>Queue Name</b><span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text"><i class="fa fa-list grey-text" style="font-size:1rem;"></i></div>
                            </div>
                            <input type="text" class="form-control" name="queueName" placeholder="Queue Name" value="<%=DataUtility.getStringData(dto.getQueueName())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("queueName", request)%></font><br>

                    <%-- Queue Size --%>
                    <span class="pl-sm-5"><b>Queue Size</b><span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text"><i class="fa fa-sort-numeric-up grey-text" style="font-size:1rem;"></i></div>
                            </div>
                           <input type="number" class="form-control" name="queueSize" placeholder="Queue Size"
       value="<%= String.valueOf(dto.getQueueSize()) %>">

                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("queueSize", request)%></font><br>

                    <%-- Queue Status --%>
                    <span class="pl-sm-5"><b>Queue Status</b><span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text"><i class="fa fa-toggle-on grey-text" style="font-size:1rem;"></i></div>
                            </div>
                            <%
                                HashMap statusMap = new HashMap();
                                statusMap.put("Active", "Active");
                                statusMap.put("Inactive", "Inactive");
                                String htmlStatusList = HTMLUtility.getList("queueStatus", dto.getQueueStatus(), statusMap);
                            %>
                            <%=htmlStatusList%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("queueStatus", request)%></font><br>

                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md" style="font-size:17px" value="<%=QueueCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-secondary btn-md" style="font-size:17px" value="<%=QueueCtl.OP_RESET%>">
                    </div>

                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4"></div>
    </div>
</form>
</main>

<div class="footer">
    <%@include file="FooterView.jsp"%>
</div>

</body>
</html>
