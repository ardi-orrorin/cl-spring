<script setup lang="ts">
defineProps<{
  title: string;
  value: string;
}>();
</script>

<template>
  <div class="col-lg-3 col-md-6">
    <div class="card h-100 border-1">
      <div class="card-body text-center">
        <h6 class="card-subtitle mb-2 text-muted">{{ title }}</h6>
        <h4 class="card-title">{{ value }}</h4>
      </div>
    </div>
  </div>
</template>
