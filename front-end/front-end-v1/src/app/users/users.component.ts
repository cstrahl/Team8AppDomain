import { Component, OnInit, PipeTransform } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountService } from '../services/account.service';
import {AppService} from '../services/app.service'
import { HttpParams } from '@angular/common/http';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';


class User {
  id: number;
  username: string;
  role: string;
  status: string;
}

const Users: User[] = [
  {
    id: 1,
    username: 'manager1',
    role: 'Admin',
    status: 'Active',
  },
  {
    id: 2,
    username: 'accountant1',
    role: 'Accountant',
    status: 'Active',
  },

];

function search(text: string, pipe: PipeTransform): User[] {
  return Users.filter(user => {
    const term = text.toLowerCase();
    return user.username.toLowerCase().includes(term)
        || pipe.transform(user.role).includes(term)
        || pipe.transform(user.id).includes(term);
  });
}

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users = Users;
  filter = new FormControl('');

  constructor(private router: Router, private AppService: AppService) { }

  ngOnInit(): void {
    // this.AppService.getUsers().subscribe(data => {
    //   this.users;
    // });
  }

  logout(){
    this.router.navigate(['/login']);
  }

}
