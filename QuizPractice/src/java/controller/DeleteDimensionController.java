package controller;

import com.google.gson.Gson;
import dal.SubjectDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Dimension;

@WebServlet("/deleteDimension")
public class DeleteDimensionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {      
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);

        SubjectDAO subjectDAO = SubjectDAO.getInstance();
        boolean result = subjectDAO.deleteDimension(id);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(new JsonResponse(result, null)));
    }

    private class JsonResponse {
        boolean success;
        Dimension dimension;

        public JsonResponse(boolean success, Dimension dimension) {
            this.success = success;
            this.dimension = dimension;
        }
    }
}