
package SchoolMatesPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vtv13qau
 */
@WebServlet(name = "FindSchoolMatesController", urlPatterns = {"/FindSchoolMatesController"})
public class FindSchoolMatesController extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(true);
            try {                    
                                
                int schoolID = Integer.parseInt(request.getParameter("school"));                      
                //getting info about currently logged in student         
                Student currentStudent = Student.getFromSession(session);
                int studentID = currentStudent.getID();
                //getting current student's attendance in previously chosen school
                SchoolStudent schoolStudent = SchoolStudent.getAttendance(studentID, schoolID);
                int startYear = schoolStudent.getStartYear();
                int endYear = schoolStudent.getEndYear();
                
                //getting list of all students who are this student's schoolmates and passing it onto the next page
                ArrayList<Student> list = Student.findSchoolMates(schoolID, startYear, endYear, studentID);
                session.setAttribute("schoolMates", list);
                request.getRequestDispatcher("schoolmates.jsp").forward(request, response);
                
            } catch (Exception e) {
                Logger.getLogger(FindSchoolMatesController.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FindSchoolMatesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FindSchoolMatesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
