package ticTacToe

import ticTacToe.models.Game
import ticTacToe.views.{CoordinateView, GameView, GestorIO}

object Main {

  var game = new Game
  // se debe pedir si es demo, los dos jugadores son automaticos o son humanos
  //utilizar traits para hacerlo
  def main(args: Array[String]): Unit = {
    GameView.write(game)
    //este while se tiene que hacer como actores
    //o se pasan el valor del trablero entre los actores
    //o hay un tercer actor que gestiona el tablero
    do {
      if (!game.isComplete){
        game = game.put(CoordinateView.read)
      } else {
        game = game.move(CoordinateView.read, CoordinateView.read)
      }
      GameView.write(game)
    } while (!game.isTicTacToe)
    GestorIO.write("... pero has perdido")
  }
}
