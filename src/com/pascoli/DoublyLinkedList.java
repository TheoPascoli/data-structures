package com.pascoli;

import java.util.Iterator;
import java.util.StringJoiner;

public class DoublyLinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    // O(n)
    public void clear() {
        Node<T> trav = head;
        while (trav != null) {
            Node<T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next;
        }
        head = tail = trav = null;
        size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // O(1)
    public void add(T elem) {
        addLast(elem);
    }

    // O(1)
    public void addFirst(T elem) {
        if (isEmpty()) {
            head = tail = new Node<>(elem, null, null);
        } else {
            head.prev = new Node<>(elem, null, head);
            head = head.prev;
        }
        size++;
    }

    // O(1)
    public void addLast(T elem) {
        if (isEmpty()) {
            head = tail = new Node<>(elem, null, null);
        } else {
            tail.next = new Node(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }

    // O(1)
    public T peekFirst() {
        if (isEmpty()) throw new RuntimeException("Empty List");
        return head.data;
    }

    // O(1)
    public T peekLast() {
        if (isEmpty()) throw new RuntimeException("Empty List");
        return tail.data;
    }

    // O(1)
    public T removeFirst() {
        if (isEmpty()) throw new RuntimeException("Empty List");
        T data = head.data;
        head = head.next;
        size--;

        if (isEmpty()) {
            tail = null;
        } else {
            head.prev = null;
        }

        return data;
    }

    // O(1)
    public T removeLast() {
        if (isEmpty()) throw new RuntimeException("Empty List");
        T data = tail.data;
        tail = tail.prev;
        size--;

        if (isEmpty()) {
            head = null;
        } else {
            tail.next = null;
        }

        return data;
    }

    // O(1)
    private T remove(Node<T> node) {
        if (node.prev == null) removeFirst();
        if (node.next == null) removeLast();

        node.next.prev = node.prev;
        node.prev.next = node.next;

        T data = node.data;

        node.data = null;
        node = node.prev = node.next = null;

        size--;

        return data;
    }

    // O(n)
    public T removeAt(int index) {
        if (index < 0 || index >= size) throw new IllegalArgumentException();

        Node<T> trav;

        if (index < size / 2) {
            trav = head;
            for (int i = 0; i != index; i++) {
                trav = trav.next;
            }
        } else {
            trav = tail;
            for (int i = size - 1; i != index; i--) {
                trav = trav.prev;
            }
        }

        return remove(trav);
    }

    // O(n)
    public boolean remove(Object obj) {
        Node<T> trav = head;

        if (obj == null) {
            for (trav = head; trav != null; trav = trav.next) {
                if (trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        } else {
            for (trav = head; trav != null; trav = trav.next) {
                if (trav.data.equals(obj)) {
                    remove(trav);
                    return true;
                }
            }
        }

        return false;
    }

    // O(n)
    public int indexOf(Object obj) {
        int index = 0;
        Node<T> trav = head;

        if (obj == null) {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data == null) {
                    return index;
                }
            }
        } else {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data.equals(obj)) {
                    return index;
                }
            }
        }

        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> trav = head;
            @Override
            public boolean hasNext() {
                return trav.next != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        Node<T> trav = head;
        while(trav.next != null) {
            sj.add(trav.data.toString());
            trav = trav.next;
        }
        return sj.toString();
    }
}
