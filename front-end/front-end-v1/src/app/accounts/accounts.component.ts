import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  constructor(private router: Router,) { }

  ngOnInit(): void {
  }
  logout(){
    this.router.navigate(['/login']);
  }

  navHome(){
    this.router.navigate(['/home']);
  }

  navAccounts(){
    this.router.navigate(['/accounts']);
  }
}
