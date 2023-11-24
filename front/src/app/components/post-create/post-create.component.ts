import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PostInterface} from "../../interfaces/post.interface";
import {Router} from "@angular/router";
import {Location} from '@angular/common';
import {MatSnackBar} from "@angular/material/snack-bar";
import {take} from "rxjs";
import { SubjectInterface } from 'src/app/interfaces/subject.interface';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { PostsService } from 'src/app/services/posts.service';
import { SubjectsService } from 'src/app/services/subjects.service';
@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.scss']
})
export class PostCreateComponent implements OnInit {
  subjects!: SubjectInterface[];
  public onError = false;
  public form!: FormGroup;
  private REGEX_ID: RegExp = /^[1-9][0-9]*$/;


  constructor(
    private authService: AuthService,
    private sessionService: SessionService,
    private subjectsService: SubjectsService,
    private postsService: PostsService,
    private fb: FormBuilder,
    private router: Router,
    private matSnackBar: MatSnackBar,
    private location: Location
  ) {
  }

  showNotification(msg: string, duration: number) {
    this.matSnackBar.open(msg, '', {
      duration: duration,
      panelClass: ['multiline-snackbar'],
    });
  }

  public submit(): void {
    const postRequest = this.form.value as PostInterface;
    postRequest.authorId = <number>this.sessionService.authUser?.id;
    postRequest.authorFirstName = <string>this.sessionService.authUser?.firstName;
    this.postsService
      .createPost(postRequest)
      .pipe(take(1))
      .subscribe({
        next: (value: PostInterface) => {
          const msg = 'Your article ' + value.title + ' is created!';
          this.showNotification(msg, 2000);
          this.router.navigate(['posts']);
        },
        error: () => {
          this.showNotification('Server error, please try again', 2000);
        },
      });
  }

  ngOnInit(): void {
    this.initForm();
    this.subjectsService.getAllSubjects()
      .pipe(take(1))
      .subscribe({
          next: (value: SubjectInterface[]) => {
            this.subjects = value;
          },
          error: () => {
            this.showNotification('Subject list is empty', 2000);
          }
        }
      );
  }

  private initForm(): void {
    this.form = this.fb.group({
      subjectId: [
        0,
        [Validators.required, Validators.pattern(this.REGEX_ID)],
      ],
      title: ['', [Validators.required, Validators.min(5), Validators.max(100)]],
      description: [
        '',
        [Validators.required, Validators.min(20)],
      ],
    });
  }

  return() {
    this.location.back();
  }
}
