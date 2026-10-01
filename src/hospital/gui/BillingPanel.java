package hospital.gui;

import hospital.dao.BillingDAO;
import hospital.dao.PatientDAO;
import hospital.models.Bill;
import hospital.models.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** BillingPanel - GUI panel for managing patient billing */
public class BillingPanel extends JPanel {

    private BillingDAO  dao    = new BillingDAO();
    private PatientDAO  patDao = new PatientDAO();

    private JTextField idField, totalField, paidField, dateField, descField;
    private JComboBox<String> patientCombo, statusCombo;
    private JTable table;
    private DefaultTableModel tableModel;

    public BillingPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        loadTable();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3,4,8,8));
        panel.setBorder(BorderFactory.createTitledBorder("Billing Information"));
        panel.add(new JLabel("Bill ID:"));   idField=new JTextField(); panel.add(idField);
        panel.add(new JLabel("Patient:"));   patientCombo=new JComboBox<>(); loadPatientCombo(); panel.add(patientCombo);
        panel.add(new JLabel("Total Amount:")); totalField=new JTextField(); panel.add(totalField);
        panel.add(new JLabel("Paid Amount:"));  paidField=new JTextField(); panel.add(paidField);
        panel.add(new JLabel("Status:"));
        statusCombo=new JComboBox<>(new String[]{"Unpaid","Paid","Partial"}); panel.add(statusCombo);
        panel.add(new JLabel("Date (YYYY-MM-DD):")); dateField=new JTextField(); panel.add(dateField);
        panel.add(new JLabel("Description:")); descField=new JTextField(); panel.add(descField);
        panel.add(new JLabel("")); panel.add(new JLabel(""));
        return panel;
    }

    private void loadPatientCombo() {
        patientCombo.removeAllItems();
        for(Patient p:patDao.getAllPatients()) patientCombo.addItem(p.getPatientId()+" - "+p.getName());
    }

    private int extractId(String val) {
        if(val==null||val.isEmpty()) return 0;
        return Integer.parseInt(val.split(" - ")[0].trim());
    }

    private JPanel buildTablePanel() {
        tableModel=new DefaultTableModel(new String[]{"Bill ID","Patient ID","Total","Paid","Status","Date","Description"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}};
        table=new JTable(tableModel);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        table.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0){
                int row=table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row,0).toString());
                totalField.setText(tableModel.getValueAt(row,2).toString());
                paidField.setText(tableModel.getValueAt(row,3).toString());
                statusCombo.setSelectedItem(tableModel.getValueAt(row,4).toString());
                dateField.setText(tableModel.getValueAt(row,5).toString());
                descField.setText(tableModel.getValueAt(row,6).toString());
            }
        });
        JPanel panel=new JPanel(new BorderLayout(5,5));
        JPanel sp=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton ref=new JButton("Refresh"); ref.addActionListener(e->{loadTable();loadPatientCombo();}); sp.add(ref);
        panel.add(sp,BorderLayout.NORTH); panel.add(new JScrollPane(table),BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER,15,5));
        JButton add=new JButton("Add Bill"); JButton upd=new JButton("Update Bill"); JButton del=new JButton("Delete Bill"); JButton clr=new JButton("Clear");
        styleButton(add,new Color(0,150,80)); styleButton(upd,new Color(0,100,200)); styleButton(del,new Color(200,50,50)); styleButton(clr,new Color(130,130,130));
        add.addActionListener(e->{Bill b=build();if(dao.addBill(b)){JOptionPane.showMessageDialog(this,"Bill added!");clearFields();loadTable();}});
        upd.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a bill.");return;}Bill b=build();b.setBillId(Integer.parseInt(idField.getText().trim()));if(dao.updateBill(b)){JOptionPane.showMessageDialog(this,"Updated!");clearFields();loadTable();}});
        del.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a bill.");return;}if(JOptionPane.showConfirmDialog(this,"Delete?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){dao.deleteBill(Integer.parseInt(idField.getText().trim()));clearFields();loadTable();}});
        clr.addActionListener(e->clearFields());
        panel.add(add); panel.add(upd); panel.add(del); panel.add(clr);
        return panel;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for(Bill b:dao.getAllBills())
            tableModel.addRow(new Object[]{b.getBillId(),b.getPatientId(),b.getTotalAmount(),b.getPaidAmount(),b.getPaymentStatus(),b.getBillingDate(),b.getDescription()});
    }

    private Bill build() {
        Bill b=new Bill();
        b.setPatientId(extractId(patientCombo.getSelectedItem()!=null?patientCombo.getSelectedItem().toString():""));
        try{b.setTotalAmount(Double.parseDouble(totalField.getText().trim()));}catch(NumberFormatException ex){b.setTotalAmount(0);}
        try{b.setPaidAmount(Double.parseDouble(paidField.getText().trim()));}catch(NumberFormatException ex){b.setPaidAmount(0);}
        b.setPaymentStatus(statusCombo.getSelectedItem().toString());
        b.setBillingDate(dateField.getText().trim()); b.setDescription(descField.getText().trim());
        return b;
    }

    private void clearFields(){idField.setText("");totalField.setText("");paidField.setText("");dateField.setText("");descField.setText("");statusCombo.setSelectedIndex(0);table.clearSelection();}
    private void styleButton(JButton b,Color c){b.setBackground(c);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setFont(new Font("Arial",Font.BOLD,12));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
}
