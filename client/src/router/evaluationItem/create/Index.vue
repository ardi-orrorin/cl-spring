<script setup lang="ts">
import { computed, onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import CategoryModal from '../(feature)/components/CategoryModal.vue';
import EvaluationCategoryServiceApi from '../(feature)/services/categoryApi';
import EvaluationItemServiceApi from '../(feature)/services/evaluationItem';
import type { EvaluationItemType } from '../(feature)/services/type';

const router = useRouter();
const route = useRoute();
const loading = ref(false);
const categoryData = ref<EvaluationItemType.Category.Detail[]>([]);
const showCategoryModal = ref(false);
const body = ref<EvaluationItemType.Item.Create>({
  name: '',
  score: 0,
  isUsed: true,
  remark: '',
  evaluationCategoryIdx: 0,
});

const isEditMode = computed(() => {
  return route.query.edit === 'true' && route.query.idx;
});

const evaluationItemIdx = computed(() => {
  return route.query.idx ? Number(route.query.idx) : null;
});

// Format score with commas for display
const formattedScore = computed(() => {
  if (!body.value.score) return '';
  return body.value.score.toLocaleString();
});

onBeforeMount(async () => {
  await getCategoryList();

  if (isEditMode.value && evaluationItemIdx.value) {
    try {
      const { data } = await EvaluationItemServiceApi.findByIdx(evaluationItemIdx.value);

      const item = data.data;

      if (item) {
        body.value = {
          idx: item.idx,
          name: item.name,
          score: item.score,
          isUsed: item.isUsed,
          remark: item.remark,
          evaluationCategoryIdx: item.evaluationCategoryIdx,
        };
      }
    } catch (error) {
      console.error('Failed to load evaluation item data:', error);
      alert('평가항목 정보를 불러오는데 실패했습니다.');
    }
  }
});

const getCategoryList = async () => {
  try {
    const { data } = await EvaluationCategoryServiceApi.findAll();
    categoryData.value = data.data;
  } catch (error) {
    console.error('Failed to load category data:', error);
    alert('카테고리 정보를 불러오는데 실패했습니다.');
  }
};

const handleSubmit = async () => {
  if (!validateForm()) {
    return;
  }

  loading.value = true;
  try {
    if (isEditMode.value) {
      await EvaluationItemServiceApi.update(body.value);
      alert('수정 완료되었습니다.');
    } else {
      await EvaluationItemServiceApi.save(body.value);
      alert('평가항목이 생성되었습니다.');
    }

    router.push('/evaluation-item/list');
  } catch (error) {
    console.error('Submit error:', error);
    alert('저장 중 오류가 발생했습니다.');
  } finally {
    loading.value = false;
  }
};

const validateForm = (): boolean => {
  if (!body.value.name?.trim()) {
    alert('평가항목명을 입력해주세요.');
    return false;
  }

  if (!body.value.evaluationCategoryIdx || body.value.evaluationCategoryIdx === 0) {
    alert('카테고리를 선택해주세요.');
    return false;
  }

  if (!body.value.score || body.value.score <= 0) {
    alert('배점을 입력해주세요.');
    return false;
  }

  if (body.value.score > 100) {
    alert('배점은 100점을 초과할 수 없습니다.');
    return false;
  }

  return true;
};

const handleOpenCategoryModal = () => {
  showCategoryModal.value = true;
};

const handleCloseCategoryModal = () => {
  showCategoryModal.value = false;
};

const handleCategoryCreated = async (newCategory: EvaluationItemType.Category.Create) => {
  console.log('handleCategoryCreated called with:', newCategory);
  try {
    const res = await EvaluationCategoryServiceApi.save(newCategory);
    console.log('API response:', res);

    if (res.status === 200) {
      alert('카테고리가 생성되었습니다.');

      await getCategoryList();

      const createdCategory = categoryData.value.find((cat) => cat.name === newCategory.name);
      if (createdCategory) {
        body.value.evaluationCategoryIdx = createdCategory.idx;
      }
    }
  } catch (error) {
    console.error('Failed to create category:', error);
    alert('카테고리 생성 중 오류가 발생했습니다.');
  }
};

const handleScoreInput = (event: Event) => {
  const target = event.target as HTMLInputElement;
  const value = target.value;
  const numericValue = value.replace(/[^\d]/g, '');

  body.value.score = numericValue ? Number(numericValue) : 0;
};
</script>

<template>
  <div class="container-fluid py-4">
    <h2 class="mb-4">{{ isEditMode ? '평가항목 수정' : '평가항목 등록' }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="row g-3">
        <div class="col-md-6">
          <label for="evaluationName" class="form-label">평가 항목명</label>
          <input
            id="evaluationName"
            v-model="body.name"
            type="text"
            class="form-control"
            placeholder="예: 신제품 아이디어 기여도"
            required
          />
        </div>

        <div class="col-md-6">
          <div class="d-flex justify-content-between align-items-center mb-2">
            <label for="category" class="form-label mb-0">카테고리</label>
            <button
              type="button"
              class="btn btn-outline-secondary btn-sm"
              @click="handleOpenCategoryModal"
              style="--bs-btn-padding-y: 0.25rem; --bs-btn-padding-x: 0.5rem; --bs-btn-font-size: 0.75rem"
            >
              + 새 카테고리
            </button>
          </div>
          <select id="category" v-model="body.evaluationCategoryIdx" class="form-select" required>
            <option value="0">선택</option>
            <option v-for="item in categoryData" :key="item.idx" :value="item.idx">
              {{ item.name }}
            </option>
          </select>
        </div>

        <!-- 배점 (점수) -->
        <div class="col-md-6">
          <label for="score" class="form-label">배점 (점수)</label>
          <input
            id="score"
            :value="formattedScore"
            @input="handleScoreInput"
            type="text"
            class="form-control"
            placeholder="예: 10, 20"
            required
          />
        </div>

        <!-- 사용 여부 -->
        <div class="col-md-6">
          <label for="isUsed" class="form-label">사용 여부</label>
          <select id="isUsed" v-model="body.isUsed" class="form-select" required>
            <option :value="true">사용</option>
            <option :value="false">미사용</option>
          </select>
        </div>

        <!-- 비고 -->
        <div class="col-12">
          <label for="remark" class="form-label">비고</label>
          <textarea
            id="remark"
            v-model="body.remark"
            class="form-control"
            rows="3"
            placeholder="평가 기준 또는 설명 입력"
          ></textarea>
        </div>
      </div>

      <div class="form-actions mt-4 d-flex justify-content-end gap-2">
        <button type="button" class="btn btn-secondary" @click="router.push('/evaluation-item/list')">목록</button>
        <button type="button" class="btn btn-primary" @click="handleSubmit" :disabled="loading">
          <span v-if="loading" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
          {{ isEditMode ? '수정' : '저장' }}
        </button>
      </div>
    </form>

    <!-- Category Modal -->
    <CategoryModal :show="showCategoryModal" @close="handleCloseCategoryModal" @handleCreated="handleCategoryCreated" />
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
