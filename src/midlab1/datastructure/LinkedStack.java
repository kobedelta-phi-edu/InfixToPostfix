package midlab1.datastructure;

public class LinkedStack<T> implements Stack<T> {
    private Node<T> top;
    private int numElements = 0;

    /**
     * @return number of elements in the stack
     */
    @Override
    public int size() {
        return (numElements);
    }

    /**
     * @return true if the stack is empty
     */
    @Override
    public boolean isEmpty() {
        return (top == null);
    }

    /**
     * @return data of the top node
     * @throws StackException thrown if the stack is empty
     */
    @Override
    public T top() throws StackException {
        if (isEmpty())
            throw new StackException("Stack is empty");
        return top.getInfo();
    }

    /**
     * removes the top data from the stack
     * @return data of the new top node
     * @throws StackException thrown if the stack is empty
     */
    @Override
    public T pop() throws StackException {
        Node<T> temp;
        if (isEmpty())
            throw new StackException("Stack underflow");
        temp = top;
        top = top.getLink();
        numElements--;
        return temp.getInfo();
    }

    /**
     * push the data into the stack
     * @param item data to be pushed
     * @throws StackException thrown if the stack is empty
     */
    @Override
    public void push(T item) throws StackException {
        Node<T> newNode = new Node<>();
        newNode.setInfo(item);
        newNode.setLink(top);
        top = newNode;
        numElements++;
    }

    /**
     * looks at the top of the stack
     * @return data of the node on top of the stack
     * @throws StackException thrown if the stack is empty
     */
    @Override
    public T peek() throws StackException {
        if (isEmpty()) throw new StackException("The stack is empty");
        else
            return top.getInfo();
    }

    /**
     * clear the stacked data
     */
    @Override
    public void clear() {
        if (isEmpty()) throw new StackException("The stack is empty");
        Node<T> current = top;
        while (top != null) {
            Node<T> previous = current;
            current = current.getLink();
            previous.setInfo(null);
            previous.setLink(null);
        }
        numElements = 0;
    }

    /**
     * @param item data to be searched
     * @return index
     */
    @Override
    public int search(T item) {
        int index = 0;
        Node<T> current = top;
        while (current != null) {
            if (current.getInfo() == item) {
                return index;
            }
            current = current.getLink();
        }
        return -1;
    }

    /**
     * @return stack in String
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        Node<T> current = top;
        while (current != null) {
            if(!(current.getLink()==null))
                output.append(current.getInfo()).append(", ");
            else output.append(current.getInfo());
            current = current.getLink();
        }
        return output.toString();
    }
}