package com.itea.task2.p08

/**
 * Устранение последовательных дубликатов элементов списка (если список содержит повторяющиеся элементы, их следует сгруппировать).
 * @see http://aperiodic.net/phil/scala/s-99/p08.scala
 */
object EliminateDuplicatesElements extends App {
  /*
   * паттерн 'match' + рекурсия по списку
   */
  def compressRecursive[A](list: List[A]): List[A] = list match {
    case Nil       => Nil
    case h :: tail => h :: compressRecursive(tail.dropWhile(_ == h))
  }

  println(
    "compressRecursive = " + compressRecursive(List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e')))

  /*
   * паттерн 'match' + хвостовая рекурсивная по списку
   */
  def compressTailRecursive[A](list: List[A]): List[A] = {
    def compressR(result: List[A], curList: List[A]): List[A] = curList match {
      case h :: tail => compressR(h :: result, tail.dropWhile(_ == h))
      case Nil       => result.reverse
    }
    compressR(Nil, list)
  }

  println(
    "compressTailRecursive = " + compressTailRecursive(List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e')))

  /*
   * функциональный подход
   */
  def compressFunctional[A](list: List[A]): List[A] =
    list.foldRight(List[A]()) { (h, r) =>
      if (r.isEmpty || r.head != h) h :: r
      else r
    }

  println(
    "compressFunctional = " + compressFunctional(List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e')))

}
