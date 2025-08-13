import type { EmployeeServiceType } from '@/router/employee/(feature)/serivces/type';

type ReportT = {
  employeeEvaluationIdx: number;
  employeeName: string;
  employmentStatus: EmployeeServiceType.EmploymentStatus;
  currentAnnualSalary: number;
  nextAnnualSalary: number;
  increaseRate: number;
  totalScore: number;
};

export namespace ReportType {
  export type Report = ReportT;
}
