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
	public String PROMOTION_VIEW = PAGE_FOLDER +"/PromotionView.jsp";
	public String PROMOTION_LIST_VIEW = PAGE_FOLDER +"/PromotionListView.jsp";
	public String DONATION_CAMP_VIEW = PAGE_FOLDER +"/DonationCampView.jsp";
	public String DONATION_CAMP_LIST_VIEW = PAGE_FOLDER +"/DonationCampListView.jsp";
	// Views
	public String ENROLLMENT_VIEW = PAGE_FOLDER + "/EnrollmentView.jsp";
	public String ENROLLMENT_LIST_VIEW = PAGE_FOLDER + "/EnrollmentListView.jsp";
	// ORSView.java mein yeh lines add karo:

	public String SUBSCRIPTION_PLAN_VIEW = PAGE_FOLDER + "/SubscriptionPlanView.jsp";
	public String SUBSCRIPTION_PLAN_LIST_VIEW = PAGE_FOLDER + "/SubscriptionPlanListView.jsp";
	
	public String PENALTY_VIEW = PAGE_FOLDER + "/PenaltyView.jsp";
	public String PENALTY_LIST_VIEW = PAGE_FOLDER + "/PenaltyListView.jsp";
	public String USER_PROFILE_VIEW = PAGE_FOLDER + "/UserProfileView.jsp";
	public String  USER_PROFILE_LIST_VIEW = PAGE_FOLDER + "/UserProfileListView.jsp";
	
	
	public String CONSUMER_VIEW = PAGE_FOLDER + "/ConsumerView.jsp";
	public String CONSUMER_LIST_VIEW = PAGE_FOLDER + "/ConsumerListView.jsp";
	
	public String BOOK_ISSUE_VIEW = PAGE_FOLDER + "/BookIssueView.jsp";
	public String BOOK_ISSUE_LIST_VIEW = PAGE_FOLDER + "/BookIssueListView.jsp";

	// Controllers Section mein:
	

	


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
	public String PROMOTION_CTL = APP_CONTEXT + "/ctl/PromotionCtl";
	public String PROMOTION_LIST_CTL = APP_CONTEXT + "/ctl/PromotionListCtl";
	public String DONATION_CAMP_CTL = APP_CONTEXT + "/ctl/DonationCampCtl";
	public String DONATION_CAMP_LIST_CTL = APP_CONTEXT + "/ctl/DonationCampListCtl";
	
	public String SUBSCRIPTION_PLAN_CTL = APP_CONTEXT + "/ctl/SubscriptionPlanCtl";
	public String SUBSCRIPTION_PLAN_LIST_CTL = APP_CONTEXT + "/ctl/SubscriptionPlanListCtl";
	// Controllers
		public String ENROLLMENT_CTL = APP_CONTEXT + "/ctl/EnrollmentCtl";
		public String ENROLLMENT_LIST_CTL = APP_CONTEXT + "/ctl/EnrollmentListCtl";
		
		public String PENALTY_CTL = APP_CONTEXT + "/ctl/PenaltyCtl";
		
		public String PENALTY_LIST_CTL = APP_CONTEXT + "/ctl/PenaltyListCtl";
		public String CONSUMER_CTL = APP_CONTEXT + "/ctl/ConsumerCtl";
		public String CONSUMER_LIST_CTL = APP_CONTEXT + "/ctl/ConsumerListCtl";
		
		 
		public String BOOK_ISSUE_CTL = APP_CONTEXT + "/ctl/BookIssueCtl";
		public String BOOK_ISSUE_LIST_CTL = APP_CONTEXT + "/ctl/BookIssueListCtl";
		public String USER_PROFILE_CTL = APP_CONTEXT + "/ctl/UserProfileCtl";
		public String USER_PROFILE_LIST_CTL = APP_CONTEXT + "/ctl/UserProfileListCtl";
		
		public String BUG_TRACKING_VIEW      = PAGE_FOLDER + "/BugTrackingView.jsp";
		public String BUG_TRACKING_LIST_VIEW = PAGE_FOLDER + "/BugTrackingListView.jsp";
		public String BUG_TRACKING_CTL       = APP_CONTEXT + "/ctl/BugTrackingCtl";
		public String BUG_TRACKING_LIST_CTL  = APP_CONTEXT + "/ctl/BugTrackingListCtl";
		 
		

	
	
	

	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
	
	
	
	
	
	
	
	
	public String GENDER_VIEW = PAGE_FOLDER + "/GenderView.jsp";
	public String GENDER_LIST_VIEW = PAGE_FOLDER + "/GenderListView.jsp";
	public String GENDER_CTL = APP_CONTEXT + "/ctl/GenderCtl";
	public String GENDER_LIST_CTL = APP_CONTEXT + "/ctl/GenderListCtl";
	
	public String SMART_LIGHT_VIEW      = PAGE_FOLDER + "/SmartLightView.jsp";
	public String SMART_LIGHT_LIST_VIEW = PAGE_FOLDER + "/SmartLightListView.jsp";
	public String SMART_LIGHT_CTL       = APP_CONTEXT + "/ctl/SmartLightCtl";
	public String SMART_LIGHT_LIST_CTL  = APP_CONTEXT + "/ctl/SmartLightListCtl";
	
	
	public String ACHIEVEMENT_VIEW      = PAGE_FOLDER + "/AchievementView.jsp";
	public String ACHIEVEMENT_LIST_VIEW = PAGE_FOLDER + "/AchievementListView.jsp";
	public String ACHIEVEMENT_CTL       = APP_CONTEXT + "/ctl/AchievementCtl";
	public String ACHIEVEMENT_LIST_CTL  = APP_CONTEXT + "/ctl/AchievementListCtl";
	
	public String PODCAST_VIEW      = PAGE_FOLDER + "/PodcastView.jsp";
	public String PODCAST_LIST_VIEW = PAGE_FOLDER + "/PodcastListView.jsp";
	public String PODCAST_CTL       = APP_CONTEXT + "/ctl/PodcastCtl";
	public String PODCAST_LIST_CTL  = APP_CONTEXT + "/ctl/PodcastListCtl";
	
	public String BLOOD_BANK_VIEW      = PAGE_FOLDER + "/BloodBankView.jsp";
	public String BLOOD_BANK_LIST_VIEW = PAGE_FOLDER + "/BloodBankListView.jsp";
	public String BLOOD_BANK_CTL       = APP_CONTEXT + "/ctl/BloodBankCtl";
	public String BLOOD_BANK_LIST_CTL  = APP_CONTEXT + "/ctl/BloodBankListCtl";
	
	public String ATM_VIEW      = PAGE_FOLDER + "/ATMView.jsp";
	public String ATM_LIST_VIEW = PAGE_FOLDER + "/ATMListView.jsp";
	public String ATM_CTL       = APP_CONTEXT + "/ctl/ATMCtl";
	public String ATM_LIST_CTL  = APP_CONTEXT + "/ctl/ATMListCtl";
	
	public String LIBRARY_VIEW      = PAGE_FOLDER + "/LibraryView.jsp";
	public String LIBRARY_LIST_VIEW = PAGE_FOLDER + "/LibraryListView.jsp";
	public String LIBRARY_CTL       = APP_CONTEXT + "/ctl/LibraryCtl";
	public String LIBRARY_LIST_CTL  = APP_CONTEXT + "/ctl/LibraryListCtl";
	
	public String EMPLOYEE_PAYROLL_VIEW      = PAGE_FOLDER + "/EmployeePayrollView.jsp";
	public String EMPLOYEE_PAYROLL_LIST_VIEW = PAGE_FOLDER + "/EmployeePayrollListView.jsp";
	public String EMPLOYEE_PAYROLL_CTL       = APP_CONTEXT + "/ctl/EmployeePayrollCtl";
	public String EMPLOYEE_PAYROLL_LIST_CTL  = APP_CONTEXT + "/ctl/EmployeePayrollListCtl";
	
	public String CLOUD_STORAGE_VIEW      = PAGE_FOLDER + "/CloudStorageView.jsp";
	public String CLOUD_STORAGE_LIST_VIEW = PAGE_FOLDER + "/CloudStorageListView.jsp";
	public String CLOUD_STORAGE_CTL       = APP_CONTEXT + "/ctl/CloudStorageCtl";
	public String CLOUD_STORAGE_LIST_CTL  = APP_CONTEXT + "/ctl/CloudStorageListCtl";
	
	public String POLICY_VIEW      = PAGE_FOLDER + "/PolicyView.jsp";
	public String POLICY_LIST_VIEW = PAGE_FOLDER + "/PolicyListView.jsp";
	public String POLICY_CTL       = APP_CONTEXT + "/ctl/PolicyCtl";
	public String POLICY_LIST_CTL  = APP_CONTEXT + "/ctl/PolicyListCtl";
	
	
	public String VEHICLE_INSURANCE_VIEW      = PAGE_FOLDER + "/VehicleInsuranceView.jsp";
	public String VEHICLE_INSURANCE_LIST_VIEW = PAGE_FOLDER + "/VehicleInsuranceListView.jsp";
	public String VEHICLE_INSURANCE_CTL       = APP_CONTEXT + "/ctl/VehicleInsuranceCtl";
	public String VEHICLE_INSURANCE_LIST_CTL  = APP_CONTEXT + "/ctl/VehicleInsuranceListCtl";
	
	public String MOBILE_STORE_VIEW      = PAGE_FOLDER + "/MobileStoreView.jsp";
	public String MOBILE_STORE_LIST_VIEW = PAGE_FOLDER + "/MobileStoreListView.jsp";
	public String MOBILE_STORE_CTL       = APP_CONTEXT + "/ctl/MobileStoreCtl";
	public String MOBILE_STORE_LIST_CTL  = APP_CONTEXT + "/ctl/MobileStoreListCtl";
	
	
	public String ENERGY_CONSUMPTION_VIEW      = PAGE_FOLDER + "/EnergyConsumptionView.jsp";
	public String ENERGY_CONSUMPTION_LIST_VIEW = PAGE_FOLDER + "/EnergyConsumptionListView.jsp";
	public String ENERGY_CONSUMPTION_CTL       = APP_CONTEXT + "/ctl/EnergyConsumptionCtl";
	public String ENERGY_CONSUMPTION_LIST_CTL  = APP_CONTEXT + "/ctl/EnergyConsumptionListCtl";
	
	
	public String DRONE_DELIVERY_VIEW      = PAGE_FOLDER + "/DroneDeliveryView.jsp";
	public String DRONE_DELIVERY_LIST_VIEW = PAGE_FOLDER + "/DroneDeliveryListView.jsp";
	public String DRONE_DELIVERY_CTL       = APP_CONTEXT + "/ctl/DroneDeliveryCtl";
	public String DRONE_DELIVERY_LIST_CTL  = APP_CONTEXT + "/ctl/DroneDeliveryListCtl";
	 
	
	public String SMART_PARKING_VIEW      = PAGE_FOLDER + "/SmartParkingView.jsp";
	public String SMART_PARKING_LIST_VIEW = PAGE_FOLDER + "/SmartParkingListView.jsp";
	public String SMART_PARKING_CTL       = APP_CONTEXT + "/ctl/SmartParkingCtl";
	public String SMART_PARKING_LIST_CTL  = APP_CONTEXT + "/ctl/SmartParkingListCtl";
	
	
	public String VOICE_COMMAND_VIEW      = PAGE_FOLDER + "/VoiceCommandView.jsp";
	public String VOICE_COMMAND_LIST_VIEW = PAGE_FOLDER + "/VoiceCommandListView.jsp";
	public String VOICE_COMMAND_CTL       = APP_CONTEXT + "/ctl/VoiceCommandCtl";
	public String VOICE_COMMAND_LIST_CTL  = APP_CONTEXT + "/ctl/VoiceCommandListCtl";
	
	
	public String AI_RECOMMENDATION_VIEW      = PAGE_FOLDER + "/AIRecommendationView.jsp";
	public String AI_RECOMMENDATION_LIST_VIEW = PAGE_FOLDER + "/AIRecommendationListView.jsp";
	public String AI_RECOMMENDATION_CTL       = APP_CONTEXT + "/ctl/AIRecommendationCtl";
	public String AI_RECOMMENDATION_LIST_CTL  = APP_CONTEXT + "/ctl/AIRecommendationListCtl";
	 
	 
	// ==== Added: Bank module (fixes "cannot find symbol" errors in BankCtl.java / BankListCtl.java) ====
	public String BANK_VIEW      = PAGE_FOLDER + "/BankView.jsp";
	public String BANK_LIST_VIEW = PAGE_FOLDER + "/BankListView.jsp";
	public String BANK_CTL       = APP_CONTEXT + "/ctl/BankCtl";
	public String BANK_LIST_CTL  = APP_CONTEXT + "/ctl/BankListCtl";

	// ==== Added: Staff Member module (fixes "cannot find symbol" errors in StaffMemberCtl.java / StaffMemberListCtl.java) ====
	public String STAFF_MEMBER_VIEW      = PAGE_FOLDER + "/StaffMemberView.jsp";
	public String STAFF_MEMBER_LIST_VIEW = PAGE_FOLDER + "/StaffMemberListView.jsp";
	public String STAFF_MEMBER_CTL       = APP_CONTEXT + "/ctl/StaffMemberCtl";
	public String STAFF_MEMBER_LIST_CTL  = APP_CONTEXT + "/ctl/StaffMemberListCtl";
	
	
	public String WEATHER_ALERT_VIEW      = PAGE_FOLDER + "/WeatherAlertView.jsp";
	public String WEATHER_ALERT_LIST_VIEW = PAGE_FOLDER + "/WeatherAlertListView.jsp";
	public String WEATHER_ALERT_CTL       = APP_CONTEXT + "/ctl/WeatherAlertCtl";
	public String WEATHER_ALERT_LIST_CTL  = APP_CONTEXT + "/ctl/WeatherAlertListCtl";
	
	

	public String PATIENT_LIST_CTL = APP_CONTEXT + "/ctl/PatientListCtl";

	public String PATIENT_CTL = APP_CONTEXT + "/ctl/PatientCtl";

}