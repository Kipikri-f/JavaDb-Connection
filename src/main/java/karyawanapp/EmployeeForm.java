package karyawanapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeForm extends JFrame {

    // ── Form Components ───────────────────────────────────────────────────────
    private JTextField txtNip, txtName, txtPlaceOfBirth;
    private JTextField txtDay, txtMonth, txtYear;
    private JTextField txtPosition;

    private JButton btnInsert, btnUpdate, btnDelete, btnClose;

    private JTable tblEmployee;
    private DefaultTableModel tableModel;

    // ── DAO ───────────────────────────────────────────────────────────────────
    private final EmployeeDAO dao = new EmployeeDAO();

    // ── Theme Colors ──────────────────────────────────────────────────────────
    private static final Color BG_OUTER  = new Color(210, 206, 188);
    private static final Color BG_CARD   = new Color(245, 242, 233);
    private static final Color BORDER_CL = new Color(178, 172, 147);
    private static final Color BTN_BG    = new Color(220, 215, 200);

    // ── Constructor ───────────────────────────────────────────────────────────
    public EmployeeForm() {
        setTitle("Employee Form - CRUD Java Swing + Hibernate");
        setSize(860, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buildUI();
        loadTable();
    }

    // ── Build UI ──────────────────────────────────────────────────────────────
    private void buildUI() {
        // Outer background
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(BG_OUTER);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setContentPane(outerPanel);

        // Main card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CL, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        outerPanel.add(card, BorderLayout.CENTER);

        // ── Input Panel ───────────────────────────────────────────────────────
        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBackground(BG_CARD);
        panelInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // NIP
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelInput.add(makeLabel("NIP"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtNip = makeTextField(20);
        txtNip.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) searchByNip();
            }
        });
        panelInput.add(txtNip, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelInput.add(makeLabel("Name"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtName = makeTextField(24);
        panelInput.add(txtName, gbc);

        // Place of Birth
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelInput.add(makeLabel("Place of Birth"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtPlaceOfBirth = makeTextField(24);
        panelInput.add(txtPlaceOfBirth, gbc);

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelInput.add(makeLabel("Date of Birth"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        datePanel.setBackground(BG_CARD);
        txtDay   = makeTextField(2); txtDay.setColumns(3);
        txtMonth = makeTextField(2); txtMonth.setColumns(3);
        txtYear  = makeTextField(4); txtYear.setColumns(5);
        datePanel.add(txtDay);
        datePanel.add(new JLabel("-"));
        datePanel.add(txtMonth);
        datePanel.add(new JLabel("-"));
        datePanel.add(txtYear);
        panelInput.add(datePanel, gbc);

        // Position
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panelInput.add(makeLabel("Position"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtPosition = makeTextField(24);
        panelInput.add(txtPosition, gbc);

        card.add(panelInput);
        card.add(Box.createRigidArea(new Dimension(0, 12)));

        // ── Button Panel ──────────────────────────────────────────────────────
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        panelBtn.setBackground(BG_CARD);
        panelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnInsert = makeButton("Insert");
        btnUpdate = makeButton("Update");
        btnDelete = makeButton("Delete");
        btnClose  = makeButton("Close");

        panelBtn.add(btnInsert);
        panelBtn.add(btnUpdate);
        panelBtn.add(btnDelete);
        panelBtn.add(btnClose);
        card.add(panelBtn);
        card.add(Box.createRigidArea(new Dimension(0, 16)));

        // ── Table ─────────────────────────────────────────────────────────────
        String[] columns = {"NIP", "Name", "Place of Birth", "Date of Birth", "Position"};
        tableModel = new DefaultTableModel(null, columns) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblEmployee = new JTable(tableModel);
        tblEmployee.setFillsViewportHeight(true);
        tblEmployee.setRowHeight(22);
        tblEmployee.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tblEmployee.setSelectionBackground(new Color(180, 200, 230));
        tblEmployee.setGridColor(BORDER_CL);

        JTableHeader header = tblEmployee.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setBackground(BTN_BG);

        // Click row → fill form
        tblEmployee.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblEmployee.getSelectedRow();
                if (row >= 0) fillFormFromTable(row);
            }
        });

        JScrollPane scroll = new JScrollPane(tblEmployee);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_CL));
        scroll.setPreferredSize(new Dimension(0, 240));

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(BG_CARD);
        tableWrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CL),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        tableWrapper.add(scroll);
        tableWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(tableWrapper);

        // ── Event Listeners ───────────────────────────────────────────────────
        btnInsert.addActionListener(e -> insertData());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());
        btnClose.addActionListener(e -> {
            HibernateUtil.shutdown();
            System.exit(0);
        });
    }

    // ── UI Helpers ────────────────────────────────────────────────────────────
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return lbl;
    }

    private JTextField makeTextField(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return tf;
    }

    private JButton makeButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setBackground(BTN_BG);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(90, 28));
        return btn;
    }

    // ── Load Table ────────────────────────────────────────────────────────────
    private void loadTable() {
        tableModel.setRowCount(0);
        List<Employee> list = dao.getAll();
        for (Employee emp : list) {
            tableModel.addRow(new Object[]{
                emp.getNip(),
                emp.getName(),
                emp.getPlaceOfBirth(),
                formatDateForDisplay(emp.getDateOfBirth()),
                emp.getPosition()
            });
        }
    }

    // ── Fill Form from Table Row ───────────────────────────────────────────────
    private void fillFormFromTable(int row) {
        txtNip.setText(tblEmployee.getValueAt(row, 0).toString());
        txtName.setText(tblEmployee.getValueAt(row, 1).toString());
        txtPlaceOfBirth.setText(tblEmployee.getValueAt(row, 2).toString());

        String dateDisplay = tblEmployee.getValueAt(row, 3).toString(); // dd-MM-yyyy
        String dateDB      = parseDateFromDisplay(dateDisplay);          // yyyy-MM-dd
        if (dateDB != null && dateDB.length() >= 10) {
            txtYear.setText(dateDB.substring(0, 4));
            txtMonth.setText(dateDB.substring(5, 7));
            txtDay.setText(dateDB.substring(8, 10));
        }
        txtPosition.setText(tblEmployee.getValueAt(row, 4).toString());
    }

    // ── CRUD Actions ──────────────────────────────────────────────────────────
    private void insertData() {
        if (!validateForm()) return;
        Employee emp = buildEmployee();
        if (dao.save(emp)) {
            JOptionPane.showMessageDialog(this, "✅ Employee saved successfully!");
            loadTable();
            clearForm();
        } else {
            showError("Failed to save. NIP may already exist.");
        }
    }

    private void updateData() {
        if (txtNip.getText().trim().isEmpty()) {
            showError("NIP cannot be empty for update.");
            return;
        }
        if (!validateForm()) return;
        Employee emp = buildEmployee();
        if (dao.update(emp)) {
            JOptionPane.showMessageDialog(this, "✅ Employee updated successfully!");
            loadTable();
            clearForm();
        } else {
            showError("Failed to update employee.");
        }
    }

    private void deleteData() {
        if (txtNip.getText().trim().isEmpty()) {
            showError("Please select a record to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(
            this, "Are you sure you want to delete this record?", "Confirm Delete",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(txtNip.getText().trim())) {
                JOptionPane.showMessageDialog(this, "🗑️ Employee deleted successfully!");
                loadTable();
                clearForm();
            } else {
                showError("Failed to delete employee.");
            }
        }
    }

    private void searchByNip() {
        String nip = txtNip.getText().trim();
        if (nip.isEmpty()) { loadTable(); return; }

        Employee emp = dao.getByNip(nip);
        if (emp != null) {
            txtName.setText(emp.getName());
            txtPlaceOfBirth.setText(emp.getPlaceOfBirth());
            String date = emp.getDateOfBirth();
            if (date != null && date.length() >= 10) {
                txtYear.setText(date.substring(0, 4));
                txtMonth.setText(date.substring(5, 7));
                txtDay.setText(date.substring(8, 10));
            }
            txtPosition.setText(emp.getPosition());

            // Filter table to show only this record
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{
                emp.getNip(), emp.getName(), emp.getPlaceOfBirth(),
                formatDateForDisplay(emp.getDateOfBirth()), emp.getPosition()
            });
        } else {
            JOptionPane.showMessageDialog(this, "No employee found with NIP '" + nip + "'.");
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private Employee buildEmployee() {
        return new Employee(
            txtNip.getText().trim(),
            txtName.getText().trim(),
            txtPlaceOfBirth.getText().trim(),
            formatDateForDB(),
            txtPosition.getText().trim()
        );
    }

    private boolean validateForm() {
        if (txtNip.getText().trim().isEmpty())      { showError("NIP is required.");      return false; }
        if (txtName.getText().trim().isEmpty())      { showError("Name is required.");     return false; }
        if (txtPosition.getText().trim().isEmpty())  { showError("Position is required."); return false; }
        if (formatDateForDB() == null)               { return false; }
        return true;
    }

    private String formatDateForDB() {
        String year  = txtYear.getText().trim();
        String month = txtMonth.getText().trim();
        String day   = txtDay.getText().trim();
        if (year.isEmpty() || month.isEmpty() || day.isEmpty()) {
            showError("Date of birth is required (DD - MM - YYYY).");
            return null;
        }
        try {
            int y = Integer.parseInt(year), m = Integer.parseInt(month), d = Integer.parseInt(day);
            if (y < 1 || y > 9999) { showError("Year is not valid.");          return null; }
            if (m < 1 || m > 12)   { showError("Month must be between 1-12."); return null; }
            if (d < 1 || d > 31)   { showError("Day must be between 1-31.");   return null; }
            return String.format("%04d-%02d-%02d", y, m, d);
        } catch (NumberFormatException e) {
            showError("Date must be numeric.");
            return null;
        }
    }

    // Format from DB (yyyy-MM-dd) → display (dd-MM-yyyy)
    private String formatDateForDisplay(String date) {
        if (date == null || date.length() < 10) return date == null ? "" : date;
        return date.substring(8, 10) + "-" + date.substring(5, 7) + "-" + date.substring(0, 4);
    }

    // Format from display (dd-MM-yyyy) → DB (yyyy-MM-dd)
    private String parseDateFromDisplay(String date) {
        if (date == null || date.length() < 10) return date;
        return date.substring(6, 10) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2);
    }

    private void clearForm() {
        txtNip.setText(""); txtName.setText(""); txtPlaceOfBirth.setText("");
        txtDay.setText(""); txtMonth.setText(""); txtYear.setText("");
        txtPosition.setText("");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
