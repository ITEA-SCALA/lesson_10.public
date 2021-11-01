package com.itea

/**
 * @see https://blog.jetbrains.com/scala/2019/03/27/intellij-scala-plugin-2019-1-highlighting-for-comprehensions-find-usages-for-implicits-and-more
 *      https://blog.maizy.ru/posts/scala-cats-summary
 */
object EitherApp extends App {

  //
  val result: Either[Int, String] = Right(value = "Good value") //TODO  правая проекция параметризована двумя типами Int и String         case class Right[+A, +B] (value: B) extends Either[A, B]
  val error: Either[Int, String] = Left(value = 500) //TODO  и левая проекция тоже параметризована двумя типами Int и String   case class Left[+A, +B]  (value: A) extends Either[A, B]
  /*
   * `Either` по умолчанию право-проекционный
   * на правильном значении (правой ветке) с ним можно что-то делать, например   .getOrElse("default")   будет проходить какая-то трансформация
   * а на левой проекции ничего происходить не будет
   */
  val resultList: Either[List[Int], String] = Right(value = "Good value")
  val errorList: Either[List[Int], String] = Left(value = List(500, 404, 300))
  /*
   * TODO не понятно как ранее определенное значение левой проекции попадает в правую проекцию... как передается ?
   */
  resultList match {
    case Right(value) => println(value)
    case Left(list) => list.foreach(println)
  }

  /*
   * For-Comprehension in Scala
   * ***
   * если после For делаем любую функцию, например print, тогда на самом деле вызывается foreach
   */
  1 to 10
  val range: Range = 1.to(10)

  // foreach
  (1 to 10).foreach(print) // эта конструкция
  println
  for (i <- 1 to 10) print(i) // и эта конструкция это одно и тоже
  //  (1 to 10).foreach(i => print(i)) // For в Scala это синтаксический сахар
  //  (1 to 10).foreach(print(_))
  //  (1 to 10).foreach(print)

  // map
  (1 to 10).map(i => i * 2) // map идет как последняя операция
  for (i <- 1 to 10) yield i * 2 // syntax sugar for map - yield здесь как последняя операция - это одно и тоже

  // filter
  (1 to 10).filter(i => i % 2 == 0) // filter идет как последняя операция
  for (i <- 1 to 10 if i % 2 == 0) yield i // syntax sugar for filter - yield здесь как последняя операция - это одно и тоже
  (1 to 10).withFilter(i => i % 2 == 0).map(i => i) // filter редкая и коварная операция, потому-что он определен не для всех основных классов
  /*
   * 1. чем filter отличается от withFilter:
   *    withFilter - это более ленивая операция и она возвращает collectionView, то есть она не делает каких-то реальных операций пока не было каких-то последующих вычислений
   * 2. еще появился `.map(i => i)` здесь приходится так писать, потому-что по другому не получиться
   */


  // flatMap
  println
  //  (1 to 10)
  //  (1 to 10)
  // но в Scala так не получиться (как в Java)
  //  for (i <- 1 to 10) {
  //    for (j <- 1 to 10) {
  //      i * j
  //    }
  //  }
  for (i <- 1 to 10; j <- 1 to 10) yield i * j // вот так код можно встретить очень редко

  for { // чаще всего делают так
    i <- 1 to 10
    j <- 1 to 10
  } println(i * j)

  val value1: Seq[Int] = for {
    i <- 1 to 10
    j <- 1 to 10
  } yield i * j
  //
  (1 to 10).flatMap(i =>
    (1 to 10).map(j => i * j),
  )
  //
  (1 to 10).flatMap { i =>
    (1 to 10).map(j => i * j)
  }

  val value2: Seq[Int] = for {
    i <- 1 to 10
    j <- 1 to 10
    k <- 1 to 10
  } yield i * j * k
  //
  (1 to 10).flatMap { i =>
    (1 to 10).flatMap { j =>
      (1 to 10).map(k => i * j * k)
    }
  }
  /*
   * TODO: Очень важно!!!
   * В Scala, какой бы класс не определяли-б с методом `flatMap`:
   * + его всегда можно использовать в выражении For-Comprehension со стрелкой `<-`
   * + выражение `for-yield` всегда можно использовать в качестве `map`
   * + а выражение `for (без yield)` всегда можно использовать в качестве `foreach`
   */


  // Optional - happy path
  val opt1 = Some(1)
  val opt2 = Some("str")
  val opt3 = Some(true)

  val valueOpt = for {
    i <- opt1
    j <- opt2
    k <- opt3
  } yield i + j + k
  //
  val option2: Option[Option[Option[String]]] = opt1.map(i => opt2.map(j => opt3.map(k => i + j + k)))
  /*
   * потому что `map` возвращает `def map[B](f: A => B): Option[B]` и таким способом они накрутят вложенный Option во вложенный во вложенный...
   * а чтобы такого не было, для этого нужен `flatMap`
   */
  val option3: Option[String] = opt1.flatMap(i => opt2.flatMap(j => opt3.map(k => i + j + k)))

}
