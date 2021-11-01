package com.itea.task2.p10

/**
 * Кодирование длин серий списка.
 *
 * @see http://aperiodic.net/phil/scala/s-99/p10.scala
 * ***
 * Кодирование метода сжатия данных.
 * Последовательные дубликаты элементов кодируется как кортежи (N, E), где N - количество дубликатов элемент E.
 */
object LengthEncodingList extends App {
  /*
   * 
   */
  import com.itea.task2.p09.DuplicatesElementsAsSublists.pack
  def encode[A](list: List[A]): List[(Int, A)] = pack(list) map { e => (e.length, e.head) }

  println(
    "encode = " + encode(List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e')))

}
