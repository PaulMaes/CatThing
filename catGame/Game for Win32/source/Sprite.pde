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
  void draw() {   
    pushMatrix();
    translate(position.x, position.y);
    rotate(rotation);
    scale(scale);
    image(img, -origin.x, -origin.y);
    
    popMatrix();
  }
  
}
