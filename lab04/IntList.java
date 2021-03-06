/**
 * A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 * <p>
 * This is a dummy implementation to allow IntListTest to compile. Replace this
 * file with your own IntList class.
 */
public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /**
     * Returns an IntList consisting of the given values.
     */
    public static IntList of(int... values) {
        if (values.length == 0) {
            return null;
        }
        IntList p = new IntList(values[0], null);
        IntList front = p;
        for (int i = 1; i < values.length; i += 1) {
            p.rest = new IntList(values[i], null);
            p = p.rest;
        }
        return front;
    }

    /**
     * Returns the size of the list.
     */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + rest.size();
    }

    /**
     * Returns [position]th value in this list.
     */
    public int get(int position) {
        if (position == 0) {
            return first;
        } else {
            return rest.get(position - 1);
        }
    }

    public String toString() {
        if (this.rest == null) {
            return (String.valueOf(this.first));
        } else {
            return (String.valueOf(this.first) + " " + this.rest.toString());
        }
    }

    /**
     * Returns whether this and the given list or object are equal.
     */
    public boolean equals(Object o) {
        IntList other = (IntList) o;
        if (this.first != other.first) {
            return false;
        } else if (this.rest != null && other.rest != null) {
            return (this.rest.equals(other.rest));
        } else if (this.rest == null && other.rest == null) {
            return true;
        } else {
            return false;
        }

    }

    public void add(int value) {
        if (this.rest == null) {
            IntList end = new IntList(value, null);
            this.rest = end;
        } else {
            this.rest.add(value);
        }
    }

    public int smallest() {
        if (this.rest == null) {
            return this.first;
        } else {
            int smallFirst = this.first;
            int smallRest = this.rest.smallest();
            if (smallFirst < smallRest) {
                return smallFirst;
            } else {
                return smallRest;
            }

        }
    }

    public int squaredSum() {
        if (this.rest == null) {
            return this.first * this.first;
        } else {
            return (this.first * this.first + this.rest.squaredSum());
        }
    }

    public static void dSquareList(IntList L) {
        while (L != null) {
            L.first = L.first * L.first;
            L = L.rest;
        }
    }

    public static IntList catenate(IntList A, IntList B) {
        if (B == null) {
            return A;
        } else if (A == null) {
            return B;
        } else {
            if (A.rest == null) {

                return new IntList(A.first, B);
            } else {
                return new IntList(A.first, catenate(A.rest, B));
            }
        }
    }


    public static IntList dcatenate(IntList A, IntList B) {
        if (B == null) {
            return A;
        } else if (A == null) {
            return B;
        } else {
            if (A.rest == null) {
                A.rest = B;
                return A;
            } else {
                A.rest = dcatenate(A.rest, B);
                return A;
            }
        }
    }
}
