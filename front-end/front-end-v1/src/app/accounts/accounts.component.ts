import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountService } from '../services/account.service';
import {AppService} from '../services/app.service'
import { HttpParams } from '@angular/common/http';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';



class Account {
  id: number;
  name: string;
  dateCreated: string;
  balance: number;
}

const Accounts: Account[] = [
  // {
  //   id: 1,
  //   name: 'SpaceX',
  //   dateCreated: '1/14/19',
  //   balance: 4036772.48,
  // },
  // {
  //   id: 1,
  //   name: 'Creighton Publishers',
  //   dateCreated: '4/6/18',
  //   balance: 20592.48,
  // },

];

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  accounts: Account[];
  filter = new FormControl('');

  constructor(private router: Router, private AppService: AppService) { }

  ngOnInit(): void {
    this.AppService.getAccounts().subscribe(data => {
      this.accounts;
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

  navEditAccount() {
    this.router.navigate(['/addAccount']);
  }

  navView() {
    this.router.navigate(['/viewAccount']);
  }


}
