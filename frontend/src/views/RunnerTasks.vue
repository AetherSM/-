<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
import { ElMessage } from 'element-plus'

const list = ref([])
const loading = ref(false)
const error = ref('')
const runnerId = Number(localStorage.getItem('userId') || 0)

const statusMap = {
  1: '待接单',
  2: '已接单',
  3: '配送中',
  4: '已完成',
  5: '已取消'
}

const load = async () => {
  loading.value = true
  try {
    const { data } = await http.get('/api/errands/runner', { params: { runnerId } })
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

const complete = async (orderNo) => {
  try {
    const { data } = await http.post('/api/errands/complete', null, { params: { orderNo } })
    if (data && data.code === 1) {
      ElMessage.success('已确认送达')
      await load()
    } else {
      ElMessage.error(data?.msg || '操作失败')
    }
  } catch (e) {
    ElMessage.error('请求失败')
  }
}

onMounted(load)
</script>

<template>
  <div>
    <h2>我的任务</h2>
    <div class="toolbar">
      <button class="btn gray" @click="load">刷新列表</button>
    </div>
    
    <div v-if="loading" class="loading">加载中...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="list.length === 0" class="empty">暂无任务</div>
    
    <div v-else class="grid">
      <div v-for="item in list" :key="item.orderId" class="card">
        <div class="row">
          <div class="title">{{ item.title }}</div>
          <div class="status" :class="{'done': item.orderStatus===4}">{{ statusMap[item.orderStatus] }}</div>
        </div>
        <div class="desc">{{ item.description }}</div>
        <div class="row info">
          <span>赏金: ¥{{ item.reward }}</span>
          <span>{{ item.contactName }} {{ item.contactPhone }}</span>
        </div>
        <div class="row info" v-if="item.pickupCode">
          <span>取件码: {{ item.pickupCode }}</span>
        </div>
        <div class="addresses">
          <div>取: {{ item.pickupAddress }}</div>
          <div>送: {{ item.deliveryAddress }}</div>
        </div>
        <div class="ops">
          <button v-if="item.orderStatus === 2 || item.orderStatus === 3" class="btn" @click="complete(item.orderNo)">确认送达</button>
          <span v-if="item.orderStatus === 4" class="done-tag">已完成</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.toolbar{margin-bottom:12px;text-align:right}
.grid{display:flex;flex-direction:column;gap:12px}
.card{padding:12px;border:1px solid #eee;border-radius:8px;background:#fff}
.row{display:flex;justify-content:space-between;align-items:center;margin-bottom:8px}
.title{font-weight:600;font-size:16px}
.status{color:#42b883;font-weight:500}
.status.done{color:#999}
.desc{color:#666;font-size:14px;margin-bottom:8px}
.info{color:#888;font-size:13px;margin-bottom:8px}
.addresses{font-size:13px;color:#444;background:#f9f9f9;padding:8px;border-radius:4px;margin-bottom:8px}
.btn{padding:6px 12px;border:none;border-radius:4px;background:#42b883;color:#fff;cursor:pointer}
.btn.gray{background:#f3f4f6;color:#333}
.done-tag{color:#999;font-size:13px}
.loading,.empty,.error{padding:20px;text-align:center;color:#999}
.error{color:#d33}
</style>
