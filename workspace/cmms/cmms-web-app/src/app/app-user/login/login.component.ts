import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {first} from 'rxjs/internal/operators/first';

import {AuthService} from '../../app-auth/services/auth.service';
import {AlertService} from '../../app-alert/services/alert.service';
import {EmployeeProfile} from "../../app-auth/models/employeeProfile";
import {LogService} from "../../app-auth/services/log.service";
import {RoleService} from "../role.service";
import {TabsetComponent} from "ngx-bootstrap/tabs";
import {WebsocketService} from "../../app-auth/services/websocket.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: []
})
export class LoginComponent implements OnInit {


  @ViewChild(TabsetComponent)
  tabSet: TabsetComponent;
  message: string;
  loginForm: FormGroup;
  userInfor: EmployeeProfile = {} as EmployeeProfile;
  user: EmployeeProfile;
 // message: EmployeeProfile;
  private stompClient = null;
  disabled = true;
  greetings: string[] = [];



  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService, private alertService: AlertService,
              private log: LogService, private roleService: RoleService, private websocketService: WebsocketService) {}

  ngOnInit() {


  //  this.accountService.accountStream.subscribe(account => this.filterNavitemsByRole(account));
   // this.chatService.getForClientChatData().subscribe(res => {
      //this.talkJsService.createCurrentSession(res);
    //});

    this.roleService.currentMessage.subscribe(messaga => this.user=messaga);
    this.loginForm = this.formBuilder.group({
      usernameOrEmail: ['', Validators.required],
      password: ['', Validators.required]
    });
   // this.connect();
    // reset login status
    this.authService.logout();
    // get return url from route parameters or default to '/'

    if (this.isLoggedIn()) {
      this.loadUserProfile(this.userInfo.username);

    }


  }

  showGreeting(message) {
    this.greetings.push(message);
  }


  // connect() {
  //   const socket = new SockJS('http://localhost:8080/notifications');
  //   this.stompClient = Stomp.over(socket);
  //
  //   const _this = this;
  //   this.stompClient.connect({}, function (frame) {
  //     _this.setConnected(true);
  //     console.log('Connected: ' + frame);
  //     _this.stompClient.subscribe('/topic/hi', function (hello) {
  //       _this.showGreeting(hello.body);
  //     });
  //   });
  // }
  //
  // disconnect() {
  //   if (this.stompClient != null) {
  //     this.stompClient.disconnect();
  //   }
  //
  //   this.setConnected(false);
  //   console.log('Disconnected!');
  // }

  setConnected(connected: boolean) {
    this.disabled = !connected;
  }




  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();}




  myFunctionSupervisor(data: EmployeeProfile){
    console.log("my function supervisor c");
  //  if(data.email){
      this.user=data;
    localStorage.setItem('user', JSON.stringify({user: this.user}));
    console.log(data);
      console.log(this.user);
      this.newMessage();
  //  }
  }


  myFunction(data: EmployeeProfile, username){
    console.log("dkncklnslkdncdlksnclknlcksnldc");
    if(data.email){
      this.user=data;
      localStorage.setItem('user', JSON.stringify({user: this.user}));
      console.log(data);
      console.log(this.user);
      this.newMessage();
    }
    else{
      console.log("jesli data jest nullem! ");
      this.authService.getUserProfileS(username).subscribe(data =>
        this.myFunctionSupervisor(data));
    }

  }

  getUser(username){
    this.authService.getUserProfile(username).subscribe(data =>
 this.myFunction(data, username));
  }

  refreshKurwa(username): void {
     this.getUser(username);
     this.authService.refreshRoles(username);
   //  this.connect();

  }

    get f()
    {
      return this.loginForm.controls;
    }

    onSubmit() {
      // stop here if form is invalid
      if (this.loginForm.invalid) {
        return;
      }

      this.authService.login(this.f.usernameOrEmail.value, this.f.password.value)
        .pipe(first())
        .subscribe(
          () => this.router.navigate(['/']),
          error => {
            this.alertService.error(error);
          });

    }

  newMessage(): void{
    this.roleService.changeMessage(this.user);
  };


    loadUserProfile(username: string) {


     // let headers = new HttpHeaders();
     // headers = headers.append('Authorization',);
  //      this.tabSet.tabs[0].active = true;
      this.authService.getUserProfile(username)
        .pipe(first())
        .subscribe(res => {
            this.userInfor = res;
        //    this.log.logger(res.surname);

        },
          error => {
            console.log(error);
          });

      this.authService.getUserProfileS(username)
         .pipe(first())
        .subscribe(res => {
          this.userInfor = res;
          //    this.log.logger(res.surname);

        },
          error => {
            console.log(error);
          });

    }


    get userInfo()
    {
      const currentUser = this.authService.getCurrentUser();
      if (!currentUser) {
        return {} as EmployeeProfile;
      }
      return currentUser;
    }

  }
