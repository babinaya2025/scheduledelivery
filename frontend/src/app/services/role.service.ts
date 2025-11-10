// src/app/services/role.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private currentRoleSubject: BehaviorSubject<string>;
  public currentRole$: Observable<string>;

  constructor() {
    // Initialize with stored role or default to 'Admin'
    const storedRole = localStorage.getItem('currentRole') || 'Admin';
    this.currentRoleSubject = new BehaviorSubject<string>(storedRole);
    this.currentRole$ = this.currentRoleSubject.asObservable();
  }

  setRole(role: string): void {
    localStorage.setItem('currentRole', role);
    this.currentRoleSubject.next(role);
  }

  getRole(): string {
    return this.currentRoleSubject.value;
  }
}