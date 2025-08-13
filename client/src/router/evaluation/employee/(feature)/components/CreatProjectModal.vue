<script setup lang="ts">
import { ref, watch } from 'vue';
import EmployeeEvaluationProjectApi from '../services/projectApi';
import type { EvaluationEmployeeType } from '../services/type';

const props = defineProps<{
  show: boolean;
  employeesEvaluationIdx: number;
}>();

const emit = defineEmits<{
  close: [];
  saved: [];
}>();

const project = ref<EvaluationEmployeeType.Project.Detail>({
  idx: 0,
  employeesEvaluationIdx: 0,
  name: '',
  participationPeriod: '',
  remark: '',
});
const loading = ref(false);

const loadEmployeeProjects = async (employeesEvaluationIdx: number) => {
  if (!employeesEvaluationIdx) return;

  loading.value = true;
  try {
    const res = await EmployeeEvaluationProjectApi.findProjectsByEmployeesEvaluationIdx(employeesEvaluationIdx);
    project.value = res.data.data;
  } catch (error) {
    console.error('프로젝트 로딩 실패:', error);
  } finally {
    loading.value = false;
  }
};

const closeModal = () => {
  emit('close');
};

const validation = () => {
  if (!project.value.name.trim() || !project.value.participationPeriod.trim()) {
    alert('프로젝트명과 참여 기간을 입력해주세요.');
    return false;
  }

  return true;
};

const handleSave = async () => {
  if (!validation()) return;

  try {
    project.value.employeesEvaluationIdx = props.employeesEvaluationIdx;

    if (project.value.idx === 0) {
      await EmployeeEvaluationProjectApi.save(project.value);
    } else {
      await EmployeeEvaluationProjectApi.update(project.value);
    }
    alert('프로젝트가 저장되었습니다.');

    emit('saved');
    closeModal();
  } catch (error) {
    console.error('프로젝트 저장 실패:', error);
    alert('프로젝트 저장에 실패했습니다.');
  }
};

watch(
  () => props.show,
  async (newValue) => {
    if (newValue) {
      document.body.classList.add('modal-open');

      await loadEmployeeProjects(props.employeesEvaluationIdx);
    } else {
      document.body.classList.remove('modal-open');
    }
  },
  { immediate: true },
);
</script>

<template>
  <div v-if="show" class="modal fade show d-block" tabindex="-1" style="background-color: rgba(0, 0, 0, 0.5)">
    <div class="modal-dialog modal-lg modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title fw-bold">프로젝트 관리</h5>
          <button type="button" class="btn-close" @click="closeModal"></button>
        </div>

        <div class="modal-body">
          <div>
            <h6 class="fw-bold mb-3">새 프로젝트 추가</h6>
            <div class="row g-3">
              <div class="col-md-6">
                <label for="projectName" class="form-label fw-semibold">
                  프로젝트명
                  <span class="text-danger">*</span>
                </label>
                <input
                  id="projectName"
                  v-model="project.name"
                  type="text"
                  class="form-control"
                  placeholder="예: A차량 설계 프로젝트"
                />
              </div>

              <div class="col-md-6">
                <label for="participationPeriod" class="form-label fw-semibold">
                  참여 기간
                  <span class="text-danger">*</span>
                </label>
                <input
                  id="participationPeriod"
                  v-model="project.participationPeriod"
                  type="text"
                  class="form-control"
                  placeholder="예: 2024.01 ~ 2024.03"
                />
              </div>

              <div class="col-12">
                <label for="remarks" class="form-label fw-semibold">비고</label>
                <textarea
                  id="remarks"
                  v-model="project.remark"
                  class="form-control"
                  rows="3"
                  placeholder="프로젝트 기여도, 문제 해결 상황 등"
                ></textarea>
              </div>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="closeModal">닫기</button>
          <button type="button" class="btn btn-primary" @click="handleSave" :disabled="loading">
            <span v-if="loading" class="spinner-border spinner-border-sm me-2" role="status"></span>
            프로젝트 추가
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
