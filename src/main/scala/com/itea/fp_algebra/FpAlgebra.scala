package com.itea.fp_algebra

import cats.Monad

class FpAlgebra {

  case class User(age: Int)

  trait UserAlgebra[F[_]] {
    def createNewUser(u: User): F[Unit]
    def deleteUser(id: Int): F[Boolean]
  }

  class UserService[F[_]: Monad] extends UserAlgebra[F] {
    override def createNewUser(u: User): F[Unit] = {
      for {
        _ <- deleteUser(id=1)
        _ <- createNewUser(User(1))
      } yeild ()
    }

    override def deleteUser(id: Int): F[Boolean] = ???
  }
}
