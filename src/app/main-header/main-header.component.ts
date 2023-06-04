import { Component } from '@angular/core';
import { AuthServiceService } from '../services/auth-service.service';

@Component({
  selector: 'app-main-header',
  templateUrl: './main-header.component.html',
  styleUrls: ['./main-header.component.css']
})
export class MainHeaderComponent {
  title: string = "Home Irrigation System"

  username: any

  constructor(public authService: AuthServiceService) {
    this.username = localStorage.getItem('username')
  }




}
