import { Component } from '@angular/core';

@Component({
  standalone: true,
  template: `
    <section class="rr-card">
      <h2>Export</h2>
      <button class="rr-btn">Download PDF</button>
      <p>Markdown / LaTeX buttons appear when that was your input format.</p>
    </section>
  `
})
export class ExportComponent {}
