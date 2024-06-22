package dal;

import context.DBContext;
import dto.ExpertDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.security.Security;

// Data Access Object for User operations, extending DBContext to utilize database connections
public class UserDAO extends DBContext {

    // Singleton instance of UserDAO
    private static UserDAO instance;
    // Lock object for thread-safe singleton instantiation
    private static Object lockPad = new Object();
    private Logger logger = Logger.getLogger(UserDAO.class.getName());

    // Private constructor to prevent instantiation
    private UserDAO() {
    }

    // Returns the singleton instance of UserDAO
    public static UserDAO getInstance() {
        if (instance == null) {
            synchronized (lockPad) {
                if (instance == null) {
                    instance = new UserDAO();
                }
            }
        }
        return instance;
    }

    public boolean checkExistByEmail(String email) {
        boolean isDuplicate = true;
        String sql = "Select 1 From users where email =?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                isDuplicate = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    public User findUserById(int id) {
        String query = "SELECT * "
                + "FROM users "
                + "WHERE id = ?";
        User user = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setFullName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhoneNumber(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setProfileImg(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setCreatedAt(rs.getDate(8));
                user.setUpdatedAt(rs.getDate(9));
                user.setRoleId(rs.getInt(10));
                user.setStatusID(rs.getInt(11));
                user.setToken(rs.getString(12));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public boolean resetPassword(String password, String email) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, Security.encryptToSHA512(password));
            ps.setString(2, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public int updatePassword(String password, String email) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        int rowAffected = 0; // Variable to store the number of rows affected
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, Security.encryptToSHA512(password));
            ps.setString(2, email);
            rowAffected = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowAffected;
    }

    public int insert(User user) {
        // SQL query with placeholders for parameterized input
        String query = "INSERT INTO [dbo].[users]\n"
                + "           ([full_name]\n"
                + "           ,[email]\n"
                + "           ,[phone_number]\n"
                + "           ,[gender]\n"
                + "           ,[profile_img]\n"
                + "           ,[password]\n"
                + "           ,[created_at]\n"
                + "           ,[updated_at]\n"
                + "           ,[role_id]\n"
                + "           ,[status_id]\n"
                + "           ,[token])\n"
                + "     VALUES\n"
                + "           (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int rowAffected = 0; // Variable to store the number of rows affected

        try {
            // Prepare the SQL query for execution
            ps = connection.prepareStatement(query);

            // Set parameters for the PreparedStatement
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setBoolean(4, user.isGender());

            // Set the profile image path based on gender
            String imgPath = user.isGender() ? "images/pic-1.jpg" : "images/pic-5.jpg";
            ps.setString(5, imgPath);

            ps.setString(6, user.getPassword()); // Ensure password is hashed
            ps.setDate(7, user.getCreatedAt());
            ps.setDate(8, user.getUpdatedAt());
            ps.setInt(9, user.getRoleId());
            ps.setInt(10, user.getStatusID());
            ps.setString(11, user.getToken());

            // Execute the query and get the number of rows affected
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }
        return rowAffected;
    }

    // Checks if a user already exists by username
    @SuppressWarnings("all")
    public boolean checkUserExistedByUsername(String username) {
        String query = "SELECT 1\n"
                + "FROM users\n"
                + "WHERE username = ?";
        boolean isDuplicate = true;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                isDuplicate = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    // Finds a user by email and password
    public User findUserByEmailAndPassword(String email, String password) {
        String query = "SELECT *\n"
                + "FROM users\n"
                + "WHERE email = ? AND [password] = ?";
        User user = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setFullName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhoneNumber(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setProfileImg(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setCreatedAt(rs.getDate(8));
                user.setUpdatedAt(rs.getDate(9));
                user.setRoleId(rs.getInt(10));
                user.setStatusID(rs.getInt(11));
                user.setToken(rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Finds a user by email
    public User findUserByEmail(String email) {
        String query = "SELECT *\n"
                + "FROM users\n"
                + "WHERE email = ?";
        User user = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setFullName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhoneNumber(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setProfileImg(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setCreatedAt(rs.getDate(8));
                user.setUpdatedAt(rs.getDate(9));
                user.setRoleId(rs.getInt(10));
                user.setStatusID(rs.getInt(11));
                user.setToken(rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Updates the status of a user by token
    public int UpdateStatusByToken(String token) {
        String query = "UPDATE users\n"
                + "SET status_id = 2\n"
                + "WHERE token = ?";
        int rowAffected = 0;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, token);
            rowAffected = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowAffected;
    }

    // Finds a user by token
    public User findUserByToken(String token) {
        String query = "SELECT * \n"
                + "FROM users\n"
                + "WHERE token = ?";

        User user = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, token);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setFullName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPhoneNumber(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setProfileImg(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setCreatedAt(rs.getDate(8));
                user.setUpdatedAt(rs.getDate(9));
                user.setRoleId(rs.getInt(10));
                user.setStatusID(rs.getInt(11));
                user.setToken(rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Updates the token of a user by email
    public int UpdateTokenByEmail(String token, String email) {
        String query = "UPDATE users\n"
                + "SET token = ?\n"
                + "WHERE email = ?;";
        int rowAffected = 0;
        System.out.println(token);
        System.out.println(email);
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, token);
            ps.setString(2, email);
            rowAffected = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowAffected;
    }

    public int UpdateUserProfile(String fullName, String phoneNumber, String gender, String email) {
        String query = "UPDATE [dbo].[users]\n"
                + "   SET [full_name] = ?\n"
                + "      ,[phone_number] = ?\n"
                + "      ,[gender] = ?\n"
                + " WHERE [email] = ?";
        int gen = 0;
        if (gender.equals("true")) {
            gen = 1;
        }
        int rowAffected = 0;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, fullName);
            ps.setString(2, phoneNumber);
            ps.setInt(3, gen);
            ps.setString(4, email);
            rowAffected = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowAffected;
    }

    public List<ExpertDTO> findAllExpert() {
        List<ExpertDTO> experts = new ArrayList<>();
        String query = "SELECT * \n"
                + "FROM [dbo].[users]\n"
                + "WHERE role_id = 3";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                experts.add(new ExpertDTO(
                        rs.getInt(1),
                        rs.getString(2)
                ));
            }
        } catch (Exception e) {
        }
        
        return experts;
    }
}
