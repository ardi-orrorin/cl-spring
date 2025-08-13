<script lang="ts" setup>
defineProps({
  to: {
    type: String,
    required: true,
  },
  icon: {
    type: String,
    required: true,
  },
  label: {
    type: String,
    required: true,
  },
  isCollapsed: {
    type: Boolean,
    default: false,
  },
});
</script>

<template>
  <li class="nav-item mb-1">
    <RouterLink
      :to="to"
      :class="['nav-link rounded d-flex align-items-center', isCollapsed ? 'justify-content-center px-2' : 'px-3 ms-3']"
      active-class="active"
    >
      <i :class="icon"></i>
      <span v-show="!isCollapsed" class="ms-2">{{ label }}</span>
    </RouterLink>
  </li>
</template>

<style scoped>
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
</style>
