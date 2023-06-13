import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Data, Router } from '@angular/router';
import { AdminServiceService } from '../services/admin-service.service';
import { AuthServiceService } from '../services/auth-service.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  allData: Data[] = []
  isAdminLoggedIn: boolean = false

  sortOrderASC: boolean = false
  sortedData: Data[] = []
  sortParam: string = ""

  constructor(private router: Router, private http: HttpClient, public authService: AuthServiceService, public adminService: AdminServiceService) {
    if(localStorage.getItem("username") == null){
      router.navigate(["/log-in"])
    }
    
    if (localStorage.getItem('username') == 'admin') {
      this.isAdminLoggedIn = true
    }
  }

  ngOnInit() {
    console.log(localStorage)
    console.log(localStorage.getItem('jwt'))
    console.log(localStorage.getItem('username'))
    if (localStorage.getItem("username") == "admin") {
      this.http.get<Data[]>("http://localhost:8080/api/admin/users/" + localStorage.getItem("chosenUser") + "/devices/" + localStorage.getItem("chosenSerialNumber") + "/data", { headers: this.authService.getAuthHeader() }).subscribe(result => {
        this.allData = result
        this.sortedData = result
        console.log(result)
      })
    } else if (localStorage.getItem("username") != "") {
      console.log("http://localhost:8080/api/users/" + localStorage.getItem('username') + "/devices/device1/data?sortedByDate=desc")
      this.http.get<Data[]>("http://localhost:8080/api/users/" + localStorage.getItem('username') + "/devices/" + localStorage.getItem("chosenUserSerialNumber") + "/data?sortedByDate=desc", { headers: this.authService.getAuthHeader() }).subscribe(result => {
        this.allData = result
        this.sortedData = result
        console.log(result)
      })
    }
  }

  makeDateFromString(dateTime: string) {
    return new Date(dateTime)
  }

  changeSortParam(value: string) {
    this.sortParam = value

    switch (value) {
      case '':
        this.sortedData = this.allData
        break;
      case 'dateTime':
        this.sortByDate()
        break;
      case 'airTemp':
        this.sortByAirTemp()
        break;
      case 'airHum':
        this.sortByAirHum()
        break;
      case 'soilMoist':
        this.sortBySoilMoist()
        break;
    }
  }

  changeSortOrder() {
    this.sortOrderASC = !this.sortOrderASC

    switch (this.sortParam) {
      case 'dateTime':
        if (this.sortOrderASC) {
          this.sortedData = this.sortedData.sort((d1, d2) => {
            let date1 = new Date(d1.dateTime)
            let date2 = new Date(d2.dateTime)

            if (date1 > date2) {
              return 1
            }

            return -1
          })
        } else {
          this.sortedData = this.sortedData.sort((d1, d2) => {
            let date1 = new Date(d1.dateTime)
            let date2 = new Date(d2.dateTime)

            if (date1 < date2) {
              return 1
            }

            return -1
          })
        }
        break;
      case 'airTemp':
        if (this.sortOrderASC) {
          this.sortedData = this.sortedData.sort((d1, d2) => d1.airTemperature - d2.airTemperature)
        } else {
          this.sortedData = this.sortedData.sort((d1, d2) => d2.airTemperature - d1.airTemperature)
        }
        break;
      case 'airHum':
        if (this.sortOrderASC) {
          this.sortedData = this.sortedData.sort((d1, d2) => d1.airHumidity - d2.airHumidity)
        } else {
          this.sortedData = this.sortedData.sort((d1, d2) => d2.airHumidity - d1.airHumidity)
        }
        break;
      case 'soilMoist':
        if (this.sortOrderASC) {
          this.sortedData = this.sortedData.sort((d1, d2) => d1.soilMoisture - d2.soilMoisture)
        } else {
          this.sortedData = this.sortedData.sort((d1, d2) => d2.soilMoisture - d1.soilMoisture)
        }
        break;
    }
  }

  sortByDate() {
    this.sortParam = "dateTime"

    this.sortedData = this.sortedData.sort((d1, d2) => {
      let date1 = new Date(d1.dateTime)
      let date2 = new Date(d2.dateTime)

      if (date1 > date2) {
        return 1
      }

      return -1
    })
  }

  sortByAirTemp() {
    this.sortedData = this.sortedData.sort((d1, d2) => d1.airTemperature - d2.airTemperature)
    this.sortParam = "airTemp"
  }

  sortByAirHum() {
    this.sortedData = this.sortedData.sort((d1, d2) => d1.airHumidity - d2.airHumidity)
    this.sortParam = "airHum"
  }

  sortBySoilMoist() {
    this.sortedData = this.sortedData.sort((d1, d2) => d1.soilMoisture - d2.soilMoisture)
    this.sortParam = "soilMoist"
  }

  deleteAllData() {
    this.http.delete("http://localhost:8080/api/users/" + localStorage.getItem('username') + "/devices/device1/data", { headers: this.authService.getAuthHeader() }).subscribe(result => {

    })
  }

}
