import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Data } from '@angular/router';
import { AdminServiceService } from '../services/admin-service.service';
import { AuthServiceService } from '../services/auth-service.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  allData: Data[] = []
  loggedUserUsername: string = ""
  isAdminLoggedIn: boolean = false

  constructor(private http: HttpClient, public authService: AuthServiceService, public adminService: AdminServiceService) {
    if (JSON.stringify(localStorage.getItem('username')) != 'null') {
      this.isAdminLoggedIn = true
    }
  }

  ngOnInit() {
    if (this.isAdminLoggedIn) {
      this.http.get<Data[]>("http://localhost:8080/api/admin/users/" + this.adminService.displayedUserUsername + "/devices/" + this.adminService.displayedSerialNumber + "/data", { headers: this.authService.getAuthHeader() }).subscribe(result => {
        this.allData = result
        console.log(result)
      })
    }
    // this.http.get<Data[]>("http://localhost:8080/api/data/sorted-by-date?param=DESC").subscribe(result => {
    //   this.allData = result
    //   console.log(result)
    // })
  }

}
