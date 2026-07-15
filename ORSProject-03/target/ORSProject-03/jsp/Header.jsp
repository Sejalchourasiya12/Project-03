<%@page import="in.co.rays.project_3.controller.LoginCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.RoleDTO"%>
<%@page import="in.co.rays.project_3.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Header</title>

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style>
.aj {
	background: linear-gradient(135deg, #89f7fe, #66a6ff);
}

.student-nav-fix .navbar-nav {
	align-items: center !important;
}

.student-nav-fix .dropdown-menu a {
	color: #000 !important;
}

.student-nav-fix .nav-link {
	color: #fff !important;
}
</style>
</head>

<body>

	<%
		UserDTO userDto = (UserDTO) session.getAttribute("user");
		boolean userLoggedIn = userDto != null;

		String welcomeMsg = "Hi, ";
		if (userLoggedIn) {
			String role = (String) session.getAttribute("role");
			welcomeMsg += userDto.getFirstName() + " (" + role + ")";
		} else {
			welcomeMsg += "Guest";
		}
	%>

	<nav
		class="navbar navbar-expand-lg fixed-top aj 
    <%if (userLoggedIn && userDto.getRoleId() == RoleDTO.STUDENT) {%>
        student-nav-fix
    <%}%>">

		<a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>"> <img
			src="<%=ORSView.APP_CONTEXT%>/img/custom.png" width="190px"
			height="50px">
		</a>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"> <i class="fa fa-bars"
				style="color: #fff; font-size: 28px;"></i>
			</span>
		</button>

		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ml-auto">

				<%
					if (userLoggedIn) {
				%>

				<%
					if (userDto.getRoleId() == RoleDTO.STUDENT) {
				%>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<span style="color: #000000;">Marksheet</span>
				</a>
					<div class="dropdown-menu">
						<a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"> <i
							class="fa fa-file-alt"></i> Marksheet Merit List
						</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<span style="color: white;">User</span>
				</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>"> <i
							class="fa fa-user-tie"></i> My Profile
						</a> <a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>">
							<i class="fa fa-edit"></i> Change Password
						</a>
					</div></li>

				<%
					} else if (userDto.getRoleId() == RoleDTO.ADMIN) {
				%>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">User</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.USER_CTL%>"><i
							class="fa fa-user-circle"></i>Add User</a> <a class="dropdown-item"
							href="<%=ORSView.USER_LIST_CTL%>"><i
							class="fa fa-user-friends"></i>User List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Marksheet</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.MARKSHEET_CTL%>"><i
							class="fa fa-file"></i>Add Marksheet</a> <a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_LIST_CTL%>"><i class="fa fa-paste"></i>Marksheet
							List</a> <a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><i
							class="fa fa-file-alt"></i>Marksheet Merit List</a> <a
							class="dropdown-item" href="<%=ORSView.GET_MARKSHEET_CTL%>"><i
							class="fa fa-copy"></i>Get Marksheet</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Role</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.ROLE_CTL%>"><i
							class="fa fa-user-tie"></i>Add Role</a> <a class="dropdown-item"
							href="<%=ORSView.ROLE_LIST_CTL%>"><i
							class="fa fa-user-friends"></i>Role List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">College</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.COLLEGE_CTL%>"><i
							class="fa fa-university"></i>Add College</a> <a class="dropdown-item"
							href="<%=ORSView.COLLEGE_LIST_CTL%>"><i
							class="fa fa-building"></i>College List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Course</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.COURSE_CTL%>"><i
							class="fa fa-book-open"></i>Add Course</a> <a class="dropdown-item"
							href="<%=ORSView.COURSE_LIST_CTL%>"><i
							class="fa fa-sort-amount-down"></i>Course List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Student</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.STUDENT_CTL%>"><i
							class="fa fa-user-circle"></i>Add Student</a> <a
							class="dropdown-item" href="<%=ORSView.STUDENT_LIST_CTL%>"><i
							class="fa fa-users"></i>Student List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Faculty</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.FACULTY_CTL%>"><i
							class="fa fa-user-tie"></i>Add Faculty</a> <a class="dropdown-item"
							href="<%=ORSView.FACULTY_LIST_CTL%>"><i class="fa fa-users"></i>Faculty
							List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Time Table</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.TIMETABLE_CTL%>"><i
							class="fa fa-clock"></i>Add TimeTable</a> <a class="dropdown-item"
							href="<%=ORSView.TIMETABLE_LIST_CTL%>"><i class="fa fa-clock"></i>TimeTable
							List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Subject</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.SUBJECT_CTL%>"><i
							class="fa fa-calculator"></i>Add Subject</a> <a class="dropdown-item"
							href="<%=ORSView.SUBJECT_LIST_CTL%>"><i
							class="fa fa-sort-amount-down"></i>Subject List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Product</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.PRODUCT_CTL%>"><i
							class="fa fa-file"></i>Add Product</a> <a class="dropdown-item"
							href="<%=ORSView.PRODUCT_LIST_CTL%>"><i class="fa fa-paste"></i>Product
							List</a>
					</div></li>

				<%-- 	<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Staff Member</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.STAFF_MEMBER_CTL%>">
							<i class="fa fa-user-tie"></i> Add Staff Member
						</a> <a class="dropdown-item"
							href="<%=ORSView.STAFF_MEMBER_LIST_CTL%>"> <i
							class="fa fa-users"></i> Staff Member List
						</a>
					</div></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Bank </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.BANK_CTL%>"> <i
							class="fa fa-university"></i> Add Bank Account
						</a> <a class="dropdown-item" href="<%=ORSView.BANK_LIST_CTL%>"> <i
							class="fa fa-list"></i> Bank Account List
						</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Patient </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.PATIENT_CTL%>"> Add
							Patient </a> <a class="dropdown-item"
							href="<%=ORSView.PATIENT_LIST_CTL%>"> Patient List </a>
					</div></li>
 --%>
				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Patient </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.PATIENT_CTL%>"> <i
							class="fa fa-user-plus"></i> Add Patient
						</a> <a class="dropdown-item" href="<%=ORSView.PATIENT_LIST_CTL%>">
							<i class="fa fa-list"></i> Patient List
						</a>
					</div></li> --%>

				<%--li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Queue</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.QUEUE_CTL%>"> <i
							class="fa fa-list"></i> Add Queue
						</a> <a class="dropdown-item" href="<%=ORSView.QUEUE_LIST_CTL%>">
							<i class="fa fa-th-list"></i> Queue List
						</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Session</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.SESSION_CTL%>"> <i
							class="fa fa-clock"></i> Add Session
						</a> <a class="dropdown-item" href="<%=ORSView.SESSION_LIST_CTL%>">
							<i class="fa fa-list"></i> Session List
						</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Leave</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.LEAVE_CTL%>"> <i
							class="fa fa-calendar-plus"></i> Add Leave
						</a> <a class="dropdown-item" href="<%=ORSView.LEAVE_LIST_CTL%>">
							<i class="fa fa-list"></i> Session List
						</a>
					</div></li>--%>


				<%-- <li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Announcement </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.ANNOUNCEMENT_CTL%>">
							<i class="fa fa-bullhorn"></i> Add Announcement
						</a> <a class="dropdown-item"
							href="<%=ORSView.ANNOUNCEMENT_LIST_CTL%>"> <i
							class="fa fa-list"></i> Announcement List
						</a>

					</div></li>--%>


				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Candidate</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.CANDIDATE_CTL%>"> <i
							class="fa fa-user-plus"></i> Add Candidate
						</a> <a class="dropdown-item" href="<%=ORSView.CANDIDATE_LIST_CTL%>">
							<i class="fa fa-list"></i> Candidate List
						</a>
					</div></li>--%>


				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Contact</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.CONTACT_CTL%>"> <i
							class="fa fa-address-book"></i> Add Contact
						</a> <a class="dropdown-item" href="<%=ORSView.CONTACT_LIST_CTL%>">
							<i class="fa fa-list"></i> Contact List
						</a>
					</div></li>--%>


				<%-- <li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Food Delivery</a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.FOOD_DELIVERY_CTL%>">
							<i class="fa fa-utensils"></i> Add Food Order
						</a> <a class="dropdown-item"
							href="<%=ORSView.FOOD_DELIVERY_LIST_CTL%>"> <i
							class="fa fa-list"></i> Food Order List
						</a>

					</div></li>--%>



				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Asset</a>

					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.ASSET_CTL%>"> <i
							class="fa fa-box"></i> Add Asset
						</a> <a class="dropdown-item" href="<%=ORSView.ASSET_LIST_CTL%>">
							<i class="fa fa-list"></i> Asset List
						</a>
					</div></li>--%>

				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Review</a>

					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.REVIEW_CTL%>"> <i
							class="fa fa-star"></i> Add Review
						</a> <a class="dropdown-item" href="<%=ORSView.REVIEW_LIST_CTL%>">
							<i class="fa fa-list"></i> Review List
						</a>
					</div></li>--%>
				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Tracking</a>

					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.TRACKING_CTL%>"> <i
							class="fa fa-truck"></i> Add Tracking
						</a> <a class="dropdown-item" href="<%=ORSView.TRACKING_LIST_CTL%>">
							<i class="fa fa-list"></i> Tracking List
						</a>
					</div></li>--%>

				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Delivery Tracking</a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.DELIVERY_TRACKING_CTL%>">
							<i class="fa fa-truck"></i> Add Delivery Tracking
						</a> <a class="dropdown-item"
							href="<%=ORSView.DELIVERY_TRACKING_LIST_CTL%>"> <i
							class="fa fa-list"></i> Delivery Tracking List
						</a>

					</div></li>--%>

				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Promotion</a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.PROMOTION_CTL%>"> <i
							class="fa fa-bullhorn"></i> Add Promotion
						</a> <a class="dropdown-item" href="<%=ORSView.PROMOTION_LIST_CTL%>">
							<i class="fa fa-list"></i> Promotion List
						</a>

					</div></li>--%>

					<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Enrollment </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.ENROLLMENT_CTL%>"> <i
							class="fa fa-user-plus"></i> Add Enrollment
						</a> <a class="dropdown-item" href="<%=ORSView.ENROLLMENT_LIST_CTL%>">
							<i class="fa fa-list"></i> Enrollment List
						</a>

					</div></li>--%>


				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Donation Camp </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.DONATION_CAMP_CTL%>">
							<i class="fa fa-hand-holding-heart"></i> Add Donation Camp
						</a> <a class="dropdown-item"
							href="<%=ORSView.DONATION_CAMP_LIST_CTL%>"> <i
							class="fa fa-list"></i> Donation Camp List
						</a>

					</div></li>--%>

				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Subscription Plan </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.SUBSCRIPTION_PLAN_CTL%>">
							<i class="fa fa-tag"></i> Add Subscription Plan
						</a> <a class="dropdown-item"
							href="<%=ORSView.SUBSCRIPTION_PLAN_LIST_CTL%>"> <i
							class="fa fa-list"></i> Subscription Plan List
						</a>

					</div></li>--%>

				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Penalty </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.PENALTY_CTL%>"> <i
							class="fa fa-exclamation-circle"></i> Add Penalty
						</a> <a class="dropdown-item" href="<%=ORSView.PENALTY_LIST_CTL%>">
							<i class="fa fa-list"></i> Penalty List
						</a>
					</div></li>--%>


				<%--<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;"> Consumer </a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.CONSUMER_CTL%>">
            <i class="fa fa-user-plus"></i> Add Consumer
        </a>
        <a class="dropdown-item" href="<%=ORSView.CONSUMER_LIST_CTL%>">
            <i class="fa fa-list"></i> Consumer List
        </a>
    </div>
</li>--%>

				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Book Issue </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.BOOK_ISSUE_CTL%>"> <i
							class="fa fa-book"></i> Add Book Issue
						</a> <a class="dropdown-item" href="<%=ORSView.BOOK_ISSUE_LIST_CTL%>">
							<i class="fa fa-list"></i> Book Issue List
						</a>
					</div></li>--%>

				<!-- User Profile -->
				<%--<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">User Profile</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.USER_PROFILE_CTL%>">
							<i class="fa fa-user"></i> Add User Profile
						</a> <a class="dropdown-item"
							href="<%=ORSView.USER_PROFILE_LIST_CTL%>"> <i
							class="fa fa-list"></i> User Profile List
						</a>
					</div></li>--%>
					<li class="nav-item dropdown">
   <%-- <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Gender</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.GENDER_CTL%>">
            <i class="fa fa-venus-mars"></i> Add Gender
        </a>
        <a class="dropdown-item" href="<%=ORSView.GENDER_LIST_CTL%>">
            <i class="fa fa-list"></i> Gender List
        </a>
    </div>
</li>--%>

 <%-- <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Smart Light</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.SMART_LIGHT_CTL%>">
            <i class="fa fa-lightbulb"></i> Add Smart Light
        </a>
        <a class="dropdown-item" href="<%=ORSView.SMART_LIGHT_LIST_CTL%>">
            <i class="fa fa-list"></i> Smart Light List
        </a>
    </div>
</li>

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Achievement</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.ACHIEVEMENT_CTL%>">
            <i class="fa fa-trophy"></i> Add Achievement
        </a>
        <a class="dropdown-item" href="<%=ORSView.ACHIEVEMENT_LIST_CTL%>">
            <i class="fa fa-list"></i> Achievement List
        </a>
    </div>
</li>--%>

 <%--<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Podcast</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.PODCAST_CTL%>">
            <i class="fa fa-microphone"></i> Add Podcast
        </a>
        <a class="dropdown-item" href="<%=ORSView.PODCAST_LIST_CTL%>">
            <i class="fa fa-list"></i> Podcast List
        </a>
    </div>
</li>

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Bug Tracking</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.BUG_TRACKING_CTL%>">
            <i class="fa fa-bug"></i> Add Bug
        </a>
        <a class="dropdown-item" href="<%=ORSView.BUG_TRACKING_LIST_CTL%>">
            <i class="fa fa-list"></i> Bug List
        </a>
    </div>
</li>

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Blood Bank</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.BLOOD_BANK_CTL%>">
            <i class="fa fa-tint"></i> Add Blood Bank
        </a>
        <a class="dropdown-item" href="<%=ORSView.BLOOD_BANK_LIST_CTL%>">
            <i class="fa fa-list"></i> Blood Bank List
        </a>
    </div>
</li>

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">ATM</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.ATM_CTL%>">
            <i class="fa fa-credit-card"></i> Add ATM
        </a>
        <a class="dropdown-item" href="<%=ORSView.ATM_LIST_CTL%>">
            <i class="fa fa-list"></i> ATM List
        </a>
    </div>
</li>

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Library</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.LIBRARY_CTL%>">
            <i class="fa fa-book"></i> Add Library
        </a>
        <a class="dropdown-item" href="<%=ORSView.LIBRARY_LIST_CTL%>">
            <i class="fa fa-list"></i> Library List
        </a>
    </div>
</li>
		
		
<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;"> Employee Payroll </a>

   <div class="dropdown-menu">
<a class="dropdown-item" href="<%=ORSView.EMPLOYEE_PAYROLL_CTL%>">
<i class="fa fa-money-bill"></i> Add Employee Payroll </a>

        <a class="dropdown-item" href="<%=ORSView.EMPLOYEE_PAYROLL_LIST_CTL%>">
 <i class="fa fa-list"></i>Employee Payroll List
 </a>
 </div>
</li>

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Cloud Storage</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.CLOUD_STORAGE_CTL%>">
            <i class="fa fa-cloud-upload-alt"></i> Add Cloud Storage
        </a>
        <a class="dropdown-item" href="<%=ORSView.CLOUD_STORAGE_LIST_CTL%>">
            <i class="fa fa-list"></i> Cloud Storage List
        </a>
    </div>
</li>


<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Policy</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.POLICY_CTL%>">
            <i class="fa fa-file-contract"></i> Add Policy
        </a>
        <a class="dropdown-item" href="<%=ORSView.POLICY_LIST_CTL%>">
            <i class="fa fa-list"></i> Policy List
        </a>
    </div>
</li>	--%>		

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
        style="color: white;">Vehicle Insurance</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=ORSView.VEHICLE_INSURANCE_CTL%>">
            <i class="fa fa-car"></i> Add Vehicle Insurance
        </a>
        <a class="dropdown-item" href="<%=ORSView.VEHICLE_INSURANCE_LIST_CTL%>">
            <i class="fa fa-list"></i> Vehicle Insurance List
        </a>
    </div>
</li>
		<%
					}
				%>

				<%
					}
				%>

				<!-- ✅ WELCOME DROPDOWN -->
				<li class="nav-item dropdown ml-3"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<span style="color: white;"><%=welcomeMsg%></span>
				</a>
					<div class="dropdown-menu dropdown-menu-right">
						<%
							if (userLoggedIn) {
						%>
						<a class="dropdown-item"
							href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">
							<i class="fa fa-sign-out-alt"></i> Logout
						</a> <a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>">
							<i class="fa fa-user-tie"></i> My Profile
						</a> <a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>">
							<i class="fa fa-edit"></i> Change Password
						</a> <a class="dropdown-item" target="blank"
							href="<%=ORSView.JAVA_DOC_VIEW%>"> <i class="fa fa-clone"></i>
							Java Doc
						</a>
						<%
							} else {
						%>
						<a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>"> <i
							class="fa fa-sign-in-alt"></i> Login
						</a> <a class="dropdown-item"
							href="<%=ORSView.USER_REGISTRATION_CTL%>"> <i
							class="fa fa-registered"></i> User Registration
						</a>
						<%
							}
						%>
					</div></li>


			</ul>
		</div>
	</nav>

</body>
</html>
