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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Pranav Phadke
 */
public class FacultyDetails extends JPanel{
//    GridLayout facDetLay=new GridLayout(2,1);
    MigLayout facDetLay2 = new MigLayout("wrap 1");
    static String curFacLocString,futFacLocString,facLocString;
    JLabel facDet,facLoc;
    static String[] facName;
    int weekEnd=0;
    List<String> facInfoDet=new ArrayList<>();
//    List<String> facLocDet=new ArrayList<>();
    public FacultyDetails(){//Regular call
//        setLayout(facDetLay);
        setLayout(facDetLay2);
        facName=MainFrame.middleContent.facInfo.getFacName();
        // Check against DB and get details
        facInfoDet=MainFrame.db.getFacDet(facName[0],facName[1]);
        MainFrame.db.getFacLoc(facName[0],facName[1]);
        setCurFacLocString();
        setFutFacLocString();
        // Create HTML string for facDet
        String facDetString=new String("<html>Details for "+facName[0]+" "+facName[1]+" :<p>Office number: "+facInfoDet.get(0)+"</p><p>Office extension: "+facInfoDet.get(1)+"</p><p>Email address: "+facInfoDet.get(2)+"</p></html>");
        facDet=new JLabel(facDetString);
        facLoc=new JLabel(curFacLocString+futFacLocString);
//        if(weekEnd==1){
//            facLoc=new JLabel(facLocString);
////            facLocString=new String("<html>Not a working day!</html>");
//        }else{
//            facLoc=new JLabel(curFacLocString+futFacLocString);
////            facLocString=new String("<html>Faculty Location Details :<p>Currently "+facLocDet.get(0)+" in "+facLocDet.get(1)+"</p></html>");
//        }

        // Create HTML string for facLoc
//        facLoc=new JLabel(facLocString);
        facDet.setBorder(BorderFactory.createTitledBorder("Faculty Details"));
        facDet.setVerticalAlignment(JLabel.BOTTOM);
        facLoc.setBorder(BorderFactory.createTitledBorder("Location Details"));
//        add(facDet);
//        add(facLoc);
        add(facDet,"width 295!,hmin 250,push, growy");
        add(facLoc,"width 295!,height 100!");
    }
    public void setCurFacLocString(){
//        System.out.println("Inside setCurFac");
        curFacLocString=new String(MainFrame.db.getCurStatus());
//        System.out.println("String set complete, going back..");
    }
    public void setFutFacLocString(){
        futFacLocString=new String(MainFrame.db.getFutStatus());
    }
//    public void setWeekEndString(String setString){
//        facLocString=new String(setString);
//    }
//    public void setWeekEndFlag(){
//        weekEnd=1;
//    }
}
