<script setup lang="ts">
import { ref, watch } from 'vue';
import type { EvaluationItemType } from '../services/type';

interface Props {
  show: boolean;
}

interface Emits {
  (e: 'close'): void;
  (e: 'handleCreated', category: EvaluationItemType.Category.Create): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const loading = ref(false);
const categoryForm = ref<EvaluationItemType.Category.Create>({
  name: '',
});

// Reset form when modal opens
watch(
  () => props.show,
  (newShow) => {
    if (newShow) {
      categoryForm.value = {
        name: '',
      };
    }
  },
);

const handleClose = () => {
  emit('close');
};

const handleSubmit = async () => {
  if (!validateForm()) {
    return;
  }

  loading.value = true;
  try {
    emit('handleCreated', categoryForm.value);
    emit('close');
  } catch (error) {
    console.error('Failed to create category:', error);
    alert('카테고리 생성 중 오류가 발생했습니다.');
  } finally {
    loading.value = false;
  }
};

const validateForm = (): boolean => {
  if (!categoryForm.value.name?.trim()) {
    alert('카테고리명을 입력해주세요.');
    return false;
  }

  if (categoryForm.value.name.length > 50) {
    alert('카테고리명은 50자를 초과할 수 없습니다.');
    return false;
  }

  return true;
};
</script>

<template>
  <div
    v-if="show"
    class="modal fade show d-block"
    tabindex="-1"
    style="background-color: rgba(0, 0, 0, 0.5)"
    @click.self="handleClose"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">새 카테고리 추가</h5>
          <button type="button" class="btn-close" @click="handleClose" aria-label="Close"></button>
        </div>

        <div class="modal-body">
          <form @submit.prevent="handleSubmit">
            <div class="mb-3">
              <label for="categoryName" class="form-label">카테고리명</label>
              <input
                id="categoryName"
                v-model="categoryForm.name"
                type="text"
                class="form-control"
                placeholder="예: 기술개발"
                maxlength="50"
                required
                autofocus
              />
              <div class="form-text">{{ categoryForm.name.length }}/50자</div>
            </div>
          </form>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="handleClose" :disabled="loading">취소</button>
          <button type="button" class="btn btn-primary" @click="handleSubmit" :disabled="loading">
            <span v-if="loading" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
            추가
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal {
  display: block;
}

.form-text {
  font-size: 0.875em;
  color: #6c757d;
}
</style>
