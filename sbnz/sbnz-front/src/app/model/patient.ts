import { Medicine } from "./medicine";
import { MedicalComponent } from "./medicalComponent";
import { MedicalRecord } from "./medicalRecord";

export interface Patient {
    id?: number;
    firstName: string;
    lastName: string;
    records?: MedicalRecord[];
    medicineAllergies?: Medicine[];
    componentAllergies?: MedicalComponent[];
}