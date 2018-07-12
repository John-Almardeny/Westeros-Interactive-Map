import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class QueueLinkedList<Customer> implements Queue<Customer> {
    Node first, last;
    int size;

    public class Node {
        Customer ele;
        Node next;
    }
    public QueueLinkedList<Customer> enqueue(Customer ele) {
            Node current = last;
            last = new Node();
            last.ele = ele;
            last.next = null;
            size++;

            if (current == null) 
                first = last;
            else 
                current.next = last;

            return this;
        }

public Customer peek() {
    Customer ele = first.ele;
    
    return ele;
}

@Override
public boolean addAll(Collection<? extends Customer> arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void clear() {
	// TODO Auto-generated method stub
	
}

@Override
public boolean contains(Object arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean containsAll(Collection<?> arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean isEmpty() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Iterator<Customer> iterator() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean remove(Object arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean removeAll(Collection<?> arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean retainAll(Collection<?> arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public int size() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public Object[] toArray() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public <T> T[] toArray(T[] arg0) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean add(Customer arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Customer element() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean offer(Customer arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Customer poll() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Customer remove() {
	// TODO Auto-generated method stub
	return null;
}
}