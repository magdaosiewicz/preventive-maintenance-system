import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../app-auth/services/auth.service";
import {Subscription} from "rxjs/internal/Subscription";
import {EmployeeProfile} from "../../app-auth/models/employeeProfile";
import {TabsetComponent} from "ngx-bootstrap/tabs";
import {first} from "rxjs/internal/operators/first";
import {b} from "@angular/core/src/render3";
import {RoleService} from "../role.service";

@Component({
  selector: 'app-supervisor-profile',
  templateUrl: './supervisor-profile.component.html',
  styleUrls: ['./supervisor-profile.component.css']
})
export class SupervisorProfileComponent implements OnInit, OnDestroy {



  @ViewChild(TabsetComponent)
  tabSet: TabsetComponent;
  username: string;
  userInfo: EmployeeProfile = {} as EmployeeProfile;
  private sub: Subscription;
  employeeRole;
  supervisorRole;
  checkRoleSuper:  boolean;
  user: EmployeeProfile;

  constructor(private route: ActivatedRoute, private authService: AuthService, private roleService: RoleService) {
    this.checkRoleSuper=this.authService.supervisorRole;
  }


  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.username = params['username'];
      this.roleService.currentMessage.subscribe(messaga => this.user=messaga);

      this.loadUserProfile(this.username);
    });

  }

  loadUserProfile(username: string) {
    // this.authService.getUserProfile(username)
    //   .pipe(first())
    //   .subscribe(res => {
    //       this.userInfo = res;
    //     },
        this.authService.getUserProfileS(username)
          .pipe(first())
          .subscribe(re => {
              this.userInfo = re;
            },
            error => {
              console.log("cos nie gra w supervosrprofile.component w loadUserProfile" );
            })

  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }



}
