package com.itea.monads

/**
 * МОНАДЫ - ЭТО МОНОИД КАТЕГОРИЙ ЭНДО-ФУНКТОРОВ
 * @see https://www.baeldung.com/scala/monads
 * ***
 * чтобы монада была монадо-подобной, для этого нужно всего лишь два метода:
 * 1. unit() ( apply )
 * 2. flatMap()
 *
 * Монады должны обладать законами:
 * - левая ассоциативность  ( 3.1. Left Identity )
 * - правая ассоциативность ( 3.2. Right Identity )
 * - и равенство            ( 3.3. Associativity )
 */
//trait Monad[T] {
//  def apply(value: T): Monad[T]
//  def flatMap[B] (f: T => Monad[B]): Monad[B]
//
//  def map[B] (f: T => B): Monad[B] =
//    flatMap{ t =>
//      new Monad[B] {
//        override def apply(value: B): Monad[B] = super.apply(value)
//        override def flatMap[U](f: B => Monad[U]): Monad[U] = super.flatMap(f)
//      }
//  }
//}

//////

object MonadRun extends App {

//  case class Maybe(value: Int) extends Monad[Int] { // в конструкторе метод apply уже определен
//    override def flatMap[B](f: Int => Monad[B]): Monad[B] = ??? //
//  }

  /*
   * если у нас есть монада и мы делаем какой-то метод
   * делаем метод flatMap() на ней и определенно возвращаем функцию
   */
  Option("str").flatMap(str => Option(str.length)) // к примеру: преобразуем стринговое значение строки в значение длины этой строки

  Option.apply("").flatMap(str => Option.apply(str.length))
}