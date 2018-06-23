/*
 * Copyright (C) 2018 elsoft
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
public class ElencoPersoneTableModel extends AbstractTableModel{
    private final String[] columnNames = { "Nome","Cognome","Descrizione"};
    private final Object[][] tableData;
    
    public ElencoPersoneTableModel(Iterator<Persona> iter) {
        int i=0; 
        List<Object[]> persone=new ArrayList<>();
        while (iter.hasNext()){
            Persona tmp=iter.next();
            Object[] tmpO=new Object[3];
            tmpO[0]=tmp;
            tmpO[1]=tmp.getCognome();
            tmpO[2]=tmp.getDenominazione();
            persone.add(tmpO);
        }
        tableData=new Object[persone.size()][]; 
        persone.toArray(tableData);
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
