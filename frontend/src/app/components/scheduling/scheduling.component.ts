// src/app/components/scheduling/scheduling.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, ValidatorFn, AbstractControl } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { Course, Participant, Trainer, ScheduleStatus, ConflictCheckResult, ScheduleRequest } from '../../shared/models';
import { SchedulingService } from '../../services/scheduling.service';

// Custom validator for date/time range
export const dateTimeRangeValidator: ValidatorFn = (control: AbstractControl): { [key: string]: boolean } | null => {
  const startDate = control.get('startDate')?.value;
  const startTime = control.get('startTime')?.value;
  const endDate = control.get('endDate')?.value;
  const endTime = control.get('endTime')?.value;

  if (!startDate || !startTime || !endDate || !endTime) {
    return null; // Don't validate if any part is missing
  }

  const startDateTime = new Date(`${startDate}T${startTime}:00`);
  const endDateTime = new Date(`${endDate}T${endTime}:00`);

  if (endDateTime <= startDateTime) {
    return { 'invalidDateTimeRange': true };
  }
  return null;
};


@Component({
  selector: 'app-scheduling',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  providers: [
    SchedulingService
  ],
  templateUrl: './scheduling.component.html',
  styleUrls: ['./scheduling.component.css']
})
export class SchedulingComponent implements OnInit, OnDestroy {
  courses: Course[] = [];
  trainers: Trainer[] = [];

  selectedCourseId: string | null = null;
  participants: Participant[] = [];
  selectedTrainer: Trainer | null = null;

  scheduleForm: FormGroup;
  currentScheduleStatus: ScheduleStatus | null = null;
  conflictCheckResult: ConflictCheckResult | null = null;
  isAdmin: boolean = true;

  isLoadingCourses: boolean = false;
  isLoadingParticipants: boolean = false;
  isLoadingScheduleStatus: boolean = false;
  isLoadingConflictCheck: boolean = false;
  isLoadingLockUnlock: boolean = false;
  isLoadingSendInvite: boolean = false;
  isLoadingTrainers: boolean = false;

  errorMessage: string | null = null;
  successMessage: string | null = null;

  private subscriptions: Subscription = new Subscription();

  constructor(
    private fb: FormBuilder,
    private schedulingService: SchedulingService
  ) {
    this.scheduleForm = this.fb.group({
      course: [null, Validators.required],
      trainer: [null, Validators.required],
      startDate: [this.getMinDate(), Validators.required],
      startTime: [this.getCurrentTime(), Validators.required],
      endDate: [this.getMinDate(), Validators.required],
      endTime: [this.getCurrentTime(1), Validators.required],
      location: ['Virtual Meeting', Validators.required]
    }, { validators: dateTimeRangeValidator });
  }

