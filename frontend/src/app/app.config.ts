// src/app/app.config.ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http'; // <-- Ensure this is imported and provided
import { provideAnimations } from '@angular/platform-browser/animations'; // Optional

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(), // <-- Ensure HttpClient is provided here
    provideAnimations(), // Optional
  ]
};
