import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  standalone:true,
  imports:[FormsModule],
  template:`
    <section class="rr-card">
      <h2>Clarification questions</h2>
      <button class="rr-btn" (click)="generate()">Generate questions</button>
      @for (q of questions; track q.id) {
        <article class="rr-card">
          <h3>{{q.question}}</h3>
          <p>{{q.reason}}</p>
          <textarea [(ngModel)]="answers[q.id]" rows="3" placeholder="Answer with facts you can confirm"></textarea>
          <button class="rr-btn" (click)="answer(q.id)">Confirm answer</button>
          @if (confirmed[q.id]) { <p>Confirmed and available to optimization.</p> }
        </article>
      }
    </section>
  `
})
export class QuestionsComponent {
  private api=inject(ApiService);
  questions:any[]=[]; answers:Record<string,string>={}; confirmed:Record<string,boolean>={};
  generate(){
    const analysis=JSON.parse(localStorage.getItem('rr_analysis')||'{}');
    this.api.generateQuestions(analysis).subscribe({next:(r:any)=>this.questions=r.data?.questions??[]});
  }
  answer(id:string){
    this.api.answerQuestion(id,this.answers[id]||'').subscribe({next:()=>this.confirmed[id]=true});
  }
}
