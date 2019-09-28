import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { AvatarModule } from 'ng2-avatar';

import { AppBootstrapModule } from './../app-bootstrap/app-bootstrap.module';

import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {ProfileComponent} from "./profile/profile.component";
import {SupervisorProfileComponent} from "./supervisor-profile/supervisor-profile.component";

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    AvatarModule,
    AppBootstrapModule
  ],
  declarations: [LoginComponent, ProfileComponent, SignupComponent, SupervisorProfileComponent]
})
export class AppUserModule { }
