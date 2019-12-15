/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp3davidplayer;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ToggleGroup;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 *
 * @author joseluisgs
 */
public class VentanaReproductor extends javax.swing.JFrame implements BasicPlayerListener {
    private static VentanaReproductor v = null;
    
    String ficheroMP3;
    BasicPlayer player = new BasicPlayer();
    BasicController control;
    
    Tiempo estadoTotal = new Tiempo();
    Tiempo estadoActual = new Tiempo();
    
    private boolean listaCargada = false;
    TablaCancionesModel tablaCancionesModel = new TablaCancionesModel();
    int indiceActual = -1;
    // Reloj para comprobar si hay reproduciendo sigamos haciendolo
    Timer reloj;
    int delay = 1000;// cada evento del reloj
    
    // Menu estilo
    ButtonGroup grupoMenuEstilo = new ButtonGroup();
    
    /**
     * Creates new form VentanaReproductor
     */
    public VentanaReproductor() {
        initComponents();
        iniciarVentana();
    }
    
     public static VentanaReproductor nuevaInstancia(String titulo) {
        if(v == null) {
            v = new VentanaReproductor();
            v.setTitle(titulo);
            
        }
        return v;         
    }
     
     
     private void iniciarVentana() {
       this.setVisible(true);
       this.setLocationRelativeTo(null);
       //this.setTitle("MP3 David Player 1.0.b1");
       Toolkit t = Toolkit.getDefaultToolkit();
       this.setIconImage(t.getImage(Recursos.getPathBaseImagenes()+"icono.png"));
       
       
       
       // iconos de la aplicacion
        ponerIconosBotones();
       
        // reloj para pasar enre canciones
        prepararReloj();
        
        // opciones de menu estilos
        grupoMenuEstilo.add(this.menuEstiloAcryl);
        grupoMenuEstilo.add(this.menuEstiloHiFi);
        grupoMenuEstilo.add(this.menuEstiloMetal);
        grupoMenuEstilo.add(this.menuEstiloSmart);
        grupoMenuEstilo.add(this.menuEstiloTexture);
        grupoMenuEstilo.add(this.menuEstiloWindows);

        //Estilo.HiFi(this);
        
        //this.pack();
       
    }
     
     // Aprender MUY IMPORTANTE. Entra en EXAMEN!!!
    private void prepararReloj() {
        //Iniciamos el reloj
        this.reloj= new Timer(delay, new ActionListener() {
            @Override
            // Metodo a hacer en cada evento (tick) del reloj
            public void actionPerformed(ActionEvent e) {
                //System.err.println("Tick");
               if(player.getStatus()==player.STOPPED) // Si ha terminado una canción
                    siguienteCancion();
            }
            
        });
    }

