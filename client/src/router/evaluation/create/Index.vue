<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const formData = ref({
  employeeName: '',
  department: '',
  evaluationPeriod: '',
  evaluationType: '',
  workQuality: 0,
  productivity: 0,
  communication: 0,
  teamwork: 0,
  leadership: 0,
  comments: ''
});

const calculateTotal = () => {
  const { workQuality, productivity, communication, teamwork, leadership } = formData.value;
  return workQuality + productivity + communication + teamwork + leadership;
};

const handleSubmit = () => {
  // TODO: API 호출 로직 추가
  console.log('평가 등록:', formData.value);
  console.log('총점:', calculateTotal());
  // 성공 후 목록 페이지로 이동
  router.push('/evaluation/list');
};

const handleCancel = () => {
  router.push('/evaluation/list');
};
</script>

<template>
  <div class="container-fluid">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
      <h1 class="h2">평가 등록</h1>
    </div>

    <div class="card">
      <div class="card-header">
        <h5 class="mb-0">새 평가 등록</h5>
      </div>
      <div class="card-body">
        <form @submit.prevent="handleSubmit">
          <!-- 기본 정보 -->
          <div class="row mb-4">
            <div class="col-12">
              <h6 class="text-primary mb-3">기본 정보</h6>
            </div>
            <div class="col-md-6 mb-3">
              <label for="employeeName" class="form-label">직원명 <span class="text-danger">*</span></label>
              <select 
                class="form-select" 
                id="employeeName" 
                v-model="formData.employeeName"
                required
              >
                <option value="">직원을 선택하세요</option>
                <option value="김철수">김철수</option>
                <option value="이영희">이영희</option>
                <option value="박민수">박민수</option>
                <option value="최지은">최지은</option>
              </select>
            </div>
            <div class="col-md-6 mb-3">
              <label for="department" class="form-label">부서 <span class="text-danger">*</span></label>
              <select 
                class="form-select" 
                id="department" 
                v-model="formData.department"
                required
              >
                <option value="">부서를 선택하세요</option>
                <option value="개발팀">개발팀</option>
                <option value="디자인팀">디자인팀</option>
                <option value="기획팀">기획팀</option>
                <option value="마케팅팀">마케팅팀</option>
                <option value="인사팀">인사팀</option>
              </select>
            </div>
            <div class="col-md-6 mb-3">
              <label for="evaluationPeriod" class="form-label">평가 기간 <span class="text-danger">*</span></label>
              <select 
                class="form-select" 
                id="evaluationPeriod" 
                v-model="formData.evaluationPeriod"
                required
              >
                <option value="">평가 기간을 선택하세요</option>
                <option value="2024년 1분기">2024년 1분기</option>
                <option value="2024년 2분기">2024년 2분기</option>
                <option value="2024년 3분기">2024년 3분기</option>
                <option value="2024년 4분기">2024년 4분기</option>
              </select>
            </div>
            <div class="col-md-6 mb-3">
              <label for="evaluationType" class="form-label">평가 유형 <span class="text-danger">*</span></label>
              <select 
                class="form-select" 
                id="evaluationType" 
                v-model="formData.evaluationType"
                required
              >
                <option value="">평가 유형을 선택하세요</option>
                <option value="정기평가">정기평가</option>
                <option value="특별평가">특별평가</option>
                <option value="승진평가">승진평가</option>
              </select>
            </div>
          </div>

          <!-- 평가 항목 -->
          <div class="row mb-4">
            <div class="col-12">
              <h6 class="text-primary mb-3">평가 항목 (각 항목 최대 20점)</h6>
            </div>
            <div class="col-md-6 mb-3">
              <label for="workQuality" class="form-label">업무 품질</label>
              <div class="input-group">
                <input 
                  type="range" 
                  class="form-range me-3" 
                  id="workQuality" 
                  v-model.number="formData.workQuality"
                  min="0" 
                  max="20"
                />
                <span class="input-group-text">{{ formData.workQuality }}점</span>
              </div>
            </div>
            <div class="col-md-6 mb-3">
              <label for="productivity" class="form-label">생산성</label>
              <div class="input-group">
                <input 
                  type="range" 
                  class="form-range me-3" 
                  id="productivity" 
                  v-model.number="formData.productivity"
                  min="0" 
                  max="20"
                />
                <span class="input-group-text">{{ formData.productivity }}점</span>
              </div>
            </div>
            <div class="col-md-6 mb-3">
              <label for="communication" class="form-label">의사소통</label>
              <div class="input-group">
                <input 
                  type="range" 
                  class="form-range me-3" 
                  id="communication" 
                  v-model.number="formData.communication"
                  min="0" 
                  max="20"
                />
                <span class="input-group-text">{{ formData.communication }}점</span>
              </div>
            </div>
            <div class="col-md-6 mb-3">
              <label for="teamwork" class="form-label">팀워크</label>
              <div class="input-group">
                <input 
                  type="range" 
                  class="form-range me-3" 
                  id="teamwork" 
                  v-model.number="formData.teamwork"
                  min="0" 
                  max="20"
                />
                <span class="input-group-text">{{ formData.teamwork }}점</span>
              </div>
            </div>
            <div class="col-md-6 mb-3">
              <label for="leadership" class="form-label">리더십</label>
              <div class="input-group">
                <input 
                  type="range" 
                  class="form-range me-3" 
                  id="leadership" 
                  v-model.number="formData.leadership"
                  min="0" 
                  max="20"
                />
                <span class="input-group-text">{{ formData.leadership }}점</span>
              </div>
            </div>
            <div class="col-md-6 mb-3">
              <div class="card bg-light">
                <div class="card-body text-center">
                  <h5 class="card-title mb-0">총점</h5>
                  <h3 class="text-primary">{{ calculateTotal() }}점 / 100점</h3>
                </div>
              </div>
            </div>
          </div>

          <!-- 코멘트 -->
          <div class="row mb-4">
            <div class="col-12">
              <h6 class="text-primary mb-3">평가 의견</h6>
            </div>
            <div class="col-12 mb-3">
              <label for="comments" class="form-label">평가 코멘트</label>
              <textarea 
                class="form-control" 
                id="comments" 
                v-model="formData.comments"
                rows="4"
                placeholder="평가에 대한 상세 의견을 입력하세요..."
              ></textarea>
            </div>
          </div>

          <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">
              <i class="bi bi-check-circle me-1"></i>
              등록
            </button>
            <button type="button" class="btn btn-secondary" @click="handleCancel">
              <i class="bi bi-x-circle me-1"></i>
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.form-label {
  font-weight: 500;
}

.text-danger {
  font-size: 0.875rem;
}

.form-range {
  flex: 1;
}

.input-group .form-range {
  border: none;
  box-shadow: none;
}

.text-primary {
  color: #0d6efd !important;
}

.card.bg-light {
  border: 2px solid #e9ecef;
}
</style>