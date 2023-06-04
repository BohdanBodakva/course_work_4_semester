import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Data } from '../entities/Data';
import { DataServiceService } from '../services/data-service.service';
import { AuthServiceService } from '../services/auth-service.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit, OnDestroy {
  displayTitle: string = "Sensors"

  currentAirTemperature: number = 0
  currentAirHumidity: number = 0
  currentSoilMoisture: number = 0

  timeFrequency = 3

  isDeviceConnected: boolean = false
  areSensorsWorkingProperly: boolean = false

  private intervalId: any

  constructor(private http: HttpClient, private dataService: DataServiceService, public authService: AuthServiceService) {

  }

  ngOnInit() {
    this.updateSensorsValues();
    this.intervalId = setInterval(() => {
      this.updateSensorsValues();
    }, 500);
  }

  updateSensorsValues() {
    this.http.get<Data>("http://localhost:8080/api/users/" + localStorage.getItem("username") + "/devices/device1/current-data", { headers: this.authService.getAuthHeader() })
      .subscribe(result => {

        this.isDeviceConnected = true
        this.areSensorsWorkingProperly = true

        if (this.currentAirTemperature == -1 && this.currentAirHumidity == -1 && this.currentSoilMoisture == -1) {
          this.isDeviceConnected = false
        } else if (this.currentAirTemperature < 0 || this.currentAirHumidity < 0 || this.currentSoilMoisture < 0 || this.currentAirTemperature > 100 || this.currentAirHumidity > 100 || this.currentSoilMoisture > 100) {
          this.areSensorsWorkingProperly = false
        }

        this.currentAirTemperature = result.airTemperature
        this.currentAirHumidity = result.airHumidity
        this.currentSoilMoisture = result.soilMoisture

        let dateTime = result.dateTime

        this.isDeviceConnected = true

        if (Math.floor(new Date().getTime() - new Date(dateTime).getTime()) / 1000 > this.timeFrequency + 5) {
          this.isDeviceConnected = false

          this.currentAirTemperature = 0
          this.currentAirHumidity = 0
          this.currentSoilMoisture = 0
        }

      })
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }


}
