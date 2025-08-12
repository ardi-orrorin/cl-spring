<script setup lang="ts">
import { RouterView } from 'vue-router';
import Sidebar from './common/component/Sidebar.vue';
</script>

<template>
  <div class="container-fluid">
    <div class="row">
      <Sidebar />

      <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style scoped></style>
