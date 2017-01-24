/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
 */
package facultylocator;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Pranav Phadke
 */
public class FacultySelector extends JPanel implements ActionListener,MouseListener{
    BoxLayout selectorLay=new BoxLayout(this,BoxLayout.Y_AXIS);
    static List<String> facultyList;//={"--Pick One--","Faculty Selector","Faculty Info","Map View"};// Change this to ArrayList
    JComboBox facultySelectorBox;
    public static String facultyName;
    private static CardLayout clUsageFacSel;
//    private static int i;
    MapView mapView;
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
//    public static void setFacultyList(){
//
//    }
           
    @Override
    public void actionPerformed(ActionEvent ae){
        JComboBox cb = (JComboBox)ae.getSource();
        facultyName = (String)cb.getSelectedItem();
        if (!facultyName.equals(facultyList.get(0))){
            MainFrame.middleContent.setFacInfo(facultyName);
            MainFrame.middleContent.add(MainFrame.middleContent.getFacInfo(),"FacultyInfo");
//            System.out.println("Name in facSel "+MainFrame.middleContent.facInfo.getFacName());
//            System.out.println("Importing CardLayout");
            clUsageFacSel=(CardLayout)(MainFrame.middleContentLay);
//            System.out.println("Imported CardLayout");
            clUsageFacSel.show(MainFrame.middleContent,"FacultyInfo");
//            System.out.println("Set card complete");
        }
    }
    public void mousePressed(MouseEvent me){
        clUsageFacSel=(CardLayout)(MainFrame.middleContentLay);
//      System.out.println("Imported CardLayout");
        clUsageFacSel.show(MainFrame.middleContent,"MapView");
    }
    public FacultySelector(){
        setLayout(selectorLay);
        setBorder(BorderFactory.createTitledBorder("Faculty Selector"));
        dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 
        // Update facultyList from DB
        facultyList=MainFrame.db.getFacultyList();
        // Convert List to Array for Combo Box labels
        String[] facList=new String[facultyList.size()];
        facList=facultyList.toArray(facList);
        facultySelectorBox=new JComboBox(facList);
        facultySelectorBox.setMaximumSize(new Dimension(300,20));
        facultySelectorBox.setRenderer(dlcr);
        facultySelectorBox.setToolTipText("Select faculty to track from the list");
        facultySelectorBox.setSelectedIndex(0);
        facultySelectorBox.addActionListener(this);
        // add MapView class object and get all faculty coords
        mapView=new MapView();
        mapView.addMouseListener(this);
        add(facultySelectorBox);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(mapView);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
