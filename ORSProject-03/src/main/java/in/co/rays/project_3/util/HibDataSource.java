package in.co.rays.project_3.util;

import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import in.co.rays.project_3.exception.ApplicationException;

/**
 * Hibernate DataSource is provides the object of session factory and session
 * 
 * 
 * @author Sejal Chourasiya
 *
 */
public class HibDataSource {
	private static SessionFactory sessionFactory = null;

	public static SessionFactory getSessionFactory() throws ApplicationException {

		if (sessionFactory == null) {

			ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");

			String jdbcUrl = System.getenv("DATABASE_URL");
			if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
				jdbcUrl = rb.getString("url");
			}
			try {
				sessionFactory = new Configuration().configure().setProperty("hibernate.connection.url", jdbcUrl)
						.buildSessionFactory();
			} catch (Exception e) {

				throw new RuntimeException("Database Server is down. Please try after some time...");
			}
		}
		return sessionFactory;
	}

	public static Session getSession() throws HibernateException, ApplicationException {
		try {
			Session session = getSessionFactory().openSession();
			return session;
		} catch (Exception e) {

			throw new RuntimeException("Database Server is down. Please try after some time...");
		}

	}

	public static void closeSession(Session session) {

		if (session != null) {
			session.close();
		}
	}

	public static void handleException(Exception e) throws ApplicationException {

		// DB down / connection issue
		throw new RuntimeException("Database Server is down. Please try after some time...");
	}
}