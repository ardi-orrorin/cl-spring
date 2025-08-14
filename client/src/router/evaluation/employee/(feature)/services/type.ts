type ListT = {
  idx: number;
  employeeIdx: number;
  employeeName: string;
  hireYear: string;
  employeeNumber: string;
  evaluationItemIdxs: number[];
  increaseRate: number;
  totalScore: number;
  currentAnnualSalary: number;
};

type UpdateT = {
  idx: number;
  employeeIdx: number;
  evaluationItemIdxs: number[];
  increaseRate: number;
};

type ProjectT = {
  idx: number;
  employeesEvaluationIdx: number;
  name: string;
  participationPeriod: string;
  remark: string;
};

type ProjectCreateT = {
  employeesEvaluationIdx: number;
  name: string;
  participationPeriod: string;
  remark: string;
};

export namespace EvaluationEmployeeType {
  export type List = ListT;
  export type Update = UpdateT;
  export namespace Project {
    export type Detail = ProjectT;
    export type Create = ProjectCreateT;
    export type Update = ProjectT;
  }
}
