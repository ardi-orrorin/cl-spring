<script setup lang="ts">
import EvaluationItemServiceApi from '@/router/evaluationItem/(feature)/services/evaluationItem';
import type { EvaluationItemType } from '@/router/evaluationItem/(feature)/services/type';
import { onBeforeMount, ref, watch } from 'vue';
import CreateProjectModal from './(feature)/components/CreatProjectModal.vue';
import EvaluationEmployeeServiceApi from './(feature)/services/api';
import type { EvaluationEmployeeType } from './(feature)/services/type';

const employeeEvaluations = ref<EvaluationEmployeeType.List[]>([]);
const availableEvaluationItemIdxs = ref([] as (EvaluationItemType.Item.Select & { index: number })[]);

// 프로젝트 모달 상태
const showProjectModal = ref(false);
const selectedEmployee = ref<{ idx: number; name: string; employeesEvaluationIdx: number } | null>(null);
const employeeProjects = ref<Record<number, EvaluationEmployeeType.Project.Detail[]>>({});

onBeforeMount(() => {
  Promise.allSettled([loadEmployees(), loadAvailableEvaluationItemIdxs()]);
});

// 평가 항목 변경을 감지하여 총점 자동 계산
watch(
  () => employeeEvaluations.value.map((emp) => emp.evaluationItemIdxs),
  () => {
    employeeEvaluations.value.forEach((employee) => {
      const totalScore = employee.evaluationItemIdxs.reduce((sum, itemIdx) => {
        const evaluationItem = availableEvaluationItemIdxs.value.find((item) => item.idx === itemIdx);
        return sum + (evaluationItem?.score || 0);
      }, 0);
      employee.totalScore = totalScore;
    });
  },
  { deep: true },
);

const loadAvailableEvaluationItemIdxs = async () => {
  try {
    const res = await EvaluationItemServiceApi.select();
    console.log(res);

    availableEvaluationItemIdxs.value = res.data.data.map((item, index) => ({
      ...item,
      index,
    }));
  } catch (error) {
    alert('평가 항목 로딩 실패');
    console.error('평가 항목 로딩 실패:', error);
  }
};

const loadEmployees = async () => {
  try {
    const res = await EvaluationEmployeeServiceApi.findAll();
    employeeEvaluations.value = res.data.data;
  } catch (error) {
    alert('직원 평가 데이터 로딩 실패');
    console.error('직원 평가 데이터 로딩 실패:', error);
  }
};

const calculateFinalSalary = (currentSalary: number, increaseRate: number) => {
  console.log(currentSalary, increaseRate);

  return currentSalary * (1 + increaseRate / 100);
};

const addEvaluationItem = (employeeEvaluationIdx: number) => {
  const oldEmployeeEvaluation = employeeEvaluations.value.find(
    (employeeEvaluation) => employeeEvaluation.idx === employeeEvaluationIdx,
  );

  const oldEvaluationItems = oldEmployeeEvaluation?.evaluationItemIdxs ?? [];

  const newEvaluationItems = [...oldEvaluationItems, availableEvaluationItemIdxs.value[0].idx];

  employeeEvaluations.value.forEach((employeeEvaluation) => {
    if (employeeEvaluation.idx === employeeEvaluationIdx) {
      employeeEvaluation.evaluationItemIdxs = newEvaluationItems;
    }
  });
};

const removeEvaluationItem = (employeeEvaluationIdx: number, evaluationItemIndex: number) => {
  employeeEvaluations.value.forEach((employeeEvaluation) => {
    if (employeeEvaluation.idx === employeeEvaluationIdx) {
      employeeEvaluation.evaluationItemIdxs.splice(evaluationItemIndex, 1);
    }
  });
};

