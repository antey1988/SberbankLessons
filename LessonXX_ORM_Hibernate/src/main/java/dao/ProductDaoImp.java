package dao;

import entities.Dish;
import entities.Product;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Repository("ProductDao")
@Transactional
public class ProductDaoImp implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImp.class);

    private SessionFactory sessionFactory;

    public ProductDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> allProducts() {
        logger.info("Method AllProducts()");
        return sessionFactory.getCurrentSession()
                .createQuery("select p from Product p")
                .getResultList();
    }

    @Override
    public List<Dish> findDishesWhitProduct(String nameProduct) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void delete(Product product) {

    }
}
