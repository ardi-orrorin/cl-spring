<script setup lang="ts">
</script>

<template>
  <div class="container-fluid">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
      <h1 class="h2">평가 조회</h1>
    </div>

    <div class="card">
      <div class="card-header">
        <h5 class="mb-0">평가 목록</h5>
      </div>
      <div class="card-body">
        <div class="row mb-3">
          <div class="col-md-4">
            <label for="searchType" class="form-label">검색 조건</label>
            <select class="form-select" id="searchType">
              <option value="all">전체</option>
              <option value="employee">직원명</option>
              <option value="period">평가 기간</option>
              <option value="status">평가 상태</option>
            </select>
          </div>
          <div class="col-md-6">
            <label for="searchKeyword" class="form-label">검색어</label>
            <input type="text" class="form-control" id="searchKeyword" placeholder="검색어를 입력하세요">
          </div>
          <div class="col-md-2 d-flex align-items-end">
            <button class="btn btn-primary w-100">
              <i class="bi bi-search me-1"></i>
              검색
            </button>
          </div>
        </div>

        <div class="table-responsive">
          <table class="table table-striped table-hover">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">직원명</th>
                <th scope="col">부서</th>
                <th scope="col">평가 기간</th>
                <th scope="col">평가 유형</th>
                <th scope="col">총점</th>
                <th scope="col">평가 상태</th>
                <th scope="col">평가일</th>
                <th scope="col">액션</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td colspan="9" class="text-center text-muted">등록된 평가가 없습니다.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.table th {
  background-color: #f8f9fa;
}
</style>