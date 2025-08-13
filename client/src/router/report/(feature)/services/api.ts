import axiosInstance from '@/common/services/exAxios';

const findAllUserReport = () => axiosInstance.get('/v1/employees-evaluation/report');

const ReportServiceApi = {
  findAllUserReport,
};

export default ReportServiceApi;
