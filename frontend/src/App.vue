<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import http from './services/http'
const route = useRoute()
const nav = ref([
  { path: '/errands', label: '跑腿广场' },
  { path: '/errands/create', label: '发布跑腿' },
  { path: '/orders', label: '我的订单' },
  { path: '/wallet', label: '钱包' },
  { path: '/shop', label: '商品商城' },
  { path: '/shop/create', label: '发布商品' }
])
const nickname = ref('')
const loggedIn = ref(false)
const refreshAuth = async () => {
  const token = localStorage.getItem('token')
  loggedIn.value = !!token
  const n = localStorage.getItem('nickname')
  if (n) {
    nickname.value = n
    return
  }
  if (token) {
    try {
      const { data } = await http.get('/auth/profile')
      if (data && data.code === 1 && data.data) {
        nickname.value = String(data.data.nickname || '')
        localStorage.setItem('nickname', nickname.value)
      }
    } catch (e) {}
  }
}
onMounted(refreshAuth)
watch(() => route.path, refreshAuth)
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
.content{flex:1;overflow:auto;padding:18px;background:#f5f7fb}
</style>
