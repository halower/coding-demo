package alibaba.coding.queryparser.context;

import alibaba.coding.queryparser.metadata.ExpressionOpType;
import lombok.Getter;

import java.util.List;

@Getter
public class ObjectFieldValueFilterOpResultNode {

    /**
     * 当前链表节点运算结果
     */
    private final List<Object> currentResult;

    /**
     * 下一个节点
     */
    private ObjectFieldValueFilterOpResultNode next;

    /**
     * 连接下一个节点的逻辑运算符
     */
    private ExpressionOpType concatLogicOpType;

    public ObjectFieldValueFilterOpResultNode(List<Object> currentResult) {
        this.currentResult = currentResult;
    }

    public void setNext(ObjectFieldValueFilterOpResultNode next) {
        this.next = next;
    }

    public void setConcatLogicOpType(ExpressionOpType concatLogicOpType) {
        this.concatLogicOpType = concatLogicOpType;
    }
}