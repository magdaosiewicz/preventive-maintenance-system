import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AppAuthModule } from './app-auth/app-auth.module';
import { AppLayoutModule } from './app-layout/app-layout.module';
// import { AppPollingModule } from './app-polling/app-polling.module';
import { AppUserModule } from './app-user/app-user.module';
import { AppAlertModule } from './app-alert/app-alert.module';
import {AppHeaderComponent} from "./app-layout/app-header/app-header.component";
import {StompRService} from "@stomp/ng2-stompjs";

@NgModule({
  declarations: [
    AppComponent

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    AppRoutingModule,
    AppAuthModule,
    AppLayoutModule,
    // AppPollingModule,
    AppUserModule,
    AppAlertModule

  ],
  providers: [StompRService],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
