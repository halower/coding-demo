package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.context.ExpressionNode;
import alibaba.coding.queryparser.context.QueryStatementContext;
import alibaba.coding.queryparser.metadata.QueryStatement;

public abstract class AbstractQueryStatementHandlerInvokeTemplate implements QueryStatementHandler {

    protected abstract QueryStatement acquireCurrentQueryStatement();

    protected abstract void invokeExecuteSegment(QueryStatementContext executeContext);

    @Override
    public void executeStatement(QueryStatementContext context) {
        validateStatement();
        this.invokeExecuteSegment(context);
    }

    private void validateStatement() {
        QueryStatement statement = acquireCurrentQueryStatement();
        statement.validate();
        ExpressionNode en = statement.statementExpression();
        while (en != null && en.getCurrentNode() != null) {
            en.getCurrentNode().validate();
            en = en.getNextNode();
        }
    }
}
