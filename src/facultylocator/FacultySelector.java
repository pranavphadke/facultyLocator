/*
*   Copyright 2016-2017 Pranav Phadke

*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at

*       http://www.apache.org/licenses/LICENSE-2.0

*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License. 
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
    MapLook mapLook;
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
        mapLook=new MapLook();
        mapLook.addMouseListener(this);
        add(facultySelectorBox);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(mapLook);
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
