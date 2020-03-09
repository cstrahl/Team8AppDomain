import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams, } from '@angular/common/http';

import { Cookie } from 'ng2-cookies';

export class loginData {
    constructor(
        public id: number,
        public name: string) { }
}

@Injectable()
export class AppService {
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

