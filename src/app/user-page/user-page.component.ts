import { Component, OnInit } from '@angular/core';
import { Device } from '../entities/Device';
import { AuthServiceService } from '../services/auth-service.service';
import { HttpClient } from '@angular/common/http';
import { DataServiceService } from '../services/data-service.service';
import { Router } from '@angular/router';
import { User } from '../entities/User';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {
  currentUser: User = new User()
  userDevices: Device[] = []

  constructor(public authService: AuthServiceService, private http: HttpClient, public dataService: DataServiceService, private router: Router) {
    if(localStorage.getItem("username") == null){
      router.navigate(["/log-in"])
    }
  }

  ngOnInit() {
    this.http.get<User>("http://localhost:8080/api/users/" + localStorage.getItem("username"), { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        this.currentUser = result

        this.http.get<Device[]>("http://localhost:8080/api/users/" + localStorage.getItem("username") + "/devices", { headers: this.authService.getAuthHeader() })
          .subscribe(result => {
            console.log(result)
            this.userDevices = result
          })
      })
  }

  goToParameters(serialNumber: string) {
    localStorage.setItem("chosenUserSerialNumber", serialNumber)

    this.router.navigate(["/parameters"])
  }

  selectDevice(serialNumber: string) {
    localStorage.setItem("chosenUserSerialNumber", serialNumber)

    localStorage.setItem("timeFrequencyInSeconds_" + serialNumber, "3")
    localStorage.setItem("irrigationThresholdInPercent_" + serialNumber, "50")

    this.router.navigate(["/history"])
  }

  deleteDevice(serialNumber: string) {
    this.http.delete("http://localhost:8080/api/users/" + localStorage.getItem("username") + "/devices/" + serialNumber, { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        window.location.reload()
      })
  }

  deleteAccount(){
    this.http.delete("http://localhost:8080/api/users/" + localStorage.getItem("username"), { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        localStorage.removeItem("username")
        localStorage.removeItem("chosenSerialNumber")
        localStorage.removeItem("jwt")

        this.router.navigate(["/log-in"])
      })
  }

}
