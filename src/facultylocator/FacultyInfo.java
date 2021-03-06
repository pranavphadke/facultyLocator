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

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Pranav Phadke
 */
class FacultyInfo extends JPanel {
    static String name;
    static String[] names,locDetails;
//    GridLayout facInLay=new GridLayout(1,2);
    MigLayout facInLay2 = new MigLayout();
    public static FacultyDetails facultyDetails;
    public FacultyInfo(String name) {//Regular call
        this.name=name;
        setFacName(name);
//        setLayout(facInLay);
        setLayout(facInLay2);
        // Check against DB and get current loc
        facultyDetails=new FacultyDetails();
        locDetails=MainFrame.db.getLocDet();
        // Get xcoord and ycoord and pass it to MapLook
        MapLook mapLook=new MapLook(this.name,this.name);
        MapLook mapLook2=new MapLook(locDetails);
//        add(facultyDetails);
//        add(mapView);
        add(facultyDetails,"width 310!,growy");
        add(mapLook2,"push,grow");
    }
    public static void setFacName(String facName){
        names=facName.split(" ");
    }
    public static String[] getFacName(){
        return(names);
    }
    
}
