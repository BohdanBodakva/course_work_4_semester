import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataServiceService {

  loadingFrequencyInMilliseconds: number = 2000

  currentDeviceSerialNumber: string = ""

  constructor() { }
}
