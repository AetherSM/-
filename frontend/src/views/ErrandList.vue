<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
const list = ref([])
const loading = ref(false)
const error = ref('')
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await http.get('/api/errands/open')
    if (data && data.code === 1) {
      list.value = data.data || []
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
  <div class="grid">
    <div v-for="item in list" :key="item.orderId" class="card">
      <div class="row">
        <div class="title">{{ item.title }}</div>
        <div class="status">待接单</div>
      </div>
      <div class="desc">{{ item.description }}</div>
      <div class="row">
        <div>赏金 ¥{{ item.reward }}</div>
        <div>联系人 {{ item.contact_name || item.contactName }}</div>
      </div>
    </div>
    <div v-if="!loading && list.length===0" class="empty">暂无数据</div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<style scoped>
.grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(280px,1fr));gap:12px}
.card{padding:12px;border:1px solid #eee;border-radius:8px;background:#fff}
.row{display:flex;justify-content:space-between;align-items:center;margin:6px 0}
.title{font-weight:600}
.status{color:#42b883}
.desc{color:#666;font-size:14px}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
