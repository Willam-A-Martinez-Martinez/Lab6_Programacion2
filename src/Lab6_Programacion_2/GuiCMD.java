package Lab6_Programacion_2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GuiCMD {
    FuncionesCMD fCMD = new FuncionesCMD();
    
    JFrame frame = new JFrame("Command Prompt");
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(textArea);
    
    ImageIcon logoCmd = new ImageIcon("cmdLogo.png");
    
    private String rutaActual = "C:\\";
    
    public GuiCMD() {
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
        
        fCMD.setFile(rutaActual); // Se establece la ruta en FuncionesCMD
        
        textArea.setText("Microsoft Windows [Versión 10.0.22000.2538]\n" +
                         "(c) Microsoft Corporation. Todos los derechos reservados.\n\n" +
                         rutaActual + "> ");
        
        frame.add(scrollPane);
        frame.setVisible(true);
        
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    procesarComando();
                    evt.consume();
                } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    restringirBorrado(evt);
                }
            }
        });
    }
    
    private void procesarComando() {
        String texto = textArea.getText();
        String[] lineas = texto.split("\n");

        if (lineas.length > 0) {
            String ultimaLinea = lineas[lineas.length - 1];

            if (ultimaLinea.startsWith(rutaActual + "> ")) {
                String comando = ultimaLinea.substring((rutaActual + "> ").length()).trim();

                if (comando.toLowerCase().startsWith("cd ")) {
                    String nuevaRuta = comando.substring(3).trim();
                    if (!nuevaRuta.isEmpty()) {
                        actualizarRuta(nuevaRuta);
                    }
                } else {
                    fCMD.reconocerComando(comando, textArea);
                    rutaActual = fCMD.getDirectorioActual(); // Actualizar la ruta después de cada comando
                }
            }

            textArea.append("\n" + rutaActual + "> ");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
    
    private void restringirBorrado(KeyEvent evt) {
        int posicionCursor = textArea.getCaretPosition();
        int ultimaRutaPos = textArea.getText().lastIndexOf(rutaActual + "> ") + (rutaActual + "> ").length();
        
        if (posicionCursor <= ultimaRutaPos) {
            evt.consume();
        }
    }
    
    public void actualizarRuta(String nuevaRuta) {
        fCMD.setFile(nuevaRuta); // Establecer la nueva ruta en FuncionesCMD
        rutaActual = fCMD.getDirectorioActual(); // Obtener la ruta actualizada
    }
}