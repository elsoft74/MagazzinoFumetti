/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magazzinofumetti;

/**
 *
 * @author elsoft
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author elsoft
 */
public abstract class Lotto {
    private int id;
    TreeSet<Albo> albi;
    String descrizione;
    float totale;
    String note;
    Date data;
    
    @Override
    public abstract String toString();
    public abstract void addAlbo(Albo tmp);
    public abstract int getNumAlbi();
    public abstract void svuotaAlbi();
    public abstract TreeSet<Albo> getAlbi();
    
    
    public String getDescrizione(){
        return descrizione;
    }
    public Date getData(){
        return data;
    }
    
    public void setDescrizione (String val){
        descrizione=val;
    }
    
    public void setTotale(float val){
        totale=val;
    }
    
    public void setData(Date data){
        this.data=data;
    }
    
    public void setNote(String val){
        note=val;
    }
    
    public String getNote(){
        return note;
    }
    
    float getTotale() {
        return totale;
    }   
    public void assegnaCostoAlbi(float costoAlbo){
        Iterator<Albo> iter=albi.iterator();
        while (iter.hasNext()){
            Albo tmpAlbo=iter.next();
            //albi.remove(tmpAlbo);
            tmpAlbo.setCosto(costoAlbo);
            //albi.add(tmpAlbo);
        }
    }
    
    public void calcolaTotale(){
        Iterator<Albo> iter=albi.iterator();
        while (iter.hasNext()){
            Albo tmpAlbo=iter.next();
            totale+=tmpAlbo.getCosto();
        }
    }

    
    void removeAlbo(Albo tmpAlbo) {
        albi.remove(tmpAlbo);
    }
}

