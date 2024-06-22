package controller;

// Import necessary classes
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import util.mail.Mail;
import util.security.CodeVerify;
import util.security.Security;
import util.validation.Validation;

// Servlet class for handling user registration
@WebServlet("/register")
public class RegisterController extends HttpServlet {

    // Handles GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the register.jsp page
        doPost(request, response);
    }

    // Handles POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve registration parameters from the request
        String fullName = request.getParameter("fullName");
        System.out.println(fullName);
        String email = request.getParameter("email");
        System.out.println(email);
        String gender = request.getParameter("gender");
        System.out.println(gender);
        String phone = request.getParameter("phone");
        System.out.println(phone);
        String password = request.getParameter("password");
        System.out.println(password);
        String cfPassword = request.getParameter("cfPassword");
        System.out.println(cfPassword);

        boolean isValidInformation = true; // Flag to check if the information is valid
        JSONObject jsonResponse = new JSONObject();

        // Get an instance of UserDAO
        UserDAO userDAO = UserDAO.getInstance();

        // Get an instance of Validation
        Validation validation = Validation.getInstance();

        try {
            // Validate fullname
            if (fullName == null || fullName.isEmpty()) {
                isValidInformation = false;
                jsonResponse.put("err", "You must fill first name");
            }

            // Validate email format
            if (!validation.CheckFormatEmail(email)) {
                isValidInformation = false;
                jsonResponse.put("err", "Email is wrong format");
            }

            // Validate gender
            if (gender == null || gender.isEmpty()
                    || (!gender.equals("true") && !gender.equals("false"))) {
                isValidInformation = false;
                jsonResponse.put("err", "Gender must be male or female");
            }

            // Validate phone number format
            if (phone != null && !phone.isEmpty() && !validation.CheckFormatPhone(phone)) {
                isValidInformation = false;
                jsonResponse.put("err", "Phone is wrong format");
            }

            if (!userDAO.checkExistByEmail(email)) {
                isValidInformation = false;
                jsonResponse.put("err", "Email has been existed");
            }

            // Validate password format
            if (!validation.CheckFormatPassword(password)) {
                isValidInformation = false;
                jsonResponse.put("err",
                        "Your input must contain at least one uppercase letter,"
                        + " one lowercase letter, one digit, "
                        + "one special character, "
                        + "and be at least 8 characters long.");
            }

            // Validate if passwords match
            if (!password.equals(cfPassword)) {
                isValidInformation = false;
                jsonResponse.put("err", "Password and Confirm password do not match");
            }

            // If all information is valid
            if (isValidInformation) {
                try {
                    jsonResponse.put("status", "success");
                    // Create a new user object and set its properties
                    User user = new User();
                    user.setFullName(fullName);
                    user.setEmail(email);
                    user.setPhoneNumber(phone);
                    user.setGender(Boolean.parseBoolean(gender));
                    password = Security.encryptToSHA512(password); // Encrypt the password
                    user.setPassword(password);
                    String token = CodeVerify.generateVerificationCode(); // Generate a verification token
                    user.setToken(token);
                    userDAO.insert(user); // Insert the user into the database
                    // Create an activation link
                    String activeLink = request.getScheme() + "://"
                            + request.getServerName()
                            + ":"
                            + request.getServerPort()
                            + "/QuizPractice/active";

                    // Send a verification email
                    Mail.sendMailVerify(email, token, activeLink);

                } catch (Exception e) {
                    // Handle exceptions
                    e.printStackTrace();
                }
            } else {
                jsonResponse.put("status", "error");
            }
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "RegisterController handles user registration and validation.";
    }

}
