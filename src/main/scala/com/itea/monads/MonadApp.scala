package com.itea.monads

/**
  * @see https://groz.github.io/scala/intro/monads
  *      *****************************************
  *      Монады | Опциональные значения
  *
  * Илья Слободянюк - Лекция 6: IO монада, ZIO и Cats Effect
  * @see https://www.youtube.com/watch?v=EIaqf4Jsy8U
  */



object MonadApp extends App {

  // #1.
  /**
    * Допустим, у нас есть таблица данных о людях с обязательным полем: 'name' и опциональными полями: 'nickname', 'height' и 'weight'
    * Представить такой объект можно следующим классом:
    */
//  case class Person(name: String, nickname: String, height: Double, weight: Double)

  /**
    * Клиентский код, использующий этот объект, всегда находится под угрозой NullPointerException и многих других:
    */
//  def isTall(p: Person): Boolean =
//    if (p.height != 0.0) p.height > 1.9
//    else ??? // что возвращать тут?
//
//  // null pointer exception, забыли проверить на null
//  def nicknameLength(p: Person) = p.nickname.length
//
//  // division by zero exception, забыли проверить на 0
//  def calcBMI(p: Person) = p.weight / (p.height * p.height)
//
//
//  val name3: String = "Person"
//  val nickname3: String = "person"
//  val height3: Double = 10.0
//  val weight3: Double = 12.0
//  val person3 = new Person(name3, nickname3, height3, weight3)
//  println( "isTall = " + isTall(person3) )
//
////  val name4: String = "Person"
////  val nickname4: String = "person"
////  val height4 = 0
////  val weight4: Double = 12.0
////  val person4 = new Person(name4, nickname4, height4, weight4)
////  println( "isTall = " + isTall(person4) ) // TODO  scala.NotImplementedError: an implementation is missing
//
//  val name5: String = "Person"
//  val nickname5: String = "person"
//  val height5: Double = 10.0
//  val weight5: Double = 12.0
//  val person5 = new Person(name5, nickname5, height5, weight5)
//  println( "nicknameLength = " + nicknameLength(person5) )
//
////  val name6: String = "Person"
////  val nickname6 = null
////  val height6: Double = 10.0
////  val weight6: Double = 12.0
////  val person6 = new Person(name6, nickname6, height6, weight6)
////  println( "nicknameLength = " + nicknameLength(person6) ) // TODO  java.lang.NullPointerException
//
//  val name1: String = "Person"
//  val nickname1: String = "person"
//  val height1: Double = 10.0
//  val weight1: Double = 12.0
//  val person1 = new Person(name1, nickname1, height1, weight1)
//  println( "calcBMI = " + calcBMI(person1) )
//
//  val name2: String = "Person"
//  val nickname2: String = "person"
//  val height2: Double = 10.0
//  val weight2 = 0
//  val person2 = new Person(name2, nickname2, height2, weight2)
//  println( "calcBMI = " + calcBMI(person2) )


  // #2.
  /**
    * Попробуем ввести понятие "возможно отсутствующего значения" в систему типов
    */
//  abstract class M[+A]
//  case class Just[A](get: A) extends M[A]
//  case object Not extends M[Nothing] // Nothing - это специальный тип в Scala, являющийся производным от всех остальных типов

  /**
    * Тогда указанные выше примеры можно переписать так:
    */
//  case class Person(name: String, nickname: M[String], height: M[Double], weight: M[Double])
//
//  def isTall(p: Person): M[Boolean] = p.height match {
//    case Just(h) => Just(h > 1.9)
//    case Not => Not
//  }
//
//  def nicknameLength(p: Person): M[Int] = p.nickname match {
//    case Just(nickname) => Just(nickname.length)
//    case Not => Not
//  }
//
//
//  val name3: String = "Person"
//  val optionalNickname3: M[String] = Just("person")
//  val optionalHeight3: M[Double] = Just(10.0)
//  val optionalWeight3: M[Double] = Just(12.0)
//  val person3 = new Person(name3, optionalNickname3, optionalHeight3, optionalWeight3)
//  println( "isTall = " + isTall(person3) )
//
//  val name4: String = "Person"
//  val optionalNickname4: M[String] = Just("person")
//  val optionalHeight4: M[Nothing] = Not
//  val optionalWeight4: M[Double] = Just(12.0)
//  val person4 = new Person(name4, optionalNickname4, optionalHeight4, optionalWeight4)
//  println( "isTall = " + isTall(person4) )
//
//  val name5: String = "Person"
//  val optionalNickname5: M[String] = Just("person")
//  val optionalHeight5: M[Double] = Just(10.0)
//  val optionalWeight5: M[Double] = Just(12.0)
//  val person5 = new Person(name5, optionalNickname5, optionalHeight5, optionalWeight5)
//  println( "nicknameLength = " + nicknameLength(person5) )
//
//  val name6: String = "Person"
//  val optionalNickname6: M[Nothing] = Not
//  val optionalHeight6: M[Double] = Just(10.0)
//  val optionalWeight6: M[Double] = Just(12.0)
//  val person6 = new Person(name6, optionalNickname6, optionalHeight6, optionalWeight6)
//  println( "nicknameLength = " + nicknameLength(person6) )


