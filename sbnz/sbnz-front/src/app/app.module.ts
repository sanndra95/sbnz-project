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
import { AddPatientComponent } from './components/add-patient/add-patient.component';
import { PatientService } from './services/patient/patient.service';
import { PatientsComponent } from './components/patients/patients.component';
import { DiagnoseComponent } from './components/diagnose/diagnose.component';
import { RecordsComponent } from './components/records/records.component';
import { OrderModule } from 'ngx-order-pipe';
import { ReportsComponent } from './components/reports/reports.component';

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
    DiseasesComponent,
    AddPatientComponent,
    PatientsComponent,
    DiagnoseComponent,
    RecordsComponent,
    ReportsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    OrderModule,
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
        { path: 'addPatient', component: AddPatientComponent },
        { path: 'patients', component: PatientsComponent },
        { path: 'diagnose/:id', component: DiagnoseComponent },
        { path: 'records/:id', component: RecordsComponent },
        { path: 'reports', component: ReportsComponent },
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
    PatientService,
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
