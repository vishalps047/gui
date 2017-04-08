/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author DELL
 */
public class QDisplay extends JPanel {
    public Queue q;
    private JPanel panel[];
    private JLabel elts[];
    private JLabel front;
    private JPanel[] frontPanel;
    private JLabel rear;
    private JPanel[] rearPanel;
    public TextInterface textSetter;
    public static Boolean q_isRandom;
    
    QDisplay() {
        super();
        super.setLayout(new BorderLayout());

        q = new Queue(15);

        elts = new JLabel[q.size + 2];
        panel = new JPanel[q.size + 2];
        front = new JLabel("Front");
        rear = new JLabel("Rear");
        front.setFont(new Font("Serif", Font.BOLD, 12));
        frontPanel = new JPanel[q.size + 2];
        rearPanel = new JPanel[q.size+2];
        Box eltBox, panelBox[];
        eltBox = Box.createHorizontalBox();
        panelBox = new Box[q.size + 2];

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border empty = BorderFactory.createEmptyBorder(5, 5, 0, 5);
        Border cmpnd = BorderFactory.createCompoundBorder(border, empty);
        
        elts[0] = new JLabel("");
        
        panel[0] = new JPanel();
        panel[0].setPreferredSize(new Dimension(55, 45));
        
        frontPanel[0] = new JPanel();
        frontPanel[0].setPreferredSize(new Dimension(55, 30));
        
        rearPanel[0]=new JPanel();
        rearPanel[0].setPreferredSize(new Dimension(55, 20));
        
        rearPanel[0].add(rear);
        
        panelBox[0] = Box.createVerticalBox();
        panelBox[0].add(frontPanel[0]);
        panelBox[0].add(panel[0]);
        panelBox[0].add(rearPanel[0]);
        
        eltBox.add(panelBox[0]);

        for (int i = 1; i <= q.size; i++) {
            elts[i] = new JLabel("");
            
            frontPanel[i] = new JPanel();
            frontPanel[i].setPreferredSize(new Dimension(55, 30));
            
            panel[i] = new JPanel();
            panel[i].setBorder(cmpnd);
            panel[i].setPreferredSize(new Dimension(55, 45));
            panel[i].add(elts[i]);
            
            rearPanel[i]=new JPanel();
            rearPanel[i].setPreferredSize(new Dimension(55, 20));
            
            panelBox[i] = Box.createVerticalBox();
            panelBox[i].add(frontPanel[i]);
            panelBox[i].add(panel[i]);
            panelBox[i].add(rearPanel[i]);
            
            eltBox.add(panelBox[i]);
        }
        
        frontPanel[q.size + 1] = new JPanel();
        frontPanel[q.size + 1].setPreferredSize(new Dimension(55, 30));
        rearPanel[q.size + 1] = new JPanel();
        rearPanel[q.size + 1].setPreferredSize(new Dimension(55, 20));
        elts[q.size + 1] = new JLabel("");
        panel[q.size + 1] = new JPanel();
        panel[q.size + 1].add(elts[q.size + 1]);
        panel[q.size + 1].setPreferredSize(new Dimension(55, 45));
        panelBox[q.size + 1] = Box.createVerticalBox();
        panelBox[q.size + 1].add(frontPanel[q.size + 1]);
        panelBox[q.size + 1].add(panel[q.size + 1]);
        panelBox[q.size + 1].add(rearPanel[q.size + 1]);
        eltBox.add(panelBox[q.size + 1]);
        
        frontPanel[1].add(front);
        
        super.add(eltBox, BorderLayout.CENTER);
    }
    
    public void update_insert(int num,Stack undo) {
        if(q.front == q.size){
            textSetter.setText("Cannot perform any more operations on the queue. Please try using the undo or the reset buttons to continue. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
            return;
        }
        if (q.rear == q.size - 1) {
            textSetter.setText("The queue is full! Cannot insert more elements. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
            return;
        }
        if (undo.top == undo.size - 1) {
            undo.forgetEarlierChoice();
        }
        undo.push(0);
        rearPanel[q.rear + 1].remove(rear);
        q.insert(num);
        rearPanel[q.rear + 1].add(rear);
        elts[q.rear + 1].setText(Integer.toString(num));
        textSetter.setText(Integer.toString(num)+" has been inserted to the queue. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
    }
    
    public void update_delete(Stack undo){
        if(q.front == q.size){
            textSetter.setText("Cannot perform any more operations on the queue. Please try using the undo or the reset buttons to continue. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
            return;
        }
        if(q.front>q.rear){
            textSetter.setText("The queue is empty! Cannot remove elements. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
            return;
        }
        if (undo.top == undo.size - 1) {
            undo.forgetEarlierChoice();
        }
        undo.push(1);
        frontPanel[q.front + 1].remove(front);
        elts[q.front + 1].setText("");
        int num=q.del();
        frontPanel[q.front + 1].add(front);
        textSetter.setText(Integer.toString(num)+" has been removed from the queue. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
    }
    
    public void undoInsert(){
        elts[q.rear + 1].setText("");
        rearPanel[q.rear + 1].remove(rear);
        q.rear--;
        rearPanel[q.rear + 1].add(rear);
        textSetter.setText("Undo successful. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
    }
    
    public void undoDelete(){
        frontPanel[q.front + 1].remove(front);
        q.front--;
        frontPanel[q.front + 1].add(front);
        elts[q.front + 1].setText(Integer.toString(q.getVal(q.front)));
        textSetter.setText("Undo successful. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
    }
    
    public void random(Stack undo){
        int choice;
        Random getRandom=new Random();
        if(q.rear==q.size - 1) choice=1;
        else if(q.front > q.rear) choice=0;
        else{
            int r=getRandom.nextInt(10+this.q.nElts);
            if(r<5) choice=0;
            else choice=1; 
        }
        if(choice==1) update_delete(undo);
        else update_insert(getRandom.nextInt(100),undo);
    }
    
    public void reset(){
        frontPanel[q.front + 1].remove(front);
        rearPanel[q.rear + 1].remove(rear);
        q.front=0;
        q.rear=-1;
        for(int i=1;i<=q.size;i++){
            elts[i].setText("");
        }
        frontPanel[1].add(front);
        rearPanel[0].add(rear);
        textSetter.setText("Queue has been reset. Front = "+Integer.toString(q.front)+" Rear = "+Integer.toString(q.rear));
    }
}
