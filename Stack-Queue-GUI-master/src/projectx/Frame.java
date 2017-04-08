/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author DELL
 */
public class Frame extends JFrame {

    StackFrame stackFrame;
    StackSetup stackSetup;
    
    QueueFrame queueFrame;

    Frame() {
        super("ProjectX");
        super.getContentPane().setBackground(Color.BLACK);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JTabbedPane tab = new JTabbedPane(); //For the tabs at the top
        
        JScrollPane scrollPane = new JScrollPane();
               
        stackSetup = new StackSetup();
        scrollPane.setViewportView(stackSetup);
        stackSetup.setPreferredSize(tab.getSize());
        
        tab.addTab("Stack", scrollPane);
        tab.setMnemonicAt(0, KeyEvent.VK_1);
        
        stackSetup.createStackButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {              
                stackSetup.isDynamicStack = stackSetup.dynamicStackYes.isSelected();
                stackSetup.numberOfStacks = stackSetup.stackNumberComboBox.getSelectedIndex() + 1;
                stackFrame = new StackFrame(stackSetup.numberOfStacks,stackSetup.isDynamicStack);
                scrollPane.setViewportView(stackFrame);
                tab.setComponentAt(0,scrollPane);
                
                stackFrame.stackMenu.settingsBtn.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent ae) {
                        int response = JOptionPane.showConfirmDialog(null, "Are you sure? "+
                                "You will lose all the data in the stack(s). Press Yes to continue. "+
                                "Press No to cancel.", "Confirm",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION){
                            stackSetup.reset();
                            scrollPane.setViewportView(stackSetup);
                            tab.setComponentAt(0,scrollPane);
                        }
                    }
                });
                
            }
        });
        
        queueFrame = new QueueFrame();
        tab.addTab("Queue", queueFrame);
        tab.setMnemonicAt(1, KeyEvent.VK_2);
        
        add(tab);
        setVisible(true);
    }
}
