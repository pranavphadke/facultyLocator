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

import java.awt.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_END;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
//import java.awt.event.ActionListener;
import javax.swing.*;
//import javax.swing.JPanel;
import javax.swing.event.*;
/*import javax.swing.event.TableModelListener;*/
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pranav Phadke
 */
public class InfoEditor extends JPanel implements ActionListener,TableModelListener{
    public JScrollPane infoPane;
    public JTable infoTable;
    JButton addB,removeB,updateB,refreshB;
    JPanel buttonP;
//    GridLayout overall=new GridLayout(2,1);
    BorderLayout overall=new BorderLayout();
    ArrayList colIden=new ArrayList();
    Object [][] data;
    String popCourseSched="SELECT * FROM APP.COURSES";
    @SuppressWarnings("empty-statement")
    public InfoEditor(String infoType){
        // set DB query keywords for Basic Information, Course Schedule or Building Info
        switch (infoType){
            case "basicInfo":{
                // reset column identifiers and set new indentifiers
                colIden.clear();
                colIden.addAll(Arrays.asList("First Name","Last Name","Office Number (ENGC 303)","Office Extension (2003)","Email Address"));
                // set query and pass it to AppDB
                MainFrame.db.runInfoGetterQuery("BASICDETAIL",colIden.size());
                // get table data from AppDB
                data= MainFrame.db.getDBInfoData();
                // null dbInfoData
                MainFrame.db.nullDBInfoData();
                break;
            }
            case "buildingInfo":{
                // reset column identifiers and set new indentifiers
                colIden.clear();
                colIden.addAll(Arrays.asList("Building Code (ENGC)","GPS Coordinates: Latitude","GPS Coordinates: Longitude","Building Name"));
                // set query and pass it to AppDB
                MainFrame.db.runInfoGetterQuery("LOCATION",colIden.size());
                // get table data from AppDB
                data= MainFrame.db.getDBInfoData();
                // null dbInfoData
                MainFrame.db.nullDBInfoData();
                break;
            }
            case "courseSched":{
                // reset column identifiers and set new indentifiers
                colIden.clear();
                colIden.addAll(Arrays.asList("Course Prefix Number Section (MEEN 1320 001)","Instructor's First Name","Instructor's Last Name","Days (MTWRF)","Start Time (15:00:00)","End Time (16:30:00)","Room (ENGC 275)","Building Code (ENGC)"));
                // set query and pass it to AppDB
                MainFrame.db.runInfoGetterQuery("COURSES",colIden.size());
                // get table data from AppDB
                data= MainFrame.db.getDBInfoData();
                // null dbInfoData
                MainFrame.db.nullDBInfoData();
                break;
            }
            default:System.out.println("Correct tab not called in InfoManagement");break;
        }
        buttonP=new JPanel();
        setLayout(overall);
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.LINE_AXIS));
        // create and add buttons
        addB=new JButton("Add");
        addB.addActionListener(this);
        buttonP.add(addB);
        removeB=new JButton("Remove");
        removeB.addActionListener(this);
        buttonP.add(removeB);
        updateB=new JButton("Update table");
        updateB.addActionListener(this);
        buttonP.add(updateB);
        refreshB=new JButton("Refresh table");
        refreshB.addActionListener(this);
        buttonP.add(refreshB);
        //create table model with data and column identifiers
        DefaultTableModel model = new DefaultTableModel(data, colIden.toArray()); 
//        {
//            @Override
//            public boolean isCellEditable(int row, int column)// Index starts at 0
//            {
//                if(row==model.getRowCount()){
//                   return true; 
//                }else return false;
//            }
//    //        @Override
//    //        public Class<?> getColumnClass(int columnIndex)
//    //        {
//    //            return columnClass[columnIndex];
//    //        }
//        };
        model.addTableModelListener(this);
        infoTable=new JTable(model){
            public boolean isCellEditable(int row, int column)// Index starts at 0
            {
                if(row==model.getRowCount()-1){
                   return true; 
                }else return false;
            }
        };
        infoPane=new JScrollPane(infoTable);
        add(infoPane,CENTER);
        add(buttonP,PAGE_END);
        // use getValueAt to get updated values    
    }
    public InfoEditor(){
        this("basicInfo"); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
           // Identify the button that initiated the event.

        JButton jb = (JButton) e.getSource ();

        // Obtain the button's label.

        String label = jb.getText ();

        // Either delete or append a row, as appropriate.

        if (label.equals ("Remove"))
        {
          DefaultTableModel dtm = (DefaultTableModel) infoTable.getModel ();
          int nRows = dtm.getRowCount ();
          if (nRows != 0)
            dtm.removeRow (nRows - 1);
        }
        else if (label.equals("Add"))
        {
          DefaultTableModel dtm = (DefaultTableModel) infoTable.getModel ();
          Object [] data = { dtm.getRowCount ()+1,"Name", 30.0,true };
          dtm.insertRow (dtm.getRowCount (), data);
        }else if (label.equals("Update table")){
            // Update entry in the DB
        }else{
            // Re-show the current tab
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
