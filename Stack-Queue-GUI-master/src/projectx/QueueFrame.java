/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

/**
 *
 * @author DELL
 */
public class QueueFrame extends JPanel{
   
    Stack choiceStack;
    boolean isRandom;
    QDisplay qDisplay;
    JPanel qDisplayPanel;
    QueueMenu qMenu;
    JPanel qMenuPanel;
    JPanel qPanel;
    JLabel qMessage;
    JPanel qMessagePanel;
    
    public QueueFrame(){
        
        choiceStack = new Stack(3);
        isRandom = false;

        JScrollPane scrollPane = new JScrollPane();
        
        qPanel = new JPanel(new BorderLayout());
        
        //Queue title and styling
        JLabel qTitle = new JLabel("<HTML><U>DATA STRUCTURES : DEMONSTRATION OF THE QUEUE</U></HTML>");
        qTitle.setFont(new Font("Baskerville Old Face", Font.PLAIN, 28));
        qTitle.setForeground(Color.yellow);
        qTitle.setPreferredSize(new Dimension(800, 80));
        
        JPanel qTitlePanel = new JPanel();
        qTitlePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 350, 0));
        qTitlePanel.setBackground(Color.darkGray);
        qTitlePanel.add(qTitle);
        
        //Queue message panel and styling
        qMessage = new JLabel(">>>Welcome to Queue Demo. An empty queue has been made. Front = 0, Rear = -1");
        qMessage.setForeground(Color.white);
        qMessage.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        
        qMessagePanel = new JPanel();
        qMessagePanel.setBackground(Color.BLACK);
        qMessagePanel.add(qMessage);

        //Queue menu and styling
        qMenu = new QueueMenu();
        qMenu.setBackground(Color.DARK_GRAY);
        
        qMenuPanel = new JPanel();
        qMenuPanel.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.LIGHT_GRAY));
        qMenuPanel.setBackground(Color.darkGray);
        qMenu.setPreferredSize(new Dimension(1350, 100));
        qMenuPanel.add(qMenu);
        qMenuPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        
        Box topDisplay = Box.createVerticalBox();
        topDisplay.add(qTitlePanel);
        topDisplay.add(qMessagePanel);
        topDisplay.add(qMenuPanel);
        
        qPanel.add(topDisplay, BorderLayout.NORTH);

        //Queue display and styling
        qDisplay = new QDisplay();
        qDisplayPanel = new JPanel();
        qDisplayPanel.add(Box.createRigidArea(new Dimension(0, 425)));
        qDisplayPanel.add(qDisplay);
        qDisplayPanel.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.LIGHT_GRAY));

        Box qDisplayBox = Box.createVerticalBox();
        qDisplayBox.add(qDisplayPanel);
        
        scrollPane.setViewportView(qDisplayBox);
        scrollPane.setVisible(true);

        qPanel.add(scrollPane, BorderLayout.CENTER);
        
        super.add(qPanel);

        //Interface to set the text in Queue Message Panel
        qDisplay.textSetter = new TextInterface() {
            public void setText(String str) {
                qMessage.setText(">>>" + str);
            }
        };

        addQueueActionListeners(); //Function to add actionlisteners to queue menu buttons
    }
    
    private void addQueueActionListeners() {

        //Queue Insert button on click actionlistener
        qMenu.insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String str = qMenu.text.getText();
                qMenu.text.setValue(null);
                if (isNum(str)) {
                    str = str.replace(",", "");
                    int num = Integer.parseInt(str);
                    qDisplay.update_insert(num, choiceStack);
                    qMenu.resetButton.setEnabled(true);
                    qMenu.resetEnabled = true;
                } else {
                    qDisplay.textSetter.setText("Invalid number");
                }
                if (choiceStack.top != -1) {
                    qMenu.undoButton.setEnabled(true);
                    qMenu.undoEnabled = true;
                }
                qPanel.updateUI();
            }
        });

        //Queue Delete button onclick actionlistener
        qMenu.deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                qDisplay.update_delete(choiceStack);
                if (choiceStack.top != -1) {
                    qMenu.undoButton.setEnabled(true);
                }
                if (qDisplay.q.rear == -1 && qDisplay.q.front == 0 && choiceStack.top == -1) {
                    qMenu.resetButton.setEnabled(false);
                }
                qPanel.updateUI();
            }
        });

        //Queue undo button onclick actionlistener
        qMenu.undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int choice = choiceStack.pop();
                if (choice == 0) {
                    qDisplay.undoInsert();
                    if (qDisplay.q.rear == -1 && qDisplay.q.front == 0 && choiceStack.top == -1) {
                        qMenu.resetButton.setEnabled(false);
                    }
                } else {
                    qDisplay.undoDelete();
                    qMenu.resetButton.setEnabled(true);

                }
                if (choiceStack.top == -1) {
                    qMenu.undoButton.setEnabled(false);
                    qMenu.undoEnabled = false;
                }
                qPanel.updateUI();
            }
        });

        //Queue random button onclick actionlistener
        qMenu.randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!isRandom) {
                    isRandom = true;
                    qMenu.randomButton.setText("Stop");
                    qMenu.text.setEnabled(false);
                    qMenu.insertButton.setEnabled(false);
                    qMenu.deleteButton.setEnabled(false);
                    qMenu.undoButton.setEnabled(false);
                    qMenu.resetButton.setEnabled(false);
                    startQueueRandom();
                } else {
                    isRandom = false;
                    qMenu.randomButton.setText("Random");
                    qMenu.text.setEnabled(true);
                    qMenu.insertButton.setEnabled(true);
                    qMenu.deleteButton.setEnabled(true);
                    qMenu.undoButton.setEnabled(qMenu.undoEnabled);
                    qMenu.resetButton.setEnabled(qMenu.resetEnabled);
                }
            }
        });

        //Queue reset button onclick actionlistener
        qMenu.resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                qDisplay.reset();
                choiceStack.top = -1;
                qMenu.undoButton.setEnabled(false);
                qMenu.undoEnabled = false;
                qMenu.resetButton.setEnabled(false);
                qMenu.resetEnabled = false;
                qPanel.updateUI();
            }
        });
    }

    //Function to implement automatic random operations in Queue
    private void startQueueRandom() {
        SwingWorker<Void, Void> q_random;
        q_random = new SwingWorker<Void, Void>() {
            protected Void doInBackground() throws Exception {
                if (qDisplay.q.front == qDisplay.q.size) {
                    qDisplay.textSetter.setText("Cannot perform any more operations on the queue. Please try using the undo or the reset buttons to continue. Front = " + Integer.toString(qDisplay.q.front) + " Rear = " + Integer.toString(qDisplay.q.rear));
                    qMenu.randomButton.doClick();
                    return null;
                }
                Thread.sleep(100);
                qDisplay.random(choiceStack);
                if (choiceStack.top != -1) {
                    qMenu.undoEnabled = true;
                }
                qMenu.resetEnabled = true;
                qPanel.updateUI();
                Thread.sleep(750);
                return null;
            }

            protected void done() {
                if (isRandom) {
                    startQueueRandom();
                }
            }
        };
        q_random.execute();
    }

    //Function to check if the input string is a valid number or not
    private static boolean isNum(String str) {
        str = str.replace(",", "");
        try {
            Integer x = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
