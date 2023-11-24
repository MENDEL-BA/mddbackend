import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {Location} from '@angular/common';
import {AuthService} from "../../services/auth.service";
import {take} from "rxjs";
import { SessionService } from 'src/app/services/session.service';
import { LoginRequest } from 'src/app/interfaces/loginRequest';
import { SessionInformation } from 'src/app/interfaces/sessionInformation';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public hide = true;
  public onError = false;
  public form!: FormGroup;

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private router: Router,
              private sessionService: SessionService,
              private location: Location) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],
      password: [
        '',
        [
          Validators.required,
        ]
      ]
    });
  }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: SessionInformation) => {
          localStorage.setItem('token', response.token);
          this.sessionService.logIn(response);
          this.router.navigate(['/posts']);
        },
        error: () => this.onError = true,
      });
  }

  public return() {
    window.history.back();
  }
}
