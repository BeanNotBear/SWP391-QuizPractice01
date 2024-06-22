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
public class PracticeListDTO {
    private int id;
    private String subjectName;
    private Date dateTaken;
    private int numberQuestion;
    private int numberCorrect;
    private int duration;

    @Override
    public String toString() {
        return "PracticeListDTO{" + "id=" + id + ", subjectName=" + subjectName + ", dateTaken=" + dateTaken + ", numberQuestion=" + numberQuestion + ", numberCorrect=" + numberCorrect + ", duration=" + duration + '}';
    }
    
    
}