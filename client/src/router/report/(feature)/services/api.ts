import axiosInstance from '@/common/services/exAxios';
import type { CommonType } from '@/common/services/type';
import type { ReportType } from './type';

const findAllUserReport = () =>
  axiosInstance.get<CommonType.ResponseStatus<ReportType.Report[]>>('/v1/employees-evaluation/report');

const ReportServiceApi = {
  findAllUserReport,
};

export default ReportServiceApi;
