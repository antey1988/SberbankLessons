package entities;

import enums.TypeProduct;
import enums.UnitsMeasurement;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    private long id;
    private String name;
    private TypeProduct type;
    private UnitsMeasurement units;
    private Map<Dish,Integer> dishes = new HashMap<>();

    public Product() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    public TypeProduct getType() {
        return type;
    }

    public void setType(TypeProduct type) {
        this.type = type;
    }

    @Column(name = "UNITS")
    @Enumerated(EnumType.STRING)
    public UnitsMeasurement getUnits() {
        return units;
    }

    public void setUnits(UnitsMeasurement units) {
        this.units = units;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "DISH_PRODUCT", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @MapKeyJoinColumn(name = "DISH_ID")
    @Column(name = "QUANTITY")
    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }

    /*public int addDish(Dish dish, int quatity) {

    }*/

    @Override
    public String toString() {
        return "Product: " +
                "id = " + id +
                ", name = " + name +
                ", type = " + type +
                ", units = " + units;
    }
}
