package controller;

import dal.SliderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Slider;

@WebServlet("/editSlider")
public class EditSliderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        SliderDAO sliderDAO = SliderDAO.getInstance();
        Slider s = sliderDAO.getSliderById(id);
        
        request.setAttribute("slider", s);

        request.getRequestDispatcher("sliderDetailManager.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String subTitle = request.getParameter("subTitle");
        String content = request.getParameter("content");
        String image = request.getParameter("image");
        String linkUrl = request.getParameter("linkUrl");
        int status = Integer.parseInt(request.getParameter("status"));
        int id = Integer.parseInt(request.getParameter("id"));
        
        //  public boolean editSlider(String title, String subTitle, String content, String image, String linkUrl, int status, int sliderId) {
        SliderDAO sliderDAO = SliderDAO.getInstance();
        boolean s = sliderDAO.editSlider(title, subTitle, content, image, linkUrl, status, id);
        if(s){
           response.sendRedirect("sliderManager");
        }else{
            response.sendRedirect("error.jsp");
        }

    }

}