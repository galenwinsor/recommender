package recommender.sol;

import tester.Tester;

import java.util.LinkedList;

/**
 * class to test methods in TreeGenerator
 */
public class TreeGeneratorTest {
    /**
     * sets up dataset of vegetables
     *
     * @return
     */
    public Dataset<Vegetable> setupData1() {
        Object[] lst1 = new Object[]{"spinach", "green", true, true, false};
        Object[] lst2 = new Object[]{"kale", "green", true, true, true};
        Object[] lst3 = new Object[]{"peas", "green", false, true, true};
        Object[] lst4 = new Object[]{"carrot", "orange", false, false, false};
        Object[] lst5 = new Object[]{"lettuce", "green", true, false, true};

        LinkedList<Vegetable> data = new LinkedList<Vegetable>();
        data.addLast(new Vegetable(lst1));
        data.addLast(new Vegetable(lst2));
        data.addLast(new Vegetable(lst3));
        data.addLast(new Vegetable(lst4));
        data.addLast(new Vegetable(lst5));

        LinkedList<String> atts = new LinkedList<String>();
        atts.addLast("name");
        atts.addLast("color");
        atts.addLast("lowCarb");
        atts.addLast("highFiber");
        atts.addLast("likeToEat");

        return new Dataset<Vegetable>(atts, data);
    }

    /**
     * runs the tree generator and prints out the tree
     *
     * @param t
     */
    public void testTreeLayer(Tester t) {
        Dataset<Vegetable> data1 = this.setupData1();
        TreeGenerator<Vegetable> tg = new TreeGenerator<Vegetable>(data1);
        tg.buildClassifier("likeToEat");
        tg.classifier.printNode("");

        t.checkExpect(0 == 0);

    }

    /**
     * main method runs tests
     *
     * @param args
     */
    public static void main(String[] args) {
        Tester.run(new TreeGeneratorTest());
    }
}
