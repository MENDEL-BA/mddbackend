import { BreakpointObserver, BreakpointState, Breakpoints } from '@angular/cdk/layout';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription, take } from 'rxjs';
import { SubjectInterface } from 'src/app/interfaces/subject.interface';
import { UserInterface, UserUpdate } from 'src/app/interfaces/user.interface';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { UserService } from 'src/app/services/user.service';
import {MatSnackBar} from "@angular/material/snack-bar";
import { SessionInformation } from 'src/app/interfaces/sessionInformation';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent {
  public onError = false;
  isDesktop!: boolean;
  subjects!: SubjectInterface[];
  user?: UserInterface;
  public form!: FormGroup;
  public hide = true;
  private sub?: Subscription;

  constructor(
    private userService: UserService,
    private subscriptionService: SubscriptionService,
    private breakpointObserver: BreakpointObserver,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private router: Router,
    private authService: AuthService,
    private matSnackBar: MatSnackBar
  ) {
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  ngOnInit(): void {
    this.update();
    this.initForm();
    this.sub = this.breakpointObserver
      .observe([Breakpoints.XSmall])
      .subscribe((result: BreakpointState) => {
        this.isDesktop = !result.matches;
      });

  }

  public submit(): void {
    const registerRequest = this.form?.value as UserUpdate;
    registerRequest.id = <number>this.user?.id;
    registerRequest.subjects = this.user?.subjects;
    registerRequest.createdAt = <Date>this.user?.createdAt;
    console.log(registerRequest);
    this.userService
      .updateUser(registerRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: SessionInformation) => {
          localStorage.setItem("token",response.token);
          this.showNotification('Mise à jour effectuée', 7000);
        },
        error: () => this.showNotification('une erreur est arrivée', 3000)
      });
  }

  unSubscribe(subject: SubjectInterface) {
    // @ts-ignore
    let subscription: SubscriptionInterface = {subjectId: subject.id, userId: this.user.id};
    this.subscriptionService.unSubscribeUser(subscription)
      .pipe(take(1))
      .subscribe({
        next: (response) => {
          this.update();
          this.showNotification('Vous vous etes desabonné', 3000);
          return response;
        },
        error: () => this.showNotification('La subscription a pas ete trouve ', 3000)
      });
  }

  private initForm(): void {
    this.form = this.fb.group({
      firstName: [
        '',
        [
          Validators.required,
          Validators.min(3),
          Validators.max(20)
        ]
      ],
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
        ]
      ]
    });
  }

  public logOut() {
    this.sessionService.logOut();
    this.router.navigate(['/']);
    this.showNotification('Vous etes deconnecte', 2000);
  }

  private showNotification(msg: string, duration: number) {
    this.matSnackBar.open(msg, '', {
      duration: duration,
      panelClass: ['multiline-snackbar'],
    });
  }

  private update() {
    this.authService.me()
      .subscribe(
        (user: UserInterface) => {
          this.user = user;
          this.subjects = this.makeUserSubjectsSubscribeTrue(this.user?.subjects);
          this.form?.setValue({
            // @ts-ignore
            firstName: this.user?.firstName,
            // @ts-ignore
            email: this.user?.email,
            password: '',
          });
        }, () => this.logOut());
  }

  private makeUserSubjectsSubscribeTrue(subjects: SubjectInterface[] | undefined): SubjectInterface[] {
    // @ts-ignore
    return subjects.map((value: SubjectInterface) => {
      value.subscription = true;
      return value;
    });
  }
}
