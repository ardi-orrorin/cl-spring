<script setup lang="ts">
import { onBeforeMount, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import DetailModal from '../(feature)/components/DetailModal.vue';
import EmployeeServiceApi from '../(feature)/serivces/api';
import type { EmployeeServiceType } from '../(feature)/serivces/type';

const route = useRoute();
const router = useRouter();

const data = ref<EmployeeServiceType.Summary[]>([]);
const status = ref<EmployeeServiceType.Status>({});

const loading = ref(true);
const error = ref<string | null>(null);
const showDetailModal = ref(false);
const selectedEmployeeId = ref<number | null>(null);

const searchParams = ref<EmployeeServiceType.Search>({
  name: (route.query.name as string) || '',
  employmentStatus: (route.query.employmentStatus as string) || '',
  workLocation: (route.query.workLocation as string) || '',
});

onBeforeMount(async () => {
  await refreshEmployeeList();
  const { data } = await EmployeeServiceApi.findAllStatus();

  status.value = data.data;
});

watch(
  () => route.query,
  (newQuery) => {
    searchParams.value = {
      name: (newQuery.name as string) || '',
      employmentStatus: (newQuery.employmentStatus as string) || '',
      workLocation: (newQuery.workLocation as string) || '',
    };
    refreshEmployeeList();
  },
  { deep: true },
);

const refreshEmployeeList = async () => {
  loading.value = true;
  try {
    const { data: summaries } = await EmployeeServiceApi.findAll(searchParams.value);
    data.value = summaries.data;
  } catch (err) {
    error.value = 'Failed to load employee data';
    console.error('API Error:', err);
  } finally {
    loading.value = false;
  }
};

const updateUrlQuery = () => {
  const query: Record<string, string> = {};

  if (searchParams.value.name) query.name = searchParams.value.name;
  if (searchParams.value.employmentStatus) query.employmentStatus = searchParams.value.employmentStatus;
  if (searchParams.value.workLocation) query.workLocation = searchParams.value.workLocation;

  router.push({ query });
};

const searchEmployeeList = () => {
  updateUrlQuery();
};

const openDetailModal = (employeeId: number) => {
  selectedEmployeeId.value = employeeId;
  showDetailModal.value = true;
};

const closeDetailModal = () => {
  showDetailModal.value = false;
  selectedEmployeeId.value = null;
};
</script>

<template>
  <div class="container-fluid py-4">
    <div>
      <h1 class="h2">사원 조회</h1>
      <div class="my-3 text-center w-100 row row-cols-4 g-2">
        <div class="col ps-0">
          <input type="text" class="form-control" placeholder="사원명" v-model="searchParams.name" />
        </div>
        <div class="col">
          <select class="form-select w-100" aria-label="근무상태" v-model="searchParams.employmentStatus">
            <option value="">근무상태</option>
            <option v-for="item in status.employmentStatuses" :key="item" :value="item">{{ item }}</option>
          </select>
        </div>
        <div class="col">
          <select class="form-select w-100" aria-label="근무지역" v-model="searchParams.workLocation">
            <option value="">근무지역</option>
            <option v-for="item in status.workLocations" :key="item" :value="item">{{ item }}</option>
          </select>
        </div>
        <div class="col">
          <button class="btn btn-primary w-100" @click="searchEmployeeList">검색</button>
        </div>
      </div>
    </div>
    <table class="table table-striped table-hover table-bordered">
      <thead class="table-light">
        <tr>
          <th scope="col">#</th>
          <th scope="col">사원명</th>
          <th scope="col">사번</th>
          <th scope="col">입사년도</th>
          <th scope="col">급여</th>
          <th scope="col">직급</th>
          <th scope="col">근무상태</th>
          <th scope="col">근무지역</th>
          <th scope="col">상세</th>
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
          @click="router.push(`/employee/create?edit=true&idx=${value.idx}`)"
        >
          <td>{{ idx + 1 }}</td>
          <td>{{ value.name }}</td>
          <td>{{ value.employeeNumber }}</td>
          <td>{{ value.hireYear }}</td>
          <td>
            <span class="fw-bold">{{ value.currentAnnualSalary?.toLocaleString() }}</span>
          </td>
          <td>{{ value.jobTitle }}</td>
          <td>{{ value.employmentStatus }}</td>
          <td>{{ value.workLocation }}</td>
          <td>
            <button class="btn btn-outline-primary btn-sm me-2" @click.stop="openDetailModal(value.idx)">상세</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="mt-5 d-flex gap-3">
      <button class="btn btn-success" @click="router.push('/employee/create')">신규 사원 등록</button>
      <button class="btn btn-success">사원 등록 (Excel)</button>
    </div>

    <DetailModal :show="showDetailModal" :employee-id="selectedEmployeeId" @close="closeDetailModal" />
  </div>
</template>

<style scoped></style>
