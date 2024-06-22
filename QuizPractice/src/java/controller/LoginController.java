package controller;

// Import necessary classes
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import util.mail.Mail;
import util.security.CodeVerify;
import util.security.Security;

// Servlet class for handling login requests
@WebServlet("/login")
public class LoginController extends HttpServlet {

    // Handles GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    // Handles POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve email and password parameters from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        JSONObject jsonResponse = new JSONObject();
        boolean isSuccess = true;
        boolean isActive = true;

        // Encrypt the password using SHA-512
        password = Security.encryptToSHA512(password);

        System.out.println(email);
        System.out.println(password);

        // Retrieve the current session
        HttpSession session = request.getSession();

        // Get an instance of UserDAO
        UserDAO userDAO = UserDAO.getInstance();

        // Find the user by email and encrypted password
        User user = userDAO.findUserByEmailAndPassword(email, password);

        // Check if the user exists
        if (user == null) {
            isSuccess = false;
            System.out.println("false");
            // If user is not found, set an error message and forward to login.jsp
        } else {

            // Check the user's status
            if (user.getStatusID() == 1) {
                isActive = false;
                // If user status is 1 (inactive), generate a verification token
                String token = CodeVerify.generateVerificationCode();

                // Update the token for the user in the database
                int r = userDAO.UpdateTokenByEmail(token, email);
                System.out.println(r);

                // Create an activation link
                String activeLink = request.getScheme() + "://"
                        + request.getServerName()
                        + ":"
                        + request.getServerPort()
                        + "/QuizPractice/active";

                // Send a verification email to the user
                Mail.sendMailVerify(email, token, activeLink);
            } else {
                // If user is found, set the user object in the session
                session.setAttribute("user", user);
            }
        }
        try {
            if (isSuccess) {
                if (isActive) {
                    jsonResponse.put("status", "success");
                } else {
                    jsonResponse.put("status", "unactive");
                }
                jsonResponse.put("message", "Login Successfully!");
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Email or password is not correct!");
            }
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // Provides a short description of the servlet
    @Override
    public String getServletInfo() {
        return "LoginController handles user login requests and redirects appropriately based on user status.";
    }
}
