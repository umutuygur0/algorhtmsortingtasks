//-----------------------------------------------------
// Title: My node class

// Author: umut uygur
// ID: 13474078970
// Section: 1
// Assignment: 1
// Description: this class include double node and double linkedlist
//-----------------------------------------------------

class Node<T> {                     // Doubly Linked List Node
    T data;
    Node<T> next;
    Node<T> prev;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

// Doubly Linked List
class DoublyLinkedList<T> {
    public Node<T> head;
    public Node<T> tail;

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {            //eğer liste boşsa head ve tail aynı
            head = tail = newNode;
        } else {
            newNode.next = head;       //önce newnodu head e bağla
            head.prev = newNode;       //headin previni nnewnoda bağla
            head = newNode;            //head i güncelle
        }
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {             //eğer liste boşsa head ve tail aynı
            head = tail = newNode;
        } else {
            newNode.prev = tail;        //yeni notun previni tail yap
            tail.next = newNode;        //tailin sonraki nodu new node
            tail = newNode;             //tail i güncelle
        }
    }

    public T removeFirst() {
        if (head == null) return null;  //liste boşsa null dön error fix
        T data = head.data;             //neyi sildiğimi bilmek .takip için silineni data olarak tut
        head = head.next;               //yeni headi tanımla
        if (head != null) {             //head previni sil ilk node
            head.prev = null;
        } else {                        //tail'ide güncelle head null ise boşsa
            tail = null;
        }
        return data;
    }

    public T removeLast() {
        if (tail == null) return null;  //liste boşsa null dön error fix
        T data = tail.data;             //neyi sildiğimi bilmek. takip için silineni data olarak tut
        tail = tail.prev;               //yeni taili tanımla
        if (tail != null) {             //tailin nexini sil en son node
            tail.next = null;
        } else {                        //head'ide güncelle tail null ise boşsa
            head = null;
        }
        return data;
    }

    public boolean isEmpty() {
        return head == null;
    }   //boşsa bana bool olarak dön
}

