// src/app/components/dashboard/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router'; // <-- Add this import

@Component({
  selector: 'app-dashboard',
  imports:[CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  modules = [
    { name: 'Scheduling', icon: 'calendar-alt', class: 'scheduling', overview: 'Manage and track all scheduled events, resources, and trainer assignments.', buttonLabel: 'View Schedules', route: '/scheduling' },
    { name: 'Support & Delivery', icon: 'life-ring', class: 'support-delivery', overview: 'Oversee the delivery of learning materials and provide necessary support.', buttonLabel: 'View Deliveries', route: '/support-delivery' },
    { name: 'Attendance Tracking', icon: 'user-check', class: 'attendance-tracking', overview: 'Monitor and record learner attendance for all sessions and courses.', buttonLabel: 'Track Attendance', route: '/attendance-tracking' },
    { name: 'Assessment', icon: 'clipboard-list', class: 'assessment', overview: 'Create, assign, and review assessments to gauge learner progress.', buttonLabel: 'Manage Assessments', route: '/assessment' },
    { name: 'Feedback', icon: 'comments', class: 'feedback', overview: 'Collect and analyze feedback from learners and trainers for continuous improvement.', buttonLabel: 'Review Feedback', route: '/feedback' }
  ];

  constructor(private router: Router) { } // <-- Inject Router

  ngOnInit(): void {
  }

  openModulePage(route: string): void {
    // ✅ Changed from window.open to router.navigate
    this.router.navigate([route]);
  }

  getCardColorClass(moduleName: string): string {
    const module = this.modules.find(m => m.name === moduleName);
    return module ? module.class : '';
  }
}