import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Account } from '../models/account'
import { User } from '../models/user';


import { Cookie } from "ng2-cookies";

export class loginData {
  constructor(public id: number, public name: string) {}
}

@Injectable()
export class AppService {
  constructor(private _router: Router, private http: HttpClient) {}

  baseUrl: string = "http://localhost:8080/api/v1/";

  login(loginPayload) {
    const headers = {
      Authorization: "Basic " + btoa("front-end:a"),
      "Content-type": "application/x-www-form-urlencoded"
    };
    return this.http.post(
      "http://localhost:8080/api/v1/oauth/token",
      loginPayload,
      { headers }
    );
  }

  getAccounts(){
    const headers = {
      Authorization: "Bearer " + JSON.parse(window.sessionStorage.getItem('token')).access_token
    };
    return this.http.get(
      "http://localhost:8080/api/v1/accounts",
      { headers }
    );

        // return this.http.get(this.baseUrl + 'accounts/' + 'accounts?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token);

  }

  createAccount(account){
    const headers = {
      Authorization: "Bearer " + JSON.parse(window.sessionStorage.getItem('token')).access_token
    };


    return this.http.post(
      "http://localhost:8080/api/v1/accounts",
        account,
      { headers }
    );


    // return this.http.post(this.baseUrl + 'user?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token, user);
  }



  getAccountById(id: number) {
    return this.http.get(this.baseUrl + 'accounts/' + id + '?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token);
  }

  getUsers() {
    return this.http.get(
      this.baseUrl +
        "user?access_token=" +
        JSON.parse(window.sessionStorage.getItem("token")).access_token
    );
  }

  createUser(user: User){
    return this.http.post(this.baseUrl + 'user?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token, user);
  }

  // updateUser(user: User): Observable {
  //   return this.http.put(this.baseUrl + 'user/' + user.id + '?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token, user);
  // }

  deleteUser(id: number){
    return this.http.delete(this.baseUrl + 'user/' + id + '?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token);
  }

  // logout() {
  //     Cookie.delete('access_token');
  //     this._router.navigate(['/login']);
  // }

}
