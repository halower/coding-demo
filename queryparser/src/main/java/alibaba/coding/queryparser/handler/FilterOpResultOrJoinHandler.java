package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.contract.FilterOpResultLogicJoinHandler;
import alibaba.coding.queryparser.metadata.ExpressionOpType;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterOpResultOrJoinHandler implements FilterOpResultLogicJoinHandler {
    @Override
    public List<Object> invokeJoin(List<Object> left, List<Object> right) {
        if (CollectionUtil.isEmpty(left)) {
            return ObjectUtil.defaultIfNull(right, Collections.emptyList());
        }
        if (CollectionUtil.isEmpty(right)) {
            return ObjectUtil.defaultIfNull(left, Collections.emptyList());
        }
        return new ArrayList<>(CollectionUtil.union(left, right));
    }

    @Override
    public ExpressionOpType supportExpressionLogicOpType() {
        return ExpressionOpType.OR;
    }
}