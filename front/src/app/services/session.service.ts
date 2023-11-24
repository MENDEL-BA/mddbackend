import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Observable, Subscription, take} from "rxjs";
import { UserInterface } from '../interfaces/user.interface';
import { AuthService } from './auth.service';
import { SessionInformation } from '../interfaces/sessionInformation';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  public authUser: UserInterface | undefined;
  public user$: Observable<UserInterface> | undefined;
  private subscription: Subscription | undefined;
  public isLogged = false;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }
  constructor(private authService: AuthService) {
  }

  public logIn(sessionInformation: SessionInformation): void {
    this.isLogged = true;
    localStorage.setItem('token', sessionInformation.token);
    this.getMe();
  }

  public updateUser(): void {
    this.getMe();
  }

  private getMe(): void {
    this.user$ = this.authService.me();
    this.subscription = this.authService.me()
      .pipe(take(1))
      .subscribe({
        next: (user: UserInterface) => {
          this.authUser = user;
        },
        error: () => this.logOut()
      });
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.authUser = undefined;
  }
}
