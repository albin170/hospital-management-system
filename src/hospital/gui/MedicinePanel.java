package hospital.gui;

import hospital.dao.MedicineDAO;
import hospital.models.Medicine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** MedicinePanel - GUI panel for managing medicines */
public class MedicinePanel extends JPanel {

    private MedicineDAO dao = new MedicineDAO();
    private JTextField idField, nameField, categoryField, quantityField, priceField, expiryField, mfrField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public MedicinePanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        loadTable("");
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2,4,8,8));
        panel.setBorder(BorderFactory.createTitledBorder("Medicine Information"));
        panel.add(new JLabel("Medicine ID:"));    idField=new JTextField(); panel.add(idField);
        panel.add(new JLabel("Name:"));           nameField=new JTextField(); panel.add(nameField);
        panel.add(new JLabel("Category:"));       categoryField=new JTextField(); panel.add(categoryField);
        panel.add(new JLabel("Quantity:"));        quantityField=new JTextField(); panel.add(quantityField);
        panel.add(new JLabel("Price:"));          priceField=new JTextField(); panel.add(priceField);
        panel.add(new JLabel("Expiry (YYYY-MM-DD):")); expiryField=new JTextField(); panel.add(expiryField);
        panel.add(new JLabel("Manufacturer:"));   mfrField=new JTextField(); panel.add(mfrField);
        return panel;
    }

    private JPanel buildTablePanel() {
        tableModel=new DefaultTableModel(new String[]{"ID","Name","Category","Qty","Price","Expiry","Manufacturer"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}};
        table=new JTable(tableModel);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        table.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0){
                int row=table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row,0).toString());
                nameField.setText(tableModel.getValueAt(row,1).toString());
                categoryField.setText(tableModel.getValueAt(row,2).toString());
                quantityField.setText(tableModel.getValueAt(row,3).toString());
                priceField.setText(tableModel.getValueAt(row,4).toString());
                expiryField.setText(tableModel.getValueAt(row,5).toString());
                mfrField.setText(tableModel.getValueAt(row,6).toString());
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
        add.addActionListener(e->{Medicine m=build();if(dao.addMedicine(m)){JOptionPane.showMessageDialog(this,"Medicine added!");clearFields();loadTable("");}});
        upd.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a medicine.");return;}Medicine m=build();m.setMedicineId(Integer.parseInt(idField.getText().trim()));if(dao.updateMedicine(m)){JOptionPane.showMessageDialog(this,"Updated!");clearFields();loadTable("");}});
        del.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a medicine.");return;}if(JOptionPane.showConfirmDialog(this,"Delete?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){dao.deleteMedicine(Integer.parseInt(idField.getText().trim()));clearFields();loadTable("");}});
        clr.addActionListener(e->clearFields());
        panel.add(add); panel.add(upd); panel.add(del); panel.add(clr);
        return panel;
    }

    private void loadTable(String name) {
        tableModel.setRowCount(0);
        List<Medicine> list = name.isEmpty() ? dao.getAllMedicines() : dao.searchByName(name);
        for(Medicine m:list)
            tableModel.addRow(new Object[]{m.getMedicineId(),m.getName(),m.getCategory(),m.getQuantity(),m.getPrice(),m.getExpiryDate(),m.getManufacturer()});
    }

    private Medicine build() {
        Medicine m=new Medicine();
        m.setName(nameField.getText().trim()); m.setCategory(categoryField.getText().trim());
        try{m.setQuantity(Integer.parseInt(quantityField.getText().trim()));}catch(NumberFormatException ex){m.setQuantity(0);}
        try{m.setPrice(Double.parseDouble(priceField.getText().trim()));}catch(NumberFormatException ex){m.setPrice(0);}
        m.setExpiryDate(expiryField.getText().trim()); m.setManufacturer(mfrField.getText().trim());
        return m;
    }

    private void clearFields(){idField.setText("");nameField.setText("");categoryField.setText("");quantityField.setText("");priceField.setText("");expiryField.setText("");mfrField.setText("");table.clearSelection();}
    private void styleButton(JButton b,Color c){b.setBackground(c);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setFont(new Font("Arial",Font.BOLD,12));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
}
