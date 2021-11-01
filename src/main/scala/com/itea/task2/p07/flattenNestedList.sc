/**
  * (99 Проблем в Scala) Урок #07 - Выровнять структуру для вложенного списка
  * @see https://www.thedigitalcatonline.com/blog/2015/04/07/99-scala-problems-07-flatten
  */

/*
 * Нужно возможность отделить элемент от списка, не являющегося списком
 * Здесь придется иметь дело с типизированными шаблонами.
 */

/*
 * Рекурсивное решение
 * ***
 * Метод flatten() списка работает только если список содержит проходимую коллекцию
 */
def flatten[A](ls: List[A]): List[A] = ls match {
  case Nil => Nil
  case (h: List[A])::tail => flatten(h):::flatten(tail)
  case (h: A)::tail => h::flatten(tail)
}

//List(List(1,2), List(3,4), 5).flatten

flatten( List(List(1,2), List(3,4), 5) ) // val res0: List[Any] = List(1, 2, 3, 4, 5)
