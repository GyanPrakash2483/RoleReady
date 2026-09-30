import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  standalone:true,
  imports:[FormsModule],
  template:`
    <section class="rr-card">
      <h2>Account</h2>
      <input [(ngModel)]="email" placeholder="email" />
      <input [(ngModel)]="password" type="password" placeholder="password" />
      <button class="rr-btn" (click)="login()">Login</button>
      <button class="rr-btn" (click)="register()">Register</button>
      <button class="rr-btn" (click)="google()">Continue with Google</button>
      <p>{{status}}</p>

      <h3>Verify email</h3>
      <input [(ngModel)]="verifyToken" placeholder="verification token" />
      <button class="rr-btn" (click)="verify()">Verify</button>

      <h3>Forgot password</h3>
      <button class="rr-btn" (click)="forgot()">Send reset email</button>

      <h3>Reset password</h3>
      <input [(ngModel)]="resetToken" placeholder="reset token" />
      <input [(ngModel)]="newPassword" type="password" placeholder="new password" />
      <button class="rr-btn" (click)="reset()">Reset</button>

      <h3>Change password</h3>
      <input [(ngModel)]="currentPassword" type="password" placeholder="current password" />
      <button class="rr-btn" (click)="change()">Change password</button>

      <button class="rr-btn" (click)="deleteAccount()">Delete account</button>
    </section>
  `
})
export class AccountComponent {
  private api=inject(ApiService);
  email=''; password=''; status=''; verifyToken=''; resetToken=''; newPassword=''; currentPassword='';

  login(){this.api.login(this.email,this.password).subscribe({next:(r:any)=>{localStorage.setItem('rr_token',r.data?.token??'');this.status='Logged in.'},error:()=>this.status='Login failed.'});}
  register(){this.api.register(this.email,this.password).subscribe({next:(r:any)=>{localStorage.setItem('rr_token',r.data?.token??'');this.status='Registered. Check your email.'},error:()=>this.status='Registration failed.'});}
  google(){window.location.href='http://localhost:8080/oauth2/authorization/google';}
  verify(){this.api.verifyEmail(this.verifyToken).subscribe({next:()=>this.status='Email verified.',error:()=>this.status='Verification failed.'});}
  forgot(){this.api.forgotPassword(this.email).subscribe({next:()=>this.status='If the account exists, a reset email was sent.',error:()=>this.status='Unable to request reset.'});}
  reset(){this.api.resetPassword(this.resetToken,this.newPassword).subscribe({next:()=>this.status='Password reset.',error:()=>this.status='Reset failed.'});}
  change(){this.api.changePassword(this.currentPassword,this.newPassword).subscribe({next:()=>this.status='Password changed.',error:()=>this.status='Change failed.'});}
  deleteAccount(){this.api.deleteAccount().subscribe({next:()=>{localStorage.removeItem('rr_token');this.status='Account deleted.'},error:()=>this.status='Delete failed.'});}
}
