/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class StackDisplay extends JPanel {

    public Stack stack;
    private JLabel topLabel;
    public TextInterface textSetter;
    public Stack popStack;
    public StackElement[] rowElement;
    public ArrayList dynamicStack;
    public Box columnBox;
    
    StackDisplay(boolean isDynamic) {
        super();
        super.setLayout(new BorderLayout());

        popStack = new Stack(3); //stack used to store recently popped elements, for undo
      
        topLabel = new JLabel("TOP-->"); //Label to point to top element in gui
        topLabel.setFont(new Font("Serif", Font.BOLD, 12)); 
         
        columnBox = Box.createVerticalBox(); //columnBox to hold all rows
        if(!isDynamic)
            columnBox.add(Box.createRigidArea(new Dimension(0,30)));
        else
            columnBox.add(Box.createRigidArea(new Dimension(0,60)));
        
        if (!isDynamic) {
            stack = new Stack(8); //Object of type Stack class used to simulate the stack
            rowElement = new StackElement[stack.size + 1];

            for (int i = 0; i < stack.size; i++) {
                rowElement[i] = new StackElement();
                columnBox.add(rowElement[i]);
            }
            rowElement[stack.size] = new StackElement();
            rowElement[stack.size].topPanel.add(topLabel);
            rowElement[stack.size].eltPanel.setBorder(null);
            columnBox.add(rowElement[stack.size]);
        }
        else{
            dynamicStack = new ArrayList();
            StackElement elt = new StackElement();
            elt.topPanel.add(topLabel);
            elt.topPanel.setVisible(true);
            elt.eltPanel.setBorder(null);
            dynamicStack.add(elt);
            columnBox.add((StackElement)dynamicStack.get(0));
        }
               
        super.add(columnBox, BorderLayout.CENTER);
    }

    //Function to update GUI when push button is pressed
    public void update_push(int num, Stack undo,int stackNumber,boolean isDynamic) {
        
        if (undo.nElts == undo.size) {
            undo.forgetEarlierChoice(); //Deleting earlier options - push or pop
        }
        
        undo.push(0); // 0 for push
        if (!isDynamic) {
            //Check if stack is full
            if (stack.nElts == stack.size) {
                //Stack full message
                textSetter.setText("Stack " + Integer.toString(stackNumber + 1)
                        + " is full! Cannot push more elements. Top = " + Integer.toString(stack.top));
                return;
            }

            rowElement[stack.size - stack.nElts].topPanel.remove(topLabel); //Remove topLabel from current position
            stack.push(num); //Push new element in actual stack
            rowElement[stack.size - stack.nElts].topPanel.add(topLabel); //topLabel points to new top
            rowElement[stack.size - stack.nElts].elt.setText(Integer.toString(num)); //Display new element
            //Display message of successful push
            textSetter.setText(Integer.toString(num) + " has been pushed on to Stack " + Integer.toString(stackNumber + 1)
                    + ". Top = " + Integer.toString(stack.top));
        }
        else{
            StackElement elt = new StackElement(num);
            ((StackElement)dynamicStack.get(dynamicStack.size()-1)).topPanel.remove(topLabel);
            elt.topPanel.add(topLabel);
            dynamicStack.add(elt);
            columnBox.add((StackElement)dynamicStack.get(dynamicStack.size()-1),1);
            
            textSetter.setText(Integer.toString(num) + " has been pushed on to Stack " + Integer.toString(stackNumber + 1)
                    + ". Top = " + Integer.toString(dynamicStack.size()-2));
        }
    }

    public void update_pop(Stack undo,int stackNumber,boolean isDynamic) {
        
        //Check if stack is empty
        if (!isDynamic && stack.top == -1) {
            //Stack empty message
            textSetter.setText("Stack "+ Integer.toString(stackNumber + 1) +
                    " is empty! Cannot pop any element. Top = " + Integer.toString(stack.top));
            return;
        }
        if(isDynamic && dynamicStack.size() == 1){
            textSetter.setText("Stack "+ Integer.toString(stackNumber + 1) +
                    " is empty! Cannot pop any element. Top = " + Integer.toString(dynamicStack.size()-2));
            return;
        }
        
        if (undo.nElts == undo.size) {
            undo.forgetEarlierChoice(); //Deleting earlier options - push or pop
        }
        if (popStack.nElts == popStack.size) {
            popStack.forgetEarlierChoice(); //Deleting elements popped earlier from temporary stack
        }
        
        undo.push(1);//1 for pop
       
        if (!isDynamic) {
            rowElement[stack.size - stack.top - 1].elt.setText(""); //Remove top element from display
            rowElement[stack.size - stack.nElts].topPanel.remove(topLabel); //Remove topLabel from current position
            int num = stack.pop(); //Popping element from actual stack
            popStack.push(num); //Storing recently popped element for undo purpose
            rowElement[stack.size - stack.nElts].topPanel.add(topLabel); //Updating topLabel to point to new top
            //Display message of successful pop
            textSetter.setText(Integer.toString(num) + " has been popped from Stack " + Integer.toString(stackNumber + 1)
                    + ". Top = " + Integer.toString(stack.top));
        }
        else{
            StackElement elt = (StackElement)dynamicStack.get(dynamicStack.size()-1);
            elt.topPanel.remove(topLabel);
            popStack.push(elt.val);
            dynamicStack.remove(dynamicStack.size()-1);
            columnBox.remove(1);
            
            textSetter.setText(Integer.toString(elt.val) + " has been popped from Stack " + Integer.toString(stackNumber + 1)
                    + ". Top = " + Integer.toString(dynamicStack.size()-2));
            
            elt = (StackElement)dynamicStack.get(dynamicStack.size()-1);
            elt.topPanel.add(topLabel);
            
        }
    }

    public void undoPush(int stackNumber,boolean isDynamic) {
        if (!isDynamic) {
            rowElement[stack.size - stack.top - 1].elt.setText(""); //Remove recently pushed element
            rowElement[stack.size - stack.nElts].topPanel.remove(topLabel); //Remove topLabel from current top
            stack.pop(); //Remove element from actual stack
            rowElement[stack.size - stack.nElts].topPanel.add(topLabel); //topLabel points to new top
            //Display message of successful undo
            textSetter.setText("Undo on Stack " + Integer.toString(stackNumber + 1)
                    + " successful. Top = " + Integer.toString(stack.top));
        }
        else{
            StackElement elt = (StackElement)dynamicStack.get(dynamicStack.size()-1);
            elt.topPanel.remove(topLabel);
            dynamicStack.remove(dynamicStack.size()-1);
            columnBox.remove(1);
            
            textSetter.setText("Undo on Stack " + Integer.toString(stackNumber + 1)
                    + " successful. Top = " + Integer.toString(dynamicStack.size()-2));
            
            elt = (StackElement)dynamicStack.get(dynamicStack.size()-1);
            elt.topPanel.add(topLabel);
        }
    }

    public void undoPop(int stackNumber,boolean isDynamic) {
        if (!isDynamic) {
            rowElement[stack.size - stack.nElts].topPanel.remove(topLabel); //Remove topLabel from current top
            stack.push(popStack.pop()); //Push back recently popped element
            rowElement[stack.size - stack.nElts].topPanel.add(topLabel); //topLabel points to new top
            //Display the element again
            rowElement[stack.size - stack.top - 1].elt.setText(Integer.toString(stack.getVal(stack.top)));
            //Display message of successful undo
            textSetter.setText("Undo on Stack " + Integer.toString(stackNumber + 1)
                    + " successful. Top = " + Integer.toString(stack.top));
        }
        else{
            StackElement elt = new StackElement(popStack.pop());
            ((StackElement)dynamicStack.get(dynamicStack.size()-1)).topPanel.remove(topLabel);
            elt.topPanel.add(topLabel);
            dynamicStack.add(elt);
            columnBox.add((StackElement)dynamicStack.get(dynamicStack.size()-1),1);
            
            textSetter.setText("Undo on Stack " + Integer.toString(stackNumber + 1)
                    + " successful. Top = " + Integer.toString(dynamicStack.size()-2));
        }
    }

    public void random(Stack undo,int stackNumber,boolean isDynamic) throws InterruptedException {
        int choice;
        Random getRandom = new Random(); //object which gets random numbers
        if (!isDynamic) {
            if (stack.nElts == stack.size) {
                choice = 1; //if stack full, then pop
            } else if (stack.nElts == 0) {
                choice = 0; //if stack empty, then push
            } else {
                int r = getRandom.nextInt(4 + this.stack.nElts); //random number
                if (r > this.stack.nElts) {
                    choice = 0; //random operation is push
                } else {
                    choice = 1; //random operation is pop
                }
            }
        }
        else{
            if(dynamicStack.size() == 1)
                choice = 0;
            else{
                int r = getRandom.nextInt(4 + dynamicStack.size()); //random number
                if(r > dynamicStack.size())
                    choice = 0;
                else
                    choice = 1;
            }
        }
        
        if (choice == 1) {
            update_pop(undo,stackNumber,isDynamic);
        } else {
            update_push(getRandom.nextInt(100), undo,stackNumber,isDynamic);
        }
    }

    public void reset(int stackNumber,boolean isDynamic) {
        
        if (!isDynamic) {
            rowElement[stack.size - stack.nElts].topPanel.remove(topLabel); //remove topLabel from current position

            //Clear actual stack
            stack.nElts = 0;
            stack.top = -1;

            for (int i = 0; i < stack.size; i++) {
                rowElement[i].elt.setText(""); //Remove all elements from display
            }

            rowElement[stack.size - stack.nElts].topPanel.add(topLabel); //topLabel points to -1
            //Display reset message
            textSetter.setText("Stack " + Integer.toString(stackNumber + 1)
                    + " has been reset. Top = " + Integer.toString(stack.top));
        }
        else{
            ((StackElement)dynamicStack.get(dynamicStack.size()-1)).topPanel.remove(topLabel);
            dynamicStack.clear();
            
            StackElement elt = new StackElement();
            elt.topPanel.add(topLabel);
            elt.eltPanel.setBorder(null);
            dynamicStack.add(elt);
            columnBox.add((StackElement)dynamicStack.get(0));
            
            columnBox.removeAll();
            columnBox.add(Box.createRigidArea(new Dimension(0,60)));
            
            textSetter.setText("Stack " + Integer.toString(stackNumber + 1)
                    + " has been reset. Top = " + Integer.toString(dynamicStack.size()-2));
        }
    }
}
