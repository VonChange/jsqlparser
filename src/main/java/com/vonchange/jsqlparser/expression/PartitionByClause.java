/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.vonchange.jsqlparser.expression;

import com.vonchange.jsqlparser.expression.operators.relational.ExpressionList;
import com.vonchange.jsqlparser.statement.select.PlainSelect;

public class PartitionByClause {
    ExpressionList partitionExpressionList;
    boolean brackets = false;

    public ExpressionList getPartitionExpressionList() {
        return partitionExpressionList;
    }
    
    public void setPartitionExpressionList(ExpressionList partitionExpressionList) {
        setPartitionExpressionList(partitionExpressionList, false);
    }

    public void setPartitionExpressionList(ExpressionList partitionExpressionList, boolean brackets) {
        this.partitionExpressionList = partitionExpressionList;
        this.brackets = brackets;
    }

    void toStringPartitionBy(StringBuilder b) {
        if (partitionExpressionList != null && !partitionExpressionList.getExpressions().isEmpty()) {
            b.append("PARTITION BY ");
            b.append(PlainSelect.
                    getStringList(partitionExpressionList.getExpressions(), true, brackets));
            b.append(" ");
        }
    }
    
    public boolean isBrackets() {
        return brackets;
    }
}
