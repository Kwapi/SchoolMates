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
@WebServlet(name = "UpdateAccountController", urlPatterns = {"/UpdateAccountController"})
public class UpdateAccountController extends HttpServlet {

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
                    //get parameters from the edit form
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    String schoolSurname = request.getParameter("schoolSurname");
                    String aboutMe = request.getParameter("aboutMe");
                    //get the student from session
                    Student student = Student.getFromSession(session);
                    
                    //check if the user input any values. If they did - update beans
                    if (!email.isEmpty()){
                        student.setEmail(email);
                    }
                    if (!password.isEmpty()){
                        student.setPassword(password);
                    }
                    if (!name.isEmpty()){
                        student.setName(name);
                    }
                    if (!surname.isEmpty()){
                        student.setSurname(surname);
                    }
                    if (!schoolSurname.isEmpty()){
                        student.setSchoolSurname(schoolSurname);
                    }
                    if (!aboutMe.isEmpty()){
                        student.setAboutMe(aboutMe);
                    }
                    
                    //updated database
                    student.persist();
                    
                                        
                    
                    request.getRequestDispatcher("profile.jsp").forward(request, response);

            } catch (Exception e) {
                Logger.getLogger(UpdateAccountController.class.getName()).log(Level.SEVERE, null, e);
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
