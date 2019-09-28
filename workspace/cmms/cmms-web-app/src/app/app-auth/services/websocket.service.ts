import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {StompConfig, StompRService} from "@stomp/ng2-stompjs";
import * as SockJS from 'sockjs-client';
import {Observable, Subject, Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";


export function socketProvider() {
  return new SockJS('http://localhost:8080' + '/ws/notifications/');
}

const stompConfig: StompConfig = {
  // Which server?
  url: socketProvider,

  // Headers
  // Typical keys: login, passcode, host
  headers: {},

  // How often to heartbeat?
  // Interval in milliseconds, set to 0 to disable
  heartbeat_in: 0, // Typical value 0 - disabled
  heartbeat_out: 20000, // Typical value 20000 - every 20 seconds

  // Wait in milliseconds before attempting auto reconnect
  // Set to 0 to disable
  // Typical value 5000 (5 seconds)
  reconnect_delay: 5000,

  // Will log diagnostics on console
  debug: false
};


@Injectable({
  providedIn: "root"
})
export class WebsocketService {

  constructor(public _stompService: StompRService, private http: HttpClient) {
  }

  connectStompClient(token) {
    const stompConfigWithHeaders = stompConfig;
    stompConfigWithHeaders.headers['Authorization'] = token;
    console.log(token);
    this._stompService.config = stompConfigWithHeaders;
    this._stompService.initAndConnect();
  //  this._stompService.subscribe('/queue/notifications', function (schedule) {
    console.log("chuje muje dzikie weze");
  //});
    }

  disconectStompClient() {
    this._stompService.disconnect();
  }

}
