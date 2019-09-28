
import * as SockJS from 'sockjs-client';
import {Injectable} from '@angular/core';
import {Observable, Subject, Subscription} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {WebsocketService} from "./websocket.service";

@Injectable({
  providedIn: 'root'
})
export class NotificationStompService {

  notificationChannelObservable: Observable<any>;
  notificationAmountChange = new Subject();
  private notificationChannelSubscription: Subscription;

  constructor(private websocketService: WebsocketService, private http: HttpClient) {
  }

  connectNotificationStompClient() {
    console.log("ohessas");

    this.notificationChannelObservable = this.websocketService._stompService.subscribe('/queue/notifications', {  });
//    this.notificationChannelObservable = this.websocketService._stompService.subscribe('/queue/topic', {});
    this.notificationChannelSubscription = this.notificationChannelObservable.subscribe((message) => {
      console.log(message);
     // this.notificationAmountChange.next(message);
    });
  }

  disconnectNotificationStompClient() {
    this.notificationChannelSubscription.unsubscribe();
  }

}
// /queue/recent-conversation-notifications
// /user/queue/notifications
