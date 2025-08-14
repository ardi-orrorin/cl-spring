import type { EmployeeServiceType } from '@/router/employee/(feature)/serivces/type';

type EmployeeEvaluationT = {
  employeeEvaluationIdx: number;
  employeeName: string;
  employmentStatus: EmployeeServiceType.EmploymentStatus;
  currentAnnualSalary: number;
  nextAnnualSalary: number;
  increaseRate: number;
  totalScore: number;
};

type ReportT = {
  resignCount: number;
  employeeEvaluations: EmployeeEvaluationT[];
};

export namespace ReportType {
  export type EmployeeEvaluation = EmployeeEvaluationT;
  export type Report = ReportT;
}
