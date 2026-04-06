package karyawanapp;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    // ── Get or build SessionFactory (singleton) ───────────────────────────────
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration().configure(); // reads hibernate.cfg.xml
                sessionFactory = config.buildSessionFactory();
                System.out.println("✅ Hibernate SessionFactory created!");
            } catch (Exception ex) {
                System.err.println("❌ Failed to create SessionFactory: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return sessionFactory;
    }

    // ── Close SessionFactory on app shutdown ──────────────────────────────────
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
