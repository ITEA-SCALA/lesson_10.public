# Небольшое введение в Scalatest

* https://habr.com/ru/post/209578

**ScalaTest** — ( www.scalatest.org ) это фреймворк для тестирования приложений, поддерживающий разные стили написания тестов.

Каждый из поддерживаемых стилей тестирования в **Scalatest** создан для использования в определенных целях.

---

Для использования каждого из стилей тестирования, необходимо создать класс, который будет реализовывать trait, в котором определён этот стиль тестирования.

Выбранный стиль определяет только то, как выглядят декларации тестов, все остальные возможности фреймворка будут работать одинаково, вне зависимости от того, какой из стилей тестирования был выбран.

---

### FlatSpec

**FlatSpec** — используют для юнит-тестов и для интеграционного тестирования.

**FlatSpec** это DSL позволяющий писать тесты в виде как можно более приближённом к написанию спецификации поведения тестируемого класса.

- **Assertions** в каждом стиле по умолчанию доступно 3 ассерта:
    - `assert` — для обычных проверок
    - `assertResult` — для проверки совпадения полученного и ожидаемого результата
    - `intercept` — для проверки что метод бросает ожидаемое исключение
- **Matchers**
    - `be` — одно из ключевых слов, которое можно использовать если подключить миксин *Matchers* в класс (который реализует тест)
- **Равенство** размер объекта, длина объекта
- **Проверка строк**
- **Проверка чисел**
- **Проверка булевых свойств**
- **Коллекции**
- **Свойства класса**
- **Соединение проверок логическими функциями**


### FeatureSpec

**FeatureSpec** — используют для приемочного тестирования.

**FeatureSpec** нацелен на создание приемочных тестов, облегчая программистам задачу работающим с не тестировщиками.




Behavior-Driven development (BDD) стиль тестирования.
---------------------------
Одной из основных идей BDD является то, что тесты могут использоваться для:
- содействия обмену мнениями между людьми, 
- принимающими решения о характере поведения программных средств,
- людьми, разрабатывающими программные средства, 
- и людьми, определяющими степень завершенности и работоспособности программных средств.

Domain-Specific language (DSL) стиль тестирования = Предметно-ориентированный язык
------------------------

* http://uchcom7.botik.ru/L/prog/java/Scala.prof.prog.pdf

### (#386) Спецификация и тестирование поведения с помощью ScalaTest FlatSpec

1. Сначала в виде строки пишется название тестируемого субъекта (**"A UniformElement"**)
2. Затем **should** или **must** или **can** (что означает *«обязан»* или *«должен»* или *«может»*)
3. Потом строка обозначающая характер поведения требуемого от субъекта (**"have a width equal to the passed value"**)
4. А после — ключевое слово **in**
5. В фигурных скобках после **in** пишется код, тестирующий указанное поведение

```scala
"A UniformElement" should "have a width equal to the passed value" in {
    val ele = elem('x', 2, 3)
    ele.width should be(2)
}
```

6. *В последующих директивах, чтобы сослаться на самый последний субъект*, можно написать **it**

```scala
"A UniformElement" should "have a width equal to the passed value" in {
  val ele = elem('x', 2, 3)
  ele.width should be(2)
}

it should "have a height equal to the passed value" in {
    val ele = elem('x', 2, 3)
    ele.height should be(3)
}
```

Подмешиванием трейта **Matchers** можно создавать утверждения, которые больше похожи на естественный язык.

Средства выявления соответствий включают синтаксис: '**should be**' и '**an** [ ] **should be thrownBy** { }'

```scala
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import Element.elem

class ElementSpec extends FlatSpec with Matchers {

  "A UniformElement" should "have a width equal to the passed value" in {
    val ele = elem('x', 2, 3)
    ele.width should be(2)
  }

  it should "have a height equal to the passed value" in {
    val ele = elem('x', 2, 3)
    ele.height should be(3)
  }

  it should "throw an IAE if passed a negative width" in {
    an[IllegalArgumentException] should be thrownBy {
      elem('x', -2, 3)
    }
  }

}
```

При выполнении трейт *FlatSpec* будет запускать каждую директиву спецификатора в виде теста *ScalaTest*
*FlatSpec* генерирует вывод, который при запуске читается как спецификация. Например:

