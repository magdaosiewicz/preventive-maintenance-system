import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// import { PollListComponent } from './app-polling/poll-list/poll-list.component';
// import { PollNewComponent } from './app-polling/poll-new/poll-new.component';
import { LoginComponent } from './app-user/login/login.component';
import { SignupComponent } from './app-user/signup/signup.component';
import { ProfileComponent } from './app-user/profile/profile.component';
import {SupervisorProfileComponent} from "./app-user/supervisor-profile/supervisor-profile.component";
import {AppAuthModule} from "./app-auth/app-auth.module";

const routes: Routes = [
  {
    path: '',
    children: [
      // { path: '', redirectTo: '/polls', pathMatch: 'full' },
      // { path: 'polls', component: PollListComponent },
      // { path: 'poll/new', component: PollNewComponent },
      { path: 'login', component: LoginComponent   },
      { path: 'signup', component: SignupComponent },
      { path: 'users/:username', component: ProfileComponent },
      { path: 'usersSupervisor/:username', component: SupervisorProfileComponent}

    ]
//    canActivate: [WebAuthentication],
    //runGuardsAndResolvers: 'always',
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
