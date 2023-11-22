import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PostInterface} from "../interfaces/post.interface";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostsService {
  private apiUrl: string = environment.baseUrl;
  private pathService = this.apiUrl+'/posts';
  constructor(private httpClient: HttpClient) {
  }

  public getAllPostsByUser(): Observable<PostInterface[]> {
    return this.httpClient.get<PostInterface[]>(`${this.pathService}`);
  }

  public createPost(post: PostInterface): Observable<PostInterface> {
    return this.httpClient.post<PostInterface>(`${this.pathService}/new`, post);
  }

  public findById(id: number): Observable<PostInterface> {
    return this.httpClient.get<PostInterface>(`${this.pathService}/${id}`);
  }
}
