// src/app/app.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { RoleService } from './services/role.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  imports: [CommonModule, FormsModule, RouterModule],
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Scheduling and Delivery';
  availableRoles: string[] = ['Admin', 'Trainer', 'Trainee'];
  selectedRole: string = 'Admin';
  currentYear: number = new Date().getFullYear();

  constructor(private roleService: RoleService) {}

  ngOnInit() {
    // Get the current role from the service
    this.selectedRole = this.roleService.getRole();
  }

  onRoleChange(event: Event) {
    this.selectedRole = (event.target as HTMLSelectElement).value;
    console.log(`Role changed to: ${this.selectedRole}`);
    
    // Update role in the service (will notify all subscribers)
    this.roleService.setRole(this.selectedRole);
  }

  login() {
    console.log('Login button clicked');
    // Future: Navigate to login page or open login modal
  }
}