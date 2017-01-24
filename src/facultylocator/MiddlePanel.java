/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
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
