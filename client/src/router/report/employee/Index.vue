<script setup lang="ts">
import { EmployeeServiceType } from '@/router/employee/(feature)/serivces/type';
import {
  BarElement,
  CategoryScale,
  Chart as ChartJS,
  Filler,
  Legend,
  LinearScale,
  LineElement,
  PointElement,
  Title,
  Tooltip,
} from 'chart.js';
import { onBeforeMount, ref } from 'vue';
import { Bar, Line } from 'vue-chartjs';
import ReportServiceApi from '../(feature)/services/api';
import type { ReportType } from '../(feature)/services/type';

// Chart.js 등록
ChartJS.register(CategoryScale, LinearScale, BarElement, LineElement, PointElement, Title, Tooltip, Legend, Filler);

const reportData = ref<ReportType.Report[]>([]);
const loading = ref(false);

// KPI 데이터
const kpiData = ref({
  currentEmployees: 6,
  retiredEmployees: 1,
  averageIncreaseRate: 6.2,
  totalSalaryChange: {
    from: 250000000,
    to: 266390000,
  },
});

onBeforeMount(() => {
  refreshData();
});

const loadReportData = async () => {
  loading.value = true;
  try {
    const response = await ReportServiceApi.findAllUserReport();
    reportData.value = response.data.data || [];
    calculateKPIs();
  } catch (error) {
    console.error('리포트 데이터 로딩 실패:', error);
  } finally {
    loading.value = false;
  }
};

const calculateKPIs = () => {
  if (reportData.value.length === 0) return;

  const totalCurrent = reportData.value.reduce((sum, emp) => sum + emp.currentAnnualSalary, 0);
  const totalNext = reportData.value.reduce((sum, emp) => sum + emp.nextAnnualSalary, 0);
  const avgIncrease = reportData.value.reduce((sum, emp) => sum + emp.increaseRate, 0) / reportData.value.length;

  const retiredCount = reportData.value.filter(
    (emp) => emp.employmentStatus === EmployeeServiceType.EmploymentStatus.RETIRED,
  ).length;

  const workingCount = reportData.value.filter(
    (emp) => emp.employmentStatus !== EmployeeServiceType.EmploymentStatus.RETIRED,
  ).length;

  kpiData.value = {
    currentEmployees: workingCount,
    retiredEmployees: retiredCount,
    averageIncreaseRate: Math.round(avgIncrease * 10) / 10,
    totalSalaryChange: {
      from: totalCurrent,
      to: totalNext,
    },
  };
};

// 차트 데이터 생성
const salaryChartData = ref({
  labels: [] as string[],
  datasets: [
    {
      label: '이전 연봉',
      backgroundColor: '#87CEEB',
      data: [] as number[],
    },
    {
      label: '상승 후 연봉',
      backgroundColor: '#90EE90',
      data: [] as number[],
    },
  ],
});

const performanceChartData = ref({
  labels: [] as string[],
  datasets: [
    {
      label: '평가 점수',
      borderColor: '#FF69B4',
      backgroundColor: 'rgba(255, 105, 180, 0.1)',
      data: [] as number[],
      fill: true,
      tension: 0.4,
    },
  ],
});

// 차트 옵션
const salaryChartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'top' as const,
    },
    title: {
      display: false,
    },
  },
  scales: {
    y: {
      beginAtZero: true,
      ticks: {
        callback: function (value: any) {
          return '₩' + value / 1000000 + 'M';
        },
      },
    },
  },
};

const performanceChartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      display: false,
    },
    title: {
      display: false,
    },
  },
  scales: {
    y: {
      beginAtZero: true,
      max: 100,
    },
  },
};

// 차트 데이터 업데이트
const updateChartData = () => {
  if (reportData.value.length === 0) return;

  salaryChartData.value.labels = reportData.value.map((emp) => emp.employeeName);
  salaryChartData.value.datasets[0].data = reportData.value.map((emp) => emp.currentAnnualSalary);
  salaryChartData.value.datasets[1].data = reportData.value.map((emp) => emp.nextAnnualSalary);

  performanceChartData.value.labels = reportData.value.map((emp) => emp.employeeName);
  performanceChartData.value.datasets[0].data = reportData.value.map((emp) => emp.totalScore);
};

const topPerformers = ref<ReportType.Report[]>([]);
const bottomPerformers = ref<ReportType.Report[]>([]);

const calculateRankings = () => {
  if (reportData.value.length === 0) return;

  const sorted = [...reportData.value].sort((a, b) => b.totalScore - a.totalScore);

  const topCount = Math.ceil(sorted.length * 0.1);
  const bottomCount = Math.ceil(sorted.length * 0.1);

  topPerformers.value = sorted.slice(0, topCount);
  bottomPerformers.value = sorted.slice(-bottomCount);
};

const refreshData = async () => {
  await loadReportData();
  updateChartData();
  calculateRankings();
};

// 초기 데이터 로드
</script>

