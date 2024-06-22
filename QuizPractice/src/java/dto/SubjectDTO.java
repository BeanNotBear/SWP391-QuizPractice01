package dto;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Tag;
import model.User;

/**
 * The User class represents a user entity with attributes such as user ID,
 * first name, last name, email, phone number, gender, date of birth, profile
 * image, username, password, creation timestamp, update timestamp, role ID, and
 * status ID.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class SubjectDTO {
    // Fields
    private int id;
    private String name;
    private int creator_id;
    private Date create_at;
    private Date update_at;
    private int status;
    private String img;
    private String description;
    private int numberOfLesson;
    private User creator;
    private List<PricePackageDTO> pricePackages;
    private List<Tag> tags;
    private boolean registered = false;
}
