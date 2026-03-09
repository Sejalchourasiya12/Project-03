package in.co.rays.project_3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author Sejal Chourasiya
 * 
 *
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	public ProductModelInt getProductModel() {
		ProductModelInt productModel = (ProductModelInt) modelCache.get("productModel");
		if (productModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				productModel = new ProductModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				productModel = new ProductModelHibImp();
			}
			modelCache.put("productModel", productModel);
		}
		return productModel;
	}

	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}

	public StaffMemberModelInt getStaffMemberModel() {

		StaffMemberModelInt staffMemberModel =
				(StaffMemberModelInt) modelCache.get("staffMemberModel");

		if (staffMemberModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				staffMemberModel = new StaffMemberModelHibImp();
			}

			if ("JDBC".equals(DATABASE)) {
				staffMemberModel = new StaffMemberModelJDBCImpl();
			}

			modelCache.put("staffMemberModel", staffMemberModel);
		}

		return staffMemberModel;
	}

	public BankModelInt getBankModel() {
		BankModelInt bankModel =
				(BankModelInt) modelCache.get("bankModelInt");

		
		if (bankModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				bankModel = new BankModelHibImp();
			}

			if ("JDBC".equals(DATABASE)) {
				bankModel = new BankModelJDBCImpl();
			}

			modelCache.put("bankModel", bankModel);
		}

		return bankModel;
	}
	
	public PatientModelInt getPatientModel() {

	    PatientModelInt patientModel =
	            (PatientModelInt) modelCache.get("patientModel");

	    if (patientModel == null) {

	        if ("Hibernate".equals(DATABASE)) {
	            patientModel = new PatientModelHibImpl();
	        }

	        if ("JDBC".equals(DATABASE)) {
	            patientModel = new PatientModelJDBCImpl();
	        }

	        modelCache.put("patientModel", patientModel);
	    }

	    return patientModel;
	}
	
	public QueueModelInt getQueueModel() {

	    QueueModelInt queueModel =
	            (QueueModelInt) modelCache.get("queueModel");

	    if (queueModel == null) {

	        if ("Hibernate".equals(DATABASE)) {
	            queueModel = new QueueModelHibImpl();
	        } else if ("JDBC".equals(DATABASE)) {
	            queueModel = new QueueModelJDBCImpl();
	        }

	        modelCache.put("queueModel", queueModel);
	    }

	    return queueModel;
	}
	
	public SessionModelInt getSessionModel() {

		SessionModelInt sessionModel = (SessionModelInt) modelCache.get("sessionModel");

		if (sessionModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				sessionModel = new SessionModelHibImp();
			}

			if ("JDBC".equals(DATABASE)) {
				sessionModel = new SessionModelHibImp(); // change if JDBC impl created
			}

			modelCache.put("sessionModel", sessionModel);
		}

		return sessionModel;
	}

	
	public LeaveModelInt getLeaveModel() {

	    LeaveModelInt leaveModel = (LeaveModelInt) modelCache.get("leaveModel");

	    if (leaveModel == null) {

	        if ("Hibernate".equals(DATABASE)) {
	            leaveModel = new LeaveModelHibImp();
	        }

	        if ("JDBC".equals(DATABASE)) {
	            leaveModel = new LeaveModelHibImp(); // change if JDBC impl created
	        }

	        modelCache.put("leaveModel", leaveModel);
	    }

	    return leaveModel;
	}
	
	public AnnouncementModelInt getAnnouncementModel() {

		AnnouncementModelInt AnnouncementModel = (AnnouncementModelInt) modelCache.get("AnnouncementModel");

	    if (AnnouncementModel == null) {

	        if ("Hibernate".equals(DATABASE)) {
	        	AnnouncementModel = new AnnouncementModelHibImp();
	        }

	        if ("JDBC".equals(DATABASE)) {
	        	AnnouncementModel = new AnnouncementModelHibImp(); // change if JDBC impl created
	        }

	        modelCache.put("AnnouncementModel", AnnouncementModel);
	    }

	    return AnnouncementModel;
	}
	
	public CandidateModelInt getCandidateModel() {
		CandidateModelInt candidateModel = (CandidateModelInt) modelCache.get("productModel");
		if (candidateModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				candidateModel = new CandidateModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				candidateModel = new CandidateModelHibImpl();
			}
			modelCache.put("candidateModel", candidateModel);
		}
		return candidateModel;
	}
	

	public ContactModelInt getContactModel() {
		ContactModelInt contactModel = (ContactModelInt) modelCache.get("ContactModel");
		if (contactModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				contactModel = new ContactModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				contactModel = new ContactModelHibImpl();
			}
			modelCache.put("contactModel", contactModel);
		}
		return contactModel;
	}
	
	public FoodDeliveryModelInt getFoodDeliveryModel() {
		FoodDeliveryModelInt foodDeliveryModel = (FoodDeliveryModelInt) modelCache.get("FoodDeliveryModel");
		if (foodDeliveryModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				foodDeliveryModel = new FoodDeliveryModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				foodDeliveryModel = new FoodDeliveryModelHibImpl();
				
			}
			modelCache.put("foodDeliveryModel", foodDeliveryModel);
		}
		return foodDeliveryModel;
	}

	public AssetModelInt getAssetModel() {
		AssetModelInt assetModel = (AssetModelInt) modelCache.get("AssetModel");
		if (assetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				assetModel = new AssetModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				assetModel = new AssetModelHibImpl();
				
			}
			modelCache.put("foodDeliveryModel", assetModel);
		}
		return assetModel;
	}
	public ReviewModelInt getReviewModel() {
		ReviewModelInt reviewModel = (ReviewModelInt) modelCache.get("ReviewModel");
		if (reviewModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				reviewModel = new ReviewModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				reviewModel = new ReviewModelHibImpl();
				
			}
			modelCache.put("review", reviewModel);
		}
		return reviewModel;
	}
	public TrackingModelInt getTrackingModel() {
		TrackingModelInt trackingModel = (TrackingModelInt) modelCache.get("TrackingModel");
		if (trackingModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				trackingModel = new TrackingModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				trackingModel = new TrackingModelHibImpl();
				
			}
			modelCache.put("tracking", trackingModel);
		}
		return trackingModel;
	}
	
	public DeliveryTrackingModelInt getDeliveryTrackingModel() {
		DeliveryTrackingModelInt deliverytrackingModel = (DeliveryTrackingModelInt) modelCache.get("DeliveryTrackingModel");
		if (deliverytrackingModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				deliverytrackingModel = new DeliveryTrackingModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				deliverytrackingModel = new DeliveryTrackingModelHibImpl();
				
			}
			modelCache.put("deliverytracking", deliverytrackingModel);
		}
		return deliverytrackingModel;
	}

	 
	}


