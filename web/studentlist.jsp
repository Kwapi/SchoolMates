<%-- 
    Document   : studentlist
    Created on : 18-Mar-2014, 15:50:16
    Author     : vtv13qau
--%>

<%@page import="SchoolMatesPackage.School"%>
<%@page import="java.util.ArrayList"%>
<%@page import="SchoolMatesPackage.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student list</title>
    </head>
    <body>
        
        <% int schoolID = Integer.parseInt(request.getParameter("id"));
           School school = School.findByID(schoolID);%>
           <h1>List of all students that attended <%=school.getName()%></h1>
           
           
           <%
           ArrayList<Student> list = Student.listAllFromSchool(schoolID);
           if(list!=null){
                   
           int studentID;
           String studentName;
           for (Student s: list){
               studentID = s.getID();
               studentName = s.getFullName();
           
        %>
        <p><a href="student.jsp?id=<%=studentID%>"> <%=s.getFullName()%></a>
        <%}%>
        <%}else{%>
               No one attended this school
         <%  }
%>        
        
    </body>
</html>
