class Options {

  Sprite playBttn;
  String playName = "play.png"; 

  Options() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 150));
  }
  void draw() {
    gameStatesOptions();
    text("Options", 0, 10);
    if (playBttn != null) playBttn.draw();
  }
}
void gameStatesOptions() {
  if (Keys.ENTER) {
    menu = new Menu();
    options = null;
  }
}

