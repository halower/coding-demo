package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.expression.element.ObjectFieldFilterOpExpression;
import alibaba.coding.queryparser.metadata.QueryObjectFieldFilterOpType;

import java.util.List;

public interface ObjectFieldValueFilterOpInvoker {

    /**
     * 执行对象字段值过滤
     *
     * @param dataSet    过滤数据集
     * @param expression 过滤表达式
     * @return 过滤后数据集
     */
    List<Object> invokeFilter(List<Object> dataSet, ObjectFieldFilterOpExpression expression);

    /**
     * 获取支持的对象字段查询过滤运算类型
     *
     * @return 对象字段查询过滤运算类型
     */
    QueryObjectFieldFilterOpType supportObjectFieldValueFilterOpType();
}