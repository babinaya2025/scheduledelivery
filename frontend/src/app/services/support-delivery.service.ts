// src/app/services/support-delivery.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TraineeDTO {
  id: string;
  name: string;
  email: string;
}

export interface TopicDTO {
  topicId: number;
  name: string;
  covered: boolean;
}

export interface WeekDTO {
  weekId: number;
  weekNumber: number;
  driveLink: string;
  githubLink: string;
  assignmentLink: string;
  topics: TopicDTO[];
}

export interface CourseDetailDTO {
  id: string;
  name: string;
  status: string;
  trainerName: string;
  trainerEmail: string;
  trainerId: number;
  trainees: TraineeDTO[];
  weeks: WeekDTO[];
}

export interface UpdateLinksRequest {
  driveLink?: string;
  githubLink?: string;
  assignmentLink?: string;
}

@Injectable({
  providedIn: 'root'
})
export class SupportDeliveryService {
  private baseUrl = 'http://localhost:8080/api/support-delivery';

  constructor(private http: HttpClient) {}

  // Admin APIs
  getAllCoursesForAdmin(status: string = 'all'): Observable<CourseDetailDTO[]> {
    const params = new HttpParams().set('status', status);
    return this.http.get<CourseDetailDTO[]>(`${this.baseUrl}/admin/courses`, { params });
  }

  // Trainer APIs
  getCoursesForTrainer(trainerId: number, status: string = 'all'): Observable<CourseDetailDTO[]> {
    const params = new HttpParams()
      .set('trainerId', trainerId.toString())
      .set('status', status);
    return this.http.get<CourseDetailDTO[]>(`${this.baseUrl}/trainer/courses`, { params });
  }

  updateTopicStatus(topicId: number, covered: boolean): Observable<TopicDTO> {
    const params = new HttpParams().set('covered', covered.toString());
    return this.http.put<TopicDTO>(`${this.baseUrl}/trainer/topics/${topicId}/status`, null, { params });
  }

  updateWeekLinks(weekId: number, request: UpdateLinksRequest): Observable<WeekDTO> {
    return this.http.put<WeekDTO>(`${this.baseUrl}/trainer/weeks/${weekId}/links`, request);
  }

  // Trainee APIs
  getCoursesForTrainee(traineeId: number, status: string = 'all'): Observable<CourseDetailDTO[]> {
    const params = new HttpParams()
      .set('traineeId', traineeId.toString())
      .set('status', status);
    return this.http.get<CourseDetailDTO[]>(`${this.baseUrl}/trainee/courses`, { params });
  }
}