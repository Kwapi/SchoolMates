<%-- 
    Document   : editprofile
    Created on : 20-Mar-2014, 13:32:48
    Author     : vtv13qau
--%>

<%@page import="SchoolMatesPackage.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% Student student = Student.getFromSession(session);%>
        <h1> Profile </h1>
        <p> Name: <%=student.getName()%>
        <p> Surname: <%=student.getSurname()%>
        <p> School surname: <%=student.getSchoolSurname()%>
        <p> About: <%=student.getAboutMe()%>
            
        <h1>Edit profile</h1>
        <form name="editAccount" action="UpdateAccountController" method="POST">
            <p> E-mail: <input type="text" name="email"> </p>
            <p> Password: <input type="password" name="password"> </p>
            <p> Name: <input type="text" name="name"> </p>
            <p> Surname: <input type="text" name="surname"> </p>
            <p> School surname: <input type="text" name="schoolSurname"></p>
            <p> About me: <input type="text" name="aboutMe" maxlength="500"></p>
            <p> <input type="submit" value="EDIT">
    </body>
</html>
