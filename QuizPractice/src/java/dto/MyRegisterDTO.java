/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyRegisterDTO {
    private int id;
    private String subjectName;
    private Date registerTime;
    private String packageName;
    private double totalCost;
    private String status;
    
     @Override
    public String toString() {
        return "MyRegisterDTO{" + "id=" + id + ", subjectName=" + subjectName + ", registerTime=" + registerTime + ", packageName=" + packageName + ", totalCost=" + totalCost + ", status=" + status + '}';
    }
}
