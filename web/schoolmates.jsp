<%-- 
    Document   : schoolmates
    Created on : 14-Mar-2014, 17:38:35
    Author     : vtv13qau
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="SchoolMatesPackage.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Your schoolmates from the chosen school</h1>

        <table border ="1">
            <%
                ArrayList<Student> schoolMates = (ArrayList<Student>) session.getAttribute("schoolMates");
                String name;
                String surname; 
                for (Student s: schoolMates){
                    name = s.getFullName();
                    
                    
            %>
            <tr>
            <tr><td><a href="student.jsp?id=<%=s.getID()%>"><%=name%></td></tr>
                
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
