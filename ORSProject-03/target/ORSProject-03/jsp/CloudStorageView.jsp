<%@page import="in.co.rays.project_3.controller.CloudStorageCtl"%>
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
<title>Cloud Storage View</title>
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
<form action="<%=ORSView.CLOUD_STORAGE_CTL%>" method="post">
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.CloudStorageDTO" scope="request"></jsp:useBean>

<div class="row pt-3">
    <div class="col-md-4 mb-4"></div>
    <div class="col-md-4 mb-4">
        <div class="card input-group-addon">
            <div class="card-body">

                <%
                    long id = DataUtility.getLong(request.getParameter("id"));
                    if (dto.getFileName() != null && dto.getId() > 0) {
                %>
                <h3 class="text-center default-text text-primary">Update Cloud Storage</h3>
                <% } else { %>
                <h3 class="text-center default-text text-primary">Add Cloud Storage</h3>
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

                    <!-- File Name -->
                    <span class="pl-sm-5"><b>File Name</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-file grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="fileName"
                                placeholder="e.g. report.pdf"
                                value="<%=DataUtility.getStringData(dto.getFileName())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("fileName", request)%></font><br>

                    <!-- File Size - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>File Size</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-hdd grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap sizeMap = new LinkedHashMap();
                                sizeMap.put("1MB",   "1 MB");
                                sizeMap.put("5MB",   "5 MB");
                                sizeMap.put("10MB",  "10 MB");
                                sizeMap.put("25MB",  "25 MB");
                                sizeMap.put("50MB",  "50 MB");
                                sizeMap.put("100MB", "100 MB");
                                sizeMap.put("250MB", "250 MB");
                                sizeMap.put("500MB", "500 MB");
                                sizeMap.put("1GB",   "1 GB");
                                sizeMap.put("2GB",   "2 GB");
                                sizeMap.put("5GB",   "5 GB");
                                String sizeDropdown = HTMLUtility.getList(
                                    "fileSize",
                                    DataUtility.getStringData(dto.getFileSize()),
                                    sizeMap
                                );
                            %>
                            <%=sizeDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("fileSize", request)%></font><br>

                    <!-- Upload Date - datepicker1 -->
                    <span class="pl-sm-5"><b>Upload Date</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-calendar grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <input type="text" id="datepicker1" class="form-control"
                                name="uploadDate" placeholder="DD/MM/YYYY" readonly="readonly"
                                value="<%=DataUtility.getDateString(dto.getUploadDate())%>">
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("uploadDate", request)%></font><br>

                    <!-- Storage Type - Preloaded Dropdown -->
                    <span class="pl-sm-5"><b>Storage Type</b> <span style="color:red;">*</span></span><br>
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="fa fa-cloud grey-text" style="font-size:1rem;"></i>
                                </div>
                            </div>
                            <%
                                LinkedHashMap storageMap = new LinkedHashMap();
                                storageMap.put("Google Drive",    "Google Drive");
                                storageMap.put("Dropbox",         "Dropbox");
                                storageMap.put("OneDrive",        "OneDrive");
                                storageMap.put("Amazon S3",       "Amazon S3");
                                storageMap.put("iCloud",          "iCloud");
                                storageMap.put("Azure Blob",      "Azure Blob");
                                storageMap.put("Local Storage",   "Local Storage");
                                String storageDropdown = HTMLUtility.getList(
                                    "storageType",
                                    DataUtility.getStringData(dto.getStorageType()),
                                    storageMap
                                );
                            %>
                            <%=storageDropdown%>
                        </div>
                    </div>
                    <font color="red" class="pl-sm-5"><%=ServletUtility.getErrorMessage("storageType", request)%></font><br>

                    <!-- Buttons -->
                    <% if (dto.getFileName() != null && dto.getId() > 0) { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=CloudStorageCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=CloudStorageCtl.OP_CANCEL%>">
                    </div>
                    <% } else { %>
                    <div class="text-center">
                        <input type="submit" name="operation" class="btn btn-success btn-md"
                            style="font-size:17px" value="<%=CloudStorageCtl.OP_SAVE%>">
                        <input type="submit" name="operation" class="btn btn-warning btn-md"
                            style="font-size:17px" value="<%=CloudStorageCtl.OP_RESET%>">
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
