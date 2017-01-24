/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
 */
package facultylocator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Pranav Phadke
 */
public class MainFrame extends JFrame{
    public static MiddlePanel middleContent;
    public static CardLayout middleContentLay;
    public static TopPanelWHome topContent;
    private JLabel bottomContent;
    private final Dimension minSize=new Dimension(640,480);
    public static AppDB db=new AppDB();
    public MainFrame(String title) {
        super(title);
        WindowUtilities.setNativeLookAndFeel();
        setMinimumSize(minSize);
        Container content = getContentPane();
        content.setBackground(Color.lightGray);
        topContent=new TopPanelWHome();
        content.add(topContent,BorderLayout.PAGE_START);
        middleContent=new MiddlePanel();
        middleContentLay=middleContent.getLayout();
        content.add(middleContent,BorderLayout.CENTER);
        bottomContent=new JLabel("Copyright info");
        content.add(bottomContent,BorderLayout.PAGE_END);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener( new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                JFrame frame = (JFrame)e.getSource();
                int result = JOptionPane.showConfirmDialog(frame,"Are you sure you want to exit the application?","Exit Application",JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION){
                    db.shutDB();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
    }
}
