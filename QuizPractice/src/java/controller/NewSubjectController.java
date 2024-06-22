package controller;

import dal.SubjectDAO;
import dal.UserDAO;
import dto.DimensionDTO;
import dto.ExpertDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import model.User;
import org.json.JSONObject;

@WebServlet("/newSubject")
public class NewSubjectController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/QuizPractice/"); // Thay đổi đường dẫn này tùy theo trang đăng nhập của bạn
            return;
        }
        if(user.getRoleId() != 2) {
            response.sendError(404);
            return;
        }
        SubjectDAO subjectDAO = SubjectDAO.getInstance();
        List<DimensionDTO> dimensionDTOs = new ArrayList<>();
        dimensionDTOs = subjectDAO.getListDimensionDTO();
        List<ExpertDTO> experts = UserDAO.getInstance().findAllExpert();
        request.setAttribute("experts", experts);
        request.setAttribute("dimensions", dimensionDTOs);
        request.getRequestDispatcher("newSubject.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO subjectDAO = SubjectDAO.getInstance();
        String img = request.getParameter("img");
        System.out.println(img);
        String name = request.getParameter("name");
        System.out.println(name);
        int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
        System.out.println(dimensionId);
        int ownerId = Integer.parseInt(request.getParameter("expertId"));
        System.out.println(ownerId);
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        System.out.println(statusId);
        boolean feature = request.getParameter("feature").equalsIgnoreCase("on");
        System.out.println(feature);
        String description = request.getParameter("description");
        System.out.println(description);
        JSONObject jsonResponse = new JSONObject();

        //creator_id
        //creater_at
        //status=1
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/QuizPractice/"); // Thay đổi đường dẫn này tùy theo trang đăng nhập của bạn
            return;
        } else {
            boolean flag = true;
            if(subjectDAO.checkExpertTeachSameSubject(name, ownerId)) {
                try {
                    flag = false;
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "This expert already teach this subject, please choose another an subject or expert");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            boolean row = false;
            if(flag) {
                row = subjectDAO.insert(name, img, dimensionId, ownerId, statusId, feature, description);
            }
            try {
                if (row) {
                    jsonResponse.put("status", "success");
                    jsonResponse.put("message", "Add a new subject successfully!");
                } else if(flag && !row){
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "Add a new subject fail!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

}
