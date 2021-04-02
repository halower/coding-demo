package alibaba.coding.queryparser.metadata;

import lombok.Data;

@Data
public class QueryStatementNode {
    /**
     * 当前查询语句
     */
    private QueryStatement statement;

    /**
     * 下一个查询语段节点
     */
    private QueryStatementNode next;

    public QueryStatementNode(QueryStatement segment) {
        this.statement = statement;
    }
}
