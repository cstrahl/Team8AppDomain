import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import {AppService} from '../services/app.service'


@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {

  addAccountForm = new FormGroup({
    name: new FormControl(''),
    accountNumber: new FormControl(''),
    description: new FormControl(''),
    normalSide: new FormControl(''),
    category: new FormControl(''),
    subcategory: new FormControl(''),
    initialBalance : new FormControl(''),
    debit: new FormControl(''),
    credit: new FormControl(''),
    balance : new FormControl(''),
    accountAdded : new FormControl(''),
    userId: new FormControl(''),
    order: new FormControl(''),
    statementName: new FormControl(''),
    comment: new FormControl(''),
  });

  constructor(private router: Router, private AppService: AppService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.addAccountForm = this.formBuilder.group({
    name: [''],
    accountNumber: [''],
    description:[''],
    normalSide: [''],
    category: [''],
    subcategory: [''],
    initialBalance : [''],
    debit: [''],
    credit: [''],
    balance : [''],
    accountAdded : [''],
    userId: [''],
    order: [''],
    statementName: [''],
    comment: [''],

    });
  }

  onSubmit(){

    this.AppService.createAccount(JSON.stringify(this.addAccountForm.getRawValue()));
    this.router.navigate(['/accounts']);
  }

  logout(){
    this.router.navigate(['/login']);
  }

}
