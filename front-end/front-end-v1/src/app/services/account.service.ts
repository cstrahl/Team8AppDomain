import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Account } from '../models/account';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';


@Injectable()
export class AccountService {

  private accountsUrl: string;

  constructor(
    private _router: Router, private http: HttpClient) {
      this.accountsUrl = 'http://localhost:8080/api/v1/accounts';
    }

    //  public getAccounts(): Observable<Account[]> {
    //   return this.http.get<Account[]>(this.accountsUrl);
    // }

    // login(loginPayload) {
    //   const headers = {
    //     'Authorization': 'Basic ' + btoa('front-end:a'),
    //     'Content-type': 'application/x-www-form-urlencoded'
    //   }
    //   return this.http.post('http://localhost:8080/api/v1/oauth/token', loginPayload, {headers});
    // }
  // getUsers() {
  //   return this.http.get(this.baseUrl + 'user?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token);
  // }



  // public save(Account: Account) {
  //   return this.http.post<Account>(this.accountsUrl, Account);
  // }
}
