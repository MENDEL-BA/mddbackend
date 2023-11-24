import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingComponent } from './shared-module/landing/landing.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './components/guards/auth.guard';
import { MeComponent } from './components/me/me.component';
import { PostsListComponent } from './components/posts-list/posts-list.component';
import { PostCreateComponent } from './components/post-create/post-create.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { SubjectComponent } from './subject/subject.component';

const routes: Routes = [
  { path: '', component: LandingComponent },
  {
    path: 'posts',
    component: PostsListComponent,
    canActivate: [AuthGuard],
  },
  {
    title: 'Create Post',
    path: 'posts/create',
    component: PostCreateComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'posts/detail/:id',
    component: PostDetailComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'me',
    component: MeComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'subjects',
    component: SubjectComponent,
    canActivate: [AuthGuard],
  },
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: '**', redirectTo: '404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
