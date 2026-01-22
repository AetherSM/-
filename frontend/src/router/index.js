import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import ErrandList from '../views/ErrandList.vue'
import CreateErrand from '../views/CreateErrand.vue'
import MyOrders from '../views/MyOrders.vue'
import Wallet from '../views/Wallet.vue'
import ProductList from '../views/ProductList.vue'
import ProductDetail from '../views/ProductDetail.vue'
import ProductCreate from '../views/ProductCreate.vue'
import Addresses from '../views/Addresses.vue'
import Coupons from '../views/Coupons.vue'

const routes = [
  { path: '/', redirect: '/errands' },
  { path: '/login', component: Login },
  { path: '/errands', component: ErrandList, meta: { requiresAuth: true } },
  { path: '/errands/create', component: CreateErrand, meta: { requiresAuth: true } },
  { path: '/orders', component: MyOrders, meta: { requiresAuth: true } },
  { path: '/wallet', component: Wallet, meta: { requiresAuth: true } },
  { path: '/addresses', component: Addresses, meta: { requiresAuth: true } },
  { path: '/coupons', component: Coupons, meta: { requiresAuth: true } }
  ,
  { path: '/shop', component: ProductList, meta: { requiresAuth: true } },
  { path: '/shop/create', component: ProductCreate, meta: { requiresAuth: true } },
  { path: '/shop/:id', component: ProductDetail, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && to.meta?.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
