import org.junit.Assert._
import org.junit.Test
import org.mockito.Mockito._
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.mockito.MockitoSugar

import scala.collection.AbstractIterator

class IteratorMapIsLazy extends AssertionsForJUnit with MockitoSugar {

  @Test def mapIsLazyAbstractIterator(): Unit = {
    var counter = 0
    val it = new AbstractIterator[Int] { def hasNext = true; def next = { counter += 1; counter } }
    val result = it.map(_ + 1)
    assertEquals("counter should be zero", 0, counter)
    result.next()
    assertEquals("counter should be one", 1, counter)
  }

  @Test def mapIsLazyContinually(): Unit = {
    var counter = 0
    val it = Iterator.continually { counter += 1; counter }
    val result = it.map(_ + 1)
    assertEquals("counter should be zero", 0, counter)
    result.next()
    assertEquals("counter should be one", 1, counter)
  }

  // https://github.com/scala/scala/blob/v2.12.6/src/library/scala/collection/Iterator.scala#L455
  @Test def mapIsLazyUsingSpyFrom(): Unit = {
    val it = spy(Iterator.from(1))
    val result = it.map(_ + 1)
    verify(it, never).next() // FIXME NPE on Linux but not MacOS - see separate Mockito issue
    result.next()
    verify(it, times(1)).next()
  }

  // https://github.com/scala/scala/blob/v2.12.6/src/library/scala/collection/Iterator.scala#L455
  @Test def mapIsLazyUsingSpyIterate(): Unit = {
    val it = spy(Iterator.iterate(1)(_ + 1))
    val result = it.map(_ + 1)
    verify(it, never).next()
    result.next()
    verify(it, times(1)).next()
  }
}
