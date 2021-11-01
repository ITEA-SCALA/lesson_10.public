package com.itea.task2.p06

import org.scalatest._
import flatspec._
import matchers._

/**
 * ScalaTest
 * @see https://www.scalatest.org
 *
 * @see http://aperiodic.net/phil/scala/s-99/p06.scala
 */
class ListIsPalindromeSpec extends AnyFlatSpec with should.Matchers {

  "Palindrome" should "using built-in commands, should be true" in {
    ListAsPalindrome().usingBuiltInCommands(List(1, 2, 3, 2, 1)) shouldBe true
  }

  "Palindrome" should "using built-in commands, should be false" in {
    ListAsPalindrome().usingBuiltInCommands(List(1, 2, 3, 4, 5)) shouldBe false
  }
}
