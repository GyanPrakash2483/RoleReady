import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  standalone:true,
  imports:[RouterLink],
  template:`
    <section class="rr-card">
      <h2>Analysis</h2>
      <p>Role Readiness analysis results will appear here.</p>
      @if (guestLimitReached) {
        <div class="rr-card">
          <h3>Free guest limit reached</h3>
          <p>Sign in to continue analyzing resumes and job descriptions.</p>
          <a class="rr-btn" routerLink="/account">Sign in / Register</a>
        </div>
      } @else {
        <p>Complete an analysis to see your score, matches, gaps, and evidence.</p>
      }
    </section>
  `
})
export class AnalysisComponent {
  guestLimitReached=false;
}
