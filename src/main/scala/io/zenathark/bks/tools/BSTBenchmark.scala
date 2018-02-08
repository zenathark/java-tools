package io.zenathark.bks.tools

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole
import io.zenathark.tools._


@State(Scope.Benchmark)
@Fork(value = 1)
@Timeout(time = 10, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class  BSTBenchmark {
  val SIZE = 100000
  val numbers: Array[Int] = new Array(SIZE)
  val bst: FastBST = FastBST.createRoot(0)
  val bstp: FastBSTp = FastBSTp.createRoot(0)

  @Setup
  def initTrees(): Unit = {
    for (i <- 0 until SIZE) {
      numbers(i) = i
      FastBST.insert(bst, i)
      FastBSTp.insert(bstp, i)
    }
  }

  @Benchmark
  def withIterator = FastBST.preOrder(bst)

  @Benchmark
  def baseline = FastBSTp.preOrder(bstp)
}
