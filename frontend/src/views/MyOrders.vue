<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
const orders = ref([])
const loading = ref(false)
const error = ref('')
const userId = localStorage.getItem('userId')
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await http.get('/api/errands/my', { params: { userId } })
    if (data && data.code === 1) {
      orders.value = data.data || []
    } else {
      error.value = data && data.msg ? data.msg : '加载失败'
    }
  } catch (e) {
    error.value = '请求失败'
  } finally {
    loading.value = false
  }
}
onMounted(load)
const complete = async (orderNo) => {
  try {
    const { data } = await http.post('/api/errands/complete', null, { params: { orderNo } })
    if (data && data.code === 1) await load()
    else error.value = data?.msg || '完成失败'
  } catch (e) { error.value = '请求失败' }
}
const review = async (orderNo) => {
  const rating = Number(prompt('请输入评分(1-5)', '5') || '5')
  const content = String(prompt('请输入评价内容', '很好') || '')
  try {
    const body = { rating, content, orderType: 2 } // 跑腿订单类型 2
    const { data } = await http.post(`/orders/${orderNo}/reviews`, body)
    if (data && data.code === 1) alert('评价成功')
    else alert(data?.msg || '评价失败')
  } catch (e) { alert('请求失败') }
}
</script>

<template>
  <div>
    <h2>我的订单</h2>
    <div v-for="item in orders" :key="item.orderId" class="card">
      <div class="row">
        <div class="title">{{ item.title }}</div>
        <div class="status">{{ item.orderStatus }}</div>
      </div>
      <div class="desc">{{ item.description }}</div>
      <div class="ops">
        <button class="btn" @click="complete(item.orderNo)">完成订单</button>
        <button class="btn gray" @click="review(item.orderNo)">评价</button>
      </div>
    </div>
    <div v-if="!loading && orders.length===0" class="empty">暂无数据</div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<style scoped>
.card{padding:12px;border:1px solid #eee;border-radius:8px;background:#fff;margin-bottom:10px}
.row{display:flex;justify-content:space-between;align-items:center;margin:6px 0}
.title{font-weight:600}
.status{color:#999}
.desc{color:#666;font-size:14px}
.ops{display:flex;gap:8px;margin-top:8px}
.btn{padding:8px 12px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.btn.gray{background:#e5e7eb;color:#111827}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
