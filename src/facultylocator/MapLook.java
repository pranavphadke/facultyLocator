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

import static facultylocator.FacultyDetails.facName;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pranav Phadke
 */
public class MapLook extends JPanel {
    String xCoord,yCoord,currCoord,currLocName,futCoord,futLocName,mapViewString,imgLink,key;
    static String[] facName;
    public MapLook(String xCoord,String yCoord) {
        this.xCoord=xCoord;
        this.yCoord=yCoord;
        facName=MainFrame.middleContent.facInfo.getFacName();
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        // Temp label
        String mapViewString=new String("<html>Map view content here for "+facName[0]+" "+facName[1]+"</html>");
        JLabel tempLabel=new JLabel(mapViewString);
        tempLabel.setHorizontalAlignment(JLabel.CENTER);
        add(tempLabel,BorderLayout.CENTER);
        //
    }
    public MapLook(String[] locDetails){
//      For one faculty member
        facName=MainFrame.middleContent.facInfo.getFacName();
        key=MainFrame.db.getAPIKey();
        if(MainFrame.db.getOnlyOffice()){
            // no classes for the rest of the day
            currCoord=locDetails[0];
            currLocName=locDetails[1];
//            mapViewString=new String("<html>Map for "+facName[0]+" "+facName[1]+" with final loc at "+currCoord+", "+currLocName+" </html>");
            imgLink= "https://maps.googleapis.com/maps/api/staticmap?center="+currCoord+"&zoom=17&size=400x400&markers=color:red%7Clabel:C%7C"+currCoord+"&key="+key;
        }else{
            // will also be at locations other than office
            currCoord=locDetails[0];
            currLocName=locDetails[1];
            futCoord=locDetails[2];
            futLocName=locDetails[3];
//            mapViewString=new String("<html>Map for "+facName[0]+" "+facName[1]+" with current loc at "+currCoord+", "+currLocName+" and next loc at "+futCoord+", "+futLocName+"</html>");
            imgLink = "https://maps.googleapis.com/maps/api/staticmap?center=27.526048,-97.881323&zoom=16&size=640x480&markers=color:red%7Clabel:C%7C"+currCoord+"&markers=color:blue%7Csize:mid%7Clabel:N%7C"+futCoord+"&key="+key;
        }
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        // Temp label
        mapViewString=new String("<html><img src="+imgLink+"></html>");
        JLabel tempLabel=new JLabel(mapViewString);
        tempLabel.setHorizontalAlignment(JLabel.CENTER);
        add(tempLabel,BorderLayout.CENTER);
    }
    public MapLook(){//FacultySelector overview
//        Get all coords in DB
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        String mapViewString=new String("<html>All MapView content here!</html>");
        // Temp label
        JLabel tempLabel=new JLabel(mapViewString);
        tempLabel.setHorizontalAlignment(JLabel.CENTER);
        add(tempLabel,BorderLayout.CENTER);
    }
    
}
