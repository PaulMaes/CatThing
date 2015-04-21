import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class catGame extends PApplet {


Menu menu;
Credits credits;
Options options;
Game game;
GameOver gameOver;
Win win;

boolean isDebug = false;

public void setup() {
  size(800, 500);
  menu = new Menu();
}
public void draw() {
  background(0);

  gameStatesDraw();
}
public void gameStatesDraw() {

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
public void mousePressed() {
  isMousePressed = true;
}
public void mouseReleased() {
  isMousePressed = false;
}

class AABB {
  PVector position = new PVector();
  boolean colliding = false;
  float halfW, halfH;
  float xMin, xMax, yMin, yMax;

  AABB() {
  }
  public void setSize(float w, float h) {
    halfW = w/2;
    halfH = h/2;
  }
  public void update(PVector pos) {
    colliding = false;
    xMin = pos.x - halfW;
    xMax = pos.x + halfW;
    yMin = pos.y - halfH;
    yMax = pos.y + halfH;
  }
  public void update(float xMin, float xMax, float yMin, float yMax) {
    colliding = false;
    this.xMin = xMin;
    this.xMax = xMax;
    this.yMin = yMin;
    this.yMax = yMax;
  }
  public void draw() {
    fill(0,255,0);
    rectMode(CORNERS);
    rect(xMin, yMin, xMax, yMax);
  }
  public boolean checkCollision(AABB aabb) {
    if (xMax < aabb.xMin) return false;
    if (xMin > aabb.xMax) return false;
    if (yMax < aabb.yMin) return false;
    if (yMin > aabb.yMax) return false;
    colliding = true;
    aabb.colliding = true;
    return true;
  }
}

class Barrel {

  PVector position = new PVector(80, 230);
  PVector spawnPosition;
  float angle;  
  Sprite barrel;

  Barrel() {
    spawnPosition = new PVector();
    barrel = new Sprite(loadImage("cannoncats.png"), position);
    barrel.origin = new PVector(0, barrel.img.height/2);
    barrel.scale = 1.3f;
  }
  public void update() {

    angle = atan2(mouseY - position.y, mouseX - position.x);
    barrel.rotation = angle;
  }
  public void draw() {

    barrel.draw();
  }
}

static public int dmg = 35;
static float speed;
class Bullet {
  //chabeg out the ellipse for an image
  
  AABB aabb = new AABB();
  PVector position = new PVector();
  boolean dead = false;
  float angle;
  Sprite cat;
  
  Bullet(float x, float y) {
    cat = new Sprite(loadImage("catcats.png"), position);    
    cat.scale = .9f;
    
    position.x = x;
    position.y = y;
    angle = atan2(mouseY - position.y, mouseX - position.x) / PI * 180;
    
    speed = 5;
    
    aabb.setSize(20,20);
  }
  public void update() {
    position.x = position.x + cos(angle / 180 * PI) * speed;
    position.y = position.y + sin(angle / 180 * PI) * speed;
    aabb.update(position);
    cat.position = position;
    
    ///////////////ROTATE THE CAT FROM ANGEL
    cat.rotation = (millis()/ 1000.0f);
    if (position.x < -10 || position.y < -10 || position.x > width + 10 || position.y > height + 10) dead = true; //marks the bullet for death in out of bound
  }
  public void draw() {
    cat.draw();
    aabb.draw();
  }
}

class Clouds {

  Sprite cloud1;
  Sprite cloud2;
  PVector cloudPosition1;
  PVector cloudPosition2;
  float speed;

  Clouds() {
    cloudPosition1 = new PVector(0, 0);
    cloudPosition2 = new PVector(width, 0);
    cloud1 = new Sprite(loadImage("cloudcats.png"), cloudPosition1);
    cloud2 = new Sprite(loadImage("cloudcats.png"), cloudPosition2);
    cloud1.origin = new PVector();
    cloud2.origin = new PVector();

    speed = .5f;
  }
  public void update() {
    cloud1.position = cloudPosition1;
    cloud2.position = cloudPosition2;
    cloudPosition1.x -= speed;
    cloudPosition2.x -= speed;

    if (cloudPosition1.x <= -width)cloudPosition1.x = width;
    if (cloudPosition2.x <= -width)cloudPosition2.x = width;
  }
  public void draw() {
    cloud1.draw();
    cloud2.draw();
  }
}

class Credits {

  Sprite playBttn;
  String playName = "play.png"; 

  Credits() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 200));
  }
  public void draw() {
    gameStatesCredits();
    text("Credits", 0, 10);
    if (playBttn != null) playBttn.draw();
  }
}
public void gameStatesCredits() {
  if (Keys.ENTER) {
    menu = new Menu();
    credits = null;
  }
}

