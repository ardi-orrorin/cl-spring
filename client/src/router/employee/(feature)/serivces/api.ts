import axiosInstance from '@/common/services/exAxios';
import type { CommonType } from '@/common/services/type';
import type { EmployeeServiceType } from './type';

const findAll = async (params: EmployeeServiceType.Search) =>
  axiosInstance.get<CommonType.ResponseStatus<EmployeeServiceType.Summary[]>>('/v1/employees', {
    params,
  });

const findByIdx = (idx: number) =>
  axiosInstance.get<CommonType.ResponseStatus<EmployeeServiceType.Detail>>(`/v1/employees/${idx}`);

const findAllStatus = () =>
  axiosInstance.get<CommonType.ResponseStatus<EmployeeServiceType.Status>>('/v1/employees/status');

const findByEmployeeIdx = (idx: number) =>
  axiosInstance.get<CommonType.ResponseStatus<EmployeeServiceType.EmployeeDetail>>(`/v1/employees/detail/${idx}`);

const save = (body: EmployeeServiceType.Create) =>
  axiosInstance.post<CommonType.ResponseStatus<boolean>>('/v1/employees', body);

const update = (body: EmployeeServiceType.Create) =>
  axiosInstance.put<CommonType.ResponseStatus<boolean>>(`/v1/employees`, body);

const EmployeeServiceApi = {
  findAll,
  findByIdx,
  findAllStatus,
  findByEmployeeIdx,
  save,
  update,
};

export default EmployeeServiceApi;
