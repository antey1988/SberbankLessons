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
        logger.info("Method allProducts()");
        return sessionFactory.getCurrentSession()
                .createQuery("select p from Product p")
                .list();
    }

    @Override
    public Product save(Product product) {
        logger.info("Method save()");
        sessionFactory.getCurrentSession().saveOrUpdate(product);
        logger.info("Product saved with id: " + product.getId());
        return product;
    }

    @Override
    public void delete(List<Product> products) {
        logger.info("Method delete()");
        Session currentSession = sessionFactory.getCurrentSession();
        for (Product product : products) {
            currentSession.delete(product);
            logger.info("Product deleted with id: " + product.getId());
        }
    }

    @Override
    public List<Product> productByName(String name) {
        logger.info("Method productByName()");
        return sessionFactory.getCurrentSession()
                .createQuery("select p from Product p where p.name = :name")
                .setParameter("name", name)
                .list();
    }
}
