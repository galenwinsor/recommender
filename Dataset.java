package recommender.sol;

import recommender.src.IAttributeDataset;
import recommender.src.IAttributeDatum;

import java.util.LinkedList;

/**
 * class that implements IAttributeDataset
 *
 * @param <T> the class of the type of datum stored in a dataset
 */
public class Dataset<T extends IAttributeDatum> implements IAttributeDataset<T> {
    /**
     * fields - list of attributes that the datum has
     * - list of data - of type T
     */
    public LinkedList<String> attributes;
    public LinkedList<T> rows;

    /**
     * constructor
     *
     * @param attributes list of attributes that the datum has
     * @param rows       list of data - of type T
     */
    public Dataset(LinkedList<String> attributes, LinkedList<T> rows) {
        this.attributes = attributes;
        this.rows = rows;
    }

    @Override
    public LinkedList<String> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean allSameValue(String ofAttribute) {
        Object val = this.rows.get(0).getValueOf(ofAttribute);
        for (T datum : this.rows) {
            if (datum.getValueOf(ofAttribute) != val) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.rows.size();
    }

    public IAttributeDataset<T> filter(String onAttribute, Object value) {
        LinkedList<T> list = new LinkedList<T>();
        for (T datum : this.rows) {
            if (datum.getValueOf(onAttribute).equals(value)) {
                list.addLast(datum);
            }
        }
        Dataset<T> data = new Dataset<T>(this.attributes, list);
        return data;
    }

    /**
     * @param onAttribute an attribute of the Data in the dataset
     * @return a list of all the values that data in the dataset take for the given attribute
     */
    public LinkedList<Object> extractValues(String onAttribute) {
        LinkedList<Object> list = new LinkedList<Object>();
        for (T datum : this.rows) {
            Object value = datum.getValueOf(onAttribute);
            if (!(list.contains(value))) {
                list.addLast(value);
            }
        }
        return list;
    }

    @Override
    public LinkedList<IAttributeDataset<T>> partition(String onAttribute) {
        LinkedList<Object> extracted = this.extractValues(onAttribute);
        LinkedList<IAttributeDataset<T>> list1 = new LinkedList<IAttributeDataset<T>>();
        for (Object value : extracted) {
            list1.addLast(this.filter(onAttribute, value));
        }
        return list1;
    }

    @Override
    public Object getSharedValue(String ofAttribute) {
        assert this.allSameValue(ofAttribute);
        return this.rows.get(0).getValueOf(ofAttribute);
    }

    @Override
    public Object mostCommonValue(String ofAttribute) {
        LinkedList<Object> extracted = this.extractValues(ofAttribute);
        LinkedList<Integer> count = new LinkedList<Integer>();
        for (Object value : extracted) {
            int i = 0;
            for (T datum : this.rows) {
                if (datum.getValueOf(ofAttribute) == value) {
                    i++;
                }
            }
            count.addLast(i);
        }

        int currentMax = 0;
        int index = 0;
        int finalIndex = 0;
        for (int i : count) {
            if (i > currentMax) {
                currentMax = i;
                finalIndex = index;
                index++;
            }
        }

        return extracted.get(finalIndex);
    }
}
