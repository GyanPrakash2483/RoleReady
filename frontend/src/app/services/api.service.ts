import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private http = inject(HttpClient);
  private base = environment.apiBaseUrl;

  register(email: string, password: string) {
    return this.http.post(`${this.base}/api/auth/register`, { email, password });
  }
  login(email: string, password: string) {
    return this.http.post(`${this.base}/api/auth/login`, { email, password });
  }
  uploadResume(file: File) {
    const form = new FormData();
    form.append('file', file);
    return this.http.post(`${this.base}/api/resume/upload`, form);
  }
  analyzeJd(text: string) {
    return this.http.post(`${this.base}/api/job-description/analyze`, { text });
  }
  analyze(body: unknown) {
    return this.http.post(`${this.base}/api/analysis`, body);
  }
  optimize(body: unknown) {
    return this.http.post(`${this.base}/api/optimization`, body);
  }
}
