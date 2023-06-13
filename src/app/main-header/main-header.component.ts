import { Component } from '@angular/core';
import { AuthServiceService } from '../services/auth-service.service';
import { DataServiceService } from '../services/data-service.service';

@Component({
  selector: 'app-main-header',
  templateUrl: './main-header.component.html',
  styleUrls: ['./main-header.component.css']
})
export class MainHeaderComponent {
  title: string = "Home Irrigation System"

  username: any

  currentDeviceSerialNumber: any  

  currentUsername: any

  chosenUsername: any
  chosenUserSerialNumber: any

  constructor(public authService: AuthServiceService, public dataService: DataServiceService) {
    this.username = localStorage.getItem("username")

    if(this.username === "admin"){
      this.chosenUsername = localStorage.getItem("chosenUser")
      this.chosenUserSerialNumber = localStorage.getItem("chosenSerialNumber")
    } else {
      this.currentDeviceSerialNumber = localStorage.getItem('chosenUserSerialNumber')      
    }
    
  }




}
