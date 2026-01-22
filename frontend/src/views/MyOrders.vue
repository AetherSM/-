<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
const orders = ref([])
const loading = ref(false)
const error = ref('')
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const userId = localStorage.getItem('userId')
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
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
