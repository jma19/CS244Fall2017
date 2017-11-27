#include <ESP8266WiFi.h>
#include <SPI.h>
#include <ESP8266HTTPClient.h>
#include "SparkFunLIS3DH.h"
#include <Wire.h>
#include "MAX30105.h"
#include <time.h>
#include "FS.h"

char ssid[] = "***";      //  your network SSID (name)
char pass[] = "***";   // your network password

String url = "http://169.234.231.225:8001/experimentData?";

int timezone = 3;
int dst = 0;
unsigned long startTime = 0;
unsigned long currentTime = 0;
int addr = 0;
String fileName = "/data.txt";
boolean stopFlag = false;

MAX30105 particleSensor;
int status = WL_IDLE_STATUS;
int FREQUENCY = 50;
int MAX_WAIT_COUNT = 100;
int batchCount = 0;
// config pin 0 as CS pin
File file;
LIS3DH myIMU(SPI_MODE, 0);
WiFiClient client;
void setup(){
  initSerial();
  initWifi();
  initLIS3DH();
  initParticleSensor();
  initTimer();
  initFS();
  printWifiStatus();
}
/**
 * init serial communication
 */
void initSerial(){
   //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  Serial.println("success to init serial");
}

/**
 * init LIS3DH module
 */
void initLIS3DH(){
   myIMU.begin();
   Serial.println("success to initLIS3DH");
}

/**
 * init particle sensor
 */
void initParticleSensor(){
  if (!particleSensor.begin(Wire, I2C_SPEED_FAST)) //Use default I2C port, 400kHz speed
  {
    Serial.println("MAX30105 was not found. Please check wiring/power. ");
    while (1);
  }
  byte ledBrightness = 0x02; //Options: 0=Off to 255=50mA
  byte sampleAverage = 1; //Options: 1, 2, 4, 8, 16, 32
  byte ledMode = 2; //Options: 1 = Red only, 2 = Red + IR, 3 = Red + IR + Green
  byte sampleRate = 50; //Options: 50, 100, 200, 400, 800, 1000, 1600, 3200
  int pulseWidth = 411; //Options: 69, 118, 215, 411
  int adcRange = 2048; //Options: 2048, 4096, 8192, 16384
  particleSensor.setup(ledBrightness, sampleAverage, ledMode, sampleRate, pulseWidth, adcRange);
  Serial.println("success to init MAX30105");
}

/**
 * init wifi module
 */
void initWifi(){
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
  Serial.println("success to init wifi");
}

/**
 * init timer module
 */
void initTimer(){
    configTime(3 * 3600, 0, "pool.ntp.org", "time.nist.gov");
    Serial.println("\nWaiting for time");
    while (!time(nullptr)) {
      Serial.print(".");
      delay(1000);
    }
    Serial.println("Success to init timer");
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

void initFS(){
  SPIFFS.begin();
  Serial.println("success to init SPIFFS");
  file = SPIFFS.open(fileName.c_str(), "w");
}


void loop()
{
  if(startTime == 0){
      Serial.println("begin collecting data...");
      startTime = millis();
  }
  if(!stopFlag){
      String data = "";
      for(int i = 0; i < FREQUENCY; i++){
          currentTime = millis();
          if((currentTime - startTime) < 60000){
             myIMU.begin();
             data = data +  String(currentTime) + "#";
             data = data +  String(myIMU.readFloatAccelX()) + ":" + String(myIMU.readFloatAccelY()) + ":" + String(myIMU.readFloatAccelZ()) +"#";
             particleSensor.begin(Wire, I2C_SPEED_FAST);
             particleSensor.setup();
             data = data +  String(particleSensor.getIR()) + ":" + String(particleSensor.getRed());
             data += "$";
          }else{
             stopFlag = true;
             break;
          }
      }
      batchCount++;
      Serial.println("save data -->" + data);
      file.println("UAHCASJCJAS");
      if(batchCount == 1 || stopFlag){
          file.flush();
          file.close();
          upload();
          file = SPIFFS.open(fileName.c_str(), "w");
          batchCount = 0;
      }
  }

}
void upload(){
  Serial.println("begin uploading data........");
  File file = SPIFFS.open(fileName.c_str(), "r");
  if (!file) {
    Serial.println("fail to open file");
  }
  else {
    while(file.available()) {
      String line = file.readStringUntil('\n');
      post(line);
      Serial.println(line);
    }
    Serial.println("finish uploading data........");
    file.close();
  }
}

boolean fileRemove(String name){
  SPIFFS.remove(name.c_str());
  return true;
}

boolean post(String data){
    HTTPClient http;    //Declare object of class HTTPClient
    http.begin(url);      //Specify request destination
    http.addHeader("Content-Type", "application/x-www-form-urlencoded");  //Specify content-type header
    int httpCode = http.POST("data="+data);   //Send the request
    Serial.println("Response HttpCode:" + String(httpCode));   //Print HTTP return code
    http.end();
}


