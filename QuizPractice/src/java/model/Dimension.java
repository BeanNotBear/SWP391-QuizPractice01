package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("all")
public class Dimension {
    private int DimensionId;
    private String DimensionName;
    private String Type;
    private String Description;
}
