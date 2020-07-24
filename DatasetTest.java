package recommender.sol;

import recommender.src.IAttributeDataset;
import tester.Tester;

import java.util.LinkedList;

/**
 * class to test the methods in Dataset
 */
public class DatasetTest {
    /**
     * empty constructor for datasetTest
     */
    public DatasetTest() {
    }

    /**
     * @return a dataset of vegetables that will be used in following tests
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
     * @return a dataset of vegetables that will be used in following tests
     */
    public Dataset<Vegetable> setupData2() {
        Object[] lst1 = new Object[]{"spinach", "green", true, true, false};
        Object[] lst2 = new Object[]{"kale", "green", true, true, true};
        Object[] lst3 = new Object[]{"peas", "green", false, true, true};
        //Object[] lst4 = new Object[]{"carrot", "orange", false, false, false};
        Object[] lst5 = new Object[]{"lettuce", "green", true, false, true};

        LinkedList<Vegetable> data = new LinkedList<Vegetable>();
        data.addLast(new Vegetable(lst1));
        data.addLast(new Vegetable(lst2));
        data.addLast(new Vegetable(lst3));
        //data.addLast(new Vegetable(lst4));
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
     * @return a dataset of candidates that will be used in following tests
     */
    public Dataset<Candidate> setupData3() {
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
     * tests the method allSameValue
     *
     * @param t
     */
    public void testAllSameValue(Tester t) {
        Dataset<Vegetable> data1 = this.setupData1();
        Dataset<Vegetable> data2 = this.setupData2(); // no orange
        Dataset<Candidate> cand = this.setupData3();


        t.checkExpect(!(data1.allSameValue("color")));
        t.checkExpect(data2.allSameValue("color")); // all green
        t.checkExpect(!(cand.allSameValue("gender")));

        Dataset<Candidate> cand2 = new Dataset<Candidate>((LinkedList<String>) cand.attributes.clone(), (LinkedList<Candidate>) cand.rows.clone()); // copy of cand
        for (Candidate datum : cand.rows) {
            if (!datum.gender.equals("Female")) {
                cand2.rows.remove(datum); // remove all non female candidates
            }
        }

        t.checkExpect(cand2.allSameValue("gender")); // now gender should be all female

    }

    /**
     * tests the method Filter
     *
     * @param t
     */
    public void testFilter(Tester t) {
        Dataset<Vegetable> data1 = this.setupData1();
        Dataset<Vegetable> data2 = this.setupData2(); // no orange
        Object[] lst4 = new Object[]{"carrot", "orange", false, false, false};
        LinkedList<Vegetable> vegLst = new LinkedList<Vegetable>();
        vegLst.addLast(new Vegetable(lst4));
        Dataset<Vegetable> data3 = new Dataset<Vegetable>(data1.attributes, vegLst); //dataset with just carrot
        Dataset<Vegetable> data4 = new Dataset<Vegetable>(data1.attributes, new LinkedList<Vegetable>()); //empty dataset
        Dataset<Candidate> cand = this.setupData3();

        t.checkExpect(data1.filter("color", "green"), data2);
        t.checkExpect(data1.filter("color", "orange"), data3);
        t.checkExpect(data1.filter("color", "yellow"),
                new Dataset<Vegetable>(data1.attributes, new LinkedList<Vegetable>()));

        Dataset<Candidate> cand2 = new Dataset<Candidate>((LinkedList<String>) cand.attributes.clone(), (LinkedList<Candidate>) cand.rows.clone()); // copy of cand
        for (Candidate datum : cand.rows) {
            if (!datum.gender.equals("Female")) {
                cand2.rows.remove(datum); // remove all non female candidates
            }
        }

        t.checkExpect(cand.filter("gender", "Female"), cand2); //filtering should remove all non female


    }

    /**
     * tests the method extractValues
     *
     * @param t
     */
    public void testExtractValues(Tester t) {
        Dataset<Vegetable> data1 = this.setupData1();
        Dataset<Vegetable> data2 = this.setupData2();
        LinkedList<Object> lst = new LinkedList<Object>(); // for color
        lst.addLast("green");
        lst.addLast("orange");
        LinkedList<Object> lst1 = new LinkedList<Object>(); // for name
        lst1.addLast("spinach");
        lst1.addLast("kale");
        lst1.addLast("peas");
        lst1.addLast("carrot");
        lst1.addLast("lettuce");
        LinkedList<Object> lst3 = new LinkedList<Object>(); // for lowCarb
        lst3.addLast(true);
        lst3.addLast(false);
        LinkedList<Object> lst4 = new LinkedList<Object>(); // for highFiber
        lst4.addLast(true);
        lst4.addLast(false);
        LinkedList<Object> lst5 = new LinkedList<Object>(); // for likeToEat
        lst5.addLast(false);
        lst5.addLast(true);

        Dataset<Candidate> cand = this.setupData3();
        LinkedList<Object> lst6 = new LinkedList<Object>(); // for gender
        lst6.addLast("Female");
        lst6.addLast("Male");


        t.checkExpect(data1.extractValues("color"), lst);
        t.checkExpect(data1.extractValues("name"), lst1);
        t.checkExpect(data1.extractValues("lowCarb"), lst3);
        t.checkExpect(data1.extractValues("highFiber"), lst4);
        t.checkExpect(data1.extractValues("likeToEat"), lst5);
        t.checkExpect(cand.extractValues("gender"), lst6);

    }

    /**
     * tests method partition
     *
     * @param t
     */
    public void testPartition(Tester t) {
        Dataset<Vegetable> data1 = this.setupData1();
        Dataset<Vegetable> data2 = this.setupData2(); // no orange
        Object[] lst4 = new Object[]{"carrot", "orange", false, false, false};
        LinkedList<Vegetable> vegLst = new LinkedList<Vegetable>();
        vegLst.addLast(new Vegetable(lst4));
        Dataset<Vegetable> data3 = new Dataset<Vegetable>(data1.attributes, vegLst); // just carrot
        Dataset<Vegetable> data4 = new Dataset<Vegetable>(data1.attributes, new LinkedList<Vegetable>()); // empty dataset
        Dataset<Candidate> cand = this.setupData3();


        LinkedList<IAttributeDataset<Vegetable>> output = data1.partition("color");

        t.checkExpect(output.get(0), data2);
        t.checkExpect(output.get(1), data3);
        t.checkExpect(output.size() == 2);
        t.checkExpect(data4.partition("color").isEmpty());

        LinkedList<IAttributeDataset<Candidate>> dataLst = new LinkedList<>();
        dataLst.addLast(cand.filter("gender", "Female"));
        dataLst.addLast(cand.filter("gender", "Male"));

        t.checkExpect(cand.partition("gender"), dataLst); // partition gives a list of filtered datasets


    }

    /**
     * tests method getSharedValue
     *
     * @param t
     */
    public void testGetSharedValue(Tester t) {
        Dataset<Vegetable> data1 = this.setupData1();
        Dataset<Vegetable> data2 = this.setupData2(); // no orange
        Object[] lst4 = new Object[]{"carrot", "orange", false, false, false};
        LinkedList<Vegetable> vegLst = new LinkedList<Vegetable>();
        vegLst.addLast(new Vegetable(lst4));
        Dataset<Vegetable> data3 = new Dataset<Vegetable>(data1.attributes, vegLst); // just carrot
        Dataset<Vegetable> data4 = new Dataset<Vegetable>(data1.attributes, new LinkedList<Vegetable>()); // empty dataset
        Dataset<Candidate> cand = this.setupData3();


        t.checkExpect(data2.getSharedValue("color"), "green");
        t.checkExpect(data3.getSharedValue("highFiber"), false);

        t.checkExpect(cand.filter("gender", "Female").getSharedValue("gender"),
                "Female"); // after filtering, the shared value should be what we filtered with


    }

    /**
     * tests method mostCommonValue
     *
     * @param t
     */
    public void testMostCommonValue(Tester t) {
        Dataset<Vegetable> data1 = this.setupData1();
        Dataset<Candidate> cand = this.setupData3();


        t.checkExpect(data1.mostCommonValue("name"), "spinach");
        t.checkExpect(data1.mostCommonValue("color"), "green");
        t.checkExpect(data1.mostCommonValue("lowCarb"), true);
        t.checkExpect(cand.mostCommonValue("hired"), true);


    }

    /**
     * runs tests
     *
     * @param args
     */
    public static void main(String[] args) {
        Tester.run(new DatasetTest());
    }
}

