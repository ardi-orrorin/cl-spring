<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterView } from 'vue-router';
import Sidebar from './common/components/Sidebar.vue';

const sidebarRef = ref();
const isCollapsed = ref(false);

onMounted(() => {
  const saved = localStorage.getItem('sidebar-collapsed');
  isCollapsed.value = saved === 'true';

  window.addEventListener('storage', (e) => {
    if (e.key === 'sidebar-collapsed') {
      isCollapsed.value = e.newValue === 'true';
    }
  });

  const checkSidebarState = () => {
    const saved = localStorage.getItem('sidebar-collapsed');
    isCollapsed.value = saved === 'true';
  };

  setInterval(checkSidebarState, 100);
});
</script>

<template>
  <div class="d-flex">
    <Sidebar ref="sidebarRef" />
    <main :class="['flex-grow-1 p-4 min-vh-100', isCollapsed ? 'ms-sidebar-collapsed' : 'ms-sidebar-expanded']">
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
