package alibaba.coding.queryparser.expression.element;

import alibaba.coding.queryparser.contract.Expression;
import alibaba.coding.queryparser.metadata.OrderDirection;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

public class OrderByFiledExpression implements Expression {
    /**
     * 排序方向
     */
    private final OrderDirection direction;

    /**
     * 对象字段名称
     */
    private final String objectFieldName;

    public OrderByFiledExpression(String objectFieldName, OrderDirection direction) {
        this.direction = direction;
        this.objectFieldName = objectFieldName;
    }

    @Override
    public boolean objectFieldAllowed() {
        return true;
    }

    @Override
    public void validate() {
        Assert.isTrue(StrUtil.isNotBlank(objectFieldName), "对象排序字段表达式 [对象字段名称] 不能为空");
        Assert.notNull(direction, "对象排序字段表达式 [排序方向] 不能为空");
    }

    public OrderDirection getDirection() {
        return direction;
    }

    public String getObjectFieldName() {
        return objectFieldName;
    }
}