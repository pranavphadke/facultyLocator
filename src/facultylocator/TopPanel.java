/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
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
}
