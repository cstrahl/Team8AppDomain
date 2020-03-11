import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import '../Include/logo.png';
import { AccountsComponent } from './accounts/accounts.component';
import { RegisterComponent } from './register/register.component';
import { AddAccountComponent } from './add-account/add-account.component';
import { ViewComponent } from './view-account/view.component';
import { AppService } from './services/app.service';
import { AccountService } from './services/account.service';
import { UsersComponent } from './users/users.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    AccountsComponent,
    RegisterComponent,
    AddAccountComponent,
    ViewComponent,
    UsersComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [AppService, AccountService],
  bootstrap: [AppComponent]
})
export class AppModule { }
