/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp3davidplayer;

import com.jtattoo.plaf.JTattooUtilities;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author joseluisgs
 */
public class Estilo {
    
    public static void HiFi(JFrame ventana) {
        try {
            // TODO code application logic here
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            //VentanaReproductor v = VentanaReproductor.nuevaInstancia("David MP3 Player v.1.0b1");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventana.getRootPane().updateUI();
        //SwingUtilities.updateComponentTreeUI(ventana);
        actualizarInterfaz();        
    }

    private static void actualizarInterfaz() {
        if (JTattooUtilities.getJavaVersion() >= 1.6) {
            Window windows[] = Window.getWindows();
            for (int i = 0; i < windows.length; i++) {
                if (windows[i].isDisplayable()) {
                    SwingUtilities.updateComponentTreeUI(windows[i]);
                }
            }
        } else {
            Frame frames[] = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                if (frames[i].isDisplayable()) {
                    SwingUtilities.updateComponentTreeUI(frames[i]);
                }
            }
        }
    }
    
   
     public static void Acryl(JFrame ventana) {
        try {
            // TODO code application logic here
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            //VentanaReproductor v = VentanaReproductor.nuevaInstancia("David MP3 Player v.1.0b1");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ventana.getRootPane().updateUI();
        actualizarInterfaz();
    }
     
    public static void Windows(JFrame ventana) {
        try {
            // TODO code application logic here
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //VentanaReproductor v = VentanaReproductor.nuevaInstancia("David MP3 Player v.1.0b1");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventana.getRootPane().updateUI();
        //SwingUtilities.updateComponentTreeUI(ventana);
        actualizarInterfaz();        
    }
    
    public static void Metal(JFrame ventana) {
        try {
            // TODO code application logic here
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            //VentanaReproductor v = VentanaReproductor.nuevaInstancia("David MP3 Player v.1.0b1");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventana.getRootPane().updateUI();
        //SwingUtilities.updateComponentTreeUI(ventana);
        actualizarInterfaz();        
    }
    
    public static void Smart(JFrame ventana) {
        try {
            // TODO code application logic here
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            //VentanaReproductor v = VentanaReproductor.nuevaInstancia("David MP3 Player v.1.0b1");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventana.getRootPane().updateUI();
        //SwingUtilities.updateComponentTreeUI(ventana);
        actualizarInterfaz();        
    }
    
    public static void Texture(JFrame ventana) {
        try {
            // TODO code application logic here
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            //VentanaReproductor v = VentanaReproductor.nuevaInstancia("David MP3 Player v.1.0b1");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventana.getRootPane().updateUI();
        //SwingUtilities.updateComponentTreeUI(ventana);
        actualizarInterfaz();        
    }
    
    public static void Defecto() {
        try {
            // TODO code application logic here
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            //VentanaReproductor v = VentanaReproductor.nuevaInstancia("David MP3 Player v.1.0b1");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MP3DavidPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        //SwingUtilities.updateComponentTreeUI(ventana);
    }
    

    
}
