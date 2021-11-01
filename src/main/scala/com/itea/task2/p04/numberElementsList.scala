package com.itea.task2.p04

/**
 * Найдите количество элементов в списке.
 * @see http://aperiodic.net/phil/scala/s-99/p04.scala
 */
object NumberElementsList extends App {
  /*
   * со встроенными командами
   */
  def lengthBuiltin[A](list: List[A]): Int = list.length

  println(
    "lengthBuiltin = " + lengthBuiltin(List(1, 1, 2, 3, 5, 8)))

  /*
   * через внутреннюю функцию 'lengthR' + паттерн 'match' + рекурсия
   */
  def lengthTailRecursive[A](list: List[A]): Int = {
    def lengthR(result: Int, curList: List[A]): Int = curList match {
      case Nil       => result
      case _ :: tail => lengthR(result + 1, tail)
    }
    lengthR(0, list)
  }

  println(
    "lengthTailRecursive = " + lengthTailRecursive(List(1, 1, 2, 3, 5, 8)))

  /*
   * функциональное решение, через foldLeft
   */
  def lengthFunctional[A](list: List[A]): Int = list.foldLeft(0) { (c, _) => c + 1 }

  println(
    "lengthFunctional = " + lengthFunctional(List(1, 1, 2, 3, 5, 8)))

}
