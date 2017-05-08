package impls.func

import java.io.ByteArrayOutputStream

import app.AppMiniExcel
import org.scalatest.{ Assertion, FunSuite }

import scala.io.Source

class MiniExcelTest extends FunSuite {

  def doTest(resourceName: String): Assertion = {
    val input = Source.fromResource(s"$resourceName.in").reader()
    val output = new ByteArrayOutputStream
    val errors = new ByteArrayOutputStream

    Console.withIn(input) {
      Console.withOut(output) {
        Console.withErr(errors) {
          AppMiniExcel.main(Array.empty)
        }
      }
    }
    assert(errors.size() == 0 && output.toString == Source.fromResource(s"$resourceName.out").mkString)
  }

  test("test1") {
    doTest("test1")
  }

  test("test2") {
    doTest("test2")
  }

  test("test3") {
    doTest("test3")
  }

  test("test4") {
    doTest("test4")
  }

  test("test5") {
    doTest("test5")
  }

  test("test6") {
    doTest("test6")
  }
}
