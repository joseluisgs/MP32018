/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp3davidplayer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author joseluisgs
 */
public class MP3DavidPlayer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            Estilo.Defecto();
            VentanaReproductor v = VentanaReproductor.nuevaInstancia("David MP3 Player v.1.0b1");
       
    }
    
}
