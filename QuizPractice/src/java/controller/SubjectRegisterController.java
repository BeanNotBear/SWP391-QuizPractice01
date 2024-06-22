package controller;

import dal.SubjectDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PricePackage;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import util.mail.Mail;
import util.security.CodeVerify;

@WebServlet(name = "SubjectRegisterController", urlPatterns = {"/subjectRegister", "/checkEmail"})
public class SubjectRegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            String subjectName = request.getParameter("subjectName");

            SubjectDAO subjectDAO = SubjectDAO.getInstance();
            List<PricePackage> listPricePackage = subjectDAO.getPricePackageBySubjectId(subjectId);

            // Set listPricePackage into request attribute
            request.setAttribute("listPricePackage", listPricePackage);
            request.setAttribute("subjectName", subjectName);
            request.setAttribute("subjectId", subjectId);

            // Forward request to JSP to display modal
            request.getRequestDispatcher("/layout/SubjectRegisterPopUp.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid subject ID format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if ("/subjectRegister".equals(servletPath)) {
            registerSubject(request, response);
        } else if ("/checkEmail".equals(servletPath)) {
            checkEmail(request, response);
        }
    }

    private void registerSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();
        UserDAO userDAO = UserDAO.getInstance();
        try {
            SubjectDAO subjectDAO = SubjectDAO.getInstance();
            int pricePackageId = Integer.parseInt(request.getParameter("pricePackageId"));
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));

            // If user is logged in, directly add to database
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                subjectDAO.addNewSubjectRegister(subjectId, user.getUserId(), pricePackageId);
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Subject registration successful.");
            } else {
                // If not logged in, create new user and then add to database
                String userName = request.getParameter("userName");
                String email = request.getParameter("email");
                String phoneNumber = request.getParameter("phoneNumber");
                String gender = request.getParameter("gender");

                // Check if email already exists
                boolean emailExists = subjectDAO.checkEmailExists(email);
                if (emailExists) {
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "Email already exists !!");
                    out.print(jsonResponse.toString());
                    out.flush();
                    return;
                }

                // Generate a verification token
                String token = CodeVerify.generateVerificationCode();
                // Add new user with the token
                subjectDAO.addNewUser(userName, email, phoneNumber, gender, token);

                // Get the generated userId
                int userId = subjectDAO.getLastUserId();
                subjectDAO.addNewSubjectRegister(subjectId, userId, pricePackageId);

                // Construct the activation link
                String activeLink = request.getScheme() + "://"
                        + request.getServerName()
                        + ":"
                        + request.getServerPort()
                        + "/QuizPractice/active";
                // Send verification email
                Mail.sendMailVerify(email, token, activeLink);
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Register successful - Please check your email to verify your account.");
            }
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                jsonResponse.put("status", "error");
                jsonResponse.put("message", ex.getMessage());
            } catch (JSONException ex1) {
                Logger.getLogger(SubjectRegisterController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        out.print(jsonResponse.toString());
        out.flush();
    }

    private void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String email = request.getParameter("email");

            SubjectDAO subjectDAO = SubjectDAO.getInstance();
            boolean emailExists = subjectDAO.checkEmailExists(email);

            jsonResponse.put("exists", emailExists); // Đảm bảo trả về JSON có thuộc tính exists
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                jsonResponse.put("exists", false);
            } catch (JSONException ex1) {
                Logger.getLogger(SubjectRegisterController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        out.print(jsonResponse.toString());
        out.flush();
    }
}