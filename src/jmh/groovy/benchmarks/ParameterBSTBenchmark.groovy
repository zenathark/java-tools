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
public class ParameterBSTBenchmark {
    class IntWrapper implements Comparable<IntWrapper> {
	int label;
	@Override
	def compareTo(o) = label - o
    }

  static final int SIZE = 100000
  int[] numbers
  FastBST bst
  FastBSTp bstp

  @Setup
    void initTrees() {
	numbers = new int[SIZE]
	bst = FastBST.createRoot(0)
	bstp = ParameterBST.createRoot<IntWrapper>(new IntWrapper(0))

	for (int i = 1; i < SIZE; i++) {
	    numbers[i] = i
	    FastBST.insert(bst, i)
	    ParameterBST.insert<IntWrapper>(bstp, new IntWrapper(0))
	}
    }

  @Benchmark
  def withParameter(Blackhole blackhole) {
    ParameterBST.preOrder(bstp)
  }

  @Benchmark
  def baseline(Blackhole blackhole) {
    FastBST.preOrder(bst)
  }
}
