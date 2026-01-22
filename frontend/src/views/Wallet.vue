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
    const { data } = await http.get('/wallet/transactions')
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
  <div>
    <h2>钱包流水</h2>
    <div v-for="t in list" :key="t.transactionId" class="row">
      <div>#{{ t.transactionId }}</div>
      <div>类型 {{ t.transactionType }}</div>
      <div>金额 ¥{{ t.amount }}</div>
      <div>余额 {{ t.balanceAfter }}</div>
      <div>{{ t.description }}</div>
    </div>
    <div v-if="!loading && list.length===0" class="empty">暂无数据</div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<style scoped>
.row{display:grid;grid-template-columns:80px 100px 120px 120px 1fr;gap:8px;padding:8px;border-bottom:1px solid #eee;background:#fff}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
