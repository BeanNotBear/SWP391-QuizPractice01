package dal;

import context.DBContext;
import dto.DimensionDTO;
import dto.MyRegisterDTO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import dto.PricePackageDTO;
import dto.SubjectDTO;
import dto.SubjectManagerDTO;
import dto.SubjectPackagePriceDTO;
import java.time.LocalDate;
import model.Dimension;
import model.PricePackage;
import model.Subject;
import model.Tag;
import model.User;

// Data Access Object for User operations, extending DBContext to utilize database connections
@SuppressWarnings("all")
public class SubjectDAO extends DBContext {

    // Singleton instance of UserDAO
    private static SubjectDAO instance;
    // Lock object for thread-safe singleton instantiation
    private static Object lockPad = new Object();

    // Private constructor to prevent instantiation
    private SubjectDAO() {
    }

    // Returns the singleton instance of UserDAO
    public static SubjectDAO getInstance() {
        if (instance == null) {
            synchronized (lockPad) {
                if (instance == null) {
                    instance = new SubjectDAO();
                }
            }
        }
        return instance;
    }

    public List<SubjectDTO> allSubjectsWithConditions(String searchParam, String sort) {
        List<SubjectDTO> subjects = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        UserDAO userDAO = UserDAO.getInstance();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT s.[id]\n"
                    + "      ,[name]\n"
                    + "      ,[creator_id]\n"
                    + "      ,[creater_at]\n"
                    + "      ,[update_at]\n"
                    + "      ,[status]\n"
                    + "      ,[img]\n"
                    + "      ,[description]\n"
                    + "	  , u.full_name\n"
                    + "  FROM [SWP391_G6].[dbo].[subjects] as s\n"
                    + "  INNER JOIN users as u on s.creator_id = u.id\n"
                    + "  WHERE s.status = 1");
            // Create SQL query with sorting, LIMIT, and OFFSET clauses
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND name LIKE ?");
                query.append(" OR description LIKE ?");
                query.append(" OR u.full_name LIKE ?");
                list.add("%" + searchParam + "%");
                list.add("%" + searchParam + "%");
                list.add("%" + searchParam + "%");
            }

            if (sort != null && !sort.trim().isEmpty()) {
                query.append(" ORDER BY [update_at] " + sort);
            }

