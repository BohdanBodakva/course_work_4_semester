import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { AuthServiceService } from '../services/auth-service.service';
import { SetParameters } from '../entities/SetParameters';
import { Router } from '@angular/router';

@Component({
  selector: 'app-parameters',
  templateUrl: './parameters.component.html',
  styleUrls: ['./parameters.component.css']
})
export class ParametersComponent implements OnInit {
  parametersTitle: string = "Change parameters"

  wereParametersSetSuccessfully: boolean = false
  parametersWereSet: boolean = false

  changeParamsForm: FormGroup

  constructor(private router: Router, private http: HttpClient, public authService: AuthServiceService) {
    if(localStorage.getItem("username") == null){
      router.navigate(["/log-in"])
    }
    
    this.changeParamsForm = new FormGroup({
      frequency: new FormControl(Number(localStorage.getItem("timeFrequencyInSeconds_" + localStorage.getItem("chosenUserSerialNumber")))),
      irrigationThreshold: new FormControl(Number(localStorage.getItem("irrigationThresholdInPercent_" + localStorage.getItem("chosenUserSerialNumber"))))
    })
  }

  ngOnInit() {

  }

  saveParameters() {
    this.parametersWereSet = true

    let frequency = this.changeParamsForm.value.frequency
    let irrigationThreshold = this.changeParamsForm.value.irrigationThreshold

    this.http.get<SetParameters>("http://localhost:8080/api/users/" + localStorage.getItem('username') + "/devices/device1/set-esp32-parameters?frequency=" + frequency
      + "&irrigationThreshold=" + irrigationThreshold, { headers: this.authService.getAuthHeader() })
      .subscribe(result => {

        localStorage.setItem("timeFrequencyInSeconds_" + localStorage.getItem("chosenUserSerialNumber"), frequency)
        localStorage.setItem("irrigationThresholdInPercent_" + localStorage.getItem("chosenUserSerialNumber"), irrigationThreshold)

        console.log(result)

        if (result.status === "Parameters were set successfully") {
          this.wereParametersSetSuccessfully = true
        } else {
          this.wereParametersSetSuccessfully = false
        }

        return result
      })

    localStorage.setItem("timeFrequencyInSeconds_" + localStorage.getItem("chosenUserSerialNumber"), frequency)
    localStorage.setItem("irrigationThresholdInPercent_" + localStorage.getItem("chosenUserSerialNumber"), irrigationThreshold)
  
  
  }
}
