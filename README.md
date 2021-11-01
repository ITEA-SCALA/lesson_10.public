# Dan Rosen: Scala Monads

* В этом видеоуроке Дэн Розен покажет вам, как использовать возможности Scala для монадического проектирования, чтобы исключить повторяющийся шаблон в вашем коде.
    * `Tagir: Опциональные значения, Монады` **(** [HelloScala1~Main22](https://github.com/Home-SCALA3/MyHelloScala1/blob/master/src/main/scala/example/Main22.scala) **)** https://groz.github.io/scala/intro/monads
    * `Martin Odersky: Implicit Parameters` **(** [HelloScala1~Main33](https://github.com/Home-SCALA3/MyHelloScala1/blob/master/src/main/scala/example/Main33.scala) **)** https://www.youtube.com/watch?v=ieo9pV-0zEY
    * **(** [HelloScala1](https://github.com/ITEA-SCALA/HelloScala1) **)**  https://www.youtube.com/watch?v=Mw_Jnn_Y5iA

## Scala with Cats (Scala – Introduction to Cats)

* `Конспект по Scala with Cats` (Ru): https://blog.maizy.ru/posts/scala-cats-summary
* `Noel Welsh and Dave Gurnell: Scala with Cats` (Ru): https://fpspecialty.github.io/scala-with-cats/scala-with-cats.html
    * https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox/KtbxLvHgRFNsWGQDLNTjRKkCQjgxmhNWsB
* `Lightbend for Scala`: https://github.com/ITEA-SCALA/LessonCatsZIO
* [Cats](https://typelevel.org/cats)
  * `cats-kernel`: Small set of basic type classes (required).
  * `cats-core`: Most core type classes and functionality (required).
  * `cats-laws`: Laws for testing type class instances.
  * `cats-free`: Free structures such as the free monad, and supporting type classes.
  * `cats-testkit`: lib for writing tests for type class instances using laws.
  * `algebra`: Type classes to represent algebraic structures.
  * `alleycats-core`: Cats instances and classes which are not lawful.


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

How to structure code
+---+-------------------------+
| 1 | N-layer app (PoEAA)     |
+---+-------------------------+
| 2 | DDD                     |
+---+-------------------------+
| 3 | FP - Algebra            |
+---+-------------------------+

N-layer
-------
PoEAA (Patterns of enterprise application architecture layers) - в центре такой модели находится база, вокруг нее доменная модель, дальше слой-сервиса и вверху контроллер.
Поэтому, в такой модели в контроллер можно можно передавать только сервисный слой.

DDD (three layer architecture) - когда друг на друга слои ссылаются и дальше например ходят в базу
---
Side Effects: Функции легко тестируются, побочные эффекты тяжелые
Pure - все побочные эффекты (Side Effects) стараются вынести как можно ближе к середине доменной модели.
Как такового слоя "контролера" нету, но есть "роуты" которые принимают функцию (Side Effects).

Все зависимости с базой данных и прочие побочные эффекты вынесенны куда-то на перефирию.
Слой приложения - в центр такой доменной модели вынесены все сервисы.

Все функции и сервисы в такой модели будут возвращать Future.

Друг на друга слои ссылаются и идут куда нибудь в базу данных
1. Presentation layer
  - Akka HTTP
2. Business layer
  - преобразование на Future-ах
3. Data access layer
  - что-либо написаное с помощью стека
    Data Source


Scala Algebra
-------------
Algebra очень похоже на сервис, норазница в том что вместо Future будет F[_]

def slickCall: Futute[Int] = ???

case class User(age: Int)

/**
* такой способ реализовать код называют - "Tagless final" - он самый сложный
* не смотря на то что тип F[_] мы можем здесь использовать Future и их монадные методы (map/flatMap)
  */
  trait UserAlgebra[F[_]] {
  def createNewUser(u: User): F[Unit]
  def deleteUser(id: Int): F[Boolean]
  }

Когда мы будем реализовывать этот трейт, он будет лежать где-то в доменной модели, потому что это то что мы можем сделать с User-м.

// class UserService[F[_]: Monad] extends UserAlgebra[F] {
class UserService[F[_]: implicit val m: Monad] extends UserAlgebra[F] { // это тоже самое (IDEA предложит подставить Monad из Cats), теперь здесь всегда будут монадные методы (map/flatMap)
override def createNewUser(u: User): F[Unit] = { // также здесь можно использовать Future, потому что в Cats также есть эффект FutureMonad и благодаря такой записи теперь можно так
for {
_ <- deleteUser(id=1)
_ <- createNewUser(User(1))
} yeild ()
}

	override def deleteUser(id: Int): F[Boolean] = ???
}



'Microservices' vs 'Modular monolith architecture'






Если так подумать, по сути так получается что само бекенд-приложение на самом деле не далает никаких вычислений над пользовательскими данными.

То есть
- в базе хранится состояние компонента
- на веб-страничке выполняются какие-либо логические действия ... пользователь что-то из чего-то выбирает

Пользовательские данные в запросе попадают на серверную часть приложения.
А здесь пользовательские данные транзитом передаются в базу данных.

Еще можно отметить сервисный-слой, который выступает связывающим звеном между слоем контроллера и моделью базы данных...
И это все работает в случае идеальной, хорошо продуманной архитектуры приложения, включая саму схему модели таблиц в базе.
Такой способ очень хорошо применяется в стартапах.

Обычно делают монолит и этого вполне хватает, ровно до того момента, когда бизнес решает вмешаться и обновить свое ранее утвержденное ТЗ.
И что еще хуже, когда бизнес предлагает свои обновления не все сразу ... а маленькими порциями.
И начинается: тут-там возьмем перепишем, а дальше одно уже не накладывается на другое и нужно полностью переписывать модуль но времени на это уже нехватает.

Часто приходилось встречать очень разросшуюся монолитную структуру.
Работать с таким кодом очень тяжело, особено в режиме сопровождения, прежде всего из-за объема кодовой базы, и размытых границ между модулями на которые ложатся определенные задачи...
Может быть по этому работать с микросервисами кажется удобнее - меньше кода в каждом, легче тестировать функционал и запоминать их задачи.
И самое главное, как для меня - в случае для таких веб-сервисов, которые объязаны круглосуточно находиться в рабочем состоянии - возможность отдельного обновления 1-микросервиса но при этом сам веб-сервис приложения никогда не выключается.


Мне кажеться, DDD архитектура больше всего применяется для разработки десктопных приложений.
По сути там тоже есть Функции с побочными эффектами ... и обычно их реаализация всегда находться в одной области.
А дальше есть какой-нибудь оконный UI, в котором собственно и сконцентрировано вызов этих функций.

И если я не ошибаюсь, в таком же стиле ведеться разработка для мобильных приложений.

Резюмируя эти способы, я думаю следующее:

1. Вариант: Веб-модуль (Angular) + N-layer (Modular monolith) + перефирия (db)
   С такой архитектурой всегда будет легко работать, даже если веб-модуль будет реализован через Thymeleaf+Spring или через Thymeleaf+Play или через Angular.
   Из-за своего сквозного функционала, потому что компоненты внутри N-layer (Modular monolith) практически не должны пересекаться между собой.

2. Вариант: UI (swing) + DDD
   С такой архитектурой тоже всегда будет легко работать, потому что функции, внутри доменного слоя, также практически не должны пересекаться между собой.
   Напримере десктопных приложений...
   Еще такие функции, внутри доменного слоя, обновляются практически редко.

3. Вариант: REST + N-layer (Modular monolith) + перефирия (db)
   Обычно это разрабатываемые веб-сервисы, которые могут обростать и изменять свои бизнес-требования.
   С разработкой такого рода серверных приложений всегда встречаются сложности:
  - громоздкая кодовая база
  - иногда бизнес-требования бывают не совместимы со структурой компонентов серверного приложения
  - иногда реализацию действий бизнес-логики хардкодят, например прямо в сервисном-слое, - что может привести к ошибочным последствиям...
  - обновление такого серверного приложения или его модулей в горячем-режиме
    Думаю по этой причине логику связанную например с: валидацией полей в запросе, аутентификация, роллевая политика, DTO-данные, формат возвращения результа типа-ощибок ... абстрагируют и выделяют из основного процесса, чтобы правильно обрабатывать бзнес-логику


---

EXCEPTION thrown from instance2 (when instance1 is already running). I even tried always setting the port and bind-port to 9999 but not matter what it always trys to bind to 2552. I am wondering if I am using this dynamic config incorrectly.

Caused by: org.jboss.netty.channel.ChannelException: Failed to bind to: /192.168.0.208:2552 Caused by: java.net.BindException: Address already in use


---

[Ссылка на запись 10 занятия](https://us02web.zoom.us/rec/play/X6rhLI1K9e9MnudaW98d_Yl8LqChsXjQukvO0obsQ6NP9VnWl0DcENiRCOJJ0eo0JpgP7JfBk1gOi9RO.XDNUdMDJS2YRiV6K?continueMode=true&_x_zm_rtaid=2JJfHmGDRVmnAdxJzdP76A.1635693445906.d722d020dde626ef63a12833306dc9c8&_x_zm_rhtaid=204)

* `Scala Docs` https://github.com/Home-SCALA2/docs

