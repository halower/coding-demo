package alibaba.coding.queryparser.metadata;

import alibaba.coding.queryparser.contract.QuerySyntaxChecker;
import alibaba.coding.queryparser.context.ExpressionNode;


public interface QueryStatement extends QuerySyntaxChecker {

    /**
     * 查询语句段类型
     */
    QueryStatementType getStatementType();

    /**
     * 查询语句段表达式链表
     *
     */
    ExpressionNode statementExpression();
}
