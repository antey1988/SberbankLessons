package entities;

import enums.Cuisine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "DISH")
public class Dish implements Serializable {

    private Long id;
    private String name;
    private Cuisine cuisine;
    private Map<Product, Integer> structure;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CUISINE")
    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    @ManyToMany
    @JoinTable(name = "DISH_PRODUCT",
            joinColumns = @JoinColumn(name = "DISH_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    public Map<Product, Integer> getStructure() {
        return structure;
    }

    public void setStructure(Map<Product, Integer> structure) {
        this.structure = structure;
    }
}
