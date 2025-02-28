 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab6_Programacion_2;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author DELL
 */
public class GuiCMD {
    FuncionesCMD fCMD = new FuncionesCMD();
    
    JFrame frame = new JFrame("Command Prompt");
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(textArea);
    
    ImageIcon logoCmd= new ImageIcon("cmdLogo");
    public GuiCMD(){
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 400);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setIconImage(logoCmd.getImage());
    
    
    textArea.setBackground(Color.BLACK);
    textArea.setForeground(Color.GREEN);
    textArea.setFont(new Font("Consolas", Font.PLAIN, 16));
    textArea.setCaretColor(Color.GREEN);
    textArea.setEditable(true);
    textArea.setText("Microsoft Windows [Versión 10.0.22000.2538]\n" +
                     "(c) Microsoft Corporation. Todos los derechos reservados.\n");
    textArea.setCaretColor(Color.GREEN);
    frame.add(scrollPane);
    frame.setVisible(true);
    
    textArea.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(java.awt.event.KeyEvent evt) {
        
        
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            
            
            String texto = textArea.getText();
            String[] lineas = texto.split("\\n"); // Dividir por líneas
            
            if (lineas.length > 0) {
                String ultimaLinea = lineas[lineas.length - 1]; // Obtener la última línea
                fCMD.reconocerComando(ultimaLinea, textArea);
            }
        }
    }
    });
    
    }
}
