package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlantDTO {

    private int ID;

    private String type;

    private String name;

    private int maxHeight;

    private double price;

    public PlantDTO(String plantType, String name, int maxHeight, double price) {
        this.type = plantType;
        this.name = name;
        this.maxHeight = maxHeight;
        this.price = price;
    }
}
