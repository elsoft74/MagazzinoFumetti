/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magazzinofumetti;

import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author elsoft
 */
public class LottoAcquisto extends Lotto {
    private int id;
    //private TreeSet<Albo> albiVenduti; // In albi, ereditato dalla classe Lotto memorizziamo i soli albi invenduti
    private Persona venditore;
    private float ricavo;
    
    public LottoAcquisto(){
        albi=new TreeSet();
        //albiVenduti=new TreeSet();
    }

    LottoAcquisto(int id, String descrizione, Persona venditore, float costo, float ricavo, Date data, String note) {
        this();
        this.id=id;
        this.descrizione=descrizione;
        this.venditore=venditore;
        this.totale=costo;
        this.ricavo=ricavo;
        this.data=data;
        this.note=note;
    }
    
    @Override
    public String toString(){
        return "Lotto:"+id+"-"+descrizione;
    }
    
    @Override
    public void addAlbo(Albo tmp){
        albi.add(tmp);
    }
   
    public int getNumAlbiTotali(){
        return albi.size()/*+albiVenduti.size()*/;
    }
    
//    public int getNumAlbiVenduti(){
//        return albiVenduti.size();
//    }
    
    public int getNumAlbiDisponibili(){
        return albi.size();
    }

    @Override
    public void svuotaAlbi() {
        albi.clear();
        //albiVenduti.clear();
    }
    
    public TreeSet<Albo> getAlbiDisponibili(){
        return albi;
    }

    @Override
    public int getNumAlbi() {
        return albi.size();
    }

    @Override
    public TreeSet<Albo> getAlbi() {
        return albi;
    }

    public int getVenditoreId() {
        return venditore.getId();
    }

    void setVenditore(Persona persona) {
        this.venditore=persona;
    }

}
