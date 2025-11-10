import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Assessment } from '../../shared/models';
import {AssessmentService} from '../../services/assessment.service';

@Component({
  selector: 'app-assessment',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './assessment.component.html',
  styleUrls: ['./assessment.component.css']
})
export class AssessmentComponent implements OnInit {
  assessments: Assessment[] = [];
  filteredAssessments: Assessment[] = [];

  // Form related properties
  showForm = false;
  isEditing = false;
  selectedAssessment: Assessment | null = null;
  newAssessment: Omit<Assessment, 'id'> = {
    scheduleId: '',
    assessmentName: '',
    completionDate: new Date(),
    trainer: '',
    score: null,
    percentage: null,
    status: 'Scheduled'
  };

  // Filter related properties
  filterAssessmentName = '';
  filterStatus = '';
  filterCompletionDate: string | null = null; // Use string for input type="date"

  // Messages
  successMessage: string | null = null;
  errorMessage: string | null = null;

  // Sorting
  sortColumn: keyof Assessment | '' = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // Dropdown options for status
  statusOptions: ('Scheduled' | 'Completed' | 'In Progress' | 'Cancelled' | 'Failed')[] =
    ['Scheduled', 'Completed', 'In Progress', 'Cancelled', 'Failed'];

  constructor(private assessmentService: AssessmentService) { }

  ngOnInit(): void {
    this.loadAssessments();
  }

  loadAssessments(): void {
    // @ts-ignore
    this.assessmentService.getAssessments().subscribe({
      complete(): void {
      },
      next: ({data}: { data: any }) => {
        this.assessments = data;
        this.applyFilters(); // Apply filters initially
      },
      error: ({err}: { err: any }) => {
        this.showError('Failed to load assessments.');
        console.error({err: err});
      }
    });
  }

  // --- Form Actions ---
  openFormForAdd(): void {
    this.isEditing = false;
    this.selectedAssessment = null;
    this.newAssessment = {
      scheduleId: '',
      assessmentName: '',
      completionDate: new Date(),
      trainer: '',
      score: null,
      percentage: null,
      status: 'Scheduled'
    };
    // Only clear messages when opening the form
    this.clearMessages();
    this.showForm = true;
  }

  openFormForEdit(assessment: Assessment): void {
    this.isEditing = true;
    this.selectedAssessment = { ...assessment }; // Create a copy to avoid direct mutation
    // Format date for input type="date"
    this.newAssessment = {
      ...assessment,
      completionDate: new Date(assessment.completionDate) // Ensure it's a Date object
    };
    // Only clear messages when opening the form
    this.clearMessages();
    this.showForm = true;
  }

  saveAssessment(): void {
    // Do not clear messages here, let showSuccess/showError handle it
    if (!this.newAssessment.assessmentName || !this.newAssessment.scheduleId || !this.newAssessment.trainer) {
      this.showError('Please fill in all required fields (Schedule ID, Assessment Name, Trainer).');
      return;
    }

    // Ensure completionDate is a Date object if it came from a string input
    const completionDate = new Date(this.newAssessment.completionDate);
    if (isNaN(completionDate.getTime())) {
      this.showError('Invalid completion date.');
      return;
    }

    const assessmentToSave: Assessment = {
      ...(this.selectedAssessment ? this.selectedAssessment : {} as Assessment), // If editing, include ID
      ...this.newAssessment,
      completionDate: completionDate // Use the validated Date object
    };

    if (this.isEditing && this.selectedAssessment) {
      this.assessmentService.updateAssessment(assessmentToSave).subscribe({
        next: () => {
          this.showSuccess('Assessment updated successfully!');
          this.loadAssessments();
          this.cancelForm();
        },
        error: (err:any) => {
          this.showError('Failed to update assessment.');
          console.error({err: err});
        }
      });
    } else {
      // Omit 'id' for new assessment
      const assessmentWithoutId: Omit<Assessment, 'id'> = {
        scheduleId: assessmentToSave.scheduleId,
        assessmentName: assessmentToSave.assessmentName,
        completionDate: assessmentToSave.completionDate,
        trainer: assessmentToSave.trainer,
        score: assessmentToSave.score,
        percentage: assessmentToSave.percentage,
        status: assessmentToSave.status
      };
      this.assessmentService.addAssessment(assessmentWithoutId).subscribe({
        next: () => {
          this.showSuccess('Assessment added successfully!');
          this.loadAssessments();
          this.cancelForm();
        },
        error: (err:any) => {
          this.showError('Failed to add assessment.');
          console.error({err: err});
        }
      });
    }
  }

