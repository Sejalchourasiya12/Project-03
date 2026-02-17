package in.co.rays.project_3.util;

import java.util.ResourceBundle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibDataSource {

    private static SessionFactory sessionFactory = null;

    // Get SessionFactory safely
    public static SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {
            try {
                ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");

                String jdbcUrl = System.getenv("DATABASE_URL");
                if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
                    jdbcUrl = rb.getString("url");
                }
                System.out.println("Hibernate using DB URL = " + jdbcUrl);

                sessionFactory = new Configuration()
                        .configure()
                        .setProperty("hibernate.connection.url", jdbcUrl)
                        .buildSessionFactory();

            } catch (HibernateException e) {
                System.out.println("Error creating Hibernate SessionFactory: " + e.getMessage());
                throw e;
            } catch (Exception e) {
                System.out.println("Unexpected error initializing Hibernate: " + e.getMessage());
                throw new HibernateException(e);
            }
        }
        return sessionFactory;
    }

    // Get Session safely
    public static Session getSession() {
        try {
            Session session = getSessionFactory().openSession();
            if (session == null) {
                throw new HibernateException("Hibernate SessionFactory returned null session");
            }
            return session;
        } catch (HibernateException e) {
            System.out.println("Failed to open Hibernate session: " + e.getMessage());
            return null;  // caller must check for null
        }
    }

    // Close session safely
    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
