/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class BlogManagerDetailDTO {

    private int id;
    private String title;
    private String categoryName;
    private String content;
    private boolean status;
    private String thumbnail;
    private String briefinfo;

    @Override
    public String toString() {
        return "BlogManagerDetailDTO{" + "id=" + id + ", title=" + title + ", categoryName=" + categoryName + ", content=" + content + ", status=" + status + '}';
    }
}
