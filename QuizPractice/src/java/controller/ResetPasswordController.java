/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import util.mail.Mail;
import util.security.CodeVerify;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {

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
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
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
    @SuppressWarnings("all")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        UserDAO userDAO = UserDAO.getInstance();

        String otpvalue = "";

        User user = new User();

        String activeLink = request.getScheme() + "://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + "/QuizPractice/new-password";

        HttpSession mySession = request.getSession();
        if (!userDAO.checkExistByEmail(email)) {
            String token = CodeVerify.generateVerificationCode(); // Generate a verification token
            user.setToken(token);
            if (Mail.sendMailVerifyResetPassword(email, token, activeLink)) {

                mySession.setAttribute("email", email);
                request.getRequestDispatcher("active.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Error email sending!");
                request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Email doesn't exist!");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
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
