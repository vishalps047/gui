/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author DELL
 */
public class StackElement extends JPanel{
    
    public int val;
    public JLabel elt;
    public JPanel eltPanel;
    public JPanel topPanel;
    
    public StackElement(){
        super.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        Box row = Box.createHorizontalBox();
        
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border cmpnd = BorderFactory.createCompoundBorder(border, empty);
                
        elt = new JLabel("");
        eltPanel = new JPanel();
        eltPanel.setBorder(cmpnd);
        eltPanel.setPreferredSize(new Dimension(55, 45));
        eltPanel.add(elt);
        
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(40, 30));
        
        row.add(topPanel);
        row.add(eltPanel);
        super.add(row);
    }
    
    public StackElement(int num){
        
        val = num;
        
        super.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        Box row = Box.createHorizontalBox();
        
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border cmpnd = BorderFactory.createCompoundBorder(border, empty);
                
        elt = new JLabel(Integer.toString(num));
        eltPanel = new JPanel();
        eltPanel.setBorder(cmpnd);
        eltPanel.setPreferredSize(new Dimension(55, 45));
        eltPanel.add(elt);
        
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(40, 30));
        
        row.add(topPanel);
        row.add(eltPanel);
        super.add(row);
    }
    
}
