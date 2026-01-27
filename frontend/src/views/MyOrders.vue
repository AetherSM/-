<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
import { ElMessage } from 'element-plus'
const orders = ref([])
const loading = ref(false)
const error = ref('')
const userId = localStorage.getItem('userId')
const status = ref(null) // null: 全部, 4: 已完成, 5: 已取消
const reviewVisible = ref(false)
const currentOrderNo = ref('')
const rating = ref(5)
const content = ref('')
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const params = { userId }
    if (status.value) params.status = status.value
    const { data } = await http.get('/api/errands/my', { params })
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
    if (data && data.code === 1) { ElMessage.success('已完成'); await load() }
    else { error.value = data?.msg || '完成失败'; ElMessage.error(error.value) }
  } catch (e) { error.value = '请求失败'; ElMessage.error(error.value) }
}
const openReview = (orderNo) => {
  currentOrderNo.value = orderNo
  rating.value = 5
  content.value = ''
  reviewVisible.value = true
}
const submitReview = async () => {
  try {
    const body = { rating: rating.value, content: content.value, orderType: 2 }
    const { data } = await http.post(`/orders/${currentOrderNo.value}/reviews`, body)
    if (data && data.code === 1) { ElMessage.success('评价成功'); reviewVisible.value = false }
    else { ElMessage.error(data?.msg || '评价失败') }
  } catch (e) { ElMessage.error('请求失败') }
}
</script>

<template>
  <div>
    <h2>我的订单</h2>
    <div class="toolbar">
      <label>筛选：</label>
      <select v-model="status" @change="load">
        <option :value="null">全部</option>
        <option :value="4">历史-已完成</option>
        <option :value="5">历史-已取消</option>
      </select>
      <button class="btn gray" @click="load">刷新</button>
    </div>
    <div v-for="item in orders" :key="item.orderId" class="card">
      <div class="row">
        <div class="title">{{ item.title }}</div>
        <div class="status">{{ item.orderStatus }}</div>
      </div>
      <div class="desc">{{ item.description }}</div>
      <div class="ops">
        <button class="btn" @click="complete(item.orderNo)">完成订单</button>
        <button class="btn gray" @click="openReview(item.orderNo)">评价</button>
      </div>
    </div>
    <div v-if="!loading && orders.length===0" class="empty">暂无数据</div>
    <div v-if="error" class="error">{{ error }}</div>
    <el-dialog v-model="reviewVisible" title="订单评价" width="520px">
      <div class="review-body">
        <div class="row">
          <span>评分：</span>
          <el-rate v-model="rating" :max="5" />
        </div>
        <div class="row">
          <span>内容：</span>
          <el-input v-model="content" type="textarea" :rows="3" placeholder="请输入评价内容" />
        </div>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <button class="btn" @click="submitReview">提交评价</button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.card{padding:12px;border:1px solid #eee;border-radius:8px;background:#fff;margin-bottom:10px}
.row{display:flex;justify-content:space-between;align-items:center;margin:6px 0}
.title{font-weight:600}
.status{color:#999}
.desc{color:#666;font-size:14px}
.toolbar{display:flex;gap:8px;align-items:center;margin-bottom:12px}
.ops{display:flex;gap:8px;margin-top:8px}
.btn{padding:8px 12px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.btn.gray{background:#e5e7eb;color:#111827}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
.review-body{display:flex;flex-direction:column;gap:12px}
</style>
