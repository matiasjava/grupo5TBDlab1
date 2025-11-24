import { createRouter, createWebHistory } from 'vue-router'

import LoginView from '@/views/LoginView.vue'
import HomeView from '@/views/HomeView.vue'
import RegisterView from '@/views/RegisterView.vue'

const requireAuth = (to, from, next) => {
  const token = localStorage.getItem("token");
  !token ? next("/login") : next();
};

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: LoginView },
    { path: '/register', component: RegisterView },
    { path: '/', component: HomeView, beforeEnter: requireAuth }
  ]
})

export default router


