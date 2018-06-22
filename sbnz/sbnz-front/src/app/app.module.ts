import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';


import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RouterModule, Routes } from '@angular/router';
import { UserService } from './services/user/user.service';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ErrorInterceptorService } from './services/error-interceptor/error-interceptor.service';
import { TokenInterceptorService } from './services/token-interceptor/token-interceptor.service';
import { SymptomsComponent } from './components/symptoms/symptoms.component';
import { SymptomService } from './services/symptom/symptom.service';
import { ComponentsComponent } from './components/components/components.component';
import { RegisterDoctorComponent } from './components/register-doctor/register-doctor.component';
import { HomeComponent } from './components/home/home.component';
import { ComponentService } from './services/component/component.service';
import { MedicinesComponent } from './components/medicines/medicines.component';
import { MedicineService } from './services/medicine/medicine.service';
import { DiseasesComponent } from './components/diseases/diseases.component';
import { DiseaseService } from './services/disease/disease.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PageNotFoundComponent,
    SymptomsComponent,
    ComponentsComponent,
    RegisterDoctorComponent,
    HomeComponent,
    MedicinesComponent,
    DiseasesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(
      [
        { path: '', component: LoginComponent},
        { path: 'home', component: HomeComponent },
        { path: 'logout', component: LoginComponent },
        { path: 'symptoms', component: SymptomsComponent },
        { path: 'components', component: ComponentsComponent },
        { path: 'medicines', component: MedicinesComponent },
        { path: 'diseases', component: DiseasesComponent },
        { path: 'registerDoctor', component: RegisterDoctorComponent },
        { path: 'not-found', component: PageNotFoundComponent},
        { path: '**', redirectTo:'not-found'}
      ]
    )
  ],
  providers: [
    UserService,
    SymptomService,
    ComponentService,
    MedicineService,
    DiseaseService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptorService,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
