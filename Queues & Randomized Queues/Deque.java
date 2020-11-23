
import java.util.Iterator;
import java.util.NoSuchElementException;


//_______________________________________________________	

//CLASS DEQUE
public class Deque <Item> implements Iterable<Item>{

	private Node first;
	private Node last;
	private int length=0;

	//construct an empty deque
	public Deque(){
	}

	//is the deque empty?
	public boolean isEmpty(){
		return length==0;
	}

	//return the number of items on the deque
	public int size(){
		return length;
	}

	//add the item to the front
	public void addFirst(Item item){
		if(item==null) throw new IllegalArgumentException();

		Node oldFirst=first;
		first=new Node();
		first.item=item;

		if(isEmpty()) last=first;
		else{
			oldFirst.previous=first;
			first.next=oldFirst;
		}
		length++;
	}

	//add the item to the back
	public void addLast(Item item){
		if(item==null) throw new IllegalArgumentException();

		Node oldlast=last;
		last=new Node();
		last.item=item;
		last.previous=oldlast;
		last.next=null;
		if(isEmpty()) first=last;
		else oldlast.next=last;

		length++;
	}

	//remove and return the item from the front
	public Item removeFirst(){
		if (isEmpty()) throw new NoSuchElementException("Deque underflow");
		Item item=first.item;
		first=first.next;
		if(first!=null)
			first.previous=null;
		if(isEmpty())last=null;
		length--;
		return item;
	}

	public Item removeLast(){
		if (isEmpty())  throw new NoSuchElementException("Deque underflow");
		Item item=last.item;
		last=last.previous;
		if(last!=null)
			last.next=null;
		if(isEmpty())first=null;
		length--;
		return item;
	}



	//return an iterator over items in order from front to back
	public Iterator<Item> iterator(){
		return new ListIterator();
	}

	//_______________________________________________________

	//AUXILIAR CLASS NODE
	private class Node{
		private Item item;
		private Node next;
		private Node previous;
	}

	//______________________________________________________

	//AUXILIAR CLASS TO ITERATE 
	private class ListIterator implements Iterator<Item>{

		private Node atualItem=first;

		@Override
		public boolean hasNext(){
			return atualItem!=null;
		}

		@Override
		public Item next(){
			if(!hasNext()) throw new NoSuchElementException();

			Item item=atualItem.item;
			atualItem=atualItem.next;
			return item;

		}

		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}



	}

	//______________________________________________________

	//MAIN

	public static void main(String[] args){
		Deque< Integer> deque =new Deque<> ();
		deque.addFirst(1);
		deque.removeLast();
		System.out.println(deque.isEmpty());
	}
}
