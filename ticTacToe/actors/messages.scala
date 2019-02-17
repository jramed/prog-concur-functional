package ticTacToe

import ticTacToe.models.Game

case class StartPlay(game: Game)
case class NextMovement(game: Game)
case class StopGame()
