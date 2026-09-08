import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
    <section class="rr-card">
      <h2>Account</h2>
      <input [(ngModel)]="email" placeholder="email" />
      <input [(ngModel)]="password" type="password" placeholder="password" />
      <button class="rr-btn" (click)="login()">Login</button>
      <button class="rr-btn" (click)="register()">Register</button>
      <p>{{ status }}</p>
      <p>Google OAuth + verify-email + reset-password plug in here (FR-AUTH-002/004/005).</p>
    </section>
  `
})
export class AccountComponent {
  private api = inject(ApiService);
  email = '';
  password = '';
  status = '';
  login() {
    this.api.login(this.email, this.password).subscribe({
      next: (r: any) => { localStorage.setItem('rr_token', r.data?.token ?? ''); this.status = 'Logged in.'; },
      error: () => (this.status = 'Login failed.')
    });
  }
  register() {
    this.api.register(this.email, this.password).subscribe({
      next: (r: any) => { localStorage.setItem('rr_token', r.data?.token ?? ''); this.status = 'Registered.'; },
      error: () => (this.status = 'Registration failed.')
    });
  }
}
