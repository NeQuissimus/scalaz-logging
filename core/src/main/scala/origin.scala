package scalaz.logging

import java.time.Instant

import scalaz.Show

import sourcecode.{ FullName, Line }

/** Log object meta information **/
final case class Origin(fullName: FullName, line: Line, time: Instant)

object Origin {
  implicit val originShow: Show[Origin] = new Show[Origin] {
    override def shows(o: Origin): String =
      s"""${o.time.toString} - ${o.fullName.value}:${o.line.value}"""
  }
}
