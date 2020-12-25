package dao;

import entities.Dish;
import entities.Product;

import java.util.List;

public interface ProductDao {
    List<Product> allProducts();
    Product save(Product product);
    void delete(List<Product> products);
    List<Product> productByName(String name);
}
