public class Stack {
    
    private int top;
    private StackObj[] stack;

    public Stack(int size) {
        this.stack = new StackObj[size];
        this.top = 0;
    }

    public boolean isEmpty() {
        if (this.top == 0 && this.stack[this.top] == null) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        if (this.isEmpty()) {
            return 0;
        } else {
            return this.top+1;
        }
    }

    public StackObj pop() {
        StackObj toReturn = this.stack[this.top];
        this.stack[this.top] = null;
        if (this.top != 0) this.top--;
        return toReturn;
    }

    public StackObj peek() {
        return this.stack[this.top];
    }

    public void push(StackObj newObj) throws ArrayStoreException {
        if (this.top == this.stack.length-1) {
            throw new ArrayStoreException("Stack is full.");
        } else {
            if (!this.isEmpty()) this.top++; 
            this.stack[this.top] = newObj;
        }
    }

}
