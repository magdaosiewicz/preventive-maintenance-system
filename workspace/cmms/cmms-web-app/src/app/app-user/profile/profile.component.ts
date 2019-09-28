import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/internal/Subscription';
import {first} from 'rxjs/internal/operators/first';
import {AuthService} from '../../app-auth/services/auth.service';
import {EmployeeProfile} from '../../app-auth/models/employeeProfile';
import {TabsetComponent} from 'ngx-bootstrap/tabs';
import {RoleService} from "../role.service";
import {RoleName} from "../../app-auth/models/roleName";
import {WebsocketService} from "../../app-auth/services/websocket.service";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  @ViewChild(TabsetComponent)
  tabSet: TabsetComponent;
  username: string;
  private sub: Subscription;
  user : EmployeeProfile;

  constructor(private route: ActivatedRoute, private authService: AuthService, private roleService: RoleService,
              private websocketService: WebsocketService) {
  }

  ngOnInit() {
    this.authService.currentUser$.subscribe(user =>
    console.log(user.name));


    this.sub = this.route.params.subscribe(params => {
      this.username = params['username'];
      this.roleService.currentMessage.subscribe(messaga => this.user=messaga);
      this.loadUserProfile(this.username);
    });

    // this.websocketService.connectStompClient(this.tokenService.getAuthToken())
    // this.accountService.accountStream.subscribe(account => this.filterNavitemsByRole(account));
    // this.chatService.getForClientChatData().subscribe(res => {
    //   this.talkJsService.createCurrentSession(res);
    // });


  }


  loadUserProfile(username: string) {
    this.authService.getUserProfile(username)

     .pipe(first())
      .subscribe(res => {
          this.user = res;
        },
            error => {
              console.log("cos nie gra w profile.component w loadUserProfile");
            });

  }


  get userInfo()
  {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      return new EmployeeProfile(77, "sdfsf", "sdfsdf", "sdfsfsdf", "kjfskkj@wp.pl", RoleName.EMPLOYEE);
    }
    return currentUser;
  }




}
