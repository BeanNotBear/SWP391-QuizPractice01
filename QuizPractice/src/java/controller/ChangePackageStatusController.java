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

@WebServlet("/changePackageStatus")
public class ChangePackageStatusController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int packageId = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        System.out.println(packageId + status + subjectId);
        SubjectDAO subjectDAO = SubjectDAO.getInstance();
        boolean result = false;
        if (status.equalsIgnoreCase("active")) {
            result = subjectDAO.addSubjectPricePackage(packageId, subjectId);
        } else {
            result = subjectDAO.deleteSubjectPricePackage(packageId, subjectId);
        }

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
