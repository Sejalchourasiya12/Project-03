package in.co.rays.project_3.controller;

/**
 * ORS View Provide Loose Coupling
 * 
 * @author Sejal Chourasiya
 *
 */
public interface ORSView {
	public String APP_CONTEXT = "/ORSProject-03";

	public String PAGE_FOLDER = "/jsp";

	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView404.jsp";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
	
	public String JASPER_CTL = APP_CONTEXT + "/ctl/JasperCtl";


	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String PATIENT_VIEW = PAGE_FOLDER + "/PatientView.jsp";
	public String PATIENT_LIST_VIEW = PAGE_FOLDER + "/PatientListView.jsp";
	public String QUEUE_VIEW = PAGE_FOLDER + "/QueueView.jsp";
	public String QUEUE_LIST_VIEW = PAGE_FOLDER + "/QueueListView.jsp";
	
	
	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";
	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";
	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	public String PRODUCT_VIEW = PAGE_FOLDER + "/ProductView.jsp";
	public String PRODUCT_LIST_VIEW = PAGE_FOLDER + "/ProductListView.jsp";
	public String LEAVE_LIST_VIEW = PAGE_FOLDER + "/LeaveListView.jsp";
	public String LEAVE_VIEW = PAGE_FOLDER + "/LeaveView.jsp";
	public String ANNOUNCEMENT_VIEW = PAGE_FOLDER +"/AnnouncementView.jsp";
	public String ANNOUNCEMENT_LIST_VIEW = PAGE_FOLDER +"/AnnouncementListView.jsp";
	public String CANDIDATE_VIEW = PAGE_FOLDER +"/CandidateView.jsp";
	public String CANDIDATE_LIST_VIEW = PAGE_FOLDER +"/CandidateListView.jsp";
	public String CONTACT_VIEW = PAGE_FOLDER +"/ContactView.jsp";
	public String CONTACT_LIST_VIEW = PAGE_FOLDER +"/ContactListView.jsp";
	public String FOOD_DELIVERY_VIEW = PAGE_FOLDER +"/FoodDeliveryView.jsp";
	public String FOOD_DELIVERY_LIST_VIEW = PAGE_FOLDER +"/FoodDeliveryListView.jsp";
	public String ASSET_VIEW = PAGE_FOLDER +"/AssetView.jsp";
	public String ASSET_LIST_VIEW = PAGE_FOLDER +"/AssetListView.jsp";
	public String REVIEW_VIEW = PAGE_FOLDER +"/ReviewView.jsp";
	public String REVIEW_LIST_VIEW = PAGE_FOLDER +"/ReviewListView.jsp";
	public String TRACKING_VIEW = PAGE_FOLDER +"/TrackingView.jsp";
	public String TRACKING_LIST_VIEW = PAGE_FOLDER +"/TrackingListView.jsp";
	public String DELIVERY_TRACKING_VIEW = PAGE_FOLDER +"/DeliveryTrackingView.jsp";
	public String DELIVERY_TRACKING_LIST_VIEW = PAGE_FOLDER +"/DeliveryTrackingListView.jsp";



	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";
	public String PRODUCT_CTL = APP_CONTEXT + "/ctl/ProductCtl";
	public String PRODUCT_LIST_CTL = APP_CONTEXT + "/ctl/ProductListCtl";
	public String QUEUE_CTL = APP_CONTEXT + "/ctl/QueueCtl";
	public String QUEUE_LIST_CTL = APP_CONTEXT + "/ctl/QueueListCtl";
	public String LEAVE_CTL = APP_CONTEXT + "/ctl/LeaveCtl";
	public String LEAVE_LIST_CTL = APP_CONTEXT + "/ctl/LeaveListCtl";
	public String ANNOUNCEMENT_CTL = APP_CONTEXT + "/ctl/AnnouncementCtl";
	public String ANNOUNCEMENT_LIST_CTL = APP_CONTEXT + "/ctl/AnnouncementListCtl";
	public String CANDIDATE_CTL = APP_CONTEXT + "/ctl/CandidateCtl";
	public String CANDIDATE_LIST_CTL = APP_CONTEXT + "/ctl/CandidateListCtl";
	public String CONTACT_CTL = APP_CONTEXT + "/ctl/ContactCtl";
	public String CONTACT_LIST_CTL = APP_CONTEXT + "/ctl/ContactListCtl";
	public String FOOD_DELIVERY_CTL = APP_CONTEXT + "/ctl/FoodDeliveryCtl";
	public String FOOD_DELIVERY_LIST_CTL = APP_CONTEXT + "/ctl/FoodDeliveryListCtl";
	public String ASSET_CTL = APP_CONTEXT + "/ctl/AssetCtl";
	public String ASSET_LIST_CTL = APP_CONTEXT + "/ctl/AssetListCtl";
	public String REVIEW_CTL = APP_CONTEXT + "/ctl/ReviewCtl";
	public String REVIEW_LIST_CTL = APP_CONTEXT + "/ctl/ReviewListCtl";
	public String TRACKING_CTL = APP_CONTEXT + "/ctl/TrackingCtl";
	public String TRACKING_LIST_CTL = APP_CONTEXT + "/ctl/TrackingListCtl";
	public String DELIVERY_TRACKING_CTL = APP_CONTEXT + "/ctl/DeliveryTrackingCtl";
	public String DELIVERY_TRACKING_LIST_CTL = APP_CONTEXT + "/ctl/DeliveryTrackingListCtl";

	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
	
	public static final String SESSION_CTL = APP_CONTEXT + "/ctl/SessionCtl";
	public static final String SESSION_LIST_CTL = APP_CONTEXT + "/ctl/SessionListCtl";
	public static final String SESSION_VIEW = "/jsp/SessionView.jsp";
	public static final String SESSION_LIST_VIEW = "/jsp/SessionListView.jsp";
	
	
	
	
	
	
	
	
	
	

	public String PATIENT_LIST_CTL = APP_CONTEXT + "/ctl/PatientListCtl";

	public String PATIENT_CTL = APP_CONTEXT + "/ctl/PatientCtl";

}