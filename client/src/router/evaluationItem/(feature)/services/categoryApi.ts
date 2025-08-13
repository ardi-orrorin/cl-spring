import axiosInstance from '@/common/services/exAxios';
import type { EvaluationItemType } from './type';

const findAll = () => axiosInstance.get('/v1/evaluation-categories');

const save = (body: EvaluationItemType.Category.Create) => axiosInstance.post('/v1/evaluation-categories', body);

const EvaluationCategoryServiceApi = {
  findAll,
  save,
};

export default EvaluationCategoryServiceApi;
