import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-average-values',
  templateUrl: './average-values.component.html',
  styleUrls: ['./average-values.component.css']
})
export class AverageValuesComponent implements OnInit {

  averageAirTemperature: number = 0
  averageAirHumidity: number = 0
  averageSoilMoisture: number = 0

  constructor(private http: HttpClient){

  }

  ngOnInit() {
    this.http.get<number>("http://localhost:8080/api/data/average-value?param=airTemperature").subscribe(result => {
      this.averageAirTemperature = result
      console.log(result)
    })
    this.http.get<number>("http://localhost:8080/api/data/average-value?param=airHumidity").subscribe(result => {
      this.averageAirHumidity = result
      console.log(result)
    })
    this.http.get<number>("http://localhost:8080/api/data/average-value?param=soilMoisture").subscribe(result => {
      this.averageSoilMoisture = result
      console.log(result)
    })
  }

}
