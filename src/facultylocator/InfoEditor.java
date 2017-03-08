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
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_END;
import java.awt.event.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.awt.event.ActionListener;
import javax.swing.*;
//import javax.swing.JPanel;
import javax.swing.event.*;
import static javax.swing.event.TableModelEvent.ALL_COLUMNS;
/*import javax.swing.event.TableModelListener;*/
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pranav Phadke
 */
public class InfoEditor extends JPanel implements ActionListener,TableModelListener{
    public JScrollPane infoPane;
    public JTable infoTable=null;
    JButton addB,removeB,updateB,refreshB;
    JPanel buttonP;
    int dbTblType,count=0,rowSel,colSel,eventType;
//    GridLayout overall=new GridLayout(2,1);
    BorderLayout overall=new BorderLayout();
    ArrayList colIden=new ArrayList();
    List temp=new ArrayList();
    List<List<String>> dbTblHead=new ArrayList<List<String>>();
    String[] dbTblName={"BASICDETAIL","LOCATION","COURSES"};
    Object [][] data;
    String popCourseSched="SELECT * FROM APP.COURSES";
    DefaultTableModel model=null;
    @SuppressWarnings("empty-statement")
    public InfoEditor(String infoType){
        // set DB table headers
        dbTblHead.add(new ArrayList<String>());
        dbTblHead.get(0).add("FIRSTNAME");
        dbTblHead.get(0).add("LASTNAME");
        dbTblHead.get(0).add("OFFNUM");
        dbTblHead.get(0).add("OFFEXT");
        dbTblHead.get(0).add("EMAIL");

        dbTblHead.add(new ArrayList<String>());
        dbTblHead.get(1).add("BLDG");
        dbTblHead.get(1).add("LAT");
        dbTblHead.get(1).add("LON");
        dbTblHead.get(1).add("BLDGNAME");

        dbTblHead.add(new ArrayList<String>());
        dbTblHead.get(2).add("COURSE");
        dbTblHead.get(2).add("INSTRUCTFIRSTNAME");
        dbTblHead.get(2).add("INSTRUCTLASTNAME");
        dbTblHead.get(2).add("DAYS");
        dbTblHead.get(2).add("STARTTIME");
        dbTblHead.get(2).add("ENDTIME");
        dbTblHead.get(2).add("ROOM");
        dbTblHead.get(2).add("BLDG");

        // set DB query keywords for Basic Information, Course Schedule or Building Info
        switch (infoType){
            case "basicInfo":{
                dbTblType=0;
                // reset column identifiers and set new indentifiers
                colIden.clear();
                colIden.addAll(Arrays.asList("First Name","Last Name","Office Number (ENGC 303)","Office Extension (2003)","Email Address"));
//                // set query and pass it to AppDB
//                MainFrame.db.runInfoGetterQuery(dbTblName[dbTblType],colIden.size());
//                // get table data from AppDB
//                data= MainFrame.db.getDBInfoData();
//                // null dbInfoData
//                MainFrame.db.nullDBInfoData();
                break;
            }
            case "buildingInfo":{
                dbTblType=1;
                // reset column identifiers and set new indentifiers
                colIden.clear();
                colIden.addAll(Arrays.asList("Building Code (ENGC)","GPS Coordinates: Latitude","GPS Coordinates: Longitude","Building Name"));
//                // set query and pass it to AppDB
//                MainFrame.db.runInfoGetterQuery(dbTblName[dbTblType],colIden.size());
//                // get table data from AppDB
//                data= MainFrame.db.getDBInfoData();
//                // null dbInfoData
//                MainFrame.db.nullDBInfoData();
                break;
            }
            case "courseSched":{
                dbTblType=2;
                // reset column identifiers and set new indentifiers
                colIden.clear();
                colIden.addAll(Arrays.asList("Course Prefix Number Section (MEEN 1320 001)","Instructor's First Name","Instructor's Last Name","Days (MTWRF)","Start Time (15:00:00)","End Time (16:30:00)","Room (ENGC 275)","Building Code (ENGC)"));
//                // set query and pass it to AppDB
//                MainFrame.db.runInfoGetterQuery(dbTblName[dbTblType],colIden.size());
//                // get table data from AppDB
//                data= MainFrame.db.getDBInfoData();
//                // null dbInfoData
//                MainFrame.db.nullDBInfoData();
                break;
            }
            default:System.out.println("Correct tab not called in InfoManagement");break;
        }
        buttonP=new JPanel();
        setLayout(overall);
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.LINE_AXIS));
        // create and add buttons
