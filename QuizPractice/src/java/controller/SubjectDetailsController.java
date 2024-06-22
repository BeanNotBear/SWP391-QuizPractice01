package controller;

import dal.SubjectDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dto.SubjectDTO;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/subject-details")
public class SubjectDetailsController extends HttpServlet {

    private int previous_id;

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
        String subjectId = request.getParameter("id");
        int subId;
        SubjectDAO subjectDAO = SubjectDAO.getInstance();
        SubjectDTO subject = null;
        final String contexPath = request.getContextPath();
        final String domain = request.getLocalName();
        boolean status = false;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            int userId = 0;

            subId = Integer.parseInt(subjectId);
            previous_id = subId;
            subject = subjectDAO.findSubjectById(subId);
            if (subject != null) {
                if (user != null) {
                    userId = user.getUserId();
                    status = subjectDAO.checkSubjectRegisterById(previous_id, userId);
                    request.setAttribute("status", status);
                }
                request.setAttribute("subject", subject);
                request.getRequestDispatcher("/playlist.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            subject = subjectDAO.findSubjectById(previous_id);
            if (subject != null) {
                request.setAttribute("subject", subject);
                request.getRequestDispatcher("/playlist.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
