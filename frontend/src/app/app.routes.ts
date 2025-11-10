// src/app/app.routes.ts
import { Routes } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SchedulingComponent } from './components/scheduling/scheduling.component'; // <-- Import standalone component
import { SupportDeliveryComponent } from './components/support-delivery/support-delivery.component';
import { AttendanceTrackingComponent } from './components/attendance-tracking/attendance-tracking.component';
import { AssessmentComponent } from './components/assessment/assessment.component';
import { FeedbackComponent } from './components/feedback/feedback.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'scheduling', component: SchedulingComponent },
  { path: 'support-delivery', component: SupportDeliveryComponent },
  { path: 'attendance-tracking', component: AttendanceTrackingComponent },
  { path: 'assessment', component: AssessmentComponent },
  { path: 'feedback', component: FeedbackComponent },
  { path: '**', redirectTo: '' }
];
