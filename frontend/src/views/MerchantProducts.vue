<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import http from '../services/http'
import { ElMessage } from 'element-plus'

const router = useRouter()
const list = ref([])
const loading = ref(false)
const error = ref('')
const sellerId = Number(localStorage.getItem('userId') || 0)

const load = async () => {
  loading.value = true
  try {
    // Reuse search API with sellerId
    const { data } = await http.get('/products', { params: { sellerId } })
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

const goCreate = () => {
  router.push('/merchant/product/create')
}

const goEdit = (id) => {
  router.push({ path: '/merchant/product/create', query: { id } })
}

const toggleStatus = async (item) => {
  try {
    const newStatus = item.status === 1 ? 0 : 1
    const { data } = await http.patch(`/products/${item.productId}/status`, { status: newStatus })
    if (data && data.code === 1) {
      ElMessage.success('状态已更新')
      item.status = newStatus
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
    <div class="header">
      <h2>商品管理</h2>
      <button class="btn primary" @click="goCreate">发布新商品</button>
    </div>
    
    <div v-if="loading" class="loading">加载中...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="list.length === 0" class="empty">暂无商品</div>
    
    <div v-else class="grid">
      <div v-for="item in list" :key="item.productId" class="card">
        <div class="row">
          <div class="title">{{ item.productName }}</div>
          <div class="status" :class="{off: item.status===0}">{{ item.status===1?'上架中':'已下架' }}</div>
        </div>
        <div class="price">¥{{ item.price }}</div>
        <div class="info">库存: {{ item.stock }} | 销量: {{ item.salesCount }}</div>
        <div class="address" v-if="item.shippingAddress">发货地: {{ item.shippingAddress }}</div>
        <div class="ops">
          <button class="btn" @click="goEdit(item.productId)">编辑</button>
          <button class="btn" :class="item.status===1?'warn':'success'" @click="toggleStatus(item)">
            {{ item.status===1 ? '下架' : '上架' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header{display:flex;justify-content:space-between;align-items:center;margin-bottom:16px}
.grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(300px,1fr));gap:12px}
.card{padding:12px;border:1px solid #eee;border-radius:8px;background:#fff}
.row{display:flex;justify-content:space-between;align-items:center;margin-bottom:8px}
.title{font-weight:600;font-size:16px}
.status{color:#42b883;font-size:12px;padding:2px 6px;border-radius:4px;background:#e6f8ef}
.status.off{color:#999;background:#f3f4f6}
.price{color:#f56c6c;font-weight:700;font-size:16px;margin-bottom:6px}
.info{color:#666;font-size:13px;margin-bottom:6px}
.address{color:#888;font-size:12px;margin-bottom:8px}
.ops{display:flex;gap:8px;margin-top:8px}
.btn{padding:6px 12px;border:1px solid #ddd;border-radius:4px;background:#fff;cursor:pointer;font-size:14px}
.btn.primary{background:#42b883;color:#fff;border-color:#42b883}
.btn.warn{color:#e6a23c;border-color:#e6a23c}
.btn.success{color:#67c23a;border-color:#67c23a}
.loading,.empty,.error{padding:20px;text-align:center;color:#999}
.error{color:#d33}
</style>
