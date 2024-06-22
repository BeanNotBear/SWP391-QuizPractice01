/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name="ChangeSubjectStatusController", urlPatterns={"/changeSubjectStatus"})
public class ChangeSubjectStatusController extends HttpServlet {
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        SubjectDAO subjectDAO = SubjectDAO.getInstance();
        JSONObject jsonResponse = new JSONObject();
        String txtStatusId = request.getParameter("statusId");
        String txtSubjectId = request.getParameter("subjectId");
        int statusId = 0;
        int subjectId = 0;
        try {
            statusId = Integer.parseInt(txtStatusId);
            subjectId = Integer.parseInt(txtSubjectId);
            boolean isSuccess = subjectDAO.changeStatusSubjectBySubjectId(subjectId, statusId);
            if(isSuccess) {
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Change status successfully!");
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Change status fail!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
