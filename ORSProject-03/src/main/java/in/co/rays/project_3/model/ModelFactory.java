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
	
	public PromotionModelInt getPromotionModel() {
	    PromotionModelInt promotionModel = (PromotionModelInt) modelCache.get("promotionModel"); // ✅
	    if (promotionModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            promotionModel = new PromotionModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            promotionModel = new PromotionModelHibImpl();
	        }
	        modelCache.put("promotionModel", promotionModel); 
	    }
	    return promotionModel;
	}
	
	public DonationCampModelInt getDonationCampModel() {
		DonationCampModelInt donationCampModel = (DonationCampModelInt) modelCache.get("donationCampModel"); // ✅
	    if (donationCampModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	        	donationCampModel = new DonationCampModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	        	donationCampModel = new DonationCampModelHibImpl();
	        }
	        modelCache.put("donationCampModel", donationCampModel); 
	    }
	    return donationCampModel;
	}
	
	
	
	public EnrollmentModelInt getEnrollmentModel() {
	    EnrollmentModelInt enrollMentModel = (EnrollmentModelInt) modelCache.get("enrollMentModel"); // ✅ correct key
	    if (enrollMentModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            enrollMentModel = new EnrollmentModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            enrollMentModel = new EnrollmentModelHibImpl(); // ✅ correct class
	        }
	        modelCache.put("enrollMentModel", enrollMentModel);
	    }
	    return enrollMentModel;
	}
	
	
	// ModelFactory.java mein yeh method add karo:

	public SubscriptionPlanModelInt getSubscriptionPlanModel() {
	    SubscriptionPlanModelInt subscriptionPlanModel = (SubscriptionPlanModelInt) modelCache.get("subscriptionPlanModel");
	    if (subscriptionPlanModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            subscriptionPlanModel = new SubscriptionPlanModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            subscriptionPlanModel = new SubscriptionPlanModelHibImpl();
	        }
	        modelCache.put("subscriptionPlanModel", subscriptionPlanModel);
	    }
	    return subscriptionPlanModel;
	}
	
	public PenaltyModelInt getPenaltyModel() {
	    PenaltyModelInt penaltyModel = (PenaltyModelInt) modelCache.get("penaltyModel");
	    if (penaltyModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            penaltyModel = new PenaltyModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            penaltyModel = new PenaltyModelHibImpl();
	        }
	        modelCache.put("penaltyModel", penaltyModel);
	    }
	    return penaltyModel;
	}
	
	public ConsumerModelInt getConsumerModel() {
	    ConsumerModelInt consumerModel = (ConsumerModelInt) modelCache.get("consumerModel");
	    if (consumerModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            consumerModel = new ConsumerModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            consumerModel = new ConsumerModelHibImpl();
	        }
	        modelCache.put("consumerModel", consumerModel);
	    }
	    return consumerModel;
	}
	
	
	public BookIssueModelInt getBookIssueModel() {
	    BookIssueModelInt bookIssueModel = (BookIssueModelInt) modelCache.get("bookIssueModel");
	    if (bookIssueModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            bookIssueModel = new BookIssueModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            bookIssueModel = new BookIssueModelHibImpl();
	        }
	        modelCache.put("bookIssueModel", bookIssueModel);
	    }
	    return bookIssueModel;
	}
	
	public UserProfileModelInt getUserProfileModel() {
		UserProfileModelInt userProfileModel = (UserProfileModelInt) modelCache.get("userProfileModel");
	    if (userProfileModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	        	userProfileModel = new UserProfileModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	        	userProfileModel = new UserProfileModelHibImpl();
	        }
	        modelCache.put("userProfileModel", userProfileModel);
	    }
	    return userProfileModel;
	}
	
	
	public GenderModelInt getGenderModel() {
	    GenderModelInt genderModel = (GenderModelInt) modelCache.get("genderModel");
	    if (genderModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            genderModel = new GenderModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            genderModel = new GenderModelHibImpl();
	        }
	        modelCache.put("genderModel", genderModel);
	    }
	    return genderModel;
	}
	
	public SmartLightModelInt getSmartLightModel() {
	    SmartLightModelInt smartLightModel = (SmartLightModelInt) modelCache.get("smartLightModel");
	    if (smartLightModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            smartLightModel = new SmartLightModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            smartLightModel = new SmartLightModelHibImpl();
	        }
	        modelCache.put("smartLightModel", smartLightModel);
	    }
	    return smartLightModel;
	}
	
	public AchievementModelInt getAchievementModel() {
	    AchievementModelInt achievementModel = (AchievementModelInt) modelCache.get("achievementModel");
	    if (achievementModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            achievementModel = new AchievementModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            achievementModel = new AchievementModelHibImpl();
	        }
	        modelCache.put("achievementModel", achievementModel);
	    }
	    return achievementModel;
	}
	
	
	public PodcastModelInt getPodcastModel() {
	    PodcastModelInt podcastModel = (PodcastModelInt) modelCache.get("podcastModel");
	    if (podcastModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            podcastModel = new PodcastModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            podcastModel = new PodcastModelHibImpl();
	        }
	        modelCache.put("podcastModel", podcastModel);
	    }
	    return podcastModel;
	}
	
	public BugTrackingModelInt getBugTrackingModel() {
	    BugTrackingModelInt bugTrackingModel = (BugTrackingModelInt) modelCache.get("bugTrackingModel");
	    if (bugTrackingModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            bugTrackingModel = new BugTrackingModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            bugTrackingModel = new BugTrackingModelHibImpl();
	        }
	        modelCache.put("bugTrackingModel", bugTrackingModel);
	    }
	    return bugTrackingModel;
	}
	
	public BloodBankModelInt getBloodBankModel() {
	    BloodBankModelInt bloodBankModel = (BloodBankModelInt) modelCache.get("bloodBankModel");
	    if (bloodBankModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            bloodBankModel = new BloodBankModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            bloodBankModel = new BloodBankModelHibImpl();
	        }
	        modelCache.put("bloodBankModel", bloodBankModel);
	    }
	    return bloodBankModel;
	}
	
	public ATMModelInt getATMModel() {
	    ATMModelInt atmModel = (ATMModelInt) modelCache.get("atmModel");
	    if (atmModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            atmModel = new ATMModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            atmModel = new ATMModelHibImpl();
	        }
	        modelCache.put("atmModel", atmModel);
	    }
	    return atmModel;
	}
	
	public LibraryModelInt getLibraryModel() {
	    LibraryModelInt libraryModel = (LibraryModelInt) modelCache.get("libraryModel");
	    if (libraryModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            libraryModel = new LibraryModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            libraryModel = new LibraryModelHibImpl();
	        }
	        modelCache.put("libraryModel", libraryModel);
	    }
	    return libraryModel;
	}
	
	public EmployeePayrollModelInt getEmployeePayrollModel() {
		EmployeePayrollModelInt employeePayrollModel = (EmployeePayrollModelInt) modelCache.get("employeePayrollModel");
	    if (employeePayrollModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	        	employeePayrollModel = new EmployeePayrollModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	        	employeePayrollModel = new EmployeePayrollModelHibImpl();
	        }
	        modelCache.put("employeePayrollModel", employeePayrollModel);
	    }
	    return employeePayrollModel;
	}
	
	public CloudStorageModelInt getCloudStorageModel() {
	    CloudStorageModelInt cloudStorageModel = (CloudStorageModelInt) modelCache.get("cloudStorageModel");
	    if (cloudStorageModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            cloudStorageModel = new CloudStorageModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            cloudStorageModel = new CloudStorageModelHibImpl();
	        }
	        modelCache.put("cloudStorageModel", cloudStorageModel);
	    }
	    return cloudStorageModel;
	}
	
	public PolicyModelInt getPolicyModel() {
	    PolicyModelInt policyModel = (PolicyModelInt) modelCache.get("policyModel");
	    if (policyModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            policyModel = new PolicyModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            policyModel = new PolicyModelHibImpl();
	        }
	        modelCache.put("policyModel", policyModel);
	    }
	    return policyModel;
	}
	
	
	public VehicleInsuranceModelInt getVehicleInsuranceModel() {
	    VehicleInsuranceModelInt vehicleInsuranceModel = (VehicleInsuranceModelInt) modelCache.get("vehicleInsuranceModel");
	    if (vehicleInsuranceModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            vehicleInsuranceModel = new VehicleInsuranceModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            vehicleInsuranceModel = new VehicleInsuranceModelHibImpl();
	        }
	        modelCache.put("vehicleInsuranceModel", vehicleInsuranceModel);
	    }
	    return vehicleInsuranceModel;
	}
	
	public MobileStoreModelInt getMobileStoreModel() {
	    MobileStoreModelInt mobileStoreModel = (MobileStoreModelInt) modelCache.get("mobileStoreModel");
	    if (mobileStoreModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            mobileStoreModel = new MobileStoreModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            mobileStoreModel = new MobileStoreModelHibImpl();
	        }
	        modelCache.put("mobileStoreModel", mobileStoreModel);
	    }
	    return mobileStoreModel;
	}
	
	public EnergyConsumptionModelInt getEnergyConsumptionModel() {
	    EnergyConsumptionModelInt energyConsumptionModel = (EnergyConsumptionModelInt) modelCache.get("energyConsumptionModel");
	    if (energyConsumptionModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            energyConsumptionModel = new EnergyConsumptionModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            energyConsumptionModel = new EnergyConsumptionModelHibImpl();
	        }
	        modelCache.put("energyConsumptionModel", energyConsumptionModel);
	    }
	    return energyConsumptionModel;
	}
	
	public DroneDeliveryModelInt getDroneDeliveryModel() {
	    DroneDeliveryModelInt droneDeliveryModel = (DroneDeliveryModelInt) modelCache.get("droneDeliveryModel");
	    if (droneDeliveryModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            droneDeliveryModel = new DroneDeliveryModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            droneDeliveryModel = new DroneDeliveryModelHibImpl();
	        }
	        modelCache.put("droneDeliveryModel", droneDeliveryModel);
	    }
	    return droneDeliveryModel;
	}

	
	public SmartParkingModelInt getSmartParkingModel() {
	    SmartParkingModelInt smartParkingModel = (SmartParkingModelInt) modelCache.get("smartParkingModel");
	    if (smartParkingModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            smartParkingModel = new SmartParkingModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            smartParkingModel = new SmartParkingModelHibImpl();
	        }
	        modelCache.put("smartParkingModel", smartParkingModel);
	    }
	    return smartParkingModel;
	}
	
	
	public VoiceCommandModelInt getVoiceCommandModel() {
	    VoiceCommandModelInt voiceCommandModel = (VoiceCommandModelInt) modelCache.get("voiceCommandModel");
	    if (voiceCommandModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            voiceCommandModel = new VoiceCommandModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            voiceCommandModel = new VoiceCommandModelHibImpl();
	        }
	        modelCache.put("voiceCommandModel", voiceCommandModel);
	    }
	    return voiceCommandModel;
	}
	
	
	public AIRecommendationModelInt getAIRecommendationModel() {
	    AIRecommendationModelInt aiRecommendationModel = (AIRecommendationModelInt) modelCache.get("aiRecommendationModel");
	    if (aiRecommendationModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            aiRecommendationModel = new AIRecommendationModelHibImpl();
	        }
	        if ("JDBC".equals(DATABASE)) {
	            aiRecommendationModel = new AIRecommendationModelHibImpl();
	        }
	        modelCache.put("aiRecommendationModel", aiRecommendationModel);
	    }
	    return aiRecommendationModel;
	}


	 
	}


