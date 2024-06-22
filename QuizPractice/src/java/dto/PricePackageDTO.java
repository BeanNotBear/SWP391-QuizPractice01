package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePackageDTO {
    private int id;
    private String name;
    private int duration;
    private double salePrice;
    private double price;
}
