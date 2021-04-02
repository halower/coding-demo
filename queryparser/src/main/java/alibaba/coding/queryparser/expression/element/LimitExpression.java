package alibaba.coding.queryparser.expression.element;

import alibaba.coding.queryparser.contract.Expression;
import cn.hutool.core.lang.Assert;
import lombok.Data;

@Data
public class LimitExpression implements Expression {

    /**
     * 结果集偏移量
     */
    private final long offset;

    /**
     * 结果集获取数量
     */
    private final long limit;

    public LimitExpression(long limit, long offset) {
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public boolean objectFieldAllowed() {
        return false;
    }

    @Override
    public void validate() {
        Assert.isTrue(limit >= 0, "Limit 结果集获取数量必须大于等于 0");
        Assert.isTrue(offset >= 0, "Limit 结果集偏移量必须大于等于 0");
    }
}

