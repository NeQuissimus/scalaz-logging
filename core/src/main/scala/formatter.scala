package scalaz.logging

import scalaz.IList

/** Format a log object into a list of lines **/
trait Formatter {
  def format(l: Log): IList[String]
}
