<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
import { ElMessage } from 'element-plus'
const list = ref([])
const loading = ref(false)
const error = ref('')
const userId = Number(localStorage.getItem('userId') || 0)
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await http.get('/api/coupons/list')
    if (data && data.code === 1) list.value = data.data || []
    else error.value = data?.msg || '加载失败'
  } catch (e) { error.value = '请求失败' } finally { loading.value = false }
}
const receive = async (couponId) => {
  try {
    const { data } = await http.post('/api/coupons/receive', null, { params: { userId, couponId } })
    if (data && data.code === 1) ElMessage.success('领取成功')
    else ElMessage.error(data?.msg || '领取失败')
  } catch (e) { ElMessage.error('请求失败') }
}
onMounted(load)
</script>

<template>
  <div>
    <h2>可领取优惠券</h2>
    <div class="grid">
      <div v-for="c in list" :key="c.couponId" class="card">
        <div class="name">{{ c.name }}</div>
        <div class="desc">类型 {{ c.type }} ｜ 面值 {{ c.value }} ｜ 门槛 {{ c.minSpend }}</div>
        <div class="ops">
          <button class="btn" @click="receive(c.couponId)">领取</button>
        </div>
      </div>
    </div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<style scoped>
.grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(280px,1fr));gap:12px}
.card{padding:12px;border:1px solid #e5e7eb;border-radius:12px;background:#fff}
.name{font-weight:600}
.desc{color:#6b7280;margin:6px 0}
.ops{display:flex;gap:8px}
.btn{padding:8px 12px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.error{padding:12px;color:#d33}
</style>
