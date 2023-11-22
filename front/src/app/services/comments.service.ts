import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MessageInterface} from "../interfaces/message.interface";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private apiUrl: string = environment.baseUrl;
  private pathService = this.apiUrl+'/messages';
  constructor(private httpClient: HttpClient) {
  }

  public createMessage(message: MessageInterface): Observable<any> {
    return this.httpClient.post<any>(`${this.pathService}`, message);
  }
}
