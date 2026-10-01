package hospital.gui;

import hospital.dao.NurseDAO;
import hospital.models.Nurse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** NursePanel - GUI panel for managing nurses */
public class NursePanel extends JPanel {

    private NurseDAO dao = new NurseDAO();
    private JTextField idField, nameField, phoneField, wardField, salaryField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public NursePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        loadTable("");
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Nurse Information"));
        panel.add(new JLabel("Nurse ID:")); idField = new JTextField(); panel.add(idField);
        panel.add(new JLabel("Name:"));     nameField = new JTextField(); panel.add(nameField);
        panel.add(new JLabel("Phone:"));    phoneField = new JTextField(); panel.add(phoneField);
        panel.add(new JLabel("Ward Assigned:")); wardField = new JTextField(); panel.add(wardField);
        panel.add(new JLabel("Salary:"));   salaryField = new JTextField(); panel.add(salaryField);
        panel.add(new JLabel("")); panel.add(new JLabel(""));
        return panel;
    }

    private JPanel buildTablePanel() {
        tableModel = new DefaultTableModel(new String[]{"ID","Name","Phone","Ward","Salary"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}};
        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        table.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && table.getSelectedRow()>=0){
                int row=table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row,0).toString());
                nameField.setText(tableModel.getValueAt(row,1).toString());
                phoneField.setText(tableModel.getValueAt(row,2).toString());
                wardField.setText(tableModel.getValueAt(row,3).toString());
                salaryField.setText(tableModel.getValueAt(row,4).toString());
            }
        });
        JPanel panel=new JPanel(new BorderLayout(5,5));
        JPanel sp=new JPanel(new FlowLayout(FlowLayout.LEFT));
        sp.add(new JLabel("Search:")); searchField=new JTextField(20); sp.add(searchField);
        JButton sb=new JButton("Search"); sb.addActionListener(e->loadTable(searchField.getText().trim())); sp.add(sb);
        JButton ab=new JButton("Show All"); ab.addActionListener(e->{searchField.setText("");loadTable("");}); sp.add(ab);
        panel.add(sp,BorderLayout.NORTH); panel.add(new JScrollPane(table),BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER,15,5));
        JButton add=new JButton("Add"); JButton upd=new JButton("Update"); JButton del=new JButton("Delete"); JButton clr=new JButton("Clear");
        styleButton(add,new Color(0,150,80)); styleButton(upd,new Color(0,100,200)); styleButton(del,new Color(200,50,50)); styleButton(clr,new Color(130,130,130));
        add.addActionListener(e->{
            Nurse n=buildFromForm();
            if(dao.addNurse(n)){JOptionPane.showMessageDialog(this,"Nurse added!");clearFields();loadTable("");}
        });
        upd.addActionListener(e->{
            if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a nurse.");return;}
            Nurse n=buildFromForm(); n.setNurseId(Integer.parseInt(idField.getText().trim()));
            if(dao.updateNurse(n)){JOptionPane.showMessageDialog(this,"Nurse updated!");clearFields();loadTable("");}
        });
        del.addActionListener(e->{
            if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a nurse.");return;}
            if(JOptionPane.showConfirmDialog(this,"Delete?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                dao.deleteNurse(Integer.parseInt(idField.getText().trim())); clearFields(); loadTable("");
            }
        });
        clr.addActionListener(e->clearFields());
        panel.add(add); panel.add(upd); panel.add(del); panel.add(clr);
        return panel;
    }

    private void loadTable(String name) {
        tableModel.setRowCount(0);
        List<Nurse> list = name.isEmpty() ? dao.getAllNurses() : dao.searchByName(name);
        for(Nurse n:list) tableModel.addRow(new Object[]{n.getNurseId(),n.getName(),n.getPhone(),n.getWardAssigned(),n.getSalary()});
    }

    private Nurse buildFromForm() {
        Nurse n=new Nurse();
        n.setName(nameField.getText().trim()); n.setPhone(phoneField.getText().trim());
        n.setWardAssigned(wardField.getText().trim());
        try{n.setSalary(Double.parseDouble(salaryField.getText().trim()));}catch(NumberFormatException ex){n.setSalary(0);}
        return n;
    }

    private void clearFields(){idField.setText("");nameField.setText("");phoneField.setText("");wardField.setText("");salaryField.setText("");table.clearSelection();}
    private void styleButton(JButton b,Color c){b.setBackground(c);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setFont(new Font("Arial",Font.BOLD,12));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
}