//        addB=new JButton("Add");
//        addB.addActionListener(this);
//        buttonP.add(addB);
        removeB=new JButton("Remove Record");
        removeB.addActionListener(this);
        buttonP.add(removeB);
//        updateB=new JButton("Update table");
//        updateB.addActionListener(this);
//        buttonP.add(updateB);
//        refreshB=new JButton("Refresh table");
//        refreshB.addActionListener(this);
//        buttonP.add(refreshB);
        //create table model with data and column identifiers
//        model = new DefaultTableModel(data, colIden.toArray());
        model=getDBTableModel();
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
//        model.addTableModelListener(this);
//        infoTable=new JTable(model){
//            public boolean isCellEditable(int row, int column)// Index starts at 0
//            {
////                if(row==model.getRowCount()-1){
////                   return true; 
////                }else return false;
//                return true;
//            }
//        };
        updateTable();
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
//        // Identify the button that initiated the event.
//        JButton jb = (JButton) e.getSource ();
//        // Obtain the button's label.
//        String label = jb.getText ();
//        // Either delete or append a row, as appropriate.
//        if (label.equals ("Remove Record")){
            // Check if selected row is other than the last row
            if (rowSel<model.getRowCount()-1){
                eventType=2;
                List headers=new ArrayList();
                headers=dbTblHead.get(dbTblType);
                FindNum locator=new FindNum();
                String st=getStatement(colSel, rowSel, rowSel,locator.getIntArray(0,headers.size()),headers,eventType);
                // display confirmation dialog box
                int result = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete selected record from the database?","Confirm Removal Action",JOptionPane.YES_NO_OPTION);
                // execute statement using AppDB if YES and then refresh Panel
                if (result == JOptionPane.YES_OPTION){
                    System.out.println("Will execute this query..."+st+"... and refresh current pane");
                    // Send and execute statment
                    MainFrame.db.runInfoSetterStatement(st);
                    MainFrame.middleContent.facSelect.updateCB();
                    count=0;
                    // Update table
                    updateTable();
                }else{
                    System.out.println("Just refresh current pane");
                    count=0;
                    // Update table
                    updateTable();
                }
            }else{
                // display Warning dialog box
            }
//            DefaultTableModel dtm = (DefaultTableModel) infoTable.getModel ();
//            int nRows = dtm.getRowCount ();
//            if (nRows != 0)
//            dtm.removeRow (nRows - 1);
//        }else{
//            // Re-show the current tab
//        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        // get first changed row number
        int firstRow=e.getFirstRow();
        System.out.println("First row:"+firstRow);
        // get last changed row number
        int lastRow=e.getLastRow();
        System.out.println("Last row:"+lastRow);
        // get changed column number
        int column=e.getColumn();
        System.out.println("Column changed:"+column);
        // get all headers for current type of  Info Table
        List headers=new ArrayList();
        headers=dbTblHead.get(dbTblType);
        
        // create a new instance of FindNum for creating all column index and remove currently selected index