  deleteAssessment(id: number): void {
    // Do not clear messages here, let showSuccess/showError handle it
    if (confirm('Are you sure you want to delete this assessment?')) {
      this.assessmentService.deleteAssessment(id).subscribe({
        next: (success:any) => {
          if (success) {
            this.showSuccess('Assessment deleted successfully!');
            this.loadAssessments();
          } else {
            this.showError('Assessment not found or failed to delete.');
          }
        },
        error: (err:any) => {
          this.showError('Failed to delete assessment.');
          console.error({err: err});
        }
      });
    }
  }

  cancelForm(): void {
    this.showForm = false;
    this.isEditing = false;
    this.selectedAssessment = null;
    this.newAssessment = {
      scheduleId: '',
      assessmentName: '',
      completionDate: new Date(),
      trainer: '',
      score: null,
      percentage: null,
      status: 'Scheduled'
    };
    this.clearMessages();
  }

  // --- Filter Actions ---
  applyFilters(): void {
    let tempAssessments = [...this.assessments];

    if (this.filterAssessmentName) {
      tempAssessments = tempAssessments.filter(a =>
        a.assessmentName.toLowerCase().includes(this.filterAssessmentName.toLowerCase())
      );
    }

    if (this.filterStatus) {
      tempAssessments = tempAssessments.filter(a =>
        a.status.toLowerCase() === this.filterStatus.toLowerCase()
      );
    }

    if (this.filterCompletionDate) {
      const filterDate = new Date(this.filterCompletionDate);
      tempAssessments = tempAssessments.filter(a =>
        a.completionDate.toDateString() === filterDate.toDateString()
      );
    }

    this.filteredAssessments = tempAssessments;
    this.sortData(this.sortColumn, this.sortDirection); // Re-apply sort after filter
  }

  clearFilters(): void {
    this.filterAssessmentName = '';
    this.filterStatus = '';
    this.filterCompletionDate = null;
    this.applyFilters();
  }

  // --- Sorting Actions ---
  sortBy(column: keyof Assessment): void {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }
    this.sortData(this.sortColumn, this.sortDirection);
  }

  private sortData(column: keyof Assessment | '', direction: 'asc' | 'desc'): void {
    if (!column) {
      return;
    }

    this.filteredAssessments.sort((a, b) => {
      const valA = a[column];
      const valB = b[column];

      if (typeof valA === 'string' && typeof valB === 'string') {
        return direction === 'asc' ? valA.localeCompare(valB) : valB.localeCompare(valA);
      }
      if (typeof valA === 'number' && typeof valB === 'number') {
        return direction === 'asc' ? valA - valB : valB - valA;
      }
      if (valA instanceof Date && valB instanceof Date) {
        return direction === 'asc' ? valA.getTime() - valB.getTime() : valB.getTime() - valA.getTime();
      }
      // Handle nulls for score/percentage
      if (valA === null && valB !== null) return direction === 'asc' ? -1 : 1;
      if (valA !== null && valB === null) return direction === 'asc' ? 1 : -1;
      return 0; // Equal or both null
    });
  }

  // --- Utility for messages ---
  showSuccess(message: string): void {
    this.successMessage = message;
    setTimeout(() => this.successMessage = null, 3000);
  }

  showError(message: string): void {
    this.errorMessage = message;
    setTimeout(() => this.errorMessage = null, 5000);
  }

  clearMessages(): void {
    this.successMessage = null;
    this.errorMessage = null;
  }

  // Helper to format date for display
  formatDate(date: Date): string {
    return date ? new Date(date).toLocaleDateString() : '';
  }

  // Helper to get date string for input type="date"
  getDateInputString(date: Date): string {
    if (!date) return '';
    const d = new Date(date);
    const year = d.getFullYear();
    const month = (d.getMonth() + 1).toString().padStart(2, '0');
    const day = d.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  protected readonly Date = Date;
  // @ts-ignore
  protected readonly any = {};

  onCompletionDateChange($event: any) {

  }
}
