package ufl.ads;

public class Demo {
	
	static FibonacciHeap<Integer, String> fibonacciHeap = new FibonacciHeap<Integer, String>();

	public static void main(String[] args) {
		
		Node<Integer, String> node1 = new Node<Integer, String>(4, "key1");
		Node<Integer, String> node2 = new Node<Integer, String>(3, "key2");
		Node<Integer, String> node3 = new Node<Integer, String>(5, "key3");
		Node<Integer, String> node4 = new Node<Integer, String>(7, "key4");
		Node<Integer, String> node5 = new Node<Integer, String>(8, "key5");
		Node<Integer, String> node6 = new Node<Integer, String>(9, "key6");
		Node<Integer, String> node7 = new Node<Integer, String>(10, "key7");
		Node<Integer, String> node8 = new Node<Integer, String>(11, "key8");
		
		fibonacciHeap.insert(node1);
		fibonacciHeap.insert(node2);
		fibonacciHeap.insert(node3);
		fibonacciHeap.insert(node4);
		fibonacciHeap.insert(node5);
		fibonacciHeap.insert(node6);
		fibonacciHeap.insert(node7);
		fibonacciHeap.insert(node8);
		
		fibonacciHeap.print();
		
		fibonacciHeap.extractMax();
		
		fibonacciHeap.print();
		
		fibonacciHeap.extractMax();
		
		fibonacciHeap.print();
		
		fibonacciHeap.increaseKey(node1, 9);
		
		System.out.println("After increase key:");

		
		fibonacciHeap.print();
		

		node1.displayNode();
		
		fibonacciHeap.delete(node3);

		fibonacciHeap.print();
		
		node7.displayNode();
		
		/*fibonacciHeap.insert(node5);
		
		node1.displayNode();*/

		
		
		//fibonacciHeap.print();
		
		
	}

}
