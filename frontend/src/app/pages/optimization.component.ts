import { Component, inject } from '@angular/core';
import { ApiService } from '../services/api.service';

@Component({
  standalone:true,
  template:`
    <section class="rr-card">
      <h2>Optimization workspace</h2>
      <button class="rr-btn" (click)="optimize()">Generate optimized changes</button>
      @for (c of changes; track c.id) {
        <article class="rr-card">
          <h3>{{c.section}}</h3>
          <p><strong>Before:</strong> {{c.before}}</p>
          <p><strong>After:</strong> {{c.after}}</p>
          <p>{{c.rationale}} · {{c.provenance}}</p>
          <button class="rr-btn" (click)="review(c.id,'accept')">Accept</button>
          <button class="rr-btn" (click)="review(c.id,'reject')">Reject</button>
          <span>{{c.decision}}</span>
        </article>
      }
    </section>
  `
})
export class OptimizationComponent {
  private api=inject(ApiService); changes:any[]=[];
  optimize(){
    this.api.optimize({
      resume:JSON.parse(localStorage.getItem('rr_resume')||'{}'),
      jd:JSON.parse(localStorage.getItem('rr_jd')||'{}'),
      answers:{}
    }).subscribe({next:(r:any)=>this.changes=r.data?.changes??[]});
  }
  review(id:string,decision:string){
    this.api.reviewChange(id,decision).subscribe({next:(r:any)=>{
      const c=this.changes.find(x=>x.id===id); if(c)c.decision=r.data?.decision??decision;
    }});
  }
}
