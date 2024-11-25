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
        },
        {
          path: 'changePwd',
          name: 'changePwd',
          components: {
            MainComponment: () => import('../views/changePwd.vue')
          }
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path === '/login' || to.path === '/register') {
    next()
  } else {
    if (userStore.token) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
