import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SubjectInterface } from 'src/app/interfaces/subject.interface';

@Component({
  selector: 'subject-card-list',
  templateUrl: './subject-card.component.html',
  styleUrls: ['./subject-card.component.scss']
})
export class SubjectCardComponent {
  @Input() isDesktop!: boolean;
  @Input() subject!: SubjectInterface;
  @Output() subscribe = new EventEmitter<SubjectInterface>();

  constructor() {
  }

  public subscription(subject: SubjectInterface) {
    this.subscribe.emit(this.subject);
  }

}