class Enemy {
  PVector position = new PVector();
  PVector speed = new PVector();
  boolean dead = false;
  int health = 100;
  int dmg = 1;
  Sprite enemy;

  AABB aabb = new AABB();

  float time;

  Enemy(float x, float y) {    
    position.x = x;
    position.y = y;

    aabb.setSize(50, 50);
    time += random(2.5f);
    println(time);
    enemy = new Sprite(loadImage("dogcats.png"), position);
  }
  public void update() {
    time += .01f;
    enemy.position = position;

    speed.x = -2 ;
    position.add(speed);

    if (position.y > height + 25) {
      dead = true;
      money -= 25;
    }
    if (health <= 0) dead = true;

    aabb.update(position);
  }
  public void sinMove() {
    position.y += sin(time * 2);
  }
  public void takeDamage(int dmg) {
    health -= dmg;
  }
  public void draw() {
    //fill(255);
    //rectMode(CORNERS);
    //rect(position.x - 10,position.y - 10,position.x + 10,position.y + 10);

    aabb.draw();
    enemy.draw();
  }
}


static float secondsBeforeNextShoot;
static int money;
static int moneyForEnemy;
class Game {

  ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  float shootCounter;
  ArrayList<Enemy> enemys = new ArrayList<Enemy>();
  float enemyCounter = 50;
  float secondsBeforeNextEnemy = 90;
  boolean isUpgrades = false;

  Tower tower;
  Clouds clouds;
  Sprite bg;
  Sprite interfaceBar;
  Upgrades upgrades;

  Game() {
    //playBttn = new Sprite(loadImage(playName), new PVector(width/2, 100));
    tower = new Tower();
    upgrades = new Upgrades();
    secondsBeforeNextShoot = 25;
    moneyForEnemy = 25;
    clouds = new Clouds();
    bg = new Sprite(loadImage("groundcats.png"), new PVector(width/2, height/2));
    interfaceBar = new Sprite(loadImage("barcats.png"), new PVector(width/2, height/2));
  }
  public void draw() {

    bg.draw();
    clouds.update();
    clouds.draw();    

    enemyCounter--;
    if (enemyCounter <= 0) {
      enemys.add(new Enemy(900, random(300) + 100));
      enemyCounter = random(10) + secondsBeforeNextEnemy;
    }
    for (int i = enemys.size () - 1; i >= 0; i--) {
      Enemy e = enemys.get(i);
      e.update();
      e.sinMove();
      e.draw();
      if (e.aabb.checkCollision(tower.aabb)) {
        e.dead = true;
        tower.takeDamage(e.dmg);
      }
      if (e.dead) {
        enemys.remove(i);
        if (e.aabb.checkCollision(tower.aabb) == false) money += moneyForEnemy;
      }
    }
    shootCounter--;
    if (mousePressed && shootCounter <= 0) {
      bullets.add(new Bullet(tower.barrel.position.x, tower.barrel.position.y));
      shootCounter = secondsBeforeNextShoot;
    }

    for (int i = bullets.size ()-1; i >= 0; i--) {
      Bullet b = bullets.get(i);
      b.update();
      b.draw();
      if (enemys.size() > 0) { //more efficiant
        for (int j = enemys.size () - 1; j >= 0; j--) {
          Enemy e = enemys.get(j);
          if (b.aabb.checkCollision(e.aabb) && enemys.size() > 0) {
            b.dead = true;
            e.takeDamage(dmg);
            if (isDebug) println("collision " + j);
          }
        }
      }

      if (b.dead) bullets.remove(i);
    }

    tower.update();
    tower.draw();
    if (tower.dead) {
      gameOver = new GameOver();
      game = null;
    }

    gameStatesGame();
    text("Game", 0, 10);


    interFace();
    debugging();

    if (isUpgrades) {
      upgrades.update();
      upgrades.draw();
    }
    if(Keys.U) isUpgrades = !isUpgrades;
  }
  public void interFace() {
    interfaceBar.draw();
    text("Tower HP: " + health, 10, 490);
    text("Money: " + money, 100, 490);
  }

