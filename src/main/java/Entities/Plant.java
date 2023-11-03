package Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "plant")
@NamedQueries({
        @NamedQuery(name = "Plant.getAll", query = "SELECT p FROM Plant p"),
        @NamedQuery(name = "Plant.getByType", query = "SELECT p FROM Plant p WHERE p.type ILIKE :type")
})
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "max_height")
    private int maxHeight;

    @Column(name = "price")
    private double price;

    public Plant(String type, String name, int maxHeight, double price) {
        this.type = type;
        this.name = name;
        this.maxHeight = maxHeight;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return id == plant.id && maxHeight == plant.maxHeight && Double.compare(price, plant.price) == 0 && Objects.equals(type, plant.type) && Objects.equals(name, plant.name);
    }

}
