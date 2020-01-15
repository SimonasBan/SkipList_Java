/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Benchmark;


import projektas_skiplist.SL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)

@State(Scope.Benchmark)

@OutputTimeUnit(TimeUnit.MICROSECONDS)

@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)


public class JmhBenchmark {

    static final int OPERATION_COUNT = 1_000;

    @Param({"4000", "8000", "16000", "32000"})
    public int listSize;

    LinkedList<Float> linkedList = new LinkedList<>();
    SL<Float> skipList = new SL<>();
    HashMap<Integer, Float> hashMap = new HashMap<>();
    
    

    //float[] ULLTest = new float[32000];
    float[] listTest = new float[32000];
    float[] skipListTest = new float[32000];
    float[] hashMapTest = new float[32000];
    int[] indexes = new int[OPERATION_COUNT];

    @Setup(Level.Trial)
    public void generateLists() {
        //util.generateList(ULL, listSize, ULLTest);
        util.generateList(linkedList, listSize, listTest);
        util.generateList(skipList, listSize, skipListTest);
        util.generateList(hashMap, listSize, hashMapTest);
    }

    @Setup(Level.Iteration)
    public void generateIndexes() {
        util.generateIndexes(indexes, listSize);
    }


    @Benchmark
    public void skipListGet(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(skipList.getByKey(i));
        }
    }

    
    @Benchmark
    public void linkedListGet(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(linkedList.get(i));
        }
    }
    
    @Benchmark
    public void hashMapGet(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(hashMap.get(i));
        }
    }
    
    
    
    @Benchmark
    public void skipListContains(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(skipList.contains(skipListTest[i]));
        }
    }

    @Benchmark
    public void linkedListContains(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(linkedList.contains(listTest[i]));
        }
    }
    
    @Benchmark
    public void hashMapContainsValue(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(hashMap.containsValue(hashMapTest[i]));
        }
    }
    
    

    @Benchmark
    public void skipListToArray(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(skipList.toArray());
        }
    }

    @Benchmark
    public void linkedListToArray(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(linkedList.toArray());
        }
    }
    
    
    @Benchmark
    public void skipListRemove(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(skipList.remove(skipListTest[i]));
        }
    }

    @Benchmark
    public void linkedListRemove(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(linkedList.remove(listTest[i]));
        }
    }
    
    @Benchmark
    public void hashMapRemove(Blackhole bh) {
        for (int i : indexes) {
            bh.consume(hashMap.remove(listTest[i]));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}