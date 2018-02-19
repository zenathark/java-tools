package io.zenathark.bks.tools

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole
import io.zenathark.tools._
import scala.collection.JavaConverters._

@State(Scope.Benchmark)
@Fork(value = 1)
@Timeout(time = 10, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ParameterBSTBenchmark {
  val SIZE = 100000
  val numbers = new Array[Int](SIZE)
  val bst = FastBST.createRoot(0)
  val bstp = ParameterBST.createRoot(new IntWrapper(0))

  @Setup
  def initTrees() =
    (0 until SIZE) foreach { i =>
      numbers(i) = i
      FastBST.insert(bst, i)
      ParameterBST.insert(bstp, new IntWrapper(i))
    }

  @Benchmark
  def baseline(blackhole: Blackhole): Unit = {
    val it = bst.preorderIterator.iterator
    while (it.hasNext) {
      val e = it.next
      blackhole.consume(e.label + 5)
    }
  }

  @Benchmark
  def asScala(blackhole: Blackhole): Unit =
    bst.preorderIterator().asScala.foreach { i =>
      blackhole.consume(i.label + 5)
    }

  @Benchmark
  def withParameter(blackhole: Blackhole): Unit = {
    val it = bstp.preorderIterator.iterator
    while (it.hasNext) {
      val e = it.next
      blackhole.consume(e.payload.label + 5)
    }
  }

  @Benchmark
  def withParameterAsScala(blackhole: Blackhole): Unit =
    bstp.preorderIterator().asScala.foreach { i =>
      blackhole.consume(i.payload.label + 5)
    }
}

class IntWrapper(val label: Int) extends Comparable[IntWrapper] {
  override def compareTo(o: IntWrapper): Int = label - o.label
}