```text
A UniformElement
- should have a width equal to the passed value
- should have a height equal to the passed value
- should throw an IAE if passed a negative width
```


Если предпочтение отдается глаголу **must**, (а не глаголу **should**) тогда можно подмешать **MustMatchers**. Например, выражения:

```scala
result must be >= 0							
map must contain key 'c'
```

Если последнее утверждение не подтвердится, будет показано сообщение об ошибке следующего вида:

```text
Map('a' -> 1, 'b' -> 2) did not contain key 'c'
```


### (#389) Спецификация и тестирование поведения с использованием среды specs2

Среда тестирования **specs2** — это средство с открытым кодом, написанное на Scala, также поддерживает BDD-стиль тестирования, но с другим синтаксисом.

В specs2, как и в ScalaTest, существует DSL-язык выявления соответствий, в строках содержащих **must be_==** и **must throwA**.

```scala
import org.specs2._
import Element.elem

object ElementSpecification extends Specification {

  "A UniformElement" should {
    "have a width equal to the passed value" in {
      val ele = elem('x',2,3)
      ele.width must be_==(2)
    }

    "have a height equal to the passed value" in {
      val ele = elem('x',2,3)
      ele.height must be_==(3)
    }

    "throw an IAE if passed a negative width" in {
      elem('x',-2,3) must throwA[IllegalArgumentException]
    }
  }

}
```


### (#391) Использование тестов для содействия обмену мнениями среди всех заинтересованных сторон

Среду **specs2** можно использовать в автономном режиме, но она также интегрируется со **ScalaTest** и **JUnit**, поэтому specs2-тесты можно запускать и с этими инструментальными средствами.

В *ScalaTest* есть специально разработанный для этого — стиль *FeatureSpec*.

**FeatureSpe** — разработан с целью направления в нужное русло обсуждений предназначения программных средств: вам следует выявить специфические особенности, а затем дать им точное определение в понятиях сценариев.

1. Сосредоточиться на переговорах об особенностях отдельно взятых сценариев помогают методы **Given**, **When** и **Then**, предоставляемые трейтом **GivenWhenThen**.
2. Вызов **pending** в самом конце показывает, что как тест, так и само поведение не реализованы, имеется лишь спецификация.
3. Как только будут реализованы все тесты и конкретные действия, тесты будут пройдены и требования можно будет посчитать выполненными.

Пример его использования

```scala
import org.scalatest._

class TVSetSpec extends FeatureSpec with GivenWhenThen {

    feature("TV power button") {
        scenario("User presses power button when TV is off") {
            Given("a TV set that is switched off")
            When("the power button is pressed")
            Then("the TV should switch on")
            pending
        }
    }

}
```


### (#391) Тестирование на основе свойств

**ScalaCheck** — среда с открытым кодом, позволяет указывать свойства, которыми должен обладать тестируемый код.

**WordSpec** — является классом стиля в *ScalaTest*.

Трейт **PropertyChecks** — предоставляет несколько методов '**forAll**' позволяющих смешивать тесты:
- *на основе проверки наличия свойств*
- с традиционными тестами (*на основе утверждений или на основе выявления соответствий*)

Свойства *ScalaCheck* выражены в виде значений функций, получающих в качестве параметров данные, необходимые для утверждений о наличии свойств.

Директива '**whenever**' — указывает на то, что при каждом вычислении *левостороннего выражения* в `true` *правостороннее выражение* также должно содержать `true`.

Для каждого свойства *ScalaCheck* создает данные и выдает утверждения, проверяющие наличие тех или иных свойств.
Пример использования *ScalaCheck* из *ScalaTest WordSpec*, в который подмешан трейт *PropertyChecks*

```scala
import org.scalatest.WordSpec
import org.scalatest.prop.PropertyChecks
import org.scalatest.MustMatchers._
import Element.elem

class ElementSpec extends WordSpec with PropertyChecks {

  "elem result" must {
    "have passed width" in {
      forAll { (w: Int) =>
        whenever (w > 0) {
          elem('x',w,3).width must equal (w)
        }
      }
    }
  }

}
```

---
 [Небольшое введение в Scalatest](https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox/QgrcJHrntPsZwDgqmpkQwHpZpFsNtxwQqHB)
 