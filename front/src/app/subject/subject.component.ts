import {Component, OnInit} from '@angular/core';
import { SubjectInterface } from '../interfaces/subject.interface';
import {iif, Subscription, take} from 'rxjs';
import { UserInterface } from '../interfaces/user.interface';
import { SubjectsService } from '../services/subjects.service';
import { AuthService } from '../services/auth.service';
import { SubscriptionService } from '../services/subscription.service';
import { BreakpointObserver, BreakpointState, Breakpoints } from '@angular/cdk/layout';
import { SessionService } from '../services/session.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SubscriptionInterface } from '../interfaces/subscription.interface';

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent {
  isDesktop!: boolean;
  userSubjects: SubjectInterface[] | undefined;
  subjects!: SubjectInterface[];
  user!: UserInterface;
  private sub!: Subscription;

  constructor(
    private subjectService: SubjectsService,
    private authService: AuthService,
    private subscriptionService: SubscriptionService,
    private breakpointObserver: BreakpointObserver,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar
  ) {
  }

  ngOnInit(): void {
    this.update();
    this.sub = this.breakpointObserver
      .observe([Breakpoints.XSmall])
      .subscribe((result: BreakpointState) => {
        this.isDesktop = !result.matches;
      });
  }

  private update() {
    this.authService.me()
      .subscribe(
        (user: UserInterface) => {
          this.user = user;
          this.subjects = this.makeUserSubjectsSubscribeTrue(this.user?.subjects);
          this.userSubjects = this.user?.subjects;
          this.initSubjects();

        }, () => this.matSnackBar.open('Une erreur est arrivée', '', {
          duration: 3000,
        }));
    this.sessionService.updateUser();
  }

  subscription(subject: SubjectInterface) {
    // const inputValue: number = (event.target as HTMLInputElement).valueAsNumber;
    // @ts-ignore
    let subscription: SubscriptionInterface = {subjectId: subject.id, userId: this.user?.id};
    if (subject.subscription === false){
      console.log(' abonnee ', JSON.stringify(subject))
      this.subscriptionService.subscribeUser(subscription)
        .pipe(take(1))
        .subscribe({
          next: (response) => {
            this.update();
            this.matSnackBar.open('Vous vous etes abonné au sujet ', '', {
              duration: 3000,
            });
            return response;
          },
          error: () => this.matSnackBar.open('Une erreur est arrivée', '', {
            duration: 3000,
          })
        });
    }else {
      this.subscriptionService.unSubscribeUser(subscription)
        .pipe(take(1))
        .subscribe({
          error: () => this.matSnackBar.open('La subscription a pas ete trouve', '', {
            duration: 3000,
          }),
          next: (response) => {
            this.update();
            this.matSnackBar.open('Vous vous etes desabonné ', '', {
              duration: 3000,
            });
            return response;
          }
        });
    }

  }

  private initSubjects(): void {
    let userSubject: SubjectInterface[] = this.makeUserSubjectsSubscribeTrue(this.userSubjects)
    let allSubjects: SubjectInterface[];
    this.subjectService.getAllSubjects()
      .pipe(take(1))
      .subscribe({
        next: (value: SubjectInterface[]) => {
          allSubjects = value;
          this.subjects = this.uniqueObjectArray(userSubject, allSubjects);
        }
      });

  }

  private makeUserSubjectsSubscribeTrue(subjects: SubjectInterface[] | undefined): SubjectInterface[] {
    // @ts-ignore
    return subjects.map((value: SubjectInterface) => {
      value.subscription = true;
      return value;
    });
  }

  private uniqueObjectArray(baseArray: SubjectInterface[], mergeArray: SubjectInterface[]) {
    // we can't compare unique objects within an array ~es6...
    // ...e.g. concat/destructure/Set()
    // so we'll create a mapping of: item.id* for each -> item
    const uniqueMap = new Map();
    const uniqueArray: SubjectInterface[] = [];

    // hash array items by id*
    baseArray.forEach(item => !uniqueMap.has(item.id) && uniqueMap.set(item.id, item))
    mergeArray.forEach(item => !uniqueMap.has(item.id) && uniqueMap.set(item.id, item))

    // hash -> array
    uniqueMap.forEach(item => uniqueArray.push(item))
    return uniqueArray
  }
}

