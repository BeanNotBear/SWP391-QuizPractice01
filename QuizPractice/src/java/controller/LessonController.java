package controller;

import com.google.gson.Gson;
import dal.PracticeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/lesson")
public class LessonController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String subject = request.getParameter("subject");
            PracticeDAO practiceDAO = PracticeDAO.getInstance();
            List<String> lessons = practiceDAO.getLessonBySubject(subject);

            // Chuyển danh sách bài học thành chuỗi JSON
            Gson gson = new Gson();
            String jsonLessons = gson.toJson(lessons);

            // Gửi danh sách bài học dưới dạng JSON trong phản hồi HTTP
            out.println(jsonLessons);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
