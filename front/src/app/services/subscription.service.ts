import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { SubscriptionInterface } from '../interfaces/subscription.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl: string = environment.baseUrl;

  private pathService = this.apiUrl+'/subscriptions';
  constructor(private httpClient: HttpClient) {
  }

  public subscribeUser(subscription: SubscriptionInterface): Observable<SubscriptionInterface> {
    return this.httpClient.post<SubscriptionInterface>(`${this.pathService}`, subscription);
  }

  public unSubscribeUser(subscription: SubscriptionInterface): Observable<SubscriptionInterface> {
    return this.httpClient.post<SubscriptionInterface>(`${this.pathService}/unsubscriptions`, subscription);
  }
}
