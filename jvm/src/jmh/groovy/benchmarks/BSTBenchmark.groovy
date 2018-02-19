package benchmarks

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

import io.zenathark.tools.*

@State(Scope.Benchmark)
@Fork(value = 1)
@Timeout(time = 10, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class IteratorBenchmark {
  static final int SIZE = 100000
  int[] numbers
  int[] bNumbers
  FastBST bst
  FastBSTp bstp

  @Setup
  void initTrees() {
    numbers = new int[SIZE]
    bNumbers = new int[SIZE]
    bst = FastBST.createRoot(0)
    bstp = FastBSTp.createRoot(0)
    for (int i = 1; i < SIZE; i++) {
      numbers[i] = i
      bNumbers[i] = SIZE - i
      FastBST.insert(bst, i)
      FastBSTp.insert(bstp, i)
    }
  }

  @Benchmark
  def withIterator(Blackhole blackhole) {
    FastBST.preOrder(bst)
  }

  @Benchmark
  def baseline(Blackhole blackhole) {
    FastBSTp.preOrder(bstp)
  }
}
