/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magazzinofumetti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author elsoft
 */
public class ElencoAlbiModel extends AbstractTableModel{
    private final String[] columnNames = { "Descrizione","Costo","Prezzo"};
    private final Object[][] tableData;
    
    public ElencoAlbiModel(Iterator<Albo> iter) {
        int i=0; 
        List<Object[]> albi=new ArrayList<>();
        while (iter.hasNext()){
            Albo tmp=iter.next();
            Object[] tmpO=new Object[3];
            tmpO[0]=tmp;
            tmpO[1]=tmp.getCosto();
            tmpO[2]=tmp.getPrezzo();
            albi.add(tmpO);
        }
        tableData=new Object[albi.size()][]; 
        albi.toArray(tableData);
    }
    
    @Override
    public int getRowCount() {
        return tableData.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        return tableData[r][c];
    }
    
    @Override
    public String getColumnName(int col) {
      return columnNames[col];
    }
    
}
