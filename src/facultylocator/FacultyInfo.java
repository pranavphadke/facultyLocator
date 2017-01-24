/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
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
    static String[] names;
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
        MapView mapView=new MapView(this.name,this.name);
//        add(facultyDetails);
//        add(mapView);
        add(facultyDetails,"width 310!,growy");
        add(mapView,"push,grow");
    }
//    public FacultyInfo(){//Default call
//        setLayout(facInLay);
//        this.name=MainFrame.middleContent.facInfo.getFacName();
//        System.out.println("Name in facInfo "+this.name);
//        facultyDetails=new FacultyDetails();
//        MapView mapView=new MapView();
//        add(facultyDetails);
//        add(mapView);
//    }
    public static void setFacName(String facName){
        names=facName.split(" ");
    }
    public static String[] getFacName(){
        return(names);
    }
    
}
