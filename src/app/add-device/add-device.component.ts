import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { AuthServiceService } from '../services/auth-service.service';
import { Device } from '../entities/Device';
import { Router } from "@angular/router"

@Component({
  selector: 'app-add-device',
  templateUrl: './add-device.component.html',
  styleUrls: ['./add-device.component.css']
})
export class AddDeviceComponent {
  addDeviceTitle: string = "Add new device"

  isProblemMessageDisplaying: boolean = false

  wereParametersSetSuccessfully: boolean = false
  parametersWereSet: boolean = false

  addDeviceForm: FormGroup

  constructor(private http: HttpClient, public authService: AuthServiceService, private router: Router) {
    if(localStorage.getItem("username") == null){
      router.navigate(["/log-in"])
    }
    
    this.addDeviceForm = new FormGroup({
      serialNumber: new FormControl()
    })
  }

  ngOnInit() {

  }

  saveParameters() {

    let serialNumber = this.addDeviceForm.value.serialNumber

    console.log(serialNumber)

    let isSerialNumberUnique: boolean = true

    this.http.get<Device[]>("http://localhost:8080/api/users/" + localStorage.getItem('username') + "/devices", { headers: this.authService.getAuthHeader() })
      .subscribe(result => {

        let existingDevices: Device[] = result

        console.log(existingDevices)

        for (let dev of existingDevices) {
          console.log(dev.serialNumber)
          console.log(serialNumber)
          if (dev.serialNumber == serialNumber) {
            isSerialNumberUnique = false
          }
        }

        if (isSerialNumberUnique) {
          let device = new Device(serialNumber)
    
          console.log(device)
    
          this.http.post<Device>("http://localhost:8080/api/users/" + localStorage.getItem('username') + "/devices", device, { headers: this.authService.getAuthHeader() })
            .subscribe(result => {
    
              console.log(result)
    
              return result
            })
    
          this.router.navigate(['/user'])
    
        } else {
          this.isProblemMessageDisplaying = true
        }
      })
    

  }
}
