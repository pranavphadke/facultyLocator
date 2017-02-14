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
    private final Dimension minSize=new Dimension(985,575);// For 640x480 map
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
        bottomContent=new JLabel("Copyright 2016-2017 Pranav Phadke");
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
