package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.context.ExpressionNode;
import alibaba.coding.queryparser.expression.element.OrderByFiledExpression;
import alibaba.coding.queryparser.expression.querymodel.OrderBy;
import alibaba.coding.queryparser.infrastructure.ReflectUtil;
import alibaba.coding.queryparser.metadata.OrderDirection;
import alibaba.coding.queryparser.metadata.QueryStatement;
import alibaba.coding.queryparser.context.QueryStatementContext;
import cn.hutool.core.collection.CollectionUtil;

import java.util.*;
import java.util.stream.Collectors;

public class OrderByHandler extends AbstractQueryStatementHandlerInvokeTemplate {

    private final OrderBy orderBy;

    public OrderByHandler(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    protected QueryStatement acquireCurrentQueryStatement() {
        return orderBy;
    }

    @Override
    protected void invokeExecuteSegment(QueryStatementContext executeContext) {
        List<Object> inboundDataSet = executeContext.getInboundDataSet();
        ExpressionNode expressionNode = orderBy.statementExpression();
        List<Object> outboundDataSet;
        if (CollectionUtil.isEmpty(inboundDataSet)) {
            outboundDataSet = Collections.emptyList();
        } else {
            OrderByFiledValuesNode head = new OrderByFiledValuesNode(null, null);
            OrderByFiledValuesNode t = head;
            Class<?> pojoClass = inboundDataSet.get(0).getClass();
            while (expressionNode != null) {
                OrderByFiledExpression orderByFiledExpression = (OrderByFiledExpression) expressionNode.getCurrentNode();
                String objectFieldName = orderByFiledExpression.getObjectFieldName();
                List<Object> sortedFiledValues = inboundDataSet.stream().map(e -> ReflectUtil.invokeGetterMethod(pojoClass, e, objectFieldName)).sorted().collect(Collectors.toList());
                if (orderByFiledExpression.getDirection() == OrderDirection.DESC) {
                    Collections.reverse(sortedFiledValues);
                }

                t.nextNode = new OrderByFiledValuesNode(objectFieldName, sortedFiledValues);
                t = head.nextNode;
                expressionNode = expressionNode.getNextNode();
            }

            t = head;
            outboundDataSet = this.sortRecursion(inboundDataSet, t.nextNode, pojoClass);
        }


        executeContext.setOutboundDataSet(outboundDataSet);
    }

    private List<Object> sortRecursion(List<Object> dataSet, OrderByFiledValuesNode valuesNode, Class<?> pojoClass) {
        // 递归终止条件
        if (valuesNode == null || CollectionUtil.isEmpty(dataSet)) {
            return dataSet;
        }

        if (dataSet.size() == 1) {
            return dataSet;
        }

        String orderByFieldName = valuesNode.fieldName;
        Map<Object, List<Object>> fieldGroupData = new HashMap<>();
        for (Object o : dataSet) {
            Object fieldValue = ReflectUtil.invokeGetterMethod(pojoClass, o, orderByFieldName);
            List<Object> valList = fieldGroupData.getOrDefault(fieldValue, new ArrayList<>());
            valList.add(o);
            fieldGroupData.put(fieldValue, valList);
        }

        List<Object> sortedValues = valuesNode.values;
        List<Object> res = new ArrayList<>();
        Set<Object> addedDataField = new HashSet<>();
        for (Object filedValue : sortedValues) {
            if (addedDataField.contains(filedValue) || !fieldGroupData.containsKey(filedValue)) {
                continue;
            }
            List<Object> tempRes = sortRecursion(fieldGroupData.get(filedValue), valuesNode.nextNode, pojoClass);
            res.addAll(tempRes);
            addedDataField.add(filedValue);
        }

        return res;
    }

    /**
     * 排序字段值集合
     */
    private static class OrderByFiledValuesNode {

        /**
         * 字段名
         */
        String fieldName;
        /**
         * 当前排序字段值
         */
        List<Object> values;
        /**
         * 下一个排序值节点
         */
        OrderByFiledValuesNode nextNode;

        public OrderByFiledValuesNode(String fieldName, List<Object> values) {
            this.values = values;
            this.fieldName = fieldName;
        }
    }
}