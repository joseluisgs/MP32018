/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp3davidplayer;

/**
 *
 * @author joseluisgs
 */
public class Tiempo {
    private int hora;
    private int minutos;
    private int segundos;
    private long valorTotal;
    
    public Tiempo(long valor) {
        this.valorTotal = valor;
        conversor();
    }

    private void conversor() {
        float num = valorTotal/1000000;
        hora=(int)num/3600;
        minutos=(int)(num-(3600*hora))/60;
        segundos=(int)num-((hora*3600)+(minutos*60));
    }
    
     public Tiempo() {

    }

    /**
     * @return the hora
     */
    public int getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /**
     * @return the minutos
     */
    public int getMinutos() {
        return minutos;
    }

    /**
     * @param minutos the minutos to set
     */
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    /**
     * @return the segundos
     */
    public int getSegundos() {
        return segundos;
    }

    /**
     * @param segundos the segundos to set
     */
    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    /**
     * @return the valorTotal
     */
    public long getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(long valorTotal) {
        this.valorTotal = valorTotal;
        conversor();
    }
    
    @Override
    public String toString() {
        String sal="";
        String min="";
        String seg="";
        if(minutos<10) {
            min="0"+minutos;
        }else{
            min=""+minutos;
        }
        if(segundos<10) {
            seg="0"+segundos;
        }else{
            seg=""+segundos;
        }
        sal=min+":"+seg;
        //System.err.println(sal);
        return sal;
    }
    
    
}
