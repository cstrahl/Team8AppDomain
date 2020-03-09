import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  accounts: Account[];

  constructor(private router: Router, private AccountService: AccountService) { }

  ngOnInit(): void {
    this.AccountService.findAll().subscribe(data => {
      //this.accounts = data;
    });
  }
  
  logout() {
    this.router.navigate(['/login']);
  }

  navHome() {
    this.router.navigate(['/home']);
  }

  navAccounts() {
    this.router.navigate(['/accounts']);
  }

  navAddAccount() {
    this.router.navigate(['/addAccount']);
  }

  navView() {
    this.router.navigate(['/viewAccount']);
  }


}