  public void debugging() {
    if (isDebug) println("bullets: " + bullets.size());
    if (isDebug) println("enemys: " + enemys.size());
  }
  public void gameStatesGame() {
    if (Keys.UP) {
      win = new Win();
      game = null;
    }
    if (Keys.DOWN) {
      gameOver = new GameOver();
      game = null;
    }
  }
}

class GameOver {

  Sprite playBttn;
  String playName = "play.png"; 

  GameOver() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 250));
  }
  public void draw() {
    gameStatesGameOver();

    text("GameOver Press Enter to g Back", 0, 10);
    if (playBttn != null) playBttn.draw();
  }
}
public void gameStatesGameOver() {
  if (Keys.ENTER) {
    menu = new Menu();
    gameOver = null;
  }
}

static class Keys {
  static boolean A = false;
  static boolean W = false;
  static boolean D = false;
  static boolean S = false;
  static boolean P = false;
  static boolean U = false;
  static boolean LEFT = false;
  static boolean RIGHT = false;
  static boolean UP = false;
  static boolean DOWN = false;
  static boolean SPACE = false;
  static boolean ENTER = false;

  ///////////////////////////////// PREVIOUS:
  static boolean PREV_A = false;
  static boolean PREV_W = false;
  static boolean PREV_D = false;
  static boolean PREV_S = false;
  static boolean PREV_P = false;
  static boolean PREV_U = false;
  static boolean PREV_LEFT = false; 
  static boolean PREV_RIGHT = false;
  static boolean PREV_UP = false;
  static boolean PREV_DOWN = false;
  static boolean PREV_SPACE = false;
  static boolean PREV_ENTER = false;
  public static void Update() {
    Keys.PREV_A = Keys.A;
    Keys.PREV_W = Keys.W;
    Keys.PREV_D = Keys.D;
    Keys.PREV_S = Keys.S;
    Keys.PREV_P = Keys.P;
    Keys.PREV_U = Keys.U;
    Keys.PREV_LEFT = Keys.LEFT;
    Keys.PREV_RIGHT = Keys.RIGHT;
    Keys.PREV_UP = Keys.UP;
    Keys.PREV_DOWN = Keys.DOWN;
    Keys.PREV_SPACE = Keys.SPACE;
    Keys.PREV_ENTER = Keys.ENTER;
  }
  public static void Set(int code, boolean pressed) {
    switch(code) {
    case 32:
      Keys.SPACE = pressed;
      break;
    case 37:
      Keys.LEFT = pressed;
      break;
    case 38:
      Keys.UP = pressed;
      break;
    case 39:
      Keys.RIGHT = pressed;
      break;
    case 40:
      Keys.DOWN = pressed;
      break;
    case 65:
      Keys.A = pressed;
      break;
    case 87:
      Keys.W = pressed;
      break;
    case 68:
      Keys.D = pressed;
      break;
    case 83:
      Keys.S = pressed;
      break;
    case 85:
      Keys.U = pressed;
      break;
    case 80:
      Keys.P = pressed;
      break;
    case 10:
      Keys.ENTER = pressed;
      break;
    }
  }
}
public void keyPressed() {
  println(keyCode);
  Keys.Set(keyCode, true);
}
public void keyReleased() {
  Keys.Set(keyCode, false);
}

