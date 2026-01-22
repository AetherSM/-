<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import http from './services/http'
const route = useRoute()
const router = useRouter()
const navAll = [
  { path: '/errands', label: '跑腿广场', roles: [1,2,3] },
  { path: '/errands/create', label: '发布跑腿', roles: [1] },           // 普通用户可发布
  { path: '/orders', label: '我的订单', roles: [1,2,3] },
  { path: '/wallet', label: '钱包', roles: [1,2,3] },
  { path: '/addresses', label: '地址管理', roles: [1,2,3] },
  { path: '/coupons', label: '优惠券', roles: [1,2,3] },
  { path: '/shop', label: '商品商城', roles: [1,2,3] },
  { path: '/shop/create', label: '发布商品', roles: [3] }               // 仅商家
]
const nav = ref(navAll)
const userType = ref(null)
const nickname = ref('')
const loggedIn = ref(false)
const refreshAuth = async () => {
  const token = localStorage.getItem('token')
  loggedIn.value = !!token
  const n = localStorage.getItem('nickname')
  const t = localStorage.getItem('userType')
  userType.value = t ? Number(t) : null
  if (n) {
    nickname.value = n
    nav.value = navAll.filter(i => !userType.value || i.roles.includes(userType.value))
    return
  }
  if (token) {
    try {
      const { data } = await http.get('/auth/profile')
      if (data && data.code === 1 && data.data) {
        nickname.value = String(data.data.nickname || '')
        userType.value = Number(data.data.userType || 0) || null
        localStorage.setItem('nickname', nickname.value)
        if (userType.value) localStorage.setItem('userType', String(userType.value))
        nav.value = navAll.filter(i => !userType.value || i.roles.includes(userType.value))
      }
    } catch (e) {}
  }
}
onMounted(refreshAuth)
watch(() => route.path, refreshAuth)

const logout = async () => {
  try { await http.post('/auth/logout') } catch (e) {}
  localStorage.removeItem('token')
  localStorage.removeItem('userId')
  localStorage.removeItem('nickname')
  localStorage.removeItem('userType')
  loggedIn.value = false
  nickname.value = ''
  userType.value = null
  nav.value = navAll
  router.push('/login')
}
</script>

<template>
  <div class="layout">
    <aside class="sidebar">
      <div class="brand">校园互助跑腿</div>
      <nav class="menu">
        <router-link v-for="item in nav" :key="item.path" :to="item.path" class="menu-item" :class="{active: route.path===item.path}">
          <span class="dot"></span>
          <span>{{ item.label }}</span>
        </router-link>
      </nav>
      <div class="sidebar-footer">
        <router-link v-if="!loggedIn" to="/login" class="login-btn">登录</router-link>
        <div v-else class="user">
          <div class="avatar">{{ nickname?.[0] || 'U' }}</div>
          <div class="name">{{ nickname || '已登录' }}</div>
          <button class="logout" @click="logout">退出</button>
        </div>
      </div>
    </aside>
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
.layout{display:flex;height:100vh}
.sidebar{width:220px;background:linear-gradient(180deg,#ffffff 0%,#f7fafc 100%);border-right:1px solid #e5e7eb;padding:16px;display:flex;flex-direction:column}
.brand{font-weight:700;margin-bottom:12px}
.menu{display:flex;flex-direction:column;gap:6px}
.menu-item{display:flex;align-items:center;gap:8px;padding:10px 12px;border-radius:10px;color:#1f2937;border:1px solid #e5e7eb;background:#fff}
.menu-item:hover{background:#f9fafb}
.menu-item.active{background:#42b883;color:#fff;border-color:#42b883}
.menu-item .dot{width:6px;height:6px;border-radius:50%;background:#9ca3af}
.menu-item.active .dot{background:#fff}
.sidebar-footer{margin-top:auto}
.login-btn{display:block;text-align:center;padding:10px 12px;border:1px solid #e5e7eb;border-radius:10px;background:#fff}
.user{display:flex;align-items:center;gap:8px;padding:10px 12px;border:1px solid #e5e7eb;border-radius:10px;background:#fff}
.avatar{width:28px;height:28px;border-radius:50%;background:#42b883;color:#fff;display:flex;align-items:center;justify-content:center;font-weight:700}
.name{font-weight:600}
.logout{margin-left:auto;border:1px solid #e5e7eb;background:#fff;border-radius:8px;padding:6px 10px;cursor:pointer}
.content{flex:1;overflow:auto;padding:18px;background:#f5f7fb}
</style>
