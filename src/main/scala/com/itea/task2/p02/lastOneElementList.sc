/**
  * (99 Проблем в Scala) Урок #02 - Найти предпоследний элемент списка
  * @see https://www.thedigitalcatonline.com/blog/2015/04/07/99-scala-problems-02-find-last-nth
  */

/*
 * Теоретически проблема очень проста:
 * мы должны в цикле или рекурсивно вызывать функцию, пока мы найдем последний элемент, тогда предпоследний элемент станет последний прочитанным элементои
 */

/*
 * Процедурное решение
 * ***
 * Cпособ 1: предпоследний элемент списка из n-элементов - является последним элементом из первых (n - 1) элементов
 * В Scala есть метод init(), который возвращает все элементы, кроме последнего.
 * Если список пуст, тогда метод выдает метод UnsupportedOperationException, рассмотрим случай с пустым списком.
 * А если список содержит единственный элемент тогда init() возвращается пустой список, и тем самым выбросит правильное исключение.
 */
def penultimate1[A] (ls: List[A]): A = {
  if (ls.isEmpty) throw new NoSuchElementException
  ls.init.last
}

penultimate1( List(0,1,2,3,4,5,6) ) // val res0: Int = 5

/*
 * Cпособ 2: cписок может быть обработан с помощью метода takeRight() - он выбирает последние n-элементов из списка.
 * А последний n-й элемент становится заголовком этого списка
 */
def lastNth1[A] (i: Int, ls: List[A]): A = {
  if (i <= 0) throw new IllegalArgumentException
  if (i > ls.length) throw new NoSuchElementException
  ls.takeRight(i).head
}

lastNth1( 2, List(0,1,2,3,4,5,6) ) // val res1: Int = 5


/*
 * Рекурсивное решение
 * ***
 * Cпособ 1:
 * Случай выхода - когда список состоит только из элементов заголовка и хвоста
 * Синтаксис сопоставления который может выразить эту ситуацию   `head :: List(tail)`
 *
 * Стандартный случай - когда хвостовой элемент охватывает все крайние элементы
 */
def penultimate2[A] (ls: List[A]): A = ls match {
  case head :: List(tail) => head
  case _ :: tail => penultimate2(tail)
  case _ => throw new NoSuchElementException
}

penultimate2( List(0,1,2,3,4,5,6) ) // val res0: Int = 5

/*
 * Cпособ 2:
 * Через вспомогательную функцию, которая подсчитывает количество n-элементов в списке, а затем находит n-й элемент
 * Использовать условные выражения с защитой, которые управляют применением шаблона
 */
def lastNth2[A] (i: Int, ls: List[A]): A = ls match {
  case tail if (tail.length == i) => tail.head
  case _ :: tail => lastNth2(i, tail)
  case _ => throw new NoSuchElementException
}

lastNth1( 2, List(0,1,2,3,4,5,6) ) // val res1: Int = 5