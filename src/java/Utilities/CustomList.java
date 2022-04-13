package Utilities;

import java.util.Iterator;

/**
 * @author Darren Sills
 * Generic, custom linked list implementing iterator
 */
public class CustomList<T> implements Iterable<T> {
    private int size; //size of the list
    public CustomNode<T> head; //first node in the list

    /**
     * Add an element to the list
     * @param e element to be added to the list
     */
    public void add(T e) {
        if(head == null){ //if no nodes in the list, head becomes the first node
            head = new CustomNode<>(e);
        }
        CustomNode<T> newE = new CustomNode<>(e);
        CustomNode<T> current = head;

        while (current.getNext() != null) {  //iterate through the list from the head
            current = current.getNext();    // and then add the new node at the end
        }
        current.setNext(newE); //the last node's "next" reference set to our new node

        size++; //add one to the size
    }

    /**
     * Get the index of a given element
     * @param index index of the element to be returned
     * @return the element at index
     */
    public T get(int index) {
        if (index < 0) {
            return null;
        }

        CustomNode<T> current;
        if (head != null) { //nothing happens if the list is empty
            current = head.getNext();
            for (int i = 0; i < index; i++) { //iterate until reaching given index
                if (current.getNext() == null) {//if the index is at null, return null
                    return null;
                }
                current = current.getNext();
            }
            return current.getData(); //return the element at the index
        }
        return null;
    }

    /**
     * Remove all elements from the list
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes the element at the specified position in this list.
     * @param index index of the element to be returned
     * @return the element deleted
     */
    public T remove(int index) {
        CustomNode<T> current = head;

        if (head != null) {
            for (int i = 0; i < index; i++) {
                if (current.getNext() == null) {
                    return null;
                } else {
                    current = current.getNext();
                }
            }
            current.setNext(current.getNext().getNext());
            size--; //decrease the size of the list
        }
        return null;
    }

    /**
     * removes the given element from the list.
     * @param e element to be removed from the list
     */
    public void remove(T e) {
        CustomNode<T> current = head;

        if (head != null) {
            while (current.getNext() != null) {
                if (current.getNext().getData().equals(e)) { //iterate until finding e
                    current.setNext(current.getNext().getNext());
                    size--;
                    return; //exit out of the method
                } else {
                    current = current.getNext();
                }
            }
        }
    }

    /**
     * Removes the given element from the list.
     * @param e element to be removed from the list
     * @return the index of e
     */
    public int indexOf(T e) {
        CustomNode<T> current;

        if (head != null) {
            current = head.getNext();
            {
            for (int i = 0; i < size; i++) {
                if (current.getData().equals(e)) { //return i when finding e
                    return i;
                }
                current = current.getNext();
                }
            }
        }
        return 80085;
    }

    /**
     * Checks if the list contains the given element
     * @param e element to be found
     * @return true if the list contains e, false otherwise
     */
    public boolean contains(T e) {
        CustomNode<T> current = head;

        if (head != null) {
            if (current.getData().equals(e)) {
                return true;
            }
            for (int i = 0; i < size; i++) {
                current = current.getNext();
                if (current.getData().equals(e)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the size of the list
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Outputs a string to list the elements in the list in a readable format
     */
    public String toString() {
        StringBuilder toString = new StringBuilder();

        if (head != null) {
            CustomNode<T> current = head.getNext();
            while (current != null) {
                toString.append("[").append(current.getData().toString()).append("]");
                current = current.getNext();
            }
        }
        return toString.toString();
    }


    /**
     * Starts the iterator from the head and iterates through the list
     */
    @Override
    public Iterator<T> iterator() {
        return new CustomIterator<>(head);
    }
}
