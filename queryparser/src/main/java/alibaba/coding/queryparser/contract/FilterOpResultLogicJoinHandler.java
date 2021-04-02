package alibaba.coding.queryparser.contract;

import alibaba.coding.queryparser.metadata.ExpressionOpType;

import java.util.List;

public interface FilterOpResultLogicJoinHandler {

    /**
     * 执行结果集连接
     *
     * @param left  左运算结果集
     * @param right 右运算结果集
     * @return 连接结果
     */
    List<Object> invokeJoin(List<Object> left, List<Object> right);

    /**
     * 返回支持的逻辑运算符类型
     *
     * @return 表达式逻辑操作类型
     */
    ExpressionOpType supportExpressionLogicOpType();
}