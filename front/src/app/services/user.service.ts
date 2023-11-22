import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserInterface } from '../interfaces/user.interface';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../interfaces/registerRequest';
import { SessionInformation } from '../interfaces/sessionInformation';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl: string = environment.baseUrl;

  private pathService = this.apiUrl+'/users';
  constructor(private httpClient: HttpClient) {
  }

  public getUserById(id: number): Observable<UserInterface> {
    return this.httpClient.get<UserInterface>(`${this.pathService}/${id}`);
  }

  public updateUser(user: RegisterRequest): Observable<SessionInformation> {
    return this.httpClient.put<SessionInformation>(`${this.pathService}`, user);
  }

  public deleteUser(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/${id}`);
  }
}
