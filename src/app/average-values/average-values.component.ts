import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from '../services/auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-average-values',
  templateUrl: './average-values.component.html',
  styleUrls: ['./average-values.component.css']
})
export class AverageValuesComponent implements OnInit {

  title: string = "Average values"

  averageAirTemperature: number = 0
  averageAirHumidity: number = 0
  averageSoilMoisture: number = 0

  constructor(private router: Router, private http: HttpClient, private authService: AuthServiceService) {
    if(localStorage.getItem("username") == null){
      router.navigate(["/log-in"])
    }
    
  }

  ngOnInit() {
    console.log(localStorage)
    this.http.get("http://localhost:8080/api/users/" + localStorage.getItem("username") + "/devices/" + localStorage.getItem("chosenUserSerialNumber") + "/avg-data?avg=airTemperature", { headers: this.authService.getAuthHeader() }).subscribe(result => {
      this.averageAirTemperature = Math.round(Number(result)) < 0 ? 0 : Math.round(Number(result))
      console.log(result)
    })
    this.http.get("http://localhost:8080/api/users/" + localStorage.getItem("username") + "/devices/" + localStorage.getItem("chosenUserSerialNumber") + "/avg-data?avg=airHumidity", { headers: this.authService.getAuthHeader() }).subscribe(result => {
      this.averageAirHumidity = Math.round(Number(result))
      console.log(result)
    })
    this.http.get("http://localhost:8080/api/users/" + localStorage.getItem("username") + "/devices/" + localStorage.getItem("chosenUserSerialNumber") + "/avg-data?avg=soilMoisture", { headers: this.authService.getAuthHeader() }).subscribe(result => {
      this.averageSoilMoisture = Math.round(Number(result))
      console.log(result)
    })
  }

}
