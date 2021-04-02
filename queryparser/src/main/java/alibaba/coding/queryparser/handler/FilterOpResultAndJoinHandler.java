package alibaba.coding.queryparser.handler;

import alibaba.coding.queryparser.contract.FilterOpResultLogicJoinHandler;
import alibaba.coding.queryparser.metadata.ExpressionOpType;
import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FilterOpResultAndJoinHandler implements FilterOpResultLogicJoinHandler {
    @Override
    public List<Object> invokeJoin(List<Object> left, List<Object> right) {
        if (CollectionUtil.isEmpty(left) || CollectionUtil.isEmpty(right)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(CollectionUtil.intersection(left, right));
    }

    @Override
    public ExpressionOpType supportExpressionLogicOpType() {
        return ExpressionOpType.AND;
    }
}