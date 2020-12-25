import config.AppConfig;
import dao.ProductDao;
import entities.Product;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class ProductDaoImpTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpTest.class);

    GenericApplicationContext ctx;
    ProductDao productDao;

    @Before
    public void initContext() {
        logger.info("Initialization context");
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        logger.info("Get bean ProductDao");
        productDao = ctx.getBean(ProductDao.class);
        Assert.assertNotNull(productDao);
    }

    @After
    public void closeContext() {
        ctx.close();
        logger.info("Closed context");
    }

    private void printProducts(List<Product> products) {
        logger.info("Printing all product from list:");
        products.forEach(p->{
            System.out.println("\t" + p.toString());
        });
    }

    @Test
    public void testAllProducts() {
        logger.info("Testing method AllProducts()");
        List<Product> products = productDao.allProducts();
        Assert.assertEquals(products.size(), 7);
        printProducts(products);
    }
}
