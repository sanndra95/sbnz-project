import { Symptom } from "./symptom";

export interface Disease {
    id?: number,
    name: string,
    diseaseGroup: string;
    symptoms?: Symptom[];
}