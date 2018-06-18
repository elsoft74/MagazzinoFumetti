/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magazzinofumetti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author elsoft
 */
public class ElencoLottiTableModel extends AbstractTableModel {
    
    private final String[] columnNames = {""};
    private final Object[][] tableData;

    public ElencoLottiTableModel(HashSet<LottoAcquisto> lottiAcquistati) {
        int i=0; 
        List<Object[]> lotti=new ArrayList<>();
        Iterator iter=lottiAcquistati.iterator();
        while (iter.hasNext()){
            LottoAcquisto tmp=(LottoAcquisto)iter.next();
            Object[] tmpO=new Object[1];
            tmpO[0]=tmp;
            lotti.add(tmpO);
        }
        tableData=new Object[lotti.size()][]; 
        lotti.toArray(tableData);
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
