import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { environment } from '../environments/environment';

@Component({
  standalone: true,
  imports: [RouterLink],
  template: `
    <section class="rr-card">
      <h2>Paste resume. Paste job. Get your Role Readiness score.</h2>
      <p>Analysis, gaps, questions, and an optimized resume — 3 free uses, no account needed.</p>
      <button class="rr-btn" routerLink="/resume">Start free analysis</button>
      <p class="rr-status" aria-live="polite">
        Backend status: <strong>{{ healthStatus }}</strong>
      </p>
    </section>
  `
})
export class LandingComponent {
  private http = inject(HttpClient);
  healthStatus = 'checking…';

  constructor() {
    this.http.get<{ status: string }>(`${environment.apiBaseUrl}/api/health`).subscribe({
      next: (response) => this.healthStatus = response.status,
      error: () => this.healthStatus = 'unavailable'
    });
  }
}
