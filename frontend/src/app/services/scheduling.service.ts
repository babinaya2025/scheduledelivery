// src/app/services/scheduling.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { delay, tap, catchError } from 'rxjs/operators';
import { Course, Participant, Trainer, ScheduleStatus, ConflictCheckResult, ScheduleRequest } from '../shared/models';

@Injectable({
  providedIn: 'root'
})
export class SchedulingService {
  private apiUrl = 'http://localhost:8080/api'; // Your Spring Boot backend URL

  constructor(private http: HttpClient) { }

  // --- API Calls ---

  // GET /api/courses (returns CourseDTOs for Events)
  getCourses(): Observable<Course[]> {
    console.log('API: Fetching courses from backend...');
    return this.http.get<Course[]>(`${this.apiUrl}/courses`).pipe(
      catchError(this.handleError)
    );
  }

  // GET /api/trainers (returns TrainerDTOs)
  getTrainers(): Observable<Trainer[]> {
    console.log('API: Fetching trainers from backend...');
    return this.http.get<Trainer[]>(`${this.apiUrl}/trainers`).pipe(
      catchError(this.handleError)
    );
  }

  // GET /api/participants/approved/{courseId} (returns ParticipantDTOs for Users)
  getApprovedParticipants(courseId: string): Observable<Participant[]> {
    console.log(`API: Fetching approved participants for course ${courseId} from backend...`);
    return this.http.get<Participant[]>(`${this.apiUrl}/participants/approved/${courseId}`).pipe(
      catchError(this.handleError)
    );
  }

  // POST /api/schedules/check-conflicts
  checkConflicts(request: ScheduleRequest): Observable<ConflictCheckResult> {
    console.log('API: Checking conflicts with backend...', request);
    return this.http.post<ConflictCheckResult>(`${this.apiUrl}/schedules/check-conflicts`, request).pipe(
      catchError(this.handleError)
    );
  }

  // PUT /api/schedules/lock/{courseId}
  lockSchedule(courseId: string, adminId: string, request: ScheduleRequest): Observable<ScheduleStatus> {
    console.log(`API: Locking schedule for course ${courseId} with backend...`);
    return this.http.put<ScheduleStatus>(`${this.apiUrl}/schedules/lock/${courseId}?adminId=${adminId}`, request).pipe(
      catchError(this.handleError)
    );
  }

  // PUT /api/schedules/unlock/{courseId}
  unlockSchedule(courseId: string, adminId: string): Observable<ScheduleStatus> {
    console.log(`API: Unlocking schedule for course ${courseId} with backend...`);
    return this.http.put<ScheduleStatus>(`${this.apiUrl}/schedules/unlock/${courseId}?adminId=${adminId}`, {}).pipe(
      catchError(this.handleError)
    );
  }

  // GET /api/schedules/status/{courseId}
  getScheduleStatus(courseId: string): Observable<ScheduleStatus> {
    console.log(`API: Getting schedule status for course ${courseId} from backend...`);
    return this.http.get<ScheduleStatus>(`${this.apiUrl}/schedules/status/${courseId}`).pipe(
      catchError(this.handleError)
    );
  }

  // POST /api/schedules/{courseId}/send-invite
  sendCalendarInvite(courseId: string): Observable<{ message: string }> {
    console.log(`API: Sending calendar invite for course ${courseId} via backend...`);
    return this.http.post<{ message: string }>(`${this.apiUrl}/schedules/${courseId}/send-invite`, {}).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // Client-side errors
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.error && error.error.message) {
      // Backend errors with a message property
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.status) {
      // Backend errors without a specific message
      errorMessage = `Server returned code ${error.status}: ${error.message}`;
    }
    console.error('HTTP Error:', error);
    return throwError(() => new Error(errorMessage));
  }
}
