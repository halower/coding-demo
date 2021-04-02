package alibaba.coding.queryparser.expression.element;


import alibaba.coding.queryparser.contract.Expression;
import cn.hutool.core.lang.Assert;

public class GroupByFieldExpression implements Expression {
    /**
     * 对象字段名称
     */
    private final String objectFieldName;

    public GroupByFieldExpression(String objectFieldName) {
        this.objectFieldName = objectFieldName;
    }

    public String getObjectFieldName() {
        return objectFieldName;
    }

    @Override
    public boolean objectFieldAllowed() {
        return true;
    }

    @Override
    public void validate() {
        Assert.notBlank(objectFieldName, "Group By 字段不能为空");
    }
}
