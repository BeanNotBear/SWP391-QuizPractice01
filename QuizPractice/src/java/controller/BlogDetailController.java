package controller;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Blog;
import model.Category;

@WebServlet(name = "BlogDetailServlet", urlPatterns = {"/blogdetail"})
public class BlogDetailController extends HttpServlet {

    private int previous_Id;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String idd = request.getParameter("pid");
        int id;

        BlogDAO dao = BlogDAO.getInstance();
        Blog blog = null;
        Blog blogHot = dao.findNewPost();
        List<Category> listcate = dao.getAllCategories();
        try {
            id = Integer.parseInt(idd);
            previous_Id = id;
            blog = dao.getBlogById(previous_Id);
            if (blog != null) {
                request.setAttribute("detail", blog);
                request.setAttribute("nee", blogHot);
                request.setAttribute("listcatego", listcate);
                request.getRequestDispatcher("/blogdetail.jsp")
                        .forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            blog = dao.getBlogById(previous_Id);
            if (blog != null) {
                request.setAttribute("detail", blog);
                request.setAttribute("nee", blogHot);
                request.setAttribute("listcatego", listcate);
                request.getRequestDispatcher("/blogdetail.jsp")
                        .forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
