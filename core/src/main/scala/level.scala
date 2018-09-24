package scalaz.logging

import scalaz.Show

/** Log levels **/
sealed trait Level
case object Trace   extends Level
case object Debug   extends Level
case object Info    extends Level
case object Warning extends Level
case object Error   extends Level

object Level {
  implicit val LevelShow: Show[Level] = new Show[Level] {
    override def shows(l: Level) = l match {
      case Trace   => "[TRACE]"
      case Debug   => "[DEBUG]"
      case Info    => "[INFO]"
      case Warning => "[WARN]"
      case Error   => "[ERROR]"
    }
  }
}
