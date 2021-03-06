/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.vonchange.jsqlparser.statement.alter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vonchange.jsqlparser.schema.Table;
import com.vonchange.jsqlparser.statement.Statement;
import com.vonchange.jsqlparser.statement.StatementVisitor;

public class Alter implements Statement {

    private Table table;

    private List<AlterExpression> alterExpressions;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void addAlterExpression(AlterExpression alterExpression) {
        if (alterExpressions == null) {
            alterExpressions = new ArrayList<AlterExpression>();
        }
        alterExpressions.add(alterExpression);
    }

    public List<AlterExpression> getAlterExpressions() {
        return alterExpressions;
    }

    public void setAlterExpressions(List<AlterExpression> alterExpressions) {
        this.alterExpressions = alterExpressions;
    }

    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    @Override
    public String toString() {

        StringBuilder b = new StringBuilder();
        b.append("ALTER TABLE ").append(table.getFullyQualifiedName()).append(" ");

        Iterator<AlterExpression> altIter = alterExpressions.iterator();

        while (altIter.hasNext()) {
            b.append(altIter.next().toString());

            // Need to append whitespace after each ADD or DROP statement
            // but not the last one
            if (altIter.hasNext()) {
                b.append(", ");
            }
        }

        return b.toString();
    }

}
