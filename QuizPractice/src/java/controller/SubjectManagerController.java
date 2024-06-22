package controller;

import dal.SubjectDAO;
import dto.DimensionDTO;
import dto.SubjectManagerDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.User;

@WebServlet("/subjectManager")
public class SubjectManagerController extends HttpServlet {

    int page = 1;
    String search = null;
    String categories = null;
    String statuses = null;
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.search = null;
        this.categories = null;
        this.statuses = null;
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int page = 1;
        int recordPerPage = 5;
        String search = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || (user.getRoleId() != 2 && user.getRoleId() != 3)) {
            response.sendRedirect("home");
            return;
        }
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
                this.page = page;
            }
            if (request.getParameter("subjectName") != null) {
                search = request.getParameter("subjectName");
                this.search = search;
            }
            if(request.getParameter("categories") != null && !request.getParameter("categories").trim().isEmpty()) {
                String cates = request.getParameter("categories");
                this.categories = cates;
            }
            if(request.getParameter("status") != null && !request.getParameter("status").trim().isEmpty()) {
                String status = request.getParameter("status");
                this.statuses = status;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("category: " + categories);
        System.out.println("status: " + statuses);
        int totalRecords = SubjectDAO.getInstance().geTotalRecords(user.getRoleId(), user.getUserId(), this.search, this.categories, this.statuses);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordPerPage);
        System.out.println(totalPages);
        List<DimensionDTO> dimensions = SubjectDAO.getInstance().getListDimensionDTO();
        List<SubjectManagerDTO> subjects = SubjectDAO.getInstance().getSubjectsPagination(user.getRoleId(), user.getUserId(), page, recordPerPage, this.search, this.categories, this.statuses);
        request.setAttribute("search", this.search);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("dimensions", dimensions);
        request.setAttribute("listSubject", subjects);
        request.getRequestDispatcher("subjectManager.jsp").forward(request, response);
    }
}
