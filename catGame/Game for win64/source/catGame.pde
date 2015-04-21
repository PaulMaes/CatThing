
Menu menu;
Credits credits;
Options options;
Game game;
GameOver gameOver;
Win win;

boolean isDebug = false;

void setup() {
  size(800, 500);
  menu = new Menu();
}
void draw() {
  background(0);

  gameStatesDraw();
}
void gameStatesDraw() {

  if (menu     != null) menu.draw();
  if (credits  != null) credits.draw();
  if (options  != null) options.draw();
  if (game     != null) game.draw();
  if (gameOver != null) gameOver.draw();
  if (win      != null) win.draw();

  if (isDebug) println("menu "     + menu);
  if (isDebug) println("credits "  + credits);
  if (isDebug) println("options "  + options);
  if (isDebug) println("game "     + game);
  if (isDebug) println("gameOver " + gameOver);
  if (isDebug) println("win "      + win);
}


boolean isMousePressed = false;
void mousePressed() {
  isMousePressed = true;
}
void mouseReleased() {
  isMousePressed = false;
}

