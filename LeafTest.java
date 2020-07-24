package recommender.sol;

import recommender.src.IAttributeDatum;
import recommender.src.INode;
import tester.Tester;

import java.util.LinkedList;

/**
 * class to test methods in Leaf
 */
public class LeafTest {
    /**
     * empty constructor
     */
    public LeafTest() {
    }

    /**
     * @return dataset of vegetables to be used in following tests
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
     * @return dataset of candidates to be used in following tests
     */
    public Dataset<Candidate> setupData2() {
        LinkedList<Candidate> data = new LinkedList<Candidate>();
        data.addLast(new Candidate("Female", false, "<1", "2", "5+", "2.0-2.9", "local", true));
        data.addLast(new Candidate("Female", true, "1-2", "3+", "5+", "2.0-2.9", "nonlocal", false));
        data.addLast(new Candidate("Male", false, "<1", "2", "5+", "2.0-2.9", "local", true));
        data.addLast(new Candidate("Female", false, "3+", "2", "5+", "2.0-2.9", "local", true));
        data.addLast(new Candidate("Male", false, "<1", "2", "5+", "2.0-2.9", "local", false));

        LinkedList<String> atts = new LinkedList<String>();
        atts.addLast("Gender");
        atts.addLast("Leadership Experience");
        atts.addLast("Last Position Duration");
        atts.addLast("Number of Work Experiences");
        atts.addLast("Programming Languages");
        atts.addLast("GPA");
        atts.addLast("Location");
        atts.addLast("Hired");

        return new Dataset<Candidate>(atts, data);
    }

    /**
     * tests method lookupDecision
     *
     * @param t
     */
    public void testLookupDecision(Tester t) {
        Dataset<Vegetable> datav = this.setupData1();
        IAttributeDatum veg = datav.rows.get(0);
        Dataset<Candidate> datac = this.setupData2();
        IAttributeDatum cand = datac.rows.get(0);

        INode leaf = new Leaf(true);
        t.checkExpect(leaf.lookupDecision(veg), true);
        t.checkExpect(leaf.lookupDecision(cand), true); // leaf should act as the base case - return its own value
    }

    /**
     * runs tests
     *
     * @param args
     */
    public static void main(String[] args) {
        Tester.run(new LeafTest());
    }
}
