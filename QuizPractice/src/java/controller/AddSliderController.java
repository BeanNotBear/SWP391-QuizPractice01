package controller;

import com.google.gson.Gson;
import dal.SliderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/addSlider")
public class AddSliderController extends HttpServlet {

    private final Gson gson = new Gson();
    private final SliderDAO sliderDAO = SliderDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implement if needed, currently not used in this example
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

//      id: id,
//                                        title: title,
//                                        subTitle: subTitle,
//                                        content: content,
//                                        image: image,
//                                        linkUrl: linkUrl
        String title = request.getParameter("title");
        String subTitle = request.getParameter("subTitle");
        String content = request.getParameter("content");
        String image = request.getParameter("image");
        String linkUrl = request.getParameter("linkUrl");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.getWriter().write(gson.toJson(new ResponseMessage("User not logged in!")));
            return;
        }

        //  public boolean addSlider(String title, String subTitle, String content, String image, String linkUrl, int userId) {
        // Toggle the slider status in the database
       
        boolean success = sliderDAO.addSlider(title, subTitle, content, image, linkUrl, user.getUserId());

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