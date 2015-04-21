class GameOver {

  Sprite playBttn;
  String playName = "play.png"; 

  GameOver() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 250));
  }
  void draw() {
    gameStatesGameOver();

    text("GameOver Press Enter to g Back", 0, 10);
    if (playBttn != null) playBttn.draw();
  }
}
void gameStatesGameOver() {
  if (Keys.ENTER) {
    menu = new Menu();
    gameOver = null;
  }
}

