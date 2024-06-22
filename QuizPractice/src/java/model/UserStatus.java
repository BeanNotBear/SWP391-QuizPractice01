package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The UserStatus class represents the status of a user with an ID and a name.
 * It is used to define different statuses that a user can have within the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class UserStatus {
    // Fields
    private int id; // Unique identifier for the user status
    private String name; // Name of the user status (e.g., "Active", "Inactive")
}
