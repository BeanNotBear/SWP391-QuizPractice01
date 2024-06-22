/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import context.DBContext;
import dto.PracticeListDTO;

/**
 *
 * @author Admin
 */
public class PracticeDAO extends DBContext {

    private static PracticeDAO instance;
    // Lock object for thread-safe singleton instantiation
    private static Object lockPad = new Object();

    // Private constructor to prevent instantiation
    private PracticeDAO() {
    }

    // Returns the singleton instance of SliderDAO
    public static PracticeDAO getInstance() {
        if (instance == null) {
            synchronized (lockPad) {
                if (instance == null) {
                    instance = new PracticeDAO();
                }
            }
        }
        return instance;
    }

    public int getTotalRecordsSearch(int userId, String searchName) {
        String query = "SELECT COUNT(*) FROM Practices WHERE UserId = ? AND SubjectId = (select id from subjects where name =?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, searchName);
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

    public List<PracticeListDTO> getPaginationPracticeListSearch(int userId, int page, int recordsPerPage, String searchName) {
        List<PracticeListDTO> lst = new ArrayList<>();
        int start = (page - 1) * recordsPerPage + 1;
        int end = start + recordsPerPage - 1;

        try {
            String query = "WITH PagedResults AS (\n"
                    + "    SELECT p.id,s.name AS subject_name, p.CreatedAt, p.NumberQuestion, p.NumberCorrect, p.Duration,\n"
                    + "           ROW_NUMBER() OVER (ORDER BY p.CreatedAt) AS row_num\n"
                    + "    FROM Practices p\n"
                    + "    LEFT JOIN subjects s ON p.SubjectId = s.id \n"
                    + "    WHERE p.UserId = ? AND s.name = ?\n"
                    + ")\n"
                    + "SELECT * \n"
                    + "FROM PagedResults\n"
                    + "WHERE row_num BETWEEN ? AND ?\n"
                    + "ORDER BY row_num";

            ps = connection.prepareStatement(query);
            ps.setInt(1, userId); // Thay đổi UserId tương ứng
            ps.setString(2, searchName);
            ps.setInt(3, start);
            ps.setInt(4, end);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String subjectName = rs.getString(2);
                Date createdAt = rs.getDate(3);
                int numberQuestion = rs.getInt(4);
                int numberCorrect = rs.getInt(5);
                int duration = rs.getInt(6);

                PracticeListDTO p = new PracticeListDTO(id, subjectName, createdAt, numberQuestion, numberCorrect, duration);
                lst.add(p);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public List<String> getListDimensionName() {
        List<String> lst = new ArrayList<>();

        try {
            String query = "select DimensionName from Dimension";

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString(1);
                lst.add(name);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public List<String> getListSubjectName() {
        List<String> lst = new ArrayList<>();

        try {
            String query = "Select name from subjects";

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString(1);
                lst.add(name);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public List<String> getSubjectByDimension(String dimension) {
        List<String> lst = new ArrayList<>();

        try {
            String query = "Select name from subjects where dimensionId = \n"
                    + "(select DimensionId from Dimension where DimensionName = ?)";

            ps = connection.prepareStatement(query);
            ps.setString(1, dimension);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString(1);
                lst.add(name);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public List<String> getLessonBySubject(String subject) {
        List<String> lst = new ArrayList<>();

        try {
            String query = "select l.name from subject_has_lesson sl \n"
                    + "left join lessons l on sl.lesson_id = l.id\n"
                    + "where sl.subject_id =\n"
                    + "(select id from subjects where name = ?)";

            ps = connection.prepareStatement(query);
            ps.setString(1, subject);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString(1);
                lst.add(name);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public List<PracticeListDTO> getPaginationPracticeList(int userId, int page, int recordsPerPage) {
        List<PracticeListDTO> lst = new ArrayList<>();
        int start = (page - 1) * recordsPerPage + 1;
        int end = start + recordsPerPage - 1;

        try {
            String query = "WITH PagedResults AS (\n"
                    + "    SELECT p.id,s.name AS subject_name, p.CreatedAt, p.NumberQuestion, p.NumberCorrect, p.Duration,\n"
                    + "           ROW_NUMBER() OVER (ORDER BY p.CreatedAt) AS row_num\n"
                    + "    FROM Practices p\n"
                    + "    LEFT JOIN subjects s ON p.SubjectId = s.id \n"
                    + "    WHERE p.UserId = ?\n"
                    + ")\n"
                    + "SELECT * \n"
                    + "FROM PagedResults\n"
                    + "WHERE row_num BETWEEN ? AND ?\n"
                    + "ORDER BY row_num";

            ps = connection.prepareStatement(query);
            ps.setInt(1, userId); // Thay đổi UserId tương ứng
            ps.setInt(2, start);
            ps.setInt(3, end);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String subjectName = rs.getString(2);
                Date createdAt = rs.getDate(3);
                int numberQuestion = rs.getInt(4);
                int numberCorrect = rs.getInt(5);
                int duration = rs.getInt(6);

                PracticeListDTO p = new PracticeListDTO(id, subjectName, createdAt, numberQuestion, numberCorrect, duration);
                lst.add(p);
            }
        } catch (SQLException ex) {
        }
        return lst;
    }

    public int getTotalRecords(int userId) {
        String query = "SELECT COUNT(*) FROM Practices WHERE UserId = ?";
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

    public static void main(String[] args) {
        PracticeDAO p = new PracticeDAO();
        System.out.println(p.getPaginationPracticeList(27, 1, 5));
        System.out.println(p.getTotalRecords(27));
        System.out.println(p.getListSubjectName());
        System.out.println(p.getListDimensionName());
    }

}