const updateEvaluationItemScore = (
  employeeEvaluationIdx: number,
  evaluationItemIndex: number,
  availableEvaluationItemIdx: number,
) => {
  const oldEmployeeEvaluation = employeeEvaluations.value.find(
    (employeeEvaluation) => employeeEvaluation.idx === employeeEvaluationIdx,
  );

  const oldEvaluationItems = oldEmployeeEvaluation?.evaluationItemIdxs ?? [];

  oldEvaluationItems[evaluationItemIndex] = availableEvaluationItemIdx;

  const newEvaluationItems = [...oldEvaluationItems];

  employeeEvaluations.value.forEach((employeeEvaluation) => {
    if (employeeEvaluation.idx === employeeEvaluationIdx) {
      employeeEvaluation.evaluationItemIdxs = newEvaluationItems;
    }
  });
};

const handleSubmit = async (employeeEvaluationIdx: number) => {
  const employeeEvaluation = employeeEvaluations.value.find(
    (employeeEvaluation) => employeeEvaluation.idx === employeeEvaluationIdx,
  );

  if (!employeeEvaluation) {
    alert('직원 평가 데이터를 찾을 수 없습니다.');
    return;
  }

  const body = {
    idx: employeeEvaluationIdx,
    evaluationItemIdxs: employeeEvaluation.evaluationItemIdxs,
    increaseRate: employeeEvaluation.increaseRate,
  };

  try {
    const res = await EvaluationEmployeeServiceApi.update(body);
    console.log(res);

    alert('직원 평가 저장 성공');
    loadEmployees();
  } catch (error) {
    alert('직원 평가 저장 실패');
    console.error('직원 평가 저장 실패:', error);
  }
};

const openProjectModal = async (employeeIdx: number, employeeName: string, employeesEvaluationIdx: number) => {
  selectedEmployee.value = { idx: employeeIdx, name: employeeName, employeesEvaluationIdx };
  showProjectModal.value = true;
};

const closeProjectModal = () => {
  showProjectModal.value = false;
  selectedEmployee.value = null;
};
</script>

