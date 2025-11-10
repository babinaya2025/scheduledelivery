// src/app/shared/models.ts

export interface Course {
  id: string;
  name: string;
  trainerId?: string;
}

export interface Participant {
  id: string;
  name: string;
  email: string;
}

export interface Trainer {
  id: string;
  name: string;
  email: string;
}

export interface ScheduleStatus {
  courseId: string;
  locked: boolean; // <-- FIXED: Changed from isLocked to locked
  lockedBy: string | null;
  lockedAt: string | null;
  startDateTime: string | null;
  endDateTime: string | null;
  trainerId: string | null;
  trainerName: string | null;
  location: string | null;
}

export interface ConflictCheckResult {
  hasConflicts: boolean;
  conflicts: string[];
}

export interface ScheduleRequest { // For sending data to backend
  courseId: string;
  trainerId: string;
  startDateTime: string;
  endDateTime: string;
  location: string;
}
export interface Assessment {
  id: number;
  scheduleId: string; // "Schedules id"
  assessmentName: string;
  completionDate: Date; // "date"
  trainer: string;
  score: number | null; // Can be null if not yet scored
  percentage: number | null; // Can be null if not yet scored
  status: 'Scheduled' | 'Completed' | 'In Progress' | 'Cancelled' | 'Failed'; // "status"
}
