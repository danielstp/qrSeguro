package org.saguez;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Swing
 *
 */
public class App extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 5093844796785224031L;
    // Name-constants to define the various dimensions
    public static final int WINDOW_WIDTH = 300;
    public static final int WINDOW_HEIGHT = 150;

    // private variables of UI components
    JRadioButton RtoLbutton;
    JRadioButton LtoRbutton;
    JTextArea cadenaACifrar;
    FlowLayout experimentLayout = new FlowLayout();
    final String RtoL = "Right to left";
    final String LtoR = "Left to right";
    JButton applyButton = new JButton("Apply component orientation");
    private JButton botonSalir  = new JButton("Salir");;
    private JButton botonGenera = new JButton("GeneraQR");;

    /** Constructor to setup the UI components */
    public App() {
        addWindowListener( new Terminator());
        Container cp = this.getContentPane();

        // Content-pane sets layout
        cp.setLayout(new FlowLayout());

        // Allocate the UI components

        // Content-pane adds components
        addComponentsToPane(cp);

        // Source object adds listener
        // .....

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit when close button clicked
        setTitle("QR Seguro"); // "this" JFrame sets title
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
        //////////////////////////////////////////////////////////////////////////////
        //                               QR                                         //
        //////////////////////////////////////////////////////////////////////////////

    }
    private void generaQR(){
        // change path as per your laptop/desktop location
        String filePath = "./CrunchifyQR.png";
        int size = 125;
        String fileType = "png";
        File myFile = new File(filePath);
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(cadenaACifrar.getText(),BarcodeFormat.QR_CODE, size, size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, myFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\nYou have successfully created QR Code.");
    }

    public void addComponentsToPane(final Container pane) {
        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
        experimentLayout.setAlignment(FlowLayout.TRAILING);
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());

        LtoRbutton = new JRadioButton(LtoR);
        LtoRbutton.setActionCommand(LtoR);
        LtoRbutton.setSelected(true);
        RtoLbutton = new JRadioButton(RtoL);
        RtoLbutton.setActionCommand(RtoL);
        cadenaACifrar = new JTextArea(5,35);
        //Add buttons to the experiment layout
        compsToExperiment.add(cadenaACifrar);
        compsToExperiment.add(new JButton("2"));
        compsToExperiment.add(new JButton("3"));
        compsToExperiment.add(botonGenera);
        compsToExperiment.add(botonSalir);
        
        botonGenera.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                generaQR();
            }
        });

        botonSalir.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            } 
        } );

        //Left to right component orientation is selected by default
        compsToExperiment.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);

        //Add controls to set up the component orientation in the experiment layout
        final ButtonGroup group = new ButtonGroup();
        group.add(LtoRbutton);
        group.add(RtoLbutton);
        controls.add(LtoRbutton);
        controls.add(RtoLbutton);
        controls.add(applyButton);

        //Process the Apply component orientation button press
        applyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String command = group.getSelection().getActionCommand();
                //Check the selection
                if (command.equals("Left to right")) {
                    compsToExperiment.setComponentOrientation(
                            ComponentOrientation.LEFT_TO_RIGHT);
                } else {
                    compsToExperiment.setComponentOrientation(
                            ComponentOrientation.RIGHT_TO_LEFT);
                }
                //update the experiment layout
                compsToExperiment.validate();
                compsToExperiment.repaint();
            }
        });
        pane.add(compsToExperiment, BorderLayout.CENTER);
        pane.add(controls, BorderLayout.SOUTH); ;
    }
}

class Terminator extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0); 
    }
}
