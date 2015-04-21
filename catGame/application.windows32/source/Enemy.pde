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
    time += random(2.5);
    println(time);
    enemy = new Sprite(loadImage("dogcats.png"), position);
  }
  void update() {
    time += .01;
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
  void sinMove() {
    position.y += sin(time * 2);
  }
  void takeDamage(int dmg) {
    health -= dmg;
  }
  void draw() {
    //fill(255);
    //rectMode(CORNERS);
    //rect(position.x - 10,position.y - 10,position.x + 10,position.y + 10);

    aabb.draw();
    enemy.draw();
  }
}

