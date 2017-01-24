/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
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
public class MapView extends JPanel {
    String xCoord,yCoord;
    static String[] facName;
    public MapView(String xCoord,String yCoord) {
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
    public MapView(){//FacultySelector overview
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
