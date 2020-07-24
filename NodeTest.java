package recommender.sol;

import tester.Tester;

import java.util.LinkedList;

/**
 * class to test methods in Node
 */
public class NodeTest {
    /**
     * empty constructor
     */
    public NodeTest() {
    }

    /**
     * sets up a node to be tested on later
     *
     * @return
     */
    public Node<Vegetable> setupNode() {
        Node<Vegetable> node1 = new Node<Vegetable>("name");
        Node<Vegetable> node2 = new Node<Vegetable>("color");
        Node<Vegetable> node3 = new Node<Vegetable>("color");
        Node<Vegetable> node4 = new Node<Vegetable>("lowCarb");
        Node<Vegetable> node5 = new Node<Vegetable>("lowCarb");
        Node<Vegetable> node6 = new Node<Vegetable>("highFiber");
        Node<Vegetable> node7 = new Node<Vegetable>("highFiber");
        Leaf leaf = new Leaf(true);
        Leaf leaf2 = new Leaf(false);

        node1.edges.addLast("spinach");
        node1.edges.addLast("carrot");

        node2.edges.addLast("green");
        node3.edges.addLast("orange");

        node4.edges.addLast(true);
        node5.edges.addLast(false);

        node6.edges.addLast(true);
        node7.edges.addLast(false);

        node1.children.addLast(node2);
        node1.children.addLast(node3);

        node2.children.addLast(node4);
        node3.children.addLast(node5);

        node4.children.addLast(node6);
        node5.children.addLast(node7);

        node6.children.addLast(leaf);
        node7.children.addLast(leaf2);

        node2.mostCommon = true;

        return node1;
    }

    /**
     * sets up a list of vegetables to test on later
     *
     * @return
     */
    public LinkedList<Vegetable> setupVeg1() {
        Object[] lst1 = new Object[]{"spinach", "green", true, true, true};
        Object[] lst2 = new Object[]{"carrot", "orange", false, false, false};
        Object[] lst3 = new Object[]{"spinach", "blue", true, true, false};

        LinkedList<Vegetable> lst = new LinkedList<>();
        lst.addLast(new Vegetable(lst1));
        lst.addLast(new Vegetable(lst2));
        lst.addLast(new Vegetable(lst3));

        return lst;
    }

    /**
     * tests the method lookupDecision
     *
     * @param t
     */
    public void testLookupDecision(Tester t) {
        Node<Vegetable> node = setupNode();
        LinkedList<Vegetable> lst = setupVeg1();
        Vegetable spinach = lst.get(0);
        Vegetable carrot = lst.get(1);
        Vegetable blueSpinach = lst.get(2);
        node.printNode("");

        t.checkExpect(node.lookupDecision(spinach), true);
        t.checkExpect(node.lookupDecision(carrot), false);
        t.checkExpect(node.lookupDecision(blueSpinach), true);
    }

    /**
     * runs tests
     *
     * @param args
     */
    public static void main(String[] args) {
        Tester.run(new NodeTest());
    }
}
