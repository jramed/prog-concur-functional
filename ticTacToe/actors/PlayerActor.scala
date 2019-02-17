package ticTacToe.actors

import akka.actor.Actor
import ticTacToe.{NextMovement, StopGame}
import ticTacToe.models.Game
import ticTacToe.traits.GenericCoordinateView
import ticTacToe.views.{GameView, GestorIO}

abstract class PlayerActor(coordinateView: GenericCoordinateView) extends Actor {
  protected def playGame(game: Game):Game = {
    if (!game.isComplete) {
      val newGame = game.put(coordinateView.read)
      GameView.write(newGame)
      newGame
    } else {
      val newGame = game.move(coordinateView.read, coordinateView.read)
      GameView.write(newGame)
      newGame
    }
  }

  protected def playGameAndCheck(game: Game):Unit = {
    val newGame = playGame(game)

    if (!newGame.isTicTacToe) {
      sender ! NextMovement(newGame)
    }
    else {
      sender ! StopGame(newGame)
      GestorIO.write("... pero has perdido\n")
      context.stop(self)
    }
  }

  protected def stopGame(): Unit = {
    context.stop(self)
    context.system.terminate()
  }
}

