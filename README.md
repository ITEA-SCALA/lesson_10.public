## Scala with Cats (Scala – Introduction to Cats)

* [Конспект по Scala with Cats](https://blog.maizy.ru/posts/scala-cats-summary)
* 1000 разных подходов к определению, монада – механизм для последовательных вычислений операции
  * **.pure(a)** – создание монадического контекста из сырого значения
  * **.flatMap(f)** – извлечение значения из контекста и создание следующего контекста в последовательности
* любая монада – функтор, map легко построить из pure+flatMap
* laws:
  * **левоассоциативность:** pure(a).flatMap(f) == f(a). тут важно помнить об эффектах. именно по этой причине Try не Monad, так как если "снять" с него контекст монады при обычном вызове получим эффект – исключение, а если не снять то получим Failure.
  * **правоассоциативность:** m.flatMap(pure) == m.
  * **ассоциативность:** m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
    * syntax:
      * .pure[T] (из cats.syntax.applicative)
      * .map(f) (из cats.syntax.functor)
      * .flatMap(f) (из cats.syntax.flatMap)
      * можно использовать for comprehensions из scala
  * при определении своих монад
    * .flatMap
    * .pure
    * .tailRecM – оптимизация для вложенных .flatMap вызовов. метод можно делать @tailrec


### Монада — это моноид в категории эндофункторов

* `Виталий Брагилевский * Монады не приговор`: https://www.youtube.com/watch?v=IkXg_mjNgG4

### Реализация
1. должна быть возможность туда поместить значение с помощью фабричных методов или конструкторов
2. применить операцию `map` для преобазования данных
3. и реализовать связывание с помощью операции `flatMap` (которая представляет из себя: применить какую-то операцию но не применять глубоко вложенные контейнеры)

**(** Простейший пример: монада `Option` (Scala), она же `Maybe` (Haskell) **)**

* `Кирилл Бяков (Туту.ру) * О монадах по-человечески`: https://www.youtube.com/watch?v=-aXZnNY2NNw

* `Иван Гришаев * Монады`: https://www.youtube.com/watch?v=5-yjqPQH_fU


---

[Ссылка на запись 9 занятия](https://us02web.zoom.us/rec/share/FVUKfWKF-H11o7QRlHZlW9jM3BqGUXMrbmVquWyEsK-yTF7qgS5JIaXtLbe7P9zc.UrnXL4VigiO9w3XE)

* `Scala Docs` https://github.com/Home-SCALA2/docs

