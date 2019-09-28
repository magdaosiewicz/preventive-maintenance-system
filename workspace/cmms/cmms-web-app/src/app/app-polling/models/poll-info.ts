import { EmployeeProfile } from "../../app-auth/models/employeeProfile";
import { ChoiceInfo } from "./choice-info";

export class PollInfo {
    id: number;
    question: string;
    choices: ChoiceInfo[];
    createdBy: EmployeeProfile;
    creationDateTime: Date;
    expirationDateTime: Date;
    expired: boolean;
    selectedChoice: number;
    totalVotes: number;
}
