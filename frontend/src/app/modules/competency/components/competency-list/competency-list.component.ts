import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-competency-list',
  templateUrl: './competency-list.component.html',
  styleUrls: ['./competency-list.component.scss']
})
export class CompetencyListComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    console.log('Teste');
  }

}
