package alibaba.coding.queryparser.expression.invoker;

import alibaba.coding.queryparser.expression.element.ObjectFieldFilterOpExpression;
import alibaba.coding.queryparser.handler.ObjectFieldValueFilterOpInvoker;
import alibaba.coding.queryparser.infrastructure.ReflectUtil;
import alibaba.coding.queryparser.metadata.QueryObjectFieldFilterOpType;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectFieldValueGeFilterOpInvoker implements ObjectFieldValueFilterOpInvoker {
    @Override
    public List<Object> invokeFilter(List<Object> dataSet, ObjectFieldFilterOpExpression expression) {
        Assert.notNull(expression, "对象字段值等于运算过滤执行器执行过滤方法, 运算表达式信息不能为空");
        if (CollectionUtil.isEmpty(dataSet)) {
            return Collections.emptyList();
        }
        Assert.isTrue(expression.getOpParticipateValue() instanceof Comparable, "运算知类型错误");
        Comparable opParticipateValue = (Comparable) expression.getOpParticipateValue();
        Class<?> pojoClass = dataSet.get(0).getClass();
        return dataSet.stream()
                .filter(data -> opParticipateValue.compareTo(ReflectUtil.invokeGetterMethod(pojoClass, data, expression.getObjectFieldName())) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public QueryObjectFieldFilterOpType supportObjectFieldValueFilterOpType() {
        return QueryObjectFieldFilterOpType.GE;
    }
}