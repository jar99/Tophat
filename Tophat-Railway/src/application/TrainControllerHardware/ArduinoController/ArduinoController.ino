#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include "PinChangeInterrupt.h"

#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 32

// declare OLED display connected to I2C
#define SCREEN_RESET -1
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, SCREEN_RESET);

// define rotary encoder


// input pin declarations
#define NAV_A 2
#define NAV_B 3
#define NAV_C 4
#define NAV_D 5
#define NAV_CLICK 6
#define EBRAKE 7
#define CONFIRM 8
#define CANCEL 9

// globals (I know they're bad, leave me alone)
byte state;     // 0 for picking ID, 1 for picking value to change, 2 for changing value, 3 for confirming change
byte parameter; // 0 for speed, 1 for ki, 2 for kp, 3 for temperature, 4 for service brake, 5 for emergency brake, 6 for interior lights
                // 7 for exterior lights, 8 for left door, 9 for right door, 10 for driving mode, 11 for id
double value;   // new value of param
char* paramStrings[] = {"Speed", "Ki", "Kp", "Temperature", "Service brake", "Emergency brake", "Interior lights",
                       "Exterior lights", "Left door", "Right door", "Driving mode", "ID"};
char* valueStrings[] = {"New speed:", "New ki", "New kp", "New temperature", "Toggle service brake?", "Reset emergency brake?",
                       "Toggle interior lights?", "Toggle exterior lights?", "Toggle left door?", "Toggle right door?",
                       "Toggle driving mode?", "New ID:"};


void setup() {
  // initialize globals
  state = 0;
  parameter = 0;
  value = 0;
  
  Serial.begin(9600);

  // set up initial interrupts
  pinMode(NAV_A, INPUT_PULLUP); // up
  attachPCINT(digitalPinToPCINT(NAV_A), nav_a, FALLING);
  pinMode(NAV_B, INPUT_PULLUP); // left
  attachPCINT(digitalPinToPCINT(NAV_B), nav_b, FALLING);
  pinMode(NAV_C, INPUT_PULLUP); // down
  attachPCINT(digitalPinToPCINT(NAV_C), nav_c, FALLING);
  pinMode(NAV_D, INPUT_PULLUP); // right
  attachPCINT(digitalPinToPCINT(NAV_D), nav_d, FALLING);
  pinMode(NAV_CLICK, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(NAV_CLICK), nav_click, FALLING);

  pinMode(EBRAKE, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(EBRAKE), emergency_brake, FALLING);

  pinMode(CONFIRM, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(CONFIRM), confirm, FALLING);
  pinMode(CANCEL, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(CANCEL), cancel, FALLING);

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
      displayConfirm();
      break;     
    default:
      display.clearDisplay();
      display.setTextSize(1);
      display.setCursor(0, 0);
      display.println(F("Something's not working"));
      display.display();
      break;
  }
}

// ISRs


// up
void nav_a(){
  switch(state){
    case 0:
      value += 10;
      break;
    case 1:
      if(parameter == 0){
        parameter = 11;
      }
      else{
        parameter--;
      }
      break;
    case 2:
      if(parameter == 4 || parameter == 5 || parameter == 6 || parameter == 7 || parameter == 8 || parameter == 9 || parameter == 10){
        if(value == 0) value = 1;
        else value = 0;
      }
      else{
        value += 10;
      }
      break;
    case 3:
      // do nothing
      break;
    default:
      break;
  }
}

// left
void nav_b(){
  switch(state){
    case 0:
      value --;
      break;
    case 1:
      if(parameter == 0){
        parameter = 11;
      }
      else{
        parameter--;
      }
      break;
    case 2:
      if(parameter == 4 || parameter == 5 || parameter == 6 || parameter == 7 || parameter == 8 || parameter == 9 || parameter == 10){
        if(value == 0) value = 1;
        else value = 0;
      }
      else{
        value -= 1;
      }
      break;
    case 3:
      break;
    default:
      break;
  }
}

// down
void nav_c(){
  switch(state){
    case 0:
      value -= 10;
      break;
    case 1:
      parameter = (parameter + 1) % 12;
      break;
    case 2:
      if(parameter == 4 || parameter == 5 || parameter == 6 || parameter == 7 || parameter == 8 || parameter == 9 || parameter == 10){
        if(value == 0) value = 1;
        else value = 0;
      }
      else{
        value -= 10;
      }
      break;
    case 3:
    
      break;
    default:
      break;
  }
}

// right
void nav_d(){
  switch(state){
    case 0:
      value++;
      break;
    case 1:
      parameter = (parameter + 1) % 12;
      break; 
    case 2:
      if(parameter == 4 || parameter == 5 || parameter == 6 || parameter == 7 || parameter == 8 || parameter == 9 || parameter == 10){
        if(value == 0) value = 1;
        else value = 0;
      }
      else{
        value += 1;
      }
      break;
    case 3:
      break;
    default:
      break;
  }
}

void nav_click(){
  byte* buf;
  switch(state){
    case 0:
      // send ID
      buf = (byte*) &value;
      Serial.write((byte) 11);
      Serial.write(buf, 4);
      state = 1;
      break;
    case 1:
      break;
    case 2:
      break;
    case 3:
      break;
    default:
      break;
  }
}

void emergency_brake(){
  if(state > 0){
    double temp = 10.0;
    byte* buf = (byte*) &temp;
    Serial.write((byte) 5);
    Serial.write(buf, 4);
  }
}

void confirm(){
  byte* buf;
  switch(state){
    case 0:
      // send ID
      buf = (byte*) &value;
      Serial.write((byte) 11);
      Serial.write(buf, 4);
      state = 1;
      break;
    case 1:
      value = 0;
      state = 2;
      break;
    case 2:
      if(parameter == 4 || parameter == 5 || parameter == 6 || parameter == 7 || parameter == 8 || parameter == 9 || parameter == 10){
        if(value == 0) state = 1;
        else state = 3;
      }
      else state = 3;
      break;
    case 3:
      // send value
      buf = (byte*) &value;
      Serial.write(parameter);
      Serial.write(buf, 4);
      state = 1;
      break;
    default:
      break;
  }
}

void cancel(){
  if(state == 3) state = 2;
}


/*
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
*/
// display functions
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
    display.println(F("Please select a value to change"));
    display.println();
    display.println(paramStrings[(int) parameter]);
    display.display();
}

void displayIdSelection(){
    display.clearDisplay();
    display.setTextSize(1);
    display.setCursor(0, 0);
    display.println(F("Please enter the train ID"));
    display.println();
    display.println(value);
    display.display();
}

void displayValChanges(){
    display.clearDisplay();
    display.setTextSize(1);
    display.setCursor(0, 0);
    display.println(valueStrings[(int) parameter]);
    display.println();
    if(parameter == 4 || parameter == 5 || parameter == 6 || parameter == 7 || parameter == 8 || parameter == 9 || parameter == 10){
      if(value == 1) display.println(F("Yes"));
      else display.println(F("No"));
    }
    else display.println(value);
    display.display();
}

void displayConfirm(){
    display.clearDisplay();
    display.setTextSize(1);
    display.setCursor(0, 0);
    display.println(F("Send value?"));
    display.println(F("Confirm to send"));
    display.println(F("Cancel to reenter value"));
    display.display();
}