//        System.out.println(headers.size());
        FindNum locator=new FindNum();
        int[] ind=locator.removeNum(locator.getIntArray(0,headers.size()), column); 
        eventType=0;
        // switch with getType()==TableModelEvent.UPDATE or.DELETE or .INSERT
        switch (e.getType()){
            case TableModelEvent.UPDATE:{
            // .UPDATE
                // check if not the last empty row
                if (firstRow<model.getRowCount()-1){
                    eventType=0;
                    // prepare statement                        
                    String st=getStatement(column, firstRow, lastRow,ind,headers,eventType);
                    // display confirmation dialog box
                    int result = JOptionPane.showConfirmDialog(this,"Are you sure you want to update content in the database?","Confirm Update Action",JOptionPane.YES_NO_OPTION);
                    // execute statement using AppDB if YES and then refresh Panel
                    if (result == JOptionPane.YES_OPTION){
                        System.out.println("Will execute this query..."+st+"... and refresh current pane");
                        // Send and execute statment
                        MainFrame.db.runInfoSetterStatement(st);
                        MainFrame.middleContent.facSelect.updateCB();
                        count=0;
                        // Update table
                        updateTable();
                    }else{
                        System.out.println("Just refresh current pane");
                        count=0;
                        // Update table
                        updateTable();
                    }
                }
            }
//            case TableModelEvent.DELETE:{
//            // .DELETE
//                // check if not the last empty row
//                if (firstRow<model.getRowCount()-1){
//                    eventType=2;
//                    // Get selected row
//                    // prepare statement
//                    // display confirmation dialog box
//                        // execute statement using AppDB if YES and then refresh Panel
//                        // Refresh Panel if NO
//                }
//            }
            case TableModelEvent.INSERT:{
            // .INSERT
                // check only the last row
                if(firstRow==model.getRowCount()-1){
                    // wait until all cells on last row are filled
                    count++;
                    if (count==headers.size()){
                        eventType=1;
                        // prepare statement
                        String st=getStatement(column, firstRow, lastRow,locator.getIntArray(0,headers.size()),headers,eventType);
                        // display confirmation dialog box
                        int result = JOptionPane.showConfirmDialog(this,"Are you sure you want to add new content to the database?","Confirm Add Action",JOptionPane.YES_NO_OPTION);
                        // execute statement using AppDB if YES and then refresh Panel
                        if (result == JOptionPane.YES_OPTION){
                            System.out.println("Will execute this query..."+st+"... and refresh current pane");
                            // Send and execute statment
                            MainFrame.db.runInfoSetterStatement(st);
                            MainFrame.middleContent.facSelect.updateCB();
//                            MainFrame.middleContent.facSelect.revalidate();
//                            MainFrame.middleContent.facSelect.repaint();
                            // reset count
                            count=0;
                            // Update table
                            updateTable();
                            
                        }else{
                            System.out.println("Just refresh current pane");
                            // reset count
                            count=0;
                            // Update table
                            updateTable();
                        }
                    }
                }
            }
        }
    }
    public String getStatement(int col,int fR,int lR,int[] ind,List head,int updType){
        String cond="",statement="",tblPrefix="",change="";
        switch (updType){
            case 0:{
                tblPrefix=tblPrefix+"UPDATE APP."+dbTblName[dbTblType]+" SET ";
                for(int i=0;i<ind.length-1;i++){
                    cond=cond+" "+head.get(ind[i])+"='"+model.getValueAt(fR,ind[i])+"'";
                    if(i<ind.length-2){
                        cond=cond+" AND ";
                    }//else cond=cond+"'";
                }
                change=change+head.get(col)+"='"+model.getValueAt(fR, col)+"'";
                cond=" WHERE"+cond;
//                statement=tblPrefix+change+cond;
                break;
            }
            case 1:{
                tblPrefix=tblPrefix+"INSERT INTO APP."+dbTblName[dbTblType]+" VALUES ";
                change=change+"(";
                for(int i=0;i<ind.length-1;i++){
                    change=change+"'"+model.getValueAt(fR,ind[i])+"'";
                    
                    if (i<ind.length-2){
                        change=change+" , ";
                    }
                }
                change=change+")";
//                statement=statement+tblPrefix+change;
                break;
            }
            case 2:{
                tblPrefix=tblPrefix+"DELETE FROM APP."+dbTblName[dbTblType];
                for(int i=0;i<ind.length-1;i++){
                    cond=cond+head.get(ind[i])+"='"+model.getValueAt(fR,ind[i])+"'";
                    if(i<ind.length-2){
                        cond=cond+" AND ";
                    }
                }
                cond=" WHERE "+cond;
//                statement=statement+tblPrefix+cond;
                break;
            }
        }
    statement=tblPrefix+change+cond;    
    return statement;
    }
    public DefaultTableModel getDBTableModel(){
        // set query and pass it to AppDB
        MainFrame.db.runInfoGetterQuery(dbTblName[dbTblType],colIden.size());
        // get table data from AppDB
        data= MainFrame.db.getDBInfoData();
        // null dbInfoData
        MainFrame.db.nullDBInfoData();        
        DefaultTableModel tblModel=new DefaultTableModel(data, colIden.toArray());
        tblModel.addTableModelListener(this);
        return tblModel;
    }
    public void updateTable(){
        model=getDBTableModel();
        if (infoTable==null){
            infoTable=new JTable(model){
                public boolean isCellEditable(int row, int column){// Index starts at 0
                        return true;
                }
            };
            infoTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            infoTable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                   rowSel=infoTable.getSelectedRow();
                   colSel=infoTable.getSelectedColumn();
                   System.out.println(rowSel);
                }
            });
        }else{
            infoTable.setModel(model);
        }
    }
    public Time toSQLTime(String time){
        String[] hhMMSS=time.split(":");
        Time sqlTime=new Time(Integer.parseInt(hhMMSS[0]),Integer.parseInt(hhMMSS[1]),Integer.parseInt(hhMMSS[2]));
        return sqlTime;
    }
}
