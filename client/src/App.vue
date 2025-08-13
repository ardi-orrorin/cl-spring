<script setup lang="ts">
import { RouterView } from 'vue-router';
import { ref, onMounted } from 'vue';
import Sidebar from './common/components/Sidebar.vue';

const sidebarRef = ref()
const isCollapsed = ref(false)

// localStorage에서 상태 복원 (사이드바와 동기화)
onMounted(() => {
  const saved = localStorage.getItem('sidebar-collapsed')
  isCollapsed.value = saved === 'true'
  
  // localStorage 변경 감지하여 상태 동기화
  window.addEventListener('storage', (e) => {
    if (e.key === 'sidebar-collapsed') {
      isCollapsed.value = e.newValue === 'true'
    }
  })
  
  // 사이드바 상태 변경 감지
  const checkSidebarState = () => {
    const saved = localStorage.getItem('sidebar-collapsed')
    isCollapsed.value = saved === 'true'
  }
  
  // 주기적으로 확인 (localStorage 이벤트가 같은 탭에서 발생하지 않으므로)
  setInterval(checkSidebarState, 100)
})

// 사용하지 않는 코드 제거됨
</script>

<template>
  <div class="d-flex">
    <Sidebar ref="sidebarRef" />
    <main :class="[
      'flex-grow-1 p-4 min-vh-100',
      isCollapsed ? 'ms-sidebar-collapsed' : 'ms-sidebar-expanded'
    ]">
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
.ms-sidebar-expanded {
  margin-left: 250px;
  transition: margin-left 0.3s ease-in-out;
}

.ms-sidebar-collapsed {
  margin-left: 80px;
  transition: margin-left 0.3s ease-in-out;
}

@media (max-width: 767.98px) {
  .ms-sidebar-expanded,
  .ms-sidebar-collapsed {
    margin-left: 0 !important;
  }
}
</style>
