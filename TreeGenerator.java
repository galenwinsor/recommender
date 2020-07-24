package recommender.sol;

import recommender.src.IAttributeDataset;
import recommender.src.IAttributeDatum;
import recommender.src.IGenerator;
import recommender.src.INode;

import java.util.LinkedList;
import java.util.Random;

/**
 * Class that generates decision trees using datasets
 *
 * @param <T>
 */
public class TreeGenerator<T extends IAttributeDatum> implements IGenerator {
    IAttributeDataset<T> data;
    INode classifier;

    /**
     * constructor takes in training data
     *
     * @param initTrainingData
     */
    public TreeGenerator(IAttributeDataset<T> initTrainingData) {
        this.data = initTrainingData;
    }

    /**
     * the recursive part of buildClassifier - makes each layer of the tree one by one
     *
     * @param remAtt     - list of attributes that still haven't appeared in the tree yet
     * @param targetAttr - the attribute to be predicted
     * @return - a decision tree
     */
    public INode treeLayer(LinkedList<String> remAtt, String targetAttr) {
        if (this.data.allSameValue(targetAttr)) { // if all data have the same value for the target attribute
            return new Leaf(this.data.getSharedValue(targetAttr));
        }

        Random rng = new Random();
        int index;
        if (remAtt.size() == 0) { // no more attributes left to partition data with
            return new Leaf(this.data.mostCommonValue(targetAttr));
        } else if (remAtt.size() == 1) { // last attribute left
            index = 0;
        } else { // randomly choose attribute
            index = rng.nextInt((remAtt.size() - 1));
        }

        String att = remAtt.get(index);
        Node<T> parent = new Node<T>(att);
        parent.mostCommon = this.data.mostCommonValue(targetAttr);
        remAtt.remove(att); // remove current attribute from the list so that it won't be used to partition data again

        LinkedList<IAttributeDataset<T>> dataList = this.data.partition(att);

        for (IAttributeDataset<T> set : dataList) {
            TreeGenerator<T> gen = new TreeGenerator<T>(set);
            INode child = gen.treeLayer(remAtt, targetAttr); // recursive call
            parent.children.addLast(child);
            parent.edges.addLast(set.getSharedValue(att));
        }

        return parent;
    }

    @Override
    public INode buildClassifier(String targetAttr) {
        LinkedList<String> remAtt = new LinkedList<String>();
        for (String att : this.data.getAttributes()) {
            remAtt.addLast(att);
        }
        remAtt.remove(targetAttr); // don't want to partition based on target attribute
        this.classifier = this.treeLayer(remAtt, targetAttr);
        return this.classifier;
    }

    @Override
    public Object lookupRecommendation(IAttributeDatum forVals) {
        return this.classifier.lookupDecision(forVals);
    }

    @Override
    public void printTree() {
        this.classifier.printNode("");
    }
}


