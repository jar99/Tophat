#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 32

// declare OLED display connected to I2C
#define SCREEN_RESET 4
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, SCREEN_RESET);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  // clear buffer
  display.clearDisplay();
}

void loop() {
  // put your main code here, to run repeatedly:

  // flow of the GUI OLED
  //  "select property to edit"
  //  scroll through values with nav switch and select one by clicking
  //  depending on value, it will be input in some way
  //  confirm using yes or no buttons
  //  value will be sent over serial, loop will reset 
}
