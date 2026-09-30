import { Component, inject } from '@angular/core';
import { ApiService } from '../services/api.service';

@Component({
  standalone:true,
  template:`
    <section class="rr-card">
      <h2>Export</h2>
      <button class="rr-btn" (click)="exportMarkdown()">Markdown</button>
      <button class="rr-btn" (click)="exportLatex()">LaTeX</button>
      <button class="rr-btn" (click)="exportPdf()">PDF</button>
      <p>{{status}}</p>
      @if (pdfUrl) { <iframe [src]="pdfUrl" title="PDF preview" style="width:100%;height:700px;border:2px solid var(--ink)"></iframe> }
      @if (content) { <pre>{{content}}</pre> }
    </section>
  `
})
export class ExportComponent {
  private api=inject(ApiService); content=''; status=''; pdfUrl:any=null;
  private resume(){return JSON.parse(localStorage.getItem('rr_resume')||'{}');}
  exportMarkdown(){this.api.exportMarkdown(this.resume()).subscribe({next:(r:any)=>{this.content=r.data?.markdown??'';this.status='Markdown generated.'}});}
  exportLatex(){this.api.exportLatex(this.resume()).subscribe({next:(r:any)=>{this.content=r.data?.latex??'';this.status='LaTeX generated.'}});}
  exportPdf(){this.api.exportPdf(this.resume()).subscribe({next:(blob:any)=>{const url=URL.createObjectURL(blob);this.pdfUrl=url;const a=document.createElement('a');a.href=url;a.download='roleready-resume.pdf';a.click();this.status='PDF generated and previewed.'}});}
}
