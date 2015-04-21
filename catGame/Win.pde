class Win {

  Sprite playBttn;
  String playName = "play.png"; 

  Win() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 300));
  }
  void draw() {

    gameStatesWin();
    text("Win Press enter to go back", 0, 10);
    //if (playBttn != null) playBttn.draw();
  }
}
void gameStatesWin() {
  if (Keys.ENTER) {
    menu = new Menu();
    win = null;
  }
}

