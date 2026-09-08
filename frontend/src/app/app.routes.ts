import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', loadComponent: () => import('./pages/landing.component').then((m) => m.LandingComponent) },
  { path: 'resume', loadComponent: () => import('./pages/resume.component').then((m) => m.ResumeComponent) },
  { path: 'job', loadComponent: () => import('./pages/job.component').then((m) => m.JobComponent) },
  { path: 'analysis', loadComponent: () => import('./pages/analysis.component').then((m) => m.AnalysisComponent) },
  { path: 'questions', loadComponent: () => import('./pages/questions.component').then((m) => m.QuestionsComponent) },
  { path: 'optimize', loadComponent: () => import('./pages/optimization.component').then((m) => m.OptimizationComponent) },
  { path: 'export', loadComponent: () => import('./pages/export.component').then((m) => m.ExportComponent) },
  { path: 'account', loadComponent: () => import('./pages/account.component').then((m) => m.AccountComponent) },
  { path: '**', redirectTo: '' }
];
