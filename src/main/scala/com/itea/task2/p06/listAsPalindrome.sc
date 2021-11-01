/**
 * (99 Проблем в Scala) Урок #06 - Выяснить является ли список палиндромом
 * @see https://www.thedigitalcatonline.com/blog/2015/04/07/99-scala-problems-06-palindome
 */

/*
 * Процедурное решение
 */
def isPalindrome1[A] (ls: List[A]): Boolean = {
  ls == ls.reverse
}

val res1 = isPalindrome1( List(0,1,2,3,4,5,6) ) //TODO   val res5: Boolean = false
val res2 = isPalindrome1( List(0,1,2,3,2,1,0) ) //TODO   val res5: Boolean = true


/*
 * Рекурсивное решение
 * ***
 * Процедурное решение для длинных списков это может быть проблемой производительности, поэтому рекурсивное решение может быть интересным
 * Достаточно проверить, равны ли голова и хвост, затем удалить их
 * и рекурсивно проверить оставшийся список
 * На последнем шаге будет проверяться либо список из одного элемента, либо пустой список (в зависимости от нечетной или четной длины списка)
 */
def isPalindrome2[A] (ls: List[A]):Boolean = ls match {
  case Nil => true
  case List(l) => true
  case list => (list.head == list.last && isPalindrome2(list.tail.init))
}

val res3 = isPalindrome2( List(0,1,2,3,4,5,6) ) //TODO   val res5: Boolean = false
val res4 = isPalindrome2( List(0,1,2,3,2,1,0) ) //TODO   val res5: Boolean = true

/*
 * Рекурсивное решение
 * ***
 * Соответствующая хвостовая рекурсивная функция есть
 */
def isPalindrome3[A] (ls: List[A]):Boolean = {
  def _palindrome(res: Boolean, rem: List[A]): Boolean = rem match {
    case Nil => res
    case List(l) => res
    case list => _palindrome(res && rem.head == rem.last, rem.tail.init)
  }
  _palindrome(true, ls)
}

val res5 = isPalindrome3( List(0,1,2,3,4,5,6) ) //TODO   val res5: Boolean = false
val res6 = isPalindrome3( List(0,1,2,3,2,1,0) ) //TODO   val res5: Boolean = true
