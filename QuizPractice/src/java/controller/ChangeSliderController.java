package controller;

import com.google.gson.Gson;
import dal.SliderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/changeSlider")
public class ChangeSliderController extends HttpServlet {

    private final Gson gson = new Gson();
    private final SliderDAO sliderDAO = SliderDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get the slider ID from the request
        String idStr = request.getParameter("id");
        
        if (idStr == null || idStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new ResponseMessage("Invalid slider ID")));
            return;
        }

        int sliderId;
        try {
            sliderId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new ResponseMessage("Invalid slider ID format")));
            return;
        }

        // Toggle the slider status in the database
        int status = sliderDAO.getStatus(sliderId);
        int statusChange = (status == 1) ? 0 : 1;
        boolean success = sliderDAO.toggleSliderStatus(sliderId, statusChange);

        if (success) {
            response.getWriter().write(gson.toJson(new ResponseMessage("Status changed successfully")));
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(new ResponseMessage("Failed to change status")));
        }
         
    }

    // Helper class for JSON response
    private static class ResponseMessage {
        private final String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}