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
  void update() {
    position.x = position.x + cos(angle / 180 * PI) * speed;
    position.y = position.y + sin(angle / 180 * PI) * speed;
    aabb.update(position);
    cat.position = position;
    
    ///////////////ROTATE THE CAT FROM ANGEL
    cat.rotation = (millis()/ 1000.0);
    if (position.x < -10 || position.y < -10 || position.x > width + 10 || position.y > height + 10) dead = true; //marks the bullet for death in out of bound
  }
  void draw() {
    cat.draw();
    aabb.draw();
  }
}

