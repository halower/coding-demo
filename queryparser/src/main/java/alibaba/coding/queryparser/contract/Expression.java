package alibaba.coding.queryparser.contract;

/**
 * 参数表达式
 */
public interface Expression extends QuerySyntaxChecker{
    /**
     * 是否传递对象字段
     * @return boolean, true: 是, false: 否
     */
    boolean objectFieldAllowed();
}
