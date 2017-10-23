#include <ESP8266WiFi.h>
#include <SPI.h>
#include <ESP8266HTTPClient.h>
#include <Wire.h>
#include "MAX30105.h"


char ssid[] = "****";      //  your network SSID (name)
char pass[] = "****";   // your network password

String url = "http://169.234.231.225:8001/experimentData?";

MAX30105 particleSensor;
int FREQUENCY = 50; //read data in 50HZ

int status = WL_IDLE_STATUS;

int MAX_WAIT_COUNT = 100;

WiFiClient client;
long randNumber;

void setup(){
  openSerial();
  initParticleSensor();
  connectToWifi();
  printWifiStatus();
  randomSeed(analogRead(0));
}

void initParticleSensor(){
  if (!particleSensor.begin(Wire, I2C_SPEED_FAST)) //Use default I2C port, 400kHz speed
  {
    Serial.println("MAX30105 was not found. Please check wiring/power. ");
    while (1);
  }
  //powerLevel = 0x02, 0.4mA - Presence detection of ~4 inch
  //powerLevel = 0x1F, 6.4mA - Presence detection of ~8 inch
  //powerLevel = 0x7F, 25.4mA - Presence detection of ~8 inch
  //powerLevel = 0xFF, 50.0mA - Presence detection of ~12 inch
  byte ledBrightness = 0xFF; //Options: 0=Off to 255=50mA
  byte sampleAverage = 4; //Options: 1, 2, 4, 8, 16, 32
  byte ledMode = 2; //Options: 1 = Red only, 2 = Red + IR, 3 = Red + IR + Green
  byte sampleRate = 200; //Options: 50, 100, 200, 400, 800, 1000, 1600, 3200
  int pulseWidth = 411; //Options: 69, 118, 215, 411
  int adcRange = 2048; //Options: 2048, 4096, 8192, 16384
  particleSensor.setup(ledBrightness, sampleAverage, ledMode, sampleRate, pulseWidth, adcRange);
  Serial.println("MAX30105 sets up successfully!!!");
}

void openSerial(){
   //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
}
void connectToWifi(){
  // check for the presence of the shield:
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue:
    while (true);
  }

  // attempt to connect to Wifi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid, pass);
    // wait 10 seconds for connection:
    delay(10000);
  }
  Serial.println("Connected to wifi");
}

void loop(){
 String data = "";
 for(int i = 0; i < FREQUENCY; i++){
    data = data +  String(particleSensor.getRed()) + ":" + String(particleSensor.getIR()) + "#";
    delay(1000/FREQUENCY);
 }

 if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
   HTTPClient http;    //Declare object of class HTTPClient
   http.begin(url);      //Specify request destination
   http.addHeader("Content-Type", "application/x-www-form-urlencoded");  //Specify content-type header
   Serial.println("generating data:" + data);
   int httpCode = http.POST("data="+data);   //Send the request
   Serial.println("Response HttpCode:" + String(httpCode));   //Print HTTP return code
   http.end();
 }else{
    Serial.println("Error in WiFi connection");
 }
}



void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}





