import axiosInstance from '@/common/services/exAxios';
import type { CommonType } from '@/common/services/type';
import type { EvaluationEmployeeType } from './type';

const findAll = () =>
  axiosInstance.get<CommonType.ResponseStatus<EvaluationEmployeeType.List[]>>('/v1/employees-evaluation');

const update = (body: EvaluationEmployeeType.Update) => axiosInstance.put('/v1/employees-evaluation', body);

const EvaluationEmployeeServiceApi = {
  findAll,
  update,
};

export default EvaluationEmployeeServiceApi;
