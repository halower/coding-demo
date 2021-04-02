package alibaba.coding.queryparser.builder;

import alibaba.coding.queryparser.metadata.QueryStatement;
import alibaba.coding.queryparser.metadata.QueryStatementNode;

public  class QueryStatementNodeBuilder {

    private QueryStatementNode statementNode;


    public QueryStatementNode buildResult() {
        return statementNode;
    }

    /**
     * 构建查询对象节点链表
     * @param queryStatement 查询语句
     * @return 构建器
     */
    public QueryStatementNodeBuilder append(QueryStatement queryStatement) {
        if (statementNode == null) {
            statementNode = new QueryStatementNode(queryStatement);
        } else {
            statementNode.setNext(new QueryStatementNode(queryStatement));
        }
        return this;
    }
}