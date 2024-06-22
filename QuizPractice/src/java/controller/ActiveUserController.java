package controller;

// Import necessary classes
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import jakarta.servlet.http.HttpServlet;
import util.security.CodeVerify;

// Controller class for activating user accounts
@WebServlet("/active")
public class ActiveUserController extends HttpServlet {

    // Handles GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the current session
        HttpSession session = request.getSession();

        // Get an instance of UserDAO
        UserDAO userDAO = UserDAO.getInstance();

        // Get the token parameter from the request
        String token = request.getParameter("token");

        // Find the user by the provided token
        User user = userDAO.findUserByToken(token);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found account!");
            return;
        }

        // Get expiry time from request
        String time = request.getParameter("time");
        long expiryTime = Long.parseLong(time);
        long currentTime = System.currentTimeMillis();
        if (currentTime > expiryTime) {
            String newToken = CodeVerify.generateVerificationCode();
            userDAO.UpdateTokenByEmail(newToken, user.getEmail());
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Token no longer valid");
            return;
        }

        // Generate a new verification token
        String newToken = CodeVerify.generateVerificationCode();

        // Store the user object in the session
        session.setAttribute("user", user);

        // Update the user's status in the database using the token
        userDAO.UpdateStatusByToken(token);

        // Update the user's token in the database with the new token
        userDAO.UpdateTokenByEmail(newToken, user.getEmail());

        // Redirect the user to the home page
        response.sendRedirect("home");

    }

    // Handles POST requests by forwarding them to the doGet method
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // Provides a short description of the servlet
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
