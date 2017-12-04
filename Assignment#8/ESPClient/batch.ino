#include <ESP8266WiFi.h>
#include <SPI.h>
#include <ESP8266HTTPClient.h>
#include "SparkFunLIS3DH.h"
#include <Wire.h>
#include "MAX30105.h"


//char ssid[] = "UCInet Mobile Access";      //  your network SSID (name)
//char pass[] = "";   // your network password

char ssid[] = "***";      //  your network SSID (name)
char pass[] = "***";   // your network password

String url = "http://169.234.231.225:8001/experimentData?"; 

MAX30105 particleSensor;

int status = WL_IDLE_STATUS;
int FREQUENCY = 150;
int MAX_WAIT_COUNT = 100;
// config pin 15 as CS pin
LIS3DH myIMU(SPI_MODE, 0); 

WiFiClient client;
void setup(){
  openSerial();
  connectToWifi();
  configLIS3DH();
  initParticleSensor();
  printWifiStatus();
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
  byte sampleAverage = 1; //Options: 1, 2, 4, 8, 16, 32
  byte ledMode = 2; //Options: 1 = Red only, 2 = Red + IR, 3 = Red + IR + Green
  byte sampleRate = 200; //Options: 50, 100, 200, 400, 800, 1000, 1600, 3200
  int pulseWidth = 411; //Options: 69, 118, 215, 411
  int adcRange = 2048; //Options: 2048, 4096, 8192, 16384
  particleSensor.setup(ledBrightness, sampleAverage, ledMode, sampleRate, pulseWidth, adcRange); 
  Serial.println("MAX30105 sets up successfully!!!");
}


void loop()
{
  
  String data = "";
  myIMU.begin();
  for(int i = 0; i < FREQUENCY; i++){
    data = data +  String(myIMU.readFloatAccelX()) + ":" + String(myIMU.readFloatAccelY()) + ":" + String(myIMU.readFloatAccelZ()) +"#";
  }
  data = data + "$";
  particleSensor.begin(Wire, I2C_SPEED_FAST);
  particleSensor.setup();
  for(int i = 0; i < FREQUENCY; i++){
    data = data +  String(particleSensor.getIR()) + ":" + String(particleSensor.getRed()) +"#";
  }
  HTTPClient http;    //Declare object of class HTTPClient
  http.begin(url);      //Specify request destination
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");  //Specify content-type header
  int httpCode = http.POST("data="+data);   //Send the request
  Serial.println("Response HttpCode:" + String(httpCode));   //Print HTTP return code
  http.end(); 
}

void openSerial(){
   //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
}

void configLIS3DH(){
   myIMU.begin();
   Serial.println("config LIS3DH successfully");
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


