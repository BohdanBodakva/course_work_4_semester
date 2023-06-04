import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminServiceService {

  displayedUserUsername: string = ""
  displayedSerialNumber: string = ""

  constructor() { }
}
