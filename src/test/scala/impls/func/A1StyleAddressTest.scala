package impls.func

import org.scalatest.FunSuite

class A1StyleAddressTest extends FunSuite {
  test("A1") {
    val address = A1StyleAddress("A1")
    assert(address.col == 0 && address.row == 0)
  }
  test("A2") {
    val address = A1StyleAddress("A2")
    assert(address.col == 0 && address.row == 1)
  }
  test("A9") {
    val address = A1StyleAddress("A9")
    assert(address.col == 0 && address.row == 8)
  }
  test("C1") {
    val address = A1StyleAddress("C1")
    assert(address.col == 2 && address.row == 0)
  }
  test("C2") {
    val address = A1StyleAddress("C2")
    assert(address.col == 2 && address.row == 1)
  }
  test("C9") {
    val address = A1StyleAddress("C9")
    assert(address.col == 2 && address.row == 8)
  }
  test("Z1") {
    val address = A1StyleAddress("Z1")
    assert(address.col == 25 && address.row == 0)
  }
  test("Z2") {
    val address = A1StyleAddress("Z2")
    assert(address.col == 25 && address.row == 1)
  }
  test("Z9") {
    val address = A1StyleAddress("Z9")
    assert(address.col == 25 && address.row == 8)
  }
  test("Empty address") {
    assertThrows[IllegalArgumentException] {
      A1StyleAddress("")
    }
  }
  test("AA") {
    assertThrows[IllegalArgumentException] {
      A1StyleAddress("AA")
    }
  }
  test("A0") {
    assertThrows[IllegalArgumentException] {
      A1StyleAddress("A0")
    }
  }
  test("00") {
    assertThrows[IllegalArgumentException] {
      A1StyleAddress("00")
    }
  }
  test("Я4") {
    assertThrows[IllegalArgumentException] {
      A1StyleAddress("Я4")
    }
  }
}
