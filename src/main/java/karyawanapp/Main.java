package karyawanapp;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeeForm().setVisible(true);
        });
    }
}
