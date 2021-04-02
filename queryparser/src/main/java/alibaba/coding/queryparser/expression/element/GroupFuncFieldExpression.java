package alibaba.coding.queryparser.expression.element;


import alibaba.coding.queryparser.contract.Expression;
import alibaba.coding.queryparser.metadata.GroupFuncType;

public class GroupFuncFieldExpression implements Expression {

    private final GroupFuncType groupFuncType;

    private final String objectFieldName;

    public GroupFuncFieldExpression(String objectFieldName, GroupFuncType groupFuncType) {
        this.objectFieldName = objectFieldName;
        this.groupFuncType = groupFuncType;
    }

    @Override
    public boolean objectFieldAllowed() {
        return true;
    }

    @Override
    public void validate() {

    }
}
