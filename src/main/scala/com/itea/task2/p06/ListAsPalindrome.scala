package com.itea.task2.p06

/**
 * Является ли список палиндромом.
 * ( Палиндром - это слово или текст, одинаково читающееся в обоих направлениях )
 */

class ListAsPalindrome {
  /*
   * позволяет обрабатывать списки любого типа
   * используем встроенные команды
   * проходит по списку дважды: один раз чтобы перевернуть его, и один раз чтобы проверить равенство
   */
  def usingBuiltInCommands[A](list: List[A]): Boolean = list == list.reverse
}

object ListAsPalindrome {
  def apply(): ListAsPalindrome = new ListAsPalindrome()
}
