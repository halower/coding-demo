package alibaba.coding.queryparser.context;

import alibaba.coding.queryparser.contract.FilterOpResultLogicJoinHandler;
import alibaba.coding.queryparser.handler.FilterOpResultAndJoinHandler;
import alibaba.coding.queryparser.handler.FilterOpResultOrJoinHandler;
import alibaba.coding.queryparser.metadata.ExpressionOpType;
import cn.hutool.core.lang.Assert;

import java.util.HashMap;
import java.util.Map;


public class FilterOpResultLogicJoinHandlerContainer {
    private static volatile FilterOpResultLogicJoinHandlerContainer instance = null;

    /**
     * 过滤运算结果连接处理器容器 Map
     */
    private final Map<ExpressionOpType, FilterOpResultLogicJoinHandler> opTypeMappedHandlerMap;

    private FilterOpResultLogicJoinHandlerContainer() {
        opTypeMappedHandlerMap = new HashMap<>();
        // 注册对象字段值过滤执行器
        FilterOpResultAndJoinHandler andJoinHandler = new FilterOpResultAndJoinHandler();
        opTypeMappedHandlerMap.put(andJoinHandler.supportExpressionLogicOpType(), andJoinHandler);
        FilterOpResultOrJoinHandler orJoinHandler = new FilterOpResultOrJoinHandler();
        opTypeMappedHandlerMap.put(orJoinHandler.supportExpressionLogicOpType(), orJoinHandler);

    }

    public static FilterOpResultLogicJoinHandlerContainer getInstance() {
        if (instance == null) {
            synchronized (FilterOpResultLogicJoinHandlerContainer.class) {
                if (instance == null) {
                    instance = new FilterOpResultLogicJoinHandlerContainer();
                }
            }
        }
        return instance;
    }


    public FilterOpResultLogicJoinHandler acquireOpInvoker(ExpressionOpType op) {
        Assert.isTrue(opTypeMappedHandlerMap.containsKey(op), "表达式逻辑操作类型不支持");
        return opTypeMappedHandlerMap.get(op);
    }
}