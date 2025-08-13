<script setup lang="ts">
import { onBeforeMount, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import EvaluationCategoryServiceApi from '../(feature)/services/categoryApi';
import EvaluationItemServiceApi from '../(feature)/services/evaluationItem';
import type { EvaluationItemType } from '../(feature)/services/type';

const route = useRoute();
const router = useRouter();

const data = ref<EvaluationItemType.Item.Summary[]>([]);
const categoryData = ref<EvaluationItemType.Category.Detail[]>([]);

const loading = ref(true);
const error = ref<string | null>(null);

const searchParams = ref<EvaluationItemType.Search.Basic>({
  name: (route.query.name as string) || '',
  evaluationCategoryIdx: Number(route.query.evaluationCategoryIdx) || 0,
});

onBeforeMount(async () => {
  await Promise.allSettled([getEmployeeList(), getCategoryList()]);
  if (error.value) {
    alert(error.value);
  }
});

watch(
  () => route.query,
  (newQuery) => {
    searchParams.value = {
      name: (newQuery.name as string) || '',
      evaluationCategoryIdx: Number(newQuery.evaluationCategoryIdx) || 0,
    };
    getEmployeeList();
  },
  { deep: true },
);

const getEmployeeList = async () => {
  loading.value = true;
  try {
    const { data: summaries } = await EvaluationItemServiceApi.findAll(searchParams.value);
    data.value = summaries.data;
  } catch (err) {
    error.value = 'Failed to load employee data';
    console.error('API Error:', err);
  } finally {
    loading.value = false;
  }
};

const getCategoryList = async () => {
  loading.value = true;
  try {
    const { data: categories } = await EvaluationCategoryServiceApi.findAll();
    categoryData.value = categories.data;
  } catch (err) {
    error.value = 'Failed to load category data';
    console.error('API Error:', err);
  } finally {
    loading.value = false;
  }
};

const updateUrlQuery = () => {
  const query: Record<string, string> = {};

  if (searchParams.value.name) query.name = searchParams.value.name;
  if (searchParams.value.evaluationCategoryIdx)
    query.evaluationCategoryIdx = String(searchParams.value.evaluationCategoryIdx);

  router.push({ query });
};

const searchEvaluationItemList = () => {
  updateUrlQuery();
};
</script>

<template>
  <div class="container-fluid py-4">
    <div class="">
      <h1 class="h2">평가 항목 조회</h1>
      <div class="my-3 text-center w-100 row row-cols-3 g-2">
        <div class="col ps-0">
          <input type="text" class="form-control" placeholder="평가 항목명 검색" v-model="searchParams.name" />
        </div>
        <div class="col">
          <select class="form-select w-100" aria-label="근무상태" v-model="searchParams.evaluationCategoryIdx">
            <option value="0" selected>카테고리</option>
            <option v-for="item in categoryData" :key="item.idx" :value="item.idx">{{ item.name }}</option>
          </select>
        </div>
        <div class="col">
          <button class="btn btn-primary w-100" @click="searchEvaluationItemList">검색</button>
        </div>
      </div>
    </div>
    <table class="table table-striped table-hover table-bordered">
      <thead class="table-light">
        <tr>
          <th scope="col">#</th>
          <th scope="col">평가 항목명</th>
          <th scope="col">카테고리</th>
          <th scope="col">점수 배점</th>
          <th scope="col">사용 여부</th>
          <th scope="col">액션</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="loading">
          <td colspan="9" class="text-center">로딩 중...</td>
        </tr>
        <tr v-else-if="error">
          <td colspan="9" class="text-center text-danger">{{ error }}</td>
        </tr>
        <tr v-else-if="data && data && data.length === 0">
          <td colspan="9" class="text-center">데이터가 없습니다.</td>
        </tr>
        <tr
          v-else
          v-for="(value, idx) in data"
          :key="idx"
          @click="router.push(`/evaluation-item/create?edit=true&idx=${value.idx}`)"
        >
          <td>{{ idx + 1 }}</td>
          <td>{{ value.name }}</td>
          <td>{{ value.evaluationCategoryName }}</td>
          <td>
            <span class="fw-bold">{{ value.score?.toLocaleString() }}</span>
          </td>
          <td>{{ value.isUsed ? '사용' : '미사용' }}</td>
          <td>
            <button
              class="btn btn-outline-primary btn-sm me-2"
              @click.stop="router.push(`/evaluation-item/create?edit=true&idx=${value.idx}`)"
            >
              수정
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="mt-5 d-flex gap-3">
      <button class="btn btn-success" @click="router.push('/evaluation-item/create')">신규 항목 등록</button>
    </div>
  </div>
</template>

<style scoped></style>
