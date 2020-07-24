package recommender.sol;

import recommender.src.IAttributeDatum;
import recommender.src.INode;

public class Leaf implements INode {
    /**
     * fields - decision is an object (depends on the attribute)
     */
    private Object decision;

    /**
     * constructor
     *
     * @param decision
     */
    public Leaf(Object decision) {
        this.decision = decision;
    }

    @Override
    public Object lookupDecision(IAttributeDatum attrVals) {
        return this.decision;
    }

    // TODO
    @Override
    public void printNode(String leadspace) {
        System.out.println(leadspace + "DECISION: " + this.decision.toString());
    }
}



