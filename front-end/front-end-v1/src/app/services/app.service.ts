<<<<<<< HEAD
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { accounts } from '../accounts/mock-data'
import { User } from '../models/user';


import { Cookie } from "ng2-cookies";

export class loginData {
  constructor(public id: number, public name: string) {}
=======
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams, } from '@angular/common/http';

import { Cookie } from 'ng2-cookies';

export class loginData {
    constructor(
        public id: number,
        public name: string) { }
>>>>>>> origin/front-v2
}

@Injectable()
export class AppService {
<<<<<<< HEAD
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
    return this.http.get(this.baseUrl + 'accounts/' + 'accounts?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token);
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
=======
    constructor(
        private _router: Router, private http: HttpClient) { }

        // baseUrl: string = 'http://localhost:8080/api/v1/';

        login(loginPayload) {
          const headers = {
            'Authorization': 'Basic ' + btoa('front-end:a'),
            'Content-type': 'application/x-www-form-urlencoded'
          }
          return this.http.post('http://localhost:8080/api/v1/oauth/token', loginPayload, {headers});
        }

    // saveToken(token) {
    //     var expireDate = new Date().getTime() + (1000 * token.expires_in);
    //     Cookie.set("access_token", token.access_token, expireDate);
    //     this._router.navigate(['/']);
    // }

    // getResource(resourceUrl): Observable<Foo> {
    //     var headers =
    //         new Headers({
    //             'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
    //             'Authorization': 'Bearer ' + Cookie.get('access_token')
    //         });
    //     var options = new RequestOptions({ headers: headers });
    //     return this._http.get(resourceUrl, options)
    //         .map((res: Response) => res.json())
    //         .catch((error: any) =>
    //             Observable.throw(error.json().error || 'Server error'));
    // }

    // checkCredentials() {
    //     if (!Cookie.check('access_token')) {
    //         this._router.navigate(['/login']);
    //     }
    // }

    // logout() {
    //     Cookie.delete('access_token');
    //     this._router.navigate(['/login']);
    // }
}

>>>>>>> origin/front-v2
