package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Role class represents a role with an ID and a name.
 * It is used to define different types of roles within the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("All")
public class Role {
    // Fields
    private int id; // Unique identifier for the role
    private String name; // Name of the role (e.g., "Admin", "User")
}