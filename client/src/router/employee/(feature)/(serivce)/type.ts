type SummaryT = {
  idx: number;
  name: string;
  employeeNumber: string;
  hireYear: string;
  jobTitle: string;
  currentAnnualSalary: number;
  employmentStatus: string;
  workLocation: string;
};

type SearchT = {
  name: string;
  employmentStatus: string;
  workLocation: string;
};

type StatusT = {
  [key: string]: string[];
};

type EmployeeDetailT = {
  email: string;
  phoneNumber: string;
  remark: string;
};

type CreateT = {
  name: string;
  employeeNumber: string;
  hireYear: string;
  jobTitle: string;
  currentAnnualSalary: number;
  employmentStatus: string;
  deptName: string;
  workLocation: string;
  email: string;
  phoneNumber: string;
  remark: string;
};

export namespace EmployeeServiceType {
  export type Summary = SummaryT;
  export type Search = SearchT;
  export type Status = StatusT;
  export type EmployeeDetail = EmployeeDetailT;
  export type Create = CreateT;
}
