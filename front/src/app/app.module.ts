import { NgModule , CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared-module/header/header.component';
import { LandingComponent } from './shared-module/landing/landing.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import {MatCardModule} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {ReactiveFormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { A11yModule } from '@angular/cdk/a11y';
import { MatDialogModule } from '@angular/material/dialog';
import {MatToolbarModule} from "@angular/material/toolbar";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexModule} from "@angular/flex-layout";
import { PostsComponent } from './components/posts/posts.component';
import { JwtInterceptor } from './components/interceptors/jwt.interceptor';

@NgModule({
  declarations: [AppComponent, HeaderComponent, LandingComponent, LoginComponent, RegisterComponent, PostsComponent],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    NgIf,
    HttpClientModule,
    A11yModule,
    MatDialogModule,
    MatToolbarModule,
    FlexModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
