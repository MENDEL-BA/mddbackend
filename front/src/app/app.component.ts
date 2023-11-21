import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {BreakpointObserver, Breakpoints, BreakpointState} from "@angular/cdk/layout";
import { AuthService } from './services/auth.service';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  isDesktop!: boolean;
  private sub: Subscription | undefined;

  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService,
    private breakpointObserver: BreakpointObserver) {
  }

  public isStillConected(): void {
    if (this.sessionService.isLogged) {
      this.sessionService.updateUser()
      this.router.navigate(['/posts'])
    } else 
      this.sessionService.logOut();
  }


  ngOnInit() {
    // Automatically close side menu on screens > small breakpoint
    this.sub = this.breakpointObserver
      .observe([Breakpoints.XSmall])
      .subscribe((result: BreakpointState) => {
        this.isDesktop = !result.matches;
      });

    this.isStillConected();
  }

  headerIsshown(): boolean {
    const currentUrl = this.router.url;
    return !(currentUrl === '/'
      ||
      (!this.isDesktop && currentUrl === '/login') ||
      (!this.isDesktop && currentUrl === '/register'));
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }
}
