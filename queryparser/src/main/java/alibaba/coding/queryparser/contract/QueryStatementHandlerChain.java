package alibaba.coding.queryparser.contract;

import java.util.List;

public interface QueryStatementHandlerChain {

    List<Object> invokeQuery(List<Object> inboundDataSet);

}