package com.itea.monads

import math.Ordering

/**
  * Implicit Parameters
  * ***********************
  * @see https://www.youtube.com/watch?v=ieo9pV-0zEY
  */

object ImplicitApp extends App {

  // #1.
//  def msort(xs: List[Int]): List[Int] = {
//    val n = xs.length / 2
//    if (n == 0) xs
//    else {
//      def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
//        case(Nil, ys) => ys
//        case(xs, Nil) => xs
//        case (x :: xs1, y :: ys1) =>
//          if (x < y) x :: merge(xs1, ys)
//          else y :: merge(xs, ys1)
//      }
//
//      val (fst, snd) = xs.splitAt(n)
//      merge(msort(fst), msort(snd))
//    }
//  }
//
//  val nums = List(2, -4, 5, 7, 1)
//  println( msort(nums) ) // TODO List(-4, 1, 2, 5, 7)

  // #2.
//  def msort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
//    val n = xs.length / 2
//    if (n == 0) xs
//    else {
//      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
//        case(Nil, ys) => ys
//        case(xs, Nil) => xs
//        case (x :: xs1, y :: ys1) =>
//          if (lt(x,y)) x :: merge(xs1, ys)
//          else y :: merge(xs, ys1)
//      }
//
//      val (fst, snd) = xs.splitAt(n)
//      merge(msort(fst)(lt), msort(snd)(lt))
//    }
//  }
//
//  val nums = List(2, -4, 5, 7, 1)
////  println( msort(nums)((x: Int, y: Int) => x < y) ) // TODO List(-4, 1, 2, 5, 7)
//  println( msort(nums)((x, y) => x < y) ) // TODO List(-4, 1, 2, 5, 7)
//
//  val fruits = List("apple", "pineapple", "orange", "banana")
////  println( msort(fruits)((x: String, y: String) => x.compareTo(y) < 0) ) // TODO List(apple, banana, orange, pineapple)
//  println( msort(fruits)((x, y) => x.compareTo(y) < 0) ) // TODO List(apple, banana, orange, pineapple)

  // #3.1
//  def msort[T](xs: List[T]) (ord: Ordering[T]): List[T] = {
//    val n = xs.length / 2
//    if (n == 0) xs
//    else {
//      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
//        case(Nil, ys) => ys
//        case(xs, Nil) => xs
//        case (x :: xs1, y :: ys1) =>
//          if (ord.lt(x,y)) x :: merge(xs1, ys)
//          else y :: merge(xs, ys1)
//      }
//
//      val (fst, snd) = xs.splitAt(n)
//      merge(msort(fst)(ord), msort(snd)(ord))
//    }
//  }
//
//  val nums = List(2, -4, 5, 7, 1)
//  println( msort(nums)(Ordering.Int) ) // TODO List(-4, 1, 2, 5, 7)
//
//  val fruits = List("apple", "pineapple", "orange", "banana")
//  println( msort(fruits)(Ordering.String) ) // TODO List(apple, banana, orange, pineapple)

  // #3.2
  def msort[T](xs: List[T]) (implicit ord: Ordering[T]): List[T] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
        case (Nil, ys) => ys
        case (xs, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (ord.lt(x,y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }

      val (fst, snd) = xs.splitAt(n)
      merge(msort(fst), msort(snd))
    }
  }

  val nums = List(2, -4, 5, 7, 1)
  println( msort(nums) ) // TODO List(-4, 1, 2, 5, 7)

  val fruits = List("apple", "pineapple", "orange", "banana")
  println( msort(fruits) ) // TODO List(apple, banana, orange, pineapple)

}
