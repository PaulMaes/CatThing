class Credits {

  Sprite playBttn;
  String playName = "play.png"; 

  Credits() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 200));
  }
  void draw() {
    gameStatesCredits();
    text("Credits", 0, 10);
    if (playBttn != null) playBttn.draw();
  }
}
void gameStatesCredits() {
  if (Keys.ENTER) {
    menu = new Menu();
    credits = null;
  }
}

