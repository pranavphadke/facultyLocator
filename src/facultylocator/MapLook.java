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
import static java.lang.Math.abs;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pranav Phadke
 */
public class MapLook extends JPanel {
    String xCoord,yCoord,currCoord,currLocName,futCoord,futLocName,mapViewString,imgLink,key,focCoord;
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
    }
    public MapLook(String[] locDetails){
//      For one faculty member
        facName=MainFrame.middleContent.facInfo.getFacName();
        key=MainFrame.db.getAPIKey();
        focCoord=getFocusCoord(locDetails);
        if(MainFrame.db.getOnlyOffice()){
            // no classes for the rest of the day
            currCoord=locDetails[0];
            currLocName=locDetails[1];
            imgLink= "https://maps.googleapis.com/maps/api/staticmap?center="+focCoord+"&zoom=17&size=640x480&markers=color:red%7Clabel:C%7C"+currCoord+"&key="+key;
        }else{
            // will also be at locations other than office
            currCoord=locDetails[0];
            currLocName=locDetails[1];
            futCoord=locDetails[2];
            futLocName=locDetails[3];
            imgLink = "https://maps.googleapis.com/maps/api/staticmap?center="+focCoord+"&zoom=17&size=640x480&markers=color:red%7Clabel:C%7C"+currCoord+"&markers=color:blue%7Csize:mid%7Clabel:N%7C"+futCoord+"&key="+key;
        }
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
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
    public String getFocusCoord(String[] coords){
        Double lat1,lat2,lon1,lon2,deltaLat,deltaLon;
        String focusCoord = "27.526048,-97.881323";// default focus
        String[] c1=coords[0].split(",");
        String[] c2=coords[2].split(",");
        if(coords[0].equals(coords[2])){
            focusCoord=coords[0];
        }else{
            lat1=Double.parseDouble(c1[0]);
            lon1=Double.parseDouble(c1[1]);
            lat2=Double.parseDouble(c2[0]);
            lon2=Double.parseDouble(c2[1]);
            deltaLat=abs(lat1-lat2)/2;
            deltaLon=abs(lon1-lon2)/2;
            if(lat1<lat2){
                if(lon1<lon2){
                    focusCoord=(lat1+deltaLat)+","+(lon1+deltaLon);
                }else{
                    focusCoord=(lat1+deltaLat)+","+(lon2+deltaLon);
                }
            }else{
                if(lon1<lon2){
                    focusCoord=(lat2+deltaLat)+","+(lon1+deltaLon);
                }else{
                    focusCoord=(lat2+deltaLat)+","+(lon2+deltaLon);
                }
            }
        }
        return(focusCoord);
    }
}
