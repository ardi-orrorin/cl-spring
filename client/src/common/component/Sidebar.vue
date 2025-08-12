<script setup lang="ts"></script>

<template>
  <nav class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="position-sticky pt-3">
      <ul class="nav flex-column">
        <li class="nav-item">
          <RouterLink to="/" class="nav-link" active-class="active">
            <span class="bi bi-house-door me-2"></span>
            홈
          </RouterLink>
        </li>
        <li class="nav-item">
          <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
            <span>직원 관리</span>
          </h6>
          <ul class="nav flex-column ms-3">
            <li class="nav-item">
              <RouterLink to="/employee/list" class="nav-link" active-class="active">
                <span class="bi bi-people me-2"></span>
                직원 조회
              </RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink to="/employee/create" class="nav-link" active-class="active">
                <span class="bi bi-person-plus me-2"></span>
                직원 생성
              </RouterLink>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
            <span>평가 관리</span>
          </h6>
          <ul class="nav flex-column ms-3">
            <li class="nav-item">
              <RouterLink to="/evaluation/list" class="nav-link" active-class="active">
                <span class="bi bi-clipboard-data me-2"></span>
                평가 조회
              </RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink to="/evaluation/create" class="nav-link" active-class="active">
                <span class="bi bi-clipboard-plus me-2"></span>
                평가 등록
              </RouterLink>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>
</template>

<style scoped>
.sidebar {
  min-height: 100vh;
}

.sidebar-heading {
  font-size: 0.75rem;
  text-transform: uppercase;
}

.nav-link {
  color: #6c757d;
}

.nav-link:hover {
  color: #495057;
}

.nav-link.active {
  color: #71819a;
  background-color: rgba(13, 110, 253, 0.1);
  border-radius: 0.25rem;
}
</style>
