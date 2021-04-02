package alibaba.coding.queryparser;

import alibaba.coding.queryparser.context.ExpressionNode;
import alibaba.coding.queryparser.contract.QueryRepository;
import alibaba.coding.queryparser.expression.builder.ExpressionNodeBuilder;
import alibaba.coding.queryparser.expression.element.*;
import alibaba.coding.queryparser.expression.querymodel.GroupBy;
import alibaba.coding.queryparser.expression.querymodel.Limit;
import alibaba.coding.queryparser.expression.querymodel.OrderBy;
import alibaba.coding.queryparser.expression.querymodel.Where;
import alibaba.coding.queryparser.metadata.ExpressionOpType;
import alibaba.coding.queryparser.metadata.GroupFuncType;
import alibaba.coding.queryparser.metadata.OrderDirection;
import alibaba.coding.queryparser.metadata.QueryObjectFieldFilterOpType;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SimpleQueryRepositoryTest {
    private final QueryRepository queryRepository = new SimpleQueryRepository();

    @Test
    public void query() {
        List<Object> dataSet = Stream.generate(() -> new Person().setAge(new Random().nextInt(100)).setName("person-"+ UUID.randomUUID())).limit(1000)
                .collect(Collectors.toList());

        // 构造 Where
        ExpressionNode whereExpression = new ExpressionNodeBuilder()
                .firstExpression(new ObjectFieldFilterOpExpression("age", Integer.class, QueryObjectFieldFilterOpType.LT
                        , 1))
                .buildResult();
        Where where = new Where(whereExpression);

//        // 构造 GroupBy
//        ExpressionNode groupExpressionNode = new ExpressionNodeBuilder()
//                .firstExpression(new GroupByFieldExpression("age"))
//                .appendExpression(new GroupFuncFieldExpression("age", GroupFuncType.COUNT), ExpressionOpType.NONE)
//                .buildResult();
//        GroupBy groupBy = new GroupBy(groupExpressionNode);

        // 构造 OrderBy
        ExpressionNode orderByExpression = new ExpressionNodeBuilder().firstExpression(new OrderByFiledExpression("age", OrderDirection.DESC)).buildResult();
        OrderBy orderBy = new OrderBy(orderByExpression);

        // 构造 Limit
        ExpressionNode limitExpressionNode = new ExpressionNodeBuilder().firstExpression(new LimitExpression(1, 100)).buildResult();
        Limit limit = new Limit(limitExpressionNode);


        List<Object> queryResult = queryRepository.query(dataSet, where, orderBy, null, limit);
        System.out.println(queryResult.size());
    }
}