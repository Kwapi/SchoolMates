/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SchoolMatesPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "CreateAccountController", urlPatterns = {"/CreateAccountController"})
public class CreateAccountController extends HttpServlet {

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
            String email = request.getParameter("email");
            try {
                if (!(Student.exists(email))) { //if the email is not used then create a new Student

                    //get parameters and create a new Student object
                    String password = request.getParameter("password");
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    String schoolSurname = request.getParameter("schoolSurname");
                    Student newStudent = new Student(email, password, name, surname, schoolSurname);

                    //insert the new Student into the database
                    newStudent.persist();
                    
                    
                    //get data to create SchoolStudent object
                    int schoolID = Integer.parseInt(request.getParameter("schoolID"));
                    int studentID = newStudent.getID();
                    int startYear = Integer.parseInt(request.getParameter("startYear"));
                    int endYear = Integer.parseInt(request.getParameter("endYear"));
                    
                    SchoolStudent schoolStudent = new SchoolStudent(schoolID, studentID, startYear, endYear);
                    //insert the new SchoolStudent into the database
                    schoolStudent.persist();
                    
                    //insert the object into the session and continue onto the main page
                    newStudent.storeInSession(session);;
                    request.getRequestDispatcher("profile.jsp").forward(request, response);

                } else { //the email is already used - redirect the user to an error page
                    response.sendRedirect("emailused.jsp"); 

                }
            } catch (Exception e) {
                Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
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
