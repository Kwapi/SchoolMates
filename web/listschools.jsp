<%-- 
    Document   : listSchools
    Created on : 17-Mar-2014, 15:55:28
    Author     : vtv13qau
--%>

<%@page import="SchoolMatesPackage.School"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Schools</title>
    </head>
    <body>
        <h1>All schools</h1>
        <% ArrayList<School> schoolList = School.listAll(); 
           for (School s : schoolList){%>
               <p><a href="schoolprofile.jsp?id=<%=s.getID()%>"><%=s.getName()%><a>
   <%}%>
        
    </body>
</html>
