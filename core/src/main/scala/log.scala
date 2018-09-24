package scalaz.logging

import java.time.Instant

import scalaz.IList

import sourcecode.{ FullName, Line }

/** Actual log object **/
sealed trait Log
final case class LogLine(value: String)                       extends Log
final case class LogObject(value: IList[Log], origin: Origin) extends Log

object Log {

  def apply(values: String*)(implicit fullName: FullName, line: Line) =
    new LogObject(IList(values: _*).map(LogLine.apply), Origin(fullName, line, Instant.now))

  def apply(value: IList[Log])(implicit fullName: FullName, line: Line) =
    new LogObject(value, Origin(fullName, line, Instant.now))
}
