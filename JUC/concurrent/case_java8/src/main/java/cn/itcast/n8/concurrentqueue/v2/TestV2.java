package cn.itcast.n8.concurrentqueue.v2;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j(topic = "c.TestV2")
public class TestV2 {

    public static void main(String[] args) throws InterruptedException {
        MyQueue<String> queue = new MyQueue<>();
        new Thread(() -> {
            queue.offer("1");
        },"t1").start();
        new Thread(() -> {
            queue.offer("2");
        },"t2").start();
        Thread.sleep(1000);
        System.out.println(queue);
    }
}
@Slf4j(topic = "c.MyQueue")
class MyQueue<E> implements Queue<E> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }


    @Override
    public E remove() {
        return null;
    }


    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    public MyQueue() {
        Node<E> initNode = new Node<E>(
                new AtomicReference<E>(null), new AtomicReference<Node<E>>(null));
        dummy = new AtomicReference<>(initNode);
        tail = new AtomicReference<>(initNode);
    }

    private AtomicReference<Node<E>> dummy;
    private AtomicReference<Node<E>> tail;

    private E dequeue() {
        /*Node<E> h = head;
        Node<E> first = h.next;
        h.next = h;
        head = first;
        E x = first.item;
        first.item = null;
        return x;*/
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public boolean offer(E e) {
        Node<E> newNode = new Node<E>(new AtomicReference<>(e), new AtomicReference<>(null));
        while (true) {
            Node<E> curTail = tail.get();
            AtomicReference<Node<E>> curNext = curTail.next;
            if (curNext.compareAndSet(null, newNode)) {
                log.debug("cas {}->{}:{}",curTail.item,newNode.item, tail.compareAndSet(curTail, newNode));
                return true;
            }
            log.debug("cas {}->{}:{}",curTail.item,newNode.item, tail.compareAndSet(curTail, newNode));
        }
    }

    private static class Node<E> {
        private AtomicReference<E> item;
        private AtomicReference<Node<E>> next;

        public Node(AtomicReference<E> item, AtomicReference<Node<E>> next) {
            this.item = item;
            this.next = next;
        }
    }
}