
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
  void takeDamage(int dmg) {
    health -= dmg;
  }
  void update() {
    if (health <= 0) dead = true;
    aabb.update(position);
    barrel.update();
  }
  void draw() {
    aabb.draw();
    barrel.draw();
    tower.draw();
  }
}

