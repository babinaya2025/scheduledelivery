import { Injectable } from '@angular/core';
import {Observable, of, throwError} from 'rxjs';
import {catchError, delay} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Assessment, Participant} from '../shared/models';



@Injectable({
  providedIn: 'root'
})
export class AssessmentService {
  private apiUrl = 'http://localhost:8081/api'; // Your Spring Boot backend URL

  constructor(private http: HttpClient) { }

  // --- API Calls ---

  // GET /api/assessment (returns assessmnetRequestDTOs for Events)
 // getAssessments(): Observable<Assessment[]> {
  getAssessments(): Observable<Assessment[]> {
    return this.http.get<Assessment[]>(`${this.apiUrl}/assessment`).pipe(
      catchError(this.handleError)
    );
  }

  getAssessmentById(id: number): Observable<Assessment | undefined> {
    // @ts-ignore
    return this.http.get<Assessment[]>(`${this.apiUrl}/asseessment`).pipe(
      catchError(this.handleError));
  }

  addAssessment(assessment: Omit<Assessment, 'id'>): Observable<Assessment> {
    // @ts-ignore
    return this.http.get<Assessment[]>(`${this.apiUrl}/asseessment`).pipe(
      catchError(this.handleError));
  }

  updateAssessment(updatedAssessment: Assessment): Observable<Assessment> {
    // @ts-ignore
    return this.http.get<Assessment[]>(`${this.apiUrl}/asseessment`).pipe(
      catchError(this.handleError));
    }

  deleteAssessment(id: number): Observable<boolean> {
  // @ts-ignore
    return this.http.get<Assessment[]>(`${this.apiUrl}/asseessment`).pipe(
    catchError(this.handleError));
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
