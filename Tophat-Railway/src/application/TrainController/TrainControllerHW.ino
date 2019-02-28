int speed = 0;
int power = 0;
int temp = 0;
bool buttonPressed = false;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(PUSH2, INPUT_PULLUP);
  pinMode(RED_LED, OUTPUT);
  pinMode(P1_0, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly: 
  
  if(digitalRead(PUSH2) == LOW){
    if(!buttonPressed){
      Serial.write(69);
      digitalWrite(RED_LED, HIGH);
      buttonPressed = true;
    }
  }
  else{
    digitalWrite(RED_LED, LOW);
    buttonPressed = false;
  }
  if(Serial.available()){
    digitalWrite(P1_0, HIGH);
    speed = Serial.readStringUntil('\n').toInt();
    power = Serial.readStringUntil('\n').toInt();
    temp = Serial.readStringUntil('\n').toInt();
    digitalWrite(P1_0, LOW);
  }
}

void serialEvent(){
  digitalWrite(P1_0, HIGH);
  // read 
  if(Serial.available()){
    digitalWrite(P1_0, HIGH);
  }
  else{
    digitalWrite(P1_0, LOW);
  }
  while(Serial.available()){
    digitalWrite(P1_0, HIGH);
    speed = Serial.readStringUntil('\n').toInt();
    power = Serial.readStringUntil('\n').toInt();
    temp = Serial.readStringUntil('\n').toInt();
    digitalWrite(P1_0, LOW);
  }
  
}
