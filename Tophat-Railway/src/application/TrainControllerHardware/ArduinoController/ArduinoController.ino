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
#define KP_2 8
#define KP_3 9
#define KP_4 10
#define EBRAKE 11

// globals (I know they're bad, leave me alone)
int state = 0; // 0 for picking value to change, 1 for changing value, 2 for confirming
int current_value = 0; // 0 for speed, 1 for ki, 2 for kp, 3 for temperature, 4 for service brake, 5 for lights, 6 for left door, 7 for right door,
// 8 for emergency brake

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println("DONT STOP THIS TRAIN");
  pinMode(NAV_A, INPUT_PULLUP);
  //attachPCINT(digitalPinToPCINT(NAV_A), ..., FALLING);
  pinMode(NAV_B, INPUT_PULLUP);
  //attachPCINT(digitalPinToPCINT(NAV_B), ..., FALLING);
  pinMode(NAV_C, INPUT_PULLUP);
  //attachPCINT(digitalPinToPCINT(NAV_C), ..., FALLING);
  pinMode(NAV_D, INPUT_PULLUP);
  //attachPCINT(digitalPinToPCINT(NAV_D), ..., FALLING);

  pinMode(EBRAKE, INPUT_PULLUP);
  attachPCINT(digitalPinToPCINT(EBRAKE), STOPTRAIN, FALLING);
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);

  // clear buffer
  display.clearDisplay();

  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.setCursor(0, 0);
  display.println(F("Would you like to set the service brake?"));
  display.display();

  delay(2000);

  display.clearDisplay();
  display.println(F("YES"));
  display.display();
}



void loop() {
  // put your main code here, to run repeatedly:

  if(state){
    display.clearDisplay();
    display.setCursor(0, 0);
    display.println(F("HOLY SHIT STOP THIS FUCKING TRAIN"));
    display.display();
    delay(100);
  }
  else{
    display.clearDisplay();
    display.display();
  }
  //  "select property to edit"
  //  scroll through values with nav switch and select one by clicking
  //  depending on value, it will be input in some way
  //  confirm using yes or no buttons
  //  value will be sent over serial, loop will reset 
}

// ISRs
void STOPTRAIN(){
  Serial.print("HOLY SHIT STOP THIS FUCKING TRAIN");
  Serial.println(state);
  if(state) state = 0;
  else state = 1;
}
void nav_up(){
  
}

void nav_left(){
  
}

void nav_down(){
  // C
}

void nav_right(){
  // D
}
