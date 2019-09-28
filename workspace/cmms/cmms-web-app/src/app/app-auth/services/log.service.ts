import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class LogService {

  logger(log: string) {
    console.log(log);
  }
}
