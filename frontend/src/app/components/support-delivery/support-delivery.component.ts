// src/app/components/support-delivery/support-delivery.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RoleService } from '../../services/role.service';
import { SupportDeliveryService, CourseDetailDTO, TopicDTO, WeekDTO, TraineeDTO } from '../../services/support-delivery.service';
import { Subscription } from 'rxjs';

// ✅ Extend WeekDTO to add frontend-only properties
interface ExtendedWeekDTO extends WeekDTO {
  expanded: boolean;
}

// ✅ Extend CourseDetailDTO to use ExtendedWeekDTO
interface Course extends Omit<CourseDetailDTO, 'weeks'> {
  showTrainees: boolean;
  weeks: ExtendedWeekDTO[];
}

@Component({
  selector: 'app-support-delivery',
  imports: [CommonModule, FormsModule],
  templateUrl: './support-delivery.component.html',
  styleUrls: ['./support-delivery.component.css']
})
export class SupportDeliveryComponent implements OnInit, OnDestroy {
  currentRole: string = 'Admin';
  selectedStatus: string = 'all';
  courses: Course[] = [];
  filteredCourses: Course[] = [];
  isLoading: boolean = false;
  errorMessage: string = '';
  
  currentTrainerId: number = 1;
  currentTraineeId: number = 100;
  
  private roleSubscription!: Subscription;

  constructor(
    private roleService: RoleService,
    private supportDeliveryService: SupportDeliveryService
  ) {}

  ngOnInit(): void {
    this.roleSubscription = this.roleService.currentRole$.subscribe(role => {
      console.log(`Support Delivery - Role updated to: ${role}`);
      this.currentRole = role;
      this.loadCourses();
    });
  }

  ngOnDestroy(): void {
    if (this.roleSubscription) {
      this.roleSubscription.unsubscribe();
    }
  }

  loadCourses(): void {
    this.isLoading = true;
    this.errorMessage = '';
    
    let apiCall;
    
    if (this.currentRole === 'Admin') {
      apiCall = this.supportDeliveryService.getAllCoursesForAdmin(this.selectedStatus);
    } else if (this.currentRole === 'Trainer') {
      apiCall = this.supportDeliveryService.getCoursesForTrainer(this.currentTrainerId, this.selectedStatus);
    } else if (this.currentRole === 'Trainee') {
      apiCall = this.supportDeliveryService.getCoursesForTrainee(this.currentTraineeId, this.selectedStatus);
    } else {
      this.isLoading = false;
      return;
    }
    
    apiCall.subscribe({
      next: (data) => {
        // ✅ Map backend data to frontend model with expanded property
        this.courses = data.map(course => ({
          ...course,
          showTrainees: false,
          weeks: course.weeks.map(week => ({
            ...week,
            expanded: false
          }))
        }));
        this.filterCourses();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading courses:', error);
        this.errorMessage = 'Failed to load courses. Please try again.';
        this.isLoading = false;
        this.courses = [];
        this.filteredCourses = [];
      }
    });
  }

  filterCourses(): void {
    if (this.selectedStatus === 'all') {
      this.filteredCourses = [...this.courses];
    } else {
      this.filteredCourses = this.courses.filter(c => c.status === this.selectedStatus);
    }
  }

  onStatusChange(): void {
    this.loadCourses();
  }

  toggleTrainees(course: Course): void {
    course.showTrainees = !course.showTrainees;
  }

  toggleWeek(week: ExtendedWeekDTO): void {
    week.expanded = !week.expanded;
  }

  updateTopicStatus(topic: TopicDTO, week: ExtendedWeekDTO, course: Course): void {
    if (this.currentRole !== 'Trainer') return;
    
    const newStatus = !topic.covered;
    
    this.supportDeliveryService.updateTopicStatus(topic.topicId, newStatus).subscribe({
      next: (updatedTopic) => {
        topic.covered = updatedTopic.covered;
        console.log(`✅ Updated "${topic.name}" - Covered: ${topic.covered}`);
      },
      error: (error) => {
        console.error('Error updating topic:', error);
        alert('Failed to update topic status. Please try again.');
      }
    });
  }

  updateLink(linkType: string, week: ExtendedWeekDTO, course: Course, event: Event): void {
    if (this.currentRole !== 'Trainer') return;
    
    const value = (event.target as HTMLInputElement).value;
    
    const request: any = {};
    if (linkType === 'drive') {
      request.driveLink = value;
      week.driveLink = value;
    } else if (linkType === 'github') {
      request.githubLink = value;
      week.githubLink = value;
    } else if (linkType === 'assignment') {
      request.assignmentLink = value;
      week.assignmentLink = value;
    }
    
    this.supportDeliveryService.updateWeekLinks(week.weekId, request).subscribe({
      next: (updatedWeek) => {
        console.log(`🔗 Updated ${linkType} link for Week ${week.weekNumber}`);
      },
      error: (error) => {
        console.error('Error updating link:', error);
        alert('Failed to update link. Please try again.');
      }
    });
  }

  getStatusBadgeClass(status: string): string {
    return `status-badge status-${status}`;
  }

  getRoleDisplayName(): string {
    return this.currentRole;
  }

  canEdit(): boolean {
    return this.currentRole === 'Trainer';
  }

  isAdmin(): boolean {
    return this.currentRole === 'Admin';
  }

  isTrainee(): boolean {
    return this.currentRole === 'Trainee';
  }

  isTrainer(): boolean {
    return this.currentRole === 'Trainer';
  }
}