package scalaz.logging

/** Describe logging action in effect type **/
trait Logger[F[_]] {
  def log[A: Loggable](level: Level)(a: A)(implicit f: Formatter): F[Unit]

  final def trace[A: Loggable](a: A)(implicit f: Formatter): F[Unit] = log(Trace)(a)
  final def debug[A: Loggable](a: A)(implicit f: Formatter): F[Unit] = log(Debug)(a)
  final def info[A: Loggable](a: A)(implicit f: Formatter): F[Unit]  = log(Info)(a)
  final def warn[A: Loggable](a: A)(implicit f: Formatter): F[Unit]  = log(Warning)(a)
  final def error[A: Loggable](a: A)(implicit f: Formatter): F[Unit] = log(Error)(a)
}
