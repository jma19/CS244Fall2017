#include <ESP8266WiFi.h>
#include <SPI.h>
#include <ESP8266HTTPClient.h>
#include "SparkFunLIS3DH.h"
#include <Wire.h>

char ssid[] = "XXXX";      //  your network SSID (name)
char pass[] = "XXXX";   // your network password

String url = "http://169.234.231.225:8001/experimentData?";

int status = WL_IDLE_STATUS;
int FREQUENCY = 50;
int MAX_WAIT_COUNT = 100;
// config pin 15 as CS pin
LIS3DH myIMU(SPI_MODE, 0);

WiFiClient client;
void setup(){
  openSerial();
  connectToWifi();
  configLIS3DH();
  printWifiStatus();
}


void loop()
{

  String data = "";
  for(int i = 0; i < FREQUENCY; i++){
    data = data +  String(myIMU.readFloatAccelX()) + ":" + String(myIMU.readFloatAccelY()) + ":" + String(myIMU.readFloatAccelZ()) + "#";
    delay(10);
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
//
//  //Get all parameters
//  Serial.print("\nAccelerometer:\n");
//  Serial.print(" X = ");
//  Serial.println(myIMU.readFloatAccelX(), 4);
//  Serial.print(" Y = ");
//  Serial.println(myIMU.readFloatAccelY(), 4);
//  Serial.print(" Z = ");
//  Serial.println(myIMU.readFloatAccelZ(), 4);
//  delay(100);
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

