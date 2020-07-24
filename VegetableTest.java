package recommender.sol;

import recommender.src.IAttributeDatum;
import tester.Tester;

import java.util.LinkedList;

/**
 * class to test the methods in vegetable
 */
public class VegetableTest {
    /**
     * sets up a list of vegetables to be tested on later
     *
     * @return
     */
    public LinkedList<Vegetable> setupData1() {
        Object[] lst1 = new Object[]{"spinach", "green", true, true, false};
        Object[] lst2 = new Object[]{"kale", "green", true, true, true};
        Object[] lst3 = new Object[]{"peas", "green", false, true, true};
        Object[] lst4 = new Object[]{"carrot", "orange", false, false, false};
        Object[] lst5 = new Object[]{"lettuce", "green", true, false, true};

        LinkedList<Vegetable> lst = new LinkedList<>();
        lst.addLast(new Vegetable(lst1));
        lst.addLast(new Vegetable(lst2));
        lst.addLast(new Vegetable(lst3));
        lst.addLast(new Vegetable(lst4));
        lst.addLast(new Vegetable(lst5));

        return lst;
    }

    /**
     * tests the method getValueOf
     *
     * @param t
     */
    public void testGetValueOf(Tester t) {
        LinkedList<Vegetable> test = setupData1();
        IAttributeDatum veg = test.get(0);

        t.checkExpect(veg.getValueOf("name"), "spinach");
        t.checkExpect(veg.getValueOf("color"), "green");
        t.checkExpect(veg.getValueOf("lowCarb"), true);
        t.checkExpect(veg.getValueOf("highFiber"), true);
        t.checkExpect(veg.getValueOf("likeToEat"), false);
    }

    /**
     * tests the method getAttributes
     *
     * @param t
     */
    public void testGetAttributes(Tester t) {
        LinkedList<Vegetable> test = setupData1();
        Vegetable veg = test.get(0);
        LinkedList<String> atts = new LinkedList<>();
        atts.addLast("name");
        atts.addLast("color");
        atts.addLast("lowCarb");
        atts.addLast("highFiber");
        atts.addLast("likeToEat");

        t.checkExpect(veg.getAttributes(), atts);
    }

    /**
     * main method runs tests
     *
     * @param args
     */
    public static void main(String[] args) {
        Tester.run(new VegetableTest());
    }
}
