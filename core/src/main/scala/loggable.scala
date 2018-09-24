package scalaz.logging

/** Turn an object into a log **/
trait Loggable[A] {
  def log(a: A): Log
}
