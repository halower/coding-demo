package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.context.ExpressionNode;
import alibaba.coding.queryparser.context.FilterOpResultLogicJoinHandlerContainer;
import alibaba.coding.queryparser.context.ObjectFieldValueFilterOpInvokerContainer;
import alibaba.coding.queryparser.context.QueryStatementContext;
import alibaba.coding.queryparser.expression.element.ObjectFieldFilterOpExpression;
import alibaba.coding.queryparser.expression.querymodel.Where;
import alibaba.coding.queryparser.metadata.ExpressionOpType;
import alibaba.coding.queryparser.metadata.QueryStatement;
import cn.hutool.core.collection.CollectionUtil;

import java.util.Collections;
import java.util.List;

public class WhereHandler extends AbstractQueryStatementHandlerInvokeTemplate {

    
        private final Where where;


        public WhereHandler(Where where) {
            this.where = where;
        }



        @Override
        protected void invokeExecuteSegment(QueryStatementContext executeContext) {
            List<Object> outboundDataSet;
            if (CollectionUtil.isEmpty(executeContext.getInboundDataSet())) {
                outboundDataSet = Collections.emptyList();
            } else {
                ExpressionNode node = where.statementExpression();

                List<Object> inboundDataSet = executeContext.getInboundDataSet();
                ObjectFieldValueFilterOpInvokerContainer opInvokerContainer = ObjectFieldValueFilterOpInvokerContainer.getInstance();
                FilterOpResultLogicJoinHandlerContainer joinHandlerContainer = FilterOpResultLogicJoinHandlerContainer.getInstance();

                List<Object> prevResult = Collections.emptyList();
                ExpressionOpType prevLogicOpType = ExpressionOpType.OR;

                List<Object> tempResult = null;

                while (node != null) {
                    ObjectFieldFilterOpExpression expression = (ObjectFieldFilterOpExpression) node.getCurrentNode();
                    List<Object> currentResult = opInvokerContainer.acquireOpInvoker(expression.getOpType()).invokeFilter(inboundDataSet, expression);
                    tempResult = joinHandlerContainer.acquireOpInvoker(prevLogicOpType).invokeJoin(prevResult, currentResult);
                    prevLogicOpType = node.getExpressionOpType();
                    prevResult = tempResult;
                    node = node.getNextNode();
                }

                outboundDataSet = tempResult;
            }

            executeContext.setOutboundDataSet(outboundDataSet);
        } 
        
    @Override
    protected QueryStatement acquireCurrentQueryStatement() {
        return where;
    }

}
