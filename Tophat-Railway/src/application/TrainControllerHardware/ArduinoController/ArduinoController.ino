#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include "PinChangeInterrupt.h"

#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 32

// declare OLED display connected to I2C
#define SCREEN_RESET -1
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, SCREEN_RESET);

// input pin declarations
#define NAV_A 2
#define NAV_B 3
#define NAV_C 4
#define NAV_D 5
#define KP_1 7
#define KP_2 8 // temp confirm button
#define KP_3 9 // temp cancel butto
#define KP_4 10
#define EBRAKE 11

// globals (I know they're bad, leave me alone)
int state; // 0 for picking ID, 1 for picking value to change, 2 for changing value, 3 for confirming change
int current_value; // 0 for speed, 1 for ki, 2 for kp, 3 for temperature, 4 for service brake, 5 for lights, 6 for left door, 7 for right door,
// 8 for emergency brake, 9 for id
byte e_brake; // untriggered
byte service_brake;
int id;
int new_value;

void setup() {
  // initialize globals
  state = 0;
  current_value = 0;
  e_brake = 0;
  id = -1; // test ID
  new_value = 0;
  
  Serial.begin(9600);
  Serial.println("DONT STOP THIS TRAIN");

  // set up initial interrupts
  pinMode(NAV_A, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(NAV_A), idUp, FALLING);
  pinMode(NAV_B, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(NAV_B), idDown, FALLING);
  pinMode(NAV_C, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(NAV_C), idDown, FALLING);
  pinMode(NAV_D, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(NAV_D), idUp, FALLING);

  pinMode(KP_2, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(KP_2), idConfirm, FALLING);
  pinMode(KP_3, INPUT_PULLUP);

  pinMode(EBRAKE, INPUT_PULLUP);

  // initialize display
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);

  // clear display buffer
  display.clearDisplay();
  display.display();

  displayStartUp();
}

void loop() {
  // get data
  switch(state){
    case 0:
      displayIdSelection();
      break;
    case 1:
      displayParamChoices();
      break;
    case 2:
      displayValChanges();
      break;
    case 3:
      if(current_value == 0 || current_value == 1 || current_value == 2 || current_value == 3){
        if(new_value){
        byte vals[] = {(byte) current_value, highByte(new_value), lowByte(new_value)};
        Serial.write(vals, 3);
        }
      }
      else if(current_value == 9){
        byte vals[] = {(byte) 9, highByte(id), lowByte(id)};
        Serial.write(vals, 3);
      }
      else if(current_value == 4 || current_value == 5 || current_value == 6 || current_value == 7 || current_value == 8){
        byte vals[] = {(byte) current_value, (byte) new_value};
        Serial.write(vals, 2);
      }
      state = 1;
      attachPCINT(digitalPinToPCINT(NAV_A), paramLeft, FALLING);
      attachPCINT(digitalPinToPCINT(NAV_B), paramLeft, FALLING);
      attachPCINT(digitalPinToPCINT(NAV_C), paramRight, FALLING);
      attachPCINT(digitalPinToPCINT(NAV_D), paramRight, FALLING);
      attachPCINT(digitalPinToPCINT(KP_2), paramConfirm, FALLING); 
      break;     
    default:
      display.clearDisplay();
      display.setTextSize(1);
      display.setCursor(0, 0);
      display.println(F("Congrats, you made it this far!"));
      display.display();
      break;
  }
}

// ISRs
void idUp(){ // state = 0
  id++;
}

void idDown(){
  id--;
}

void idConfirm(){
  detachPCINT(digitalPinToPCINT(NAV_A));
  detachPCINT(digitalPinToPCINT(NAV_B));
  detachPCINT(digitalPinToPCINT(NAV_C));
  detachPCINT(digitalPinToPCINT(NAV_D));
  detachPCINT(digitalPinToPCINT(KP_2));
  state = 1;
  attachPCINT(digitalPinToPCINT(NAV_A), paramLeft, FALLING);
  attachPCINT(digitalPinToPCINT(NAV_B), paramLeft, FALLING);
  attachPCINT(digitalPinToPCINT(NAV_C), paramRight, FALLING);
  attachPCINT(digitalPinToPCINT(NAV_D), paramRight, FALLING);
  attachPCINT(digitalPinToPCINT(KP_2), paramConfirm, FALLING);
}

void STOPTRAIN(){
  if(!e_brake && state > 0){
    byte vals[2] = {0x08, 0x01};
    Serial.write(vals, 2);
    e_brake = 1;
  }
}

void paramLeft(){ // state = 1
  current_value--;
}

void paramRight(){ // state = 1
  current_value++;
}

void paramConfirm(){
  if(!e_brake && current_value == 8) return;
  detachPCINT(digitalPinToPCINT(NAV_A));
  detachPCINT(digitalPinToPCINT(NAV_B));
  detachPCINT(digitalPinToPCINT(NAV_C));
  detachPCINT(digitalPinToPCINT(NAV_D));
  detachPCINT(digitalPinToPCINT(KP_2));
  state = 2;
  new_value = 0;
  attachPCINT(digitalPinToPCINT(NAV_A), valueUp, FALLING);
  attachPCINT(digitalPinToPCINT(NAV_B), valueDown, FALLING);
  attachPCINT(digitalPinToPCINT(NAV_C), valueDown, FALLING);
  attachPCINT(digitalPinToPCINT(NAV_D), valueUp, FALLING);
  attachPCINT(digitalPinToPCINT(KP_2), valueConfirm, FALLING);
}

