import axiosInstance from '@/common/service/exAxios';
import type { CommonType } from '@/common/service/type';
import type { EmployeeServiceType } from './type';

const findAll = async (params: EmployeeServiceType.Search) =>
  axiosInstance.get<CommonType.ResponseStatus<EmployeeServiceType.Summary[]>>('/v1/employees', {
    params,
  });

const findAllStatus = () =>
  axiosInstance.get<CommonType.ResponseStatus<EmployeeServiceType.Status>>('/v1/employees/status');

const findByEmployeeIdx = (idx: number) =>
  axiosInstance.get<CommonType.ResponseStatus<EmployeeServiceType.EmployeeDetail>>(`/v1/employees/detail/${idx}`);

const save = (body: EmployeeServiceType.Create) =>
  axiosInstance.post<CommonType.ResponseStatus<boolean>>('/v1/employees', body);

const EmployeeServiceApi = {
  findAll,
  findAllStatus,
  findByEmployeeIdx,
  save,
};

export default EmployeeServiceApi;
