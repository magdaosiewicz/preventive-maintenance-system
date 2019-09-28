import { Component, OnInit } from '@angular/core';
import {AuthService} from "../app-auth/services/auth.service";
import {EmployeeProfile} from "../app-auth/models/employeeProfile";

@Component({
  selector: 'app-content',
  template: '<router-outlet> </router-outlet>' ,
  styles: []
})
export class AppContentComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }


}
