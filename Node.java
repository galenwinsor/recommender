package recommender.sol;


import recommender.src.IAttributeDatum;
import recommender.src.INode;

import java.util.LinkedList;

/**
 * class that represents nodes of a decision tree
 *
 * @param <T>
 */
public class Node<T extends IAttributeDatum> implements INode {
    /**
     * each node has a corresponding attribute, a list of edges (the different values of the attribute),
     * a list of corresponding children (one for each edge)
     */
    public String attribute;
    public LinkedList<Object> edges;
    public LinkedList<INode> children;
    public Object mostCommon; //keeps track of most common decision made here onwards

    /**
     * Constructor - sets all edges, children and mostCommon to null.
     *
     * @param attribute
     */
    public Node(String attribute) {
        this.attribute = attribute;
        this.edges = new LinkedList<Object>();
        this.children = new LinkedList<INode>();
        this.mostCommon = null;
    }

    @Override
    public Object lookupDecision(IAttributeDatum atrVals) {
        Object value = atrVals.getValueOf(this.attribute);
        int index = 0;

        if (this.edges.contains(value)) {
            for (Object val : this.edges) {
                if (value.equals(val)) {
                    break;
                } else {
                    index++;
                }
            }

            return this.children.get(index).lookupDecision(atrVals);
        } else {
            return this.mostCommon;
        }
    }

    @Override
    public void printNode(String leadspace) {
        System.out.println(leadspace + "ATTRIBUTE: " + this.attribute + "\n" + leadspace + "EDGES: " + this.edges.toString() + "\n");
        for (INode child : this.children) {
            child.printNode(leadspace + "     ");
        }
    }
}
