package alibaba.coding.queryparser.context;


import lombok.Data;

import java.util.List;

@Data
public class QueryStatementContext {
    /**
     * 执行语句段结果集
     */
    private List<Object> inboundDataSet;

    /**
     * 语句段执行完成后结果集
     */
    private List<Object> outboundDataSet;
}
