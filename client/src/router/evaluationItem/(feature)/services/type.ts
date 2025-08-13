type EvaluationCategoryBasicT = {
  name: string;
};

type EvaluationCategoryDetailT = EvaluationCategoryBasicT & {
  idx: number;
};

type EvaluationSearchT = {
  name?: string;
  evaluationCategoryIdx?: number;
};

type SummaryT = {
  idx: number;
  evaluationCategoryName: string;
  name: string;
  score: number;
  isUsed: boolean;
};

type SelectT = {
  idx: number;
  name: string;
  score: number;
};

type EvaluationItemBasicT = {
  name: string;
  score: number;
  isUsed: boolean;
  remark: string;
  evaluationCategoryIdx: number;
};

type EvaluationItemDetailT = EvaluationItemBasicT & {
  idx: number;
};

type EvaluationItemCreateT = EvaluationItemBasicT & {
  idx?: number;
};

export namespace EvaluationItemType {
  export namespace Category {
    export type Basic = EvaluationCategoryBasicT;
    export type Detail = EvaluationCategoryDetailT;
    export type Create = EvaluationCategoryBasicT;
  }
  export namespace Item {
    export type Basic = EvaluationItemBasicT;
    export type Detail = EvaluationItemDetailT;
    export type Create = EvaluationItemCreateT;
    export type Summary = SummaryT;
    export type Select = SelectT;
  }
  export namespace Search {
    export type Basic = EvaluationSearchT;
  }
}
