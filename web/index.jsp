<%-- 
    Document   : index
    Created on : 07-Mar-2014, 16:45:04
    Author     : vtv13qau
--%>

<%@page import="SchoolMatesPackage.Student"%>
<%@page import="java.util.ArrayList"%>
<%@page import="SchoolMatesPackage.School"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        


        <%  //a list of all schools
            ArrayList<School> list = School.listAll();
            Student loggedInStudent = Student.getFromSession(session);
        %>
        <h1> IS STUDENT LOGGED IN - TEST </h1>
        <%if (loggedInStudent != null){%>
        <p> Hello, <%=loggedInStudent.getName()%>
        <p> <a href="profile.jsp"> Your profile </a>
        <%}else{%>
        <p> No one is logged in
        <%}%>
        
        <h1>TEST - CREATE NEW STUDENT</h1>
        <form name="newAccount" action="CreateAccountController" method="POST">
            <p> E-mail: <input type="text" name="email"> </p>
            <p> Password: <input type="password" name="password"> </p>
            <p> Name: <input type="text" name="name"> </p>
            <p> Surname: <input type="text" name="surname"> </p>
            <p> School surname (OPTIONAL): <input type="text" name="schoolSurname"></p>
            <p> School : <select name="schoolID">

                    <% //loop through the school list and populate the dropdown list
                        for (School s : list) {
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
                <h1> TEST - LOG IN </h1>
                <form name ="login" action="LoginController" method ="POST">
                <p> E-mail: <input type="text" name="email"> </p>
                <p> Password: <input type="password" name="password"> </p>    
                <p> <input type="submit" value="Log in">  </p>    
                </form>
                
                <h1> TEST - LOG OUT </h1>
                <form name ="logout" action="LogoutController" method ="POST">
                <p> <input type="submit" value="Log out">  </p>     
                </form>
                
                <h1> <a href="listschools.jsp" >TEST - Find schools </a></h1>
                
                <h1> TEST - Find friends</h1>
                <form name="findFriends" action="studentlist.jsp" method="POST">
            <select name="id">
                    <% //loop through the school list and populate the dropdown list
                        for (School s : list) {
                            int ID = s.getID();
                            String name = s.getName();
                    %>
                    <option value="<%=ID%>" ><%=name%></option> 
                    <% }%>                 
                </select>
                <p> <input type="submit" value="Search">  </p>
        </form>
                
                






    </body>
</html>
