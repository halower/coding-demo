package alibaba.coding.queryparser.builder;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class WhereExpressionExecuteThreadFactory implements ThreadFactory {
    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "Where-Statement-Execute-Thread-" + counter.getAndIncrement());
    }
}