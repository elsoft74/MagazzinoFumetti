/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magazzinofumetti;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author elsoft
 */
public class Albo implements Comparable{
    private int id;
    private String editore;
    private String descrizione;
    private String numero;
    private float costo; // Costo d'acquisto
    private float prezzo; // Prezzo di vendita
    private Boolean venduto;
    private int lottoAcquisto;
    private int lottoVendita;
    private String tipologia;

    Albo(String editore,String tipologia,String text,String numero, float costo, float prezzo) {
        id=-1;
        this.editore=editore;
        this.tipologia=tipologia;
        descrizione=text;
        this.numero=numero;
        this.costo=costo;
        this.prezzo=prezzo;
        venduto=false;
    }
    
    public float getCosto(){
        return costo;
    }
    
    public float getPrezzo(){
        return prezzo;
    }
    
    @Override
    public String toString(){
        String out=descrizione;
//        out+=(venduto==true)?"_Venduto:"+prezzo:"";
        return out;
    }
    
    public String getEditore(){
        return editore;
    }
    
    public void setEditore(String val){
        editore=val;
    }
    
    @Override
    public int compareTo(Object o) {
        Albo albo=(Albo)o;
        int val=0;
        if (id==albo.getId()){
            val=editore.compareTo(albo.getEditore());
            if (val==0){
                val=tipologia.compareTo(albo.getTipologia());
                if (val==0){
                    val=descrizione.compareTo(albo.getDescrizione());
                    if (val==0){
                        val=numero.compareTo(albo.getNumero());
                            if (val==0){
                                val=(costo==albo.getCosto())?0:(costo>albo.getCosto())?1:-1;
                                if (val==0){
                                    val=(prezzo==albo.getPrezzo())?0:(prezzo>albo.getPrezzo())?1:-1;
                                    if (val==0){
                                        val=(venduto==albo.getVenduto())?0:1;
                                    }
                            }
                        }
                    }
                }
            }
        }
        return val;
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getId());
        builder.append(getEditore());
        builder.append(getTipologia());
        builder.append(getDescrizione());
        builder.append(getCosto());
        builder.append(getPrezzo());
        builder.append(getVenduto());
        return builder.hashCode();
    }
       
    @Override
    public boolean equals(Object o) {
            if (!(o instanceof Albo))
                    return false;
            Albo albo  = (Albo) o;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(id, albo.getId());
            builder.append(tipologia,albo.getTipologia());
            builder.append(editore, albo.getEditore());
            builder.append(descrizione,albo.getDescrizione());
            builder.append(numero,albo.getNumero());
            builder.append(costo,albo.getCosto());
            builder.append(prezzo,albo.getPrezzo());
            return builder.isEquals();
   }

    private int getId() {
        return id;
    }

    String getDescrizione() {
        return descrizione;
    }

    private boolean getVenduto() {
        return venduto;
    }

    String getNumero() {
        return numero;
    }
    
    String getTipologia() {
    return tipologia;
    }
    
    void setTipologia(String value){
        tipologia=value;
    }

    void setCosto(float costoAlbo) {
        costo=costoAlbo;
    }
}
