import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('./Index.vue'),
      children: [
        {
          path: 'employee',
          name: 'employee',
          redirect: '/employee/list',
          children: [
            {
              path: 'list',
              name: 'employee-list',
              component: () => import('./employee/list/Index.vue'),
            },
            {
              path: 'create',
              name: 'employee-create',
              component: () => import('./employee/create/Index.vue'),
            },
          ],
        },
        {
          path: 'evaluation-item',
          name: 'evaluation-item',
          redirect: '/evaluation-item/list',
          children: [
            {
              path: 'list',
              name: 'evaluation-item-list',
              component: () => import('./evaluationItem/list/Index.vue'),
            },
            {
              path: 'create',
              name: 'evaluation-item-create',
              component: () => import('./evaluationItem/create/Index.vue'),
            },
          ],
        },
        {
          path: 'evaluation',
          name: 'evaluation',
          redirect: '/evaluation/employee',
          children: [
            {
              path: 'employee',
              name: 'evaluation-employee',
              component: () => import('./evaluation/employee/Index.vue'),
            },
          ],
        },
        {
          path: 'report',
          name: 'report',
          redirect: '/report/employee',
          children: [
            {
              path: 'employee',
              name: 'report-employee',
              component: () => import('./report/employee/Index.vue'),
            },
          ],
        },
      ],
    },
  ],
});

export default router;
