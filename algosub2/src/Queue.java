//-----------------------------------------------------
// Title: My queue class

// Author: umut uygur
// ID: 13474078970
// Section: 1
// Assignment: 1
// Description: this class include queue operation
//-----------------------------------------------------
class Queue<T> {            // Queue Implementation using Doubly Linked List
    private DoublyLinkedList<T> list = new DoublyLinkedList<>();        //FİFO

    public void enqueue(T data) {           //engueue işlemini yap listenin sonuna ekle push
        list.addLast(data);
    }

    public T dequeue() {                    //dequeue işlemini yap listenin ilk elemanını sil ve dön pop
        return list.removeFirst();
    }

    public T peek() {
        if (list.isEmpty()) return null;    //liste boşsa null dön
        Node<T> head = list.head;           //listenin baş elemanını direkt olarak ulaş
        if (head != null) {
            return head.data;               //liste boş değilse head datayı dön
        } else {
            return null;                    //error fix
        }
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}

