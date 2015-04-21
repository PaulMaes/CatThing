class Barrel {

  PVector position = new PVector(80, 230);
  PVector spawnPosition;
  float angle;  
  Sprite barrel;

  Barrel() {
    spawnPosition = new PVector();
    barrel = new Sprite(loadImage("cannoncats.png"), position);
    barrel.origin = new PVector(0, barrel.img.height/2);
    barrel.scale = 1.3;
  }
  void update() {

    angle = atan2(mouseY - position.y, mouseX - position.x);
    barrel.rotation = angle;
  }
  void draw() {

    barrel.draw();
  }
}

