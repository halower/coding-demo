package alibaba.coding.queryparser.expression.querymodel;

import alibaba.coding.queryparser.context.ExpressionNode;
import alibaba.coding.queryparser.metadata.QueryStatement;
import alibaba.coding.queryparser.metadata.QueryStatementType;
import cn.hutool.core.lang.Assert;

public class OrderBy  implements QueryStatement {

    private final ExpressionNode expressionNode;

    private final QueryStatementType statementType = QueryStatementType.ORDER_BY;

    public OrderBy(ExpressionNode segmentExpression) {
        this.expressionNode = segmentExpression;
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
        Assert.notNull(expressionNode, "Order By 表达式信息不能为空");
    }
}
