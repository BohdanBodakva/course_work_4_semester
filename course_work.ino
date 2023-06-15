#include <DHT.h>
#include <DHT_U.h>

#include <ESP32Servo.h>

#include <WiFi.h>
#include <ESPAsyncWebServer.h>
#include <HTTPClient.h>

#define TEMPERATURE_PIN 32
#define SOIL_MOISTURE_PIN 33
#define SERVO_PIN 12
#define BUZZER_PIN 25
#define DHT_TYPE DHT11
#define INTERRUPT_PIN 2

const char* ssid = "712a";
const char* password = "zinovii_nytrebych";
const char* serverName = "http://192.168.1.104:8080/api/users/bohdan/devices/device1/post-esp32-values";

AsyncWebServer server(80);
DHT dht(TEMPERATURE_PIN, DHT_TYPE);
Servo servo;

float currentAirTeperature = -1;
float currentAirHumidity = -1;
float currentSoilMoisture = -1;

int currentMillis = 0;
volatile int frequencyMillis = 3000;

volatile float expectedSoilMoisture = 0;
int servoTimeInMillis = 0;

void handleRequest(AsyncWebServerRequest *request) {
  // if (request->method() == HTTP_GET && request->url() == "/set-esp32-parameters") {
    // Handle GET request for sending a request
    // Modify this block according to your needs
    frequencyMillis = (int)(request->getParam("frequency")->value().toFloat() * 1000);
    expectedSoilMoisture = request->getParam("irrigationThreshold")->value().toFloat() * 4096 / 100;

    Serial.println("------------------------------------------");
    Serial.print(frequencyMillis);
    Serial.println("   ---");
    Serial.print(expectedSoilMoisture);
    Serial.println("   ---");
    Serial.println("------------------------------------------");

    request->send(200, "text/plain", "POST request sent");
  // } else {
  //   // Handle other GET requests
  //   // Modify this block according to your needs
  //   request->send(200, "text/plain", "Hello from ESP32!");
  // }
}



void sendPostRequest() {
  HTTPClient http;
  WiFiClient client;
  
  http.begin(client, serverName);
  http.addHeader("Content-Type", "application/json");
  http.addHeader("Access-Control-Allow-Origin", "Origin");
  http.addHeader("Access-Control-Allow-Credentials", "true");
  http.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
  http.addHeader("Access-Control-Max-Age", "3600");
  http.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

  String json = "{\"airTemperature\":" + String(currentAirTeperature) + 
                ",\"airHumidity\":" + String(currentAirHumidity) + 
                ",\"soilMoisture\":" + String(currentSoilMoisture * 100 / 4096) + "}";

  Serial.println(currentAirTeperature);
  Serial.println(currentAirHumidity);
  Serial.println(currentSoilMoisture);

  int httpResponseCode = http.POST(json);

  Serial.print("HTTP Response code: ");
  Serial.println(httpResponseCode);
  http.end();
}

void setup() {
  Serial.begin(115200);

  servo.attach(SERVO_PIN);
  dht.begin();
  
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi...");
  }
  
  Serial.println("Connected to WiFi");
  Serial.print("Local IP address: ");
  Serial.println(WiFi.localIP());

  server.on("/set-parameters", HTTP_GET, [](AsyncWebServerRequest *request) {
    handleRequest(request);
  });

  server.begin();
}

// int currentMillis = 0;

void loop() {
  // Send a POST request every minute
  if (millis() - currentMillis > frequencyMillis) {
    Serial.print("a ");
    Serial.println(frequencyMillis);

    currentAirTeperature = dht.readTemperature();
    currentAirHumidity = dht.readHumidity();
    currentSoilMoisture = analogRead(SOIL_MOISTURE_PIN);    
    
    if (isnan(currentAirTeperature) || isnan(currentAirHumidity) || isnan(currentSoilMoisture)) {
      Serial.println("Failed to read from sensors!");
    } else if (WiFi.status() == WL_CONNECTED){

      sendPostRequest();    
            
    } else {
      Serial.println("WiFi Disconnected");
    }    
    // Serial.println("---");
    // Serial.println(currentSoilMoisture);
    // Serial.println(expectedSoilMoisture);
    // Serial.println(currentSoilMoisture <= expectedSoilMoisture);
    // Serial.println("---");

    Serial.println(currentSoilMoisture <= expectedSoilMoisture);

    if(currentSoilMoisture <= expectedSoilMoisture){
      // int notes[] = {
      //   1000, 2000, 10000, 1070, 25500, 54040
      // };
      // int durations[] = {
      //   100, 200, 300, 150, 300, 220
      // };
      // int currentBoozerMillis = 0;
      // int i = 0;
  
      // while(i < sizeof(durations)){
      //   if(millis() - currentBoozerMillis > durations[i]){
          tone(BUZZER_PIN, 1200);
          delay(1000);
      //     delay(durations[i]);
      //     i++;
      //     currentBoozerMillis = millis();
      //   }    
      // }

        noTone(BUZZER_PIN);
      // makeSound();
        irrigate();
      }

    currentMillis = millis();

  }
  
}

void irrigate(){
  moveServoToDegrees(10, 900);
  moveServoToDegrees(80, 900);
}

void moveServoToDegrees(int degrees, int duration) {
  int currentPos = servo.read();
  int targetPos = map(degrees, 0, 180, 0, 180);
  int increment = targetPos > currentPos ? 1 : -1;
  
  for (int pos = currentPos; pos != targetPos; pos += increment) {
    servo.write(pos);
    delay(duration / abs(targetPos - currentPos));
  }
  
  servo.write(targetPos);
}





void makeSound(){
  int notes[] = {
    1000, 2000, 10000, 1070, 25500, 54040
  };
  int durations[] = {
    100, 200, 300, 150, 300, 220
  };
  int currentBoozerMillis = 0;
  int i = 0;
  
  while(i < sizeof(durations)){
    if(millis() - currentBoozerMillis > durations[i]){
      tone(BUZZER_PIN, notes[i]);
      delay(durations[i]);
      i++;
      currentBoozerMillis = millis();
    }    
  }

  noTone(BUZZER_PIN);
} 





