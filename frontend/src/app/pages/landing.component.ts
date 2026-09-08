import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  imports: [RouterLink],
  template: `
    <section class="rr-card">
      <h2>Paste resume. Paste job. Get your Role Readiness score.</h2>
      <p>Analysis, gaps, questions, and an optimized resume — 3 free uses, no account needed.</p>
      <button class="rr-btn" routerLink="/resume">Start free analysis</button>
    </section>
  `
})
export class LandingComponent {}
