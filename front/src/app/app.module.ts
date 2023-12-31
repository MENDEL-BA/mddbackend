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
import {NgIf, NgOptimizedImage} from "@angular/common";
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { A11yModule } from '@angular/cdk/a11y';
import { MatDialogModule } from '@angular/material/dialog';
import {MatToolbarModule} from "@angular/material/toolbar";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexModule} from "@angular/flex-layout";
import { JwtInterceptor } from './components/interceptors/jwt.interceptor';
import { MeComponent } from './components/me/me.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { CommentsComponent } from './components/comments/comments.component';
import { PostCardComponent } from './components/post-card/post-card.component';
import { PostCreateComponent } from './components/post-create/post-create.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { PostsListComponent } from './components/posts-list/posts-list.component';
import {MatRippleModule} from "@angular/material/core";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatListModule} from "@angular/material/list";
import {MatSelectModule} from "@angular/material/select";
import { SubjectComponent } from './subject/subject.component';
import { SubjectCardComponent } from './components/subject-card/subject-card.component';
@NgModule({
  declarations: [AppComponent, HeaderComponent, LandingComponent, 
    LoginComponent, RegisterComponent, MeComponent,
    CommentsComponent, PostCardComponent, PostCreateComponent, PostDetailComponent, 
    PostsListComponent, SubjectComponent, SubjectCardComponent],
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
    ReactiveFormsModule,
    MatSnackBarModule,
    MatRippleModule,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatListModule,
    ReactiveFormsModule,
    MatSelectModule,
    NgOptimizedImage
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
