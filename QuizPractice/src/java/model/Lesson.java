package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class Lesson {
    private int id;
    private String name;
    private int creatorId;
    private Date updateAt;
    private Date createdAt;
    private int status;
    private String content;
    private String media;
    private int lessonIndex;
    private String type;
}
