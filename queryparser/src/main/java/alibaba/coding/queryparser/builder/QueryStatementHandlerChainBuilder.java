package alibaba.coding.queryparser.builder;

import alibaba.coding.queryparser.context.QueryStatementContext;
import alibaba.coding.queryparser.contract.QueryStatementHandlerChain;
import alibaba.coding.queryparser.handler.QueryStatementHandler;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询语句段执行器链
 */
public final class QueryStatementHandlerChainBuilder implements QueryStatementHandlerChain {

    private final List<QueryStatementHandler> statementHandlers;

    private QueryStatementHandlerChainBuilder(List<QueryStatementHandler> statementHandlers) {
        this.statementHandlers = statementHandlers;
    }

    public static QueryStatementHandlerChainBuilder make(QueryStatementHandler... handlers) {
        Assert.isTrue(handlers != null, "生成查询语句段执行链参数错误");
        List<QueryStatementHandler> handlerList = new ArrayList<>();
        for (QueryStatementHandler handler : handlers) {
            if (handler != null) {
                handlerList.add(handler);
            }
        }
        return new QueryStatementHandlerChainBuilder(handlerList);
    }

    public List<Object> invokeQuery(List<Object> inboundDataSet) {
        QueryStatementContext context = new QueryStatementContext();
        context.setInboundDataSet(inboundDataSet);

        if (CollectionUtil.isEmpty(statementHandlers)) {
            return inboundDataSet;
        }

        List<Object> result = null;
        for (QueryStatementHandler segmentHandler : statementHandlers) {
            segmentHandler.executeStatement(context);
            context.setInboundDataSet(context.getOutboundDataSet());
            result = context.getOutboundDataSet();
            context.setOutboundDataSet(null);
        }
        return result;
    }
}