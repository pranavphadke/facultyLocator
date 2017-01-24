/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
 */
package facultylocator;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author Pranav Phadke
 */
public class TopPanelWHome extends TopPanel{
    JButton backButton;
    private static CardLayout clUsageTopPan;
    public TopPanelWHome(){
        super();
        backButton=new JButton("Main Page");
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent bckBut){
//                System.out.println("Going back to faculty selection");
//                System.out.println("Importing CardLayout");
                clUsageTopPan=(CardLayout)(MainFrame.middleContentLay);
//                System.out.println("Imported CardLayout");
                clUsageTopPan.show(MainFrame.middleContent, "FacultySelector");
//                System.out.println("Set card complete");
            }
        });
        mainPageButtonPanel.add(backButton,BorderLayout.LINE_START);// Aligns button to the left of the panel
    }
}
