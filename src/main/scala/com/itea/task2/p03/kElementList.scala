package com.itea.task2.p03

/**
 * Найдите K- й элемент списка.
 * @see http://aperiodic.net/phil/scala/s-99/p03.scala
 */
object KElementList extends App {

  /*
   * со встроенными командами
   */
  def nthBuiltin[A](index: Int, list: List[A]): A =
    if (index >= 0) list(index)
    else throw new NoSuchElementException

  println(
    "nthBuiltin = " + nthBuiltin(4, List(1, 1, 2, 3, 5, 8)))

  /*
   * паттерн 'match' + рекурсия по списку
   */
  def nthRecursive[A](index: Int, list: List[A]): A = (index, list) match {
    case (0, h :: _   ) => h
    case (n, _ :: tail) => nthRecursive(n - 1, tail)
    case (_, Nil      ) => throw new NoSuchElementException
  }

  println(
    "nthRecursive = " + nthRecursive(4, List(1, 1, 2, 3, 5, 8)))
}
