package workwithstrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReverseArrayList<T> extends ArrayList<T> {


    public ReverseArrayList(List<T> strings) {
        super(strings);
    }

    @Override
    public Iterator<T> iterator() {
        return new ReverseIterator(super.listIterator(this.size()));
    }

    private class ReverseIterator implements Iterator<T> {
        ListIterator<T> itr;

        public ReverseIterator(ListIterator<T> itr) {
            this.itr = itr;
        }

        @Override
        public boolean hasNext() {
            return itr.hasPrevious();
        }

        @Override
        public T next() {
            return itr.previous();
        }

        @Override
        public void remove() {
            itr.remove();
        }
    }
}
