import { MedicalComponent } from "./medicalComponent";

export interface Medicine {
    id?: number,
    name: string,
    type: string,
    components?: MedicalComponent[]
}