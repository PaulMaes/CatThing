class Menu {

  Sprite playBttn;
  String playName = "play.png"; 

  Menu() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 50));
  }
  void draw() {
    gameStatesMenu();
    text("Press P to Begin", 0, 10);
    //if (playBttn != null) playBttn.draw();
  }
}
void gameStatesMenu() {  
  if (Keys.P) {
    game = new Game();
    menu = null;
  }
  if (Keys.LEFT) {
    credits = new Credits();
    menu = null;
  }
  if (Keys.RIGHT) {
    options = new Options();
    menu = null;
  }
}

