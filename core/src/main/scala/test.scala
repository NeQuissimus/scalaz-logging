package nequi.logtest

import scalaz._
import scalaz.logging._
import Scalaz._

import sourcecode._

final case class Foo(a: Int)
final case class Foo2(a: Foo, b: Foo)
final case class Foo3(abc: String)

object Foo {
  implicit def fooLoggable(implicit fullName: FullName, line: Line) =
    new Loggable[Foo] {
      def log(f: Foo) = Log(f.a.toString)
    }
}

object Foo2 {
  implicit def foo2Loggable(
    implicit
    fullName: FullName,
    line: Line,
    fooLoggable: Loggable[Foo]
  ) = new Loggable[Foo2] {
    def log(f: Foo2) = Log(IList(LogLine("Foo2"), fooLoggable.log(f.a), fooLoggable.log(f.b)))
  }
}

object Foo3 {
  implicit def foo3Loggable(implicit fullName: FullName, line: Line) = new Loggable[Foo3] {
    def log(f: Foo3) = Log(f.abc)
  }
}

object Test {
  type Sync[A] = A

  def main(args: Array[String]): Unit = {
    import Level._
    import Origin._

    implicit val formatter: Formatter = new Formatter {
      def format(l: Log): IList[String] = {
        def format0(l: Log): IList[String] = l match {
          case LogLine(s)           => IList.single(s)
          case LogObject(values, _) => values.flatMap(format0).map(x => s"  ${x}")
        }

        l match {
          case l @ LogObject(_, origin) => origin.shows :: format0(l)
          case x                        => format0(x)
        }
      }
    }

    val logger: Logger[Sync] = new Logger[Sync] {
      def log[A](l: Level)(a: A)(implicit L: Loggable[A], F: Formatter): Sync[Unit] =
        (l.shows :: F.format(L.log(a))).toList.foreach(println)
    }

    logger.debug(Foo(2))
    logger.debug(Foo2(Foo(3), Foo(5)))
    logger.info(Foo3("Hello World"))
  }
}
