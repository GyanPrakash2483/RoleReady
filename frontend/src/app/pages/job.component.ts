import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
    <section class="rr-card">
      <h2>Job description</h2>
      <textarea [(ngModel)]="text" rows="10" cols="60" placeholder="Paste job description text…"></textarea>
      <br /><br />
      <button class="rr-btn" (click)="analyze()">Analyze JD</button>
      <p>{{ status }}</p>
    </section>
  `
})
export class JobComponent {
  private api = inject(ApiService);
  text = '';
  status = '';
  analyze() {
    if (!this.text.trim()) { this.status = 'Paste a job description first.'; return; }
    this.status = 'Analyzing…';
    this.api.analyzeJd(this.text).subscribe({
      next: (r: any) => { localStorage.setItem('rr_jd', JSON.stringify(r.data ?? r)); this.status = 'JD analyzed.'; },
      error: () => (this.status = 'JD analysis failed.')
    });
  }
}
