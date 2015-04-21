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

    speed = .5;
  }
  void update() {
    cloud1.position = cloudPosition1;
    cloud2.position = cloudPosition2;
    cloudPosition1.x -= speed;
    cloudPosition2.x -= speed;

    if (cloudPosition1.x <= -width)cloudPosition1.x = width;
    if (cloudPosition2.x <= -width)cloudPosition2.x = width;
  }
  void draw() {
    cloud1.draw();
    cloud2.draw();
  }
}

