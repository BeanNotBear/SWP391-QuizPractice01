package controller;

import dal.SubjectDAO;
import dto.DimensionDTO;
import dto.SubjectPackagePriceDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Dimension;
import model.Subject;
import model.User;

@WebServlet("/subjectDetailExpert")
public class SubjectDetailExpertController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tempId = request.getParameter("id");
        int id = 0;
        if(tempId != null){
            id = Integer.parseInt(tempId);
        }
        else{
           request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        
        SubjectDAO subjectDAO = SubjectDAO.getInstance();
        List<DimensionDTO> dimensionDTOs = new ArrayList<>();
        dimensionDTOs = subjectDAO.getListDimensionDTO();
        request.setAttribute("dimensions", dimensionDTOs);
                
        List<Dimension> dimensionList = new ArrayList<>();
        dimensionList = subjectDAO.getListDimension();
        request.setAttribute("dimensionList", dimensionList);
        
        Subject subject = subjectDAO.getSubjectById(id);
        request.setAttribute("subject", subject);
        
        
        List<SubjectPackagePriceDTO> packageList = new ArrayList<>();
        packageList = subjectDAO.getListSubjectPackagePriceDTO(id);
         request.setAttribute("packageList", packageList);
        
        request.getRequestDispatcher("subjectDetailExpert.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO subjectDAO = SubjectDAO.getInstance();
        String tempId = request.getParameter("id");
        int id = 0;
        if(tempId != null){
            id = Integer.parseInt(tempId);
        }
        else{
           request.getRequestDispatcher("error.jsp").forward(request, response);
        }
       
        System.out.println(id);
        String img = request.getParameter("img");
        System.out.println(img);
        String name = request.getParameter("name");
        System.out.println(name);
        int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
        System.out.println(dimensionId);
        String description = request.getParameter("description");
        System.out.println(description);

        //creator_id
        //creater_at
        //status=1
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/QuizPractice/"); // Thay đổi đường dẫn này tùy theo trang đăng nhập của bạn
            return;
        } else {           
            boolean row = subjectDAO.updateSubject(id,name, img, dimensionId,description);
            if (row) {
                response.sendRedirect("subjectDetailExpert?id=" + id); // Thay đổi đường dẫn này tùy theo trang đăng nhập của bạn
                return;
            }else{
                response.sendRedirect("/QuizPractice/error.jsp");
            }
        }
    }

}