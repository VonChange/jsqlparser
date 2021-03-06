/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.vonchange.jsqlparser.statement.update;

import java.util.List;

import com.vonchange.jsqlparser.expression.Expression;
import com.vonchange.jsqlparser.schema.Column;
import com.vonchange.jsqlparser.schema.Table;
import com.vonchange.jsqlparser.statement.Statement;
import com.vonchange.jsqlparser.statement.StatementVisitor;
import com.vonchange.jsqlparser.statement.select.FromItem;
import com.vonchange.jsqlparser.statement.select.Join;
import com.vonchange.jsqlparser.statement.select.PlainSelect;
import com.vonchange.jsqlparser.statement.select.Select;
import com.vonchange.jsqlparser.statement.select.OrderByElement;
import com.vonchange.jsqlparser.statement.select.Limit;
import com.vonchange.jsqlparser.statement.select.SelectExpressionItem;

public class Update implements Statement {

    private Table table;
    private Expression where;
    private List<Column> columns;
    private List<Expression> expressions;
    private FromItem fromItem;
    private List<Join> joins;
    private List<Join> startJoins;
    private Select select;
    private boolean useColumnsBrackets = true;
    private boolean useSelect = false;
    private List<OrderByElement> orderByElements;
    private Limit limit;
    private boolean returningAllColumns = false;
    private List<SelectExpressionItem> returningExpressionList = null;

    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    public Table getTable() {
        return table;
    }

    public Expression getWhere() {
        return where;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setWhere(Expression expression) {
        where = expression;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setColumns(List<Column> list) {
        columns = list;
    }

    public void setExpressions(List<Expression> list) {
        expressions = list;
    }

    public FromItem getFromItem() {
        return fromItem;
    }

    public void setFromItem(FromItem fromItem) {
        this.fromItem = fromItem;
    }

    public List<Join> getJoins() {
        return joins;
    }

    public void setJoins(List<Join> joins) {
        this.joins = joins;
    }

    public List<Join> getStartJoins() {
        return startJoins;
    }

    public void setStartJoins(List<Join> startJoins) {
        this.startJoins = startJoins;
    }

    public Select getSelect() {
        return select;
    }

    public void setSelect(Select select) {
        this.select = select;
    }

    public boolean isUseColumnsBrackets() {
        return useColumnsBrackets;
    }

    public void setUseColumnsBrackets(boolean useColumnsBrackets) {
        this.useColumnsBrackets = useColumnsBrackets;
    }

    public boolean isUseSelect() {
        return useSelect;
    }

    public void setUseSelect(boolean useSelect) {
        this.useSelect = useSelect;
    }

    public void setOrderByElements(List<OrderByElement> orderByElements) {
        this.orderByElements = orderByElements;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public List<OrderByElement> getOrderByElements() {
        return orderByElements;
    }

    public Limit getLimit() {
        return limit;
    }

    public boolean isReturningAllColumns() {
        return returningAllColumns;
    }

    public void setReturningAllColumns(boolean returningAllColumns) {
        this.returningAllColumns = returningAllColumns;
    }

    public List<SelectExpressionItem> getReturningExpressionList() {
        return returningExpressionList;
    }

    public void setReturningExpressionList(List<SelectExpressionItem> returningExpressionList) {
        this.returningExpressionList = returningExpressionList;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("UPDATE ");
        b.append(table);
        if (startJoins != null) {
                for (Join join : startJoins) {
                    if (join.isSimple()) {
                        b.append(", ").append(join);
                    } else {
                        b.append(" ").append(join);
                    }
                }
            }
        b.append(" SET ");

        if (!useSelect) {
            for (int i = 0; i < getColumns().size(); i++) {
                if (i != 0) {
                    b.append(", ");
                }
                b.append(columns.get(i)).append(" = ");
                b.append(expressions.get(i));
            }
        } else {
            if (useColumnsBrackets) {
                b.append("(");
            }
            for (int i = 0; i < getColumns().size(); i++) {
                if (i != 0) {
                    b.append(", ");
                }
                b.append(columns.get(i));
            }
            if (useColumnsBrackets) {
                b.append(")");
            }
            b.append(" = ");
            b.append("(").append(select).append(")");
        }

        if (fromItem != null) {
            b.append(" FROM ").append(fromItem);
            if (joins != null) {
                for (Join join : joins) {
                    if (join.isSimple()) {
                        b.append(", ").append(join);
                    } else {
                        b.append(" ").append(join);
                    }
                }
            }
        }

        if (where != null) {
            b.append(" WHERE ");
            b.append(where);
        }
        if (orderByElements != null) {
            b.append(PlainSelect.orderByToString(orderByElements));
        }
        if (limit != null) {
            b.append(limit);
        }

        if (isReturningAllColumns()) {
            b.append(" RETURNING *");
        } else if (getReturningExpressionList() != null) {
            b.append(" RETURNING ").append(PlainSelect.
                    getStringList(getReturningExpressionList(), true, false));
        }

        return b.toString();
    }
}
