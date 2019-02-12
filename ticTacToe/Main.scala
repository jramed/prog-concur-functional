package ticTacToe

import ticTacToe.models.Game
import ticTacToe.traits.GenericCoordinateView
import ticTacToe.views.{GameMode, GameView, GestorIO, ManualCoordinateView}

object Main {

  var game = new Game
  // se debe pedir si es demo, los dos jugadores son automaticos o son humanos
  //utilizar traits para hacerlo
  def main(args: Array[String]): Unit = {
    def selectGameMode = {
      val gameMode = GameView.obtainGameMode()
      var coordinateView: GenericCoordinateView = ManualCoordinateView
      gameMode match {
        case mode if mode == GameMode.Demo => {
          println("demo mode selected")
          coordinateView = DemoCoordinateView
        }
        case _ => {
          println("manual mode selected")
          coordinateView = ManualCoordinateView
        }
      }
      coordinateView
    }

    var coordinateView: GenericCoordinateView = selectGameMode

    GameView.write(game)
    //este while se tiene que hacer como actores
    //o se pasan el valor del trablero entre los actores
    //o hay un tercer actor que gestiona el tablero
    do {
      if (!game.isComplete){
        game = game.put(coordinateView.read)
      } else {
        game = game.move(coordinateView.read, coordinateView.read)
      }
      GameView.write(game)
    } while (!game.isTicTacToe)
    GestorIO.write("... pero has perdido")
  }
}
