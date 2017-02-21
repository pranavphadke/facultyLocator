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

import static java.lang.Math.abs;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.*;
/**
 *
 * @author Pranav Phadke
 */
public class AppDB {
    private String protocol="jdbc:derby:bin/";
    private String framework="embedded";
    static List<String> facNameList;
    List<String> facDet,facLoc;
    static String[] locDet={"","","",""};
    Object [][] dbInfoData;
//    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String frName=null,lsName=null,ofNum=null,mail=null,facultyDB="facultyDB",tableName,printStatement,delStatement,dayOfWeek,curStatus,futStatus,noWrkHr,availInOffice,notWrkDay,offCoordString=null,key;
    int ofExt=9999,tableCheckFlag=0,countFacCourse=0,countInfoEntry=0;
    Statement qrFacDB=null;
    PreparedStatement insFacDB=null,updFacDB=null,setSchema=null;
    Connection conn=null;
    ResultSet rsFacDB=null,countFacLoc=null,offCoord=null,apiKey=null,adminAuth=null,countInfo=null,runInfoQuery=null;
    Properties p;
    //Time sT1,eT1,sT2,eT2,timeDiff;
    Calendar sT1,eT1,sT2,eT2;
    static boolean onlyOffFlag;
    public AppDB(){
//        System.out.println("Check for db mode...");
//        if (arg.length>0) {
//            if (arg[0].equals("derbyclient")) {
//                framework="derbyclient";
//                protocol = "jdbc:derby://localhost:1527/";
//            }
//        }
        try{
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
//            p = new Properties();
//            p.put("user", "admin");
//            p.put("pass","H1511");
            //Create connection to DB
            conn=DriverManager.getConnection(protocol+facultyDB+";create=false;user=admin;password=H1511");//p
            setSchema=conn.prepareStatement("set schema ?");
            setSchema.setString(1,"APP");
            setSchema.executeUpdate();
            conn.commit();
//            DatabaseMetaData metadata = conn.getMetaData();
            conn.setAutoCommit(false);
            //Create query statement to the DB to check against tables 
            qrFacDB=conn.createStatement();
            conn.commit();
            apiKey=qrFacDB.executeQuery("SELECT APIKEY FROM APP.GMAPS");
            apiKey.next();
            setAPIKey(apiKey.getString("APIKEY"));
        }catch(SQLException se){
            System.out.println("SQL error for main catch:"+se);
        }
    }
    public void setAPIKey(String k){
        key=k;
    }
    public String getAPIKey(){
        return(key);
    }
    public void addFacDet(String fName, String lName, String oNum,String eAdd){
        try{
            frName=fName;
            lsName=lName;
            ofNum=oNum;
            mail=eAdd;
            ofExt=0000;
            insFacDB=conn.prepareStatement("insert into APP.BASICDETAIL values (?,?,?,?,?)");
            insFacDB.setString(1,frName);
            insFacDB.setString(2,lsName);
            insFacDB.setString(3,ofNum);
            insFacDB.setInt(4,ofExt);
            insFacDB.setString(3,mail);
            insFacDB.executeUpdate();
            conn.commit();
        }
        catch(SQLException se){
            System.out.println("SQL error for addFacDet catch:"+se);
        }
    }
    public void shutDB(){
        if (framework.equals("embedded")) {
            System.out.println("Closing DB connection");
            try{
                DriverManager.getConnection(protocol+facultyDB+";shutdown=true;user=admin;password=H1511");
            }catch(SQLException see){
                if (( (see.getErrorCode() == 45000)
                        && ("08006".equals(see.getSQLState()) ))) {
                    // we got the expected exception
                    System.out.println("Derby shutdown normal: "+see.getErrorCode()+" "+see.getSQLState()+" "+see);
                    // For single database shutdown, the expected
                    // SQL state is "08006", and the error code is 45000.
//                  // For complete system shutdown  SQL state is "XJ015" and error code is 50000
                } else {
                    // if the error code or SQLState is different, we have
                    // an unexpected exception (shutdown failed)
                    System.err.println("Derby did not shutdown normally: "+see.getErrorCode()+" "+see.getSQLState()+" "+see);
                }
            }finally{
                // Release resources
                try{
                    if (rsFacDB!=null) {
                        rsFacDB.close();
                        rsFacDB=null;
                    }
                }catch(SQLException se){
                    System.out.println("SQL error for ResultSet catch:"+se);
                }
                try{
                    if(qrFacDB!=null){
                        qrFacDB.close();
                        qrFacDB=null;
                    }
                }catch(SQLException se){
                    System.out.println("SQL error for Statement catch:"+se);
                }
                try{
                    if(insFacDB!=null){
                        insFacDB.close();
                        insFacDB=null;
                    }
                }catch(SQLException se){
                    System.out.println("SQL error for Prepared Statement catch 1:"+se);
                }
                try{
                    if(updFacDB!=null){
                        updFacDB.close();
                        updFacDB=null;
                    }
                }catch(SQLException se){
                    System.out.println("SQL error for Prepared Statement catch 2:"+se);
                }
                try{
                    if(conn!=null){
                        conn.close();
                        conn=null;
                    }
                }catch(SQLException se){
                    System.out.println("SQL error for Connection catch:"+se);
                }
            }
        }
    }
    public void deleteTable(String t2D){
        delStatement=String.format("drop table APP.%s",t2D.toUpperCase());
        try {
            System.out.println("Now starting deletion process...");
            qrFacDB.execute(delStatement);
            System.out.println("Table deleted from the DB");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(AppDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<String> getFacultyList(){
        facNameList=new ArrayList<>();
        try{
            facNameList.add("--Pick One--");
            rsFacDB=qrFacDB.executeQuery("select * from APP.BASICDETAIL order by FIRSTNAME asc");
            while(rsFacDB.next()){
                facNameList.add(String.format("%s %s",rsFacDB.getString(1),rsFacDB.getString(2)));
            }
        }catch(SQLException se){
            System.out.println("SQL error for getFacultyList catch:"+se);
        }
        return(facNameList);
    }
    public List<String> getFacDet(String frsName, String lstName){
        facDet=new ArrayList<>();
        try{
            // get faculty details and find office coordinates
            rsFacDB=qrFacDB.executeQuery(String.format("select OFFNUM,OFFEXT,EMAIL from APP.BASICDETAIL where upper(FIRSTNAME)='%s' AND upper(LASTNAME)='%s'",frsName.toUpperCase(),lstName.toUpperCase()));
            while(rsFacDB.next()){
                facDet.add(rsFacDB.getString("OFFNUM"));
                facDet.add(rsFacDB.getString("OFFEXT"));// Use getString("COLUMNNAME")
                facDet.add(rsFacDB.getString("EMAIL"));
                ofNum=rsFacDB.getString("OFFNUM");
            }
            String[] offNumSplit=ofNum.split(" ");
            try{
                offCoord=qrFacDB.executeQuery(String.format("SELECT LOCATION.LAT,LOCATION.LON FROM APP.LOCATION WHERE LOCATION.BLDG = '%s'",offNumSplit[0]));
                while(offCoord.next()){
                    // set string with office coordinates (used in inProg)
                    offCoordString=String.format("%s,%s",offCoord.getString("LAT"),offCoord.getString("LON"));
                }
            }catch(SQLException se){
                System.out.println("SQL error for getFacDet->offCoord catch:"+se);
            }
        }catch(SQLException se){
            System.out.println("SQL error for getFacDet catch:"+se);
        }
        return(facDet);
    }
    public void getFacLoc(String frsName, String lstName){
        facLoc=new ArrayList<>();
        countFacCourse=0;
        Calendar now = Calendar.getInstance();
        setDayCode(now.get(Calendar.DAY_OF_WEEK));
        // check if week day
        if(dayOfWeek.equals("NA")){
            // create not a working day string
            curStatus="<html>University closed for weekend";
            futStatus="</html>";
        } else {
            String likeCond=new String("%"+dayOfWeek+"%");
            String hourCond=new String(MainFrame.topContent.hour24+":"+MainFrame.topContent.minS+":00");//"+MainFrame.topContent.minS+"
            try{
//                System.out.println("Creating count query..");
                countFacLoc=qrFacDB.executeQuery(String.format("SELECT COUNT(*) FROM APP.COURSES INNER JOIN APP.LOCATION ON COURSES.BLDG =LOCATION.BLDG WHERE DAYS LIKE '%s' AND upper(COURSES.INSTRUCTFIRSTNAME)='%s' AND upper(COURSES.INSTRUCTLASTNAME)='%s' AND COURSES.ENDTIME >= '%s'",likeCond,frsName.toUpperCase(),lstName.toUpperCase(),hourCond));//COURSES.STARTTIME
//                System.out.println("Query created and passes");
//                System.out.println("Now finding course count ...");
                if(countFacLoc.next()){
                    countFacCourse=countFacLoc.getInt(1);
                }
//                System.out.printf("Course count is : %s\n",countFacCourse);
                if(countFacCourse==0){
                    // No class for faculty today
                    availOffice();
                    locDet[0]=offCoordString;
                    locDet[1]=String.format("Faculty Office in %s",ofNum);
                    locDet[2]=offCoordString;
                    locDet[3]=String.format("Faculty Office in %s",ofNum);
                    setOnlyOffice(true);
                    futStatus="</html>";
                }else{
                    setOnlyOffice(false);
//                    System.out.println("Creating result query..");
                    rsFacDB=qrFacDB.executeQuery(String.format("SELECT COURSES.COURSE,COURSES.ROOM,COURSES.STARTTIME,COURSES.ENDTIME,LOCATION.LAT,LOCATION.LON FROM APP.COURSES INNER JOIN APP.LOCATION ON COURSES.BLDG =LOCATION.BLDG WHERE DAYS LIKE '%s' AND upper(COURSES.INSTRUCTFIRSTNAME)='%s' AND upper(COURSES.INSTRUCTLASTNAME)='%s' AND COURSES.ENDTIME >= '%s' ORDER BY COURSES.ENDTIME asc",likeCond,frsName.toUpperCase(),lstName.toUpperCase(),hourCond));//COURSES.STARTTIME
//                    System.out.println("Query created and passed");
                    inProg();
                }
            }catch(SQLException se){
                System.out.println("SQL error for getFacLoc catch:"+se);
            }
        }
    }
    public void availOffice(){
        // Set office as current location only if current time is between working hours
        if(isWorkingHours()){
            // create current loc display string and pass to FacultyDetails
            curStatus="<html>Faculty currently available in office";
        }else{
            // Out of working hours
             curStatus="<html>Faculty working hours are only between 08:00 HRS - 17:00 HRS";
        }
    }
    public boolean isWorkingHours(){
        // Finds if it is currently faculty working hours or not
        int openHr=8,closeHr=17;
        int openMin=0,closeMin=0;
        long currTime=MainFrame.topContent.hour24*60*60+MainFrame.topContent.min*60;
        long openTime=openHr*60*60+openMin*60;
        long closeTime=closeHr*60*60+closeMin*60;
        if(openTime-currTime<=0 & currTime-closeTime<=0){
            return(true);
        }else return(false);
    }
    public int beforeCurrentTime(Calendar d1){
        // Compares if current system time is past d1
        long t1;
        long currTime;
        t1 = d1.get(Calendar.HOUR_OF_DAY)*60*60+d1.get(Calendar.MINUTE)*60;
        currTime=MainFrame.topContent.hour24*60*60+MainFrame.topContent.min*60;
        long diff = t1-currTime;
        if(diff<0){
            return(1);
        }else return(0);
    }
    public void inProg(){
        try{
            sT1=Calendar.getInstance();
            eT1=Calendar.getInstance();
            if(rsFacDB.next()){
                // Identify if first class in the list is in progress
                sT1.setTime(rsFacDB.getTime("STARTTIME"));
                eT1.setTime(rsFacDB.getTime("ENDTIME"));
                //compare current time with st and et of first result
                if(beforeCurrentTime(sT1)==1 & beforeCurrentTime(eT1)==0){
                    // if current time between st and et set current status and check next result
                    // create current loc display string and pass to FacultyDetails
                    locDet[0]=String.format("%s,%s",rsFacDB.getString("LAT"),rsFacDB.getString("LON"));
                    locDet[1]=rsFacDB.getString("COURSE");
                    curStatus=String.format("<html>Faculty Location Details :<p>Currently teaching %s in %s until %02d:%02d HRS</p><p>", rsFacDB.getString("COURSE"),rsFacDB.getString("ROOM"),eT1.get(Calendar.HOUR_OF_DAY),eT1.get(Calendar.MINUTE));
                    if(rsFacDB.next()){
                        // If more classes in the list set the next one as future location only if there is less than 30 mins between them
                        sT2=Calendar.getInstance();
                        eT2=Calendar.getInstance();
                        // Get diff between et and st of 1st and second result
                        sT2.setTime(rsFacDB.getTime("STARTTIME"));
                        eT2.setTime(rsFacDB.getTime("ENDTIME"));
                        // if diff > 30 min then set future status as office
                        if(abs(eT1.get(Calendar.MINUTE)-sT2.get(Calendar.MINUTE))>30){
                            // set future status to office
                            futStatus=String.format("Faculty will be available next in the office after %02d:%02d HRS until %02d:%02d HRS</p></html>",eT1.get(Calendar.HOUR_OF_DAY),eT1.get(Calendar.MINUTE),sT2.get(Calendar.HOUR_OF_DAY),sT2.get(Calendar.MINUTE));
                            // New query to find office location
                            locDet[2]=offCoordString;
                            locDet[3]=String.format("Faculty Office in %s",ofNum);
                        }else{
                            // set future status to 2nd result
                            futStatus=String.format("Faculty will be teaching %s in %s from %02d:%02d HRS until %02d:%02d HRS</p></html>",rsFacDB.getString("COURSE"),rsFacDB.getString("ROOM"),sT2.get(Calendar.HOUR_OF_DAY),sT2.get(Calendar.MINUTE),eT2.get(Calendar.HOUR_OF_DAY),eT2.get(Calendar.MINUTE));
                            locDet[2]=String.format("%s,%s",rsFacDB.getString("LAT"),rsFacDB.getString("LON"));
                            locDet[3]=rsFacDB.getString("COURSE");
                        }
                    }else{
                        // set future status to office
                        futStatus="Faculty will be available next in the office until 17:00 HRS</p></html>";
                        locDet[2]=offCoordString;
                        locDet[3]=String.format("Faculty Office in %s",ofNum);
                    }
                }else{
                    // else set first entry as future status and current status as office
                    availOffice();
                    locDet[0]=offCoordString;
                    locDet[1]=String.format("Faculty Office in %s",ofNum);
                    futStatus=String.format("<p>Faculty will be teaching %s later on in %s from %02d:%02d HRS until %02d:%02d HRS</p></html>",rsFacDB.getString("COURSE"),rsFacDB.getString("ROOM"),sT1.get(Calendar.HOUR_OF_DAY),sT1.get(Calendar.MINUTE),eT1.get(Calendar.HOUR_OF_DAY),eT1.get(Calendar.MINUTE));
                    locDet[2]=String.format("%s,%s",rsFacDB.getString("LAT"),rsFacDB.getString("LON"));
                    locDet[3]=rsFacDB.getString("COURSE");
                }
//                System.out.println(locDet[0]);
//                System.out.println(locDet[1]);
//                System.out.println(locDet[2]);
//                System.out.println(locDet[3]);
            }
        }catch(SQLException se){
                System.out.println("SQL error for inProg catch:"+se);
        }
    }
    public String getCurStatus(){
        return(curStatus);
    }
    public String getFutStatus(){
        return(futStatus);
    }
    public String[] getLocDet(){
        return(locDet);
    }
    public void setOnlyOffice(boolean flag_1){
        if (flag_1==true){
            onlyOffFlag=true;
        } else{
            onlyOffFlag=false;
        }
    }
    public boolean getOnlyOffice(){
        return(onlyOffFlag);
    }
    public void setDayCode(int day){
        if(day==2){
            dayOfWeek="M";
        }else if(day==3){
            dayOfWeek="T";
        }else if(day==4){
            dayOfWeek="W";
        }else if(day==5){
            dayOfWeek="R";
        }else if(day==6){
            dayOfWeek="F";
        }else{
            dayOfWeek="NA";
        }
    }
    public boolean authenticate(String username, String password) {
        boolean adminAuthOk=false;
        try{
            // check username and password against DB
            // create query statement
            adminAuth=qrFacDB.executeQuery(String.format("SELECT PIN FROM APP.ADMINS WHERE upper(ADMINS.ID)='%s'",username.toUpperCase()));
            // check if result set has exactly one entry
            if(adminAuth.next()){
                // if yes then
                // if password provided match the result set then
                if (password.equals(adminAuth.getString("PIN"))) {
                    adminAuthOk=true;
                } else adminAuthOk=false;// else return false
            }else adminAuthOk=false;// else return false
        }catch(SQLException se){
            System.out.println("SQL error for authenticate catch:"+se);
        }
        return adminAuthOk;
    }
    public void runInfoGetterQuery(String query,int cols){
        try{
            int row=-1;
            // find how much info will be returned
            countInfo=qrFacDB.executeQuery("SELECT COUNT(*) FROM APP."+query);
            if(countInfo.next()){
            countInfoEntry=countInfo.getInt(1);
            }
            // create new string [][] with specific dims
            if(countInfoEntry>0){
                dbInfoData=new Object [countInfoEntry][cols];
            }else{
                dbInfoData=new Object [1][cols];
            }
            // run main query
            runInfoQuery=qrFacDB.executeQuery("SELECT * FROM APP."+query);
            // save result set in string [][]
            while(runInfoQuery.next()){
                row++;// Move this at the end of while and set row=0 to have one additional empty row
                if(query.equals("BASICDETAIL")){
                    dbInfoData[row][0]=runInfoQuery.getString("FIRSTNAME");
                    dbInfoData[row][1]=runInfoQuery.getString("LASTNAME");
                    dbInfoData[row][2]=runInfoQuery.getString("OFFNUM");
                    dbInfoData[row][3]=runInfoQuery.getString("OFFEXT");
                    dbInfoData[row][4]=runInfoQuery.getString("EMAIL");
                }else if(query.equals("LOCATION")){
                    dbInfoData[row][0]=runInfoQuery.getString("BLDG");
                    dbInfoData[row][1]=runInfoQuery.getString("LAT");
                    dbInfoData[row][2]=runInfoQuery.getString("LON");
                    dbInfoData[row][3]=runInfoQuery.getString("BLDGNAME");
                 }else if(query.equals("COURSES")){
                    dbInfoData[row][0]=runInfoQuery.getString("COURSE");
                    dbInfoData[row][1]=runInfoQuery.getString("INSTRUCTFIRSTNAME");
                    dbInfoData[row][2]=runInfoQuery.getString("INSTRUCTLASTNAME");
                    dbInfoData[row][3]=runInfoQuery.getString("DAYS");
                    dbInfoData[row][4]=runInfoQuery.getString("STARTTIME");
                    dbInfoData[row][5]=runInfoQuery.getString("ENDTIME");
                    dbInfoData[row][6]=runInfoQuery.getString("ROOM");
                    dbInfoData[row][7]=runInfoQuery.getString("BLDG");
                }else{
                     System.out.println("Not yet implemented!");
                }
//                row++;
            }
        }catch(SQLException se){
            System.out.println("SQL error for runQuery catch:"+se);
        }
    }
    public Object [][] getDBInfoData(){
        return dbInfoData;
    }
    public void nullDBInfoData(){
        dbInfoData=null;
    }
}