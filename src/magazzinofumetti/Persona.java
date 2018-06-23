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
class Persona {
    private int id;
    private String nome;
    private String cognome;
    private String denominazione;
    private String indirizzo;
    private String email;
    private String telefono;
    private String venditore;
    private String acquirente;
    
    public int getId(){
        return id;
    }
    
    String getNome() {
        return nome;
    }
    
    public String getCognome() {
        return cognome;
    }

    public String getDenominazione() {
        return denominazione;
    }
    
    public String getIndirizzo() {
        return indirizzo;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public String getVenditore() {
        return venditore;
    }
    
    public String getAcquirente() {
        return acquirente;
    }
    
    public String toString(){
        return nome/*+" "+cognome+" ("+denominazione+")"*/;
    }
    
    public Persona(){
        this.nome="";
        this.id=-1;
        this.cognome="";
        this.denominazione="";
        this.indirizzo="";
        this.email="";
        this.telefono="";
        this.venditore="N";
        this.acquirente="N";
    }
    
    public Persona(String nome,String cognome, int id,String denominazione,String indirizzo, String email, String telefono, String venditore, String acquirente){
        this.nome=nome;
        this.cognome=cognome;
        this.id=id;
        this.denominazione=denominazione;
        this.indirizzo=indirizzo;
        this.email=email;
        this.telefono=telefono;
        this.venditore=venditore;
        this.acquirente=acquirente;
    }
    
}
