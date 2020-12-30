package entities;

import enums.Cuisine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "DISH")
@NamedQueries({
        @NamedQuery(name = "Dish.allDishesByName",
        query = "select d from Dish d " +
                "where d.name like :name"),
        @NamedQuery(name = "Dish.allDishesWithProduct",
                query = "select distinct d from Dish d " +
                        "left join fetch d.products")
}
)
public class Dish implements Serializable {

    private Long id;
    private String name;
    private Cuisine cuisine;
    private Map<Product, Integer> products = new HashMap<>();

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
    @Enumerated(EnumType.STRING)
    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    @ElementCollection
    @CollectionTable(name = "DISH_PRODUCT", joinColumns = @JoinColumn(name = "DISH_ID"))
    @MapKeyJoinColumn(name = "PRODUCT_ID")
    @Column(name = "QUANTITY")
    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public void addProducts(Product product, int quatity) {
        product.getDishes().put(this, quatity);
        products.put(product, quatity);
    }

    public boolean removeProduct(Product product) {
        Integer value = products.remove(product);
        product.getDishes().remove(this);
        return (value != null);
    }

    @Override
    public String toString() {
        return "Dish: " +
                "id = " + id +
                ", name = " + name +
                ", cuisine = " + cuisine;
    }
}
