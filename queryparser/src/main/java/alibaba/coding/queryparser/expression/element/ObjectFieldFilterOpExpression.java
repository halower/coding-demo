package alibaba.coding.queryparser.expression.element;

import alibaba.coding.queryparser.contract.Expression;
import alibaba.coding.queryparser.metadata.QueryObjectFieldFilterOpType;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.Collection;

@Data
public class ObjectFieldFilterOpExpression implements Expression {

    /**
     * 对象字段名称
     */
    private String objectFieldName;

    /**
     * 对象值类型 class
     */
    private Class<?> objectFieldValueClass;

    /**
     * 过滤运算操作类型
     */
    private QueryObjectFieldFilterOpType opType;

    /**
     * 过滤运算操作数值
     */
    private Object opParticipateValue;

    @Override
    public boolean objectFieldAllowed() {
        return true;
    }

    public ObjectFieldFilterOpExpression(String objectFieldName, Class<?> objectFieldValueClass, QueryObjectFieldFilterOpType opType, Object opParticipateValue) {
        this.objectFieldName = objectFieldName;
        this.objectFieldValueClass = objectFieldValueClass;
        this.opType = opType;
        this.opParticipateValue = opParticipateValue;
    }


    @Override
    public void validate() {
        Assert.isTrue(StrUtil.isNotBlank(objectFieldName), "对象值字段与传入值逻辑运算表达式中 [对象字段名称] 不能为空");
        Assert.notNull(objectFieldValueClass, "对象值字段与传入值逻辑运算表达式中 [对象值类型 class] 不能为空");
        Assert.notNull(opType, "对象值字段与传入值逻辑运算表达式中 [过滤运算操作类型] 不能为空");

        switch (opType) {
            case EQ:
            case NE:
                Assert.notNull(opParticipateValue, "对象值字段与传入值逻辑运算表达式中 [过滤运算操作类型] 不能为空");
                break;
            case GE:
            case LE:
            case GT:
            case LT:
                Assert.notNull(opParticipateValue, "对象值字段与传入值逻辑运算表达式中 [过滤运算操作数值] 不能为空");
                Assert.isTrue(opParticipateValue instanceof Comparable, "过滤运算操作数值不可比较");
                break;
            case IN:
            case NOT_IN:
                Assert.notNull(opParticipateValue, "对象值字段与传入值逻辑运算表达式中 [过滤运算操作数值] 不能为空");
                Assert.isTrue(opParticipateValue instanceof Collection, "过滤运算操作数值类型错误, 应该是集合类型");
                Assert.isTrue(CollectionUtil.isNotEmpty((Collection<?>) opParticipateValue), "过滤运算操作数值类型错误, 集合不能为空");
                break;
        }
    }
}