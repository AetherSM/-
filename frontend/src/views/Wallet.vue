<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
const list = ref([])
const loading = ref(false)
const error = ref('')
const amount = ref(100)
const recharging = ref(false)
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
const recharge = async () => {
  recharging.value = true
  try {
    const { data } = await http.post('/wallet/recharge', { amount: amount.value })
    if (data && data.code === 1) {
      await load()
    } else {
      error.value = data?.msg || '充值失败'
    }
  } catch (e) {
    error.value = '请求失败'
  } finally {
    recharging.value = false
  }
}
</script>

<template>
  <div>
    <div class="form">
      <h2>钱包充值</h2>
      <div class="row">
        <input v-model.number="amount" type="number" min="1" step="1" />
        <button class="btn" :disabled="recharging" @click="recharge">{{ recharging ? '充值中' : '充值' }}</button>
      </div>
    </div>
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
.form{width:100%;padding:16px;border:1px solid #e5e7eb;border-radius:12px;background:#fff;margin-bottom:12px}
.row{display:flex;gap:8px;align-items:center}
.btn{padding:10px 16px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.row{display:grid;grid-template-columns:80px 100px 120px 120px 1fr;gap:8px;padding:8px;border-bottom:1px solid #eee;background:#fff}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
