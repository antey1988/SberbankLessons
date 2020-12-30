import config.AppConfig;
import dao.DishDao;
import entities.Dish;
import entities.Product;
import enums.Cuisine;
import enums.UnitsMeasurement;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class DishDaoImpTest {

    private static final Logger logger = LoggerFactory.getLogger(DishDaoImpTest.class);

    GenericApplicationContext ctx;
    DishDao dishDao;

    @Before
    public void initContext() {
        logger.info("Initialization context");
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        logger.info("Get bean DishDao");
        dishDao = ctx.getBean(DishDao.class);
        Assert.assertNotNull(dishDao);
    }

    @After
    public void closeContext() {
        ctx.close();
        logger.info("Closed context");
    }

    private void printDishes(List<Dish> dishes) {
        logger.info("Printing all dishes from list:");
        dishes.forEach(d -> {
            System.out.println("\t" + d.toString());
        });
    }

    private void printDishesWithProduct(List<Dish> dishes) {
        logger.info("Printing all dishes with structure from list:");
        dishes.forEach(d -> {
            System.out.println("\t" + d.toString());
            d.getProducts().forEach((product, integer) -> {
                System.out.printf("\t\t Product: id = %d, name = %s, %d %s%n",
                        product.getId(), product.getName(), integer, product.getUnits());
            });
        });
    }

    @Test
    public void testAllProducts() {
        logger.info("Testing method AllDishess()");
        List<Dish> dishes = dishDao.allDishes();
        Assert.assertEquals(dishes.size(), 4);
        printDishes(dishes);
    }

    @Test
    public void testAllDishesByName() {
        logger.info("Testing method AllDishesByName()");
        List<Dish> dishes = dishDao.allDishesByName("%NI%");
        Assert.assertEquals(dishes.size(), 2);
        printDishes(dishes);
    }

    @Test
    public void testAllDishesWithProduct() {
        logger.info("Testing method AllDishesWithProduct()");
        List<Dish> dishes = dishDao.allDishesWithProduct();
        printDishesWithProduct(dishes);
        Assert.assertEquals(dishes.size(), 4);
    }

    @Test
    public void testSave() {
        logger.info("Testing method Save()");

        Product product = new Product();
        product.setName("BREAD");
        product.setUnits(UnitsMeasurement.PIECES);

        logger.info("Create new dish");
        Dish dish = new Dish();
        dish.setCuisine(Cuisine.EUROPEAN);
        dish.setName("TOAST");
        dish.addProducts(product, 2);
        dishDao.save(dish);
        Assert.assertNotNull(dish.getId());
        List<Dish> dishes = dishDao.allDishesWithProduct();
        Assert.assertEquals(dishes.size(), 5);
        printDishesWithProduct(dishes);
    }

    @Test
    public void testDelete() {
        logger.info("Testing method Delete()");
        List<Dish> dishes = dishDao.allDishes();
        logger.info("List dishes before delete");
        Assert.assertEquals(dishes.size(), 4);
        printDishes(dishes);

        List<Dish> delDish = dishDao.allDishesByName("MANTI");
        dishDao.delete(delDish);
        dishes = dishDao.allDishes();
        logger.info("List dishes after delete");
        Assert.assertEquals(dishes.size(), 3);
        printDishes(dishes);
    }
}
