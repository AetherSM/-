<script setup>
import { ref } from 'vue'
import http from '../services/http'
import { useRouter } from 'vue-router'
const router = useRouter()
const phone = ref('')
const password = ref('')
const loading = ref(false)
const message = ref('')
const submit = async () => {
  loading.value = true
  message.value = ''
  try {
    const { data } = await http.post('/auth/login', { phone: phone.value, password: password.value })
    if (data && data.code === 1 && data.data && data.data.token) {
      localStorage.setItem('token', data.data.token)
      localStorage.setItem('userId', String(data.data.user.userId))
      localStorage.setItem('nickname', String(data.data.user.nickname || ''))
      router.push('/errands')
    } else {
      message.value = data && data.msg ? data.msg : '登录失败'
    }
  } catch (e) {
    message.value = '请求失败'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="card">
    <h2>登录</h2>
    <div class="field">
      <label>手机号</label>
      <input v-model="phone" placeholder="请输入手机号" />
    </div>
    <div class="field">
      <label>密码</label>
      <input v-model="password" type="password" placeholder="请输入密码" />
    </div>
    <button class="btn" :disabled="loading" @click="submit">{{ loading ? '提交中' : '登录' }}</button>
    <div class="msg" v-if="message">{{ message }}</div>
  </div>
  <div class="tip">测试账号可用数据库中的手机号，密码为123456</div>
  <div class="shortcut">
    <button class="link" @click="phone='13800000003';password='123456'">填充张三</button>
    <button class="link" @click="phone='13800000004';password='123456'">填充李四</button>
  </div>
  </template>

<style scoped>
.card{max-width:360px;margin:40px auto;padding:24px;border:1px solid #eee;border-radius:8px;background:#fff}
.field{display:flex;flex-direction:column;gap:6px;margin-bottom:16px}
.field input{padding:10px;border:1px solid #ddd;border-radius:6px}
.btn{width:100%;padding:10px;border:none;border-radius:6px;background:#42b883;color:#fff;cursor:pointer}
.msg{margin-top:12px;color:#d33}
.tip{text-align:center;margin-top:12px;color:#666}
.shortcut{display:flex;justify-content:center;gap:8px;margin-top:8px}
.link{padding:6px 10px;border:1px solid #ddd;border-radius:6px;background:#f7f7f7;cursor:pointer}
</style>
