import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account';
import { Observable } from 'rxjs';

 
@Injectable()
export class AccountService {
 
  private accountsUrl: string;
 
  constructor(private http: HttpClient) {
    this.accountsUrl = 'http://localhost:8080/api/v1/accounts';
  }
 
  public findAll(): Observable<Account[]> {
    return this.http.get<Account[]>(this.accountsUrl);
  }
 
  public save(Account: Account) {
    return this.http.post<Account>(this.accountsUrl, Account);
  }
}