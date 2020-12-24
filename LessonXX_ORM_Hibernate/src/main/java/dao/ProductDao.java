package dao;

import entities.Dish;
import entities.Product;

import java.util.List;

public interface ProductDao {
    List<Product> allProducts();
    List<Dish> findDishesWhitProduct(String nameProduct);
    Product save(Product product);
    void delete(Product product);
}
