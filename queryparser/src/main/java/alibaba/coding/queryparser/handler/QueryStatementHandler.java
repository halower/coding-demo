package alibaba.coding.queryparser.handler;


import alibaba.coding.queryparser.context.QueryStatementContext;

public interface QueryStatementHandler {
    void executeStatement(QueryStatementContext context);
}
