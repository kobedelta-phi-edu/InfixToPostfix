package midlab1.datastructure;

/**
 * thrown when an error occurred in the queue
 */
class StackException extends RuntimeException {
    public StackException(){
        super();
    }
    public StackException(String err) {
        super(err);
    }
}