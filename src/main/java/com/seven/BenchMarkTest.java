package com.seven;

import com.seven.utils.JsonUtil;
import lombok.SneakyThrows;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 3, time = 1)
public class BenchMarkTest {
    String json = "{\"id\":122345667,\"email\":\"jianzh5@163.com\",\"price\":12.25}";
    @Benchmark
    public void jsonToObj() {
        Map<String, Object> map = JsonUtil.toMap(json);
    }
    @SneakyThrows
    @Test
    public void test() {
        Options opt = new OptionsBuilder().include(BenchMarkTest.class.getSimpleName()).build();
        new Runner(opt).run();
    }
}
