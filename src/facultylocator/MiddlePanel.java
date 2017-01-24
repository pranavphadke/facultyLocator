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

import java.awt.CardLayout;
import javax.swing.*;

/**
 *
 * @author Pranav Phadke
 */
public class MiddlePanel extends JPanel{
    private static CardLayout midContent=new CardLayout();
    public static FacultySelector facSelect;
    public static FacultyInfo facInfo;
    public static MapView mapV;
    String[] contentList={"FacultySelector","FacultyInfo","MapView"};
    public MiddlePanel(){
//        MainFrame.db.printContent("BASICDETAIL");
        setLayout(midContent);
        facSelect=new FacultySelector();
//        facInfo=new FacultyInfo();
        mapV=new MapView();
        add(facSelect,"FacultySelector");
//        add(facInfo,"FacultyInfo");
        add(mapV,"MapView");
    }
    public CardLayout getLayout(){
        return(midContent);
    }
    public static void setFacInfo(String name){
        facInfo=new FacultyInfo(name);
    }
    public static FacultyInfo getFacInfo(){
        return(facInfo);
    }
    
}
