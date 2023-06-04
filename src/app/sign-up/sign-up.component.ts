import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthServiceService } from '../services/auth-service.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  title = "Sign Up"

  isPasswordConfirmed = true
  nameSurnameMessage = false
  usernamePasswordMessage = false

  signUpForm: FormGroup

  constructor(private http: HttpClient, public authService: AuthServiceService) {
    this.signUpForm = new FormGroup({
      name: new FormControl(),
      surname: new FormControl(),
      username: new FormControl(),
      password: new FormControl(),
      confirmPassword: new FormControl(),
    })
  }

  ngOnInit() {

  }

  signUp() {
    let validated = true

    let name: string = this.signUpForm.value.name
    let surname: string = this.signUpForm.value.surname
    let username: string = this.signUpForm.value.username
    let password: string = this.signUpForm.value.password
    let confirmPassword: string = this.signUpForm.value.confirmPassword

    this.nameSurnameMessage = false
    this.usernamePasswordMessage = false
    this.isPasswordConfirmed = true

    if (name.length < 2 || name.length > 50 || surname.length < 1 || surname.length > 50) {
      this.nameSurnameMessage = true
      validated = false
    }

    if (username.length < 5 || username.length > 60 || password.length < 5 || password.length > 60) {
      this.usernamePasswordMessage = true
      validated = false
    }

    if (password != confirmPassword) {
      this.isPasswordConfirmed = false
      validated = false
    }

    if (validated) {
      this.authService.signup(name, surname, username, password)
    }

  }
}
