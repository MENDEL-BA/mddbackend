import {ENVIRONMENT_INITIALIZER, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterRequest} from "../interfaces/registerRequest";
import {Observable} from "rxjs";
import {LoginRequest} from "../interfaces/loginRequest";
import { SessionInformation } from '../interfaces/sessionInformation';
import { UserInterface } from '../interfaces/user.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl: string = environment.baseUrl;

  private pathService = this.apiUrl+'/auths';

  constructor(private httpClient: HttpClient) {
  }

  public register(registerRequest: RegisterRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }

  public me(): Observable<UserInterface> {
    return this.httpClient.get<UserInterface>(`${this.pathService}/me`);
  }
}