    private void ponerIconosBotones() {
        // Iconos botones
        ImageIcon imageIconAbrir = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"abrir.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        this.botonAbrir.setIcon(imageIconAbrir);
        ImageIcon imageIconMenuAbrir = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"abrir.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuAbrir.setIcon(imageIconMenuAbrir);
        
        ImageIcon imageIconBorrar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"borrar.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        this.botonBorrar.setIcon(imageIconBorrar);
        ImageIcon imageIconMenuBorrar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"borrar.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuBorrar.setIcon(imageIconMenuBorrar);
        
        
        ImageIcon imageIconLimpiar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"limpiar.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        this.botonLimpiar.setIcon(imageIconLimpiar);
        ImageIcon imageIconMenuLimpiar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"limpiar.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuLimpiar.setIcon(imageIconMenuLimpiar);
        
        ImageIcon imageIconAnterior = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"rw01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        this.botonAnterior.setIcon(imageIconAnterior);
        ImageIcon imageIconMenuAnterior = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"rw01.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuAnterior.setIcon(imageIconMenuAnterior);
        
        ImageIcon imageIconReproducir = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"play01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        this.botonReproducir.setIcon(imageIconReproducir);
        ImageIcon imageIconMenuReproducir = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"play01.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuReproducir.setIcon(imageIconMenuReproducir);
        
        
        ImageIcon imageIconPausar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"pause01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        this.botonPausar.setIcon(imageIconPausar);
        ImageIcon imageIconMenuPausar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"pause01.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuPausar.setIcon(imageIconMenuPausar);
        
        
        ImageIcon imageIconParar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"stop01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        this.botonParar.setIcon(imageIconParar);
        ImageIcon imageIconMenuParar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"stop01.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuParar.setIcon(imageIconMenuParar);
        
        ImageIcon imageIconSiguiente = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"fw01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        this.botonSiguiente.setIcon(imageIconSiguiente);
        ImageIcon imageIconMenuSiguiente = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"fw01.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuSiguiente.setIcon(imageIconMenuSiguiente);
        
        ImageIcon imageIconMenuSalir = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"salir.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuSalir.setIcon(imageIconMenuSalir);
        
        ImageIcon imageIconMenuAcercaDe = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"acercade.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.menuAcercaDe.setIcon(imageIconMenuAcercaDe);
    }
     
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        botonAbrir = new javax.swing.JButton();
        labelCancion = new javax.swing.JLabel();
        botonLimpiar = new javax.swing.JButton();
        botonBorrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        botonReproducir = new javax.swing.JButton();
        botonParar = new javax.swing.JButton();
        botonPausar = new javax.swing.JButton();
        barraVolumen = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        botonAnterior = new javax.swing.JButton();
        botonSiguiente = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        labelActual = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();
        barraProgreso = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCanciones = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuAbrir = new javax.swing.JMenuItem();
        menuBorrar = new javax.swing.JMenuItem();
        menuLimpiar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuReproducir = new javax.swing.JMenuItem();
        menuParar = new javax.swing.JMenuItem();
        menuPausar = new javax.swing.JMenuItem();
        menuAnterior = new javax.swing.JMenuItem();
        menuSiguiente = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuEstiloHiFi = new javax.swing.JRadioButtonMenuItem();
        menuEstiloWindows = new javax.swing.JRadioButtonMenuItem();
        menuEstiloMetal = new javax.swing.JRadioButtonMenuItem();
        menuEstiloAcryl = new javax.swing.JRadioButtonMenuItem();
        menuEstiloSmart = new javax.swing.JRadioButtonMenuItem();
        menuEstiloTexture = new javax.swing.JRadioButtonMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("MP3 David Player");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        botonAbrir.setToolTipText("Abrir Ficheros");
        botonAbrir.setMaximumSize(new java.awt.Dimension(30, 30));
        botonAbrir.setMinimumSize(new java.awt.Dimension(30, 30));
        botonAbrir.setPreferredSize(new java.awt.Dimension(30, 30));
        botonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAbrirActionPerformed(evt);
            }
        });

        labelCancion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelCancion.setText("  ");

        botonLimpiar.setToolTipText("Limpiar lista");
        botonLimpiar.setMaximumSize(new java.awt.Dimension(30, 30));
        botonLimpiar.setMinimumSize(new java.awt.Dimension(30, 30));
        botonLimpiar.setPreferredSize(new java.awt.Dimension(30, 30));
        botonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarActionPerformed(evt);
            }
        });

        botonBorrar.setToolTipText("Borrar Canción");
        botonBorrar.setMaximumSize(new java.awt.Dimension(30, 30));
        botonBorrar.setMinimumSize(new java.awt.Dimension(30, 30));
        botonBorrar.setPreferredSize(new java.awt.Dimension(30, 30));
        botonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCancion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCancion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonAbrir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(botonBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botonLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        botonReproducir.setToolTipText("Reproducir");
        botonReproducir.setPreferredSize(new java.awt.Dimension(45, 45));
        botonReproducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReproducirActionPerformed(evt);
            }
        });

        botonParar.setToolTipText("Parar");
        botonParar.setMaximumSize(new java.awt.Dimension(85, 23));
        botonParar.setMinimumSize(new java.awt.Dimension(85, 23));
        botonParar.setPreferredSize(new java.awt.Dimension(45, 45));
        botonParar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonPararMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                botonPararMouseReleased(evt);
            }
        });
        botonParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPararActionPerformed(evt);
            }
        });

        botonPausar.setToolTipText("Pausar");
        botonPausar.setMaximumSize(new java.awt.Dimension(85, 23));
        botonPausar.setMinimumSize(new java.awt.Dimension(85, 23));
        botonPausar.setPreferredSize(new java.awt.Dimension(45, 45));
        botonPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPausarActionPerformed(evt);
            }
        });

        barraVolumen.setToolTipText("");
        barraVolumen.setValue(85);
        barraVolumen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                barraVolumenStateChanged(evt);
            }
        });

        jLabel2.setText("Volumen");

        botonAnterior.setToolTipText("Anterior");
        botonAnterior.setMaximumSize(new java.awt.Dimension(45, 45));
        botonAnterior.setMinimumSize(new java.awt.Dimension(45, 45));
        botonAnterior.setPreferredSize(new java.awt.Dimension(45, 45));
        botonAnterior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonAnteriorMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                botonAnteriorMouseReleased(evt);
            }
        });
        botonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnteriorActionPerformed(evt);
            }
        });

        botonSiguiente.setToolTipText("Siguiente");
        botonSiguiente.setPreferredSize(new java.awt.Dimension(45, 45));
        botonSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonSiguienteMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                botonSiguienteMouseReleased(evt);
            }
        });
        botonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSiguienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonReproducir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonPausar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonParar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(barraVolumen, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(barraVolumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonParar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonPausar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonReproducir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelActual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelActual.setText("00:00");

        labelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotal.setText("00:00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barraProgreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(labelActual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelTotal)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelActual)
                    .addComponent(labelTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        tablaCanciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Grupo", "Título", "Duración"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCanciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCancionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCanciones);

        jMenu1.setText("Archivo");

        menuAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuAbrir.setText("Abrir ficheros");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        jMenu1.add(menuAbrir);

        menuBorrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        menuBorrar.setText("Borrar Canción");
        menuBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBorrarActionPerformed(evt);
            }
        });
        jMenu1.add(menuBorrar);

        menuLimpiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        menuLimpiar.setText("Limpiar lista");
        menuLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLimpiarActionPerformed(evt);
            }
        });
        jMenu1.add(menuLimpiar);
        jMenu1.add(jSeparator1);

        menuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSalir.setText("Salir");
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        jMenu1.add(menuSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Reproducción");

        menuReproducir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuReproducir.setText("Reproducir");
        menuReproducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuReproducirActionPerformed(evt);
            }
        });
        jMenu2.add(menuReproducir);

        menuParar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuParar.setText("Parar");
        menuParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPararActionPerformed(evt);
            }
        });
        jMenu2.add(menuParar);

        menuPausar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuPausar.setText("Pausar");
        menuPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPausarActionPerformed(evt);
            }
        });
        jMenu2.add(menuPausar);

        menuAnterior.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuAnterior.setText("Anterior");
        menuAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAnteriorActionPerformed(evt);
            }
        });
        jMenu2.add(menuAnterior);

        menuSiguiente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSiguiente.setText("Siguiente");
        menuSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSiguienteActionPerformed(evt);
            }
        });
        jMenu2.add(menuSiguiente);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Estilos");

        menuEstiloHiFi.setSelected(true);
        menuEstiloHiFi.setText("Hi-Fi");
        menuEstiloHiFi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEstiloHiFiActionPerformed(evt);
            }
        });
        jMenu4.add(menuEstiloHiFi);

        menuEstiloWindows.setText("Windows");
        menuEstiloWindows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEstiloWindowsActionPerformed(evt);
            }
        });
        jMenu4.add(menuEstiloWindows);

        menuEstiloMetal.setText("Metal");
        menuEstiloMetal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEstiloMetalActionPerformed(evt);
            }
        });
        jMenu4.add(menuEstiloMetal);

        menuEstiloAcryl.setText("Acryl");
        menuEstiloAcryl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEstiloAcrylActionPerformed(evt);
            }
        });
        jMenu4.add(menuEstiloAcryl);

        menuEstiloSmart.setText("Smart");
        menuEstiloSmart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEstiloSmartActionPerformed(evt);
            }
        });
        jMenu4.add(menuEstiloSmart);

        menuEstiloTexture.setText("Texture");
        menuEstiloTexture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEstiloTextureActionPerformed(evt);
            }
        });
        jMenu4.add(menuEstiloTexture);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Ayuda");

        menuAcercaDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menuAcercaDe.setText("Acerca de");
        menuAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAcercaDeActionPerformed(evt);
            }
        });
        jMenu3.add(menuAcercaDe);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAbrirActionPerformed

        cargarCancionesenTabla();
        System.gc();
        
    }//GEN-LAST:event_botonAbrirActionPerformed

    private void cargarCancionesenTabla() throws HeadlessException {
        // Inicio controles
        player = new BasicPlayer();
        control = (BasicController) player;
        player.addBasicPlayerListener(this);
        
        // elijo las canciones
        JFileChooser elegirRuta = new JFileChooser();
        elegirRuta.setDialogTitle("Selecciona el fichero MP3");
        FileNameExtensionFilter filtroFichero=new FileNameExtensionFilter("MP3","mp3");
        elegirRuta.setFileFilter(filtroFichero);
        elegirRuta.setMultiSelectionEnabled(true);
        int seleccion = elegirRuta.showOpenDialog(this);
        // Si pulsa guardar o aceptar
        if (seleccion == JFileChooser.OPEN_DIALOG) {
            File[] ficheros = elegirRuta.getSelectedFiles();
            
            //limpiamos la tabla y cambiamos el reloj
            setCursor(Cursor.WAIT_CURSOR);
            listaCargada = false;
            ((DefaultTableModel)this.tablaCanciones.getModel()).setRowCount(0);
            this.tablaCancionesModel = new TablaCancionesModel(ficheros);
            this.tablaCanciones.setModel(this.tablaCancionesModel);
            this.tablaCanciones.setRowSelectionAllowed(true);
            this.tablaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.tablaCanciones.getColumnModel().getColumn(2).setPreferredWidth(10);
            
            // Añadimos el evento doble click para una fila de la tabla
            /*
            this.tablaCanciones.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        JTable tabla = (JTable) e.getSource();
                        indiceActual= tabla.getSelectedRow();
                        reproducirCancion(indiceActual);
                    }
                }
            });
            */
   
            
            // cargamos los datos de la canción
            for(File f: ficheros){
                try {
                    control.open(f);
                    
                } catch (BasicPlayerException ex) {
                    JOptionPane.showMessageDialog(this,ex.getMessage(),"Error al cargar canciones",JOptionPane.ERROR_MESSAGE);
                }
            }
            listaCargada=true;
            // si hay canciones seleccionams la primera
            if(this.tablaCanciones.getRowCount()>0) {
                indiceActual=0;
                this.tablaCanciones.setRowSelectionInterval(0, 0);
            }
            // ponemos el cursos por defecto
            setCursor(Cursor.getDefaultCursor());
       
        }
    }

    private void botonReproducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReproducirActionPerformed
        reproducirCancion(indiceActual);
        
    }//GEN-LAST:event_botonReproducirActionPerformed

    private void botonPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPararActionPerformed
        pararCancion();
    }//GEN-LAST:event_botonPararActionPerformed

    private void pararCancion() {
         if(this.tablaCancionesModel.size()>0){
            try {
                // TODO add your handling code here:
                control.stop();
                ImageIcon imageIconReproducir = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"play01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                this.botonReproducir.setIcon(imageIconReproducir);
                ImageIcon imageIconPausar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"pause01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                this.botonPausar.setIcon(imageIconPausar);
                reloj.stop();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }

    private void botonPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPausarActionPerformed
        pausarCancion();
    }//GEN-LAST:event_botonPausarActionPerformed

    private void pausarCancion() {
        // TODO add your handling code here:
        if(this.player.getStatus()==player.PAUSED){
            //this.botonPausar.setText("Pausar");
            try {
                control.resume();
                ImageIcon imageIconPausar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"pause01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                this.botonPausar.setIcon(imageIconPausar);
                reloj.restart();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(this.player.getStatus()==player.PLAYING){
            try {
                control.pause();
                ImageIcon imageIconPausar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"pause02.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                this.botonPausar.setIcon(imageIconPausar);
                reloj.stop();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void barraVolumenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_barraVolumenStateChanged
        actualizarVolumen();
        this.barraVolumen.setToolTipText("Vol: "+this.barraVolumen.getValue());
    }//GEN-LAST:event_barraVolumenStateChanged

    private void actualizarVolumen() {
        // TODO add your handling code here:
        float valor = this.barraVolumen.getValue();
        try {
            if(control!=null)
                control.setGain(valor/100);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tablaCancionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCancionesMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1) {
            this.indiceActual=this.tablaCanciones.getSelectedRow();
            this.tablaCanciones.setRowSelectionInterval(indiceActual, indiceActual);
        }else if(evt.getClickCount()==2){
            indiceActual= this.tablaCanciones.getSelectedRow();
            reproducirCancion(indiceActual);
        }
        
    }//GEN-LAST:event_tablaCancionesMouseClicked

    private void botonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarActionPerformed

        borrarCancion();
    }//GEN-LAST:event_botonBorrarActionPerformed

    private void botonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarActionPerformed
        limpiarLista();
    }//GEN-LAST:event_botonLimpiarActionPerformed

    private void limpiarLista() {
        // TODO add your handling code here:
        if(player.getStatus()==player.PLAYING && player!=null){
            this.pararCancion();
        }
        this.tablaCancionesModel = new TablaCancionesModel();
        this.tablaCanciones.setModel(tablaCancionesModel);
        this.labelCancion.setText("");
        this.labelActual.setText("00:00");
        this.labelTotal.setText("00:00");
    }

    private void botonAnteriorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAnteriorMousePressed
        // TODO add your handling code here:
       ImageIcon imageIconAnterior = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"rw02.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
       this.botonAnterior.setIcon(imageIconAnterior);
    }//GEN-LAST:event_botonAnteriorMousePressed

    private void botonAnteriorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAnteriorMouseReleased
        // TODO add your handling code here:
        ImageIcon imageIconAnterior = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"rw01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
       this.botonAnterior.setIcon(imageIconAnterior);
    }//GEN-LAST:event_botonAnteriorMouseReleased

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        anteriorCancion();
    }//GEN-LAST:event_botonAnteriorActionPerformed

    private void anteriorCancion() {
        // TODO add your handling code here:
        this.indiceActual--;
        actualizarCancionActual();
        reproducirCancion(this.indiceActual);
    }

    private void botonPararMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonPararMousePressed
        // TODO add your handling code here:
       ImageIcon imageIconParar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"stop02.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
       this.botonParar.setIcon(imageIconParar);
    }//GEN-LAST:event_botonPararMousePressed

    private void botonPararMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonPararMouseReleased
        // TODO add your handling code here:
       ImageIcon imageIconParar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"stop01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
       this.botonParar.setIcon(imageIconParar);
    }//GEN-LAST:event_botonPararMouseReleased

    private void botonSiguienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSiguienteMousePressed
        // TODO add your handling code here:
        ImageIcon imageIconSiguiente = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"fw02.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
       this.botonSiguiente.setIcon(imageIconSiguiente);
    }//GEN-LAST:event_botonSiguienteMousePressed

    private void botonSiguienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSiguienteMouseReleased
        // TODO add your handling code here:
       ImageIcon imageIconSiguiente = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"fw01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
       this.botonSiguiente.setIcon(imageIconSiguiente);
    }//GEN-LAST:event_botonSiguienteMouseReleased

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed
        siguienteCancion();
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void siguienteCancion() {
        // TODO add your handling code here:
        this.indiceActual++;
        actualizarCancionActual();
        reproducirCancion(this.indiceActual);
    }

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        // TODO add your handling code here:
        cerrarAplicacion();
    }//GEN-LAST:event_menuSalirActionPerformed

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        // TODO add your handling code here:
        this.cargarCancionesenTabla();
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void menuBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBorrarActionPerformed
        // TODO add your handling code here:
        this.borrarCancion();
    }//GEN-LAST:event_menuBorrarActionPerformed

    private void menuLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLimpiarActionPerformed
        // TODO add your handling code here:
        this.limpiarLista();
    }//GEN-LAST:event_menuLimpiarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.cerrarAplicacion();
    }//GEN-LAST:event_formWindowClosing

    private void menuAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAcercaDeActionPerformed
        // TODO add your handling code here:
        VentanaAcercaDe v = VentanaAcercaDe.nuevaInstancia("Acerca de David MP3 Player");
    }//GEN-LAST:event_menuAcercaDeActionPerformed

    private void menuReproducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuReproducirActionPerformed
        // TODO add your handling code here:
        this.reproducirCancion(this.indiceActual);
    }//GEN-LAST:event_menuReproducirActionPerformed

    private void menuPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPararActionPerformed
        // TODO add your handling code here:
        this.pararCancion();
    }//GEN-LAST:event_menuPararActionPerformed

    private void menuPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPausarActionPerformed
        // TODO add your handling code here:
        this.pausarCancion();
    }//GEN-LAST:event_menuPausarActionPerformed

    private void menuAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAnteriorActionPerformed
        // TODO add your handling code here:
        this.anteriorCancion();
    }//GEN-LAST:event_menuAnteriorActionPerformed

    private void menuSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSiguienteActionPerformed
        // TODO add your handling code here:
        this.siguienteCancion();
    }//GEN-LAST:event_menuSiguienteActionPerformed

    private void menuEstiloHiFiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEstiloHiFiActionPerformed
        // TODO add your handling code here:
        Estilo.HiFi(this);
    }//GEN-LAST:event_menuEstiloHiFiActionPerformed

    private void menuEstiloWindowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEstiloWindowsActionPerformed
        // TODO add your handling code here:
        Estilo.Windows(this);
    }//GEN-LAST:event_menuEstiloWindowsActionPerformed

    private void menuEstiloAcrylActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEstiloAcrylActionPerformed
        // TODO add your handling code here:
        Estilo.Acryl(this);
    }//GEN-LAST:event_menuEstiloAcrylActionPerformed

    private void menuEstiloMetalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEstiloMetalActionPerformed
        // TODO add your handling code here:
        Estilo.Metal(this);
    }//GEN-LAST:event_menuEstiloMetalActionPerformed

    private void menuEstiloSmartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEstiloSmartActionPerformed
        // TODO add your handling code here:
        Estilo.Smart(this);
    }//GEN-LAST:event_menuEstiloSmartActionPerformed

    private void menuEstiloTextureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEstiloTextureActionPerformed
        // TODO add your handling code here:
        Estilo.Texture(this);
    }//GEN-LAST:event_menuEstiloTextureActionPerformed

    private void borrarCancion() {
        // TODO add your handling code here:
        if(this.tablaCancionesModel.size()>0){
            if(player.getStatus()==player.PLAYING){
                this.pararCancion();
            }
            int indice = this.tablaCanciones.getSelectedRow();
            this.tablaCancionesModel.removeRow(indice);
            this.tablaCancionesModel.borrarFichero(indice);
            this.indiceActual++;
            actualizarCancionActual();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaReproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaReproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaReproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaReproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaReproductor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgreso;
    private javax.swing.JSlider barraVolumen;
    private javax.swing.JButton botonAbrir;
    private javax.swing.JButton botonAnterior;
    private javax.swing.JButton botonBorrar;
    private javax.swing.JButton botonLimpiar;
    private javax.swing.JButton botonParar;
    private javax.swing.JButton botonPausar;
    private javax.swing.JButton botonReproducir;
    private javax.swing.JButton botonSiguiente;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel labelActual;
    private javax.swing.JLabel labelCancion;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuAcercaDe;
    private javax.swing.JMenuItem menuAnterior;
    private javax.swing.JMenuItem menuBorrar;
    private javax.swing.JRadioButtonMenuItem menuEstiloAcryl;
    private javax.swing.JRadioButtonMenuItem menuEstiloHiFi;
    private javax.swing.JRadioButtonMenuItem menuEstiloMetal;
    private javax.swing.JRadioButtonMenuItem menuEstiloSmart;
    private javax.swing.JRadioButtonMenuItem menuEstiloTexture;
    private javax.swing.JRadioButtonMenuItem menuEstiloWindows;
    private javax.swing.JMenuItem menuLimpiar;
    private javax.swing.JMenuItem menuParar;
    private javax.swing.JMenuItem menuPausar;
    private javax.swing.JMenuItem menuReproducir;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JMenuItem menuSiguiente;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tablaCanciones;
    // End of variables declaration//GEN-END:variables

public void setController(BasicController controller)
  {
    //display("setController : "+controller);
  }

    @Override
    public void opened(Object o, Map map) {
        //display("opened : "+map.toString());
        /*
        //Para saber los datos del map
        for(Object key: map.keySet())
            System.out.println(key + " - " + map.get(key));
        */
        if(!listaCargada) {
            // obteniendo lso datos de la cancion los meto en la tabla
            String titulo=map.get("title").toString();
            String grupo =map.get("author").toString();
            Tiempo tiempo = new Tiempo(Integer.parseInt(map.get("duration").toString()));
            //String duracion = tiempo.getMinutos()+":"+tiempo.getSegundos();
            Vector fila = new Vector();
            fila.add(grupo);
            fila.add(titulo);
            fila.add(tiempo.toString());
            this.tablaCancionesModel.addRow(fila);
        }else {
            
            this.labelCancion.setText(map.get("author").toString()+": "+ map.get("title").toString());
            
            this.estadoTotal.setValorTotal(Integer.parseInt(map.get("duration").toString()));
            this.labelTotal.setText(this.estadoTotal.toString());
            this.barraProgreso.setMaximum((int) this.estadoTotal.getValorTotal());
            this.barraProgreso.setMinimum(0);
        }
        
       
        
        
        
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
        /* Para saber los valores del map
         display("progress : "+map.toString());
         for(Object key: map.keySet())
            System.out.println(key + " - " + map.get(key));
        */ 
          this.estadoActual.setValorTotal(Integer.parseInt(map.get("mp3.position.microseconds").toString()));
          this.labelActual.setText(this.estadoActual.toString());
          this.barraProgreso.setValue((int) this.estadoActual.getValorTotal());
         


    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {
         //display("stateUpdated : "+bpe.toString());
    }
    
    public void display(String salida) {
        //System.out.println(salida);
    }

    private void reproducirCancion(int indice) {
         if(this.tablaCancionesModel.size()>0){
            try {
                File fichero = this.tablaCancionesModel.getFichero(indice);
                control.open(fichero);
                control.play();
                ImageIcon imageIconReproducir = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"play02.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                this.botonReproducir.setIcon(imageIconReproducir);
                ImageIcon imageIconPausar = new ImageIcon(new ImageIcon(Recursos.getPathBaseImagenes()+"pause01.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                this.botonPausar.setIcon(imageIconPausar);
                
                // comenzamos con el reloj
                reloj.restart();
                
                
                
            } catch (BasicPlayerException ex) {
                Logger.getLogger(VentanaReproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }

    private void actualizarCancionActual() {
        if(this.tablaCancionesModel.size()!=0){
            if(this.indiceActual>=this.tablaCancionesModel.size()){
                this.indiceActual=0;
                //this.tablaCanciones.setRowSelectionInterval(indiceActual, indiceActual);
            }
            if(this.indiceActual<0){
                this.indiceActual=this.tablaCancionesModel.size()-1;
                //this.tablaCanciones.setRowSelectionInterval(indiceActual, indiceActual);
            }else if(this.indiceActual==0){
                this.indiceActual=0;
            }
            //reproducirCancion(this.indiceActual);
             this.tablaCanciones.setRowSelectionInterval(indiceActual, indiceActual);
        }
    }

    private void cerrarAplicacion() {
        //Preguntamos si queremos cerrar
        int seleccion = JOptionPane.showConfirmDialog(null, "¿Realmente desea salir David MP3 Player?", "Salir David MP3 Player", JOptionPane.YES_NO_OPTION);
        if (seleccion == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
            
    // Metodo del evento del reloj Timer
    

}
