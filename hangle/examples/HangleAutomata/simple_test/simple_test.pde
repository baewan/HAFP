import automata.library.*;

HangleLibrary Hangle = new HangleLibrary (this);
String strValue1 = "gksrmf";
String strValue2 = "dhxhakxk";
void setup() {
  size(400,400);
  smooth();
  
  //Hangle = new HangleLibrary (this);
  PFont font = createFont("",40);
  textFont(font);
  background(0);
  fill(255);
  text(Hangle.hangleAuto(strValue1)+" "
        +Hangle.hangleAuto(strValue2)+" 0.1v", 20, 200);
}

void draw() {
}
