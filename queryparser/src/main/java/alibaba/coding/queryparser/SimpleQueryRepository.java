package alibaba.coding.queryparser;

import alibaba.coding.queryparser.builder.QueryStatementHandlerChainBuilder;
import alibaba.coding.queryparser.contract.QueryRepository;
import alibaba.coding.queryparser.contract.QueryStatementHandlerChain;
import alibaba.coding.queryparser.expression.querymodel.GroupBy;
import alibaba.coding.queryparser.expression.querymodel.Limit;
import alibaba.coding.queryparser.expression.querymodel.OrderBy;
import alibaba.coding.queryparser.expression.querymodel.Where;
import alibaba.coding.queryparser.builder.WhereExpressionExecuteThreadFactory;
import alibaba.coding.queryparser.handler.GroupByHandler;
import alibaba.coding.queryparser.handler.LimitHandler;
import alibaba.coding.queryparser.handler.OrderByHandler;
import alibaba.coding.queryparser.handler.WhereHandler;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class SimpleQueryRepository implements QueryRepository {
    @Override
    public List<Object> query(List<Object> dataSet, Where where, OrderBy orderBy, GroupBy groupBy, Limit limit) {
        int whereExpressionExecuteThreadExecutorCoreSize = Integer.parseInt(System.getProperty("hread-pool.core-size", "10"));
//        int whereExpressionExecuteThreadExecutorMaxSize = Integer.parseInt(System.getProperty("thread-pool.max-size", "100"));
//        ThreadPoolExecutor whereExpressionExecuteThreadExecutor = new ThreadPoolExecutor(
//                whereExpressionExecuteThreadExecutorCoreSize, whereExpressionExecuteThreadExecutorMaxSize, 60,
//                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new WhereExpressionExecuteThreadFactory());

        WhereHandler whereHandler = where == null ? null : new WhereHandler(where, null);
        GroupByHandler groupByHandler = groupBy == null ? null : new GroupByHandler(groupBy);
        OrderByHandler orderByHandler = orderBy == null ? null : new OrderByHandler(orderBy);
        LimitHandler limitHandler = limit == null ? null : new LimitHandler(limit);

        QueryStatementHandlerChain handlerChain = QueryStatementHandlerChainBuilder.make(whereHandler, groupByHandler, orderByHandler, limitHandler);
        return handlerChain.invokeQuery(dataSet);
    }
}
