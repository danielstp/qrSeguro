package org.saguez;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Swing
 *
 */
public class App extends JFrame {
   // Name-constants to define the various dimensions
   public static final int WINDOW_WIDTH = 300;
   public static final int WINDOW_HEIGHT = 150;
   // ......
 
   // private variables of UI components
   // ......
 
   /** Constructor to setup the UI components */
   public App() {
      Container cp = this.getContentPane();
 
      // Content-pane sets layout
      // cp.setLayout(new ....Layout());
 
      // Allocate the UI components
      // .....
 
      // Content-pane adds components
      // cp.add(....)
 
      // Source object adds listener
      // .....
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit when close button clicked
      setTitle("......"); // "this" JFrame sets title
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);  // or pack() the components
      setVisible(true);   // show it
   }
 
   /** The entry main() method */
   public static void main(String[] args) {
      // Run GUI codes in the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new App();  // Let the constructor do the job
         }
      });
   }
}
