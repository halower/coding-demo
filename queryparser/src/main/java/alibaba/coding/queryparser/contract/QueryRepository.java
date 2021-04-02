package alibaba.coding.queryparser.contract;

import alibaba.coding.queryparser.expression.querymodel.GroupBy;
import alibaba.coding.queryparser.expression.querymodel.Limit;
import alibaba.coding.queryparser.expression.querymodel.OrderBy;
import alibaba.coding.queryparser.expression.querymodel.Where;

import java.util.List;

public interface QueryRepository{

    /**
     * 查询方法
     *
     * @param dataSet 查询数据集
     * @param where   where 过滤条件
     * @param orderBy 排序条件
     * @param groupBy 分组条件
     * @param limit   最大返回结果数
     * @return 结果集
     */
    List<Object> query(List<Object> dataSet, Where where, OrderBy orderBy, GroupBy groupBy, Limit limit);
}