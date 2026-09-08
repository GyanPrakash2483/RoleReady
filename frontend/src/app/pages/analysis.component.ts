import { Component } from '@angular/core';

@Component({
  standalone: true,
  template: `
    <section class="rr-card">
      <h2>Analysis dashboard</h2>
      <p>Role Readiness: <strong>–/100</strong> (wired to POST /api/analysis).</p>
      <ul>
        <li>Required skills / experience / responsibilities / keywords / ATS …</li>
        <li>Strong · Partial · Weak · Missing · Unclear + evidence</li>
      </ul>
    </section>
  `
})
export class AnalysisComponent {}
