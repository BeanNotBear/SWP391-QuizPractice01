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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import util.security.Security;
import util.validation.Validation;

@WebServlet("/change-password")
public class ChangePasswordController extends HttpServlet {

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
        doPost(request, response);
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
        HttpSession session = request.getSession();

        String oldPassword = request.getParameter("old-password");
        String newPassword = request.getParameter("new-password");
        String confirmPassword = request.getParameter("confirm-password");

        UserDAO userDAO = UserDAO.getInstance();

        User user = (User) session.getAttribute("user");

        Validation validation = Validation.getInstance();

        JSONObject jsonResponse = new JSONObject();

        boolean isValidInformation = true; // Flag to check if the information is valid
        try {
            if (!validation.CheckFormatPassword(newPassword)) {
                isValidInformation = false;
                jsonResponse.put("err",
                        "Your input must contain at least one uppercase letter,"
                        + " one lowercase letter, one digit, "
                        + "one special character, "
                        + "and be at least 8 characters long.");
            }

            // Validate if passwords match
            if (!newPassword.equals(confirmPassword)) {
                isValidInformation = false;
                jsonResponse.put("err", "Password and Confirm password do not match");
            }

            if (isValidInformation && Security.encryptToSHA512(oldPassword).equals(user.getPassword()) 
                    && oldPassword != null 
                    && newPassword != null 
                    && confirmPassword != null 
                    && newPassword.equals(confirmPassword) 
                    && validation.CheckFormatPassword(newPassword)) {
                jsonResponse.put("status", "success");
                userDAO.updatePassword(newPassword, user.getEmail());
            } else {
                jsonResponse.put("status", "error");
            }
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("home");
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
