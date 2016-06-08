<%-- 
    Document   : schoolprofile
    Created on : 17-Mar-2014, 16:03:25
    Author     : vtv13qau
--%>

<%@page import="java.io.File"%>
<%@page import="SchoolMatesPackage.SchoolStudent"%>
<%@page import="SchoolMatesPackage.Student"%>
<%@page import="SchoolMatesPackage.School"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>School profile</title>
        
    </head>
    <body>
        <%
            int schoolID = Integer.parseInt(request.getParameter("id")); //getting this school's id
            School school = School.findByID(schoolID); //getting this School object 
            // see if the profile picture is stored on the server
                // if not display default picture
                
                String fileName = "school" + schoolID+".jpg"; 
                String filePath1 = "U:\\SchoolMates\\SchoolMates\\photos\\" + fileName;
                String filePath2 = "U:\\SchoolMates\\SchoolMates\\photos\\school_default.jpg" ;
                if (new File(filePath1).exists()){
        %>
        <img src="<%=filePath1%>" alt="some_text">  
        
        <%}else{%>
        <img src="<%=filePath2%>" alt="some_text">
        <%}%>
        
        <h1><%=school.getName()%></h1>
        <p> Type: <%=school.getType()%>
        <p> Address: <%=school.getStreet()%>, <%=school.getCity()%>, <%=school.getPostCode()%> 
        <p> Website: <%=school.getWebsite()%>

                    
        <%
            Student student = Student.getFromSession(session);  //getting currently logged in student
            if (student!= null){
                int studentID = student.getID(); //getting their id 
                if (SchoolStudent.getAttendance(studentID, schoolID)!=null){ //checking if the logged in student attended this school
        %>
                    <form name="findSchoolMates" action="FindSchoolMatesController" method="POST">
                        <input type="hidden" name="school" value ="<%=school.getID()%>">
                        <p><input type="submit" value="Find your SchoolMates from this school">
                    </form>
                <%}
            }%>
        
        
        
            
            
    </body>
</html>
