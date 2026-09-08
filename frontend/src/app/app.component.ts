import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'rr-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <div class="rr-shell">
      <header class="rr-card">
        <h1>RoleReady</h1>
        <p>How well does your resume fit this job — and how to make it better.</p>
        <nav class="rr-nav">
          <a routerLink="/">Home</a>
          <a routerLink="/resume">Resume</a>
          <a routerLink="/job">Job</a>
          <a routerLink="/analysis">Analysis</a>
          <a routerLink="/questions">Questions</a>
          <a routerLink="/optimize">Optimize</a>
          <a routerLink="/export">Export</a>
          <a routerLink="/account">Account</a>
        </nav>
      </header>
      <main style="margin-top:16px"><router-outlet /></main>
    </div>
  `
})
export class AppComponent {}
