package dao;

import entities.Dish;
import java.util.List;

public interface DishDao {
    List<Dish> allDishes();
    List<Dish> allDishesByName(String dishName);
    List<Dish> allDishesWithProduct();
    Dish save(Dish dish);
    void delete(List<Dish> dish);
}
