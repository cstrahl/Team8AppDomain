import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

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

  navUsers(){
    this.router.navigate(['/users']);
  }

}
