<script setup lang="ts">
import { computed, onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import EmployeeServiceApi from '../(feature)/serivces/api';
import type { EmployeeServiceType } from '../(feature)/serivces/type';

const router = useRouter();
const route = useRoute();
const loading = ref(false);
const status = ref<EmployeeServiceType.Status>({});

// Check if it's edit mode based on query parameters
const isEditMode = computed(() => {
  return route.query.edit === 'true' && route.query.idx;
});

const employeeIdx = computed(() => {
  return route.query.idx ? Number(route.query.idx) : null;
});

// Format salary with commas for display
const formattedSalary = computed(() => {
  if (!body.value.currentAnnualSalary) return '';
  return body.value.currentAnnualSalary.toLocaleString();
});

onBeforeMount(async () => {
  const { data } = await EmployeeServiceApi.findAllStatus();
  status.value = data.data;

  if (isEditMode.value && employeeIdx.value) {
    try {
      const [employee, detail] = await Promise.allSettled([
        EmployeeServiceApi.findByIdx(employeeIdx.value),
        EmployeeServiceApi.findByEmployeeIdx(employeeIdx.value),
      ]);

      if (employee.status === 'fulfilled' && detail.status === 'fulfilled') {
        const employeeData = employee.value.data.data;
        const detailData = detail.value.data.data;

        body.value = {
          idx: employeeData.idx,
          name: employeeData.name,
          employeeNumber: employeeData.employeeNumber,
          hireYear: employeeData.hireYear,
          currentAnnualSalary: employeeData.currentAnnualSalary,
          jobTitle: employeeData.jobTitle,
          employmentStatus: employeeData.employmentStatus,
          workLocation: employeeData.workLocation,
          deptName: employeeData.deptName,
          email: detailData.email,
          phoneNumber: detailData.phoneNumber,
          remark: detailData.remark,
        };
      }
    } catch (error) {
      console.error('Failed to load employee data:', error);
      alert('직원 정보를 불러오는데 실패했습니다.');
    }
  }
});

const body = ref<EmployeeServiceType.Create>({
  name: '',
  employeeNumber: '',
  hireYear: new Date().getFullYear().toString(),
  currentAnnualSalary: 0,
  jobTitle: '',
  employmentStatus: '',
  workLocation: '',
  deptName: '',
  email: '',
  phoneNumber: '',
  remark: '',
});

const handleSubmit = async () => {
  if (!validateForm()) {
    return;
  }

  loading.value = true;
  try {
    if (isEditMode.value) {
      await EmployeeServiceApi.update(body.value);
    } else {
      await EmployeeServiceApi.save(body.value);
    }

    alert(`${isEditMode.value ? '수정' : '생성'} 완료되었습니다.`);

    router.push('/employee/list');
  } catch (error) {
    console.error('Submit error:', error);
  } finally {
    loading.value = false;
  }
};

const validateForm = (): boolean => {
  if (!body.value.name?.trim()) {
    alert('사원명을 입력해주세요.');
    return false;
  }

  if (!body.value.email?.trim()) {
    alert('이메일을 입력해주세요.');
    return false;
  }

  if (!body.value.hireYear) {
    alert('입사년도를 입력해주세요.');
    return false;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(body.value.email)) {
    alert('올바른 이메일 형식을 입력해주세요.');
    return false;
  }

  if (!body.value.jobTitle) {
    alert('직급을 선택해주세요.');
    return false;
  }

  if (!body.value.employmentStatus) {
    alert('근무 상태를 선택해주세요.');
    return false;
  }

  if (!body.value.workLocation) {
    alert('근무 지역을 선택해주세요.');
    return false;
  }

  const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;
  if (!phoneRegex.test(body.value.phoneNumber)) {
    alert('올바른 전화번호 형식을 입력해주세요. 예: 010-0000-0000');
    return false;
  }

  return true;
};

const handleSalaryInput = (event: Event) => {
  const target = event.target as HTMLInputElement;
  const value = target.value;
  const numericValue = value.replace(/[^\d]/g, '');
  body.value.currentAnnualSalary = numericValue ? Number(numericValue) : 0;
};
</script>

<template>
  <div class="container-fluid py-4">
    <h2 class="mb-4">{{ isEditMode ? '사원 수정' : '사원 등록' }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="row g-3">
        <div class="col-md-6">
          <label for="employeeName" class="form-label">사원명</label>
          <input
            id="employeeName"
            v-model="body.name"
            type="text"
            class="form-control"
            placeholder="예: 홍길동"
            required
          />
        </div>

        <div class="col-md-6">
          <label for="employeeNumber" class="form-label">사번</label>
          <input
            id="employeeNumber"
            v-model="body.employeeNumber"
            type="text"
            class="form-control"
            placeholder="자동 생성 또는 직접 입력"
          />
        </div>

        <div class="col-md-6">
          <label for="hireYear" class="form-label">입사년도</label>
          <input
            id="hireYear"
            v-model="body.hireYear"
            type="text"
            class="form-control"
            placeholder="YYYY"
            min="1900"
            :max="new Date().getFullYear().toString()"
            required
          />
        </div>

        <div class="col-md-6">
          <label for="salary" class="form-label">급여</label>
          <input
            id="salary"
            :value="formattedSalary"
            @input="handleSalaryInput"
            type="text"
            class="form-control"
            placeholder="연봉 (원)"
          />
        </div>

        <div class="col-md-6">
          <label for="jobTitle" class="form-label">직급</label>
          <select id="jobTitle" v-model="body.jobTitle" class="form-select" required>
            <option value="">선택</option>
            <option v-for="item in status.jobTitles" :key="item" :value="item">
              {{ item }}
            </option>
          </select>
        </div>

        <div class="col-md-6">
          <label for="employmentStatus" class="form-label">근무 상태</label>
          <select id="employmentStatus" v-model="body.employmentStatus" class="form-select" required>
            <option value="">선택</option>
            <option v-for="item in status.employmentStatuses" :key="item" :value="item">
              {{ item }}
            </option>
          </select>
        </div>

        <div class="col-md-6">
          <label for="workLocation" class="form-label">근무 지역</label>
          <select id="workLocation" v-model="body.workLocation" class="form-select" required>
            <option value="">선택</option>
            <option v-for="item in status.workLocations" :key="item" :value="item">
              {{ item }}
            </option>
          </select>
        </div>

        <div class="col-md-6">
          <label for="department" class="form-label">부서</label>
          <input
            id="department"
            v-model="body.deptName"
            type="text"
            class="form-control"
            placeholder="예: 인사팀, 개발팀"
          />
        </div>

        <div class="col-md-6">
          <label for="email" class="form-label">이메일</label>
          <input
            id="email"
            v-model="body.email"
            type="email"
            class="form-control"
            placeholder="name@company.com"
            required
          />
        </div>

        <div class="col-md-6">
          <label for="phone" class="form-label">전화번호</label>
          <input id="phone" v-model="body.phoneNumber" type="tel" class="form-control" placeholder="010-0000-0000" />
        </div>
        <div class="col-12">
          <label for="notes" class="form-label">비고</label>
          <textarea
            id="notes"
            v-model="body.remark"
            class="form-control"
            rows="3"
            placeholder="추가 정보 입력"
          ></textarea>
        </div>
      </div>

      <div class="form-actions mt-4 d-flex justify-content-end gap-2">
        <button type="button" class="btn btn-secondary" @click="router.push('/employee/list')">목록</button>
        <button type="button" class="btn btn-primary" @click="handleSubmit" :disabled="loading">
          <span v-if="loading" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
          {{ isEditMode ? '수정' : '저장' }}
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.form-label {
  font-weight: 500;
  margin-bottom: 0.5rem;
}

.form-actions {
  border-top: 1px solid #dee2e6;
  padding-top: 1rem;
}
</style>
