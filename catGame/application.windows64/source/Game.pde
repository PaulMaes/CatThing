
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
  void draw() {

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
  void interFace() {
    interfaceBar.draw();
    text("Tower HP: " + health, 10, 490);
    text("Money: " + money, 100, 490);
  }

  void debugging() {
    if (isDebug) println("bullets: " + bullets.size());
    if (isDebug) println("enemys: " + enemys.size());
  }
  void gameStatesGame() {
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

