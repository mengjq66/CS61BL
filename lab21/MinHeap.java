import java.util.ArrayList;
//import java.util.Collections;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
//        if (!(index * 2 > size)) {
        return index * 2;
//        }
//        return 0;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
//        if (!(index * 2 + 1 > size)) {
        return index * 2 + 1;
//        }
//        return 0;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. */
    private int min(int index1, int index2) {
        if (getElement(index1) == null) {
            return index2;
        }
        if (getElement(index2) == null) {
            return index1;
        }
        if (getElement(index1).compareTo(getElement(index2)) < 0) {
            return index1;
        }
        return index2;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E peek() {
        return contents.get(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
//        if (index != 1) {
        int i = getParentOf(index);
        if (getElement(i) != null && min(index, i) == index) {
            //Collections.swap(contents, index, i);
            swap(index, i);
            bubbleUp(i);
//            }
        }
    }

    /* Bubbles down the element currently at index
    INDEX. */
    private void bubbleDown(int index) {
        int l = getLeftOf(index);
        int r = getRightOf(index);
        int lorr = min(l, r);

        while ((getElement(lorr)) != null && (getElement(index).compareTo(getElement(lorr))) > 0) {
            swap(index, lorr);
//                index = lorr;
//                lorr = min(getLeftOf(index), getRightOf(index));
            bubbleDown(lorr);
        }

//
//        if (index * 2 + 1 <= size && index * 2 <= size) {
//            int l = getLeftOf(index);
//            int r = getRightOf(index);
//            int lorr = min(l, r);
//            if (index < lorr) {
//                swap(index, lorr);
//                bubbleDown(lorr);
//            }
//        }
//        if (index * 2 <= size) {
//            int l = getLeftOf(index);
//            int lori = min(l, index);
//            if (l < lori) {
//                swap(index, lori);
//                bubbleDown(lori);
//            }
//        }
//        if (index * 2 + 1 <= size) {
//            int r = getLeftOf(index);
//            int rori = min(r, index);
//            if (r < rori) {
//                swap(index, rori);
//                bubbleDown(rori);
//            }
//        }

    }

    /* Inserts element into the MinHeap. */
    public void insert(E element) {
        contents.add(element);
        size++;
        bubbleUp(size);
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Returns the smallest element. */
    public E removeMin() {
        E ele = peek();
        swap(1, size);
        contents.remove(size);
        size--;
        bubbleDown(1);
        return ele;
    }

    /* Updates the position of ELEMENT inside the MinHeap, which may have been
       mutated since the inital insert. If a copy of ELEMENT does not exist in
       the MinHeap, do nothing.*/
    public void update(E element) {
        for (E e : contents) {
            if (e != null && e.equals(element)) {
                int i = contents.indexOf(e);
                setElement(i, element);
                bubbleDown(i);
                bubbleUp(i);
            }
        }

    }

    public static void main(String[] args) {
        MinHeap m = new MinHeap<Integer>();
        m.insert(5);
        m.insert(1);
        m.insert(2);
        m.insert(4);
        m.insert(3);
    }

}
