package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class TablesNamesFinderExt extends TablesNamesFinder {
    List<String> mySelectTableList = new ArrayList<>();
    List<String> mySelectTableAlliasList = new ArrayList<>();
    boolean inSelect = true;
    /**
     * To solve JSqlParsers Problem in getting tablenames from subselect using an Insert
     * statement.
     *
     * @param insert
     * @return
     */
    public List<String> getTableList(Insert insert) {
        List<String> list = super.getTableList(insert);
        if (insert.getSelect() != null) {
            insert.getSelect().getSelectBody().accept(this);
        }
        return list;
    }

    @Override
    public void visit(SubSelect subSelect) {
        inSelect = true;
        super.visit(subSelect);
    }

    @Override
    public void visit(Table tableName) {
        super.visit(tableName); 
        if (inSelect && !mySelectTableList.contains(tableName.getFullyQualifiedName())) {
            mySelectTableList.add(tableName.getFullyQualifiedName());
            if(tableName.getAlias() !=null) {
            	mySelectTableAlliasList.add(tableName.getAlias().getName());
            }
            else
            	mySelectTableAlliasList.add(null);
            
        }
    }

    public List<String> getSelectTableList() {
        return mySelectTableList;
    }
    
    public List<String> getSelectTableAlliasList() {
        return mySelectTableAlliasList;
    }

}
