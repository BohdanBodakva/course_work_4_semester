import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from '../entities/User';
import { AuthServiceService } from '../services/auth-service.service';
import { Device } from '../entities/Device';
import { Router } from "@angular/router"
import { AdminServiceService } from '../services/admin-service.service';
import { Location } from '@angular/common';

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

  constructor(public adminService: AdminServiceService, private router: Router, public http: HttpClient, public authService: AuthServiceService, private location: Location) {
    if(localStorage.getItem("username") == null){
      router.navigate(["/log-in"])
    }
  }

  ngOnInit() {
    this.getAllUsers()
  }

  getAllUsers() {
    this.http.get<User[]>("http://localhost:8080/api/admin/users", { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        console.log(result)
        this.users = result
      })
  }

  banUser(username: string) {
    console.log(username)
    this.http.post<string>("http://localhost:8080/api/admin/users/" + username + "/ban", null, { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        console.log(result)
      })

      window.location.reload();
  }

  makeUserActive(username: string) {
    console.log(username)
    this.http.post<string>("http://localhost:8080/api/admin/users/" + username + "/makeActive", null, { headers: this.authService.getAuthHeader() })
      .subscribe(result => {
        console.log(result)
      })

      window.location.reload();
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

  goToDeviceData(username: string, serialNumber: string) {
    localStorage.setItem("chosenUser", username)
    localStorage.setItem("chosenSerialNumber", serialNumber)
    this.router.navigate(['/history'])
  }


}
