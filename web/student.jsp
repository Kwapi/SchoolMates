<%-- 
    Document   : student
    Created on : 18-Mar-2014, 13:49:36
    Author     : vtv13qau
--%>

<%@page import="java.io.File"%>
<%@page import="SchoolMatesPackage.School"%>
<%@page import="java.util.ArrayList"%>
<%@page import="SchoolMatesPackage.SchoolStudent"%>
<%@page import="SchoolMatesPackage.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Profile</title>
    </head>
    <body>
        <%  Student loggedStudent = Student.getFromSession(session);
            int loggedStudentID = loggedStudent.getID();
            int studentID = Integer.parseInt(request.getParameter("id"));
            Student profileStudent = Student.findByID(studentID);
            // see if the profile picture is stored on the server
            // if not display default picture 
                String photoURL = "data/student"+studentID+".jpg";
                String fileName = "student" + studentID+".jpg"; 
                String filePath1 = "U:\\SchoolMates\\SchoolMates\\photos\\" + fileName;
                String filePath2 = "U:\\SchoolMates\\SchoolMates\\photos\\student_default.png" ;
                if (new File(filePath1).exists()){
        %>
        <img src="<%=filePath1%>" alt="some_text">  
        
        <%}else{%>
        <img src="<%=filePath2%>" alt="some_text">
        <%}%>
            
        <h1><%=profileStudent.getFullName()%>
                  

</h1>
            <p>About me: <%=profileStudent.getAboutMe()%>
                <%  //get the list of schools this student attended
                    ArrayList<SchoolStudent> list = SchoolStudent.getAllAttendance(studentID);
                    School school;
                    int schoolID;
                    String schoolName;
                %>
                    
                
            <p>My schools: <% for (SchoolStudent s: list){
                schoolID = s.getSchoolID();
                school = School.findByID(schoolID);
                schoolName = school.getName();
                //generate links to these schools' profiles - always pass id within values (in this case school's id)
                
            %>   
            <br> <a href="schoolprofile.jsp?id=<%=schoolID%>"><%=schoolName%></a> <table border ="1"> <tr><td><%=s.getStartYear()%></td><td><%=s.getEndYear()%></td></tr></table>
                
                <%}%>
                
            <% if (loggedStudentID != studentID){%>
            <h1> Send a message </h1>
            <form name="newMessage" action="CreateMessageController" method="POST">
                    <p>Title: <input type="text" name="title">
                    <p>Message:  <input type="text" name="message">
                    <input type="hidden" name="receiverID" value="<%=studentID%>">    
                    <input type="submit" value="SEND">
                </form>                   
                               
            <%}%>
    </body>
</html>
