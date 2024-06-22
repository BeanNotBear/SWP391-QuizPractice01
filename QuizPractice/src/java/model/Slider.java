/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slider {
    private int ID;
    private String Title;
    private String SubTitle;
    private String Content;
    private String Image;
    private String LinkUrl;
    private Date CreatedAt;
    private int CreatedBy;
    private int Status;
}
