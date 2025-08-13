<script setup lang="ts">
import { onMounted, ref } from 'vue';
import NavHeader from './NavHeader.vue';
import NavItem from './NavItem.vue';

const isCollapsed = ref(false);

onMounted(() => {
  const saved = localStorage.getItem('sidebar-collapsed');
  isCollapsed.value = saved === 'true';
});

const toggleSidebar = () => {
  isCollapsed.value = !isCollapsed.value;
  localStorage.setItem('sidebar-collapsed', isCollapsed.value.toString());
};

defineExpose({
  isCollapsed,
});
</script>

<template>
  <nav
    :class="[
      'bg-light border-end position-fixed top-0 start-0 h-100',
      isCollapsed ? 'sidebar-collapsed' : 'sidebar-expanded',
    ]"
  >
    <div class="h-100 overflow-auto">
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
        <NavItem to="/" icon="bi bi-house-door" label="홈" :is-collapsed="isCollapsed" />

        <NavHeader :is-collapsed="isCollapsed" label="직원 관리" />

        <NavItem to="/employee/list" icon="bi bi-people" label="직원 조회" :is-collapsed="isCollapsed" />
        <NavItem to="/employee/create" icon="bi bi-person-plus" label="직원 생성" :is-collapsed="isCollapsed" />

        <NavHeader :is-collapsed="isCollapsed" label="평가 항목 관리" />

        <NavItem
          to="/evaluation-item/list"
          icon="bi bi-clipboard-data"
          label="평가 항목 조회"
          :is-collapsed="isCollapsed"
        />
        <NavItem
          to="/evaluation-item/create"
          icon="bi bi-clipboard-plus"
          label="평가 항목 등록"
          :is-collapsed="isCollapsed"
        />

        <NavHeader :is-collapsed="isCollapsed" label="평가" />

        <NavItem to="/evaluation/employee" icon="bi bi-clipboard-data" label="사원 평가" :is-collapsed="isCollapsed" />

        <NavHeader :is-collapsed="isCollapsed" label="보고서" />

        <NavItem
          to="/report/employee"
          icon="bi bi-file-earmark-bar-graph"
          label="사원 평가 보고서"
          :is-collapsed="isCollapsed"
        />
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
</style>
