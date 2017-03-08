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

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Pranav Phadke
 */
public class InfoManagement extends JPanel{
//    final static String basicInfo="Faculty Details";
//    final static String buildingInfo="Building Information";
//    final static String courseSched="Course Schedule";
    
    public InfoManagement(){
        super(new GridLayout(1,1));
        JTabbedPane infoManage=new JTabbedPane();
        // create cards/tabs
        InfoEditor basicInfo=new InfoEditor("basicInfo");
        infoManage.addTab("Faculty Details",basicInfo);
        
        InfoEditor buildingInfo=new InfoEditor("buildingInfo");
        infoManage.addTab("Building Information",buildingInfo);
        
        InfoEditor courseSched=new InfoEditor("courseSched");
        infoManage.addTab("Course Schedule",courseSched);
        add(infoManage);
    }
    
}
