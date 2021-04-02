package alibaba.coding.queryparser.context;

import alibaba.coding.queryparser.expression.invoker.*;
import alibaba.coding.queryparser.handler.ObjectFieldValueFilterOpInvoker;
import alibaba.coding.queryparser.metadata.QueryObjectFieldFilterOpType;
import cn.hutool.core.lang.Assert;

import java.util.HashMap;
import java.util.Map;

public final class ObjectFieldValueFilterOpInvokerContainer {
    private static volatile ObjectFieldValueFilterOpInvokerContainer instance = null;

    /**
     * 对象字段值过滤执行器容器 Map
     */
    private final Map<QueryObjectFieldFilterOpType, ObjectFieldValueFilterOpInvoker> opTypeMappedInvokerMap;

    private ObjectFieldValueFilterOpInvokerContainer() {
        opTypeMappedInvokerMap = new HashMap<>();
        // 注册对象字段值过滤执行器
        ObjectFieldValueEqFilterOpInvoker eqFilterOpInvoker = new ObjectFieldValueEqFilterOpInvoker();
        opTypeMappedInvokerMap.put(eqFilterOpInvoker.supportObjectFieldValueFilterOpType(), eqFilterOpInvoker);
        ObjectFieldValueNeFilterOpInvoker neFilterOpInvoker = new ObjectFieldValueNeFilterOpInvoker();
        opTypeMappedInvokerMap.put(neFilterOpInvoker.supportObjectFieldValueFilterOpType(), neFilterOpInvoker);
        ObjectFieldValueInFilterOpInvoker inFilterOpInvoker = new ObjectFieldValueInFilterOpInvoker();
        opTypeMappedInvokerMap.put(inFilterOpInvoker.supportObjectFieldValueFilterOpType(), inFilterOpInvoker);
        ObjectFieldValueIsNullFilterOpInvoker isNullFilterOpInvoker = new ObjectFieldValueIsNullFilterOpInvoker();
        opTypeMappedInvokerMap.put(isNullFilterOpInvoker.supportObjectFieldValueFilterOpType(), isNullFilterOpInvoker);
        ObjectFieldValueGeFilterOpInvoker geFilterOpInvoker = new ObjectFieldValueGeFilterOpInvoker();
        opTypeMappedInvokerMap.put(geFilterOpInvoker.supportObjectFieldValueFilterOpType(), geFilterOpInvoker);
    }

    /**
     * 获取单例对象
     *
     * @return 单例对象
     */
    public static ObjectFieldValueFilterOpInvokerContainer getInstance() {
        if (instance == null) {
            synchronized (ObjectFieldValueFilterOpInvokerContainer.class) {
                if (instance == null) {
                    instance = new ObjectFieldValueFilterOpInvokerContainer();
                }
            }
        }
        return instance;
    }

    /**
     * 获取对象字段值过滤执行器
     *
     * @param op 对象字段查询过滤运算类型
     * @return 对象字段值过滤执行器
     */
    public ObjectFieldValueFilterOpInvoker acquireOpInvoker(QueryObjectFieldFilterOpType op) {
        Assert.isTrue(opTypeMappedInvokerMap.containsKey(op), "对象字段值过滤执行器不支持");
        return opTypeMappedInvokerMap.get(op);
    }
}