//            query.append(" order by id");
            // Create prepared statement
            ps = connection.prepareStatement(query.toString());
            mapParams(ps, list);
            // Execute the query
            rs = ps.executeQuery();
            // Iterate over the result set
            while (rs.next()) {
                // Retrieve subject details from each row
                SubjectDTO subject = new SubjectDTO();
                int id = rs.getInt("id");
                subject.setId(id);
                subject.setName(rs.getString("name"));
                User creator = userDAO.findUserById(rs.getInt("creator_id"));
                subject.setCreator(creator);
                subject.setCreate_at(rs.getDate("creater_at"));
                subject.setUpdate_at(rs.getDate("update_at"));
                subject.setStatus(rs.getInt("status"));
                subject.setImg(rs.getString("img"));
                subject.setDescription(rs.getString("description"));
                // count lesson to add to list subject return by search
                subject.setNumberOfLesson(countLessonsBySubjectId(id));
                try {
                    subject.setPricePackages(findPricePackageBySubjectId(id));
                    subject.setTags(findTagsBySubjectId(id));
                } catch (Exception e) {
                }
                System.out.println("ok");
                // Add the subject to the list
                subjects.add(subject);
            }
            // Close the resources
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return subjects;
    }

    public int countLessonsBySubjectId(int subjectId) {
        // count lesson with subject id in database
        int lessonCount = 0;
        try {
            String query = "select count(*) as lesson_count from subject_has_lesson where subject_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, subjectId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lessonCount = resultSet.getInt("lesson_count");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lessonCount;
    }

    public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        // Khởi tạo biến chỉ số bắt đầu từ 1 (do chỉ số của PreparedStatement bắt đầu từ 1)
        int i = 1;

        // Lặp qua từng phần tử trong danh sách tham số
        for (Object arg : args) {
            // Kiểm tra kiểu của tham số và ánh xạ tương ứng vào PreparedStatement
            if (arg instanceof Date) {
                // Nếu tham số là kiểu Date, ánh xạ thành Timestamp
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                // Nếu tham số là kiểu Integer, ánh xạ thành Int
                ps.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                // Nếu tham số là kiểu Long, ánh xạ thành Long
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                // Nếu tham số là kiểu Double, ánh xạ thành Double
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                // Nếu tham số là kiểu Float, ánh xạ thành Float
                ps.setFloat(i++, (Float) arg);
            } else {
                // Nếu tham số là kiểu String hoặc bất kỳ kiểu nào khác, ánh xạ thành String
                ps.setString(i++, (String) arg);
            }
        }
    }

    // phân trang để hiển thị page
    public List<SubjectDTO> Paging(List<SubjectDTO> subject, int page, int pageSize) {
        // Tính toán chỉ số bắt đầu của danh sách con cho trang hiện tại
        int fromIndex = (page - 1) * pageSize;

        // Tính toán chỉ số kết thúc của danh sách con cho trang hiện tại
        // Chỉ số kết thúc không được vượt quá kích thước của danh sách
        int toIndex = Math.min(fromIndex + pageSize, subject.size());

        // Đảm bảo rằng fromIndex không lớn hơn toIndex
        // Điều này xử lý trường hợp khi fromIndex được tính toán vượt quá kích thước của danh sách
        if (fromIndex > toIndex) {
            // Điều chỉnh fromIndex bằng với toIndex để tránh ngoại lệ IndexOutOfBoundsException
            fromIndex = toIndex;
        }

        // Trả về danh sách con cho trang cụ thể
        // Điều này sẽ trả về danh sách trống nếu fromIndex bằng với toIndex
        return subject.subList(fromIndex, toIndex);
    }

    public SubjectDTO findSubjectById(int id) {
        String query = "SELECT [id]\n"
                + ",[name]\n"
                + ",[creator_id]\n"
                + ",[creater_at]\n"
                + ",[update_at]\n"
                + ",[status]\n"
                + ",[img]\n"
                + ",[description]"
                + "FROM [SWP391_G6].[dbo].[subjects]\n"
                + "WHERE [id] = ?";
        SubjectDTO subject = null;
        UserDAO userDAO = UserDAO.getInstance();
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                subject = new SubjectDTO();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                User creator = userDAO.findUserById(rs.getInt("creator_id"));
                subject.setCreator(creator);
                subject.setCreate_at(rs.getDate("creater_at"));
                subject.setUpdate_at(rs.getDate("update_at"));
                subject.setStatus(rs.getInt("status"));
                subject.setImg(rs.getString("img"));
                subject.setDescription(rs.getString("description"));
                // count lesson to add to list subject return by search
                subject.setNumberOfLesson(countLessonsBySubjectId(rs.getInt("id")));
                subject.setPricePackages(findPricePackageBySubjectId(id));
                subject.setTags(findTagsBySubjectId(id));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subject;
    }

    public List<PricePackageDTO> findPricePackageBySubjectId(int id) {
        List<PricePackageDTO> pricePackages = new ArrayList<>();
        String query = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[duration]\n"
                + "      ,[sale_price]\n"
                + "      ,[price]\n"
                + "FROM [SWP391_G6].[dbo].[package_price]\n"
                + "INNER JOIN [dbo].[subject_has_price_package] AS SP ON SP.price_package_id = id\n"
                + "WHERE SP.subject_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PricePackageDTO pricePackage = new PricePackageDTO();
                pricePackage.setId(resultSet.getInt(1));
                pricePackage.setName(resultSet.getString(2));
                pricePackage.setDuration(resultSet.getInt(3));
                pricePackage.setSalePrice(resultSet.getDouble(4));
                pricePackage.setPrice(resultSet.getDouble(5));
                pricePackages.add(pricePackage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return pricePackages;
    }

    public List<Tag> findTagsBySubjectId(int subjectId) {
        List<Tag> tags = new ArrayList<>();
        String query = "SELECT [id]\n"
                + "      ,[tag]\n"
                + "  FROM [SWP391_G6].[dbo].[subject_has_tag]\n"
                + "  INNER JOIN tags as t on t.id = tag_id\n"
                + "  WHERE subject_id = ?";
        PreparedStatement pst;
        ResultSet rst;
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, subjectId);
            rst = pst.executeQuery();
            while (rst.next()) {
                Tag tag = new Tag();
                tag.setId(rst.getInt(1));
                tag.setTag(rst.getString(2));
                tags.add(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tags;
    }

    public List<SubjectDTO> listTop8Subject() {
        List<SubjectDTO> listSubject = new ArrayList<>();
        try {
            String query = "SELECT TOP 8 * FROM subjects WHERE [status] = 1 order by creater_at desc";

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int creator_id = rs.getInt(3);
                Date creater_at = rs.getDate(4);
                Date update_at = rs.getDate(5);
                int status = rs.getInt(6);
                String img = rs.getString(7);
                String description = rs.getString(8);

                SubjectDTO subject = new SubjectDTO();
                subject.setId(id);
                subject.setName(name);
                subject.setCreator_id(creator_id);
                subject.setCreate_at(creater_at);
                subject.setUpdate_at(update_at);
                subject.setStatus(status);
                subject.setImg(img);
                subject.setDescription(description);
                subject.setPricePackages(findPricePackageBySubjectId(id));
                subject.setTags(findTagsBySubjectId(id));
                listSubject.add(subject);
            }
        } catch (SQLException ex) {

        }
        return listSubject;
    }

    public List<SubjectDTO> find3FeatureSubject() {
        List<SubjectDTO> listSubject = new ArrayList<>();
        try {
            String query = "SELECT TOP 3 * FROM subjects order by creater_at desc";

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int creator_id = rs.getInt(3);
                Date creater_at = rs.getDate(4);
                Date update_at = rs.getDate(5);
                int status = rs.getInt(6);
                String img = rs.getString(7);
                String description = rs.getString(8);

                SubjectDTO subject = new SubjectDTO();
                subject.setId(id);
                subject.setName(name);
                subject.setCreator_id(creator_id);
                subject.setCreate_at(creater_at);
                subject.setUpdate_at(update_at);
                subject.setStatus(status);
                subject.setImg(img);
                subject.setDescription(description);
                subject.setPricePackages(findPricePackageBySubjectId(id));
                subject.setTags(findTagsBySubjectId(id));
                listSubject.add(subject);
            }
        } catch (SQLException ex) {
        }
        return listSubject;
    }

    public List<PricePackage> getPricePackageBySubjectId(int subjectId) {
        List<PricePackage> listPricePackage = new ArrayList<>();
        try {
            String query = "select * \n"
                    + "from package_price p\n"
                    + "where p.id in(\n"
                    + "select price_package_id from  subject_has_price_package \n"
                    + "where subject_id = ?)";

            ps = connection.prepareStatement(query);
            ps.setInt(1, subjectId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int duration = rs.getInt(3);
                double salePrice = rs.getDouble(4);
                double price = rs.getDouble(5);
                double originalPrice = rs.getDouble(6);

                PricePackage p = new PricePackage(id, name, duration, salePrice, price, originalPrice);
                listPricePackage.add(p);
            }
        } catch (SQLException ex) {
        }
        return listPricePackage;
    }

