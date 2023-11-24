import {Component, OnInit} from '@angular/core';
import {PostsService} from "../../services/posts.service";
import {Router} from "@angular/router";
import {PostInterface} from "../../interfaces/post.interface";
import {MatSnackBar} from "@angular/material/snack-bar";
import {take} from "rxjs";

@Component({
  selector: 'app-posts-list',
  templateUrl: './posts-list.component.html',
  styleUrls: ['./posts-list.component.scss']
})
export class PostsListComponent implements OnInit {
  posts!: PostInterface[];
  sortDirection: 'ascending' | 'descending' = 'ascending';

  constructor(
    private postsService: PostsService,
    private router: Router,
    private matSnackBar: MatSnackBar
  ) {
  }

  ngOnInit(): void {
    this.postsService.getAllPostsByUser()
      .pipe(take(1))
      .subscribe({
        next: (value: PostInterface[]) => {
          this.posts = value;
        },
        error: () => this.matSnackBar.open('Aucun aticle trouvé', '', {
          duration: 3000,
        })
      });
  }


  orderByDate() {
    this.sortDirection =
      this.sortDirection === 'ascending' ? 'descending' : 'ascending';
    this.posts = this.posts.reverse();
  }

  newArticle() {
    this.router.navigateByUrl(`posts/create`);
  }
}
