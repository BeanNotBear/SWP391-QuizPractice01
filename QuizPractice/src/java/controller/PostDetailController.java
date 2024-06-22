package controller;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import dto.BlogManagerDetailDTO;
import jakarta.servlet.annotation.WebServlet;
import model.Category;
import org.json.JSONObject;

@WebServlet(name = "PostDetailController", urlPatterns = {"/postdetail"})
public class PostDetailController extends HttpServlet {

    private int previous_id = -1;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int id = -1;

        try {
            if (idParam != null && !idParam.isEmpty()) {
                id = Integer.parseInt(idParam);
                previous_id = id;
            }
        } catch (NumberFormatException e) {
            // Log lỗi nếu cần thiết
        }

        if (previous_id == -1) {
            // ID không hợp lệ hoặc không được truyền vào
            // Chuyển hướng đến trang lỗi hoặc trang mặc định
            response.sendRedirect("errorPage.jsp"); // hoặc trang mặc định
            return;
        }

        BlogDAO blogDao = BlogDAO.getInstance();
        BlogManagerDetailDTO blog = blogDao.getBlogDetailDTOById(previous_id);

        if (blog == null) {
            // Blog không tồn tại với ID đã cho
            response.sendRedirect("errorPage.jsp"); // hoặc trang mặc định
            return;
        }

        List<Category> listCategory = blogDao.getAllCategories();

        request.setAttribute("listCategory", listCategory);
        request.setAttribute("blog", blog);
        request.getRequestDispatcher("blogDetailManager.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status2 = request.getParameter("status");
        boolean status = (status2 != null && status2.equalsIgnoreCase("on"));
        System.out.println(status);

        JSONObject jsonObject = new JSONObject();
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String categoryName = request.getParameter("categoryName");
        String content = request.getParameter("content");
        String thumbnail = request.getParameter("thumbnail");
        String briefinfo = request.getParameter("briefinfo");

        BlogManagerDetailDTO blog = new BlogManagerDetailDTO(id, title, categoryName, content, status, thumbnail, briefinfo);
        //System.out.println(blog);

        BlogDAO blogDao = BlogDAO.getInstance();
        boolean updated = blogDao.updateBlog(blog);

        try {
            if (updated) {
                String successMessage = "Update succesfully!";
                jsonObject.put("status", "success");
                jsonObject.put("message", successMessage);
            } else {
                jsonObject.put("status", "error");
                jsonObject.put("message", "Update fail!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonObject.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
