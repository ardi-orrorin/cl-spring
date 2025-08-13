import axiosInstance from '@/common/services/exAxios';
import type { EvaluationEmployeeType } from './type';

const findByIdx = (employeeEvaluationIdx: number) =>
  axiosInstance.get(`/v1/evaluation-projects/employees/${employeeEvaluationIdx}`);

const findProjectsByEmployeesEvaluationIdx = (employeeEvaluationIdx: number) =>
  axiosInstance.get(`/v1/evaluation-projects/employees/${employeeEvaluationIdx}`);

const save = (body: EvaluationEmployeeType.Project.Create) => axiosInstance.post('/v1/evaluation-projects', body);

const update = (body: EvaluationEmployeeType.Project.Update) => axiosInstance.put('/v1/evaluation-projects', body);

const EmployeeEvaluationProjectApi = {
  findByIdx,
  findProjectsByEmployeesEvaluationIdx,
  save,
  update,
};

export default EmployeeEvaluationProjectApi;
