package alibaba.coding.queryparser.expression.querymodel;

import alibaba.coding.queryparser.context.ExpressionNode;
import alibaba.coding.queryparser.metadata.QueryStatement;
import alibaba.coding.queryparser.metadata.QueryStatementType;
import cn.hutool.core.lang.Assert;

public class Limit implements QueryStatement {

    private final ExpressionNode expressionNode;

    private final QueryStatementType statementType = QueryStatementType.LIMIT;

    public Limit(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    @Override
    public QueryStatementType getStatementType() {
        return statementType;
    }

    @Override
    public ExpressionNode statementExpression() {
        return expressionNode;
    }

    @Override
    public void validate() {
        Assert.notNull(expressionNode, "Limit 表达式信息不能为空");
        Assert.isNull(expressionNode.getNextNode(), "Limit 只能有一个表达式信息");
    }
}