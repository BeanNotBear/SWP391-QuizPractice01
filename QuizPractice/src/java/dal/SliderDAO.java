package dal;

import context.DBContext;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Slider;

/**
 *
 * @author Admin
 */
// Data Access Object for User operations, extending DBContext to utilize database connections
public class SliderDAO extends DBContext {

    // Singleton instance of UserDAO
    private static SliderDAO instance;
    // Lock object for thread-safe singleton instantiation
    private static Object lockPad = new Object();

    // Private constructor to prevent instantiation
    private SliderDAO() {
    }

    // Returns the singleton instance of SliderDAO
    public static SliderDAO getInstance() {
        if (instance == null) {
            synchronized (lockPad) {
                if (instance == null) {
                    instance = new SliderDAO();
                }
            }
        }
        return instance;
    }

    public boolean addSlider(String title, String subTitle, String content, String image, String linkUrl, int userId) {
        boolean updated = false;
        String query = "insert into Slider values(?,?,?,?,?,GETDATE(),?,1)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, subTitle);
            ps.setString(3, content);
            ps.setString(4, image);
            ps.setString(5, linkUrl);
            ps.setInt(6, userId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception properly in a production environment
        }
        return updated;
    }

    public boolean editSlider(String title, String subTitle, String content, String image, String linkUrl, int status, int sliderId) {
        boolean updated = false;
        String query = "update Slider set Title=?,SubTitle=?,Content=?,Image=?,LinkUrl=?,Status=?\n"
                + "where ID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, subTitle);
            ps.setString(3, content);
            ps.setString(4, image);
            ps.setString(5, linkUrl);
            ps.setInt(6, status);
            ps.setInt(7, sliderId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception properly in a production environment
        }
        return updated;
    }

    public Slider getSliderById(int id) {
        String query = "select * from Slider where ID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                String title = rs.getString(2);
                String subTitle = rs.getString(3);
                String content = rs.getString(4);
                String image = rs.getString(5);
                String linkUrl = rs.getString(6);
                Date createdAt = rs.getDate(7);
                int createdBy = rs.getInt(8);
                int tmpStatus = rs.getInt(9);
                int status = tmpStatus == 1 ? 1 : 0;

                Slider s = new Slider(id, title, subTitle, content, image, linkUrl, createdAt, createdBy, status);
                return s;
            }

        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }
        return null;
    }

    public int getStatus(int id) {
        String query = "select Status from Slider where ID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                int status = rs.getInt(1);
                return status;
            }

        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }
        return 0;
    }

    public boolean toggleSliderStatus(int id, int statusChange) {
        boolean updated = false;
        String query = "update Slider set Status = ? where ID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, statusChange);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception properly in a production environment
        }
        return updated;
    }

    public List<Slider> getPaginationSlider(int page, int recordsPerPage) {
        List<Slider> lst = new ArrayList<>();
        int start = (page - 1) * recordsPerPage + 1;
        int end = start + recordsPerPage - 1;

        try {
            String query = "WITH PagedResults AS (\n"
                    + "    SELECT \n"
                    + "       *, \n"
                    + "        ROW_NUMBER() OVER (ORDER BY CreatedAt) AS row_num\n"
                    + "    FROM \n"
                    + "        Slider\n"
                    + "   )\n"
                    + "SELECT * \n"
                    + "FROM PagedResults\n"
                    + "WHERE row_num BETWEEN ? AND ?\n"
                    + "ORDER BY row_num;";

            ps = connection.prepareStatement(query);
            // Thay đổi UserId tương ứng
            ps.setInt(1, start);
            ps.setInt(2, end);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String subTitle = rs.getString(3);
                String content = rs.getString(4);
                String image = rs.getString(5);
                String linkUrl = rs.getString(6);
                Date createdAt = rs.getDate(7);
                int createdBy = rs.getInt(8);
                int tmpStatus = rs.getInt(9);
                int status = tmpStatus == 1 ? 1 : 0;

                Slider s = new Slider(id, title, subTitle, content, image, linkUrl, createdAt, createdBy, status);
                lst.add(s);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public int getTotalRecordSlider() {
        String query = "select count(*) as total from Slider";
        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }

        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }
        return 0;
    }

    //Query
    public List<Slider> listTop8Slider() {
        List<Slider> listSliders = new ArrayList<>();
        try {
            String query = "SELECT * \n"
                    + "FROM slider \n"
                    + "WHERE Status = 1\n"
                    + "order by ID desc";

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String subTitle = rs.getString(3);
                String content = rs.getString(4);
                String image = rs.getString(5);
                String linkUrl = rs.getString(6);
                Date createdAt = rs.getDate(7);
                int createdBy = rs.getInt(8);
                int status = rs.getInt(9);

                Slider slider = new Slider(id, title, subTitle, content, image, linkUrl, createdAt, createdBy, status);
                listSliders.add(slider);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSliders;
    }

    public static void main(String[] args) {
//        PracticeDAO p = new PracticeDAO();
//        System.out.println(p.getPaginationPracticeList(27, 1, 5));
//        System.out.println(p.getTotalRecords(27));
//        System.out.println(p.getListSubjectName());
//        System.out.println(p.getListDimensionName());
        SliderDAO s = new SliderDAO();
        System.out.println(s.getPaginationSlider(1, 4));
        System.out.println(s.getTotalRecordSlider());
        System.out.println(s.getStatus(1));
        System.out.println(s.toggleSliderStatus(1, 0));
    }

}