<template>
  <div class="container-fluid py-4">
    <!-- 제목 -->
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2 class="fw-bold text-dark">사원 평가 보고서</h2>
    </div>

    <!-- KPI 카드 -->
    <div class="row g-4 mb-4">
      <div class="col-lg-3 col-md-6">
        <div class="card h-100 border-0">
          <div class="card-body text-center">
            <h6 class="card-subtitle mb-2 text-muted">근무 인원수</h6>
            <h3 class="card-title text-primary fw-bold">{{ kpiData.currentEmployees }} 명</h3>
          </div>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="card h-100 border-0">
          <div class="card-body text-center">
            <h6 class="card-subtitle mb-2 text-muted">퇴직 인원수</h6>
            <h3 class="card-title text-warning fw-bold">{{ kpiData.retiredEmployees }} 명</h3>
          </div>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="card h-100 border-0">
          <div class="card-body text-center">
            <h6 class="card-subtitle mb-2 text-muted">평균 연봉 상승률</h6>
            <h3 class="card-title text-success fw-bold">{{ kpiData.averageIncreaseRate }}%</h3>
          </div>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="card h-100 border-0">
          <div class="card-body text-center">
            <h6 class="card-subtitle mb-2 text-muted">총 인건비 변화</h6>
            <div class="d-flex justify-content-center align-items-center">
              <small class="text-muted me-2">₩{{ (kpiData.totalSalaryChange.from / 1000000).toFixed(0) }}M</small>
              <i class="bi bi-arrow-right text-muted"></i>
              <h4 class="card-title text-info fw-bold ms-2 mb-0">
                ₩{{ (kpiData.totalSalaryChange.to / 1000000).toFixed(0) }}M
              </h4>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row g-4 mb-4">
      <div class="col-lg-6">
        <div class="card h-100 border-0">
          <div class="card-header bg-white border-0">
            <h5 class="card-title mb-0 fw-bold">연봉 비교</h5>
            <small class="text-muted">이전 연봉 vs 상승 후 연봉</small>
          </div>
          <div class="card-body">
            <div style="height: 300px">
              <Bar v-if="salaryChartData.labels.length > 0" :data="salaryChartData" :options="salaryChartOptions" />
              <div v-else class="d-flex justify-content-center align-items-center h-100">
                <div class="text-center">
                  <div v-if="loading" class="spinner-border text-primary"></div>
                  <p v-else class="text-muted">데이터가 없습니다.</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="col-lg-6">
        <div class="card h-100 border-0">
          <div class="card-header bg-white border-0">
            <h5 class="card-title mb-0 fw-bold">성과 트렌드</h5>
            <small class="text-muted">평가 점수 분포</small>
          </div>
          <div class="card-body">
            <div style="height: 300px">
              <Line
                v-if="performanceChartData.labels.length > 0"
                :data="performanceChartData"
                :options="performanceChartOptions"
              />
              <div v-else class="d-flex justify-content-center align-items-center h-100">
                <div class="text-center">
                  <div v-if="loading" class="spinner-border text-primary"></div>
                  <p v-else class="text-muted">데이터가 없습니다.</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row g-4">
      <!-- 우수사원 섹션 -->
      <div class="col-lg-6">
        <div class="card border-1 py-3">
          <div class="d-flex align-items-center justify-content-center">
            <div class="text-center">
              <small class="card-title mb-0">우수사원 (최고 점수)</small>
              <h5 class="text-muted" v-if="topPerformers.length > 0">
                {{ topPerformers[0].employeeName }} ({{ topPerformers[0].totalScore }}점)
              </h5>
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
                <tr
                  v-for="(employee, index) in topPerformers"
                  :key="employee.employeeEvaluationIdx"
                  class="align-middle"
                >
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

      <!-- 최하사원 섹션 -->
      <div class="col-lg-6">
        <div class="card border-1 py-3">
          <div class="d-flex align-items-center justify-content-center">
            <div class="text-center">
              <small class="mb-0">최하사원 (최저 점수)</small>
              <h5 class="text-muted" v-if="bottomPerformers.length > 0">
                {{ bottomPerformers[0].employeeName }} ({{ bottomPerformers[0].totalScore }}점)
              </h5>
            </div>
          </div>
        </div>
        <div class="mt-4 card-body">
          <h6 class="fw-bold mb-3">하위 10% 사원 리스트</h6>
          <div class="table-responsive">
            <table class="table table-sm table-bordered table-hover">
              <thead class="table-secondary">
                <tr>
                  <th>순위</th>
                  <th>사원명</th>
                  <th>점수</th>
                  <th>상승 연봉</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(employee, index) in bottomPerformers"
                  :key="employee.employeeEvaluationIdx"
                  class="align-middle"
                >
                  <td>
                    <span>{{ index + 1 }}</span>
                  </td>
                  <td>{{ employee.employeeName }}</td>
                  <td>
                    <span>{{ employee.totalScore }}점</span>
                  </td>
                  <td>₩{{ employee.nextAnnualSalary.toLocaleString() }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
