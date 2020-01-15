/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektas_skiplist;

/**
 *
 * @author Simonas
 */
public interface SkipListInterface<E> {
    
    
    void add(int k, E value);
    
    E getByKey(int k);
    
    E getByIndex(int index);
    
    boolean contains(E value);
    
    boolean remove(E value);
    
    int getSize();
    
    boolean containsKey(int key);
    
    void clear();
    
    int keyOf(E e);
    
    int indexOfObject(E e);
    
    int containsAmount(E e);
    
    public Object[] toArray();
    
    public E set(int index, E element);
}