//    String userName = request.getParameter("userName");
//            String email = request.getParameter("email");
//            String phoneNumber = request.getParameter("phoneNumber");
//            String gender = request.getParameter("gender");
    public void addNewUser(String userName, String email, String phoneNumber, String gender) {
        String query = "insert into users(full_name, email, phone_number, password,gender,created_at,role_id,status_id) \n"
                + "values(?,?,?,?,?,?,?,?);";

        String password = "123";
        int role = 1;
        int status = 2;
        int gender2 = gender.equalsIgnoreCase("male") ? 1 : 0;

        try {
            // Prepare the SQL query for execution
            ps = connection.prepareStatement(query);

            // Set parameters for the PreparedStatement
            ps.setString(1, userName);
            ps.setString(2, email);
            ps.setString(3, phoneNumber);
            ps.setString(4, password);
            ps.setInt(5, gender2);
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.setInt(7, role);
            ps.setInt(8, status);

            ps.executeUpdate();

        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }

    }

    public int getLastUserId() {
        String query = "SELECT @@IDENTITY AS LastInsertedId";
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

    public List<MyRegisterDTO> getPaginationRegisterSubjectSearch(int userId, int page, int recordsPerPage, String searchName) {
        List<MyRegisterDTO> lst = new ArrayList<>();
        int start = (page - 1) * recordsPerPage + 1;
        int end = start + recordsPerPage - 1;

        try {
            String query = "WITH PagedResults AS (\n"
                    + "    SELECT r.id,s.name AS subject_name, r.CreatedAt, p.name AS package_name, p.original_price, r.Status,\n"
                    + "           ROW_NUMBER() OVER (ORDER BY r.CreatedAt) AS row_num\n"
                    + "    FROM Subject_Register r \n"
                    + "    LEFT JOIN subjects s ON r.SubjectId = s.id \n"
                    + "\n"
                    + "    LEFT JOIN package_price p ON p.id = r.PackageId\n"
                    + "    WHERE r.UserId = ? and s.name like ?\n"
                    + ")\n"
                    + "SELECT * \n"
                    + "FROM PagedResults\n"
                    + "WHERE row_num BETWEEN ? AND ?\n"
                    + "ORDER BY row_num;";

            ps = connection.prepareStatement(query);
            ps.setInt(1, userId); // Thay đổi UserId tương ứng
            ps.setString(2, "%" + searchName + "%");
            ps.setInt(3, start);
            ps.setInt(4, end);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String subjectName = rs.getString(2);
                Date duration = rs.getDate(3);
                String packageName = rs.getString(4);
                double originalPrice = rs.getDouble(5);
                String status = rs.getString(6);

                MyRegisterDTO p = new MyRegisterDTO(id, subjectName, duration, packageName, originalPrice, status);
                lst.add(p);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public List<MyRegisterDTO> getPaginationRegisterSubject(int userId, int page, int recordsPerPage) {
        List<MyRegisterDTO> lst = new ArrayList<>();
        int start = (page - 1) * recordsPerPage + 1;
        int end = start + recordsPerPage - 1;

        try {
            String query = "WITH PagedResults AS ("
                    + "    SELECT r.id,s.name AS subject_name, r.CreatedAt, p.name AS package_name, p.original_price, r.Status,"
                    + "           ROW_NUMBER() OVER (ORDER BY r.CreatedAt) AS row_num"
                    + "    FROM Subject_Register r"
                    + "    LEFT JOIN subjects s ON r.SubjectId = s.id"
                    + "    LEFT JOIN package_price p ON p.id = r.PackageId"
                    + "    WHERE r.UserId = ?"
                    + ")"
                    + "SELECT * "
                    + "FROM PagedResults "
                    + "WHERE row_num BETWEEN ? AND ? "
                    + "ORDER BY row_num;";

            ps = connection.prepareStatement(query);
            ps.setInt(1, userId); // Thay đổi UserId tương ứng
            ps.setInt(2, start);
            ps.setInt(3, end);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String subjectName = rs.getString(2);
                Date duration = rs.getDate(3);
                String packageName = rs.getString(4);
                double originalPrice = rs.getDouble(5);
                String status = rs.getString(6);

                MyRegisterDTO p = new MyRegisterDTO(id, subjectName, duration, packageName, originalPrice, status);
                lst.add(p);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public int getTotalRecords(int userId) {
        String query = "SELECT COUNT(*) FROM Subject_Register WHERE UserId = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
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

    public boolean deleteRegister(int id) {
        // SQL query with placeholders for parameterized input
        String query = "delete from Subject_Register where id = ?";

        try {
            // Prepare the SQL query for execution
            ps = connection.prepareStatement(query);

            // Set parameters for the PreparedStatement
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }
        return false;
    }

    public int getTotalRecordSubject() {
        String query = "SELECT COUNT(*) FROM subjects";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            }

        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }
        return 0;
    }

    public int getTotalRecordsExpertManagerSubjectSearchBySubjectName(int userId, String subjectName) {
        String query = "SELECT COUNT(*) FROM subjects WHERE creator_id = ? and name like ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, "%" + subjectName + "%");
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

    public boolean addSubjectPricePackage(int packageId, int subjectId) {
        boolean updated = false;
        String query = "insert into subject_has_price_package values(?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, subjectId);
            ps.setInt(2, packageId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception properly in a production environment
        }
        return updated;
    }

    public boolean deleteSubjectPricePackage(int packageId, int subjectId) {
        boolean updated = false;
        String query = "delete from subject_has_price_package where subject_id=? and price_package_id=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, subjectId);
            ps.setInt(2, packageId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception properly in a production environment
        }
        return updated;
    }

    public List<SubjectPackagePriceDTO> getListSubjectPackagePriceDTO(int subjectId) {
        List<SubjectPackagePriceDTO> lst = new ArrayList<>();

        try {
            String query = "SELECT \n"
                    + "    pp.*,\n"
                    + "    CASE \n"
                    + "        WHEN EXISTS (\n"
                    + "            SELECT 1 \n"
                    + "            FROM subject_has_price_package shpp \n"
                    + "            WHERE shpp.price_package_id = pp.id \n"
                    + "              AND shpp.subject_id = ?\n"
                    + "        ) THEN 'active'\n"
                    + "        ELSE 'inactive'\n"
                    + "    END AS status\n"
                    + "FROM \n"
                    + "package_price pp";

            ps = connection.prepareStatement(query);
            ps.setInt(1, subjectId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int duration = rs.getInt(3);
                double salePrice = rs.getDouble(4);
                double price = rs.getDouble(5);
                String status = rs.getString(7);

                SubjectPackagePriceDTO s = new SubjectPackagePriceDTO(id, name, duration, price, salePrice, status);
                lst.add(s);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public boolean deleteDimension(int id) {
        boolean updated = false;
        String query = "update Dimension set Status = 0 where DimensionId=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception properly in a production environment
        }
        return updated;
    }

    public boolean addDimension(String name, String type, String description) {
        boolean added = false;
        String query = "insert into Dimension (DimensionName, Type, Description,Status) values (?, ?, ?, 1)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setString(3, description);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                added = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception properly in a production environment
        }
        return added;
    }

    public boolean updateDimension(int id, String name, String type, String description) {
        boolean updated = false;
        String query = "update Dimension set DimensionName=?, Type=?, Description=? where DimensionId=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setString(3, description);
            ps.setInt(4, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception properly in a production environment
        }
        return updated;
    }

    public List<Dimension> getListDimension() {
        List<Dimension> lst = new ArrayList<>();

        try {
            String query = "select * from Dimension where Status = 1";

            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                String description = rs.getString(4);

                Dimension dimension = new Dimension(id, name, type, description);
                lst.add(dimension);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public Dimension getDimensionById(int id) {
        String query = "select * from Dimension where DimensionId =?";
        Dimension dimension = null;

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String DimensionName = rs.getString(2);
                String Type = rs.getString(3);
                String Description = rs.getString(4);
                boolean Status = rs.getBoolean(5);

                dimension = new Dimension(id, DimensionName, Type, Description);
                return dimension;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dimension;
    }

    public Subject getSubjectById(int id) {
        String query = "select * from subjects where id =?";
        Subject subject = null;

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                int creator_id = rs.getInt(3);
                Date creater_at = rs.getDate(4);
                Date updated_at = rs.getDate(5);
                int status = rs.getInt(6);
                String img = rs.getString(7);
                String description = rs.getString(8);
                int dimensionId = rs.getInt(9);

                subject = new Subject(id, name, creator_id, creater_at, updated_at, status, img, description, dimensionId);
                return subject;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subject;
    }

    public boolean updateSubject(int id, String name, String img, int dimensionId, String description) {
        boolean updated = false;
        String query = "update subjects set name=? ,img=?, description=?, dimensionId=?, update_at=GETDATE() where id=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, img);
            ps.setString(3, description);
            ps.setInt(4, dimensionId);
            ps.setInt(5, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {

        }
        return updated;
    }

    public boolean insert(String name, String img, int dimensionId, int creator_id, int status, boolean feature, String description) {
        boolean updated = false;
        String query = "INSERT INTO [dbo].[subjects]\n"
                + "           ([name]\n"
                + "           ,[creator_id]\n"
                + "           ,[creater_at]\n"
                + "           ,[update_at]\n"
                + "           ,[status]\n"
                + "           ,[img]\n"
                + "           ,[description]\n"
                + "           ,[dimensionId]\n"
                + "           ,[feature])\n"
                + "     VALUES\n"
                + "           (?,?,GETDATE(),GETDATE(),?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, creator_id);
            ps.setInt(3, status);
            ps.setString(4, img);
            ps.setString(5, description);
            ps.setInt(6, dimensionId);
            ps.setBoolean(7, feature);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return updated;
    }

    public List<DimensionDTO> getListDimensionDTO() {
        List<DimensionDTO> lst = new ArrayList<>();

        try {
            String query = "select * from Dimension";

            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);

                DimensionDTO dimensionDTO = new DimensionDTO(id, name);
                lst.add(dimensionDTO);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public void addNewSubjectRegister(int subjectId, int userId, int packageId) {
        // SQL query with placeholders for parameterized input
        String query = "insert into Subject_Register \n"
                + "values(?,?,?,?,null,null,'pending')";

        try {
            // Prepare the SQL query for execution
            ps = connection.prepareStatement(query);

            // Set parameters for the PreparedStatement
            ps.setInt(1, subjectId);
            ps.setInt(2, userId);
            ps.setInt(3, packageId);
            ps.setDate(4, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();
        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }

    }

    public boolean changeStatusSubjectBySubjectId(int id, int statusId) {
        String query = "UPDATE [dbo].[subjects]\n"
                + "SET update_at = GETDATE(),\n"
                + "	[status] = ?\n"
                + "WHERE id = ?";
        boolean isSuccess = false;
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setInt(1, statusId);
            prepareStatement.setInt(2, id);
            isSuccess = prepareStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SubjectDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return isSuccess;
    }

    public boolean checkSubjectRegisterById(int subId, int userId) {
        String query = "SELECT *\n"
                + "FROM [dbo].[Subject_Register]\n"
                + "WHERE [SubjectId] = ? AND [UserId] = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, subId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkEmailExists(String email) {
        String query = "SELECT COUNT(*) AS count FROM Users WHERE email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logger in real application
            return false;
        } finally {
            // Ensure resources are closed
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Replace with logger in real application
            }
        }
    }

    public boolean checkExpertTeachSameSubject(String subjectName, int userId) {
        String query = "SELECT s.name, u.id\n"
                + "FROM subjects AS s\n"
                + "INNER JOIN users AS u ON u.id = s.creator_id\n"
                + "WHERE s.name = ? AND u.id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, subjectName);
            ps.setInt(2, userId);
            System.out.println("DK: " + ps.executeQuery().next());
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addNewUser(String userName, String email, String phoneNumber, String gender, String token) {
        String query = "insert into users(full_name, email, phone_number, password, gender, created_at, role_id, status_id, token) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        String password = "123";
        int role = 1;
        int status = 1;
        int genderValue = gender.equalsIgnoreCase("male") ? 1 : 0;

        try {
            // Prepare the SQL query for execution
            ps = connection.prepareStatement(query);

            // Set parameters for the PreparedStatement
            ps.setString(1, userName);
            ps.setString(2, email);
            ps.setString(3, phoneNumber);
            ps.setString(4, password);
            ps.setInt(5, genderValue);
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.setInt(7, role);
            ps.setInt(8, status);
            ps.setString(9, token);

            ps.executeUpdate();
        } catch (SQLException e) {
            // Log the exception (if a logging framework is available)
            e.printStackTrace(); // Replace with logger in real application
        }
    }

    public List<SubjectManagerDTO> getSubjectsPagination(int roleId, int userId, int page, int recordsPerPage, String search, String categories, String statuses) {
        StringBuilder query = new StringBuilder();
        query.append("WITH PagedResults AS (\n")
                .append("    SELECT \n")
                .append("        s.id, \n")
                .append("        s.name, \n")
                .append("        s.img, \n")
                .append("        d.DimensionName, \n")
                .append("        COUNT(sl.lesson_id) AS NumberLesson, \n")
                .append("        s.status, \n")
                .append("        ROW_NUMBER() OVER (ORDER BY s.status) AS row_num, \n")
                .append("        u.full_name \n")
                .append("    FROM \n")
                .append("        subjects s \n")
                .append("    LEFT JOIN \n")
                .append("        Dimension d ON s.dimensionId = d.DimensionId \n")
                .append("    LEFT JOIN \n")
                .append("        subject_has_lesson sl ON sl.subject_id = s.id \n")
                .append("    LEFT JOIN \n")
                .append("        users u ON u.id = s.creator_id \n")
                .append("    WHERE 1 = 1\n");
        if (roleId == 3) {
            query.append("	AND creator_id = " + userId + "\n");
        }
        if(search != null) {
            String condition = "%"+search+"%";
            query.append("      AND s.name LIKE '" + condition + "'\n");
        }
        if(categories != null) {
            String condition = "(" + categories + ")\n";
            query.append("      AND d.dimensionId IN " + condition);
        }
        if(statuses != null) {
            String condition = "(" + statuses + ")\n";
            query.append("      AND s.status IN " + condition);
        }
        query.append("    GROUP BY \n")
                .append("        s.id, \n")
                .append("        s.name, \n")
                .append("        s.img, \n")
                .append("        d.DimensionName, \n")
                .append("        s.status, \n")
                .append("        s.creater_at, \n")
                .append("        u.full_name \n")
                .append(")\n")
                .append("SELECT * \n")
                .append("FROM PagedResults \n")
                .append("WHERE row_num BETWEEN ? AND ? \n")
                .append("ORDER BY row_num;");
        List<SubjectManagerDTO> subjects = new ArrayList<>();
        try {
            ps = connection.prepareStatement(query.toString());

            int start = (page - 1) * recordsPerPage + 1;
            int end = start + recordsPerPage - 1;
            ps.setInt(1, start);
            ps.setInt(2, end);
            rs = ps.executeQuery();
            while (rs.next()) {
                subjects.add(new SubjectManagerDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7)
                ));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public int geTotalRecords(int roleId, int userId, String search, String categories, String statuses) {
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM subjects s ")
                .append("LEFT JOIN Dimension d ON s.dimensionId = d.DimensionId ")
                .append("WHERE 1 = 1 ");
        if (roleId == 3) {
            query.append("AND s.creator_id = " + userId + "\n");
        }
        if(search != null) {
            String condition = "%"+search+"%";
            query.append("AND s.name LIKE '" + condition + "'\n");
        }
        if(categories != null) {
            String condition = "(" + categories + ")\n";
            query.append("      AND d.dimensionId IN " + condition);
        }
        if(statuses != null) {
            String condition = "(" + statuses + ")\n";
            query.append("      AND s.status IN " + condition);
        }
        try {
            ps = connection.prepareStatement(query.toString());
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        // view all subjects in database with status = 1
        SubjectDAO c = new SubjectDAO();

        List<SubjectManagerDTO> subjects = SubjectDAO.getInstance().getSubjectsPagination(3, 28, 1, 5, "s", "1,2,3,4", "2");
        System.out.println(subjects.size());
        for (SubjectManagerDTO subject : subjects) {
            System.out.println(subject.toString());
        }
//        System.out.println(c.geTotalRecords(2, 1, null, "1,2,3,4", "1, 2"));
    }
}
