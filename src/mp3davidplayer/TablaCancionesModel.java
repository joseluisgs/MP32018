/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp3davidplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joseluisgs
 */
public class TablaCancionesModel extends DefaultTableModel {
    private ArrayList<File> ficheros = new ArrayList<File>();
    
    public TablaCancionesModel(File[] listaFicheros) {
        
        this.addColumn("Grupo");
        this.addColumn("Título");
        this.addColumn("Duración");
        
        for(File f: listaFicheros)
            this.ficheros.add(f);
        
    }
    
    public TablaCancionesModel() {
        
        this.addColumn("Grupo");
        this.addColumn("Título");
        this.addColumn("Duración");

        
    }
    
    public void setFilaCalificacion(Vector filaCancion){
        // Añadimos Filas
        if(filaCancion!=null) {
           this.addRow(filaCancion);
        }
    }
    
    public File getFichero(int indice) {
    
        return ficheros.get(indice);
    }
    
    
    @Override
        public boolean isCellEditable(int row, int column){
            // make read only fields except column 0,13,14
            //return column == 4;
            return false;

    }
        
        public void borrarFichero(int indice){
            this.ficheros.remove(indice);
        }
        
        public int size(){
            return this.ficheros.size();
        }
    
    
}
