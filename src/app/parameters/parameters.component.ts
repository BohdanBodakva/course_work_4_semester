import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { AuthServiceService } from '../services/auth-service.service';

@Component({
  selector: 'app-parameters',
  templateUrl: './parameters.component.html',
  styleUrls: ['./parameters.component.css']
})
export class ParametersComponent implements OnInit {
  parametersTitle: string = "Change parameters"

  changeParamsForm: FormGroup

  constructor(private http: HttpClient, public authService: AuthServiceService) {
    this.changeParamsForm = new FormGroup({
      frequency: new FormControl(),
      irrigationThreshold: new FormControl()
    })
  }
  ngOnInit() {

  }

  saveParameters() {
    let frequency = this.changeParamsForm.value.frequency
    let irrigationThreshold = this.changeParamsForm.value.irrigationThreshold

    console.log("aaaaa " + frequency + " " + irrigationThreshold);

    this.http.get<string>("http://localhost:8080/api/users/" + localStorage.getItem('username') + "/devices/device1/set-esp32-parameters?frequency=" + frequency
      + "&irrigationThreshold=" + irrigationThreshold, { headers: this.authService.getAuthHeader() })
      .subscribe(result => { return result })
  }
}
