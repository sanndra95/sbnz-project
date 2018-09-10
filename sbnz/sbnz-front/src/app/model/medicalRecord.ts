import { Disease } from "./disease";
import { Symptom } from "./symptom";
import { Medicine } from "./medicine";

export interface MedicalRecord {
    id?: number;
    disease?: Disease;
    symptoms: Symptom[];
    medicine?: Medicine;
    date?: Date;
}