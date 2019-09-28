import {Component, OnInit} from '@angular/core';
import {AuthService} from "./app-auth/services/auth.service";

@Component({
  selector: 'app-root',
  template: `<app-layout></app-layout>`,
  styles: []
})
export class AppComponent implements OnInit{


  constructor(private authService: AuthService){}

  ngOnInit(): void {



  }





}
