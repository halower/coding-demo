package alibaba.coding.queryparser.context;

import alibaba.coding.queryparser.contract.Expression;
import alibaba.coding.queryparser.metadata.ExpressionOpType;
import lombok.Data;

/**
 * 表达式链表节点
 */
@Data
public class ExpressionNode {
    /**
     * 表达式当前节点
     */
    private Expression currentNode;

    /**
     * 表达式链表是否有下一个节点
     */
    private boolean hasNext;
    /**
     * 表达式链表下一个节点
     */
    private ExpressionNode nextNode;
    /**
     * 表达式之间的逻辑连接符
     */
    private ExpressionOpType expressionOpType;

    public ExpressionNode(Expression currentNode, boolean hasNext, ExpressionNode nextNode, ExpressionOpType expressionOpType) {
        this.currentNode = currentNode;
        this.hasNext = hasNext;
        this.nextNode = nextNode;
        this.expressionOpType = expressionOpType;
    }

    public ExpressionNode(Expression currentNode) {
        this.currentNode = currentNode;
    }
}
