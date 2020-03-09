import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { AccountsComponent } from './accounts/accounts.component';
import { AuthGuard } from './helpers';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [

  { path: 'home', component: HomeComponent, /*canActivate: [AuthGuard]*/ },
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent },
  { path: 'accounts', component: AccountsComponent },
  { path: 'register', component: RegisterComponent },

  
  // otherwise redirect to home
  { path: '**', redirectTo: 'login' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
