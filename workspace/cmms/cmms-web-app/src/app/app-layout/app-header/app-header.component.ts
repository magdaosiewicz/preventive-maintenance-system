import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from '../../app-auth/services/auth.service';
import {EmployeeProfile} from '../../app-auth/models/employeeProfile';
import {first} from "rxjs/internal/operators/first";
import {TabsetComponent} from "ngx-bootstrap/tabs";
import {Subscription} from "rxjs/internal/Subscription";
import {RoleService} from "../../app-user/role.service";
import {RoleName} from "../../app-auth/models/roleName";

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html',
  styles: []
})
export class AppHeaderComponent implements OnInit {


  @ViewChild(TabsetComponent)
  tabSet: TabsetComponent;
  userInfor: EmployeeProfile = {} as EmployeeProfile;
  currentUser: EmployeeProfile;
  isCollapsed = true;
  public employeeRole;
  public supervisorRole;
  username: string;
  private sub: Subscription;
  user: EmployeeProfile;
  userName: string;

  constructor(private route: ActivatedRoute, private router: Router, private toastr: ToastrService, private authService: AuthService, private roleService: RoleService) {
  }


  ngOnInit() {

    this.roleService.currentMessage.subscribe(messaga => this.user=messaga);

    this.authService.getRoleE().subscribe(data =>
      this.employeeRole = data);

    this.authService.getRoleS().subscribe(data =>
      this.supervisorRole = data);

    if (this.isLoggedIn()) {
    this.loadUserProfile(this.userInfo.username);

    }


  }

  loadUserProfile(username: string) {
   // this.tabSet.tabs[0].active = true;
    this.authService.getUserProfile(username)
        .pipe(first())
      .subscribe(res => {
        this.userInfor = res;
      },
        error => {
          console.log(error);
        });

    this.authService.getUserProfileS(username)
    //    .pipe(first())
      .subscribe(res => {
        this.userInfor = res;
      },
        error => {
          console.log(error);
        });
  }

  isLoggedIn(): boolean {

    return this.authService.isLoggedIn();


  }


  logout() {
    this.authService.logout();
  //  this.authService.refreshRolesDuringLogOut();
    this.toastr.success("You're successfully logged out.", "Polling App");
    this.router.navigate(['login']);
  }

  get userInfo()
  {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      return new EmployeeProfile(77, "sdfsf", "sdfsdf", "sdfsfsdf", "kjfskkj@wp.pl", RoleName.EMPLOYEE);
    }
    return currentUser;
  };


  get roleEmployeeInfo()
  {
    const roleInformation = this.authService.getEmployeeRoleInfo();
    if (!roleInformation) {
      return false
    }
    return roleInformation;
  };

  get roleSupervisorInfo()
  {
    const roleInformation = this.authService.getSupervisorRoleInfo();
    if (!roleInformation) {
      return false
    }
    return roleInformation;
  };


}
