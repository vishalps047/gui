/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx;

/**
 *
 * @author DELL
 */
public class Stack {

    public int top;
    private int arr[];
    public int size;
    public int nElts;

    public Stack(int sz) {
        top = -1;
        size = sz;
        arr = new int[size];
        nElts = 0;
    }

    public void push(int elt) {
        arr[++top] = elt;
        nElts++;
    }

    public int pop() {
        nElts--;
        return arr[top--];
    }

    public int getVal(int i) {
        return arr[i];
    }

    //For undo option in Stack and Queue, to remove earlier choices when number of operations > 3
    public void forgetEarlierChoice() {
        for (int i = 0; i < this.top; i++) {
            this.arr[i] = this.arr[i + 1];
        }
        this.top--;
        this.nElts--;
    }
};