  ngOnInit(): void {
    this.loadCourses();
    this.loadTrainers();

    this.subscriptions.add(
      this.scheduleForm.get('course')?.valueChanges.subscribe(courseId => {
        this.selectedCourseId = courseId as string;
        this.participants = [];
        this.currentScheduleStatus = null;
        this.conflictCheckResult = null;
        this.errorMessage = null;
        this.successMessage = null;

        const selectedCourse = this.courses.find(c => c.id === courseId);
        if (selectedCourse && selectedCourse.trainerId) {
          this.scheduleForm.get('trainer')?.setValue(selectedCourse.trainerId, { emitEvent: false });
        } else {
          this.scheduleForm.get('trainer')?.setValue(null, { emitEvent: false });
        }
        this.selectedTrainer = this.trainers.find(t => t.id === this.scheduleForm.get('trainer')?.value) || null;


        if (this.selectedCourseId) {
          this.loadParticipants(this.selectedCourseId);
          this.loadScheduleStatus(this.selectedCourseId);
        }
      })
    );

    this.subscriptions.add(
      this.scheduleForm.get('trainer')?.valueChanges.subscribe(trainerId => {
        this.selectedTrainer = this.trainers.find(t => t.id === trainerId) || null;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  getMinDate(): string {
    const today = new Date();
    return today.toISOString().split('T')[0];
  }

  getCurrentTime(offsetHours: number = 0): string {
    const now = new Date();
    now.setHours(now.getHours() + offsetHours);
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
  }


  loadTrainers(): void {
    this.isLoadingTrainers = true;
    this.errorMessage = null;
    this.subscriptions.add(
      this.schedulingService.getTrainers().subscribe({
        next: (trainers: Trainer[]) => {
          this.trainers = trainers;
          this.isLoadingTrainers = false;
          if (this.selectedCourseId) {
            const selectedCourse = this.courses.find(c => c.id === this.selectedCourseId);
            if (selectedCourse && selectedCourse.trainerId) {
              this.scheduleForm.get('trainer')?.setValue(selectedCourse.trainerId, { emitEvent: false });
              this.selectedTrainer = this.trainers.find(t => t.id === selectedCourse.trainerId) || null;
            }
          }
        },
        error: (err: any) => {
          this.errorMessage = this.extractErrorMessage(err) || 'Failed to load trainers.';
          this.isLoadingTrainers = false;
          console.error(err);
        }
      })
    );
  }

  loadCourses(): void {
    this.isLoadingCourses = true;
    this.errorMessage = null;
    this.subscriptions.add(
      this.schedulingService.getCourses().subscribe({
        next: (courses: Course[]) => {
          this.courses = courses;
          this.isLoadingCourses = false;
          if (this.courses.length > 0 && !this.selectedCourseId) {
            this.scheduleForm.get('course')?.setValue(this.courses[0].id);
          }
        },
        error: (err: any) => {
          this.errorMessage = this.extractErrorMessage(err) || 'Failed to load courses.';
          this.isLoadingCourses = false;
          console.error(err);
        }
      })
    );
  }

  loadParticipants(courseId: string): void {
    this.isLoadingParticipants = true;
    this.errorMessage = null;
    this.subscriptions.add(
      this.schedulingService.getApprovedParticipants(courseId).subscribe({
        next: (participants: Participant[]) => {
          this.participants = participants;
          this.isLoadingParticipants = false;
        },
        error: (err: any) => {
          this.errorMessage = this.extractErrorMessage(err) || `Failed to load participants for course ${courseId}.`;
          this.isLoadingParticipants = false;
          console.error(err);
        }
      })
    );
  }

  loadScheduleStatus(courseId: string): void {
    this.isLoadingScheduleStatus = true;
    this.errorMessage = null;
    this.subscriptions.add(
      this.schedulingService.getScheduleStatus(courseId).subscribe({
        next: (status: ScheduleStatus) => {
          console.log('loadScheduleStatus received:', status);
          this.currentScheduleStatus = status;
          this.isLoadingScheduleStatus = false;
        },
        error: (err: any) => {
          this.errorMessage = this.extractErrorMessage(err) || `Failed to load schedule status for course ${courseId}.`;
          this.isLoadingScheduleStatus = false;
          console.error(err);
        }
      })
    );
  }

  checkConflicts(): void {
    this.errorMessage = null;
    this.successMessage = null;
    this.conflictCheckResult = null;
    if (this.scheduleForm.invalid) {
      this.errorMessage = 'Please select a course, trainer, valid date range, time range, and location.';
      this.scheduleForm.markAllAsTouched();
      return;
    }

    const { course, trainer, startDate, startTime, endDate, endTime, location } = this.scheduleForm.value;
    const startDateTime = new Date(`${startDate}T${startTime}:00`);
    const endDateTime = new Date(`${endDate}T${endTime}:00`);

    if (startDateTime >= endDateTime) {
      this.errorMessage = 'End date/time must be after start date/time.';
      return;
    }

    const request: ScheduleRequest = {
      courseId: course as string,
      trainerId: trainer as string,
      startDateTime: startDateTime.toISOString(),
      endDateTime: endDateTime.toISOString(),
      location: location as string
    };

    this.isLoadingConflictCheck = true;
    this.subscriptions.add(
      this.schedulingService.checkConflicts(request).subscribe({
        next: (result: ConflictCheckResult) => {
          this.conflictCheckResult = result;
          this.isLoadingConflictCheck = false;
          if (result.hasConflicts) {
            this.errorMessage = 'Conflicts detected! Please review.';
          } else {
            this.successMessage = 'No conflicts found for the selected range. Schedule can be locked.';
          }
        },
        error: (err: any) => {
          this.errorMessage = this.extractErrorMessage(err) || 'Failed to check conflicts.';
          this.isLoadingConflictCheck = false;
          console.error(err);
        }
      })
    );
  }

  lockSchedule(): void {
    this.errorMessage = null;
    this.successMessage = null;
    if (this.scheduleForm.invalid) {
      this.errorMessage = 'Please select a course, trainer, valid date range, time range, and location before locking.';
      this.scheduleForm.markAllAsTouched();
      return;
    }
    if (!this.isAdmin) {
      this.errorMessage = 'Only administrators can lock a schedule.';
      return;
    }

    const { course, trainer, startDate, startTime, endDate, endTime, location } = this.scheduleForm.value;
    const courseId = course as string;
    const adminId = 'AdminUser123';
    const startDateTime = new Date(`${startDate}T${startTime}:00`);
    const endDateTime = new Date(`${endDate}T${endTime}:00`);

    const request: ScheduleRequest = {
      courseId: courseId,
      trainerId: trainer as string,
      startDateTime: startDateTime.toISOString(),
      endDateTime: endDateTime.toISOString(),
      location: location as string
    };

    this.isLoadingLockUnlock = true;
    this.subscriptions.add(
      this.schedulingService.lockSchedule(courseId, adminId, request).subscribe({
        next: (status: ScheduleStatus) => {
          console.log('lockSchedule successful, received:', status);
          this.isLoadingLockUnlock = false;
          const courseName = this.courses.find(c => c.id === courseId)?.name || 'the training';
          this.successMessage = `Schedule locked successfully for the training "${courseName}"!`;
          this.loadScheduleStatus(courseId); // Re-fetch status after successful lock
        },
        error: (err: any) => {
          this.errorMessage = this.extractErrorMessage(err) || 'Failed to lock schedule.';
          this.isLoadingLockUnlock = false;
          console.error(err);
        }
      })
    );
  }

  unlockSchedule(): void {
    this.errorMessage = null;
    this.successMessage = null;
    if (!this.isAdmin) {
      this.errorMessage = 'Only administrators can unlock a schedule.';
      return;
    }
    // The backend will now throw a ConflictException if the schedule is not locked.
    // So, no need for frontend check here.

    const courseId = this.scheduleForm.get('course')?.value as string;
    const adminId = 'AdminUser123';

    this.isLoadingLockUnlock = true;
    this.subscriptions.add(
      this.schedulingService.unlockSchedule(courseId, adminId).subscribe({
        next: (status: ScheduleStatus) => {
          console.log('unlockSchedule successful, received:', status);
          this.isLoadingLockUnlock = false;
          this.successMessage = 'Schedule unlocked successfully!';
          this.loadScheduleStatus(courseId); // Re-fetch status after successful unlock
        },
        error: (err: any) => {
          this.errorMessage = this.extractErrorMessage(err) || 'Failed to unlock schedule.';
          this.isLoadingLockUnlock = false;
          console.error(err);
        }
      })
    );
  }

  sendCalendarInvite(): void {
    this.errorMessage = null;
    this.successMessage = null;
    if (!this.currentScheduleStatus || !this.currentScheduleStatus.locked) { // Use .locked
      this.errorMessage = 'Schedule must be locked before sending invites.';
      return;
    }

    const courseId = this.scheduleForm.get('course')?.value as string;

    this.isLoadingSendInvite = true;
    this.subscriptions.add(
      this.schedulingService.sendCalendarInvite(courseId).subscribe({
        next: (response: { message: string }) => {
          this.successMessage = response.message;
          this.isLoadingSendInvite = false;
        },
        error: (err: any) => {
          this.errorMessage = this.extractErrorMessage(err) || 'Failed to send calendar invite.';
          this.isLoadingSendInvite = false;
          console.error(err);
        }
      })
    );
  }

  isFormControlInvalid(controlName: string): boolean {
    const control = this.scheduleForm.get(controlName);
    return control ? control.invalid && (control.dirty || control.touched) : false;
  }

  isFormGroupInvalid(groupName?: string): boolean {
    const control = groupName ? this.scheduleForm.get(groupName) : this.scheduleForm;
    return control ? control.invalid && (control.dirty || control.touched) : false;
  }

  // Helper function to extract error message from backend's consistent error structure
  private extractErrorMessage(error: any): string | null {
    if (error && error.error && error.error.message) {
      return error.error.message;
    } else if (error && error.message) {
      return error.message;
    }
    return null;
  }
}
