<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([])
const loading = ref(false)
const error = ref('')
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await http.get('/api/shopping-records')
    if (data && data.code === 1) list.value = data.data || []
    else error.value = data?.msg || '加载失败'
  } catch (e) {
    error.value = '请求失败'
  } finally {
    loading.value = false
  }
}
onMounted(load)
const removeItem = async (id) => {
  try {
    await ElMessageBox.confirm('确认删除该记录？', '提示', { type: 'warning' })
  } catch (e) { return }
  try {
    const { data } = await http.delete(`/api/shopping-records/${id}`)
    if (data && data.code === 1) {
      ElMessage.success('删除成功')
      await load()
    } else {
      ElMessage.error(data?.msg || '删除失败')
    }
  } catch (e) {
    ElMessage.error('请求失败')
  }
}
</script>

<template>
  <div>
    <h2>购物记录</h2>
    <div class="list">
      <div v-for="r in list" :key="r.recordId" class="row">
        <img :src="r.productImage || 'https://via.placeholder.com/80x80?text=Img'" />
        <div class="info">
          <div class="name">{{ r.productName }}</div>
          <div class="meta">订单号 {{ r.orderNo }}</div>
        </div>
        <div class="price">¥{{ r.price }}</div>
        <div class="qty">x{{ r.quantity }}</div>
        <div class="subtotal">小计 ¥{{ r.subtotal }}</div>
        <button class="btn gray" @click="removeItem(r.recordId)">删除</button>
      </div>
    </div>
    <div v-if="!loading && list.length===0" class="empty">暂无记录</div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<style scoped>
.list{display:flex;flex-direction:column;gap:8px}
.row{display:grid;grid-template-columns:80px 1fr 120px 80px 160px 100px;gap:10px;align-items:center;padding:12px;border:1px solid #e5e7eb;border-radius:12px;background:#fff}
.row img{width:80px;height:80px;object-fit:cover;border-radius:8px;background:#f3f4f6}
.name{font-weight:600}
.meta{color:#6b7280;font-size:12px}
.price{color:#ef4444;font-weight:600}
.qty{color:#374151}
.subtotal{color:#111827;font-weight:600}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
.btn{padding:8px 12px;border:none;border-radius:10px;background:#e5e7eb;color:#111827;cursor:pointer}
</style>
