package com.itea

import org.scalatest.{EitherValues, OptionValues}
import org.scalatest.funsuite.AnyFunSuite

class ForComprehensionSuite extends AnyFunSuite with OptionValues with EitherValues {

  test("Option example - happy path") {
    val opt1 = Some(1)
    val opt2 = Some("str")
    val opt3 = Some(true)

    val option: Option[String] = opt1.flatMap(i => opt2.flatMap(j => opt3.map(k => i + j + k)))

    assert("1strtrue" == option.value)
  }

  test("Option example - unhappy path") {
    val opt1: Option[Int] = None
    val opt2 = Some("str")
    val opt3 = Some(true)

//                              Option
//   None extends Option[Nothing]    Some(value)

//    val option: Option[String] = opt1.flatMap(i => opt2.flatMap(j => opt3.map(k => i + j + k))) //TODO  операции выполняются до тех пор пока есть flatMap
    val option = for {
      i <- opt1       // здесь выполняется 1-я операция
      j <- opt2       // если 1-я операция прошла успешно, тогда выполняется 2-я операция
      k <- opt3       // а если 2-я операция прошла успешно, тогда выполняется 3-я операция
    } yield i + j + k // и если 3-я операция прошла успешно, тогда уже выполняется 4-я операция
    // если где-то свалиться None, тогда метод flatMap у Option ничего не делает и просто вернет None

//    assert(None == option)
    assert(option.isEmpty)
  }

  test("Either example") {
//    val e1 = Right(1)
//    val e2 = Right("str")
//    val e3 = Right(true)
    val e1: Either[String, Int] = Right(1) //val e1: Right[Nothing, Int] = Right(1)
    val e2: Either[String, String] = Right("str") //val e2: Right[Nothing, String] = Right("str")
    val e3: Either[String, Boolean] = Right(true) //val e3: Right[Nothing, Boolean] = Right(true)

    /*
     * Scala 2.13  Either as right based
     * Either работает ровно таким же способом как работает и Option
     */
    val res = e1.flatMap(i => e2.flatMap(j => e3.map(k => i + j + k)))
    assert(res.isRight)
  }

  test("example") {
    case class Address()
    case class User(name: String, address: Address)

    def getUser: Option[User] = None
    def getAddress: Option[Address] = Some(Address())
    /*
     * и чаще Scala-код похож на такие For-Comprehension-ы
     */
    val res = for {
      user <- getUser
      address <- getAddress
    } yield user.copy(address = address) // такая операция копирования будет выполнятся только при условии наличия юзера (при пустом юзере такое выражение выполняться не будет)

    assert(res.isEmpty)
  }

  test("") {
    :: (1, Nil) // это вызов конструктора для кейс-класса
    ::.apply(1, Nil)

    1 :: Nil    // в даном случае это будет уже метод на объекте

    def compressRecursive[A] (ls: List[A]): List[A] = ls match {
      case Nil => Nil
      case h :: tail => h :: compressRecursive(tail.dropWhile(_ == h))
    }
  }

}
