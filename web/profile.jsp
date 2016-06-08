<%-- 
    Document   : welcome
    Created on : 11-Mar-2014, 14:51:35
    Author     : vtv13qau
--%>

<%@page import="java.net.URL"%>
<%@page import="java.io.File"%>
<%@page import="SchoolMatesPackage.Message"%>
<%@page import="SchoolMatesPackage.Student"%>
<%@page import="SchoolMatesPackage.School"%>
<%@page import="java.util.ArrayList"%>
<%@page import="SchoolMatesPackage.SchoolStudent"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>


        <% Student student = Student.getFromSession(session);
                int studentID = student.getID();
                
                // see if the profile picture is stored on the server
                // if not display default picture
                
                String fileName = "student" + studentID+".jpg"; 
                String filePath1 = "U:\\SchoolMates\\SchoolMates\\photos\\" + fileName;
                String filePath2 = "U:\\SchoolMates\\SchoolMates\\photos\\student_default.png" ;
                if (new File(filePath1).exists()){
        %>
        <img src="<%=filePath1%>" alt="some_text">  
        
        <%}else{%>
        <img src="<%=filePath2%>" alt="some_text">
        <%}%>
        <h1> Profile <a href="editprofile.jsp">EDIT</a></h1>
        <p> Name: <%=student.getName()%>
        <p> Surname: <%=student.getSurname()%>
        <p> School surname: <%=student.getSchoolSurname()%>
        <p> About: <%=student.getAboutMe()%>
            
        <p> <a href="photouploadtest.jsp"> Upload your photo </a>
        <h2> Your schools:</h2>
        <% //get attendance from the currently logged student 
            
            ArrayList<SchoolStudent> attendanceList = SchoolStudent.getAllAttendance(studentID);
            int schoolID;
            School school;
            String schoolName;
            
           
            for (SchoolStudent s: attendanceList){
                schoolID = s.getSchoolID();
                school = School.findByID(schoolID);
                schoolName = school.getName();
                //generate links to these schools' profiles - always pass id within values (in this case school's id)
                
            %>   
            <br> <a href="schoolprofile.jsp?id=<%=schoolID%>"><%=schoolName%></a> <table border ="1"> <tr><td><%=s.getStartYear()%></td><td><%=s.getEndYear()%></td></tr></table>
        <form method="post" action="DeleteAttendance">
            <input type="hidden" name="schoolID" value="<%=schoolID%>"/>
            <input type="submit" value="Remove"/>
        </form>
                <%}%>
         
                <h2> Add new school </h2>
         <form name="newAttendance" action="NewAttendanceController" method="POST">
               <p> School : <select name="schoolID">

                    <% 
                        //a list of all schools
                        ArrayList<School> schoolList = School.listAll();
                        //loop through the school list and populate the dropdown list
                        for (School s : schoolList) {
                            int ID = s.getID();
                            String name = s.getName();

                    %>
                    <option value="<%=ID%>" ><%=name%></option> 
                    <% }%>                 
                </select>
            <p> Start year: <input type="text" name="startYear"> </p>
            <p> End year: <input type="text" name="endYear"> </p>
            <p> <input type="submit" value="Create">  </p>
        </form>       
                    
                    
                

                
                <h1> Inbox </h1>
                <%  ArrayList<Student> senders = Message.listAllSenders(studentID); 
                    ArrayList<Message> messages = Message.listAllReceivedMessages(studentID);
                    //both have the same size
                    
                    //see if there are any messages
                    if(senders!=null){
                    
                    for (int i = 0; i<senders.size(); i++){//looping through the list of messages and corresponding senders
                        
                        if(messages.get(i).isRead()){    //use setAsRead() method to mark as read
                            %> THIS MESSAGE WAS READ <%
                        }
                        else{
                            %> THIS MESSAGE WAS NOT READ <%
                        }%>
                        <p> Date sent: <%=messages.get(i).getTimeSent()%> </p>
                        <p> From:  <%=senders.get(i).getFullName()%></p>
                        <p> Subject: <b><%=messages.get(i).getTitle()%></b></p>
                        <p> Body: <%=messages.get(i).getMessage()%></p>
                            <br><br>
                    <%}%>
                
                    
                <%}else{ //no messages
                %> NO MESSAGES <%}%>
                
                <h1> Outbox </h1>
                <%  ArrayList<Student> receivers = Message.listAllReceivers(studentID); 
                    ArrayList<Message> rec_messages = Message.listAllSentMessages(studentID);
                    //both have the same size
                    
                    //see if there are any messages
                    if(receivers!=null){
                    
                    for (int i = 0; i<receivers.size(); i++){//looping through the list of sent  messages and corresponding receivers
                        
                        if(rec_messages.get(i).isRead()){    //use setAsRead() method to mark as read - FUTURE REFERENCE
                            %> THIS MESSAGE WAS READ <%
                        }
                        else{
                            %> THIS MESSAGE WAS NOT READ <%
                        }%>
                        <p> Date sent: <%=rec_messages.get(i).getTimeSent()%> </p>
                        <p> To:  <%=receivers.get(i).getFullName()%></p>
                        <p> Subject: <b><%=rec_messages.get(i).getTitle()%></b></p>
                        <p> Body: <%=rec_messages.get(i).getMessage()%></p>
                            <br><br>
                    <%}%>
                
                    
                <%}else{ //no messages
                %> NO MESSAGES <%}%>
                
                    
                    
                    
                
                



    </body>
</html>
