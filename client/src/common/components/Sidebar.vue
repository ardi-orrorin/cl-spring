<script setup lang="ts">
import { ref, onMounted } from 'vue'

const isCollapsed = ref(false)

// localStorage에서 상태 복원
onMounted(() => {
  const saved = localStorage.getItem('sidebar-collapsed')
  isCollapsed.value = saved === 'true'
})

// 사이드바 토글 함수
const toggleSidebar = () => {
  isCollapsed.value = !isCollapsed.value
  localStorage.setItem('sidebar-collapsed', isCollapsed.value.toString())
}

// 부모 컴포넌트에서 상태를 사용할 수 있도록 expose
defineExpose({
  isCollapsed
})
</script>

<template>
  <nav :class="[
    'bg-light border-end position-fixed top-0 start-0 h-100',
    isCollapsed ? 'sidebar-collapsed' : 'sidebar-expanded'
  ]">
    <div class="h-100 overflow-auto">
      <!-- 토글 버튼 -->
      <div class="d-flex justify-content-between align-items-center p-3 border-bottom">
        <h6 v-show="!isCollapsed" class="text-muted mb-0 fw-bold">메뉴</h6>
        <button 
          type="button" 
          class="btn btn-outline-secondary btn-sm ms-auto" 
          @click="toggleSidebar"
          :title="isCollapsed ? '사이드바 펼치기' : '사이드바 접기'"
        >
          <i :class="isCollapsed ? 'bi bi-chevron-right' : 'bi bi-chevron-left'"></i>
        </button>
      </div>
      <ul class="nav flex-column px-2 pt-3">
        <li class="nav-item mb-1">
          <RouterLink to="/" :class="[
            'nav-link rounded d-flex align-items-center',
            isCollapsed ? 'justify-content-center px-2' : 'px-3'
          ]" active-class="active">
            <i class="bi bi-house-door"></i>
            <span v-show="!isCollapsed" class="ms-2">홈</span>
          </RouterLink>
        </li>
        
        <li v-show="!isCollapsed" class="nav-item mt-4 mb-2">
          <h6 class="sidebar-heading px-3 text-muted text-uppercase fs-6 fw-bold">
            직원 관리
          </h6>
        </li>
        <li class="nav-item mb-1">
          <RouterLink to="/employee/list" :class="[
            'nav-link rounded d-flex align-items-center',
            isCollapsed ? 'justify-content-center px-2' : 'px-3 ms-3'
          ]" active-class="active">
            <i class="bi bi-people"></i>
            <span v-show="!isCollapsed" class="ms-2">직원 조회</span>
          </RouterLink>
        </li>
        <li class="nav-item mb-1">
          <RouterLink to="/employee/create" :class="[
            'nav-link rounded d-flex align-items-center',
            isCollapsed ? 'justify-content-center px-2' : 'px-3 ms-3'
          ]" active-class="active">
            <i class="bi bi-person-plus"></i>
            <span v-show="!isCollapsed" class="ms-2">직원 생성</span>
          </RouterLink>
        </li>
        
        <li v-show="!isCollapsed" class="nav-item mt-4 mb-2">
          <h6 class="sidebar-heading px-3 text-muted text-uppercase fs-6 fw-bold">
            평가 항목 관리
          </h6>
        </li>
        <li class="nav-item mb-1">
          <RouterLink to="/evaluation-item/list" :class="[
            'nav-link rounded d-flex align-items-center',
            isCollapsed ? 'justify-content-center px-2' : 'px-3 ms-3'
          ]" active-class="active">
            <i class="bi bi-clipboard-data"></i>
            <span v-show="!isCollapsed" class="ms-2">평가 항목 조회</span>
          </RouterLink>
        </li>
        <li class="nav-item mb-1">
          <RouterLink to="/evaluation-item/create" :class="[
            'nav-link rounded d-flex align-items-center',
            isCollapsed ? 'justify-content-center px-2' : 'px-3 ms-3'
          ]" active-class="active">
            <i class="bi bi-clipboard-plus"></i>
            <span v-show="!isCollapsed" class="ms-2">평가 항목 등록</span>
          </RouterLink>
        </li>
        
        <li v-show="!isCollapsed" class="nav-item mt-4 mb-2">
          <h6 class="sidebar-heading px-3 text-muted text-uppercase fs-6 fw-bold">
            평가
          </h6>
        </li>
        <li class="nav-item mb-1">
          <RouterLink to="/evaluation/employee" :class="[
            'nav-link rounded d-flex align-items-center',
            isCollapsed ? 'justify-content-center px-2' : 'px-3 ms-3'
          ]" active-class="active">
            <i class="bi bi-clipboard-data"></i>
            <span v-show="!isCollapsed" class="ms-2">사원 평가</span>
          </RouterLink>
        </li>
        
        <li v-show="!isCollapsed" class="nav-item mt-4 mb-2">
          <h6 class="sidebar-heading px-3 text-muted text-uppercase fs-6 fw-bold">
            보고서
          </h6>
        </li>
        <li class="nav-item mb-1">
          <RouterLink to="/report/employee" :class="[
            'nav-link rounded d-flex align-items-center',
            isCollapsed ? 'justify-content-center px-2' : 'px-3 ms-3'
          ]" active-class="active">
            <i class="bi bi-file-earmark-bar-graph"></i>
            <span v-show="!isCollapsed" class="ms-2">사원 평가 보고서</span>
          </RouterLink>
        </li>
      </ul>
    </div>
  </nav>
</template>

<style scoped>
.sidebar-expanded {
  width: 250px;
  transition: width 0.3s ease-in-out;
  z-index: 1000;
}

.sidebar-collapsed {
  width: 80px;
  transition: width 0.3s ease-in-out;
  z-index: 1000;
}

.nav-link {
  color: var(--bs-secondary);
  transition: all 0.15s ease-in-out;
}

.nav-link:hover {
  color: var(--bs-dark);
  background-color: var(--bs-light);
}

.nav-link.active {
  color: var(--bs-primary);
  background-color: rgba(var(--bs-primary-rgb), 0.1);
}

.sidebar-heading {
  font-size: 0.75rem;
}
</style>
