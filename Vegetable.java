package recommender.sol;

import recommender.src.IAttributeDatum;

import java.util.LinkedList;

/**
 * class representing vegetable data
 */
public class Vegetable implements IAttributeDatum {
    /**
     * has a list of atrtibutes and a corresponding list of values taken at each attribute
     */
    String[] attribute;
    Object[] values;

    /**
     * Constructor
     *
     * @param values are entered as an Array
     */
    public Vegetable(Object[] values) {
        this.attribute = new String[]{"name", "color", "lowCarb", "highFiber", "likeToEat"}; // hard code this array
        this.values = values;
    }

    @Override
    public Object getValueOf(String attributeName) {
        for (int i = 0; i < this.attribute.length; i++) {
            if (this.attribute[i].equals(attributeName)) {
                return this.values[i];
            }
        }
        throw new RuntimeException("attribute not found");
    }

    /**
     * gets the attributes of the vegetable class
     *
     * @return
     */
    public LinkedList<String> getAttributes() {
        LinkedList<String> list = new LinkedList<String>();

        for (String att : this.attribute) {
            list.addLast(att);
        }

        return list;
    }
}
