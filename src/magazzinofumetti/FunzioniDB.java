/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magazzinofumetti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elsoft
 */
public class FunzioniDB {
    static Connection c = null;
    
    private static void connetti(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dati.db");
            inizializza();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    public static boolean inserisciLottoAcquisto(LottoAcquisto lotto){
        connetti();
        boolean out=false;
        
        try {
            if (!c.isClosed()){
                String query="BEGIN TRANSACTION";
                PreparedStatement stmt=c.prepareStatement(query);
                stmt.execute();
                query="INSERT INTO `lotti_acquisto` (descrizione,venditore,costo,data,note) VALUES (?,?,?,?,?)";
                stmt=c.prepareStatement(query);
                stmt.setString(1, lotto.getDescrizione());
                stmt.setInt(2, lotto.getVenditoreId());
                stmt.setFloat(3, lotto.getTotale());
                stmt.setDate(4, new java.sql.Date(lotto.getData().getTime()));
                stmt.setString(5, lotto.getNote());
                stmt.execute();
                
                query="SELECT seq FROM `sqlite_sequence` WHERE name=\"lotti_acquisto\"";
                stmt=c.prepareStatement(query);
                ResultSet rr=stmt.executeQuery();
                int lottoId=-1;
                while (rr.next()){
                    lottoId=rr.getInt("seq");
                }
                
                Iterator albi=lotto.getAlbi().iterator();
                while (albi.hasNext()){
                    Albo albo=(Albo)albi.next();
                    query="INSERT INTO albi (editore,tipologia,descrizione,numero,costo,prezzo,lottoAcquisto) VALUES (?,?,?,?,?,?,?)";
                    stmt=c.prepareStatement(query);
                    stmt.setString(1,albo.getEditore());
                    stmt.setString(2,albo.getTipologia());
                    stmt.setString(3, albo.getDescrizione());
                    stmt.setString(4,albo.getNumero());
                    stmt.setFloat(5,albo.getCosto());
                    stmt.setFloat(6,albo.getPrezzo());
                    stmt.setInt(7,lottoId);
                    stmt.execute();
                }
                
                query="COMMIT";
                stmt=c.prepareStatement(query);
                stmt.execute();
                stmt.close();
                chiudiConnessione();
                out=true;
            }
        } catch (SQLException ex) {
            out=false;
            ex.printStackTrace();
        }
        chiudiConnessione();
        return out;
    }

    private static void inizializza() {
        try {
            String query="CREATE TABLE IF NOT EXISTS `persone` (\n" +
                    "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "	`nome`	TEXT,\n" +
                    "	`cognome`	TEXT,\n" +
                    "	`denominazione`	TEXT,\n" +
                    "	`indirizzo`	TEXT,\n" +
                    "	`telefono`	TEXT,\n" +
                    "	`email`	TEXT,\n" +
                    "	`venditore`	TEXT,\n" +
                    "	`acquirente`	TEXT\n" +
                    ");\n";
            PreparedStatement stmt=c.prepareStatement(query);
            stmt.execute();
            
            query="CREATE TABLE IF NOT EXISTS `lotti_acquisto` (\n"+
                    "   `id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "	`descrizione`	TEXT,\n" +
                    "	`venditore`	INTEGER NOT NULL,\n" +
                    "	`venduti`	INTEGER DEFAULT 0,\n" +
                    "	`costo`	REAL DEFAULT 0,\n" +
                    "	`ricavo`	REAL DEFAULT 0,\n" +
                    "	`data`	TEXT,\n" +
                    "	`note`	TEXT)";
            stmt=c.prepareStatement(query);
            stmt.execute();
            
            query="CREATE TABLE IF NOT EXISTS `lotti_vendita` (\n"+
                    "   `id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "	`descrizione`	TEXT,\n" +
                    "	`acquirente`	INTEGER NOT NULL,\n" +
                    "	`venduti`	INTEGER DEFAULT 0,\n" +
                    "	`data`	TEXT,\n" +
                    "	`note`	TEXT)";
            stmt=c.prepareStatement(query);
            stmt.execute();
            
            query="CREATE TABLE IF NOT EXISTS `albi` ("+
                "`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"+
                "`editore`	TEXT,"+
                "`tipologia`	TEXT,"+
                "`descrizione`	TEXT,"+
                "`numero`	TEXT,"+
                "`costo`	REAL,"+
                "`prezzo`	REAL,"+
                "`venduto`	INTEGER DEFAULT 0,"+
                "`lottoVendita`	INTEGER,"+
                "`lottoAcquisto`	INTEGER)";
            stmt=c.prepareStatement(query);
            stmt.execute();
           
        } catch (SQLException ex) {
            Logger.getLogger(FunzioniDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void chiudiConnessione() {
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(FunzioniDB.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    static HashSet<LottoAcquisto> leggiLotti() {
        connetti();
        HashSet<LottoAcquisto> out=new HashSet();
        try {
            if (!c.isClosed()){
                String query="SELECT * FROM `lotti_acquisto`";
                PreparedStatement stmt=c.prepareStatement(query);
                ResultSet rr=stmt.executeQuery();
                while (rr.next()){
                    int id=rr.getInt("id");
                    String descrizione=rr.getString("descrizione");
                    int venditoreId=rr.getInt("venditore");
                    float costo=rr.getFloat("costo");
                    float ricavo=rr.getFloat("ricavo");
                    long data=rr.getLong("data");
                    String note=rr.getString("note");
                    Persona venditore=leggiVenditore(venditoreId); 
                    LottoAcquisto lotto=new LottoAcquisto(id,descrizione,venditore,costo,ricavo,new java.util.Date(data),note);
                    out.add(lotto);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FunzioniDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        chiudiConnessione();
        return out;
    }

    private static Persona leggiVenditore(int venditoreId) {
        return new Persona();
    }
    
    public static HashSet leggiVenditori(){
        HashSet<LottoAcquisto> out=leggiPersone("V");
        return out;
    }
    
    public static HashSet leggiAcquirenti(){
        HashSet<LottoAcquisto> out=leggiPersone("A");
        return out;
    }
    
    public static HashSet leggiPersone(){
        return leggiPersone("");
    }
    
    private static HashSet leggiPersone(String par){
        connetti();
        HashSet<Persona> out=new HashSet();
        try {
            if (!c.isClosed()){
                String query="SELECT * FROM `persone`";
                PreparedStatement stmt=null;
                if (par.equalsIgnoreCase("V")){
                    query+=" WHERE `venditore`=?";
                    stmt=c.prepareStatement(query);
                    stmt.setString(1, "S");
                    } else if (par.equalsIgnoreCase("A")){
                        query+=" WHERE `acquirente`=?";
                        stmt=c.prepareStatement(query);
                        stmt.setString(1, "S");
                    } else stmt=c.prepareStatement(query);
                query+=" ORDER BY nome,cognome";
                ResultSet rr=stmt.executeQuery();
                while (rr.next()){
                    int id=rr.getInt("id");
                    String nome=rr.getString("nome");
                    String cognome=rr.getString("cognome");
                    String denominazione=rr.getString("denominazione");
                    String indirizzo=rr.getString("indirizzo");
                    String telefono=rr.getString("telefono");
                    String email=rr.getString("email");
                    String acquirente=rr.getString("acquirente");
                    String venditore=rr.getString("venditore");
                    
                    Persona persona=new Persona(nome,cognome,id,denominazione,indirizzo,email,telefono,venditore,acquirente);
                    out.add(persona);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FunzioniDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        chiudiConnessione();
        return out;
    }

    static String aggiornaPersona(Persona pers) {
        connetti();
        String out="Errore durante l'aggiornamento";
        try {
            if (!c.isClosed()){
                String query="UPDATE `persone` SET nome=?,"+
                        "cognome=?, denominazione=?, indirizzo=?,"+
                        "telefono=?, email=?, venditore=?, acquirente=?"+
                        "WHERE id=?";
                PreparedStatement stmt=c.prepareStatement(query);
                stmt=c.prepareStatement(query);
                stmt.setString(1, pers.getNome());
                stmt.setString(2, pers.getCognome());
                stmt.setString(3, pers.getDenominazione());
                stmt.setString(4, pers.getIndirizzo());
                stmt.setString(5, pers.getTelefono());
                stmt.setString(6, pers.getEmail());
                stmt.setString(7, pers.getVenditore());
                stmt.setString(8, pers.getAcquirente());
                stmt.setInt(9, pers.getId());
                stmt.execute();
                stmt.close();
                chiudiConnessione();
                out="";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        chiudiConnessione();
        return out;
    }

    static String inserisciPersona(Persona pers) {
        connetti();
        String out="Errore durante l'inserimento";
        try {
            if (!c.isClosed()){
                String query="INSERT INTO `persone` "+
                        "(nome,cognome,denominazione,indirizzo,telefono,email,venditore,acquirente)"+
                        " VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement stmt=c.prepareStatement(query);
                stmt=c.prepareStatement(query);
                stmt.setString(1, pers.getNome());
                stmt.setString(2, pers.getCognome());
                stmt.setString(3, pers.getDenominazione());
                stmt.setString(4, pers.getIndirizzo());
                stmt.setString(5, pers.getTelefono());
                stmt.setString(6, pers.getEmail());
                stmt.setString(7, pers.getVenditore());
                stmt.setString(8, pers.getAcquirente());
                stmt.execute();
                stmt.close();
                chiudiConnessione();
                out="";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        chiudiConnessione();
        return out;
    }

    static void rimuoviPersona(int selId) {
        connetti();
        
        try {
            if (!c.isClosed()){
                String query="BEGIN TRANSACTION";
                PreparedStatement stmt=c.prepareStatement(query);
                stmt.execute();
                
                //Si rendono anonimi i dati associati alla persona cancellata
                //Non vengono però rimossi i dettagli sui lotti acquistati o venduti
                
                query="UPDATE `lotti_acquisto` SET venditore=-1 WHERE venditore=?";
                stmt=c.prepareStatement(query);
                stmt.setInt(1, selId);
                stmt.execute();

                query="UPDATE `lotti_vendita` SET acquirente=-1 WHERE acquirente=?";
                stmt=c.prepareStatement(query);
                stmt.setInt(1, selId);
                stmt.execute();   
                
                query="DELETE FROM `persone` WHERE id=?";
                stmt=c.prepareStatement(query);
                stmt.setInt(1, selId);
                stmt.execute();                
                
                query="COMMIT";
                stmt=c.prepareStatement(query);
                stmt.execute();
                stmt.close();
                chiudiConnessione();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        chiudiConnessione();
    }
}
