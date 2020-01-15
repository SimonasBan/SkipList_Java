/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektas_skiplist;

import Utils.Ks;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Simonas
 */
public class SL<E extends Comparable<E>> implements SkipListInterface<E>{
    
    private final int MAX_LEVEL =10;

	// no tail, as tail will be always null
	private SLNode<E> head;

	private int size;

	// control the current max height, avoid a sudden boost in level
	private int currentMaxLevel;
	private Random seed = new Random();

	public SL() {
		this.head = new SLNode<>(0, null, MAX_LEVEL);
		this.currentMaxLevel = 1;
		this.size = 0;
	}

	public void add(int key, E value) {

            int level = createLevel();

            SLNode<E> newNode = new SLNode<>(key, value,level);

            SLNode tempNode = head;

            for (int i = currentMaxLevel - 1; i >= 0; i--) {
                    while (tempNode.next[i] != null) {                               

                            if (tempNode.next[i].key > key) {
                                    break;
                            }
                            tempNode = tempNode.next[i];
                    }

                    if (i <= level) {
                            newNode.next[i] = tempNode.next[i];
                            tempNode.next[i] = newNode;
                    }
            }

            size++;
	}
        
        
        
        public E getByKey(int searchKey) {
            SLNode<E> x = this.head;          
            for (int i=currentMaxLevel - 1; i>=0; i--){       
                while ((x.next[i] != null) && (searchKey > x.next[i].key)){
                    x = x.next[i];
                }
            }
            x = x.next[0];  
            if ((x != null) && (searchKey == x.key)){
                return x.value;    
            }
            else return null;                 
                            
        }
        
        @Override
        public E getByIndex(int index) {
            SLNode<E> x = this.head;
            if(index < 0 || index >= size){
                throw new IndexOutOfBoundsException();
            }
            for (int i = 0; i <= index; i++) {
                x=x.next[0];
            }
            if(x != null){
                return x.value;
            }
            else return null;  
        }
        
        
        //If it returns -1, it means that there is not such an object
        @Override
        public int indexOfObject(E e){
            
            SLNode<E> x = this.head;
            
            if(e == null){
                throw new NullPointerException();
            }
            
            if(contains(e)){
                
                for (int i = 0; i <= size; i++) {
                    x=x.next[0];
                    
                    if(e.equals(x.value)){
                        return i;
                    }
                }
            }
            
            return -1;
        }
        
        //Grąžina reikšmę, kurią pakeitė
        public E set(int key, E element){
            SLNode<E> x = this.head;          
            for (int i=currentMaxLevel - 1; i>=0; i--){       
                while ((x.next[i] != null) && (key > x.next[i].key)){
                    x = x.next[i];
                }
            }
            x = x.next[0];  
            if ((x != null) && (key == x.key)){
                E removedValue = x.value;
                x.value = element;
                return removedValue;
            }
            else return null;  
        }
        
        public int containsAmount(E e){
            int count = 0;            
            
            
            SLNode tempNode = head;
            for (int i = currentMaxLevel - 1; i >= 0; i--) {
                    
                while (null != tempNode.next[i]) {

                    if (greaterThan((E) tempNode.next[i].getValue(), e)) {
                        break;
                    }
                    if (equalTo((E) tempNode.next[i].getValue(), e)) {
                        count++;
                        tempNode = tempNode.next[i];
                        if(tempNode.next[i] != null){
                            while(tempNode.next[i].value == e){
                                count++;
                                tempNode = tempNode.next[i];
                            }
                        }
                        return count;
                    }
                    tempNode = tempNode.next[i];
                }
            }
            return count;
        }

	public boolean contains(E value) {
            SLNode tempNode = head;
            for (int i = currentMaxLevel - 1; i >= 0; i--) {
                    
                while (null != tempNode.next[i]) {

                    if (greaterThan((E) tempNode.next[i].getValue(), value)) {
                        break;
                    }
                    if (equalTo((E) tempNode.next[i].getValue(), value)) {
                        return true;
                    }
                    tempNode = tempNode.next[i];
                }
            }

            return false;
	}
        
        
        public boolean containsKey(int key) {
            SLNode tempNode = head;
            for (int i = currentMaxLevel - 1; i >= 0; i--) {
                while (null != tempNode.next[i]) {
                    if (tempNode.next[i].key > key) {
                        break;
                    }
                    if (tempNode.next[i].key == key) {
                        return true;
                    }
                    tempNode = tempNode.next[i];
                }
            }

            return false;
	}
        
        public void clear(){
            head = new SLNode<>(0, null, MAX_LEVEL);
            currentMaxLevel = 1;
            size = 0;
        }

