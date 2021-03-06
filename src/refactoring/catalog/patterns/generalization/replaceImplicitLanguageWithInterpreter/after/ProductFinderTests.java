package refactoring.catalog.patterns.generalization.replaceImplicitLanguageWithInterpreter.after;

import junit.framework.TestCase;
import refactoring.catalog.patterns.generalization.replaceImplicitLanguageWithInterpreter.domain.Product;
import refactoring.catalog.patterns.generalization.replaceImplicitLanguageWithInterpreter.domain.ProductRepository;
import refactoring.catalog.patterns.generalization.replaceImplicitLanguageWithInterpreter.domain.ProductSize;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * @author mdrozdz
 */
public class ProductFinderTests extends TestCase {
    private ProductFinder finder;

    private Product fireTruck =
            new Product("f1234", "Fire Truck",
                    Color.red, 8.95f, ProductSize.MEDIUM);

    private Product barbieClassic =
            new Product("b7654", "Barbie Classic",
                    Color.yellow, 15.95f, ProductSize.SMALL);

    private Product frisbee =
            new Product("f4321", "Frisbee",
                    Color.pink, 9.99f, ProductSize.LARGE);

    private Product baseball =
            new Product("b2343", "Baseball",
                    Color.white, 8.95f, ProductSize.NOT_APPLICABLE);

    private Product toyConvertible =
            new Product("p1112", "Toy Porsche Convertible",
                    Color.red, 230.00f, ProductSize.NOT_APPLICABLE);

    protected void setUp() {
        finder = new ProductFinder(createProductRepository());
    }

    private ProductRepository createProductRepository() {
        ProductRepository repository = new ProductRepository();
        repository.add(fireTruck);
        repository.add(barbieClassic);
        repository.add(frisbee);
        repository.add(baseball);
        repository.add(toyConvertible);
        return repository;
    }

    public void testFindByColor() {
        List foundProducts = finder.selectBy(new ColorSpec(Color.RED));
        assertEquals("found 2 red products", 2, foundProducts.size());
        assertTrue("found fireTruck", foundProducts.contains(fireTruck));
        assertTrue("found Toy Porsche Convertible", foundProducts.contains(toyConvertible));
    }

    public void testFindByPrice() {
        BelowPriceSpec spec = new BelowPriceSpec(8.95f);
        List foundProducts = finder.selectBy(spec);
        assertEquals("found products that cost $8.95", 2, foundProducts.size());
        for (Iterator i = foundProducts.iterator(); i.hasNext();) {
            Product p = (Product) i.next();
            assertTrue(p.getPrice() == 8.95f);
        }
    }

    public void testFindBelowPriceAvoidingAColor() {
        List foundProducts =
                finder.selectBy(new AndSpec(new BelowPriceSpec(9.00f), new NotSpec(new ColorSpec(Color.white))));
        assertEquals(
                "found 1 non-white product < $9.00",
                1,
                foundProducts.size());
        assertTrue("found fireTruck", foundProducts.contains(fireTruck));

        foundProducts = finder.selectBy(new AndSpec(new BelowPriceSpec(9.00f), new NotSpec(new ColorSpec(Color.RED))));
        assertEquals(
                "found 1 non-red product < $9.00",
                1,
                foundProducts.size());
        assertTrue("found baseball", foundProducts.contains(baseball));
    }
}