class Menu {

  Sprite playBttn;
  String playName = "play.png"; 

  Menu() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 50));
  }
  public void draw() {
    gameStatesMenu();
    text("Press P to Begin", 0, 10);
    //if (playBttn != null) playBttn.draw();
  }
}
public void gameStatesMenu() {  
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

class Options {

  Sprite playBttn;
  String playName = "play.png"; 

  Options() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 150));
  }
  public void draw() {
    gameStatesOptions();
    text("Options", 0, 10);
    if (playBttn != null) playBttn.draw();
  }
}
public void gameStatesOptions() {
  if (Keys.ENTER) {
    menu = new Menu();
    options = null;
  }
}

//sprite class all the classes(tabs above) will be useing this class as a templet
class Sprite {
  //variables each variable holds things
  float scale = 1;
  PImage img;
  PVector position = new PVector();
  PVector origin = new PVector();
  float rotation = 0;


  //the words in orange are what is required in other classes for this class to be vaild
  Sprite(PImage img, PVector position) {

    this.img = img;
    this.position.x = position.x;
    this.position.y = position.y;
    origin = new PVector(img.width / 2, img.height / 2);

  }
  public void draw() {   
    pushMatrix();
    translate(position.x, position.y);
    rotate(rotation);
    scale(scale);
    image(img, -origin.x, -origin.y);
    
    popMatrix();
  }
  
}

static int health = 10;
class Tower {

  Sprite tower;
  Barrel barrel = new Barrel();


  PVector position = new PVector(75, 350);
  PVector barrelPosition = new PVector(120, 235);
  boolean dead = false;


  AABB aabb = new AABB();

  Tower() {
    tower = new Sprite(loadImage("towercats.png"), position);
    aabb.setSize(150, 300);
  }
  public void takeDamage(int dmg) {
    health -= dmg;
  }
  public void update() {
    if (health <= 0) dead = true;
    aabb.update(position);
    barrel.update();
  }
  public void draw() {
    aabb.draw();
    barrel.draw();
    tower.draw();
  }
}

class Upgrades {

  Upgrades() {
    
  }
  public void update() {
    
  }
  public void draw() {
    
    fill(127);
    rectMode(CENTER);
    rect(width/2, height/2, 650, 350); // main BG
    rectMode(CORNERS);
    fill(0);
    rect(125, 125, 275, 225); //dmg button 150x100
    rect(125, 275, 275, 375); //fire rate button
    rect(325, 125, 475, 225); //tower hp button
    rect(325, 275, 475, 375); //bullet speed button
    rect(525, 125, 675, 225); //number of bullets button
    rect(525, 275, 675, 375); //back button
    fill(255);
    text("DMG: " + dmg , 140, 150);
    text("Fire Rate: " + secondsBeforeNextShoot , 140, 300);
    text("Tower HP: " + health , 340, 150);
    text("Bullet speed: " + speed , 340, 300);
    text("Money for enemy " + moneyForEnemy , 540, 150);
    text("Back: " + dmg , 540, 300);
  }
}

class Win {

  Sprite playBttn;
  String playName = "play.png"; 

  Win() {
    playBttn = new Sprite(loadImage(playName), new PVector(width/2, 300));
  }
  public void draw() {

    gameStatesWin();
    text("Win Press enter to go back", 0, 10);
    //if (playBttn != null) playBttn.draw();
  }
}
public void gameStatesWin() {
  if (Keys.ENTER) {
    menu = new Menu();
    win = null;
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "catGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
