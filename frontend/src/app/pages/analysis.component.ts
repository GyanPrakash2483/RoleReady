import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { JsonPipe } from '@angular/common';
import { ApiService } from '../services/api.service';

@Component({
  standalone:true,
  imports:[RouterLink,JsonPipe],
  template:`
    <section class="rr-card">
      <h2>Analysis dashboard</h2>
      <button class="rr-btn" (click)="run()">Run analysis</button>
      @if (loading) { <p>Analyzing…</p> }
      @if (error) { <p>{{error}}</p> }
      @if (result) {
        <h1>{{result.roleReadiness}} / 100</h1>
        <h3>Category scores</h3>
        @for (item of categoryEntries; track item[0]) {
          <p><strong>{{item[0]}}</strong>: {{item[1]}}</p>
        }
        <h3>Matches and gaps</h3>
        @for (e of result.evidence ?? []; track e.requirement) {
          <article class="rr-card">
            <strong>{{e.classification}}:</strong> {{e.requirement}}
            <p>{{e.evidence}}</p>
          </article>
        }
        <h3>Score explanations</h3>
        <pre>{{result.explanations | json}}</pre>
        <button class="rr-btn" (click)="generateSuggestions()">Generate suggestions</button>
      }
      @if (suggestions.length) {
        <h3>Suggestions</h3>
        @for (s of suggestions; track s.id) {
          <article class="rr-card"><strong>{{s.category}}</strong><p>{{s.rationale}}</p><p>{{s.action}}</p></article>
        }
      }
      @if (guestLimitReached) {
        <div class="rr-card"><h3>Free guest limit reached</h3><p>Sign in to continue.</p><a class="rr-btn" routerLink="/account">Sign in / Register</a></div>
      }
    </section>
  `,
  providers: []
})
export class AnalysisComponent {
  private api=inject(ApiService);
  loading=false; error=''; result:any=null; suggestions:any[]=[]; guestLimitReached=false;
  get categoryEntries(){return Object.entries(this.result?.categoryScores ?? {});}
  run(){
    this.loading=true; this.error='';
    const resume=JSON.parse(localStorage.getItem('rr_resume')||'{}');
    const jd=JSON.parse(localStorage.getItem('rr_jd')||'{}');
    this.api.analyzeResume({resume,jd,resumeText:JSON.stringify(resume)}).subscribe({
      next:(r:any)=>{this.result=r.data??r;this.loading=false;localStorage.setItem('rr_analysis',JSON.stringify(this.result));},
      error:(e:any)=>{this.loading=false;if(e.status===403)this.guestLimitReached=true;else this.error=e?.error?.message??'Analysis failed.';}
    });
  }
  generateSuggestions(){
    this.api.generateSuggestions({resume:JSON.parse(localStorage.getItem('rr_resume')||'{}'),jd:JSON.parse(localStorage.getItem('rr_jd')||'{}'),analysis:this.result})
      .subscribe({next:(r:any)=>this.suggestions=r.data?.suggestions??r.suggestions??[]});
  }
}
