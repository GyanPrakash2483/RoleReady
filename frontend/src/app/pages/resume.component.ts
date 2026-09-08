import { Component, inject } from '@angular/core';
import { ApiService } from '../services/api.service';

@Component({
  standalone: true,
  template: `
    <section class="rr-card">
      <h2>Resume workspace</h2>
      <p>Upload PDF / DOCX / TXT / MD / LaTeX, or create from scratch.</p>
      <input type="file" (change)="onFile($event)" accept=".pdf,.docx,.txt,.md,.tex" />
      <p>{{ status }}</p>
    </section>
  `
})
export class ResumeComponent {
  private api = inject(ApiService);
  status = '';
  onFile(e: Event) {
    const file = (e.target as HTMLInputElement).files?.[0];
    if (!file) return;
    this.status = 'Uploading…';
    this.api.uploadResume(file).subscribe({
      next: () => (this.status = 'Resume parsed.'),
      error: () => (this.status = 'Upload failed. Check file type/size.')
    });
  }
}
