package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.context.ExpressionNode;
import alibaba.coding.queryparser.expression.element.GroupByFieldExpression;
import alibaba.coding.queryparser.expression.element.GroupFuncFieldExpression;
import alibaba.coding.queryparser.expression.querymodel.GroupBy;
import alibaba.coding.queryparser.context.QueryStatementContext;

public class GroupByHandler implements QueryStatementHandler {

    private final GroupBy groupBy;

    public GroupByHandler(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    @Override
    public void executeStatement(QueryStatementContext context) {
        // 解析 GroupBy 语句段表达式, 构造两个链表
        ExpressionNode expressionNode = groupBy.statementExpression();
        GroupFieldExpressionNode fieldExpNodeHead = new GroupFieldExpressionNode();
        GroupFuncExecFieldExpressionNode funcExecFieldExpNodeHead = new GroupFuncExecFieldExpressionNode();
        GroupFuncExecFieldExpressionNode tFuncExecNode = funcExecFieldExpNodeHead;

        while (expressionNode != null) {
            if (expressionNode.getCurrentNode() instanceof GroupByFieldExpression) {
                GroupFieldExpressionNode groupFieldExpressionNode = new GroupFieldExpressionNode();
                groupFieldExpressionNode.current = (GroupByFieldExpression) expressionNode.getCurrentNode();
                fieldExpNodeHead.next = groupFieldExpressionNode;
            } else {
                GroupFuncExecFieldExpressionNode funcExecFieldExpressionNode = new GroupFuncExecFieldExpressionNode();
                funcExecFieldExpressionNode.current = (GroupFuncFieldExpression) expressionNode.getCurrentNode();
                tFuncExecNode.next = funcExecFieldExpressionNode;
            }
            expressionNode = expressionNode.getNextNode();
         }
    }

        /**
         * 分组字段链表节点
         */
        private static class GroupFieldExpressionNode {
            /**
             * 当前表达式
             */
            GroupByFieldExpression current;
            /**
             * 下一个节点
             */
            GroupFieldExpressionNode next;
        }

        /**
         * 分组字段链表节点
         */
        private static class GroupFuncExecFieldExpressionNode {
            /**
             * 当前表达式
             */
            GroupFuncFieldExpression current;
            /**
             * 下一个节点
             */
            GroupFuncExecFieldExpressionNode next;
        }

}