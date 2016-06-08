<%-- 
    Document   : editschoolstudent
    Created on : 25-Mar-2014, 16:47:13
    Author     : vtv13qau
--%>

<%@page import="SchoolMatesPackage.SchoolStudent"%>
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
        <h1>Hello World!</h1>
         <%  //a list of all schools
            ArrayList<School> schoolList = School.listAll();
            Student loggedInStudent = Student.getFromSession(session);
            //get attendance from the currently logged student and put it into form
            int studentID = loggedInStudent.getID();
            ArrayList<SchoolStudent> attendanceList = SchoolStudent.getAllAttendance(studentID);
            int schoolID;
            School school;
            String schoolName;
        %>
        
        
         <p>My schools: <% for (SchoolStudent s: attendanceList){
                schoolID = s.getSchoolID();
                school = School.findByID(schoolID);
                schoolName = school.getName();
                //generate links to these schools' profiles - always pass id within values (in this case school's id)
                
            %>   
            <br> <a href="schoolprofile.jsp?id=<%=schoolID%>"><%=schoolName%></a> <table border ="1"> <tr><td><%=s.getStartYear()%></td><td><%=s.getEndYear()%></td></tr></table>
                
                <%}%>
        
        
        
        <p> School : <select name="schoolID">

                    <% //loop through the school list and populate the dropdown list
                        for (School s : schoolList) {
                            int ID = s.getID();
                            String name = s.getName();

                    %>
                    <option value="<%=ID%>" ><%=name%></option> 
                    <% }%>                 
                </select>
            <p> Start year: <input type="text" name="startYear"> </p>
            <p> End year: <input type="text" name="endYear"> </p>
    </body>
</html>
