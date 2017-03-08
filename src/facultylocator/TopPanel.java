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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Pranav Phadke
 */
public class TopPanel extends JPanel implements ActionListener{
        int timerTimeInMilliSeconds = 500;    
        Calendar time = Calendar.getInstance();
        int hour,min,hour24,pad;
        String dateTime,amPm,hourS,hour24s,minS;    
        JLabel currentTime;
        JPanel mainPageButtonPanel=new JPanel();// Reuse for back button
        public TopPanel(){
            setLayout(new GridLayout(1,2));
            pad=5;
            mainPageButtonPanel.setLayout(new BorderLayout());// Explicit declaration of panel layout; Not defaulting to Border layout
            currentTime=new JLabel("Current Time : -:- -");//,SwingConstants.RIGHT
            currentTime.setHorizontalAlignment(JLabel.RIGHT);// Aligns label to the right
            currentTime.setBorder(BorderFactory.createEmptyBorder(pad,pad,pad,pad));// creates padding by using empty border
//            currentTime.setVerticalAlignment(JLabel.CENTER);
            Dimension d=currentTime.getPreferredSize();
            d.height=25;
            currentTime.setPreferredSize(d);
            add(mainPageButtonPanel);
            add(currentTime);
            this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            Timer refreshTimer=new Timer(timerTimeInMilliSeconds,this);// Refresh time value every 500ms
            refreshTimer.start();// start auto timed events
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            time.setTimeInMillis(System.currentTimeMillis());// Required to set delay in milliseconds
            if (time.get(Calendar.AM_PM) == Calendar.PM) {
                amPm="PM";
            }else{
                amPm="AM";
            }
            hour = time.get(Calendar.HOUR);
            hourS=Integer.toString(hour);// between 0-11
            if (hour==0){
                hourS="12";
            }
            min = time.get(Calendar.MINUTE);
            minS=Integer.toString(min);
            if(min<=9){
                minS="0"+minS;
            }
            dateTime="Current time : " + hourS + ":" + minS + " " + amPm;
            currentTime.setText(dateTime);
            repaint();
            timeConvert(hour,amPm);
        }
        public void timeConvert(int hr,String aP){
            if(aP.equals("AM")){
                hour24=hour;
            }else{
                hour24=hour+12;
            }
            hour24s=Integer.toString(hour24);
        }
}
