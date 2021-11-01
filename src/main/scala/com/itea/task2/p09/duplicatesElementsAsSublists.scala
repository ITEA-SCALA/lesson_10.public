package com.itea.task2.p09

/**
 * Упаковать последовательные дубликаты элементов списка в подсписки (если список содержит повторяющиеся элементы тогда их следует поместить в отдельные подсписки).
 * @see http://aperiodic.net/phil/scala/s-99/p09.scala
 */
object DuplicatesElementsAsSublists extends App {
  /*
   *
   */
  def pack[A](list: List[A]): List[List[A]] = {
    if (list.isEmpty) List(List())
    else {
      val (packed, next) = list span { _ == list.head }
      if (next == Nil) List(packed)
      else packed :: pack(next)
    }
  }

  println(
    "pack = " + pack(List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e')))

}
