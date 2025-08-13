<script setup lang="ts">
import { ref, watch } from 'vue';
import EmployeeServiceApi from '../serivces/api';

const props = defineProps<{
  show: boolean;
  employeeId: number | null;
}>();

const emit = defineEmits<{
  close: [];
  edit: [employeeId: number];
}>();

const loading = ref(false);
const error = ref<string | null>(null);
const employeeDetail = ref<any>(null);

watch(
  () => [props.show, props.employeeId],
  async ([show, employeeId]) => {
    if (show && employeeId && typeof employeeId === 'number') {
      await loadEmployeeDetail(employeeId);
    }
  },
  { immediate: true },
);

const loadEmployeeDetail = async (employeeId: number) => {
  if (!employeeId) return;

  loading.value = true;
  error.value = null;
  employeeDetail.value = null;

  try {
    const { data } = await EmployeeServiceApi.findByEmployeeIdx(employeeId);
    employeeDetail.value = data.data;
  } catch (err) {
    error.value = '사원 정보를 불러오는데 실패했습니다.';
    console.error('Failed to load employee detail:', err);
  } finally {
    loading.value = false;
  }
};

const closeModal = () => {
  emit('close');
};

watch(
  () => props.show,
  (newValue) => {
    if (newValue) {
      document.body.classList.add('modal-open');
    } else {
      document.body.classList.remove('modal-open');
      employeeDetail.value = null;
      error.value = null;
    }
  },
  { immediate: true },
);
</script>

<template>
  <Teleport to="body">
    <div
      v-if="show"
      class="modal fade show modal-center"
      tabindex="-1"
      role="dialog"
      aria-labelledby="employeeDetailModalLabel"
      aria-modal="true"
    >
      <div class="modal-dialog modal-lg w-50" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="employeeDetailModalLabel">사원 상세정보</h5>
            <button type="button" class="btn-close" aria-label="Close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <div v-if="loading" class="text-center py-5">
              <div class="spinner-border" role="status">
                <span class="visually-hidden">Loading...</span>
              </div>
              <p class="mt-2">데이터를 불러오는 중...</p>
            </div>

            <div v-else-if="error" class="alert alert-danger" role="alert">
              <i class="bi bi-exclamation-triangle"></i>
              {{ error }}
            </div>

            <div v-else-if="employeeDetail" class="row g-3">
              <div class="col-md-6">
                <label class="form-label fw-bold">이메일</label>
                <div class="form-control-plaintext">
                  <a :href="`mailto:${employeeDetail.email}`" class="text-decoration-none">
                    <i class="bi bi-envelope"></i> {{ employeeDetail.email }}
                  </a>
                </div>
              </div>

              <div class="col-md-6">
                <label class="form-label fw-bold">전화번호</label>
                <div class="form-control-plaintext">
                  <span v-if="employeeDetail.phoneNumber">
                    <i class="bi bi-telephone"></i> {{ employeeDetail.phoneNumber }}
                  </span>
                  <span v-else class="text-muted">-</span>
                </div>
              </div>

              <div class="col-12 mt-4">
                <h6 class="pb-2 mb-3">비고</h6>
                <div class="form-control-plaintext">
                  {{ employeeDetail.remark || '' }}
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeModal">닫기</button>
          </div>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
.modal-center {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal.show {
  animation: fadeIn 0.15s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.modal-dialog {
  animation: slideIn 0.15s ease-out;
}

@keyframes slideIn {
  from {
    transform: translateY(-50px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.form-control-plaintext {
  padding: 0.375rem 0;
  margin-bottom: 0;
  font-size: inherit;
  line-height: 1.5;
  background-color: transparent;
  border: solid transparent;
  border-width: 1px 0;
}

.badge {
  font-size: 0.875em;
}

.border-bottom {
  border-bottom: 2px solid #dee2e6 !important;
}

h6 {
  color: #495057;
  font-weight: 600;
}
</style>
