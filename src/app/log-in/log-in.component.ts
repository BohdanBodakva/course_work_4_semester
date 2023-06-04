import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthServiceService } from '../services/auth-service.service';
import { TreeError } from '@angular/compiler';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {
  title = "Log In"

  usernamePasswordMessage = false

  logInForm: FormGroup

  constructor(private http: HttpClient, public authService: AuthServiceService) {
    this.logInForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl()
    })
  }

  ngOnInit() {

  }

  logIn() {
    let validated = true

    let username: string = this.logInForm.value.username
    let password: string = this.logInForm.value.password

    this.usernamePasswordMessage = false

    if (username.length < 5 || username.length > 60 || password.length < 5 || password.length > 60) {
      this.usernamePasswordMessage = true
      validated = false
    }

    if (validated) {
      this.authService.login(username, password)
    }

  }


}
