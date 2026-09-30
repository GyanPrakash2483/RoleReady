import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  standalone:true,
  imports:[FormsModule],
  template:`
    <section class="rr-card">
      <h2>Resume workspace</h2>
      <input type="file" (change)="onFile($event)" accept=".pdf,.docx,.txt,.md,.tex" />
      <p>{{status}}</p>

      <h3>Personal information</h3>
      <input [(ngModel)]="resume.personal.name" placeholder="Name" />
      <input [(ngModel)]="resume.personal.email" placeholder="Email" />
      <input [(ngModel)]="resume.personal.phone" placeholder="Phone" />
      <input [(ngModel)]="resume.personal.location" placeholder="Location" />
      <input [(ngModel)]="resume.personal.linkedin" placeholder="LinkedIn" />

      <h3>Summary</h3>
      <textarea [(ngModel)]="resume.summary" rows="5"></textarea>

      <h3>Experience</h3>
      <textarea [(ngModel)]="resume.experience" rows="7"></textarea>

      <h3>Education</h3>
      <textarea [(ngModel)]="resume.education" rows="5"></textarea>

      <h3>Skills</h3>
      <textarea [(ngModel)]="resume.skills" rows="4"></textarea>

      <h3>Projects</h3>
      <textarea [(ngModel)]="resume.projects" rows="5"></textarea>

      <h3>Achievements</h3>
      <textarea [(ngModel)]="resume.achievements" rows="4"></textarea>

      <h3>Certifications</h3>
      <textarea [(ngModel)]="resume.certifications" rows="4"></textarea>

      <h3>Custom sections</h3>
      <textarea [(ngModel)]="resume.custom" rows="4"></textarea>

      <p>
        <button class="rr-btn" (click)="save()">Save resume</button>
        <button class="rr-btn" (click)="previewing=!previewing">Preview</button>
      </p>

      @if (previewing) {
        <article class="rr-card">
          <h2>{{resume.personal.name || 'Your Name'}}</h2>
          <p>{{resume.personal.email}} {{resume.personal.phone}} {{resume.personal.location}}</p>
          <h3>Summary</h3><p>{{resume.summary}}</p>
          <h3>Experience</h3><pre>{{resume.experience}}</pre>
          <h3>Education</h3><pre>{{resume.education}}</pre>
          <h3>Skills</h3><pre>{{resume.skills}}</pre>
          <h3>Projects</h3><pre>{{resume.projects}}</pre>
          <h3>Achievements</h3><pre>{{resume.achievements}}</pre>
          <h3>Certifications</h3><pre>{{resume.certifications}}</pre>
          <h3>Custom</h3><pre>{{resume.custom}}</pre>
        </article>
      }
    </section>
  `
})
export class ResumeComponent {
  private api=inject(ApiService);
  status='';
  previewing=false;
  resume:any={
    personal:{name:'',email:'',phone:'',location:'',linkedin:''},
    summary:'',experience:'',education:'',skills:'',projects:'',
    achievements:'',certifications:'',custom:''
  };

  onFile(e:Event){
    const file=(e.target as HTMLInputElement).files?.[0];
    if(!file)return;
    this.status='Uploading and parsing…';
    this.api.uploadResume(file).subscribe({
      next:(r:any)=>{
        const d=r.data??r;
        this.resume.summary=d.sections?.summary??this.resume.summary;
        this.resume.experience=d.sections?.experience??this.resume.experience;
        this.resume.education=d.sections?.education??this.resume.education;
        this.resume.skills=d.sections?.skills??this.resume.skills;
        this.resume.projects=d.sections?.projects??this.resume.projects;
        this.resume.achievements=d.sections?.achievements??this.resume.achievements;
        this.resume.certifications=d.sections?.certifications??this.resume.certifications;
        this.status='Imported. You can edit every section below.';
      },
      error:(e:any)=>this.status=e?.error?.message??'Upload failed.'
    });
  }

  save(){
    this.api.updateResume(this.resume).subscribe({
      next:()=>this.status='Resume saved for this session.',
      error:()=>this.status='Unable to save resume.'
    });
  }
}
