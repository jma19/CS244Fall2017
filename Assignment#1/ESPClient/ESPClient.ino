#include <ESP8266WiFi.h>
#include <SPI.h>
#include <ESP8266HTTPClient.h>

char ssid[] = "*****";      //  your network SSID (name)
char pass[] = "*****";   // your network password

String url = "http://xxx:8001/experimentData?";

int status = WL_IDLE_STATUS;

WiFiClient client;
long randNumber;

void setup(){
  openSerial();
  connectToWifi();
  printWifiStatus();
  randomSeed(analogRead(0));
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
 if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
   HTTPClient http;    //Declare object of class HTTPClient
   http.begin(url);      //Specify request destination
   http.addHeader("Content-Type", "application/x-www-form-urlencoded");  //Specify content-type header
   randNumber = random(300);
   Serial.println("generating data:" + String(randNumber));
   int httpCode = http.POST("data="+String(randNumber));   //Send the request
   String payload = http.getString();                  //Get the response payload
   Serial.println("Response HttpCode:" + String(httpCode));   //Print HTTP return code
   Serial.println("Resonse payload:" + payload);    //Print request response payload
   http.end();
 }else{
    Serial.println("Error in WiFi connection");
 }
 delay(500);
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
