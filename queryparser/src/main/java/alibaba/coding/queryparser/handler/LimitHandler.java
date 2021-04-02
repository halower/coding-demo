package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.expression.element.LimitExpression;
import alibaba.coding.queryparser.expression.querymodel.Limit;
import alibaba.coding.queryparser.metadata.QueryStatement;
import alibaba.coding.queryparser.context.QueryStatementContext;
import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LimitHandler extends AbstractQueryStatementHandlerInvokeTemplate  {

    private final Limit limit;

    public LimitHandler(Limit limit) {
        this.limit = limit;
    }

    @Override
    protected QueryStatement acquireCurrentQueryStatement() {
        return limit;
    }

    @Override
    protected void invokeExecuteSegment(QueryStatementContext context) {
        List<Object> inboundDataSet = context.getInboundDataSet();
        LimitExpression limitExpression = (LimitExpression) limit.statementExpression().getCurrentNode();
        List<Object> outboundDataSet;
        if (CollectionUtil.isEmpty(inboundDataSet) || inboundDataSet.size() < limitExpression.getOffset()) {
            outboundDataSet = Collections.emptyList();
        } else {
            long recvCount = limitExpression.getLimit();
            outboundDataSet = new ArrayList<>();
            for (int i = 0; i < inboundDataSet.size() && recvCount > 0; i++) {
                if (i < limitExpression.getOffset()) {
                    continue;
                }
                outboundDataSet.add(inboundDataSet.get(i));
                recvCount--;
            }
        }

        context.setOutboundDataSet(outboundDataSet);
    }
}
