
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;



public class RandomizedQueue <Item> implements Iterable <Item>{


	private Item[] items=(Item[]) new Object[2];
	private int size=0;


	public RandomizedQueue(){
	} 


	public boolean isEmpty(){
		return size==0;
	} 


	public int size(){
		return size;
	}
	private void resize(int capacity){
		Item[] copy=(Item[]) new Object[capacity];
		for (int i=0; i < size; i++)
			copy[i]=items[i];
		items=copy;
	}


	public void enqueue(Item item){
		if(item==null) throw new NullPointerException();
		if(size==items.length) resize(2*items.length);
		items[size++]=item;
	}



	public Item dequeue(){
		if(size==0) throw new NoSuchElementException();
		int randomPos =StdRandom.uniform(size);
		Item item=items[randomPos];
		items[randomPos]=items[--size];
		items[size]=null; 
		if(size > 0 && size==items.length / 4) 
		resize(items.length/2);
		return item;
	}


	public Item sample(){
		if(size==0) throw new NoSuchElementException();	
		return items[StdRandom.uniform(size)];
	}


	@Override
	public Iterator<Item> iterator(){
		return new ListIterator();
	}

	public static void main(String[] args){
		RandomizedQueue<Integer> queue=new RandomizedQueue<>();
		for (int i = 0; i < 10; i++) {
			queue.enqueue(i);
		}
		System.out.println(queue.size());
		for (Integer i : queue) {
			System.out.println(i);
		}
		System.out.println("sample:" + queue.sample());
		System.out.println("dequeue");
		while (!queue.isEmpty()) System.out.println(queue.dequeue());
		System.out.println(queue.size());
	}


	private class ListIterator implements Iterator<Item>{

		private int i;
		private final Item[] vetorrandom;
		
		public ListIterator(){
			vetorrandom=(Item[])new Object[size];
			for (int j =0; j < size; j++){
				vetorrandom[j]=items[j];
			}
			i=size;
		}

		@Override
		public boolean hasNext(){
			return i>0;
		}

		@Override
		public Item next(){
			if (!hasNext()) throw new NoSuchElementException();
			int randomPos =StdRandom.uniform(i--);
			Item item=vetorrandom[randomPos];
			vetorrandom[randomPos]=vetorrandom[i];
			vetorrandom[i]=null; 
			return item;
		}

		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}

	}



}