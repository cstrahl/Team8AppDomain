import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  viewAccountForm = new FormGroup({});
  constructor(private router: Router,) { }

  ngOnInit(): void {
  }

  navAccounts(){
    this.router.navigate(['/accounts']);
  }

  logout(){
    this.router.navigate(['/login']);
  }

}
