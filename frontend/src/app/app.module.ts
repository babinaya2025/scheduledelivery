// src/app/app.module.ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // <-- Ensure ReactiveFormsModule is here
import { HttpClientModule } from '@angular/common/http'; // <-- Ensure HttpClientModule is here
//import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
// Import your components from their new location
import { SchedulingComponent } from './components/scheduling/scheduling.component';
import { SupportDeliveryComponent } from './components/support-delivery/support-delivery.component';
import { AttendanceTrackingComponent } from './components/attendance-tracking/attendance-tracking.component';
import { AssessmentComponent } from './components/assessment/assessment.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';


@NgModule({
  declarations: [
    AppComponent,
    SchedulingComponent, // If SchedulingComponent is NOT standalone, it should be declared here
    SupportDeliveryComponent,
    AttendanceTrackingComponent,
    AssessmentComponent,
    FeedbackComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule, // <-- Ensure ReactiveFormsModule is imported
    HttpClientModule,    // <-- Ensure HttpClientModule is imported
  //  AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
