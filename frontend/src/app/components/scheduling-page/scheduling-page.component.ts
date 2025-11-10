import { Component, OnInit } from '@angular/core';
import { SchedulingService } from '../../services/scheduling.service';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';


// Interfaces for data structure
interface Course {
  id: number;
  name: string;
}

interface Participant {
  id: number;
  name: string;
  poRef: string; // Participant data from the "PO Module"
}

@Component({
  selector: 'app-scheduling-page',
  imports:[RouterLink],
  templateUrl: './scheduling-page.component.html',
  styleUrls: ['./scheduling-page.component.css']
})
export class SchedulingPageComponent implements OnInit {

  courses: Course[] = [];
  selectedCourseId: number | null = null;
  participants: Participant[] = [];
  isLoadingCourses: boolean = false;
  isLoadingParticipants: boolean = false;
  errorMessage: string | null = null;

  constructor(private schedulingService: SchedulingService) { }

  ngOnInit(): void {
    this.fetchCourses();
  }

  // 1. Fetch available courses
  fetchCourses(): void {
    this.isLoadingCourses = true;
    this.schedulingService.getCourses().subscribe({
      next: (data) => {
        this.courses = data;
        this.isLoadingCourses = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load courses.';
        this.isLoadingCourses = false;
        console.error('Error fetching courses:', err);
      }
    });
  }

  // 2. Handle course selection and trigger participant fetch
  onCourseSelect(event: any): void {
    const courseId = event.target.value ? parseInt(event.target.value, 10) : null;
    this.selectedCourseId = courseId;
    this.participants = []; // Clear previous list

    if (this.selectedCourseId) {
      this.fetchParticipants(this.selectedCourseId);
    }
  }

  // 3. Fetch approved participants based on the selected course ID
  fetchParticipants(courseId: number): void {
    this.isLoadingParticipants = true;
    this.errorMessage = null;

    this.schedulingService.getApprovedParticipants(courseId).subscribe({
      next: (data) => {
        this.participants = data;
        this.isLoadingParticipants = false;
      },
      error: (err) => {
        this.errorMessage = `Failed to load participants for course ${courseId}.`;
        this.isLoadingParticipants = false;
        console.error('Error fetching participants:', err);
      }
    });
  }
}