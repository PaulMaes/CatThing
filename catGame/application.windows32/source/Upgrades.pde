class Upgrades {

  Upgrades() {
    
  }
  void update() {
    
  }
  void draw() {
    
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

