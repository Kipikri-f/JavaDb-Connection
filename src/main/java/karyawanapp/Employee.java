package karyawanapp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    // ── Fields ────────────────────────────────────────────────────────────────

    @Id
    @Column(name = "nip", length = 20, nullable = false)
    private String nip;

    @Column(name = "nm_kar", length = 100, nullable = false)
    private String name;

    @Column(name = "tem_lhr", length = 100)
    private String placeOfBirth;

    @Column(name = "tgl_lhr")
    private String dateOfBirth;

    @Column(name = "jabatan", length = 100)
    private String position;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Employee() {}

    public Employee(String nip, String name, String placeOfBirth, String dateOfBirth, String position) {
        this.nip         = nip;
        this.name        = name;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.position    = position;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public String getNip()                    { return nip; }
    public void setNip(String nip)            { this.nip = nip; }

    public String getName()                   { return name; }
    public void setName(String name)          { this.name = name; }

    public String getPlaceOfBirth()           { return placeOfBirth; }
    public void setPlaceOfBirth(String v)     { this.placeOfBirth = v; }

    public String getDateOfBirth()            { return dateOfBirth; }
    public void setDateOfBirth(String v)      { this.dateOfBirth = v; }

    public String getPosition()               { return position; }
    public void setPosition(String position)  { this.position = position; }

    @Override
    public String toString() {
        return "Employee{nip='" + nip + "', name='" + name + "', position='" + position + "'}";
    }
}
