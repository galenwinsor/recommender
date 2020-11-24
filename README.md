# Recommender
A supervised machine learning program that can be used to make recommendations based on a training dataset, i.e., whether you'll like a new food based on your previous preferences. 

## High-level description

Our recommender system has a class for datasets which defines a dataset object for the recommender to be called on. A
dataset has a list of data, which are each of type IAttributeDatum, an interface provided in the source code. The
dataset also has a list of attributes, which are the attributes of the data type in the dataset.

The Leaf and Node classes define the structure of a decision tree. A node has an attribute, a list of children, which
are the nodes connected to the current node, a list of edges, which are values corresponding to the children, and the
most common value for the target attribute in the decision tree. A leaf contains a value. If a search through the
decision tree reaches that leaf, it terminates and returns the value in the leaf as the found decision.

The TreeGenerator class generates a decision tree of nodes and leaves using a dataset and a target attribute in that
dataset. It has a dataset, and a starting node, which is initialized when the tree is built. The TreeGenerator class can
also be given a datum of the same type in the tree's dataset, and will return the recommended value for the tree's
target attribute.

The Vegetable and Candidate classes both implement IAttributeDatum, and are examples of possible data.


