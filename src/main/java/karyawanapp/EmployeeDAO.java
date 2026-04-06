package karyawanapp;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDAO {

    // ── INSERT ────────────────────────────────────────────────────────────────
    public boolean save(Employee emp) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(emp);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("SAVE error: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public boolean update(Employee emp) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(emp);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("UPDATE error: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean delete(String nip) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Employee emp = session.get(Employee.class, nip);
            if (emp != null) session.remove(emp);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("DELETE error: " + e.getMessage());
            return false;
        }
    }

    // ── GET ALL ───────────────────────────────────────────────────────────────
    public List<Employee> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee ORDER BY nip", Employee.class).list();
        } catch (Exception e) {
            System.out.println("GET ALL error: " + e.getMessage());
            return List.of();
        }
    }

    // ── GET BY NIP ────────────────────────────────────────────────────────────
    public Employee getByNip(String nip) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employee.class, nip);
        } catch (Exception e) {
            System.out.println("GET BY NIP error: " + e.getMessage());
            return null;
        }
    }
}
