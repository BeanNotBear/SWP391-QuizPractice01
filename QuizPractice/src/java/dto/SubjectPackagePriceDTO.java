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
public class SubjectPackagePriceDTO {
    private int id;
    private String packageName;
    private int duration;
    private double salePrice;
    private double price;   
    private String status;
}
