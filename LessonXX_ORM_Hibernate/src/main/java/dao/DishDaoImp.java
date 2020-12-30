package dao;

import entities.Dish;
import entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository("DishDao")
@Transactional
public class DishDaoImp implements DishDao {

    private static final String ALLDISHES = "select d from Dish d";
    private static final Logger LOGGER = LoggerFactory.getLogger(DishDaoImp.class);

    private SessionFactory sessionFactory;

    public DishDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> allDishes() {
        LOGGER.info("Method AllDishes");
        return sessionFactory.getCurrentSession()
                .createQuery(ALLDISHES)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> allDishesByName(String dishName) {
//        LOGGER.info("Method AllDishes");
        return sessionFactory.getCurrentSession()
                .createNamedQuery("Dish.allDishesByName")
                .setParameter("name", dishName)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> allDishesWithProduct() {
//        LOGGER.info("Method AllDishesWithProduct");
        return sessionFactory.getCurrentSession()
                .createNamedQuery("Dish.allDishesWithProduct")
                .list();
    }

    @Override
    public Dish save(Dish dish) {
        Session session = sessionFactory.getCurrentSession();
        if (dish.getProducts().size() != 0) {
            //новые ранее не созданные продукты
            dish.getProducts().keySet().stream().filter(product -> product.getId() == 0).forEach(product -> {
                //создаем копию, чтобы не было зацикливания
                Product prod = new Product();
                prod.setName(product.getName());
                prod.setUnits(product.getUnits());
                prod.setType(product.getType());

                session.saveOrUpdate(prod);
                product.setId(prod.getId());
                LOGGER.info("Product saved with id: " + product.getId());
            });
        }
        session.saveOrUpdate(dish);
        LOGGER.info("Dish saved with id: " + dish.getId());
        return dish;
    }

    @Override
    public void delete(List<Dish> dishes) {
        Session session = sessionFactory.getCurrentSession();
        dishes.forEach(dish -> {
            session.delete(dish);
            LOGGER.info("Dish deleted with id: " + dish.getId());
        });
    }


}
