//-----------------------------------------------------
// Title: My stack class

// Author: umut uygur
// ID: 13474078970
// Section: 1
// Assignment: 1
// Description: this class include stack operation
//-----------------------------------------------------

class Stack<T> {            // Stack Implementation using Doubly Linked List
    private DoublyLinkedList<T> list = new DoublyLinkedList<>();        //LİFO

    public void push(T data) {
        list.addFirst(data);
    }         //ekleme

    public T pop() {
        return list.removeFirst();
    }           //sonuncuyu pop la çıkar

    public T peek() {
        if (list.isEmpty()) return null;
        Node<T> head = list.head;  // direct access for peek
        return head != null ? head.data : null;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }       //boşsa bana dön
}