	public boolean remove(E value) {
            SLNode cur_walker = head;
            boolean result = false;
            for (int i = currentMaxLevel - 1; i >= 0; i--) {
                while (null != cur_walker.next[i]) {
                    if (greaterThan((E) cur_walker.next[i].getValue(), value)) {
                        break;
                    }
                    if (equalTo((E) cur_walker.next[i].getValue(), value)) {
                        cur_walker.next[i] = cur_walker.next[i].next[i];
                        result = true;
                        break;
                    }
                    cur_walker = cur_walker.next[i];
                }
            }
            if(result){
                size--;
            }
            return result;
	}

	public int getSize(){
	    return this.size;
        }
        
        public Object[] toArray(){
            SLNode tempNode = head;
            Object[] array = new Object[size];
            int count = 0;
            while (tempNode.next[0] != null ) {
		tempNode = tempNode.next[0];
                array[count] = tempNode.value;
                count++;
            }
            return array;
        }
        
        public String[][] getModelList() {
        String[][] result = new String[size][];
        int count = 0;
        
            if(head.next[0]!=null){
                SLNode tempNode = head;
                int start = MAX_LEVEL - 1;


                while (null == tempNode.next[start]) {
                        start--;
                }


                tempNode = head;
                List<SLNode> ref = new ArrayList<>();
                while (tempNode != null) {
                        ref.add(tempNode);
                        tempNode = tempNode.next[0];
                }

                for (int i = 0; i <= start; i++) {
                        List<String> list = new ArrayList<>();
                        tempNode = head;
                        tempNode = tempNode.next[i];


                        list.add("[ " + i + " ]");
                        int levelIndex = 1;
                        while (tempNode != null) {


                                if (i > 0) {
                                        while (ref.get(levelIndex).getValue() != tempNode.getValue()) {
                                                levelIndex++;
                                                list.add("-->");
                                                list.add("-->");
                                        }
                                        levelIndex++;
                                }
                                list.add("-->");
                                list.add(tempNode.toString());
                                tempNode = tempNode.next[i];
                        }
                        result[i] = list.toArray(new String[0]);
                }

            }

        return result;
        }

	public void Print() {
            if(head.next[0]!=null){
            SLNode tempNode = head;
            int start = MAX_LEVEL - 1;
            while (null == tempNode.next[start]) {
                start--;
            }

            tempNode = head;
            List<SLNode> ref = new ArrayList<>();
            while (tempNode != null) {
                ref.add(tempNode);
                tempNode = tempNode.next[0];
            }

            for (int i = 0; i <= start; i++) {

                tempNode = head;
                tempNode = tempNode.next[i];
                System.out.print( "Layer "+ i + " | level " + MAX_LEVEL + " | head |");

                int levelIndex = 1;
                while (tempNode != null) {


                    if (i > 0) {
                        while (ref.get(levelIndex).getValue() != tempNode.getValue()) {
                            levelIndex++;
                            System.out.print( "--------------------------");
                        }
                        levelIndex++;
                    }

                    System.out.print( "---> " + tempNode);
                    tempNode = tempNode.next[i];
                }

                System.out.println();
            }
            }
            else{
                Ks.oun("Sąrašas tuščias");
            }
	}
        
        public int keyOf(E e){         
            SLNode node = head;
		for (int i = currentMaxLevel - 1; i >= 0; i--) {
			// walk down the level until it find a range
			while (null != node.next[i]) {
				// when at bottom level, i is always 0, needs to find the right node to stop
				if (greaterThan((E) node.next[i].getValue(), e)) {
					break;
				}
				if (equalTo((E) node.next[i].getValue(), e)) {
					return node.next[i].key;
				}
				node = node.next[i];
			}
		}

		return -1;
            
          
        }
        



	private boolean lessThan(E a, E b) {
		return a.compareTo(b) < 0;
	}

	private boolean equalTo(E a, E b) {
		return a.compareTo(b) == 0;
	}

	private boolean greaterThan(E a, E b) {
		return a.compareTo(b) > 0;
	}

	private int createLevel() {
		boolean head = true;
		int level = 0;

		for (int i = 0; i < MAX_LEVEL; i++) {
			head &= seed.nextBoolean();

			if (head) {
				level++;
				if (level == this.currentMaxLevel) {
					currentMaxLevel++;
					break;
				}
			} else {
				break;
			}
		}

		return level;
	}

    
    
    
    
    
    
    protected static class SLNode<E extends Comparable<E>> {

	public SLNode[] next;
        private int key;
	private E value;
	private int level;

	public SLNode(int key, E value, int level)
	{
                this.key = key;
		this.value = value;
		this.level = level;
		// 0 is the base level
		this.next = new SLNode[level+1];
	}

	public E getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return "[ level " + level + " | value "+value + " ]";
	}
    }
    
     
}
