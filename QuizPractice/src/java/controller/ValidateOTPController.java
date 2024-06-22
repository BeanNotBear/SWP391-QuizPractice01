/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import util.security.CodeVerify;

@WebServlet("/validateotp")
public class ValidateOTPController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ValidateOTP</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ValidateOTP at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Get the token parameter from the request
        String token = request.getParameter("token");

        // Get an instance of UserDAO
        UserDAO userDAO = UserDAO.getInstance();

        // Find the user by the provided token
        User user = userDAO.findUserByToken(token);
        
        // Check if a user with the given token exists
        if(user != null) {
            // Generate a new verification token
            String newToken = CodeVerify.generateVerificationCode();
            
            // Store the user object in the session
            session.setAttribute("user", user);
            
            // Update the user's status in the database using the token
            userDAO.UpdateStatusByToken(token);
            
            // Update the user's token in the database with the new token
            userDAO.UpdateTokenByEmail(newToken, user.getEmail());
            
            // Redirect the user to the home page
            response.sendRedirect("new-password");
        } else {
            // If no user is found, send a 404 error response
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String value = request.getParameter("otp");
        
        HttpSession session = request.getSession();
        
        String otp = (String) session.getAttribute("otp");
        
        if (value.equals(otp)) {

            request.setAttribute("email", request.getParameter("email"));
            request.setAttribute("status", "Successfully");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);

        } else {
            request.setAttribute("message", "Wrong OTP!");
            request.getRequestDispatcher("enterOTP.jsp").forward(request, response);
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
