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
          path: 'evaluation',
          name: 'evaluation',
          redirect: '/evaluation/list',
          children: [
            {
              path: 'list',
              name: 'evaluation-list',
              component: () => import('./evaluation/list/Index.vue'),
            },
            {
              path: 'create',
              name: 'evaluation-create',
              component: () => import('./evaluation/create/Index.vue'),
            },
          ],
        },
      ],
    },
  ],
});

export default router;
