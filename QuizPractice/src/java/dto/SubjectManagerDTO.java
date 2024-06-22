package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("all")
public class SubjectManagerDTO {
    private int id;
    private String name;
    private String thumbnail;
    private String dimensionName;
    private int numberOfLesson;
    private int status;
    private String owner;
}
