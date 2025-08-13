import axiosInstance from '@/common/services/exAxios';
import type { CommonType } from '@/common/services/type';
import type { EvaluationItemType } from './type';

const findAll = (params: EvaluationItemType.Search.Basic) =>
  axiosInstance.get<CommonType.ResponseStatus<EvaluationItemType.Item.Summary[]>>('/v1/evaluation-items', {
    params,
  });

const findByIdx = (idx: number) =>
  axiosInstance.get<CommonType.ResponseStatus<EvaluationItemType.Item.Detail>>(`/v1/evaluation-items/${idx}`);

const select = () =>
  axiosInstance.get<CommonType.ResponseStatus<EvaluationItemType.Item.Select[]>>('/v1/evaluation-items/select');

const save = (body: EvaluationItemType.Item.Create) =>
  axiosInstance.post<CommonType.ResponseStatus<boolean>>('/v1/evaluation-items', body);

const update = (body: EvaluationItemType.Item.Create) =>
  axiosInstance.put<CommonType.ResponseStatus<boolean>>(`/v1/evaluation-items`, body);

const EvaluationItemServiceApi = {
  findAll,
  findByIdx,
  select,
  save,
  update,
};

export default EvaluationItemServiceApi;
