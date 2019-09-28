import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {first, tap} from 'rxjs/operators';
import {Subject} from 'rxjs';

import {EmployeeProfile} from '../models/employeeProfile';
import {Observable} from "rxjs/internal/Observable";
import {LogService} from "./log.service";
import {WebsocketService} from "./websocket.service";
import {NotificationStompService} from "./notification-stomp.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  currentUser$: Subject<EmployeeProfile> = new Subject<EmployeeProfile>();
  supervisorRole;
  employeeRole;
  supervisorRole$: Subject<boolean> = new Subject<boolean>();
  employeeRole$: Subject<boolean> = new Subject<boolean>();
  username;
  roleNameE: string;
  roleNameS: string;

  constructor(private http: HttpClient, private log: LogService, private websocketService: WebsocketService, private  notificationStompService: NotificationStompService) {
  }


  myFunctionSupervisor(data: EmployeeProfile, username) {
    console.log(data.roleName + "wlnlwkn lkewklwn lkw");

    console.log(data);
    this.roleNameE = data.roleName.toLocaleString();

    console.log(this.roleNameE);

    console.log("proszę panstwa getEmployeeRole --> " + this.roleNameE);
    console.log("proszępoanstwa getSupervisorRole --> " + this.roleNameS);

    if (this.roleNameE === "SUPERVISOR") {
      this.supervisorRole = true;
      localStorage.setItem('supervisorRole', JSON.stringify({supervisorRole: this.supervisorRole}));
      this.supervisorRole$.next(this.supervisorRole);
      this.log.logger('Zmiana na kierownika!');

    }


  }

  myFunction(data: EmployeeProfile, username) {

    console.log("czy w ogole do tej funkci wejdziem...");
    if (data.email) {
      console.log(data);
      this.roleNameE = data.roleName.toLocaleString();

      console.log(this.roleNameE);

      console.log("proszę panstwa getEmployeeRole --> " + this.roleNameE);
      console.log("proszępoanstwa getSupervisorRole --> " + this.roleNameS);

      if (this.roleNameE === "EMPLOYEE") {

        this.employeeRole = true;
        localStorage.setItem('employeeRole', JSON.stringify({employeeRole: this.employeeRole}));

        this.employeeRole$.next(this.employeeRole);
        this.log.logger('Zmiana na pracownika!');

      }

    }
    else {
      this.getUserProfileS(username).subscribe(data =>
        //    console.log(data.roleName + "wlnlwkn lkewklwn lkw"));
        this.myFunctionSupervisor(data, username));

    }
    console.log("proszę panstwa getEmployeeRole --> " + this.roleNameE);
    console.log("proszępoanstwa getSupervisorRole --> " + this.roleNameS);

  }

  refreshRoles(username): void {
    console.log(username);
    this.getUserProfile(username).subscribe(data =>
      this.myFunction(data, username));


  }

  refreshRolesDuringLogOut(): void {
    //   if (this.getSupervisorRole(username)) {
    this.employeeRole = false;
    this.supervisorRole = false;

    this.employeeRole$.next(this.employeeRole);
    this.supervisorRole$.next(this.supervisorRole);
    this.log.logger('Zarowno kierownik jak i pracownik na false!!');

  }


  checkUsernameAvailability(username: string) {
    return this.http.get<any>(`/api/user/checkUsernameAvailability?username=${username}`);
  }

  checkEmailAvailability(email: string) {
    return this.http.get<any>(`/api/user/checkEmailAvailability?email=${email}`);
  }


  register(user: EmployeeProfile) {
    return this.http.post('/api/auth/signup', user);
  }

  getRoleS(): Observable<boolean> {
    return this.supervisorRole$.asObservable();
  }

  getRoleE(): Observable<boolean> {
    return this.employeeRole$.asObservable();
  }

  getEmployeeRole(username: string): Observable<string> {
    return this.http.get<string>(`/api/auth/userR/${username}`);
  }

  // getSupervisorRole(username: string): Observable<string> {
  //   return this.http.get<string>(`/api/auth/userSR/${username}`);
  // }

  login(username: string, password: string) {

    return this.http.post<any>('/api/auth/signin', {"usernameOrEmail": username, "password": password})
    // this is just the HTTP call,
    // we still need to handle the reception of the token


      .pipe(tap(res => this.setSession(res)), // handles the auth result
        //   shareReplay()
      )
      .pipe(tap(d =>
      {
        console.log("Test1")
        this.websocketService.connectStompClient(this.getAuthToken());
        this.notificationStompService.connectNotificationStompClient();//w p
        console.log("Test2")
      }
  )
      ); // prevents the receiver of this Observable from accidentally triggering multiple POST requests due to multiple subscriptions.

  }

  logout() {
    this.refreshRolesDuringLogOut();

    localStorage.removeItem('employeeRole');
    localStorage.removeItem('supervisorRole');
    localStorage.removeItem('accessToken');
    localStorage.removeItem('currentUser');
    this.currentUser$.next(undefined);
  }

  isLoggedIn() {

    return !!localStorage.getItem('accessToken');
  }

  getAuthToken(): string {
    return localStorage.getItem('accessToken');
  }


  getUserProfile(username): Observable<EmployeeProfile> {
    return this.http.get<EmployeeProfile>(`/api/users/` + username);

  }

  getUserProfileS(username): Observable<EmployeeProfile> {
    return this.http.get<EmployeeProfile>(`/api/auth/usersSupervisor/` + username);
  }


  getCurrentUser(): EmployeeProfile {
    return JSON.parse(localStorage.getItem('currentUser')) as EmployeeProfile;

  }


  private setSession(authResult) {
    localStorage.setItem('accessToken', authResult.accessToken);

    this.http.get('/api/auth/user/me') //|| this.http.get('/api/user/meS')
      .pipe(first())
      .subscribe((user: EmployeeProfile) => {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUser$.next(user);

        },
        // this.http.get('/api/user/meS')
        //   .pipe(first())
        //   .subscribe((user: EmployeeProfile) => {
        //     localStorage.setItem('currentUser', JSON.stringify(user));
        //     this.currentUser$.next(user);
        //   },
        error => {
          console.log(error);
        });
  }

  getEmployeeRoleInfo(): boolean {
    return JSON.parse(localStorage.getItem('employeeRole'));

  }

  getSupervisorRoleInfo(): boolean {
    return JSON.parse(localStorage.getItem('supervisorRole'));

  }

}
