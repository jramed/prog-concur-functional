package ticTacToe.views

object ColorView {

  def getChar(color:Int):Char =
    color match {
      case -1 => '.'
      case 0 => 'x'
      case 1 => 'o'
    }
}
