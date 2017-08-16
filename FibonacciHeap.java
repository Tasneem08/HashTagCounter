package ufl.ads;

import java.util.ArrayList;
import java.util.List;

public class FibonacciHeap<T extends Comparable<T>, S extends Comparable<S>> {

    private Node<T,S> maxNode;
    private int size;

    public FibonacciHeap() {
        
		maxNode = null;
        size = 0;
    }

	private FibonacciHeap(Node<T,S> node) {
        
		maxNode = node;
        size = 1;
    }

	private FibonacciHeap(Node<T,S> maxNode, int size) {
        
		this.maxNode = maxNode;
        this.size = size;
    }

    public boolean isEmpty() {
        
		return maxNode == null;
    }

    public void clear() {
        
		maxNode = null;
        size = 0;
    }

    public Node<T,S> insert(Node<T,S> node) {
        
		maxNode = mergeLists(maxNode, node);
        size++;
        return node;
    }

    public Node<T,S> findMaximum() {
        
		return maxNode;
    }

    public void increaseKey(Node<T,S> node, T newKey) {
    	
        if (newKey.compareTo(node.getNodeKey()) < 0) {        	
            throw new IllegalArgumentException("New key is smaller than old key.");
        }

        node.setNodeKey(newKey);
        
        Node<T,S> parent = node.getNodeParent();
        if (parent != null && node.compareTo(parent) > 0) {
            cut(node, parent);
            cascadingCut(parent);

        }
        if (node.compareTo(maxNode) > 0) {
            maxNode = node;
        }
    }

    private void cut(Node<T,S> node, Node<T,S> parent) {
    	
        
    	Node<T,S> maxChild = node.getNodeNext();
        Node<T,S> nextChild = node.getNodeNext();
        
        nextChild = maxChild;
        
        //Get the next maximum child of parent
        do  {
        	
        	if(nextChild.compareTo(maxChild) > 0)
        		maxChild = nextChild;
        	
        	nextChild = nextChild.getNodeNext();        	
        }while(nextChild != node);
        
        if(maxChild == node) 
        	maxChild = null;
    	
		removeNodeFromList(node);
        parent.setNodeDegree(parent.getNodeDegree()-1);
        parent.setNodeChild(maxChild);
        node.setNodeParent(null);        
        mergeLists(maxNode, node);
        node.setMarked(false);
        
    }

    private void cascadingCut(Node<T,S> node) {
        
		Node<T,S> parent = node.getNodeParent();
        if (parent != null) {
            if (node.isMarked()) {
                cut(node, parent);
                cascadingCut(parent);
            } else {
                node.setMarked(true);
            }
        }

    }

    public void delete(Node<T,S> node) {
        
		node.setMaximum(true);
        Node<T,S> parent = node.getNodeParent();
        if (parent != null) {
            cut(node, parent);
            cascadingCut(parent);
        }
        maxNode = node;

        extractMax();
    }

    public Node<T,S> extractMax() {
    	
        Node<T,S> extractedMax = maxNode;
        if (extractedMax != null) {
        	
            //Set parent to null for the maximum's children
            if (extractedMax.getNodeChild() != null) {
                Node<T,S> child = extractedMax.getNodeChild();
                do {
                    child.setNodeParent(null);
                    child = child.getNodeNext();
                } while (child != extractedMax.getNodeChild());
            }

            Node<T,S> nextInRootList = maxNode.getNodeNext() == maxNode ? null : maxNode.getNodeNext();

            //Remove max from root list
            removeNodeFromList(extractedMax);
            size--;

            //Merge the children of the minimum node with the root list
            maxNode = mergeLists(nextInRootList, extractedMax.getNodeChild());
            
            if (nextInRootList != null) {
                maxNode = nextInRootList;
                consolidate();
            }

        }
        return extractedMax;
    }

    private void consolidate() {
	
        List<Node<T,S>> pairwiseCombine = new ArrayList<Node<T,S>>();
        NodeListIterator<T,S> it = new NodeListIterator<T,S>(maxNode);
        
        while (it.hasNext()) {
            Node<T,S> current = it.next();

            while (pairwiseCombine.size() <= current.getNodeDegree() + 1) {
            	pairwiseCombine.add(null);
            }
            
            // If there exists another node with the same degree, merge them
            while (pairwiseCombine.get(current.getNodeDegree()) != null) {
                if (current.getNodeKey().compareTo(pairwiseCombine.get(current.getNodeDegree()).getNodeKey()) < 0) {
                    Node<T,S> temp = current;
                    
                    current = pairwiseCombine.get(current.getNodeDegree());
                    pairwiseCombine.set(current.getNodeDegree(), temp);
                }
                meld(pairwiseCombine.get(current.getNodeDegree()), current);
                pairwiseCombine.set(current.getNodeDegree(), null);
                current.setNodeDegree(current.getNodeDegree() + 1);
                
            }
            
            

            while (pairwiseCombine.size() <= current.getNodeDegree() + 1) {
            	pairwiseCombine.add(null);
            }
            pairwiseCombine.set(current.getNodeDegree(), current);
        }

        maxNode = null;
        for (int i = 0; i < pairwiseCombine.size(); i++) {
            if (pairwiseCombine.get(i) != null) {
                // Remove siblings before merging
            	pairwiseCombine.get(i).setNodeNext(pairwiseCombine.get(i));
            	pairwiseCombine.get(i).setNodePrev(pairwiseCombine.get(i));
                maxNode = mergeLists(maxNode, pairwiseCombine.get(i));
            }
        }
    }

    private void removeNodeFromList(Node<T,S> node) {

        Node<T,S> prev = node.getNodePrev();
        Node<T,S> next = node.getNodeNext();
        
        prev.setNodeNext(next);
        next.setNodePrev(prev);

        node.setNodeNext(node);
        node.setNodePrev(node);
        
    }

    private void meld(Node<T,S> min, Node<T,S> max) {
        
    	removeNodeFromList(min);
        max.setNodeChild(mergeLists(min, max.getNodeChild()));
        min.setNodeParent(max);
        min.setMarked(false);
    }

    //Union another fibonacci heap with this one
    public void union(FibonacciHeap<T,S> other) {
        maxNode = mergeLists(maxNode, other.maxNode);
        size += other.size;
    }

    //Merges two lists and returns the maximum node
    public static <T extends Comparable<T>, S extends Comparable<S>>  Node<T,S> mergeLists(
            Node<T,S> a, Node<T,S> b) {

        if (a == null && b == null) {
            return null;
        }
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        Node<T,S> temp = a.getNodeNext();
        a.setNodeNext(b.getNodeNext());
        a.getNodeNext().setNodePrev(a);
        b.setNodeNext(temp);
        b.getNodeNext().setNodePrev(b);

        return a.compareTo(b) > 0 ? a : b;
    }

    public void print() {
        System.out.println("Fibonacci heap:");
        if (maxNode != null) {
            maxNode.print(0);
        }
    }
}