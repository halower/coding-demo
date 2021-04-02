package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.context.*;
import alibaba.coding.queryparser.expression.element.ObjectFieldFilterOpExpression;
import alibaba.coding.queryparser.expression.querymodel.Where;
import alibaba.coding.queryparser.metadata.ExpressionOpType;
import alibaba.coding.queryparser.metadata.QueryStatement;
import cn.hutool.core.collection.CollectionUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class WhereHandler extends AbstractQueryStatementHandlerInvokeTemplate {

        /**
         * where 语句段
         */
        private final Where where;


        private final ThreadPoolExecutor whereExpressionExecuteThreadExecutor;

        public WhereHandler(Where where, ThreadPoolExecutor whereExpressionExecuteThreadExecutor) {
            this.where = where;
            this.whereExpressionExecuteThreadExecutor = whereExpressionExecuteThreadExecutor;
        }



        @Override
        protected void invokeExecuteSegment(QueryStatementContext executeContext) {

            if (whereExpressionExecuteThreadExecutor == null) {
                this.invokeExecuteSegmentSerial(executeContext);
            } else {
                this.invokeExecuteSegmentParallel(executeContext);
            }
        }

       protected void invokeExecuteSegmentSerial(QueryStatementContext executeContext) {
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
                    // 和上一个结果合并
                    tempResult = joinHandlerContainer.acquireOpInvoker(prevLogicOpType).invokeJoin(prevResult, currentResult);
                    prevLogicOpType = node.getExpressionOpType();
                    prevResult = tempResult;
                    node = node.getNextNode();
                }

                outboundDataSet = tempResult;
            }

            executeContext.setOutboundDataSet(outboundDataSet);

        }

        private void invokeExecuteSegmentParallel(QueryStatementContext executeContext) {
            List<Object> outboundDataSet;
            if (CollectionUtil.isEmpty(executeContext.getInboundDataSet())) {
                outboundDataSet = Collections.emptyList();
            } else {
                ExpressionNode node = where.statementExpression();
                Map<ExpressionNode, Future<List<Object>>> futureTaskMap = new HashMap<>();
                List<Object> inboundDataSet = executeContext.getInboundDataSet();
                ObjectFieldValueFilterOpInvokerContainer opInvokerContainer = ObjectFieldValueFilterOpInvokerContainer.getInstance();
                while (node != null) {
                    ObjectFieldFilterOpExpression expression = (ObjectFieldFilterOpExpression) node.getCurrentNode();
                    Callable<List<Object>> callable = () -> opInvokerContainer.acquireOpInvoker(expression.getOpType()).invokeFilter(inboundDataSet, expression);
                    futureTaskMap.put(node, whereExpressionExecuteThreadExecutor.submit(callable));
                    node = node.getNextNode();
                }

                node = where.statementExpression();

                ObjectFieldValueFilterOpResultNode resultNodeHead = new ObjectFieldValueFilterOpResultNode(null);
                ObjectFieldValueFilterOpResultNode tempNode = resultNodeHead;
                while (node != null) {
                    Future<List<Object>> future = futureTaskMap.get(node);
                    try {
                        List<Object> result = future.get();
                        ObjectFieldValueFilterOpResultNode currentResultNode = new ObjectFieldValueFilterOpResultNode(result);
                        currentResultNode.setConcatLogicOpType(node.getExpressionOpType());
                        tempNode.setNext(currentResultNode);
                        tempNode = tempNode.getNext();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("执行过滤表达式逻辑出错",e);
                    }
                    node = node.getNextNode();
                }

                FilterOpResultLogicJoinHandlerContainer joinHandlerContainer = FilterOpResultLogicJoinHandlerContainer.getInstance();

                List<Object> prevResult = Collections.emptyList();
                ExpressionOpType prevLogicOpType = ExpressionOpType.OR;

                while (resultNodeHead.getNext() != null) {
                    ObjectFieldValueFilterOpResultNode resultNode = resultNodeHead.getNext();
                    if (prevLogicOpType != null) {
                        prevResult = joinHandlerContainer.acquireOpInvoker(prevLogicOpType).invokeJoin(prevResult, resultNode.getCurrentResult());
                        prevLogicOpType = resultNode.getConcatLogicOpType();
                    }
                    resultNodeHead = resultNode;
                }

                outboundDataSet = prevResult;
            }

            executeContext.setOutboundDataSet(outboundDataSet);
        }

    @Override
    protected QueryStatement acquireCurrentQueryStatement() {
        return where;
    }

}