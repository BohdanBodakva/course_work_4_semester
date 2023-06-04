import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from '../entities/User';
import { AuthServiceService } from '../services/auth-service.service';
import { Device } from '../entities/Device';
import { Data } from '../entities/Data';
import {Router} from "@angular/router"
import { AdminServiceService } from '../services/admin-service.service';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  title = "Welcome, Admin!"
  usersTitle = "Users"

  displayUserDevices: boolean = false
  
  users: User[] = []

  devices: Device[] = []

  constructor(public adminService: AdminServiceService, private router: Router, public http: HttpClient, public authService: AuthServiceService) {

  }

  ngOnInit() {
    this.http.get<User[]>("http://localhost:8080/api/admin/users", { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        console.log(result)
        this.users = result
      })
  }

  banUser(username: string) {
    console.log(username)
    let s = "http://localhost:8080/api/admin/users/" + username + "/ban"
    this.http.post<any>(s, { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        console.log(result)
      })
  }

  makeUserActive(username: string) {
    console.log(username)
    this.http.post<any>("http://localhost:8080/api/admin/users/" + username + "/makeActive", { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        console.log(result)
      })
  }

  getUserDevices(username: string) {
    this.http.get<Device[]>("http://localhost:8080/api/admin/users/" + username + "/devices", { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        this.devices = result
        this.adminService.displayedUserUsername = username
      })

    this.displayUserDevices = true  
  }

  backToUserList() {
    this.devices = []
    this.adminService.displayedUserUsername = ""
    this.displayUserDevices = false
  }

  goToDeviceData(username: string, serialNumber: string){
    this.adminService.displayedUserUsername = username
    this.adminService.displayedSerialNumber = serialNumber
    this.router.navigate(['/history'])
  }


}
