package recommender.sol;

import java.util.LinkedList;

/**
 * another class to test tree generator
 */
public class TreeGenTest {
    public TreeGenTest() {
    }

    ;

    /**
     * NOTE: Do not modify anything in the files except TODO:
     */

    // the list of candidate objects to generate the tree on
    static Dataset<Candidate> candidates;

    /**
     * A method to set up candidate attributes and training data
     */


    /**
     * uses the CSVParser to set up candidates
     */
    public static void setupCandidates() {
        LinkedList<String> canAttr = new LinkedList<String>();
        // different attributes to consider similar to the Candidate class
        canAttr.add("gender");
        canAttr.add("leadershipExperience");
        canAttr.add("lastPositionDuration");
        canAttr.add("numWorkExperiences");
        canAttr.add("programmingLanguages");
        canAttr.add("gpa");
        canAttr.add("location");
        canAttr.add("hired");

        /**
         * TODO: change this filepath
         */
        String filepath = "train_candidates_equal.csv";

        RecommenderCSVParser<Candidate> parser = new RecommenderCSVParser<Candidate>();

        LinkedList<Candidate> allCandidates = new LinkedList<>();
        // parsing the dataset in the form of a CSV file, CommaSeparatedValues.
        allCandidates = (LinkedList<Candidate>) parser.parse(Candidate.class, filepath, ',', true);
        // if the filename is the correlated variable, we're not looking at the gender
        // variable at all while looking
        // at different Candidate objects while building the tree.
        if (filepath.equals("train_candidates_correlated.csv")) {
            canAttr.remove("gender");
        }

        BiasTest.candidates = new Dataset<Candidate>(canAttr, allCandidates);
    }

    /**
     * main method builds and prints a decision tree for the data
     *
     * @param args
     */
    public static void main(String[] args) {
        TreeGenTest.setupCandidates();
        tester.Tester.run(new TreeGenTest());

        TreeGenerator<Candidate> builder = new TreeGenerator<Candidate>(BiasTest.candidates);
        builder.buildClassifier("hired");
        builder.classifier.printNode("");
    }
}
