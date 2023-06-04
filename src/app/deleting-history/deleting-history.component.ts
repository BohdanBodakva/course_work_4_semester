import { Component, OnInit } from '@angular/core';
import { DeletingData } from '../entities/DeletingData';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-deleting-history',
  templateUrl: './deleting-history.component.html',
  styleUrls: ['./deleting-history.component.css']
})
export class DeletingHistoryComponent implements OnInit {

  allDeletingData: DeletingData[] = []

  constructor(private http: HttpClient){

  }

  ngOnInit() {
    this.http.get<DeletingData[]>("http://localhost:8080/api/clear-data/").subscribe(result => {
      this.allDeletingData = result
      console.log(result)
    })
  }

}
