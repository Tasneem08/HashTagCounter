package ufl.ads;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

// This Iterator is used to simplify the consolidate() method. It works by
// gathering a list of the nodes in the list in the constructor since the
// nodes can change during consolidation.
public class NodeListIterator<T extends Comparable<T>, S extends Comparable<S>>
        implements Iterator<Node<T,S>> {

    private Queue<Node<T,S>> items = new LinkedList<Node<T,S>>();

    public NodeListIterator(Node<T,S> start) {
        if (start == null) {
            return;
        }

        Node<T,S> current = start;
        do {
            items.add(current);
            current = current.getNodeNext();
        } while (start != current);
    }

    public boolean hasNext() {
        return items.peek() != null;
    }

    public Node<T,S> next() {
        return items.poll();
    }

    public void remove() {
        throw new UnsupportedOperationException(
                "NodeListIterator.remove is not implemented");
    }
}


