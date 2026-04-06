# Employee CRUD - Java Swing + Hibernate + MySQL

Desktop CRUD application for employee data management using **Java Swing** (GUI), **Hibernate ORM** (database layer), and **MySQL via XAMPP**.

---

## Project Structure

```
karyawan-hibernate/
├── pom.xml                              ← Maven build + dependencies
├── karyawan_db.sql                      ← SQL setup + dummy data
├── README.md
└── src/main/
    ├── java/karyawanapp/
    │   ├── Main.java                    ← Entry point
    │   ├── Employee.java                ← Entity class (@Entity)
    │   ├── HibernateUtil.java           ← SessionFactory singleton
    │   ├── EmployeeDAO.java             ← CRUD via Hibernate Session
    │   └── EmployeeForm.java            ← Swing GUI
    └── resources/
        └── hibernate.cfg.xml            ← Hibernate + DB config
```

---

## How Hibernate Works Here

```
EmployeeForm (GUI)
    └── EmployeeDAO       ← no SQL, uses session.persist / session.merge / session.remove
          └── HibernateUtil (SessionFactory)
                └── hibernate.cfg.xml   ← DB connection + entity mapping
                      └── Employee.java (@Entity) ← mapped to table `karyawan`
```

---

## Setup

### 1. Start XAMPP MySQL
Open XAMPP Control Panel → Start **MySQL**

### 2. Run SQL file
Open **phpMyAdmin** (`localhost/phpmyadmin`) → run `karyawan_db.sql`

### 3. Run the app
```bash
mvn compile exec:java
```

---

## Features

| Feature | Description |
|---------|-------------|
| **Insert** | Add new employee record |
| **Update** | Edit existing employee by NIP |
| **Delete** | Remove employee record |
| **Search** | Press Enter on NIP field to search |
| **Click Row** | Click table row to auto-fill form |

---

## Tech Stack

- Java 11+
- Java Swing (GUI)
- Hibernate ORM 6.4
- MySQL 8 via XAMPP
- Maven
