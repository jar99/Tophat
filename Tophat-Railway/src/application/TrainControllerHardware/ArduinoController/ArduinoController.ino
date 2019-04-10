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
#define NAV_BUTTON 6
#define KP_1 7
#define KP_2 8
#define KP_3 9
#define KP_4 10
#define EBRAKE 11

// globals (I know they're bad, leave me alone)
int state = 0; // 0 for picking value to change, 1 for changing value, 2 for confirming
int current_value = 0; // 0 for speed

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

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

  //  "select property to edit"
  //  scroll through values with nav switch and select one by clicking
  //  depending on value, it will be input in some way
  //  confirm using yes or no buttons
  //  value will be sent over serial, loop will reset 
}

// ISRs
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
