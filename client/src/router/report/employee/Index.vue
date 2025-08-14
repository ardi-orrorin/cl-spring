<script setup lang="ts">
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
import KpiCard from '../(feature)/components/KpiCard.vue';
import PerformersCard from '../(feature)/components/PerformersCard.vue';
import ReportServiceApi from '../(feature)/services/api';
import type { ReportType } from '../(feature)/services/type';

ChartJS.register(CategoryScale, LinearScale, BarElement, LineElement, PointElement, Title, Tooltip, Legend, Filler);

const reportData = ref<ReportType.EmployeeEvaluation[]>([]);
const resignCount = ref(0);
const topPerformers = ref<ReportType.EmployeeEvaluation[]>([]);
const bottomPerformers = ref<ReportType.EmployeeEvaluation[]>([]);

const loading = ref(false);

const kpiData = ref({
  currentEmployees: 0,
  retiredEmployees: 0,
  averageIncreaseRate: 0,
  totalSalaryChange: {
    from: 0,
    to: 0,
  },
});

onBeforeMount(() => {
  refreshData();
});

const loadReportData = async () => {
  loading.value = true;
  try {
    const response = await ReportServiceApi.findAllUserReport();
    reportData.value = response.data.data.employeeEvaluations || [];
    resignCount.value = response.data.data.resignCount;
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

  const retiredCount = resignCount.value;

  const workingCount = reportData.value.length;

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

const updateChartData = () => {
  if (reportData.value.length === 0) return;

  salaryChartData.value.labels = reportData.value.map((emp) => emp.employeeName);
  salaryChartData.value.datasets[0].data = reportData.value.map((emp) => emp.currentAnnualSalary);
  salaryChartData.value.datasets[1].data = reportData.value.map((emp) => emp.nextAnnualSalary);

  performanceChartData.value.labels = reportData.value.map((emp) => emp.employeeName);
  performanceChartData.value.datasets[0].data = reportData.value.map((emp) => emp.totalScore);
};

const calculateRankings = () => {
  if (reportData.value.length === 0) return;

  const sorted = [...reportData.value].sort((a, b) => b.totalScore - a.totalScore);

  const topCount = Math.ceil(sorted.length * 0.1);
  const bottomCount = Math.ceil(sorted.length * 0.1);

  topPerformers.value = sorted.slice(0, topCount);
  const topIds = topPerformers.value.map((emp) => emp.employeeEvaluationIdx);
  bottomPerformers.value = sorted.filter((emp) => !topIds.includes(emp.employeeEvaluationIdx)).slice(-bottomCount);
};

const refreshData = async () => {
  await loadReportData();
  updateChartData();
  calculateRankings();
};
</script>

<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2 class="fw-bold text-dark">사원 평가 보고서</h2>
    </div>

    <div class="row g-4 mb-4">
      <KpiCard title="근무 인원수" :value="kpiData.currentEmployees + ' 명'" />
      <KpiCard title="퇴직 인원수" :value="kpiData.retiredEmployees + ' 명'" />
      <KpiCard title="평균 연봉 상승률" :value="kpiData.averageIncreaseRate + '%'" />

      <div class="col-lg-3 col-md-6">
        <div class="card h-100 border-1">
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
      <PerformersCard :performers="topPerformers" />
      <PerformersCard :performers="bottomPerformers" />
    </div>
  </div>
</template>
