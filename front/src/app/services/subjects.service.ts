import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SubjectInterface} from "../interfaces/subject.interface";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubjectsService {
  private apiUrl: string = environment.baseUrl;
  private pathService = this.apiUrl+'/subjects';
  
  constructor(private httpClient: HttpClient) {
  }

  public getAllSubjects(): Observable<SubjectInterface[]> {
    return this.httpClient.get<SubjectInterface[]>(`${this.pathService}`);
  }
}
