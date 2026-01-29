<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
import { ElMessage } from 'element-plus'

const list = ref([])
const loading = ref(false)
const error = ref('')

const statusMap = {
  1: '待支付',
  2: '待发货',
  3: '待收货',
  4: '已完成',
  5: '已取消'
}

const load = async () => {
  loading.value = true
  try {
    const { data } = await http.get('/orders/seller')
    if (data && data.code === 1) {
      list.value = data.data || []
    } else {
      error.value = data?.msg || '加载失败'
    }
  } catch (e) {
    error.value = '请求失败'
  } finally {
    loading.value = false
  }
}

const ship = async (orderNo) => {
  try {
    const { data } = await http.post(`/orders/${orderNo}/ship`)
    if (data && data.code === 1) {
      ElMessage.success('发货成功')
      await load()
    } else {
      ElMessage.error(data?.msg || '发货失败')
    }
  } catch (e) {
    ElMessage.error('请求失败')
  }
}

onMounted(load)
</script>

<template>
  <div>
    <div class="header">
      <h2>订单管理</h2>
      <button class="btn gray" @click="load">刷新</button>
    </div>
    
    <div v-if="loading" class="loading">加载中...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="list.length === 0" class="empty">暂无订单</div>
    
    <div v-else class="list">
      <div v-for="item in list" :key="item.orderId" class="card">
        <div class="row header-row">
          <span>订单号: {{ item.orderNo }}</span>
          <span class="status">{{ statusMap[item.orderStatus] }}</span>
        </div>
        <div class="row">
          <div class="amount">总额: ¥{{ item.totalAmount }}</div>
          <div class="time">{{ item.createTime }}</div>
        </div>
        <div class="address-box">
          <div>收货人: {{ item.contactName }} {{ item.contactPhone }}</div>
          <div>地址: {{ item.deliveryAddress }}</div>
        </div>
        <div class="ops" v-if="item.orderStatus === 2">
          <button class="btn primary" @click="ship(item.orderNo)">确认发货</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header{display:flex;justify-content:space-between;align-items:center;margin-bottom:16px}
.list{display:flex;flex-direction:column;gap:12px}
.card{padding:12px;border:1px solid #eee;border-radius:8px;background:#fff}
.row{display:flex;justify-content:space-between;margin-bottom:8px}
.header-row{border-bottom:1px solid #f5f5f5;padding-bottom:8px;margin-bottom:8px;font-size:13px;color:#666}
.status{color:#42b883;font-weight:600}
.amount{font-weight:600;font-size:16px;color:#333}
.time{font-size:12px;color:#999}
.address-box{background:#f9f9f9;padding:8px;border-radius:4px;font-size:13px;color:#555;margin-bottom:8px}
.ops{text-align:right;border-top:1px solid #f5f5f5;padding-top:8px}
.btn{padding:6px 12px;border:none;border-radius:4px;cursor:pointer}
.btn.gray{background:#f3f4f6;color:#333}
.btn.primary{background:#42b883;color:#fff}
.loading,.empty,.error{padding:20px;text-align:center;color:#999}
.error{color:#d33}
</style>
