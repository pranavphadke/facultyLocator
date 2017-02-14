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
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author Pranav Phadke
 */
public class LogIn extends JPanel{
    private static CardLayout clUsageLogIn;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
    public LogIn(){
        setLayout(new GridBagLayout());
        GridBagConstraints cs=new GridBagConstraints();
 
        cs.fill=GridBagConstraints.NONE;
        cs.anchor=GridBagConstraints.CENTER;
 
        lbUsername=new JLabel("Username: ");
        cs.gridx=0;
        cs.gridy=0;
        cs.gridwidth=1;
        add(lbUsername,cs);
 
        tfUsername=new JTextField(35);
        cs.gridx=1;
        cs.gridy=0;
        cs.gridwidth =2;
        add(tfUsername,cs);
 
        lbPassword=new JLabel("Password: ");
        cs.gridx=0;
        cs.gridy=1;
        cs.gridwidth=1;
        add(lbPassword,cs);
 
        pfPassword=new JPasswordField(35);
        cs.gridx=1;
        cs.gridy=1;
        cs.gridwidth=2;
        add(pfPassword,cs);
        setBorder(new LineBorder(Color.GRAY));
 
        btnLogin=new JButton("Login");
        cs.gridx=1;
        cs.gridy=2;
        cs.gridwidth=1;
        add(btnLogin,cs);
 
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.db.authenticate(getUsername(), getPassword())) {
                    succeeded = true;
                    // clear password field
                    pfPassword.setText("");
                    // set card to InfoManagement
                    clUsageLogIn=(CardLayout)(MainFrame.middleContentLay);
                    clUsageLogIn.show(MainFrame.middleContent, "InfoManagement");
                } else {
                    succeeded = false;
                    // clear username and password fields
                    tfUsername.setText("");
                    pfPassword.setText("");
                    // Display error dialog box
                    JOptionPane.showMessageDialog(LogIn.this,"Incorrect login or password","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public String getUsername() {
        return tfUsername.getText().trim();
    }
 
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
 
    public boolean isSucceeded() {
        return succeeded;
    }
}