  // #3.
  /**
    * Их можно упростить добавив метод map (вместо громоздкого паттерна 'match'):
    */
//  abstract class M[+A] {
//    def map[B](f: A => B): M[B]
//  }
//
//  case class Just[A](get: A) extends M[A] {
//    def map[B](f: A => B) = Just(f(get))
//  }
//
//  case object Not extends M[Nothing] {
//    def map[B](f: Nothing => B) = Not // всегда остается Not
//  }
//
//  case class Person(name: String, nickname: M[String], height: M[Double], weight: M[Double])
//
//  def isTall(p: Person): M[Boolean] = p.height.map(_ > 1.9)
//  def nicknameLength(p: Person): M[Int] = p.nickname.map{ _.length }
//
//  def calcBMI(p: Person): M[M[Double]] =
//    p.weight.map { weight =>
//      p.height.map { height => weight / (height * height) }
//    }
//
//
//  val name3: String = "Person"
//  val optionalNickname3: M[String] = Just("person")
//  val optionalHeight3: M[Double] = Just(10.0)
//  val optionalWeight3: M[Double] = Just(12.0)
//  val person3 = new Person(name3, optionalNickname3, optionalHeight3, optionalWeight3)
//  println( "isTall = " + isTall(person3) )
//
//  val name4: String = "Person"
//  val optionalNickname4: M[String] = Just("person")
//  val optionalHeight4: M[Nothing] = Not
//  val optionalWeight4: M[Double] = Just(12.0)
//  val person4 = new Person(name4, optionalNickname4, optionalHeight4, optionalWeight4)
//  println( "isTall = " + isTall(person4) )
//
//  val name5: String = "Person"
//  val optionalNickname5: M[String] = Just("person")
//  val optionalHeight5: M[Double] = Just(10.0)
//  val optionalWeight5: M[Double] = Just(12.0)
//  val person5 = new Person(name5, optionalNickname5, optionalHeight5, optionalWeight5)
//  println( "nicknameLength = " + nicknameLength(person5) )
//
//  val name6: String = "Person"
//  val optionalNickname6: M[Nothing] = Not
//  val optionalHeight6: M[Double] = Just(10.0)
//  val optionalWeight6: M[Double] = Just(12.0)
//  val person6 = new Person(name6, optionalNickname6, optionalHeight6, optionalWeight6)
//  println( "nicknameLength = " + nicknameLength(person6) )
//
//  val name1: String = "Person"
//  val optionalNickname1: M[String] = Just("person")
//  val optionalHeight1: M[Double] = Just(10.0)
//  val optionalWeight1: M[Double] = Just(12.0)
//  val person1 = new Person(name1, optionalNickname1, optionalHeight1, optionalWeight1)
//  println( "calcBMI = " + calcBMI(person1) )
//
//  val name2: String = "Person"
//  val optionalNickname2: M[String] = Just("person")
//  val optionalHeight2: M[Double] = Just(10.0)
//  val optionalWeight2: M[Nothing] = Not
//  val person2 = new Person(name2, optionalNickname2, optionalHeight2, optionalWeight2)
//  println( "calcBMI = " + calcBMI(person2) )


  // #4.
  /**
    * Чтобы избавиться от вложенного 'M' понадобится добавление метода 'flatMap' в реализацию
    *
    * Класс 'M' является примером монады в Scala
    * Монада - это способ решения следующей проблемы - если у нас есть значение 'A' в контексте 'M' то как нам работать с этим значением не теряя контекста
    * Попросту говоря в Scala монадой называется тип 'M[A]' у которого определены методы 'map' и 'flatMap' со следующими сигнатурами:
    */
  abstract class M[+A] {
    def map[B](f: A => B): M[B]
    def flatMap[B](f: A => M[B]): M[B]
  }

  case class Just[A](a: A) extends M[A] { // TODO (некий) простой тип
    def map[B](f: A => B) = Just( f(a) )
    def flatMap[B](f: A => M[B]): M[B] = f(a)
  }

  case object Not extends M[Nothing] { // TODO пустой тип
    def map[B](f: Nothing => B) = Not // всегда остается Not
    def flatMap[B](f: Nothing => M[B]): M[B] = Not
  }


  val optionalX: M[Int] = Just(5)
  val optionalY: M[Int] = Just(10)
  val optionalZ: M[Int] = Just(12)

  /**
    * Выражения "сохраняющие контекст" встречаются в Scala повсеместно и for-выражения - общепринятое средство работы с ними.
    * По правилам:
    * 1. все предыдущие генераторы выражения типа (a <- containerA) в for преобразуются в вызов flatMap
    * 2. а последний генератор выражение типа (a <- containerA) в for преобразуется в вызов метода map
    */
  val m: M[Boolean] = for {
    x <- optionalX
    y <- optionalY
    z <- optionalZ
  } yield (x + y) > z
  /**
    * Это разворачивается компилятором в следующее выражение:
    * val m: M[Boolean] =
    *   optionalX.flatMap{ x =>
    *     optionalY.flatMap { y =>
    *         optionalZ.map(z => (x + y) > z)
    *     }
    *   }
    *
    * Самые распространенные примеры монад в языке Scala:
    * 1. коллекции (List, Set)
    * 2. "опциональное значение" (Option - название вышеприведенного M в стандартной библиотеке)
    * 3. отложенное вычисление (Future)
    */

  println( "'(x + y) > z' = " + m ) // TODO: '(x + y) > z' = Just(true)

}
