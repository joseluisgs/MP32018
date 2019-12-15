/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp3davidplayer;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author joseluisgs
 */
public class Recursos {
    public static String getPathBaseImagenes() {
        // Path Actual, directorio de la BD
        String path = System.getProperty("user.dir");
        String pathImagenes= path+"\\imagenes\\";
        return pathImagenes;   
    }

    static void enviarEmail(String address, JFrame ventana) {
       try {
            Desktop.getDesktop().mail(new URI("mailto:" + address + "?subject=Hola 1º DAM"));
        } catch (URISyntaxException | IOException ex) {
            JOptionPane.showMessageDialog(ventana,"No se puede mandar correo electrónico.\nRevise su configuación.", "Error al mandar correo electrónico",JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
