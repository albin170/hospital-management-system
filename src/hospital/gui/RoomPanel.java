package hospital.gui;

import hospital.dao.RoomDAO;
import hospital.models.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** RoomPanel - GUI panel for managing rooms and wards */
public class RoomPanel extends JPanel {

    private RoomDAO dao = new RoomDAO();
    private JTextField idField, roomNumField, wardField, priceField;
    private JComboBox<String> roomTypeCombo, statusCombo;
    private JTable table;
    private DefaultTableModel tableModel;

    public RoomPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        loadTable();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2,4,8,8));
        panel.setBorder(BorderFactory.createTitledBorder("Room / Ward Information"));
        panel.add(new JLabel("Room ID:"));      idField=new JTextField(); panel.add(idField);
        panel.add(new JLabel("Room Number:"));  roomNumField=new JTextField(); panel.add(roomNumField);
        panel.add(new JLabel("Room Type:"));    roomTypeCombo=new JComboBox<>(new String[]{"General","Private","ICU","Emergency","Semi-Private"}); panel.add(roomTypeCombo);
        panel.add(new JLabel("Ward:"));         wardField=new JTextField(); panel.add(wardField);
        panel.add(new JLabel("Status:"));       statusCombo=new JComboBox<>(new String[]{"Available","Occupied"}); panel.add(statusCombo);
        panel.add(new JLabel("Price/Day:"));    priceField=new JTextField(); panel.add(priceField);
        panel.add(new JLabel("")); panel.add(new JLabel(""));
        return panel;
    }

    private JPanel buildTablePanel() {
        tableModel=new DefaultTableModel(new String[]{"ID","Room No","Type","Ward","Status","Price/Day"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}};
        table=new JTable(tableModel);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        table.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0){
                int row=table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row,0).toString());
                roomNumField.setText(tableModel.getValueAt(row,1).toString());
                roomTypeCombo.setSelectedItem(tableModel.getValueAt(row,2).toString());
                wardField.setText(tableModel.getValueAt(row,3).toString());
                statusCombo.setSelectedItem(tableModel.getValueAt(row,4).toString());
                priceField.setText(tableModel.getValueAt(row,5).toString());
            }
        });
        JPanel panel=new JPanel(new BorderLayout(5,5));
        JPanel sp=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton showAvail=new JButton("Show Available Only");
        showAvail.addActionListener(e->{
            tableModel.setRowCount(0);
            for(Room r:dao.getAvailableRooms())
                tableModel.addRow(new Object[]{r.getRoomId(),r.getRoomNumber(),r.getRoomType(),r.getWard(),r.getStatus(),r.getPricePerDay()});
        });
        JButton showAll=new JButton("Show All");
        showAll.addActionListener(e->loadTable());
        sp.add(showAvail); sp.add(showAll);
        panel.add(sp,BorderLayout.NORTH); panel.add(new JScrollPane(table),BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER,15,5));
        JButton add=new JButton("Add Room"); JButton upd=new JButton("Update Room"); JButton del=new JButton("Delete Room"); JButton clr=new JButton("Clear");
        styleButton(add,new Color(0,150,80)); styleButton(upd,new Color(0,100,200)); styleButton(del,new Color(200,50,50)); styleButton(clr,new Color(130,130,130));
        add.addActionListener(e->{Room r=build();if(dao.addRoom(r)){JOptionPane.showMessageDialog(this,"Room added!");clearFields();loadTable();}});
        upd.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a room.");return;}Room r=build();r.setRoomId(Integer.parseInt(idField.getText().trim()));if(dao.updateRoom(r)){JOptionPane.showMessageDialog(this,"Updated!");clearFields();loadTable();}});
        del.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a room.");return;}if(JOptionPane.showConfirmDialog(this,"Delete?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){dao.deleteRoom(Integer.parseInt(idField.getText().trim()));clearFields();loadTable();}});
        clr.addActionListener(e->clearFields());
        panel.add(add); panel.add(upd); panel.add(del); panel.add(clr);
        return panel;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for(Room r:dao.getAllRooms())
            tableModel.addRow(new Object[]{r.getRoomId(),r.getRoomNumber(),r.getRoomType(),r.getWard(),r.getStatus(),r.getPricePerDay()});
    }

    private Room build() {
        Room r=new Room();
        r.setRoomNumber(roomNumField.getText().trim());
        r.setRoomType(roomTypeCombo.getSelectedItem().toString());
        r.setWard(wardField.getText().trim());
        r.setStatus(statusCombo.getSelectedItem().toString());
        try{r.setPricePerDay(Double.parseDouble(priceField.getText().trim()));}catch(NumberFormatException ex){r.setPricePerDay(0);}
        return r;
    }

    private void clearFields(){idField.setText("");roomNumField.setText("");wardField.setText("");priceField.setText("");roomTypeCombo.setSelectedIndex(0);statusCombo.setSelectedIndex(0);table.clearSelection();}
    private void styleButton(JButton b,Color c){b.setBackground(c);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setFont(new Font("Arial",Font.BOLD,12));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
}
