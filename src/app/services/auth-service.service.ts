import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthRequest } from '../entities/AuthRequest';
import { RegisterRequest } from '../entities/RegisterRequest';
import { Router } from "@angular/router"

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  getAuthHeader() {
    return new HttpHeaders()
      .set('Authorization', 'Bearer ' + localStorage.getItem('jwt'))
      .set('Access-Control-Allow-Origin', '*')
      .set('Access-Control-Allow-Methods', '*')
      .set('Access-Control-Allow-Headers', '*')
  }

  isUserExisting = false
  isUserAbsent = false

  constructor(public http: HttpClient, private router: Router) {
  }

  public login(username: string, password: string) {
    var authRequest = new AuthRequest(username, password)
    this.http.post<any>("http://localhost:8080/api/auth/authenticate", authRequest).subscribe(result => {
      console.log(result)
      let jwt = result.jwtToken
      localStorage.setItem('jwt', jwt);
      localStorage.setItem('username', username)

      console.log(localStorage.getItem('username'))

      // localStorage.setItem("timeFrequencyInSeconds", "3")
      // localStorage.setItem("irrigationThresholdInPercent", "50")

      if (localStorage.getItem('username') === 'admin') {
        this.router.navigate(['admin'])
      } else {
        this.router.navigate(['user'])
      }

    },
      error => {
        if (error.status == 400) {
          this.isUserAbsent = true
        }
      })
  }

  public signup(name: string, surname: string, username: string, password: string) {
    var registerRequest = new RegisterRequest(name, surname, username, password)
    this.http.post<any>("http://localhost:8080/api/auth/register", registerRequest).subscribe(result => {
      let jwt = result.jwtToken
      localStorage.setItem('jwt', jwt)
      localStorage.setItem('username', username)

      console.log(result)

      // localStorage.setItem("timeFrequencyInSeconds", "3")
      // localStorage.setItem("irrigationThresholdInPercent", "50")

      this.router.navigate(['user'])
    },
      error => {
        console.log(error)
        if (error.status == 400) {
          this.isUserExisting = true
        }
      })
  }

  public logout() {
    localStorage.removeItem("chosenUserSerialNumber")

    localStorage.removeItem("jwt")
    localStorage.removeItem("username")

    this.router.navigate(['log-in'])
  }

}