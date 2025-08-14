<script setup lang="ts">
import type { ReportType } from '../services/type';

defineProps<{ performers: ReportType.Report[] }>();
</script>

<template>
  <div class="col-lg-6" v-if="performers.length > 0">
    <div class="card border-1 py-3">
      <div class="d-flex align-items-center justify-content-center">
        <div class="text-center">
          <small class="card-title mb-0">우수사원 (최고 점수)</small>
          <h5 class="text-muted">{{ performers[0].employeeName }} ({{ performers[0].totalScore }}점)</h5>
        </div>
      </div>
    </div>
    <div class="mt-4 card-body">
      <h6 class="fw-bold mb-3">상위 10% 사원 리스트</h6>
      <div class="table-responsive">
        <table class="table table-sm table-hover table-bordered">
          <thead class="table-secondary">
            <tr>
              <th>순위</th>
              <th>사원명</th>
              <th>점수</th>
              <th>상승 연봉</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(employee, index) in performers" :key="employee.employeeEvaluationIdx" class="align-middle">
              <td>{{ index + 1 }}</td>
              <td>{{ employee.employeeName }}</td>
              <td>{{ employee.totalScore }}점</td>
              <td>₩{{ employee.nextAnnualSalary.toLocaleString() }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
