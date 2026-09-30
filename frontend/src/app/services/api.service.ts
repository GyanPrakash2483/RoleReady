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
  verifyEmail(token: string) { return this.http.post(`${this.base}/api/auth/verify-email`, { token }); }
  forgotPassword(email: string) { return this.http.post(`${this.base}/api/auth/forgot-password`, { email }); }
  resetPassword(token: string, password: string) { return this.http.post(`${this.base}/api/auth/reset-password`, { token, password }); }
  changePassword(currentPassword: string, newPassword: string) { return this.http.post(`${this.base}/api/auth/change-password`, { currentPassword, newPassword }); }
  deleteAccount() { return this.http.delete(`${this.base}/api/auth/account`); }
  uploadResume(file: File) {
    const form = new FormData();
    form.append('file', file);
    return this.http.post(`${this.base}/api/resume/upload`, form);
  }
  createResume(body: unknown) { return this.http.post(`${this.base}/api/resume/create`, body); }
  updateResume(body: unknown) { return this.http.put(`${this.base}/api/resume/current`, body); }
  analyzeJd(text: string) {
    return this.http.post(`${this.base}/api/job-description/analyze`, { text });
  }
  analyze(body: unknown) {
    return this.http.post(`${this.base}/api/analysis`, body);
  }
  optimize(body: unknown) {
    return this.http.post(`${this.base}/api/optimization`, body);
  }
  analyzeResume(body: unknown) { return this.http.post(`${this.base}/api/analysis`, body); }
  createSession() { return this.http.post(`${this.base}/api/session`, {}); }
  generateQuestions(analysis: unknown) { return this.http.post(`${this.base}/api/questions`, { analysis }); }
  answerQuestion(id: string, answer: string) { return this.http.post(`${this.base}/api/questions/${id}/answer`, { answer }); }
  generateSuggestions(body: unknown) { return this.http.post(`${this.base}/api/suggestions`, body); }
  reviewChange(id: string, decision: string) { return this.http.put(`${this.base}/api/optimization/changes/${id}`, { decision }); }
  exportMarkdown(body: unknown) { return this.http.post(`${this.base}/api/export/markdown`, body); }
  exportLatex(body: unknown) { return this.http.post(`${this.base}/api/export/latex`, body); }
  exportPdf(body: unknown) { return this.http.post(`${this.base}/api/export/pdf`, body, { responseType: 'blob' }); }
}
