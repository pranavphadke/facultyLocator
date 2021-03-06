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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author Pranav Phadke
 */
public class TopPanelWHome extends TopPanel{
    JButton backButton,adminConsole;
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
        adminConsole=new JButton("Admin Console");
        adminConsole.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent adConBut){
//                System.out.println("Going back to faculty selection");
//                System.out.println("Importing CardLayout");
                clUsageTopPan=(CardLayout)(MainFrame.middleContentLay);
//                System.out.println("Imported CardLayout");
                clUsageTopPan.show(MainFrame.middleContent, "AdminConsole");
//                System.out.println("Set card complete");
            }
        });
        mainPageButtonPanel.setLayout(new BoxLayout(mainPageButtonPanel, BoxLayout.LINE_AXIS));
        mainPageButtonPanel.add(backButton);
        mainPageButtonPanel.add(adminConsole);
    }
}
