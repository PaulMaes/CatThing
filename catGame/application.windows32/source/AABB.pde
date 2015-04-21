class AABB {
  PVector position = new PVector();
  boolean colliding = false;
  float halfW, halfH;
  float xMin, xMax, yMin, yMax;

  AABB() {
  }
  void setSize(float w, float h) {
    halfW = w/2;
    halfH = h/2;
  }
  void update(PVector pos) {
    colliding = false;
    xMin = pos.x - halfW;
    xMax = pos.x + halfW;
    yMin = pos.y - halfH;
    yMax = pos.y + halfH;
  }
  void update(float xMin, float xMax, float yMin, float yMax) {
    colliding = false;
    this.xMin = xMin;
    this.xMax = xMax;
    this.yMin = yMin;
    this.yMax = yMax;
  }
  void draw() {
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

