import {Injectable} from "@angular/core";
import {Subject} from "rxjs/internal/Subject";
import {Observable} from "rxjs/internal/Observable";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {FormGroup} from "@angular/forms";
import {EmployeeProfile} from "../app-auth/models/employeeProfile";

@Injectable({
  providedIn: 'root'
})
export class RoleService{

  // private supervisorRole = new Subject<string>();
  // private employeeRole = new Subject<string>();
  messageSource = new BehaviorSubject<EmployeeProfile>(null);
  currentMessage = this.messageSource.asObservable();

  constructor(){}

  changeMessage(message: EmployeeProfile){
    this.messageSource.next(message);

  }


  // refreshRoles() {
  //   this.sumClicks += 1;
  //   this.sum.next(this.sumClicks);
  //   this.log.logger('Kliknięcie!');
  // }
  //

  // getsupervisorRole(): Observable<string>{
  //   return this.supervisorRole.asObservable();
  // }

}
