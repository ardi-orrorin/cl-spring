<script lang="ts" setup>
defineProps({
  isCollapsed: {
    type: Boolean,
    default: false,
  },
  label: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <li v-show="!isCollapsed" class="nav-item mt-4 mb-2">
    <h6 class="sidebar-heading px-3 text-muted text-uppercase fs-6 fw-bold">{{ label }}</h6>
  </li>
</template>

<style scoped>
.sidebar-heading {
  font-size: 0.75rem;
}
</style>