<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2 class="fw-bold text-dark">사원 평가</h2>
    </div>

    <div class="card shadow-sm">
      <div class="card-body p-0">
        <div class="table-responsive">
          <table class="table table-hover mb-0" style="table-layout: auto">
            <colgroup>
              <col style="width: 8%" />
              <!-- 사원명 -->
              <col style="width: 10%" />
              <!-- 사번 -->
              <col style="width: 8%" />
              <!-- 연차 -->
              <col style="width: 12%" />
              <!-- 연봉(현재) -->
              <col style="width: 25%" />
              <!-- 평가 항목 / 점수 -->
              <col style="width: 8%" />
              <!-- Total 점수 -->
              <col style="width: 12%" />
              <!-- 연봉 상승률(%) -->
              <col style="width: 12%" />
              <!-- 상승 후 연봉 -->
              <col style="width: 10%" />
              <!-- 메모 -->
              <col style="width: 5%" />
              <!-- 저장 -->
            </colgroup>
            <thead class="table-light">
              <tr>
                <th scope="col" class="text-center border-end">사원명</th>
                <th scope="col" class="text-center border-end">사번</th>
                <th scope="col" class="text-center border-end">연차</th>
                <th scope="col" class="text-center border-end">연봉(현재)</th>
                <th scope="col" class="text-center border-end">평가 항목 / 점수</th>
                <th scope="col" class="text-center border-end">Total 점수</th>
                <th scope="col" class="text-center border-end">연봉 상승률(%)</th>
                <th scope="col" class="text-center border-end">상승 후 연봉</th>
                <th scope="col" class="text-center border-end">프로젝트</th>
                <th scope="col" class="text-center">저장</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="employeeEvaluation in employeeEvaluations" :key="employeeEvaluation.idx" class="align-middle">
                <td class="text-center border-end fw-medium">{{ employeeEvaluation.employeeName }}</td>

                <td class="text-center border-end text-muted">{{ employeeEvaluation.employeeNumber }}</td>

                <td class="text-center border-end">
                  {{ new Date().getFullYear() - Number(employeeEvaluation.hireYear) }}년차
                </td>

                <td class="text-center border-end">₩{{ employeeEvaluation.currentAnnualSalary.toLocaleString() }}</td>

                <td class="border-end px-3">
                  <div class="evaluation-items">
                    <div
                      v-for="(itemIdx, index) in employeeEvaluation.evaluationItemIdxs"
                      :key="itemIdx"
                      class="d-flex align-items-center mb-2"
                    >
                      <select
                        class="form-select form-select-sm me-2"
                        style="width: 120px; flex-shrink: 0"
                        :value="itemIdx"
                        @change="
                          updateEvaluationItemScore(
                            employeeEvaluation.idx,
                            index,
                            Number(($event.target as HTMLSelectElement).value),
                          )
                        "
                      >
                        <option v-for="item in availableEvaluationItemIdxs" :key="item.idx" :value="item.idx">
                          {{ item.name }}
                        </option>
                      </select>

                      <span
                        class="form-control form-control-sm me-2 text-center"
                        style="width: 60px; flex-shrink: 0"
                        min="0"
                        max="100"
                        placeholder="점수"
                      >
                        {{
                          availableEvaluationItemIdxs.find(
                            (item) => item.idx === employeeEvaluation.evaluationItemIdxs[index],
                          )?.score ?? 0
                        }}
                      </span>

                      <button
                        class="btn btn-outline-danger btn-sm"
                        type="button"
                        @click="removeEvaluationItem(employeeEvaluation.idx, index)"
                      >
                        삭제
                      </button>
                    </div>

                    <button
                      class="btn btn-outline-primary btn-sm"
                      type="button"
                      @click="addEvaluationItem(employeeEvaluation.idx)"
                    >
                      + 항목 추가
                    </button>
                  </div>
                </td>

                <td class="text-center border-end">
                  <span class="fs-6 px-3 py-2">{{ employeeEvaluation.totalScore }}</span>
                </td>

                <td class="text-center border-end">
                  <div class="d-flex align-items-center justify-content-center">
                    <input
                      v-model.number="employeeEvaluation.increaseRate"
                      type="text"
                      class="form-control form-control-sm text-center"
                      style="width: 80px"
                      @input="
                        ($event.target as HTMLInputElement).value = ($event.target as HTMLInputElement).value.replace(
                          /[^0-9.]/g,
                          '',
                        )
                      "
                      pattern="[0-9]*\.?[0-9]*"
                      inputmode="numeric"
                    />
                  </div>
                </td>

                <td class="text-center border-end fw-bold text-success">
                  ₩{{
                    calculateFinalSalary(
                      employeeEvaluation.currentAnnualSalary,
                      employeeEvaluation.increaseRate,
                    ).toLocaleString()
                  }}
                </td>

                <td class="border-end px-2">
                  <div class="d-flex flex-column gap-1">
                    <div v-if="employeeProjects[employeeEvaluation.idx]?.length" class="small">
                      <div
                        v-for="project in employeeProjects[employeeEvaluation.idx].slice(0, 2)"
                        :key="project.idx"
                        class="text-truncate"
                        style="max-width: 120px"
                        :title="project.name"
                      >
                        • {{ project.name }}
                      </div>
                      <div v-if="employeeProjects[employeeEvaluation.idx].length > 2" class="text-muted">
                        +{{ employeeProjects[employeeEvaluation.idx].length - 2 }}개 더
                      </div>
                    </div>
                    <button
                      type="button"
                      class="btn btn-outline-secondary btn-sm"
                      @click="
                        openProjectModal(
                          employeeEvaluation.idx,
                          employeeEvaluation.employeeName,
                          employeeEvaluation.idx,
                        )
                      "
                    >
                      {{ employeeProjects[employeeEvaluation.idx]?.length ? '메모 관리' : '메모 추가' }}
                    </button>
                  </div>
                </td>

                <td class="text-center">
                  <button class="btn btn-success btn-sm fw-medium" @click="handleSubmit(employeeEvaluation.idx)">
                    저장
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <CreateProjectModal
      :show="showProjectModal"
      :employees-evaluation-idx="selectedEmployee?.employeesEvaluationIdx || 0"
      @close="closeProjectModal"
    />
  </div>
</template>
