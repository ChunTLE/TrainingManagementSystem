import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/store.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      components: {
        MainComponment: () => import('../views/login.vue')
      }
    },
    {
      path: '/register',
      name: 'register',
      components: {
        MainComponment: () => import('../views/register.vue')
      }
    },
    {
      path: '/',
      name: 'layout',
      components: {
        MainComponment: () => import('../components/Layout.vue')
      },
      children: [
        {
          path: '',
          name: 'home',
          components: {
            MainComponment: () => import('../views/index.vue')
          }
        },
        {
          path: 'student',
          name: 'student',
          components: {
            MainComponment: () => import('../views/student.vue')
          }
        },
        {
          path: 'study',
          name: 'study',
          components: {
            MainComponment: () => import('../views/study.vue')
          }
        }
      ]
    }
  ]
})

export default router