void valueUp(){
  // if value is numeric, display integers
  // if value is service brake, have enable or disable
    switch(current_value){
      case 0: // speed
        new_value++;
        break;
      case 1: // ki
        new_value++;
        break;
      case 2: // kp
        new_value++;
        break;
      case 3: // temperature
        new_value++;
        break;
      case 4: // service brake
        new_value = !new_value;
        break;
      case 5: // lights
        new_value = !new_value;
        break;
      case 6: // left door
        new_value = !new_value;
        break;
      case 7: // right door
        new_value = !new_value;
        break;
      case 8: // emergency brake
        new_value = !new_value;
        break;
      case 9: // id
        id++;
        break;
      default:
        break;
    }
}

void valueDown(){
    switch(current_value){
      case 0: // speed
        new_value--;
        break;
      case 1: // ki
        new_value--;
        break;
      case 2: // kp
        new_value--;
        break;
      case 3: // temperature
        new_value--;
        break;
      case 4: // service brake
        new_value = !new_value; 
        break;
      case 5: // lights
        new_value = !new_value;
        break;
      case 6: // left door
        new_value = !new_value;
        break;
      case 7: // right door
        new_value = !new_value;
        break;
      case 8: // emergency brake
        new_value = !new_value;
        break;
      case 9: // id
        id--;
        break;
      default:
        break;
    }
}

void valueConfirm(){
  detachPCINT(digitalPinToPCINT(NAV_A));
  detachPCINT(digitalPinToPCINT(NAV_B));
  detachPCINT(digitalPinToPCINT(NAV_C));
  detachPCINT(digitalPinToPCINT(NAV_D));
  detachPCINT(digitalPinToPCINT(KP_2));
  state = 3;
}

void displayStartUp(){
  display.clearDisplay();
  display.setTextSize(3);
  display.setTextColor(WHITE);
  display.setCursor(0, 8);
  display.print(F("L"));
  display.display();
  delay(100);
  display.print(F("O"));
  display.display();
  delay(100);
  display.print(F("A"));
  display.display();
  delay(100);
  display.print(F("D"));
  display.display();
  delay(100);
  display.print(F("I"));
  display.display();
  delay(100);
  display.print(F("N"));
  display.display();
  delay(100);
  display.print(F("G"));
  display.display();
  delay(100);  
}

void displayParamChoices(){
    display.clearDisplay();
    display.setTextSize(1);
    display.setCursor(0, 0);
    switch(current_value){
      case 0: // speed
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Speed"));
        break;
      case 1: // ki
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Ki"));
        break;
      case 2: // kp
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Kp"));
        break;
      case 3: // temperature
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Temperature"));
        break;
      case 4: // service brake
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Service brake"));
        break;
      case 5: // lights
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Lights"));
        break;
      case 6: // left door
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Left door"));
        break;
      case 7: // right door
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Right door"));
        break;
      case 8: // emergency brake
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Emergency brake"));
        break;
      case 9: // id
        display.println(F("Please select a value to change"));
        display.println();
        display.println(F("Train ID"));
        break;
      default:
        current_value = 0;
        break;
    }
    display.display();
}

void displayIdSelection(){
    display.clearDisplay();
    display.setTextSize(1);
    display.setCursor(0, 0);
    display.println(F("Please enter the train ID"));
    display.println();
    display.println(id);
    display.display();
}

void displayValChanges(){
    display.clearDisplay();
    display.setTextSize(1);
    display.setCursor(0, 0);
    switch(current_value){
      case 0: // speed
        display.println(F("New speed:"));
        display.println();
        display.println(new_value);
        break;
      case 1: // ki
        display.println(F("New ki:"));
        display.println();
        display.println(new_value);
        break;
      case 2: // kp
        display.println(F("New kp:"));
        display.println();
        display.println(new_value);
        break;
      case 3: // temperature
        display.println(F("New temperature:"));
        display.println();
        display.println(new_value);
        break;
      case 4: // service brake
        display.println(F("Toggle service brake?"));
        display.println();
        if(new_value) display.println(F("Yes"));
        else display.println(F("No"));
        break;
      case 5: // lights
        display.println(F("Toggle lights?"));
        display.println();
        if(new_value) display.println(F("Yes"));
        else display.println(F("No"));
        break;
      case 6: // left door
        display.println(F("Toggle left door?"));
        display.println();
        if(new_value) display.println(F("Yes"));
        else display.println(F("No"));
        break;
      case 7: // right door
        display.println(F("Toggle right door?"));
        display.println();
        if(new_value) display.println(F("Yes"));
        else display.println(F("No"));
        break;
      case 8: // emergency brake
        display.println(F("Reset emergency brake?"));
        display.println();
        if(new_value) display.println(F("Yes"));
        else display.println(F("No"));
        break;
      case 9: // id
        display.println(F("Please enter new train ID"));
        display.println();
        display.println(id);
        break;
      default:
        current_value = 0;
        break;
    }
    display.display();
}

void serialEvent(){
  if(Serial.available() > 0){
    e_brake = Serial.read();
  }
}
